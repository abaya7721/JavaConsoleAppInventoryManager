
CREATE DATABASE inventory;

USE inventory;

CREATE TABLE product (
  product_id INT PRIMARY KEY auto_increment,
  name VARCHAR(128 ) null,
  price DOUBLE(10,2) null,
  quantity INT null,
  product_type varchar(32),
  expiration_date DATE null
);

INSERT INTO product (product_id, name, price, quantity, product_type, expiration_date) VALUES
(101, 'Apples', 2.99, 100, 'product', null),
(102, 'Bananas', 1.49, 200, 'product', null),
(103, 'Carrots', 1.99, 150, 'product', null),
(104, 'Bread', 3.50, 300, 'perishable', '2025-03-20'),
(105, 'Cheese', 4.75, 50,  'perishable','2025-03-01'),
(106, 'Milk', 2.30, 250,  'perishable','2025-02-27'),
(107, 'Eggs', 2.99, 400,  'perishable','2025-03-10'),
(108, 'Tomatoes', 2.60, 180, 'product', null),
(109, 'Chicken Breast', 5.50, 220,  'perishable', '2025-02-25'),
(110, 'Lettuce', 1.80, 310, 'product', null);

SELECT * from product;
