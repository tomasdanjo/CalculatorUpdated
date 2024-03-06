package com.example.calculator;

import java.util.Stack;

public class Calculator {

    public static void main(String[] args) {
//        String equation = "3 + 5.5 * 2 - 8 / 4"; // Example equation
//        double result = evaluateEquation(equation);
//        System.out.println("Result: " + result);
    }

    public static double evaluateEquation(String equation) {
        Stack<Double> operandStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();

        String[] tokens = equation.split("\\s+");

        for (String token : tokens) {
            if (token.isEmpty()) {
                continue; // Ignore empty tokens (e.g., multiple spaces)
            }

            if (isOperand(token)) {
                operandStack.push(Double.parseDouble(token));
            } else if (isOperator(token.charAt(0))) {
                while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) >= precedence(token.charAt(0))) {
                    applyOperator(operandStack, operatorStack.pop());
                }
                operatorStack.push(token.charAt(0));
            }
        }

        while (!operatorStack.isEmpty()) {
            applyOperator(operandStack, operatorStack.pop());
        }

        return operandStack.pop();
    }

    private static boolean isOperand(String token) {
        return token.matches("-?\\d+(\\.\\d+)?"); // Matches integer or decimal numbers, optionally starting with '-'
    }

    private static boolean isOperator(char token) {
        return token == '+' || token == '-' || token == 'x' || token == '/';
    }

    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case 'x':
            case '/':
                return 2;
            default:
                return 0; // Assume lowest precedence for unknown operators
        }
    }

    private static void applyOperator(Stack<Double> operandStack, char operator) {
        double operand2 = operandStack.pop();
        double operand1 = operandStack.pop();

        switch (operator) {
            case '+':
                operandStack.push(operand1 + operand2);
                break;
            case '-':
                operandStack.push(operand1 - operand2);
                break;
            case 'x':
                operandStack.push(operand1 * operand2);
                break;
            case '/':
                operandStack.push(operand1 / operand2);
                break;
        }
    }
}

