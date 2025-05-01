// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.SQLException;

// public class DatabaseConnection {
//     private static final String URL = "jdbc:mysql://localhost:3306/Bakery";  // <-- Added the database name
//     private static final String USER = "root";
//     private static final String PASSWORD = "";

//     public static Connection getConnection() throws SQLException, ClassNotFoundException {
//         // Load the MySQL JDBC driver
//         Class.forName("com.mysql.cj.jdbc.Driver");  // <-- This is mandatory in Java 9+
//         return DriverManager.getConnection(URL, USER, PASSWORD);
//     }

//     public static void main(String[] args) {
//         try {
//             Connection conn = getConnection();
//             System.out.println("Connected to database successfully!");
//             conn.close();
//         } catch (SQLException | ClassNotFoundException e) {
//             e.printStackTrace();
//         }
        
//     }
// }



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/Bakery";
    private static final String USER = "root";
    private static final String PASSWORD = "bakery2025";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        Connection conn = null; // Declare the connection object outside the try block
        try {
            conn = getConnection();
            System.out.println("Connected to database successfully!");

            // // Insert data into Orders table
            // String insertQuery = "INSERT INTO Orders (order_id, product_id, customer_id, quantity, data_place, total_price) VALUES (?, ?, ?, ?, ?, ?)";
            // try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
            //     stmt.setInt(1, 1001);  // Order ID
            //     stmt.setInt(2, 101);  // Product ID
            //     stmt.setInt(3, 1);  // Customer ID
            //     stmt.setInt(4, 3);  // Quantity
            //     stmt.setDate(5, java.sql.Date.valueOf("2025-04-25"));  // Order date
            //     stmt.setInt(6, 60);  // Total price
            //     int rowsAffected = stmt.executeUpdate();
            //     System.out.println("Inserted " + rowsAffected + " row(s) into the Orders table.");
            // }

           String insertCustomers = "INSERT INTO Customers (customerID, Name) VALUES (?, ?)";
           try (PreparedStatement stmt = conn.prepareStatement(insertCustomers)) {
            stmt.setInt(1, 45);    // customer's id 
            stmt.setString(2, "Pedro"); // Customer's name 
            //stmt.setInt(3, 50);   // order's id
            
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Inserted " + rowsAffected + " row(s) into the Customers table.");
}





        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Always close the connection in a finally block to ensure it's released
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


