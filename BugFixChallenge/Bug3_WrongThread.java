import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bug3_WrongThread extends JFrame {
    private JLabel timeLabel;
    private JButton startButton;
    private volatile boolean running = false;
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    public Bug3_WrongThread() {
        setTitle("Bug 3 - Wrong Thread (Fixed)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        timeLabel = new JLabel("Time: --:--:--");
        timeLabel.setFont(new Font("Arial", Font.BOLD, 16));

        startButton = new JButton("Start Clock");
        startButton.addActionListener(e -> {
            if (running) {
                return; // Prevent starting multiple clock threads
            }
            running = true;
            startButton.setEnabled(false);

            new Thread(() -> {
                while (running) {
                    String currentTimeStr = timeFormat.format(new Date());

                    // FIX: Always dispatch UI updates to the Event Dispatch Thread (EDT)
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
        });

        add(timeLabel);
        add(startButton);
        setSize(350, 150);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Bug3_WrongThread().setVisible(true);
        });
    }
}
