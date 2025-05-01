
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.SQLException;
// import java.util.Random; 


// public class DatabaseConnection {
//     private static final String URL = "jdbc:mysql://localhost:3306/Bakery";
//     private static final String USER = "root";
//     private static final String PASSWORD = "bakery2025";

//     public static Connection getConnection() throws SQLException, ClassNotFoundException {
//         Class.forName("com.mysql.cj.jdbc.Driver");
//         return DriverManager.getConnection(URL, USER, PASSWORD);
//     }

//     public static void main(String[] args) {
//         Connection conn = null; // Declare the connection object outside the try block

//         Random rand = new Random(); 
//         int randNumber = rand.nextInt(500); 

//         try {
//             conn = getConnection();
//             System.out.println("Connected to database successfully!");

//             // // Insert data into Orders table
//             // String insertQuery = "INSERT INTO Orders (order_id, product_id, customer_id, quantity, data_place, total_price) VALUES (?, ?, ?, ?, ?, ?)";
//             // try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
//             //     stmt.setInt(1, 1001);  // Order ID
//             //     stmt.setInt(2, 101);  // Product ID
//             //     stmt.setInt(3, 1);  // Customer ID
//             //     stmt.setInt(4, 3);  // Quantity
//             //     stmt.setDate(5, java.sql.Date.valueOf("2025-04-25"));  // Order date
//             //     stmt.setInt(6, 60);  // Total price
//             //     int rowsAffected = stmt.executeUpdate();
//             //     System.out.println("Inserted " + rowsAffected + " row(s) into the Orders table.");
//             // }

//            String insertCustomers = "INSERT INTO Customers (customerID, Name) VALUES (?, ?)";
//            try (PreparedStatement stmt = conn.prepareStatement(insertCustomers)) {
//             stmt.setInt(1, randNumber);    // customer's id 
//             stmt.setString(2, "Luis"); // Customer's name 
//             //stmt.setInt(3, 50);   // order's id
            
//             int rowsAffected = stmt.executeUpdate();
//             System.out.println("Inserted " + rowsAffected + " row(s) into the Customers table.");
// }





//         } catch (SQLException | ClassNotFoundException e) {
//             e.printStackTrace();
//         } finally {
//             // Always close the connection in a finally block to ensure it's released
//             if (conn != null) {
//                 try {
//                     conn.close();
//                 } catch (SQLException e) {
//                     e.printStackTrace();
//                 }
//             }
//         }
//     }
// }


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/Bakery";
    private static final String USER = "root";
    private static final String PASSWORD = "bakery2025";

   private static Connection getConnection() throws SQLException, ClassNotFoundException {
    Class.forName("com.mysql.cj.jdbc.Driver");
    return java.sql.DriverManager.getConnection(URL, USER, PASSWORD);
}


    public static void main(String[] args) {
        // Create the JFrame
        JFrame frame = new JFrame("Customer Data Entry");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the panel
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        // Display the window
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // Create JLabel and JTextField for Name
        JLabel nameLabel = new JLabel("Customer Name:");
        nameLabel.setBounds(10, 20, 120, 25);
        panel.add(nameLabel);

        JTextField nameText = new JTextField(20);
        nameText.setBounds(150, 20, 200, 25);
        panel.add(nameText);

        // Create Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(150, 80, 100, 25);
        panel.add(submitButton);

        // Add ActionListener for the button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String customerName = nameText.getText();
                insertCustomerData(customerName);
            }
        });
    }

    private static void insertCustomerData(String customerName) {
        Random rand = new Random();
        int randNumber = rand.nextInt(500); // Generate a random customerID

        try (Connection conn = DatabaseConnection.getConnection()) {
            String insertCustomers = "INSERT INTO Customers (customerID, Name) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertCustomers)) {
                stmt.setInt(1, randNumber);
                stmt.setString(2, customerName);

                int rowsAffected = stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Inserted " + rowsAffected + " row(s) into the Customers table.");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}


