import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SmartHomePrototype.java
 * Prototype implementation of an Event-Driven Smart Home Controller.
 * Week 1 EDP - Activity 5
 */
public class SmartHomePrototype extends JFrame {

    private JLabel statusLabel;
    private JLabel modeLabel;
    private JTextArea eventLog;
    private JToggleButton lightsToggle;
    private JToggleButton securityToggle;
    private JButton doorButton;
    private JButton motionButton;
    private JButton resetAlarmButton;
    private JPanel headerPanel;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public SmartHomePrototype() {
        setTitle("Smart Home Controller (IoT Edge Hub)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 1. Header & Status Panel (NORTH)
        headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        headerPanel.setBackground(new Color(240, 243, 246));

        statusLabel = new JLabel("System Status: NORMAL (All Zones Secure)");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        statusLabel.setForeground(new Color(30, 130, 60));

        modeLabel = new JLabel("Security: DISARMED | Living Room Lights: OFF");
        modeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        modeLabel.setForeground(Color.DARK_GRAY);

        headerPanel.add(statusLabel);
        headerPanel.add(modeLabel);
        add(headerPanel, BorderLayout.NORTH);

        // 2. Central Event Log (CENTER)
        eventLog = new JTextArea(12, 40);
        eventLog.setEditable(false);
        eventLog.setFont(new Font("Consolas", Font.PLAIN, 12));
        eventLog.setBackground(new Color(250, 250, 250));
        JScrollPane scrollPane = new JScrollPane(eventLog);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Live Event Dispatch Log"));
        add(scrollPane, BorderLayout.CENTER);

        // 3. Interactive Controls Panel (SOUTH)
        JPanel controlPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        // Handler 1: Lights Toggle
        lightsToggle = new JToggleButton("Lights: OFF");
        lightsToggle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lightsToggle.addActionListener(e -> {
            boolean isOn = lightsToggle.isSelected();
            lightsToggle.setText(isOn ? "Lights: ON" : "Lights: OFF");
            logEvent("EVENT_LIGHT_TOGGLED", isOn ? "Living room lights turned ON." : "Living room lights turned OFF.");
            updateModeDisplay();
        });

        // Handler 2: Security Armed Toggle
        securityToggle = new JToggleButton("Security: DISARMED");
        securityToggle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        securityToggle.addActionListener(e -> {
            boolean isArmed = securityToggle.isSelected();
            if (isArmed) {
                securityToggle.setText("Security: ARMED");
                securityToggle.setBackground(new Color(255, 230, 200));
                logEvent("EVENT_SECURITY_MODE", "Perimeter security armed by homeowner.");
            } else {
                securityToggle.setText("Security: DISARMED");
                securityToggle.setBackground(null);
                logEvent("EVENT_SECURITY_MODE", "Security system disarmed.");
                clearAlarmVisuals();
            }
            updateModeDisplay();
        });

        // Handler 3: Door Sensor Simulator
        doorButton = new JButton("Simulate Door Sensor Open");
        doorButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        doorButton.addActionListener(e -> {
            if (securityToggle.isSelected()) {
                triggerAlarm("BREACH DETECTED: Perimeter Front Door Opened while Armed!");
            } else {
                logEvent("EVENT_DOOR_SENSOR", "Front door opened (Normal entry - system disarmed).");
                statusLabel.setText("System Status: NORMAL (Door opened)");
                statusLabel.setForeground(new Color(30, 130, 60));
            }
        });

        // Handler 4: Motion Sensor Simulator
        motionButton = new JButton("Simulate Motion Detector");
        motionButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        motionButton.addActionListener(e -> {
            if (securityToggle.isSelected()) {
                triggerAlarm("INTRUSION DETECTED: Motion detected in Hallway zone while Armed!");
            } else {
                logEvent("EVENT_MOTION_SENSOR", "Hallway motion detected. Auto night-light timer triggered.");
            }
        });

        // Handler 5: Alarm Reset Button
        resetAlarmButton = new JButton("Reset Alarm / Silence Siren");
        resetAlarmButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        resetAlarmButton.setBackground(new Color(230, 230, 230));
        resetAlarmButton.addActionListener(e -> {
            clearAlarmVisuals();
            logEvent("EVENT_ALARM_RESET", "Alarm silenced and system reset to normal monitoring.");
        });

        controlPanel.add(lightsToggle);
        controlPanel.add(securityToggle);
        controlPanel.add(doorButton);
        controlPanel.add(motionButton);
        controlPanel.add(resetAlarmButton);

        add(controlPanel, BorderLayout.SOUTH);

        // Window properties
        pack();
        setSize(580, 500);
        setLocationRelativeTo(null);

        logEvent("SYSTEM_INIT", "Smart Home Controller initialized and listening for events.");
    }

    private void triggerAlarm(String breachDescription) {
        logEvent("CRITICAL_SECURITY_ALERT", breachDescription);
        statusLabel.setText("ALERT: INTRUDER DETECTED! SIREN ACTIVE!");
        statusLabel.setForeground(Color.RED);
        headerPanel.setBackground(new Color(255, 200, 200));

        // Offload siren/alert notification to background thread so EDT doesn't freeze
        new Thread(() -> {
            // Emulate audio siren or cloud IoT push dispatch
            Toolkit.getDefaultToolkit().beep();
        }).start();
    }

    private void clearAlarmVisuals() {
        statusLabel.setText("System Status: NORMAL (All Zones Secure)");
        statusLabel.setForeground(new Color(30, 130, 60));
        headerPanel.setBackground(new Color(240, 243, 246));
    }

    private void updateModeDisplay() {
        String secStatus = securityToggle.isSelected() ? "ARMED" : "DISARMED";
        String lightStatus = lightsToggle.isSelected() ? "ON" : "OFF";
        modeLabel.setText(String.format("Security: %s | Living Room Lights: %s", secStatus, lightStatus));
    }

    private void logEvent(String eventType, String details) {
        String timestamp = dateFormat.format(new Date());
        String entry = String.format("[%s] <%s> %s%n", timestamp, eventType, details);
        eventLog.append(entry);
        eventLog.setCaretPosition(eventLog.getDocument().getLength());
    }

    public static void main(String[] args) {
        // Guarantee GUI initialization on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            new SmartHomePrototype().setVisible(true);
        });
    }
}
