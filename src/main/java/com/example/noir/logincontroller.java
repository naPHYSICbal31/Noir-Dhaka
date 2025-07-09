package com.example.noir;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class logincontroller {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Add your login logic here
        // For example:
        // if (authenticate(username, password)) {
        //     // Login successful
        // } else {
        //     // Login failed
        // }
    }
}