-- liquibase formatted sql
-- changeset liquibaseuser:1


CREATE TABLE customer_orders (
	id    integer PRIMARY KEY AUTO_INCREMENT,
	customer_id varchar(255) NULL,
	order_id int8 NULL,
	product_id varchar(255) NULL,
	quantity int4 NOT NULL
);
-- rollback drop table customer_orders;

create table products(
id varchar(40) PRIMARY KEY,
DATA JSON);
-- rollback drop table products;
