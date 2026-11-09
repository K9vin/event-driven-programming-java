# Bug Fix Documentation

This document records the symptoms, root causes, and solutions for the four event-driven programming bugs in the `BugFixChallenge` module.

---

## Bug 1: Blocking the EDT

- **File:** `Bug1_BlockingEDT.java`
- **Symptom:** The user interface freezes completely when the "Process" button is clicked. The window cannot be dragged, resized, or minimized, buttons cannot be clicked, and the operating system marks the window as "(Not Responding)" for 5 seconds.
- **Cause:** A long-running synchronous blocking operation (`Thread.sleep(5000)`) was executed directly inside the `ActionListener` on the Event Dispatch Thread (EDT). Because the EDT handles both event dispatching and visual rendering sequentially, blocking it halts the entire GUI loop.
- **Fix:** Spawn a worker background thread (`new Thread(() -> { ... }).start()`) to perform the long-running operation off the EDT. When complete, marshal the UI update (`statusLabel.setText("Done!")`) safely back onto the EDT using `SwingUtilities.invokeLater()`. Additionally, disable the button while processing to prevent duplicate trigger events.
- **Code Change:**
  ```java
  // Replaced direct Thread.sleep on EDT with background execution:
  processButton.addActionListener(e -> {
      statusLabel.setText("Processing...");
      processButton.setEnabled(false);

      new Thread(() -> {
          try {
              Thread.sleep(5000);
          } catch (InterruptedException ex) {
              Thread.currentThread().interrupt();
          }
          SwingUtilities.invokeLater(() -> {
              statusLabel.setText("Done!");
              processButton.setEnabled(true);
          });
      }).start();
  });
  ```

---

## Bug 2: Missing Listener

- **File:** `Bug2_MissingListener.java`
- **Symptom:** Clicking the "Increment" button produces zero response. The counter value on screen remains stuck at `Count: 0`.
- **Cause:** The button widget (`JButton`) was instantiated and added to the visual container, but no `ActionListener` was registered via `addActionListener()`. Without a registered listener, the event loop discards click events for that component.
- **Fix:** Register an `ActionListener` lambda on `incrementButton` that increments the integer counter `count` and updates `countLabel` with the new value.
- **Code Change:**
  ```java
  // Added the missing ActionListener:
  incrementButton.addActionListener(e -> {
      count++;
      countLabel.setText("Count: " + count);
  });
  ```

---

## Bug 3: Wrong Thread

- **File:** `Bug3_WrongThread.java`
- **Symptom:** UI components update intermittently, glitch visually, or risk race conditions, state corruption, and random exceptions when running over time. Clicking "Start Clock" multiple times also starts multiple uncontrolled background threads.
- **Cause:** Swing components are **not thread-safe**. Calling methods like `timeLabel.setText(...)` directly from an arbitrary background worker thread violates the Swing single-thread rule and can corrupt component internal state during concurrent repaints.
- **Fix:** Wrap all component modifications in `SwingUtilities.invokeLater(() -> { ... })` so updates execute safely on the EDT. In addition, guard against spawning multiple background threads by checking `running` state and disabling the "Start Clock" button once running.
- **Code Change:**
  ```java
  // Dispatched GUI mutation to EDT via SwingUtilities.invokeLater():
  new Thread(() -> {
      while (running) {
          String currentTimeStr = timeFormat.format(new Date());
          SwingUtilities.invokeLater(() -> {
              timeLabel.setText("Time: " + currentTimeStr);
          });
          try {
              Thread.sleep(1000);
          } catch (InterruptedException ex) {
              Thread.currentThread().interrupt();
              break;
          }
      }
  }).start();
  ```

---

## Bug 4: Memory Leak

- **File:** `Bug4_MemoryLeak.java`
- **Symptom:** Memory consumption steadily climbs as buttons are added and removed. Over long sessions or with many elements, this leads to an `OutOfMemoryError`.
- **Cause:** When "Remove All" was clicked, `buttonPanel.removeAll()` detached the button components from the graphical layout container, but the `createdButtons` list (`ArrayList<JButton>`) still retained strong object references to every button ever instantiated. The Java Garbage Collector (GC) cannot reclaim objects that remain reachable through active collections.
- **Fix:** Invoke `createdButtons.clear()` inside the "Remove All" event handler to release all strong references, allowing the Garbage Collector to reclaim heap memory immediately.
- **Code Change:**
  ```java
  removeButton.addActionListener(e -> {
      buttonPanel.removeAll();
      buttonPanel.revalidate();
      buttonPanel.repaint();
      // Added clearing of internal collection to prevent memory leak:
      createdButtons.clear();
  });
  ```
