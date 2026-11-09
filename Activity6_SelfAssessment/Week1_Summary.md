# Week 1 Summary: Foundations of Event-Driven Programming

---

## 1. Executive Summary & Key Definitions

During Week 1, we explored the transition from linear procedural programming to the reactive, user-centric paradigm of **Event-Driven Programming (EDP)**.

- **Event:** A message, token, or object signaling that a state change or user action took place (e.g., `ActionEvent`, `KeyEvent`, `MouseEvent`).
- **Event Source:** The component or hardware emitter that notices the condition and publishes the event (e.g., `JButton`, optical sensor, system clock).
- **Event Listener:** An interface or registered subscriber that waits to be notified when specific events are dispatched (e.g., `ActionListener`).
- **Event Handler:** The concrete callback routine containing business logic executed upon event delivery (e.g., `actionPerformed()` or a lambda expression).
- **Event Queue:** A thread-safe FIFO buffer that collects events asynchronously from the OS and user interactions.
- **Event Loop / EDT:** A single-threaded dispatch loop that dequeues events sequentially and delegates them to their registered listeners.

---

## 2. Core Architecture Diagrams

### A. The End-to-End Event Pipeline
```text
┌─────────────────┐       ┌─────────────────┐       ┌─────────────────┐
│  USER / SENSORS │ ────> │  EVENT SOURCES  │ ────> │   EVENT QUEUE   │
│ (Clicks, Taps)  │       │ (Buttons, Keys) │       │  (FIFO Buffer)  │
└─────────────────┘       └─────────────────┘       └────────┬────────┘
                                                             │
                                                             ▼
┌─────────────────┐       ┌─────────────────┐       ┌─────────────────┐
│   GUI REPAINT   │ <──── │  EVENT HANDLER  │ <──── │   EVENT LOOP    │
│ (Visual State)  │       │   (Callbacks)   │       │ (Swing EDT Thread)
└─────────────────┘       └─────────────────┘       └─────────────────┘
```

### B. Procedural vs. Event-Driven Execution Model
```text
Procedural (Linear Flow):
[Start] ──> [Prompt Input 1] ──> [Block] ──> [Prompt Input 2] ──> [Compute] ──> [Exit]

Event-Driven (Inverted Reactive Flow):
[Start] ──> [Initialize UI & Listeners] ──> [Sleep in Event Loop]
                                                    │
                 ┌──────────────────────────────────┴──────────────────────────────────┐
                 ▼                                  ▼                                  ▼
          [Click Button A]                   [Press Key 'X']                    [Timer Tick]
                 │                                  │                                  │
          [Run Handler A]                    [Run Handler B]                    [Run Handler C]
                 │                                  │                                  │
                 └──────────────────────────────────┬──────────────────────────────────┘
                                                    │
                                                    ▼
                                          [Return to Event Loop]
```

---

## 3. Helpful Code Patterns & Best Practices

### Pattern 1: Starting Swing Applications Safely on the EDT
Swing components must always be instantiated and configured on the EDT:
```java
public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        new EventDrivenCalculator().setVisible(true);
    });
}
```

### Pattern 2: Keeping the EDT Non-Blocking with Background Threads
Never perform blocking I/O, delays, or heavy calculations inside an `ActionListener`:
```java
processButton.addActionListener(e -> {
    processButton.setEnabled(false);
    new Thread(() -> {
        // Heavy work runs off the EDT
        doHeavyComputation();

        // Safely update UI state on the EDT
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText("Operation Complete");
            processButton.setEnabled(true);
        });
    }).start();
});
```

### Pattern 3: Clean Lambda Event Listeners
Compact lambda expressions replace verbose anonymous inner classes:
```java
myButton.addActionListener(e -> {
    count++;
    countLabel.setText("Count: " + count);
});
```

---

## 4. Open Questions & Next Steps

1. **Scalability:** When an application has hundreds of components across multiple microservices or distributed systems, how does the event loop evolve? *(Answer: Distributed event streaming via Kafka, RabbitMQ, and reactive stream specifications).*
2. **Design Patterns:** How do we decouple the event source and listener completely so neither knows the other's concrete class? *(Answer: The **Observer Pattern**, which will be our focus in Week 2!)*
