
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class CustomCalculator extends JFrame {
    private JTextField textField;

    public CustomCalculator() {
        setTitle("Basic Calculator");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Color bgColor = new Color(240, 248, 240);
        Color buttonColor = new Color(189, 224, 185);
        Color textColor = new Color(40, 40, 40);

        textField = new JTextField();
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setFont(new Font("Arial", Font.BOLD, 70));
        textField.setBackground(bgColor);
        textField.setForeground(textColor);
        textField.setCaretColor(textColor);
        add(textField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBackground(bgColor);

        String[] buttons = {
                "C", "√", "%", "/",
                "7", "8", "9", "*",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "0", ".", "^", "="
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 22));
            button.setBackground(buttonColor);
            button.setForeground(textColor);
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        textField.addActionListener(e -> evaluateExpression());

        setFocusable(true);
        requestFocusInWindow();
        getContentPane().setBackground(bgColor);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("=")) {
                evaluateExpression();
            } else if (command.equals("C")) {
                textField.setText("");
            } else {
                textField.setText(textField.getText() + command);
            }
        }
    }

    private void evaluateExpression() {
        try {
            String expression = textField.getText();
            double result = calculate(expression);
            if (Double.isNaN(result)) {
                textField.setText("Error");
            }else {
                textField.setText(String.valueOf(result));
            }
        } catch (Exception ex) {
            textField.setText("Error");
        }
    }

    private double calculate(String expression) {
        try {
            if (expression.contains("+")) {
                String[] parts = expression.split("\\+");
                if (parts.length != 2) throw new IllegalArgumentException("Invalid Input");
                return Double.parseDouble(parts[0]) + Double.parseDouble(parts[1]);
            } else if (expression.contains("-")) {
                String[] parts = expression.split("-");
                if (parts.length != 2) throw new IllegalArgumentException("Invalid Input");
                return Double.parseDouble(parts[0]) - Double.parseDouble(parts[1]);
            } else if (expression.contains("*")) {
                String[] parts = expression.split("\\*");
                if (parts.length != 2) throw new IllegalArgumentException("Invalid Input");
                return Double.parseDouble(parts[0]) * Double.parseDouble(parts[1]);
            } else if (expression.contains("/")) {
                String[] parts = expression.split("/");
                if (parts.length != 2) throw new IllegalArgumentException("Invalid Input");
                double num1 = Double.parseDouble(parts[0]);
                double num2 = Double.parseDouble(parts[1]);

                if (num1 == 0 && num2 == 0) throw new ArithmeticException("Undefined");
                if (num2 == 0) throw new ArithmeticException("Division by zero");
                return num1 / num2;
            } else if (expression.contains("^")) {
                String[] parts = expression.split("\\^");
                if (parts.length != 2) throw new IllegalArgumentException("Invalid Input");
                return Math.pow(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
            } else if (expression.contains("√")) {
                if (expression.equals("√")) throw new IllegalArgumentException("Invalid Input");
                return Math.sqrt(Double.parseDouble(expression.replace("√", "")));
            } else if (expression.contains("%")) {
                if (expression.equals("%")) throw new IllegalArgumentException("Invalid Input");
                return Double.parseDouble(expression.replace("%", "")) / 100;
            }

            throw new IllegalArgumentException("Invalid Input");

        } catch (ArithmeticException e) {
            textField.setText(e.getMessage());  
            return Double.NaN;
        } catch (Exception e) {
            textField.setText("Error");
            return Double.NaN;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CustomCalculator calculator = new CustomCalculator();
            calculator.setVisible(true);
        });
    }
}

