
--create database
CREATE DATABASE Bakery; 

-- Create Customers table
CREATE TABLE Customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,  
);


-- Create Product table
CREATE TABLE Product (
    product_id INT PRIMARY KEY,
    product_name VARCHAR(20),
    inventory INT,
    price INT
);

-- infor for product 
INSERT INTO Product (product_id, product_name, inventory, price) VALUES
(101, 'Chocolate Cake', 25, 18.99),
(102, 'Blueberry Muffin', 60, 3.49),
(103, 'Croissant', 50, 2.75),
(104, 'Banana Bread', 30, 4.99),
(105, 'Apple Pie', 20, 14.50),
(106, 'Donut', 70, 1.99),
(107, 'Bagel', 55, 2.25),
(108, 'Cupcake', 40, 3.75),
(109, 'Cheesecake Slice', 20, 5.99),
(110, 'Brownie', 45, 2.99),
(111, 'Cinnamon Roll', 35, 4.25),
(112, 'Lemon Tart', 25, 4.50),
(113, 'Macaron', 50, 1.50),
(114, 'Strawberry Shortcake', 18, 6.99),
(115, 'Pumpkin Pie', 22, 13.99),
(116, 'Coffee', 100, 2.50),
(117, 'Hot Chocolate', 80, 2.75);




-- Create Orders table
CREATE TABLE Orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    customer_id INT,
    quantity INT UNSIGNED,
    total_price INT,
    FOREIGN KEY (product_id) REFERENCES Product(product_id),
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)
);

ALTER TABLE `Orders` ADD date DATE; 



-- Create Employees table
CREATE TABLE Employees (
    employee_id INT PRIMARY KEY,
    name VARCHAR(20),
    phone_number VARCHAR(15),
    email VARCHAR(30),
    hire_date DATE
);

-- Insert into Customers
INSERT INTO Customers (customer_id, name) VALUES
(1, 'Valeria Arango'),
(2, 'Bob Johnson'),
(3, 'Charlie Lee'),
(4, 'Luis Calderon'),
(5, 'Ethan Clark'),
(6, 'Fiona White'),
(7, 'George Lewis'),
(8, 'Hannah Scott'),
(9, 'Ivan Reed'),
(10, 'Jenna Hall'),
(11, 'Kevin Rodas'),
(12, 'Laura Brooks'),
(13, 'Michael Young'),
(14, 'Nina Turner'),
(15, 'Oscar Gonzales');


-- Insert into Employees
INSERT INTO Employees (employee_id, name, phone_number, email, hire_date) VALUES
(201, 'Sarah Miles', '1234567890', 'sarah@example.com', '2022-01-15'),
(202, 'Tom Green', '1234567891', 'tom@example.com', '2021-12-10'),
(203, 'Olivia Brown', '1234567892', 'olivia@example.com', '2023-03-01'),
(204, 'Noah Walker', '1234567893', 'noah@example.com', '2020-11-21'),
(205, 'Emma Davis', '1234567894', 'emma@example.com', '2024-02-14'),
(206, 'Liam Turner', '1234567895', 'liam@example.com', '2022-08-09'),
(207, 'Sophia Harris', '1234567896', 'sophia@example.com', '2021-05-03'),
(208, 'Mason Allen', '1234567897', 'mason@example.com', '2023-06-12'),
(209, 'Isabella Young', '1234567898', 'isabella@example.com', '2024-07-07'),
(210, 'James Moore', '1234567899', 'james@example.com', '2020-10-18'),
(211, 'Ava Martinez', '1234567800', 'ava@example.com', '2022-04-25'),
(212, 'William Perez', '1234567801', 'william@example.com', '2023-09-30'),
(213, 'Lily Carter', '1234567802', 'lily@example.com', '2021-01-10'),
(214, 'Daniel Evans', '1234567803', 'daniel@example.com', '2023-02-15'),
(215, 'Chloe Bell', '1234567804', 'chloe@example.com', '2024-10-05');
