# Week 8: Properties and Bindings in JavaFX

Practical implementation guide and lab solutions for Week 8 of the **Event-Driven Programming (EDP)** curriculum in Java.

---

## Overview

This module covers reactive user interfaces and data synchronization in JavaFX using **Observable Properties** and **Bindings**. Instead of manually registering procedural listeners and mutating UI controls, JavaFX enables declarative data models where UI elements react automatically to data changes.

### Key Concepts

1. **Observable Properties (`Property<T>`):** Wrappers around primitive and object values (`StringProperty`, `IntegerProperty`, `DoubleProperty`, `BooleanProperty`) that support observation and binding.
2. **Listeners:**
   - **`InvalidationListener`:** Fires as soon as the property value becomes invalid (lazy evaluation).
   - **`ChangeListener`:** Provides `observable`, `oldValue`, and `newValue`. Does not fire if the same value is set again.
3. **Unidirectional Binding (`bind`):** One-way synchronization from a source property to a target property.
4. **Bidirectional Binding (`bindBidirectional`):** Two-way synchronization between two properties with automatic conversion (e.g. `NumberStringConverter`).
5. **Reactive UI Validation & Expressions:** Combining boolean expressions (`isEmpty()`, `.or()`, `.not()`, `greaterThan()`) to control component states (e.g., `disableProperty()`).

---

## Directory Structure

```text
week08-javafx-bindings/
├── pom.xml                                    # Maven configuration with JavaFX 21 controls & FXML
├── README.md                                  # Complete activity documentation and solutions
└── src/
    └── main/
        └── java/
            └── com/
                └── example/
                    ├── Person.java            # Activity 1: Observable Data Model
                    ├── ListenerDemo.java      # Activity 2: ChangeListener vs InvalidationListener
                    ├── UnidirectionalDemo.java# Activity 3: Unidirectional live string concatenation
                    ├── BidirectionalDemo.java # Activity 4: Bidirectional Slider <-> TextField sync
                    └── ReactiveFormApp.java   # Activity 5: Capstone Reactive Registration Form
```

---

## Activities & Code Walkthrough

### Activity 1: Observable Properties (The Data Model)
- **File:** `Person.java`
- **Class:** `com.example.Person`
- **Description:** Implements a JavaFX-compliant data model using `SimpleStringProperty` and `SimpleIntegerProperty`. Follows the JavaFX property design pattern:
  - Standard getters/setters: `getName()`, `setName(String)`, `getAge()`, `setAge(int)`
  - Property accessors: `nameProperty()`, `ageProperty()`

### Activity 2: ChangeListener vs. InvalidationListener
- **File:** `ListenerDemo.java`
- **Class:** `com.example.ListenerDemo`
- **Description:** Demonstrates the behavioral differences between `InvalidationListener` and `ChangeListener`.
- **Expected Console Output:**
  ```text
  --- Setting name to Alice ---
  1. [InvalidationListener] Name was changed!
  2. [ChangeListener] Name changed from '' to 'Alice'

  --- Setting name to Bob ---
  1. [InvalidationListener] Name was changed!
  2. [ChangeListener] Name changed from 'Alice' to 'Bob'

  --- Setting name to Bob (same value) ---
  ```
  *(Setting the same value "Bob" does not trigger either listener).*

### Activity 3: Unidirectional Binding
- **File:** `UnidirectionalDemo.java`
- **Class:** `com.example.UnidirectionalDemo`
- **Description:** Demonstrates one-way data binding. As text is entered into `nameField`, `greetingLabel` is automatically updated via:
  ```java
  greetingLabel.textProperty().bind(
      nameField.textProperty().concat(", welcome!")
  );
  ```

### Activity 4: Bidirectional Binding
- **File:** `BidirectionalDemo.java`
- **Class:** `com.example.BidirectionalDemo`
- **Description:** Demonstrates two-way data synchronization between a `Slider` (double value) and a `TextField` (string value) using `NumberStringConverter`:
  ```java
  ageField.textProperty().bindBidirectional(
      slider.valueProperty(),
      new javafx.util.converter.NumberStringConverter()
  );
  ```

### Activity 5: Capstone — Reactive Registration Form
- **File:** `ReactiveFormApp.java`
- **Class:** `com.example.ReactiveFormApp`
- **Description:** A complete reactive UI form featuring live character counters, real-time bio preview, and multi-field declarative validation that controls button enablement without manual event handler flags:
  - **Live character counter:** Bound to `bioField.textProperty().length().asString().concat(" / 150 characters")`
  - **Live preview:** Bound to `Bindings.concat("Hi, I am ", nameField.textProperty(), ". ", bioField.textProperty())`
  - **Submit Button Disable Binding:**
    ```java
    BooleanBinding emailContainsAt = Bindings.createBooleanBinding(
        () -> emailField.getText() != null && emailField.getText().contains("@"),
        emailField.textProperty()
    );

    submitBtn.disableProperty().bind(
        nameField.textProperty().isEmpty()
            .or(emailField.textProperty().isEmpty())
            .or(emailContainsAt.not())
            .or(bioField.textProperty().length().greaterThan(150))
    );
    ```

---

## Self-Check Questions & Answers

### Activity 1
- **Q1: Why do we use `StringProperty` instead of a standard `String` in a JavaFX model?**
  - **A:** Standard `String` objects are immutable and cannot notify listeners when a value is updated. `StringProperty` wraps the value with change notification support, enabling automatic UI updates and declarative bindings.
- **Q2: What is the purpose of the `nameProperty()` method?**
  - **A:** It returns the property wrapper instance itself rather than its raw value, allowing UI controls and other properties to bind to it or register listeners.

### Activity 2
- **Q1: What is the main difference between an `InvalidationListener` and a `ChangeListener`?**
  - **A:** An `InvalidationListener` only signals that the property has become invalid (its value may have changed), which is useful for lazy evaluation. A `ChangeListener` eagerly evaluates the new value and supplies both `oldValue` and `newValue`.
- **Q2: If you set a property to the exact same value it already holds, do the listeners fire?**
  - **A:** No. JavaFX checks whether `Objects.equals(oldValue, newValue)` holds; if identical, no change event is dispatched.

### Activity 3
- **Q1: What does "unidirectional" mean in the context of bindings?**
  - **A:** Data flows exclusively from source to target. Modifications to the source update the target, but target changes do not update the source.
- **Q2: What happens if you try to call `greetingLabel.setText("New Text")` on a bound label?**
  - **A:** A `java.lang.RuntimeException: A bound value cannot be set` is thrown. The binding must be removed via `greetingLabel.textProperty().unbind()` before setting the value directly.

### Activity 4
- **Q1: What is the main difference between `bind()` and `bindBidirectional()`?**
  - **A:** `bind()` enforces a one-way master-to-slave relationship where the target becomes read-only. `bindBidirectional()` creates a two-way peer synchronization where changing either property updates the other.
- **Q2: Why did we need `NumberStringConverter` in this activity?**
  - **A:** `slider.valueProperty()` is a `DoubleProperty` whereas `ageField.textProperty()` is a `StringProperty`. The converter handles type coercion in both directions.

### Activity 5
- **Q1: Why is it better to bind the `disableProperty()` instead of using an `if` statement inside a `KeyListener`?**
  - **A:** Declarative bindings automatically track all dependencies and update synchronously whenever any dependent property changes. This eliminates boilerplate listener logic, race conditions, and synchronization bugs.
- **Q2: What does the `.not()` method do in the email validation binding?**
  - **A:** It inverts the boolean condition. If the email contains `"@"`, `emailContainsAt` is `true`, so `.not()` produces `false` (meaning "do not disable"). If `"@"` is missing, `.not()` produces `true` (disabling the button).

---

## How to Build and Run

### Prerequisites
- JDK 17+ (or OpenJDK 25 LTS)
- OpenJFX 21 modules

### Compile
```powershell
$jfxMods = (Get-ChildItem -Path "$HOME\.m2\repository\org\openjfx" -Filter "*-win.jar" -Recurse | Select-Object -ExpandProperty FullName) -join ";"
javac --module-path $jfxMods --add-modules javafx.controls,javafx.fxml -d target\classes (Get-ChildItem src\main\java\com\example\*.java | Select-Object -ExpandProperty FullName)
```

### Run Activities
- **Activity 2 (Listener Demo):**
  ```powershell
  java --module-path $jfxMods --add-modules javafx.controls,javafx.fxml -cp target\classes com.example.ListenerDemo
  ```
- **Activity 3 (Unidirectional Demo):**
  ```powershell
  java --module-path $jfxMods --add-modules javafx.controls,javafx.fxml -cp target\classes com.example.UnidirectionalDemo
  ```
- **Activity 4 (Bidirectional Demo):**
  ```powershell
  java --module-path $jfxMods --add-modules javafx.controls,javafx.fxml -cp target\classes com.example.BidirectionalDemo
  ```
- **Activity 5 (Reactive Registration Form Capstone):**
  ```powershell
  java --module-path $jfxMods --add-modules javafx.controls,javafx.fxml -cp target\classes com.example.ReactiveFormApp
  ```

---

## Learning Outcomes Checklist

- [x] **Outcome 1:** Use JavaFX properties to create observable data models (`Person.java`)
- [x] **Outcome 2:** Implement `ChangeListener`s and `InvalidationListener`s on properties (`ListenerDemo.java`)
- [x] **Outcome 3:** Create unidirectional and bidirectional bindings between UI and model (`UnidirectionalDemo.java`, `BidirectionalDemo.java`)
- [x] **Outcome 4:** Build a fully reactive UI that updates automatically with bindings (`ReactiveFormApp.java`)
