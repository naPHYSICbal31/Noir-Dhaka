package com.example.backend;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class Client {
    private Socket socket;
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;
    public static String currentToken;

    public Client() {
        String host = "127.0.0.1";
        int port = 12345;
        try {
            socket = new Socket(host, port);
            objectOut = new ObjectOutputStream(socket.getOutputStream());
            objectOut.flush();
            objectIn = new ObjectInputStream(socket.getInputStream());
            objectOut.writeObject("setToken");

            if(currentToken == null){
                objectOut.writeObject("null");
                objectOut.flush();
            }else{
                objectOut.writeObject(currentToken);
                objectOut.flush();
            }
            String response = null;
            try {
                response = (String) objectIn.readObject();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Server: " + response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Read confirmation

    }

    private String sendCommand(String command){
        try {
            objectOut.writeObject(command);
            objectOut.flush();
            return (String) objectIn.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void register(User user){
        try {
            objectOut.writeObject("register");
            objectOut.writeObject(user);
            String tok =(String) objectIn.readObject();

            if(tok.equals("Username is already in use.")){
                throw new RuntimeException("Username is already in use");
            }else{
                currentToken = tok;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void validateLogin(String username, String password){
        try {
            objectOut.writeObject("login");
            objectOut.writeObject(username);
            objectOut.writeObject(password);
            Boolean hi = (Boolean) objectIn.readObject();
            if(!hi){
                throw new RuntimeException("Invalid Username or password");
            }else{
                currentToken = (String)objectIn.readObject();
                System.out.println(currentToken);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void logOut(){
        currentToken = null;
    }

    public User getUserinfo(){
        try {
            objectOut.writeObject("getUserInfo");
            User u = (User)objectIn.readObject();
            return u;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Coffee> getAllCoffees(){
        try {
            objectOut.writeObject("getAllCoffees");
            objectOut.flush();
            return (List<Coffee>) objectIn.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Coffee getCoffeeById(int id){
        try {
            objectOut.writeObject("getCoffeeById");
            objectOut.writeObject(id);
            objectOut.flush();
            Coffee c = (Coffee) objectIn.readObject();
            if(c.getId() == 0){
                return null;
            }
            return c;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Coffee> getCoffeesByKeyValueInt(String key, int value)  {
        try {
            objectOut.writeObject("getCoffeesByKeyValueInt");
            objectOut.writeObject(key);
            objectOut.writeObject(value);
            objectOut.flush();
            List<Coffee> c = (List<Coffee>) objectIn.readObject();
            if(c.isEmpty()){
                return null;
            }
            return c;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Coffee> getCoffeesByKeyValueString(String key, String value) {
        try {
            objectOut.writeObject("getCoffeesByKeyValueString");
            objectOut.writeObject(key);
            objectOut.writeObject(value);
            objectOut.flush();
            List<Coffee> c = (List<Coffee>) objectIn.readObject();
            if(c.isEmpty()){
                return null;
            }
            return c;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Coffee> getCoffeesByKeyValueBoolean(String key, boolean value) {
        try {
            objectOut.writeObject("getCoffeesByKeyValueBoolean");
            objectOut.writeObject(key);
            objectOut.writeObject(value);
            objectOut.flush();
            List<Coffee> c = (List<Coffee>) objectIn.readObject();
            if(c.isEmpty()){
                return null;
            }
            return c;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Cart getCart() {
        try {
            objectOut.writeObject("getCart");
            objectOut.flush();
            return (Cart) objectIn.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addToCart(int coffeeid, int count){
        try {
            objectOut.writeObject("addToCart");
            objectOut.writeObject(coffeeid);
            objectOut.writeObject(count);
            objectOut.flush();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeFromCart(int coffeeid){
        try {
            objectOut.writeObject("removeFromCart");
            objectOut.writeObject(coffeeid);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Review> getAllReviews() throws IOException, ClassNotFoundException {
        objectOut.writeObject("getAllReviews");
        objectOut.flush();
        return (List<Review>) objectIn.readObject();
    }

    public List<Review> getCriticalReviews(Coffee coffeeId) throws IOException, ClassNotFoundException {
        objectOut.writeObject("getCriticalReviews");
        objectOut.writeObject(coffeeId);
        objectOut.flush();
        return (List<Review>) objectIn.readObject();
    }

    public List<Review> getModerateReviews(Coffee coffeeId) throws IOException, ClassNotFoundException {
        objectOut.writeObject("getModerateReviews");
        objectOut.writeObject(coffeeId);
        objectOut.flush();
        return (List<Review>) objectIn.readObject();
    }

    public List<Review> getPositiveReviews(Coffee coffeeId){
        try {
            objectOut.writeObject("getPositiveReviews");
            objectOut.writeObject(coffeeId);
            objectOut.flush();
            try {
                return (List<Review>) objectIn.readObject();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    /*
    * ADD
    * */


    public String addCoffee(Coffee coffee){
        try {
            objectOut.writeObject("addCoffee");
            objectOut.writeObject(coffee);
            objectOut.flush();
            return (String) objectIn.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String addCoffees(List<Coffee> coffees) {
        try {
            objectOut.writeObject("addCoffees");
            objectOut.writeObject(coffees);
            objectOut.flush();
            return (String) objectIn.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public String addReview(Review review){
        try {
            objectOut.writeObject("addReview");
            objectOut.writeObject(review);
            objectOut.flush();
            return (String) objectIn.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String addCart(Cart cart){
        try {
            objectOut.writeObject("addCart");
            objectOut.writeObject(cart);
            objectOut.flush();
            return (String) objectIn.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    public String buyCart(Cart cart){
        try {
            objectOut.writeObject("buyCart");
            objectOut.writeObject(cart);
            objectOut.flush();
            return (String) objectIn.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String removeCart() throws IOException, ClassNotFoundException {
        return sendCommand("removeCart");
    }

    public String removeUser(int userId) {
        try {
            objectOut.writeObject("removeUser");
            objectOut.writeInt(userId);
            objectOut.flush();
            return (String) objectIn.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String removeCoffee(int coffeeId){
        try {
            objectOut.writeObject("removeCoffee");
            objectOut.writeInt(coffeeId);
            objectOut.flush();
            return (String) objectIn.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String updateCart(Cart cart) {
        try {
            objectOut.writeObject("updateCart");
            objectOut.writeObject(cart);
            objectOut.flush();
            return (String) objectIn.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String updateUser(User user) {
        try {
            objectOut.writeObject("updateUser");
            objectOut.writeObject(user);
            objectOut.flush();
            return (String) objectIn.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String updateCoffee(Coffee coffee){
        try {
            objectOut.writeObject("updateCoffee");
            objectOut.writeObject(coffee);
            objectOut.flush();
            return (String) objectIn.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean saveReview(Review review) {
        try {
            objectOut.writeObject("saveReview");
            objectOut.writeObject(review);
            objectOut.flush();
            return (Boolean) objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasUserReviewedCoffee(int userId, int coffeeId) {
        try {
            objectOut.writeObject("hasUserReviewedCoffee");
            objectOut.writeInt(userId);
            objectOut.writeInt(coffeeId);
            objectOut.flush();
            return (Boolean) objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateReview(int userId, int coffeeId, double newRating, String newDescription) {
        try {
            objectOut.writeObject("updateReview");
            objectOut.writeInt(userId);
            objectOut.writeInt(coffeeId);
            objectOut.writeDouble(newRating);
            objectOut.writeObject(newDescription);
            objectOut.flush();
            return (Boolean) objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Review getUserReviewForCoffee(int userId, int coffeeId) {
        try {
            objectOut.writeObject("getUserReviewForCoffee");
            objectOut.writeInt(userId);
            objectOut.writeInt(coffeeId);
            objectOut.flush();
            Review r = (Review) objectIn.readObject();

            if(r.getDescription() == null){
                return null;
            }else{
                return r;
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }





    public void close() throws IOException {
        objectOut.close();
        objectIn.close();
        socket.close();
    }

}

