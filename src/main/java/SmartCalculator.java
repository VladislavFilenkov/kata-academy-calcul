import java.util.Scanner;

public class SmartCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите выражение:");

        String input = scanner.nextLine();

        try {
            String result = calculateExpression(input);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        scanner.close();
    }

    private static String calculateExpression(String input) {
        if (containsRomanNumerals(input)) {
            int result = Calculator.calculateExpression(input, true);
            return Calculator.arabicToRoman(result);
        } else {
            int result = Calculator.calculateExpression(input, false);
            return String.valueOf(result);
        }
    }

    private static boolean containsRomanNumerals(String input) {
        return input.matches(".*[IVX].*");
    }
}

class Calculator {
    public static int calculateExpression(String input, boolean isRoman) {
        String[] tokens = input.split("\\s+");

        if (tokens.length != 3) {
            throw new IllegalArgumentException("Некорректный ввод");
        }

        int num1 = isRoman ? romanToArabic(tokens[0]) : Integer.parseInt(tokens[0]);
        char operator = tokens[1].charAt(0);
        int num2 = isRoman ? romanToArabic(tokens[2]) : Integer.parseInt(tokens[2]);

        validateRange(num1);
        validateRange(num2);

        return calculate(num1, operator, num2);
    }

    private static void validateRange(int num) {
        if (num < 1 || num > 10) {
            throw new IllegalArgumentException("Некорректный ввод");
        }
    }

    private static int calculate(int num1, char operator, int num2) {
        switch (operator) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Некорректный ввод");
        }
    }

    public static String arabicToRoman(int arabic) {
        if (arabic < 1 || arabic > 100) {
            throw new IllegalArgumentException("Некорректный ввод");
        }

        String[] romanArray = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XL", "L", "XC", "C"};
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 40, 50, 90, 100};

        StringBuilder result = new StringBuilder();
        int i = 13;

        while (arabic > 0) {
            int div = arabic / values[i];
            arabic %= values[i];

            while (div-- > 0) {
                result.append(romanArray[i]);
            }
            i--;
        }

        return result.toString();
    }

    private static int romanToArabic(String roman) {
        int result = 0;

        for (int i = 0; i < roman.length(); i++) {
            char currentChar = roman.charAt(i);
            int currentValue = romanCharToValue(currentChar);

            if (i + 1 < roman.length()) {
                int nextValue = romanCharToValue(roman.charAt(i + 1));

                if (currentValue < nextValue) {
                    result -= currentValue;
                } else {
                    result += currentValue;
                }
            } else {
                result += currentValue;
            }
        }

        return result;
    }

    private static int romanCharToValue(char romanChar) {
        switch (romanChar) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            default:
                throw new IllegalArgumentException("Некорректный ввод");
        }
    }
}
