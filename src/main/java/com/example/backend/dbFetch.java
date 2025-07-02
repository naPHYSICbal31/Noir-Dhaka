package com.example.backend;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import javax.print.Doc;
import java.util.*;
import java.time.Instant;

public class dbFetch {
    //public static boolean isOnlyOne = false;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    public dbFetch() {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("noirdb");
        }
    }





    /*
     * Login and session management
     * Methods throw exceptions with messages instead of returning false
     */

    static String currentToken;

    public void register(User user){
        this.collection = database.getCollection("users");
        if(this.collection.find(new Document("username", user.getUsername())).first() != null){
            throw new RuntimeException("Username is already in use");
        }

        Document entry = new Document("id", user.getUserid()).append("username", user.getUsername()).append("email", user.getEmail()).append("address", user.getAddress()).append("isAds", user.isAds());
        this.collection.insertOne(entry);

        this.collection = database.getCollection("creds");
        entry = new Document("username", user.getUsername()).append("password", user.getPasshash());
        this.collection.insertOne(entry);
        setAndGenToken(user.getUsername());

    }

    public void validateLogin(String username, String password){
        this.collection = database.getCollection("creds");
        String pass = User.hashpass(password);

        Document match = collection.find(new Document("user",username)).first();

        if(match != null){
            String compPass = match.getString("password");
            if(compPass.equals(pass)){
                terminateSession(username);
                setAndGenToken(username);
            }
        }
        throw new RuntimeException("Invalid username or password");
    }

    public void logOut(){
        terminateSession(currentToken);
        currentToken = null;
    }

    private void terminateSession(String token){
        MongoCollection<Document> d = database.getCollection("sessions");
        d.findOneAndDelete(new Document("token",token));
    }

    private void setAndGenToken(String username){
        String sessionToken = UUID.randomUUID().toString();

        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(86400);

        MongoCollection<Document> d = database.getCollection("sessions");
        Document entry = new Document("username", username)
                .append("sessionToken", sessionToken);
        d.insertOne(entry);

        currentToken  = sessionToken;
    }


    /*
    *  GET
    * */

    private int getUserIdByToken(){
        MongoCollection<Document> d  = database.getCollection("sessions");
        return d.find(new Document("token",currentToken)).first().getInteger("id");
    }

    public User getUserinfo(){
        this.collection = database.getCollection("users");
        if(currentToken == null){
            return null;
        }
        int userid = getUserIdByToken();
        List<Document> matches = this.collection.find(new Document("userid",userid)).into(new ArrayList<>());
        if(matches != null){
            return parseUsers(matches).getFirst();
        }
        return null;
    }

    public List<Coffee> getAllCoffees(){
        List<Coffee> coffees = new ArrayList<Coffee>();
        MongoCollection<Document> d = database.getCollection("coffees");

        List<Document> allCoffees = d.find().into(new ArrayList<>());


        return coffees;
    }

    public Coffee getCoffeeById(int id){
        MongoCollection<Document> d = database.getCollection("coffees");

        List<Document> m = new ArrayList<>();
        m = m = d.find(new Document("id" , id)).into(new ArrayList<>());

        if(m != null){
            parseCoffees(m);
        }
        return null;
    }

    public List<Coffee> getCoffeesByKeyAndValue(String key, int value){
        MongoCollection<Document> d = database.getCollection("coffees");
        List<Document> m = new ArrayList<>();
        List<Coffee> c = new ArrayList<>();

        m = d.find(new Document(key , value)).into(new ArrayList<>());

        if(m != null){
            parseCoffees(m);
        }

        return c;
    }

    public List<Coffee> getCoffeesByKeyAndValue(String key, String value){
        MongoCollection<Document> d = database.getCollection("coffees");
        List<Document> m = new ArrayList<>();
        List<Coffee> c = new ArrayList<>();

        m = d.find(new Document(key , value)).into(new ArrayList<>());

        if(m != null){
            parseCoffees(m);
        }

        return c;
    }

    public List<Coffee> getCoffeesByKeyAndValue(String key, boolean value){
        MongoCollection<Document> d = database.getCollection("coffees");
        List<Document> m = new ArrayList<>();
        List<Coffee> c = new ArrayList<>();

        m = d.find(new Document(key , value)).into(new ArrayList<>());

        if(m != null){
            parseCoffees(m);
        }

        return c;
    }

//    private List<Document> toList(Iterable<Document> iterable) {
//        List<Document> list = new ArrayList<>();
//        for (Document doc : iterable) {
//            list.add(doc);
//        }
//        return list;
//    }

    // ⭐ All reviews
    public List<Review> getAllReviews() {
        MongoCollection<Document> d = database.getCollection("reviews");
        List<Document> docs = d.find().into(new ArrayList<>());
        return parseReviews(docs);
    }


    public List<Review> getCriticalReviews() {
        MongoCollection<Document> d = database.getCollection("reviews");
        List<Document> docs = d.find(Filters.lte("stars", 2.0)).into(new ArrayList<>());
        return parseReviews(docs);
    }


    public List<Review> getModerateReviews() {
        MongoCollection<Document> d = database.getCollection("reviews");
        List<Document> docs = d.find(
                Filters.and(
                        Filters.gt("stars", 2.0),
                        Filters.lt("stars", 4.0)
                )).into(new ArrayList<>());
        return parseReviews(docs);
    }


    public List<Review> getPositiveReviews() {
        MongoCollection<Document> d = database.getCollection("reviews");
        List<Document> docs = d.find(Filters.gte("stars", 4.0)).into(new ArrayList<>());
        return parseReviews(docs);
    }



    private List<Review> parseReviews(List<Document> d){

        List<Review> reviews = new ArrayList<>(d.size());

        for (Document doc : d) {
            reviews.add(new Review(
                    doc.getInteger("userid"),
                    doc.getDouble("stars"),
                    doc.getInteger("coffeeid"),
                    doc.getString("description"),
                    doc.getString("timestamp"),
                    doc.getBoolean("isVerified"),
                    doc.getString("title")
            ));
        }
        return reviews;
    }

    private List<Coffee> parseCoffees(List<Document> d){
        List<Coffee> coffees = new ArrayList<>(d.size());
        for(Document m : d){
            List<Document> rev = m.getList("reviews", Document.class);
            List<Review> revs = parseReviews(rev);
            List<Boolean> tg = m.getList("tags", Boolean.class);
            coffees.add(new Coffee(m.getInteger("id"), m.getString("name"),m.getString("imageurl"),m.getString("description"),m.getString("packetSize"),m.getDouble("weight"),m.getDouble("packetSize"),m.getInteger("strength"),m.getInteger("flavour"),m.getInteger("acidity"),m.getInteger("aroma"),m.getBoolean("isRare"),m.getBoolean("isSmallBatch"),m.getBoolean("isFarmToCup"),m.getInteger("currentStock"),m.getInteger("numberOfSales"),m.getBoolean("isSoldOut"),m.getBoolean("isNearSoldOut"),revs,tg));
        }

        return coffees;
    }


    private List<User> parseUsers(List<Document> d) {
        List<User> users = new ArrayList<>(d.size());

        for (Document m : d) {
            String username = m.getString("username");
            int userid = m.getInteger("userid");
            String email = m.getString("email");

            String address = m.getString("address");
            boolean isAds = m.getBoolean("isAds");

            // buyHistory is assumed to be stored in a serializable way
            // You might need to parse it from Document list form
            List<Document> historyList = m.getList("buyHistory", Document.class);
            HashMap<Vector<Coffee>, Integer> buyHistory = new HashMap<>();

            if (historyList != null) {
                for (Document entry : historyList) {
                    List<Document> coffeeList = entry.getList("coffees", Document.class);
                    int count = entry.getInteger("count");

                    // Assuming each entry contains one vector and a count
                    Vector<Coffee> coffeeVector = new Vector<>(parseCoffees(coffeeList));
                    buyHistory.put(coffeeVector, count);
                }
            }

            users.add(new User(username, userid, email, address, isAds, buyHistory));
        }

        return users;
    }







    /*
    *  ADD
    * */

    public void addCoffee(Coffee coffee){
        this.collection = database.getCollection("coffees");
        this.collection.insertOne(new Document("id", coffee.getId()).append( "name", coffee.getName()).append("imageurl", coffee.getImageurl()).append("description", coffee.getDescription()).append("packetSize", coffee.getPacketSize()).append("weight", coffee.getWeight()).append("tag", Arrays.asList(coffee.getTag())).append("price", coffee.getPrice()).append("strength", coffee.getStrength()).append("flavour",  coffee.getFlavour()).append("acidity", coffee.getAcidity()).append("aroma", coffee.getAroma()).append("currentStock", coffee.getCurrentStock()).append("numberOfSales", coffee.getNumberOfSales()).append("isSoldOut", coffee.isSoldOut()).append("isNearSoldOUt", coffee.isNearSoldOut()).append("isRare", coffee.isRare()).append("isSmallBatch", coffee.isSmallBatch()).append("isFarmToCup", coffee.isFarmToCup()));
    }

    public void addCoffees(List<Coffee> coffees){
        this.collection = database.getCollection("coffees");
        for(Coffee cof : coffees){
            addCoffee(cof);
        }
    }

    public void addReview(Review review){
        this.collection = database.getCollection("reviews");
        Document doc = new Document("review", review.getReviewid())
                .append("stars", review.getStars())
                .append("coffeeid", review.getCoffeeid())
                .append("description", review.getDescription())
                .append("isVerified", review.isVerified())
                .append("title", review.getTitle())
                .append("timestamp", review.getTimestamp().toString()) // convert LocalDateTime to string
                .append("userid", review.getUserid());

        this.collection.insertOne(doc);
    }


    /*
    * REMOVE
    * */





    /*
    * UPDATE
    * */


}
