package com.example.backend.server;
import java.util.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

import com.example.backend.*;
import com.google.gson.Gson;
import com.sun.nio.sctp.AbstractNotificationHandler;

public class Server {

    private static final int PORT = 12345;
    private static final ExecutorService pool = Executors.newFixedThreadPool(10);


    public static void main(String[] args) {
        System.out.println("Server running on port " + PORT);
        try (ServerSocket listener = new ServerSocket(PORT)) {
            while (true) {
                Socket client = listener.accept();
                System.out.println("Accepted connection from " + client.getInetAddress());
                pool.execute(new ClientHandler(client));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private final Gson gson = new Gson();
        private dbFetch database = new dbFetch();

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
                objectOut.flush();
                ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());




                String command;
                while ((command = (String)objectIn.readObject()) != null) {
                    switch (command.trim()) {
                        case "setToken":
                            System.out.println("setToken");
                            String token = (String)objectIn.readObject();
                            System.out.println("Got token" + token);
                            if (token.equals("null") || token.isEmpty()) {
                                objectOut.writeObject("Token is null");
                            }else {
                                if(database.validateToken(token)){
                                    database.setCurrentToken(token);
                                    objectOut.writeObject("Token accepted.");
                                }
                                objectOut.writeObject("Not accepted.");
                                socket.close();
                            }
                            break;
                        case "register":
                            User u = (User)objectIn.readObject();
                            String tok;
                            try{
                                tok = database.register(u);

                                objectOut.writeObject(tok);
                            }
                            catch (Exception e){
                                objectOut.writeObject("Username is already in use.");
                            }
                            break;
                        case "getUserInfo":
                            objectOut.writeObject(database.getUserinfo());
                            break;

                        case "login":
                            String username = (String)objectIn.readObject();
                            String password = (String)objectIn.readObject();
                            String hi;
                            try{ hi =database.validateLogin(username, password);}
                            catch(RuntimeException e){
                                objectOut.writeObject(false);
                                break;
                            }
                            objectOut.writeObject(true);
                            objectOut.writeObject(hi);
                            break;

                        case "getAllCoffees":
                            objectOut.writeObject(database.getAllCoffees());
                            break;

                        case "getCart":
                            objectOut.writeObject(database.getCart());
                            break;

                        case "addToCart":
                            int id =  (Integer) objectIn.readObject();
                            int cnt = (Integer) objectIn.readObject();
                            database.addToCart(id, cnt);
                            break;

                        case "removeFromCart":
                            int idc = (Integer) objectIn.readObject();
                            database.removeCart(idc);
                            break;

                        case "getAllReviews":
                            objectOut.writeObject(database.getAllReviews());
                            break;

                        case "getCriticalReviews": {
                            int ida = Integer.parseInt(in.readLine());
                            objectOut.writeObject(database.getCriticalReviews(database.getCoffeeById(ida)));
                            break;
                        }

                        case "getModerateReviews": {
                            int idb = Integer.parseInt(in.readLine());
                            objectOut.writeObject(database.getModerateReviews(database.getCoffeeById(idb)));
                            break;
                        }

                        case "getPositiveReviews": {
                            int idd = Integer.parseInt(in.readLine());
                            objectOut.writeObject(database.getPositiveReviews(database.getCoffeeById(idd)));
                            break;
                        }

                        case "getCoffeeById": {
                            int ide = (Integer)objectIn.readObject();
                            Coffee c = database.getCoffeeById(ide);
                            if(c != null){
                                objectOut.writeObject(c);
                                break;
                            }
                            objectOut.writeObject(new Coffee());
                            break;
                        }

                        case "getCoffeesByKeyValueInt": {
                            String key = (String)objectIn.readObject();
                            int value = (Integer)objectIn.readObject();
                            List<Coffee> c = database.getCoffeesByKeyAndValue(key, value);
                            if(c != null){
                                objectOut.writeObject(c);
                                break;
                            }
                            List<Coffee> e = new ArrayList<>();
                            e.add(new Coffee());
                            objectOut.writeObject(e);
                            break;
                        }

                        case "getCoffeesByKeyValueString": {
                            String key = (String)objectIn.readObject();
                            String value = (String)objectIn.readObject();
                            List<Coffee> c = database.getCoffeesByKeyAndValue(key, value);
                            if(c != null){
                                objectOut.writeObject(c);
                                break;
                            }
                            List<Coffee> e = new ArrayList<>();
                            e.add(new Coffee());
                            objectOut.writeObject(e);
                            break;
                        }

                        case "getCoffeesByKeyValueBoolean": {
                            String key = (String)objectIn.readObject();
                            Boolean value = (Boolean)objectIn.readObject();
                            List<Coffee> c = database.getCoffeesByKeyAndValue(key, value);
                            if(c != null){
                                objectOut.writeObject(c);
                                break;
                            }
                            List<Coffee> e = new ArrayList<>();
                            e.add(new Coffee());
                            objectOut.writeObject(e);
                            break;
                        }
                        case "addCoffee": {
                            Coffee coffee = (Coffee) objectIn.readObject();
                            database.addCoffee(coffee);
                            objectOut.writeObject("Coffee added.");
                            break;
                        }

                        case "addCoffees": {
                            List<Coffee> coffees = (List<Coffee>) objectIn.readObject();
                            database.addCoffees(coffees);
                            objectOut.writeObject("Coffees added.");
                            break;
                        }

                        case "addReview": {
                            Review review = (Review) objectIn.readObject();
                            database.addReview(review);
                            objectOut.writeObject("Review added.");
                            break;
                        }

                        case "addCart": {
                            Cart cart = (Cart) objectIn.readObject();
                            System.out.println(cart.getToken());
                            database.addCart(cart);
                            objectOut.writeObject("Cart added.");
                            break;
                        }

                        case "buyCart": {
                            Cart cart = (Cart) objectIn.readObject();
                            System.out.println(cart.getToken());
                            database.buyCart(cart);
                            objectOut.writeObject("Cart purchased.");
                            break;
                        }

                        case "removeCart": {
                            database.removeCart();
                            objectOut.writeObject("Cart removed.");
                            break;
                        }

                        case "removeUser": {
                            int userId = objectIn.readInt();
                            database.removeUser(userId);
                            objectOut.writeObject("User removed.");
                            break;
                        }

                        case "removeCoffee": {
                            int coffeeId = objectIn.readInt();
                            database.removeCoffee(coffeeId);
                            objectOut.writeObject("Coffee removed.");
                            break;
                        }

                        case "updateCart": {
                            Cart cart = (Cart) objectIn.readObject();
                            database.updateCart(cart);
                            objectOut.writeObject("Cart updated.");
                            break;
                        }

                        case "updateUser": {
                            User user = (User) objectIn.readObject();
                            database.updateUser(user);
                            objectOut.writeObject("User updated.");
                            break;
                        }

                        case "updateCoffee": {
                            Coffee coffee = (Coffee) objectIn.readObject();
                            database.updateCoffee(coffee);
                            objectOut.writeObject("Coffee updated.");
                            break;
                        }

                        

                        default:
                            objectOut.writeObject("Undefined");
                    }
                }
            } catch (IOException | NumberFormatException | ClassNotFoundException e) {
                //objectOut.writeObject("ERROR: " + e.getMessage());
                System.out.println(e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    if (socket != null) socket.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        }
    }
}
