import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage {

    public static void main(String[] args) {
        // Create the main JFrame
        JFrame frame = new JFrame("Bakery System - Main Page");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center on screen

        JPanel panel = new JPanel(new GridBagLayout());
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Padding

        // Welcome label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel welcomeLabel = new JLabel("Welcome to Bakery System");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(welcomeLabel, gbc);

        // Employee button
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JButton employeeButton = new JButton("Cashier");
        panel.add(employeeButton, gbc);

        // Manager button
        gbc.gridx = 1;
        JButton managerButton = new JButton("Manager");
        panel.add(managerButton, gbc);

        // Action listeners
        employeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeePage.showEmployeePage();
            }
        });

        managerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManagerPage.showManagerPage();
            }
        });
    }
}
