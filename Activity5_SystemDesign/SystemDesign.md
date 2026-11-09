# Event-Driven System Design

## System Name: Smart Home Controller (IoT Edge Hub)

---

## Part 1: Event Sources
List all components that can generate events:

1. **Ambient Light / Wall Switch (`JToggleButton` / Physical Switch):** Generates toggled states (`ON`/`OFF`) for room illumination based on manual user override or photo-sensor thresholds.
2. **Security System Arming Keypad / Switch (`JToggleButton`):** Emits state transition events (`ARMED_AWAY`, `ARMED_STAY`, `DISARMED`) when occupants engage or disengage the home defense perimeter.
3. **Smart Door & Window Contact Sensors (`JButton` / Magnetic Reed Switch):** Triggers `DOOR_OPENED` or `DOOR_CLOSED` events whenever a perimeter portal boundary is breached or secured.
4. **Motion PIR & Thermal Presence Sensor (`JButton` / Passive Infrared Sensor):** Emits `MOTION_DETECTED` pulses when infrared thermal patterns shift within interior monitored zones.
5. **Thermostat Temperature Sensor & HVAC Controller (`JSlider` / Bimetallic Probe):** Emits `TEMPERATURE_CHANGED` events when room temperature deviates above cooling setpoints or below heating setpoints.

---

## Part 2: Events
List all possible events in the system:

| Event Name | Source | Description |
|---|---|---|
| `LIGHT_STATE_TOGGLED` | Wall Switch / UI Toggle | Triggered when user manually flips light toggle or ambient lux drops below threshold. |
| `SECURITY_MODE_CHANGED` | Security Keypad / UI Button | Triggered when security mode transitions between Disarmed, Home, or Armed. |
| `PERIMETER_DOOR_BREACHED`| Magnetic Door Sensor | Triggered when door contact breaks while door opens. |
| `MOTION_DETECTED` | PIR Motion Sensor | Triggered when movement is sensed inside living zones. |
| `HVAC_CYCLE_DEMAND` | Thermostat Setpoint / Sensor | Triggered when current temperature crosses heating/cooling hysteresis threshold. |

---

## Part 3: Event Handlers
For each event, describe what should happen:

| Event | Handler Action |
|---|---|
| `LIGHT_STATE_TOGGLED` | Reads new toggle state; switches relays for target lighting fixtures; updates visual bulb icon, lumen telemetry, and app log. |
| `SECURITY_MODE_CHANGED` | If Armed: locks smart deadbolts, arms perimeter tripwires, and updates banner status. If Disarmed: turns off sirens, disarms sirens, and resets visual alert indicators. |
| `PERIMETER_DOOR_BREACHED`| Checks security system state. If Armed: sounds 105 dB alarm siren, flashes red emergency strobe lighting, logs security incident with timestamp, and dispatches cellular push alert. If Disarmed: plays gentle entry chime and updates access log. |
| `MOTION_DETECTED` | If Armed: flags intruder alarm and engages CCTV recording. If Disarmed: turns on hallway night-lights automatically for 3 minutes and updates motion telemetry log. |
| `HVAC_CYCLE_DEMAND` | Calculates differential; engages furnace heater or AC compressor unit; activates airflow blower; logs power consumption. |

---

## Part 4: Event Flow Diagram
Draw a diagram showing how events flow through the system:

```text
[ Physical Sensor / User Action ]
               │
               ▼
   [ Event Source (e.g. Door Sensor) ]
               │
               ▼ (Generates Event Object: type, timestamp, sourceId)
       ┌────────────────┐
       │  EVENT QUEUE   │  <-- FIFO Queue buffering concurrent events
       └────────────────┘
               │
               ▼ (Dequeued by EDT / Event Dispatch Loop)
       ┌────────────────┐
       │   EVENT LOOP   │  <-- Single-threaded dispatcher
       └────────────────┘
               │
               ├──> [ Listener Registry Lookup ]
               │
               ▼ (Invokes registered listener callback)
   [ Event Handler / Dispatcher ]
         │               │
         │ (State Check) │ (Heavy/Async tasks offloaded to Worker Thread)
         ▼               ▼
 [ Security Armed? ]   [ Background Dispatch (SMS, Network IoT) ]
   ├── YES: Flash Red UI, Play Siren, Alert Cloud
   └── NO:  Play Soft Chime, Log Entry
         │
         ▼ (SwingUtilities.invokeLater)
 [ GUI State Update & Event Log Append ]
```

---

## Part 5: Potential Issues & Mitigation Strategies

1. **EDT Freeze on Network Dispatch / Cloud Notification:**
   - *Risk:* If the event handler synchronously contacts cloud IoT services (e.g. AWS IoT, push notification API) on the EDT during an alarm event, the entire interface will freeze until the network call returns or times out.
   - *Mitigation:* Offload all network I/O, database logging, and sound synthesis onto a daemon background thread pool (`ExecutorService`), using `SwingUtilities.invokeLater()` strictly for graphical status and log updates.

2. **Race Conditions / Event Bursting (Sensor Chattering):**
   - *Risk:* A vibrating or fluctuating physical door contact switch might trigger dozens of rapid open/close events within a few milliseconds, overwhelming the event queue.
   - *Mitigation:* Implement debouncing / throttling logic in the event listener or firmware to ignore subsequent events within a 250ms threshold.

3. **Memory Leaks from Stale Subscriptions:**
   - *Risk:* Dynamically registering listener callbacks without unregistering them when sensor components or widgets are decommissioned creates dangling references, preventing garbage collection.
   - *Mitigation:* Maintain clean lifecycle management with unregister/cleanup methods or utilize weak references (`WeakReference`) for listener subscriptions.
