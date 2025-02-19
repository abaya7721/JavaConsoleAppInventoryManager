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
        System.out.println(message);
        console.nextLine();
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

    public double checkPrice(String message) {
        String result;
        result = readString(message);
        if(result.isEmpty()){
            return -1.00;
        }
        else {
            return validatePrice(result, 0.25, 10000.00);
        }
    }


    public int checkQuantity(String message) {
        String result;
        result = readString(message);
        if(result.isEmpty()){
            return -1;
        }
        else {
            return validateQuantity(result, 0, 1000);
        }
    }

    private int validateQuantity(String input, int min, int max){
        int quantity;
        boolean isValid = false;
            do {
                try {
                    Integer.parseInt(input);
                    isValid = true;
                } catch (NumberFormatException ex) {
                    System.out.printf("%s is not a valid number.%n", input);
                }
            } while (!isValid);

            do {
                quantity = Integer.parseInt(input);
                if (quantity < min || quantity > max) {
                    System.out.printf("Value must be between %s and %s.%n", min, max);
                }
            } while (quantity < min || quantity > max);
        return Integer.parseInt(input);
    }

    private double validatePrice(String input, double min, double max){
        double price;
        boolean isValid = false;
        do {
            try {
                Double.parseDouble(input);
                isValid = true;
            } catch (NumberFormatException ex) {
                System.out.printf("%s is not a valid number.%n", input);
            }
        } while (!isValid);

        do {
            price = Double.parseDouble(input);
            if (price < min || price > max) {
                System.out.printf("Value must be between %s and %s.%n", min, max);
            }
        } while (price < min || price > max);
        return Double.parseDouble(input);
    }

}

