package com.example;

import javafx.application.Application;
import javafx.stage.Stage;

public class ListenerDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        Person person = new Person();

        // 1. InvalidationListener (Fires when the property becomes "invalid")
        person.nameProperty().addListener((observable) -> {
            System.out.println("1. [InvalidationListener] Name was changed!");
        });

        // 2. ChangeListener (Provides old and new values)
        person.nameProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("2. [ChangeListener] Name changed from '"
                + oldValue + "' to '" + newValue + "'");
        });

        // Trigger the changes
        System.out.println("--- Setting name to Alice ---");
        person.setName("Alice");

        System.out.println("\n--- Setting name to Bob ---");
        person.setName("Bob");

        System.out.println("\n--- Setting name to Bob (same value) ---");
        person.setName("Bob");  // ChangeListener will NOT fire here!
    }

    public static void main(String[] args) {
        launch(args);
    }
}
