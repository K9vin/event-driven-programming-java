# Activity 1: "Spot the Components" — Real-World EDP Scavenger Hunt

## Objective
Identify and define the fundamental components of an event-driven system:
- **Event Source:** The component or sensor that detects an external interaction or condition and initiates an event.
- **Event:** An object, message, or signal encapsulating information about state change or user interaction.
- **Event Listener:** A construct or software module registered to receive and monitor for specific event occurrences.
- **Event Handler / Action:** The specific method, subroutine, or logic that runs in response to an event.

---

## 1. EDP Components Analysis Table

| System | Event Source | Event | Listener | Handler / Action |
|---|---|---|---|---|
| **ATM Machine Operation** | Keypad button | Key press (`PIN entered`) | ATM Software Service | Validate entered PIN against bank database |
| **ATM Machine Operation** | Card reader slot | Card inserted | Card reader driver / daemon | Read chip/magnetic strip data and trigger session start |
| **ATM Machine Operation** | Cash dispenser | Cash bundle removed | Infrared optical sensor | Close dispensing shutter, record transaction completion, issue receipt |
| **Microwave Oven Use** | "Start" tactile button | Button click / contact closed | Microcontroller control loop | Engage magnetron, turn on internal light, start countdown timer |
| **Microwave Oven Use** | Door latch mechanism | Door opened mid-cycle | Safety interlock switch | Immediately cut power to the magnetron for user safety |
| **Traffic Light System** | Inductive vehicle loop in pavement | Metal vehicle mass detected | Intersection controller unit | Trigger state change sequence (switch cross street to yellow, then red) |
| **Traffic Light System** | Pedestrian crossing button | Push-button contact activated | Crosswalk controller | Schedule pedestrian "WALK" signal in next phase cycle and trigger audio chirp |
| **Video Game Gameplay** | Gamepad controller button | "A" button pressed (`BUTTON_DOWN`) | Input Manager / Game Loop | Execute player jump animation and apply upward velocity vector |
| **Video Game Gameplay** | Collision bounding box | Player intersects enemy hitbox | Physics / Collision Engine | Deduct health points, play damage audio, trigger invulnerability frames |
| **Elevator Operation** | Hallway call button | Button pressed (Up/Down) | Elevator Dispatcher / Scheduler | Queue floor request, illuminate button LED, dispatch nearest available cabin |
| **Elevator Operation** | Door optical light curtain | Infrared beam broken | Door safety sensor | Abort door closure immediately and reopen door fully |
| **Smartphone Touchscreen** | Capacitive glass display | Swipe upward gesture | Touch gesture recognizer (OS level) | Dismiss active app and transition to home screen |
| **Security Alarm System** | PIR motion sensor | Infrared signature shift detected | Alarm panel / Security gateway | Sound audible siren, illuminate floodlights, send cellular alert to dispatch |
| **Supermarket Self-Checkout** | Barcode optical scanner | Barcode decoded (UPC scanned) | Point-of-Sale (POS) application | Look up product price in inventory database, add item to bill, update running total |

---

## 2. Self-Check Questions & Answers

### 1. What is the difference between an event source and an event listener?
- **Event Source:** The originator that detects real-world or system changes (such as hardware sensors, keyboard keys, timers, or network sockets) and generates/emits an event notification. It does not decide what business logic should happen; it only broadcasts that an event occurred.
- **Event Listener:** An entity or software object registered with an event source to actively listen for incoming notifications. It acts as the bridge or receiver that receives the event and invokes the appropriate handler function.

### 2. Can one event source have multiple listeners? Give an example.
**Yes.** In event-driven architecture, a single event source can multicast events to multiple registered listeners (publish-subscribe pattern).
- *Example:* When the user clicks the "Submit Order" button on an e-commerce checkout page (Event Source), multiple independent listeners handle the single click event:
  1. An **Inventory Listener** reserves stock in the warehouse.
  2. A **Payment Listener** initiates the credit card charge.
  3. A **UI Listener** disables the button and displays a loading spinner to prevent double submissions.
  4. An **Analytics Listener** logs the conversion metrics to a marketing pipeline.

### 3. Can one listener handle events from multiple sources? Give an example.
**Yes.** A single listener or handler can be attached to multiple distinct event sources.
- *Example:* In a document editor (like Google Docs or VS Code), a `SaveDocumentHandler` listener can be registered to:
  1. The "Save" item in the File menu (`JMenuItem`).
  2. The keyboard shortcut `Ctrl + S` (`KeyEvent`).
  3. The toolbar floppy disk icon (`JButton`).
  4. An auto-save background interval timer (`javax.swing.Timer`).
  In all cases, the exact same listener executes the save logic regardless of which event source triggered it.
