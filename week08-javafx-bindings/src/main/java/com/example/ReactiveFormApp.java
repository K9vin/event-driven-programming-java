package com.example;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ReactiveFormApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(15);

        // UI Components
        TextField nameField = new TextField();
        TextField emailField = new TextField();
        TextArea bioField = new TextArea();
        bioField.setPrefRowCount(3);
        bioField.setWrapText(true);

        Label charCountLabel = new Label("0 / 150 characters");
        Label previewLabel = new Label("Preview will appear here");
        previewLabel.setTextFill(Color.BLUE);

        Button submitBtn = new Button("Submit Registration");

        // Build Layout
        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(emailField, 1, 1);
        grid.add(new Label("Bio:"), 0, 2);
        grid.add(bioField, 1, 2);
        grid.add(charCountLabel, 1, 3);
        grid.add(previewLabel, 1, 4);
        grid.add(submitBtn, 1, 5);

        // ============================================================
        // THE MAGIC OF BINDINGS
        // ============================================================

        // 1. LIVE CHARACTER COUNT
        charCountLabel.textProperty().bind(
            bioField.textProperty().length().asString()
                .concat(" / 150 characters")
        );

        // 2. LIVE PREVIEW
        previewLabel.textProperty().bind(
            Bindings.concat("Hi, I am ", nameField.textProperty(), ". ", bioField.textProperty())
        );

        // 3. VALIDATION: DISABLE SUBMIT BUTTON
        javafx.beans.binding.BooleanBinding emailContainsAt = Bindings.createBooleanBinding(
            () -> emailField.getText() != null && emailField.getText().contains("@"),
            emailField.textProperty()
        );

        submitBtn.disableProperty().bind(
            nameField.textProperty().isEmpty()
                .or(emailField.textProperty().isEmpty())
                .or(emailContainsAt.not())
                .or(bioField.textProperty().length().greaterThan(150))
        );

        // 4. SUBMIT ACTION
        submitBtn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Registration Successful");
            alert.setContentText("Welcome, " + nameField.getText() + "!");
            alert.showAndWait();
        });

        Scene scene = new Scene(grid, 500, 450);
        primaryStage.setTitle("Reactive Registration Form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
