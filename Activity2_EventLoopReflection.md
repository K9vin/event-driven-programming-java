# Activity 2: "Build the Event Loop" — Physical Simulation & Reflection

## 1. Simulation Observations & Questions (Round 3 Analysis)

### Question 1: What happened to the other events while you were "stuck" on the slow handler?
While holding the slow card (simulating a 30-second blocking operation on the single dispatch loop), all other event cards accumulated unprocessed in the Event Queue. Because the Event Loop processes incoming events sequentially in a single-threaded FIFO (First-In, First-Out) pipeline, no subsequent events—such as mouse moves, button clicks, repaints, or keyboard inputs—could be examined or handled until the slow handler finally returned control.

### Question 2: What would a user see if this happened in a real application?
The graphical user interface (GUI) would become completely frozen and unresponsive. The window would stop repainting (often turning blank, white, or gray), clicks on buttons or text fields would not register, scrolling would freeze, the operating system cursor might turn into a spinning beachball or hourglass, and the OS title bar would display messages such as **"(Not Responding)"** (Windows) or **"Application Not Responding (ANR)"** (Android/macOS).

### Question 3: How could this problem be fixed?
This issue is resolved by offloading long-running, blocking, or computationally intensive tasks (e.g., file I/O, database queries, network requests, or cryptographic computations) onto a **worker thread or background background pool** (such as Java's `SwingWorker` or `Thread`/`ExecutorService`). The Event Dispatch Thread (EDT) or event loop is then freed immediately to continue pumping UI events. Once the worker thread finishes, it posts a callback event back into the event queue (via `SwingUtilities.invokeLater()`) to update the UI safely without ever blocking the loop.

---

## 2. Reflection Paragraph (Event Loop, Queue, and Non-Blocking Architecture)

The **event queue** serves as a sequential, thread-safe buffer that stores incoming user inputs, sensor signals, OS interrupts, and system timers in First-In, First-Out (FIFO) order. The **event loop** is the continuous orchestration engine that actively polls this queue, dequeues each event one by one, and dispatches it directly to registered listener callbacks. Because typical UI frameworks (such as Java Swing, web browsers, and mobile runtimes) rely on a single dedicated event dispatch thread to execute both event handlers and interface rendering, the event loop must **never be blocked**. When a callback executes heavy computation or blocking I/O synchronously on the event thread, it halts the loop completely, preventing subsequent user actions from processing and rendering frames from drawing. Keeping event handlers lightweight and offloading extensive tasks to background worker threads ensures silky 60+ FPS UI responsiveness, robust thread safety, and an optimal interactive user experience.
