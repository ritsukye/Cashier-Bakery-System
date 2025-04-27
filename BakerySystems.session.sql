CREATE TABLE Customers (
    name VARCHAR(255),
    customer_id INT PRIMARY KEY,
    order_id INT
);

CREATE TABLE Product (
    product_id INT PRIMARY KEY,
    inventory INT,
    price INT
);

CREATE TABLE Orders (
    order_id INT PRIMARY KEY,
    product_id INT,
    customer_id INT,
    quantity INT,
    data_place DATE,
    total_price INT,
    FOREIGN KEY (product_id) REFERENCES Product(product_id),
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)
);

CREATE TABLE Employees (
    name VARCHAR(20),
    employee_id INT PRIMARY KEY,
    phone_number INT,
    email VARCHAR(30),
    hire_date DATE
);

SELECT * FROM Customers; 