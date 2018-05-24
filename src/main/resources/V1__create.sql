CREATE TABLE CUSTOMER (
	customer_id BIGINT(20) NOT NULL AUTO_INCREMENT,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	username VARCHAR(50) NOT NULL UNIQUE,
	PRIMARY KEY(customer_id)
);

CREATE TABLE LOCATION (
	location_id BIGINT(20) NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL UNIQUE,
	country VARCHAR(50),
	city VARCHAR(50) NOT NULL,
	county VARCHAR(50),
	street VARCHAR(50) NOT NULL,
	PRIMARY KEY(location_id)
);

CREATE TABLE ORDERS (
	order_id BIGINT(20) NOT NULL AUTO_INCREMENT,
	location_id BIGINT(20),
	customer_id BIGINT(20) NOT NULL,
	country VARCHAR(50),
	city VARCHAR(50) NOT NULL,
	county VARCHAR(50),
	street VARCHAR(50) NOT NULL,
	PRIMARY KEY(order_id),
 	CONSTRAINT FK_location FOREIGN KEY (location_id) REFERENCES LOCATION (location_id),
 	CONSTRAINT FK_customer FOREIGN KEY (customer_id) REFERENCES CUSTOMER (customer_id)
);

CREATE TABLE PRODUCT_CATEGORY (
	category_id BIGINT(20) NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	description VARCHAR(50),
	PRIMARY KEY(category_id)
);

CREATE TABLE SUPPLIER (
	supplier_id BIGINT(20) NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	PRIMARY KEY(supplier_id)
);

CREATE TABLE PRODUCT (
	product_id BIGINT(20) NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	description VARCHAR(50),
	price DECIMAL(6,2),
	weight DECIMAL(4,2),
	category_id BIGINT(20) NOT NULL,
	supplier_id BIGINT(20) NOT NULL,
	PRIMARY KEY(product_id),
 	CONSTRAINT FK_category FOREIGN KEY (category_id) REFERENCES PRODUCT_CATEGORY (category_id),
 	CONSTRAINT FK_supplier FOREIGN KEY (supplier_id) REFERENCES SUPPLIER (supplier_id)
);

CREATE TABLE ORDER_DETAIL (
	order_id BIGINT(20) NOT NULL,
	product_id BIGINT(20) NOT NULL,
	quantity INT,
	PRIMARY KEY(order_id, product_id),
 	CONSTRAINT FK_order FOREIGN KEY (order_id) REFERENCES ORDERS (order_id),
 	CONSTRAINT FK_product FOREIGN KEY (product_id) REFERENCES PRODUCT (product_id)
);

CREATE TABLE STOCK (
	product_id BIGINT(20) NOT NULL,
	location_id BIGINT(20) NOT NULL,
	quantity INT,
	PRIMARY KEY(product_id, location_id),
 	CONSTRAINT FK_product_2 FOREIGN KEY (product_id) REFERENCES PRODUCT (product_id),
 	CONSTRAINT FK_location_2 FOREIGN KEY (location_id) REFERENCES LOCATION (location_id)
);