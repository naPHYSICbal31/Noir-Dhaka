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
        this.mongoClient = MongoClients.create("mongodb://localhost:27017");
        this.database = mongoClient.getDatabase("noirdb");
    }





    /*
     * Login and session management
     * Methods throw exceptions with messages instead of returning false
     */

    protected static String currentToken;

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

    public void validateLogin(String username, String password) throws RuntimeException{
        this.collection = database.getCollection("creds");
        String pass = User.hashpass(password);

        Document match = collection.find(new Document("username",username)).first();

        if(match != null){
            String compPass = match.getString("password");
            try{if(compPass.equals(pass)){
                terminateSession(username);
                setAndGenToken(username);

            }else{
                throw new RuntimeException("Invalid username or password");
            }}finally {

            }
        }

    }

    public void logOut(){
        terminateSession(currentToken);
        currentToken = null;
    }

    private void terminateSession(String username){
        MongoCollection<Document> d = database.getCollection("sessions");
        d.findOneAndDelete(new Document("username",username));
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
        String m =  d.find(new Document("sessionToken", currentToken)).first().getString("username");
        MongoCollection<Document> f =  database.getCollection("users");
        return f.find(new Document("username",m)).first().getInteger("id");
    }

    private String getUserNameById(int id){
        MongoCollection<Document> d  = database.getCollection("users");
        String m =  d.find(new Document("id", id)).first().getString("username");

        return m;
    }

    public User getUserinfo(){
        this.collection = database.getCollection("users");
        if(currentToken == null){
            return null;
        }
        int userid = getUserIdByToken();
        List<Document> matches = this.collection.find(new Document("id",userid)).into(new ArrayList<>());
        if(matches != null){
            return parseUsers(matches).getFirst();
        }
        return null;
    }

    public List<Coffee> getAllCoffees(){
        List<Coffee> coffees = new ArrayList<Coffee>();
        MongoCollection<Document> d = database.getCollection("coffees");

        List<Document> allCoffees = d.find().into(new ArrayList<>());

        for(Document x : allCoffees){
            coffees.add(parseCoffee(x));
        }
        return coffees;
    }

    public Coffee getCoffeeById(int id){
        MongoCollection<Document> d = database.getCollection("coffees");


        Document m = d.find(new Document("id" , id)).first();

        if(m != null){
            return parseCoffee(m);

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


    public List<Review> getAllReviews() {
        MongoCollection<Document> d = database.getCollection("reviews");
        List<Document> docs = d.find().into(new ArrayList<>());
        return parseReviews(docs);
    }


    public List<Review> getCriticalReviews(Coffee coffee) {
        MongoCollection<Document> d = database.getCollection("reviews");
        List<Document> docs = d.find(Filters.and(
                Filters.lte("stars", 2.0),
                Filters.eq("coffeeid", coffee.getId())
                )
        ).into(new ArrayList<>());
        return parseReviews(docs);
    }


    public List<Review> getModerateReviews(Coffee coffee) {
        MongoCollection<Document> d = database.getCollection("reviews");
        List<Document> docs = d.find(
                Filters.and(
                        Filters.gt("stars", 2.0),
                        Filters.lt("stars", 4.0),
                        Filters.eq("coffeeid", coffee.getId())
                )).into(new ArrayList<>());
        return parseReviews(docs);
    }


    public List<Review> getPositiveReviews(Coffee coffee) {
        MongoCollection<Document> d = database.getCollection("reviews");
        List<Document> docs = d.find(Filters.and(
                        Filters.gte("stars", 4.0),
                        Filters.eq("coffeeid", coffee.getId())
                )
        ).into(new ArrayList<>());
        return parseReviews(docs);
    }

    public Cart getCart(){
        int userid = getUserIdByToken();

        this.collection = database.getCollection("carts");

        Document match = this.collection.find(new Document("userid", userid)).first();

        if(match != null){
            return null;
        }
        HashMap<Integer,Integer> buyHistory = new HashMap<>();

        for(Document x : match.getList("coffees", Document.class)){
            buyHistory.put(x.getInteger("coffeeid"), x.getInteger("count"));
        }

        return new Cart(buyHistory);

    }
    /*
    *  PRIVATE PARSE FUNCTIONS
    * */

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
            boolean isRare = Boolean.TRUE.equals(m.getBoolean("isRare"));
            boolean isSmallBatch = Boolean.TRUE.equals(m.getBoolean("isSmallBatch"));
            boolean isFarmToCup = Boolean.TRUE.equals(m.getBoolean("isFarmToCup"));
            boolean isSoldOut = Boolean.TRUE.equals(m.getBoolean("isSoldOut"));
            boolean isNearSoldOut = Boolean.TRUE.equals(m.getBoolean("isNearSoldOut"));
            List<Document> rev = m.getList("reviews", Document.class);
            List<Review> revs = parseReviews(rev);
            List<Boolean> tg = m.getList("tag", Boolean.class);
            coffees.add(new Coffee(m.getInteger("id"), m.getString("name"),m.getString("imageurl"),m.getString("description"),m.getString("packetSize"),m.getDouble("weight"),m.getDouble("price"),m.getInteger("strength"),m.getInteger("flavour"),m.getInteger("acidity"),m.getInteger("aroma"),isRare,isSmallBatch,isFarmToCup,m.getInteger("currentStock"),m.getInteger("numberOfSales"),isSoldOut,isNearSoldOut,revs,tg));
        }

        return coffees;
    }

    private Coffee parseCoffee(Document d){
        List<Document> rev = d.getList("reviews", Document.class);
        List<Review> revs = parseReviews(rev);
        List<Boolean> tg = d.getList("tag", Boolean.class);
        boolean isRare = Boolean.TRUE.equals(d.getBoolean("isRare"));
        boolean isSmallBatch = Boolean.TRUE.equals(d.getBoolean("isSmallBatch"));
        boolean isFarmToCup = Boolean.TRUE.equals(d.getBoolean("isFarmToCup"));
        boolean isSoldOut = Boolean.TRUE.equals(d.getBoolean("isSoldOut"));
        boolean isNearSoldOut = Boolean.TRUE.equals(d.getBoolean("isNearSoldOut"));

        return new Coffee(
                d.getInteger("id"),
                d.getString("name"),
                d.getString("imageurl"),
                d.getString("description"),
                d.getString("packetSize"),
                d.getDouble("weight"),
                d.getDouble("price"),
                d.getInteger("strength"),
                d.getInteger("flavour"),
                d.getInteger("acidity"),
                d.getInteger("aroma"),
                isRare,
                isSmallBatch,
                isFarmToCup,
                d.getInteger("currentStock"),
                d.getInteger("numberOfSales"),
                isSoldOut,
                isNearSoldOut,
                revs,
                tg
        );
    }



    private List<User> parseUsers(List<Document> d) {
        List<User> users = new ArrayList<>(d.size());

        for (Document m : d) {
            String username = m.getString("username");
            int userid = m.getInteger("id");
            String email = m.getString("email");

            String address = m.getString("address");
            boolean isAds = m.getBoolean("isAds");

            // buyHistory is assumed to be stored in a serializable way
            // You might need to parse it from Document list form
            List<Document> historyList = m.getList("buyHistory", Document.class);
            HashMap<Integer, Integer> buyHistory = new HashMap<>();

            if (historyList != null) {
                for (Document entry : historyList) {
                    int coffee = entry.getInteger("coffeeid");
                    int count = entry.getInteger("count");
                    buyHistory.put(coffee , count);
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

        List<Document> reviews = new ArrayList<>();

        for(Review review : coffee.getReviews()){
            Document doc = new Document("review", review.getReviewid())
                    .append("stars", review.getStars())
                    .append("coffeeid", review.getCoffeeid())
                    .append("description", review.getDescription())
                    .append("isVerified", review.isVerified())
                    .append("title", review.getTitle())
                    .append("timestamp", review.getTimestamp().toString()) // convert LocalDateTime to string
                    .append("userid", review.getUserid());

            reviews.add(doc);
        }

        this.collection.insertOne(new Document("id", coffee.getId()).append( "name", coffee.getName()).append("imageurl", coffee.getImageurl()).append("description", coffee.getDescription()).append("packetSize", coffee.getPacketSize()).append("weight", coffee.getWeight()).append("tag", coffee.getTag()).append("price", coffee.getPrice()).append("strength", coffee.getStrength()).append("flavour",  coffee.getFlavour()).append("acidity", coffee.getAcidity()).append("aroma", coffee.getAroma()).append("currentStock", coffee.getCurrentStock()).append("numberOfSales", coffee.getNumberOfSales()).append("isSoldOut", coffee.isSoldOut()).append("isNearSoldOUt", coffee.isNearSoldOut()).append("isRare", coffee.isRare()).append("isSmallBatch", coffee.isSmallBatch()).append("isFarmToCup", coffee.isFarmToCup()).append("reviews", reviews));
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

    public void addCart(Cart c){
        this.collection = database.getCollection("carts");
        int userid = getUserIdByToken();
        List<Document> coffees = new ArrayList<>();

        for(Map.Entry<Integer, Integer> entry: c.getBuyHistory().entrySet()){
            int coffeeid = entry.getKey();
            int count = entry.getValue();

            coffees.add(new Document("coffeeid", coffeeid).append("count", count));
        }

        Document docx = new Document("userid", userid).append("timestamp", Instant.now().toString()).append("coffees", coffees);

        this.collection.insertOne(docx);
    }

    /*
    * REMOVE
    * */

    public void removeCart(){
        this.collection = database.getCollection("carts");
        this.collection.deleteOne(new Document("userid", getUserIdByToken()));
    }

    public void removeCart(int userid){
        this.collection = database.getCollection("carts");
        this.collection.deleteOne(new Document("userid", userid));
    }

    public void removeUser(int id){
        this.collection = database.getCollection("session");
        this.collection.deleteOne(new Document("username", getUserNameById(id)));

        removeCart(id);
        this.collection = database.getCollection("users");
        this.collection.deleteOne(new Document("id", id));

    }

    public void removeCoffee(int id){

    }



    /*
    * UPDATE
    * */

    public void updateUser(User user){
        int id = getUserIdByToken();

        this.collection = database.getCollection("users");

        List<Document> history= new ArrayList<>();

        for(Map.Entry<Integer, Integer> entry : user.getBuyHistory().entrySet()){
            Integer coffee = entry.getKey();
            int count = entry.getValue();

            //    new Document("id", coffee.getId()).append( "name", coffee.getName()).append("imageurl", coffee.getImageurl()).append("description", coffee.getDescription()).append("packetSize", coffee.getPacketSize()).append("weight", coffee.getWeight()).append("tag", Arrays.asList(coffee.getTag())).append("price", coffee.getPrice()).append("strength", coffee.getStrength()).append("flavour",  coffee.getFlavour()).append("acidity", coffee.getAcidity()).append("aroma", coffee.getAroma()).append("currentStock", coffee.getCurrentStock()).append("numberOfSales", coffee.getNumberOfSales()).append("isSoldOut", coffee.isSoldOut()).append("isNearSoldOUt", coffee.isNearSoldOut()).append("isRare", coffee.isRare()).append("isSmallBatch", coffee.isSmallBatch()).append("isFarmToCup", coffee.isFarmToCup()));
            //
            Document res = new Document("coffeeid", coffee).append("count", count);

            history.add(res);
        }


        Document newUser = new Document("id", user.getUserid()).append("username", user.getUsername()).append("email", user.getEmail()).append("address", user.getAddress()).append("isAds", user.isAds()).append("buyHistory", history);


        this.collection.findOneAndReplace(
                new Document("id", id), newUser
        );

        //this.collection.updateOne(, parseUsers())
    }

    public void updateCoffee(Coffee coffee){
        this.collection = database.getCollection("coffees");

        List<Document> reviews = new ArrayList<>();

        for(Review review : coffee.getReviews()){
            Document doc = new Document("review", review.getReviewid())
                    .append("stars", review.getStars())
                    .append("coffeeid", review.getCoffeeid())
                    .append("description", review.getDescription())
                    .append("isVerified", review.isVerified())
                    .append("title", review.getTitle())
                    .append("timestamp", review.getTimestamp().toString()) // convert LocalDateTime to string
                    .append("userid", review.getUserid());

            reviews.add(doc);
        }

        Document j = new Document("id", coffee.getId()).append( "name", coffee.getName()).append("imageurl", coffee.getImageurl()).append("description", coffee.getDescription()).append("packetSize", coffee.getPacketSize()).append("weight", coffee.getWeight()).append("tag", Arrays.asList(coffee.getTag())).append("price", coffee.getPrice()).append("strength", coffee.getStrength()).append("flavour",  coffee.getFlavour()).append("acidity", coffee.getAcidity()).append("aroma", coffee.getAroma()).append("currentStock", coffee.getCurrentStock()).append("numberOfSales", coffee.getNumberOfSales()).append("isSoldOut", coffee.isSoldOut()).append("isNearSoldOUt", coffee.isNearSoldOut()).append("isRare", coffee.isRare()).append("isSmallBatch", coffee.isSmallBatch()).append("isFarmToCup", coffee.isFarmToCup()).append("reviews", reviews);

        this.collection.findOneAndReplace(new Document("coffeeid", coffee.getId()), j);
    }

    // public void update review???



}
