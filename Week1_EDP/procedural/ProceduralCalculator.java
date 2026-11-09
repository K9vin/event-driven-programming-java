import java.util.Scanner;

public class ProceduralCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Procedural Calculator ===");
        System.out.println();

        // Step 1: Get first number
        System.out.print("Enter first number: ");
        double num1 = scanner.nextDouble();

        // Step 2: Get operator
        System.out.print("Enter operator (+, -, *, /): ");
        String operator = scanner.next();

        // Step 3: Get second number
        System.out.print("Enter second number: ");
        double num2 = scanner.nextDouble();

        // Step 4: Calculate
        double result = 0;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    System.out.println("Error: Cannot divide by zero");
                    scanner.close();
                    return;
                }
                break;
            default:
                System.out.println("Invalid operator");
                scanner.close();
                return;
        }

        // Step 5: Display result
        System.out.println();
        System.out.println("Result: " + num1 + " " + operator + " " + num2 + " = " + result);

        scanner.close();
    }
}
