# Activity 3: "Procedural vs. Event-Driven" — Side-by-Side Analysis

## 1. Procedural Calculator Analysis

### Answers to Questions:
1. **What is the exact order of steps in this program?**
   - Step 1: Initialize `Scanner` and display header.
   - Step 2: Prompt user for the first number (`num1`) and block execution until input is provided.
   - Step 3: Prompt user for the operator string (`+`, `-`, `*`, `/`) and block execution until provided.
   - Step 4: Prompt user for the second number (`num2`) and block execution until provided.
   - Step 5: Evaluate operator in a `switch` statement and perform calculation (or terminate on error).
   - Step 6: Print the computed result, close `Scanner`, and terminate `main()`.

2. **What happens if the user enters the wrong input at step 2?**
   - If the user enters a non-numeric token during `nextDouble()`, a runtime `InputMismatchException` crashes the program immediately.
   - If the user enters an unrecognized operator (e.g. `%` or `abc`), the `default` case branch executes, printing `"Invalid operator"` and terminating execution without a chance to retry.

3. **Can the user calculate again without restarting the program?**
   - No. The procedural control flow is strictly linear and terminates immediately after one calculation. To calculate another expression, the entire program must be relaunched.

4. **Who controls the flow of this program — the user or the program?**
   - The **program** controls the flow. The program dictates exactly what piece of information must be provided and at what exact time. The user is passive, only responding when prompted.

---

## 2. Event-Driven Calculator Analysis

### Answers to Questions:
1. **Can the user click buttons in any order?**
   - Yes. The user has full freedom to click any digit, clear (`C`), or operator button at any time. The application responds reactively to the user's choices.

2. **What happens if the user makes a mistake?**
   - The user can simply click the Clear (`C`) button to reset the current calculation state to `0`, or click a different operator before entering the second operand, recovering gracefully without restarting the application.

3. **Can the user calculate again without restarting?**
   - Yes. When the equals (`=`) button is pressed, the result is displayed and the state variable `startNewNumber` is set to `true`. The user can immediately start a new calculation or continue chaining operations onto the existing result indefinitely.

4. **Who controls the flow — the user or the program?**
   - The **user** controls the flow. The application remains idle in the event loop waiting for user interactions. The user decides when, how, and in what sequence events occur.

5. **How many event handlers are in this program?**
   - There are **16 buttons** on the 4x4 grid:
     - 10 number buttons (0–9), each with an `ActionListener`
     - 4 operator buttons (`+`, `-`, `*`, `/`), each with an `ActionListener`
     - 1 equals button (`=`) with an `ActionListener`
     - 1 clear button (`C`) with an `ActionListener`
   - Thus, there are **16 individual event handler callbacks** registered across the interactive UI components.

---

## 3. Comparison Table

| ASPECT | PROCEDURAL | EVENT-DRIVEN |
|---|---|---|
| **Who controls the flow?** | The **program** (strictly linear sequence determined by code execution). | The **user** (application waits idly and responds reactively to user actions). |
| **Can user make mistakes and recover easily?** | **No.** Invalid inputs cause runtime crashes or early program termination. | **Yes.** Users can click "Clear" (`C`) or change operands without crashing or quitting. |
| **Can user interact in any order?** | **No.** Input must be provided in the predetermined order: Num 1 → Operator → Num 2. | **Yes.** Buttons can be clicked in flexible, non-linear sequences (e.g., reset anytime). |
| **How many possible execution paths?** | **Very few.** Strictly 1 linear path with minor branching at the operator `switch`. | **Virtually infinite.** Combinations of user actions, clicks, sequences, and timings. |
| **Code organization** | Top-to-bottom sequential procedural steps within a single main method. | Modular event handlers (lambdas / callbacks) decoupled around UI components. |
| **User experience** | Rigid, unforgiving command-line prompts; must restart after each run. | Intuitive, interactive, continuous graphical interface with immediate visual feedback. |

---

## 4. Reflection Paragraph (Procedural vs. Event-Driven Paradigms)

Procedural programming models execution as an inflexible, step-by-step pipeline dictated entirely by the program's source code, making it well-suited for linear batch operations, data migrations, and simple command-line utilities. In contrast, event-driven programming shifts control to the end-user or external environment, organizing logic around reactive listeners that fire when discrete actions occur. For interactive software, event-driven architecture is overwhelmingly superior because users expect freedom to explore interfaces, change their minds, cancel operations, and receive instant visual feedback without breaking application state. Rather than blocking on synchronous console reads, an event-driven GUI remains responsive by sleeping in an event loop until user stimuli arrive. This inversion of control decouples interface design from business logic and provides the flexible, resilient foundation necessary for modern desktop, web, and mobile user experiences.
