package com.example.noir;

import com.example.backend.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
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
        while((line = reader.readLine()) != null)
        {
            try {

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
    
    private void loadGlobalFonts() {
        try {
            // Load RetrokiaCaps-Rough font (existing)
            Font customFont = Font.loadFont(getClass().getResourceAsStream("/fonts/RetrokiaCaps-Rough.otf"), 20);
            if (customFont == null) {
                System.err.println("Failed to load RetrokiaCaps-Rough font");
            } else {
                System.out.println("RetrokiaCaps-Rough font loaded successfully");
            }
            
            // Load Euclid Circular A Regular font (corrected filename)
            Font euclidRegular = Font.loadFont(getClass().getResourceAsStream("/fonts/RetrokiaCaps-Rough.otf"), 16);
            if (euclidRegular == null) {
                System.err.println("Failed to load Euclid Circular A Regular font");
            } else {
                System.out.println("Euclid Circular A Regular font loaded successfully");
            }

            // Load Euclid Circular A Bold font (corrected filename)
            Font euclidBold = Font.loadFont(getClass().getResourceAsStream("/fonts/RetrokiaCaps-Rough.otf"), 16);
            if (euclidBold == null) {
                System.err.println("Failed to load Euclid Circular A Bold font");
            } else {
                System.out.println("Euclid Circular A Bold font loaded successfully");
            }
            
        } catch (Exception e) {
            System.err.println("Error loading fonts: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void start(Stage stage) throws IOException {
        try {
            // Load fonts first
            loadGlobalFonts();
            (new dbFetch()).validateLogin("test", "test");
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1440, 810);
            scene.getStylesheets().add(getClass().getResource("/font.css").toExternalForm());
            stage.setTitle("Noir Dhaka");
            stage.setResizable(false);
            stage.setScene(scene);
            
            // Try this approach for loading the icon
            try {
                stage.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("images/noir_logo.png")));
                System.out.println("Icon loaded successfully");
            } catch (Exception iconException) {
                System.err.println("Failed to load application icon: " + iconException.getMessage());
                iconException.printStackTrace();
            }
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