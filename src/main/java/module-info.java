module com.example.noir {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jbcrypt;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;
    requires org.mongodb.driver.sync.client;
    requires javafx.web;
    requires com.google.gson;
    requires jdk.sctp;


    opens com.example.noir to javafx.fxml;
    exports com.example.noir;
    exports com.example.backend;
    opens com.example.backend to javafx.fxml;
    exports com.example.backend.server;
    opens com.example.backend.server to javafx.fxml;
}