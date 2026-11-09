import javax.swing.*;
import java.awt.*;

public class EventDrivenCalculator extends JFrame {

    private JTextField display;
    private double firstOperand = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    public EventDrivenCalculator() {
        // Window setup
        setTitle("Event-Driven Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Display field
        display = new JTextField("0");
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 28));
        display.setBackground(Color.WHITE);
        add(display, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Row 1: 7, 8, 9, /
        buttonPanel.add(createNumberButton("7"));
        buttonPanel.add(createNumberButton("8"));
        buttonPanel.add(createNumberButton("9"));
        buttonPanel.add(createOperatorButton("/"));

        // Row 2: 4, 5, 6, *
        buttonPanel.add(createNumberButton("4"));
        buttonPanel.add(createNumberButton("5"));
        buttonPanel.add(createNumberButton("6"));
        buttonPanel.add(createOperatorButton("*"));

        // Row 3: 1, 2, 3, -
        buttonPanel.add(createNumberButton("1"));
        buttonPanel.add(createNumberButton("2"));
        buttonPanel.add(createNumberButton("3"));
        buttonPanel.add(createOperatorButton("-"));

        // Row 4: 0, C, =, +
        buttonPanel.add(createNumberButton("0"));
        buttonPanel.add(createClearButton("C"));
        buttonPanel.add(createEqualsButton("="));
        buttonPanel.add(createOperatorButton("+"));

        add(buttonPanel, BorderLayout.CENTER);

        // Window settings
        setSize(350, 450);
        setLocationRelativeTo(null);
    }

    private JButton createNumberButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 20));

        // THIS IS THE EVENT HANDLER
        btn.addActionListener(e -> {
            if (startNewNumber) {
                display.setText(text);
                startNewNumber = false;
            } else {
                display.setText(display.getText() + text);
            }
        });

        return btn;
    }

    private JButton createOperatorButton(String op) {
        JButton btn = new JButton(op);
        btn.setFont(new Font("Arial", Font.BOLD, 20));

        // THIS IS THE EVENT HANDLER
        btn.addActionListener(e -> {
            firstOperand = Double.parseDouble(display.getText());
            operator = op;
            startNewNumber = true;
        });

        return btn;
    }

    private JButton createEqualsButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 20));
        btn.setBackground(new Color(100, 200, 100));

        // THIS IS THE EVENT HANDLER
        btn.addActionListener(e -> {
            double secondOperand = Double.parseDouble(display.getText());
            double result = 0;

            switch (operator) {
                case "+":
                    result = firstOperand + secondOperand;
                    break;
                case "-":
                    result = firstOperand - secondOperand;
                    break;
                case "*":
                    result = firstOperand * secondOperand;
                    break;
                case "/":
                    if (secondOperand != 0) {
                        result = firstOperand / secondOperand;
                    } else {
                        display.setText("Error");
                        startNewNumber = true;
                        return;
                    }
                    break;
                default:
                    return;
            }

            display.setText(String.valueOf(result));
            startNewNumber = true;
        });

        return btn;
    }

    private JButton createClearButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 20));
        btn.setBackground(new Color(255, 150, 150));

        // THIS IS THE EVENT HANDLER
        btn.addActionListener(e -> {
            display.setText("0");
            firstOperand = 0;
            operator = "";
            startNewNumber = true;
        });

        return btn;
    }

    public static void main(String[] args) {
        // ALWAYS start Swing applications on the EDT
        SwingUtilities.invokeLater(() -> {
            new EventDrivenCalculator().setVisible(true);
        });
    }
}
