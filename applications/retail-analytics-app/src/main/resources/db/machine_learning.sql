
INSERT INTO products(id, data)
VALUES ('sku1','{"id" : "sku1", "name" : "Peanut butter"}');

INSERT INTO products(id, data)
VALUES ('sku2','{"id" : "sku2", "name" : "Jelly"}');

INSERT INTO products(id, data)
VALUES ('sku3','{"id" : "sku3", "name" : "Bread"}');


INSERT INTO products(id, data)
VALUES ('sku4','{"id" : "sku4", "name" : "Milk"}');

CREATE TABLE customer_orders (
	id int8 NOT NULL,
	order_id int8 NOT NULL,
	customer_id varchar(255) NULL,
	product_id varchar(255) NULL,
	quantity int4 NOT NULL,
	CONSTRAINT customer_orders_pkey PRIMARY KEY (id)
);



INSERT INTO customer_orders
(id, order_id, customer_id, product_id, quantity)
VALUES(1, 1, 'nyla', 'sku1', 1);


INSERT INTO customer_orders
(id, order_id, customer_id, product_id, quantity)
VALUES(2, 1, 'nyla', 'sku1', 1);


INSERT INTO customer_orders
(id, order_id, customer_id, product_id, quantity)
VALUES(3, 2, 'nyla', 'sku1', 1);

INSERT INTO customer_orders
(id, order_id, customer_id, product_id, quantity)
VALUES(4, 2, 'nyla', 'sku1', 1);

INSERT INTO customer_orders
(id, order_id, customer_id, product_id, quantity)
VALUES(5, 2, 'nyla', 'sku4', 1);


SELECT c.original_SKU as original_SKU, c.bought_with as bought_with, count(*) as times_bought_together
FROM (
  SELECT a.product_id as original_SKU, b.product_id as bought_with
  FROM customer_orders a
  INNER join customer_orders b
  ON a.order_id = b.order_id AND a.product_id != b.product_id
  and a.order_id != 1 ) c
GROUP BY c.original_SKU, c.bought_with
having original_SKU in ('jelly')  and bought_with not in ('jelly')
ORDER BY times_bought_together desc
FETCH FIRST 10 rows only


-- version 2



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
having original_SKU in ('sku1')  and bought_with not in ('sku1')
ORDER BY times_bought_together desc
FETCH FIRST 10 rows only) top_associations,
(select product_id, sum(quantity) as product_cnt
      from customer_orders
      group by product_id) count_by_product,
products p
where count_by_product.product_id = top_associations.original_SKU
and cast(top_associations.times_bought_together as double precision)/
cast(count_by_product.product_cnt as  double precision) > 0.4
and  p.id = top_associations.original_SKU 


GRANT pg_read_all_data ON TABLE products TO postgres;

select * from pg_tables where tablename = 'products';

/conninfo

select * from products
