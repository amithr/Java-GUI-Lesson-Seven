/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lessonseven;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;


/**
 *
 * @author amith
 */
public class SQLiteFormExample extends JFrame {
    private JTextField nameField;
    private JTextField gradeField;
    private JButton submitButton;

    public SQLiteFormExample() {
        initializeDatabase();
        // Create form elements
        nameField = new JTextField(20);
        gradeField = new JTextField(20);
        submitButton = new JButton("Submit");

        // Layout
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Add components to frame
        this.add(new JLabel("Name:"));
        this.add(nameField);
        this.add(new JLabel("Grade:"));
        this.add(gradeField);
        this.add(submitButton);

        // Action listener for the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitFormData();
            }
        });

        // Frame settings
        this.setSize(300, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Student Form");
        this.pack(); // Adjusts frame to fit all components
        this.setVisible(true);
    }
    
    private void initializeDatabase() {
        String url = "jdbc:sqlite:student.db"; // Update the path
        
        // SQL statement for creating a new table
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS students (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "name TEXT NOT NULL," +
                                "grade INTEGER)";
        
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // Create a new table if not exists
            stmt.execute(sqlCreateTable);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error initializing database: " + e.getMessage());
        }
    }

    private void submitFormData() {
        String name = nameField.getText();
        int grade = Integer.parseInt(gradeField.getText()); // Basic validation

        String url = "jdbc:sqlite:student.db"; // Update the path

        // SQL statement for inserting data
        String sqlInsert = "INSERT INTO students (name, grade) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {

            // Set parameters
            pstmt.setString(1, name);
            pstmt.setInt(2, grade);

            // Execute insert operation
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data submitted successfully.");

            // Clear form fields after submission
            nameField.setText("");
            gradeField.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }


}
