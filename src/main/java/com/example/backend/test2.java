package com.example.backend;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class test2 {
    public static void main(String[] args) {
        dbFetch db = new dbFetch();

        // Register one user
        User user = new User("test", 1001, "soloUser@test.com", "123 Bean Street", true,
                new HashMap<>(), new HashMap<>(), new HashMap<>());
        user.setPass("test");
        try {
            db.register(user);
            System.out.println("[REGISTERED] soloUser");
        } catch (Exception e) {
            System.out.println("[REGISTER FAIL] soloUser: " + e.getMessage());
        }


        String[] coffeeNames = {
                "Guatemala Antigua",
                "Kenya AA",
                "Colombian Supremo",
                "Sumatra Mandheling",
                "Ethiopian Yirgacheffe"
        };

        String[] descriptions = {
                "A smooth and full-bodied coffee with hints of cocoa and spice, grown in the highlands of Antigua, Guatemala. Known for its rich aroma and clean finish.",

                "Bright and vibrant with wine-like acidity, Kenya AA offers bold citrus notes and a juicy berry finish. Sourced from the high-altitude slopes of Mount Kenya.",

                "Balanced and mellow, Colombian Supremo features a medium body with nutty undertones and a subtle caramel sweetness. A classic crowd-pleaser from Colombia’s finest beans.",

                "Earthy and intense with a syrupy body, Sumatra Mandheling is prized for its herbal complexity and low acidity. Hand-picked from the volcanic soils of Northern Sumatra.",

                "Floral and tea-like with notes of bergamot and lemon zest, Ethiopian Yirgacheffe delivers a bright cup with delicate sweetness. Grown at high elevations in Ethiopia’s legendary Yirgacheffe region."
        };


        for (int i = 0; i < 5; i++) {
            List<Boolean> tags = Arrays.asList(i % 2 == 0, i % 3 == 0, i % 5 == 0);
            Coffee coffee = new Coffee(
                    200 + i,
                    coffeeNames[i],
                    "http://image.url/" + i,
                    descriptions[i],
                    "250g",
                    0.25 + i * 0.01,
                    400 + i * 10,
                    i % 10, i % 10, i % 10, i % 10,
                    i % 2 == 0, i % 3 == 0, i % 5 == 0,
                    100 - i,
                    i * 5,
                    false, false,
                    new ArrayList<>(),
                    tags
            );

            db.addCoffee(coffee);
            System.out.println("[ADDED COFFEE] ID: " + (500 + i) + ", Name: " + coffeeNames[i]);
        }
    }
}
