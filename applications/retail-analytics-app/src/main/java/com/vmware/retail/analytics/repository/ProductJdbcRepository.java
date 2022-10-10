package com.vmware.retail.analytics.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmware.retail.domain.CustomerFavorites;
import com.vmware.retail.domain.Product;
import com.vmware.retail.domain.ProductQuantity;
import com.vmware.retail.domain.order.ProductOrder;
import lombok.RequiredArgsConstructor;
import nyla.solutions.core.patterns.jdbc.Sql;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import static nyla.solutions.core.util.Organizer.toMap;

@Repository
@RequiredArgsConstructor
public class ProductJdbcRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    private double confidence;


    @Override
    public CustomerFavorites findCustomerFavoritesByCustomerIdAndTopCount(String customerId, int topCount) {

        final String sql = """
            SELECT data, total_quantity
            from products p, 
               (SELECT sum(quantity) total_quantity, 
                    product_id 
            FROM customer_order_entity 
            WHERE customer_id = ? 
            GROUP BY product_id order by total_quantity 
            DESC 
            FETCH FIRST ? rows only) top_orders
            WHERE p.id = top_orders.product_id;
            """;

        final SortedSet<ProductQuantity> productQuantities = new TreeSet<>();

        jdbcTemplate.query(sql, rs -> {
                    try {
                        var productJson = rs.getString("data");
                        var product = objectMapper.readValue(productJson, Product.class);
                        productQuantities.add(new ProductQuantity(product, rs.getInt("total_quantity")));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                },
                customerId,
                topCount);

        return new CustomerFavorites(customerId, productQuantities);
    }

    @Override
    public List<Product> findFrequentlyBoughtTogether(List<ProductOrder> productOrders) {

        //Notes In causes have limits depending on database
        // Postgres does set an exact count limit

        final String sql = """
                select p.data,top_associations.original_SKU,
                  top_associations.bought_with,top_associations.times_bought_together,
                 count_by_product.product_cnt,
                 cast(top_associations.times_bought_together as double precision)/cast(count_by_product.product_cnt as  double precision) as probability
                from (
                SELECT c.original_SKU as original_SKU, c.bought_with as bought_with, count(*) as times_bought_together
                FROM (
                  SELECT a.product_id as original_SKU, b.product_id as bought_with
                  FROM customer_orders a
                  INNER join customer_orders b
                  ON a.order_id = b.order_id AND a.product_id != b.product_id ) c
                GROUP BY c.original_SKU, c.bought_with
                having original_SKU in (:productIds)  and bought_with not in (:productIds)
                ORDER BY times_bought_together desc
                FETCH FIRST 10 rows only) top_associations,
                (select product_id, sum(quantity) as product_cnt
                      from customer_orders
                      group by product_id) count_by_product,
                products p
                where count_by_product.product_id = top_associations.original_SKU
                and cast(top_associations.times_bought_together as double precision)/
                cast(count_by_product.product_cnt as  double precision) > :confidence
                and  p.id = top_associations.original_SKU
                """;


        Map<String, ?> map = toMap("productIds", productOrders.stream().map( po -> po.productId()).toList(),
                "confidence",confidence);


        RowMapper<Product> rowMapper = (rs , rowNum) -> {
            try {
                return objectMapper.readValue(rs.getString("data"),Product.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };

        return this.namedParameterJdbcTemplate.query(sql,map, rowMapper);
    }

}
