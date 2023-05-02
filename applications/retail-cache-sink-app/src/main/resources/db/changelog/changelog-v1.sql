-- liquibase formatted sql
-- changeset liquibaseuser:1

create sequence customer_seq;
-- rollback drop sequence customer_seq;

CREATE TABLE customer_orders (
	id    integer PRIMARY KEY DEFAULT nextval('customer_seq'),
	customer_id varchar(255) NULL,
	order_id int8 NULL,
	product_id varchar(255) NULL,
	quantity int4 NOT NULL
);
-- rollback drop table customer_orders;

create table products(
id text PRIMARY KEY,
DATA JSONB);
-- rollback drop table products;
