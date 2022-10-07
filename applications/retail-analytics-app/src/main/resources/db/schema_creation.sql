

create table customer_order_entity (id bigint not null,
customer_id varchar(255),
product_id varchar(255),
quantity integer not null, primary key (id));


INSERT INTO public.customer_order_entity
(id, customer_id, product_id, quantity)
VALUES(1, 'g01', 'sku1', 4);

INSERT INTO public.customer_order_entity
(id, customer_id, product_id, quantity)
VALUES(2, 'g01', 'sku2', 3);

INSERT INTO public.customer_order_entity
(id, customer_id, product_id, quantity)
VALUES(3, 'g01', 'sku3', 50);

INSERT INTO public.customer_order_entity
(id, customer_id, product_id, quantity)
VALUES(4, 'g01', 'sku1', 2);

create table products(
id text PRIMARY KEY,
DATA JSONB);


INSERT INTO public.products(id, data)
VALUES ('sku1','{"id" : "sku1", "name" : "Peanut butter"}');

INSERT INTO public.products(id, data)
VALUES ('sku2','{"id" : "sku2", "name" : "Jelly"}');

INSERT INTO public.products(id, data)
VALUES ('sku3','{"id" : "sku3", "name" : "Bread"}');


INSERT INTO public.products(id, data)
VALUES ('sku4','{"id" : "sku4", "name" : "Milk"}');