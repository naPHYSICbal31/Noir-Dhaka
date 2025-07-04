package com.example.noir;

import com.example.backend.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.*;

public class HelloApplication extends Application {

    @FXML private javafx.scene.text.Text title1;
    Review[] reviews = new Review[100];
    int reviewCount = 0;
    User[] users = new User[100];
    int userCount = 0;
    public void loadReviews() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("C://Users//nafis//Desktop//codes//Project//Noir//src//main//resources//files//reviews.txt"));
        String line;
        System.out.println("here");
        while((line = reader.readLine()) != null)
        {
            try {
                System.out.println("works");
                String[] parts = line.split(",");
                String title = parts[0].replace("\"", "");
                String description = parts[1].replace("\"", "");
                String username = parts[2].replace("\"", "");
                int stars = Integer.parseInt(parts[3].trim());
                boolean isVerified = Boolean.parseBoolean(parts[4].trim());
                users[userCount++] = new User(username);
                reviews[reviewCount++] = new Review(title,description,users[userCount-1],stars,isVerified);
                System.out.println(reviews[0].getTitle());
                displayReview(reviews[reviewCount-1]);
                // Store or process review object as needed
            } catch (Exception e) {
                System.err.println("Error parsing review line: " + line);
                e.printStackTrace();
            }
        }
    }
    public void displayReview(Review review)
    {
        if(review != null)
        {
            title1.setText(review.getTitle());
        }
    }
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1440, 810);
            stage.setTitle("Noir Dhaka");
            stage.setResizable(false);
            stage.setScene(scene);
            scene.getRoot().requestFocus(); // Add this line to prevent automatic focus on TextField
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void main(String[] args) {
        launch();
    }
}