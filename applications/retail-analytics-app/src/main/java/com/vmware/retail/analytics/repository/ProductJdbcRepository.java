package com.vmware.retail.analytics.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmware.retail.domain.CustomerFavorites;
import com.vmware.retail.domain.Product;
import com.vmware.retail.domain.ProductQuantity;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.SortedSet;
import java.util.TreeSet;

@Repository
@RequiredArgsConstructor
public class ProductJdbcRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    private final String sql = """
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
    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public CustomerFavorites findCustomerFavoritesByCustomerIdAndTopCount(String customerId, int topCount) {

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
}
