import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage {

    public static void main(String[] args) {
        // Create the main JFrame
        JFrame frame = new JFrame("Bakery System - Main Page");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel welcomeLabel = new JLabel("Welcome to Bakery System");
        welcomeLabel.setBounds(100, 20, 250, 25);
        panel.add(welcomeLabel);

        // Employee Button
        JButton employeeButton = new JButton("Employee");
        employeeButton.setBounds(50, 80, 120, 30);
        panel.add(employeeButton);

        // Manager Button
        JButton managerButton = new JButton("Manager");
        managerButton.setBounds(200, 80, 120, 30);
        panel.add(managerButton);

        // Action listeners
        employeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Placeholder for employee logic
                EmployeePage.showEmployeePage();
            }
        });

        managerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the showManagerPage method from ManagerPage
                ManagerPage.showManagerPage();
            }
        });
        
    }
}
