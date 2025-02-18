package com.app.manager.view;

import java.util.Scanner;

public class Console {
    private Scanner console = new Scanner(System.in);

    public MenuOptions displayMainMenu(){
        displayHeader("===== Inventory Manager =====\n");
        MenuOptions[] options = MenuOptions.values();
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%s. %s%n\n", i + 1, options[i].getMessage());
        }

        String msg = String.format("Select [%s-%s]:", 1, options.length);
        int value = readInt(msg, 1, options.length);
        return options[value - 1];
    }

    public void displayHeader(String header){
        System.out.println(header);
    }

    private String readString(String message) {
        System.out.print(message);
        return console.nextLine();
    }

    public void pressEnter(String message) {
        System.out.print(message);
        console.next();
    }

    public String readRequiredString(String message) {
        String result;
        do {
            result = readString(message);
            if (result.trim().length() == 0) {
                System.out.println("Value is required.");
            }
        } while (result.trim().length() == 0);
        return result;
    }

    public int readInt(String message) {
        String input = null;
        int result = 0;
        boolean isValid = false;
        do {
            try {
                input = readRequiredString(message);
                result = Integer.parseInt(input);
                isValid = true;
            } catch (NumberFormatException ex) {
                System.out.printf("%s is not a valid number.%n", input);
            }
        } while (!isValid);

        return result;
    }

        public int readInt(String prompt, int min, int max){
        int result;
        do {
            result = readInt(prompt);
            if (result < min || result > max) {
                System.out.printf("Value must be between %s and %s.%n", min, max);
            }
        } while (result < min || result > max);
        return result;
    }

    public double readDouble(String message) {
        String input = null;
        double result = 0;
        boolean isValid = false;
        do {
            try {
                input = readRequiredString(message);
                result = Double.parseDouble(input);
                isValid = true;
            } catch (NumberFormatException ex) {
                System.out.printf("%s is not a valid number.%n", input);
            }
        } while (!isValid);

        return result;
    }

}
