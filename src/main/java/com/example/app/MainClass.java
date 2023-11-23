
package com.example.app;

import com.example.app.math.MathOperations;
import com.example.util.Formatter;

public class MainClass {

    public static void main(String[] args) {
        int num1 = 10;
        int num2 = 5;

        // Perform math operations with num1 = 10 and num2 = 5
        int sum = MathOperations.add(num1, num2);
        int difference = MathOperations.subtract(num1, num2);
        int product = MathOperations.multiply(num1, num2);
        double quotient = MathOperations.divide(num1, num2);

        // Print results
        Formatter.printResult("addition", sum);
        Formatter.printResult("subtraction", difference);
        Formatter.printResult("multiplication", product);
        Formatter.printResult("division", quotient);
    }
}
