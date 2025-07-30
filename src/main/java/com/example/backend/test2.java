package com.example.backend;

import com.example.backend.Client;

import java.util.*;

public class test2 {
    public static void main(String[] args) {
        Client db = new Client();

        // Register one user
        User user = new User("test", 1001, "soloUser@test.com", "123 Bean Street", true,
                new HashMap<>(), new HashMap<>(), new HashMap<>());
        User user2 = new User("test2", 1001, "soloUser@test.com", "123 Bean Street", true,
                new HashMap<>(), new HashMap<>(), new HashMap<>());
        user.setPass("test");
        user2.setPass("test2");
        try {
            db.register(user);

            System.out.println("[REGISTERED] soloUser");
        } catch (Exception e) {
            System.out.println("[REGISTER FAIL] soloUser: " + e.getMessage());
        }
        try {
            db.register(user2);

            System.out.println("[REGISTERED] soloUser2");
        } catch (Exception e) {
            System.out.println("[REGISTER FAIL] soloUser: " + e.getMessage());
        }


        String[] coffeeNames = {
                "Seasonal 5 Bag Promotion Pack",
                "Colombian Bucaramanga",
                "Colombian Supremo",
                "Sumatra Mandheling",
                "Ethiopian Yirgacheffe"
        };

        String[] descriptions = {
                "We have hand-picked 5 very different tasting coffees for you to try, changing on a fortnightly basis!",
                "Bright and vibrant with wine-like acidity, Kenya AA offers bold citrus notes and a juicy berry finish. Sourced from the high-altitude slopes of Mount Kenya.",
                "Balanced and mellow, Colombian Supremo features a medium body with nutty undertones and a subtle caramel sweetness. A classic crowd-pleaser from Colombia’s finest beans.",
                "Earthy and intense with a syrupy body, Sumatra Mandheling is prized for its herbal complexity and low acidity. Hand-picked from the volcanic soils of Northern Sumatra.",
                "Floral and tea-like with notes of bergamot and lemon zest, Ethiopian Yirgacheffe delivers a bright cup with delicate sweetness. Grown at high elevations in Ethiopia’s legendary Yirgacheffe region."
        };


        for (Coffee i : db.getAllCoffees()) {
            System.out.println(db.getAverageRatingForCoffee(i.getId()));
        }
    }


}
