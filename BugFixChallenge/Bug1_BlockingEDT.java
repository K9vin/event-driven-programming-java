import javax.swing.*;
import java.awt.*;

public class Bug1_BlockingEDT extends JFrame {

    private JLabel statusLabel;
    private JButton processButton;

    public Bug1_BlockingEDT() {
        setTitle("Bug 1 - Blocking EDT (Fixed)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        statusLabel = new JLabel("Ready");
        processButton = new JButton("Process");

        // FIX: Offload long-running work to a background thread to keep EDT responsive
        processButton.addActionListener(e -> {
            statusLabel.setText("Processing...");
            processButton.setEnabled(false); // Prevent re-triggering while running

            new Thread(() -> {
                try {
                    // Simulate heavy 5-second computation or network/disk operation
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

                // Safely update UI components on the EDT
                SwingUtilities.invokeLater(() -> {
                    statusLabel.setText("Done!");
                    processButton.setEnabled(true);
                });
            }).start();
        });

        add(statusLabel);
        add(processButton);
        setSize(300, 150);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Bug1_BlockingEDT().setVisible(true);
        });
    }
}
