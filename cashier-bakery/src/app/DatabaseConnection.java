package app;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/BakerySystem";
    private static final String USER = "root";
    private static final String PASSWORD = "rootpassword";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try {
            Connection conn = getConnection();
            System.out.println("Connected to database successfully!");

            Statement stmt = conn.createStatement(); 

                // Create database
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS BakerySystem");
                stmt.executeUpdate("USE BakerySystem");
    
                // Create tables
                stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS Employees (
                        EmployeeID INT PRIMARY KEY AUTO_INCREMENT,
                        Name VARCHAR(100),
                        PhoneNumber VARCHAR(20),
                        Email VARCHAR(100),
                        HireDate DATE
                    )""");
    
                stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS Products (
                        ProductID INT PRIMARY KEY AUTO_INCREMENT,
                        Inventory INT,
                        Price DECIMAL(10,2)
                    )""");
    
                stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS Customers (
                        CustomerID INT PRIMARY KEY AUTO_INCREMENT,
                        Name VARCHAR(100)
                    )""");
    
                stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS Orders (
                        OrderID INT PRIMARY KEY AUTO_INCREMENT,
                        CustomerID INT,
                        EmployeeID INT,
                        DatePlaced DATETIME,
                        TotalPrice DECIMAL(10,2),
                        FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID),
                        FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID)
                    )""");
    
                stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS OrderProducts (
                        OrderID INT,
                        ProductID INT,
                        Quantity INT,
                        PRIMARY KEY (OrderID, ProductID),
                        FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
                        FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
                    )""");

    
                System.out.println("Database and tables created successfully.");

                            // Insert into Products
            stmt.addBatch("""
                INSERT IGNORE INTO Products (ProductID, Inventory, Price) VALUES
                (1, 10, 7.50),
                (2, 20, 2.50),
                (3, 7, 3.00),
                (4, 3, 30.00),
                (5, 25, 4.50),
                (6, 9, 2.00),
                (7, 15, 5.25),
                (8, 5, 2.75),
                (9, 13, 9.50),
                (10, 5, 10.00),
                (11, 6, 5.50),
                (12, 3, 6.75),
                (13, 2, 35.00),
                (14, 9, 15.50),
                (15, 2, 12.00)
            """);

            // Insert into Customers
            stmt.addBatch("""
                INSERT IGNORE INTO Customers (CustomerID, Name) VALUES
                (1000, 'Alice'),
                (1001, 'George'),
                (1002, 'Joe'),
                (1003, 'Bob'),
                (1004, 'Jack'),
                (1005, 'Greg'),
                (1006, 'Sammy'),
                (1007, 'Theresa'),
                (1008, 'Sandy'),
                (1009, 'Shelly'),
                (1010, 'Glinda'),
                (1011, 'Anna'),
                (1012, 'Daisy'),
                (1013, 'Michael'),
                (1014, 'Louis')
            """);

            // Insert into Employees
            stmt.addBatch("""
                INSERT IGNORE INTO Employees (EmployeeID, Name, PhoneNumber, Email, HireDate) VALUES
                (2000, 'Noah', '123-456-7890', 'noah@gmail.com', '2020-05-07'),
                (2001, 'Oliver', '098-765-4321', 'oliver@gmail.com', '2020-05-10'),
                (2002, 'James', '112-916-7990', '7ames@gmail.com', '2020-05-17'),
                (2003, 'Janice', '512-364-2905', 'janice@gmail.com', '2020-05-29'),
                (2004, 'Amelia', '234-456-6321', 'amelia90@gmail.com', '2020-10-07'),
                (2005, 'Sophia', '123-234-1234', 'sophia@gmail.com', '2020-11-07'),
                (2006, 'Emma', '904-321-4123', 'emma@gmail.com', '2021-03-01'),
                (2007, 'Charlotte', '123-234-1222', 'charlotte@gmail.com', '2021-09-02'),
                (2008, 'Lilly', '902-213-4223', 'lillyrose@gmail.com', '2021-06-06'),
                (2009, 'Hank', '900-543-4323', 'hank@gmail.com', '2021-09-10'),
                (2010, 'Kevin', '132-134-2095', 'kevin@gmail.com', '2022-04-23'),
                (2011, 'Richard', '234-456-6333', 'richard3@gmail.com', '2023-05-28'),
                (2012, 'Brad', '523-214-1334', 'bradsmith@gmail.com', '2024-07-08'),
                (2013, 'Lucas', '009-231-4134', 'lucas@gmail.com', '2024-12-13'),
                (2014, 'Ollie', '445-234-2896', '0llie@gmail.com', '2025-04-09')
            """);

            // Insert into Orders
            stmt.addBatch("""
                INSERT IGNORE INTO Orders (OrderID, CustomerID, EmployeeID, DatePlaced, TotalPrice) VALUES
                (3000, 1000, 2000, '2024-09-02', 50.00),
                (3001, 1000, 2007, '2024-09-02', 9.00),
                (3002, 1010, 2013, '2024-09-02', 11.00),
                (3003, 1014, 2014, '2024-09-02', 5.25),
                (3004, 1002, 2014, '2024-09-02', 2.00),
                (3005, 1004, 2006, '2024-09-02', 30.00),
                (3006, 1008, 2003, '2024-09-02', 6.75),
                (3007, 1007, 2012, '2024-09-02', 11.00),
                (3008, 1006, 2013, '2024-09-03', 35.00),
                (3009, 1009, 2012, '2024-09-12', 5.50),
                (3010, 1001, 2013, '2024-10-02', 9.50),
                (3011, 1001, 2002, '2025-01-01', 30.00),
                (3012, 1000, 2008, '2025-02-02', 45.00),
                (3013, 1002, 2005, '2025-03-02', 24.00),
                (3014, 1010, 2007, '2025-05-04', 15.75)
            """);

            // Insert into OrderProducts
            stmt.addBatch("""
                INSERT IGNORE INTO OrderProducts(OrderID, ProductID, Quantity) VALUES
                (3000, 10, 5),
                (3001, 3, 3),
                (3002, 11, 2),
                (3003, 7, 1),
                (3004, 6, 1),
                (3005, 4, 1),
                (3006, 12, 1),
                (3007, 11, 2),
                (3008, 13, 1),
                (3009, 8, 2),
                (3010, 9, 1),
                (3011, 4, 1),
                (3012, 5, 10),
                (3013, 15, 2),
                (3014, 7, 3)
            """);

            stmt.executeBatch();
            System.out.println("Data inserted successfully.");
    
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
