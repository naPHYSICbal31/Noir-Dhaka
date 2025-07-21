package com.example.backend;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        dbFetch db = new dbFetch();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        // Create multiple users and stress test registration/login
        for (int i = 0; i < 50; i++) {
            String username = "user" + i;
            User user = new User(username, 1000 + i, username + "@test.com", "Address " + i, i % 2 == 0, new HashMap<>(), new HashMap<>());
            user.setPass("pass" + i);

            try {
                db.register(user);
                System.out.println("[REGISTERED] " + username);
            } catch (Exception e) {
                System.out.println("[REGISTER FAIL] " + username + ": " + e.getMessage());
            }

            try {
                db.validateLogin(username, "pass" + i);
                System.out.println("[LOGIN] " + username);
            } catch (Exception e) {
                System.out.println("[LOGIN FAIL] " + username + ": " + e.getMessage());
            }
        }

        // Add multiple coffees
        for (int i = 0; i < 20; i++) {
            List<Boolean> tags = Arrays.asList(i % 2 == 0, i % 3 == 0, i % 5 == 0);
            Coffee coffee = new Coffee(200 + i, "Coffee" + i, "http://image.url/" + i, "Description of coffee" + i,
                    "250g", 0.25 + i * 0.01, 400 + i * 10, i % 10, i % 10, i % 10, i % 10,
                    i % 2 == 0, i % 3 == 0, i % 5 == 0, 100 - i, i * 5, false, false,
                    new ArrayList<>(), tags);
            db.addCoffee(coffee);
            System.out.println("[ADDED COFFEE] ID: " + (200 + i));
        }

        // Add reviews from each user
        for (int i = 0; i < 50; i++) {
            String username = "user" + i;
            try {
                db.validateLogin(username, "pass" + i);
                int coffeeId = 200 + (i % 20);
                String time = LocalDateTime.now().format(formatter);
                Review review = new Review(3000 + i, (i % 5) + 0.5, coffeeId, "Review by " + username, time, i % 2 == 0, "Title " + i);
                db.addReview(review);
                System.out.println("[ADDED REVIEW] by " + username);
            } catch (Exception e) {
                System.out.println("[REVIEW FAIL] for user" + i + ": " + e.getMessage());
            }
        }

        // Add and fetch carts
        for (int i = 0; i < 50; i++) {
            String username = "user" + i;
            try {
                db.validateLogin(username, "pass" + i);
                HashMap<Integer, Integer> cartItems = new HashMap<>();
                cartItems.put(200 + (i % 20), (i % 3) + 1);
                db.addCart(new Cart(cartItems));

                Cart fetched = db.getCart();
                if (fetched != null) {
                    System.out.println("[FETCHED CART] " + username + ": " + fetched.getBuyHistory());
                }
                db.buyCart(fetched);
            } catch (Exception e) {
                System.out.println("[CART FAIL] for " + username + ": " + e.getMessage());
            }
        }

        // Test edge cases: nonexistent coffee or review
        System.out.println("[EDGE TEST] Nonexistent coffee fetch:");
        Coffee ghost = db.getCoffeeById(9999);
        System.out.println(ghost == null ? "Not found." : ghost.getName());

        System.out.println("[EDGE TEST] Get reviews for nonexistent coffee:");
        Coffee dummy = new Coffee(9999, "Dummy", "", "", "", 0.0, 0.0, 0, 0, 0, 0, false, false, false, 0, 0, false, false, new ArrayList<>(), Arrays.asList(false, false, false));
        System.out.println("Critical: " + db.getCriticalReviews(dummy).size());
        System.out.println("Moderate: " + db.getModerateReviews(dummy).size());
        System.out.println("Positive: " + db.getPositiveReviews(dummy).size());

        // Test user update with extreme buyHistory
        try {
            db.validateLogin("user0", "pass0");
            User u = db.getUserinfo();
            for (int i = 0; i < 100; i++) {
                u.getBuyHistory().put(200 + (i % 20), i);
            }
            db.updateUser(u);
            System.out.println("[UPDATED USER WITH LARGE HISTORY] user0");
        } catch (Exception e) {
            System.out.println("[USER UPDATE FAIL] user0: " + e.getMessage());
        }

        db.logOut();
    }
}

