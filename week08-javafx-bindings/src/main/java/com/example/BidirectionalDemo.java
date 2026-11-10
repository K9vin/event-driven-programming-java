package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BidirectionalDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label instruction = new Label("Drag the slider or type a number:");
        Slider slider = new Slider(0, 100, 50);
        TextField ageField = new TextField("50");

        VBox root = new VBox(20, instruction, slider, ageField);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // ---- BIDIRECTIONAL BINDING ----
        // We need a converter because the Slider is a Double and the TextField is a String.
        ageField.textProperty().bindBidirectional(
            slider.valueProperty(),
            new javafx.util.converter.NumberStringConverter()
        );

        Scene scene = new Scene(root, 400, 200);
        primaryStage.setTitle("Bidirectional Binding");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
