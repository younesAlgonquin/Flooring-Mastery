package com.sg.flooringmastery.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 * @author Younes Boutaleb email address: boutalebyounes@gmail.com Current date:
 * Purpose of the class:
 */
@Component
public class UserIOConsoleImpl implements UserIO {

    final private Scanner console = new Scanner(System.in);

    /**
     *
     * A very simple method that takes in a message to display on the console
     * and then waits for a integer answer from the user to return.
     *
     * @param msg - String of information to display to the user.
     *
     */
    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    /**
     *
     * A simple method that takes in a message to display on the console, and
     * then waits for an answer from the user to return.
     *
     * @param msgPrompt - String explaining what information you want from the
     * user.
     * @return the answer to the message as string
     */
    @Override
    public String readString(String msgPrompt) {
        System.out.println(msgPrompt);
        return console.nextLine();
    }

    /**
     *
     * A simple method that takes in a message to display on the console, and
     * continually reprompts the user with that message until they enter an
     * integer to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the
     * user.
     * @return the answer to the message as integer
     */
    @Override
    public int readInt(String msgPrompt) {
        boolean invalidInput = true;
        int num = 0;
        while (invalidInput) {
            try {
                // print the message msgPrompt (ex: asking for the # of cats!)
                String stringValue = this.readString(msgPrompt);
                // Get the input line, and try and parse
                num = Integer.parseInt(stringValue); // if it's 'bob' it'll break
                invalidInput = false; // or you can use 'break;'
            } catch (NumberFormatException e) {
                // If it explodes, it'll go here and do this.
                System.out.println("Input error. Please try again.");
            }
        }
        return num;
    }

    /**
     *
     * A slightly more complex method that takes in a message to display on the
     * console, and continually reprompts the user with that message until they
     * enter an integer within the specified min/max range to be returned as the
     * answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the
     * user.
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return an integer value as an answer to the message prompt within the
     * min/max range
     * @throws com.sg.vendingmachine.ui.InventoryInvalidInputException
     */
    @Override
    public int readInt(String msgPrompt, int min, int max) {
        int result;
        do {
            result = readInt(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    /**
     *
     * A simple method that takes in a message to display on the console, and
     * continually reprompts the user with that message until they enter a long
     * to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the
     * user.
     * @return the answer to the message as long
     * @throws com.sg.vendingmachine.ui.InventoryInvalidInputException
     */
    @Override
    public long readLong(String msgPrompt) {
        while (true) {
            try {
                return Long.parseLong(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                System.out.println("Input error. Please try again.");
            }
        }
    }

    /**
     * A slightly more complex method that takes in a message to display on the
     * console, and continually reprompts the user with that message until they
     * enter a double within the specified min/max range to be returned as the
     * answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the
     * user.
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return an long value as an answer to the message prompt within the
     * min/max range
     * @throws com.sg.vendingmachine.ui.InventoryInvalidInputException
     */
    @Override
    public long readLong(String msgPrompt, long min, long max) {
        long result;
        do {
            result = readLong(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    /**
     *
     * A simple method that takes in a message to display on the console, and
     * continually reprompts the user with that message until they enter a float
     * to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the
     * user.
     * @return the answer to the message as float
     */
    @Override
    public float readFloat(String msgPrompt) {
        while (true) {
            try {
                return Float.parseFloat(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                System.out.println("Input error. Please try again.");
            }
        }
    }

    /**
     *
     * A slightly more complex method that takes in a message to display on the
     * console, and continually reprompts the user with that message until they
     * enter a float within the specified min/max range to be returned as the
     * answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the
     * user.
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return an float value as an answer to the message prompt within the
     * min/max range
     * @throws com.sg.vendingmachine.ui.InventoryInvalidInputException
     */
    @Override
    public float readFloat(String msgPrompt, float min, float max) {
        float result;
        do {
            result = readFloat(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    /**
     *
     * A simple method that takes in a message to display on the console, and
     * continually reprompts the user with that message until they enter a
     * double to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the
     * user.
     * @return the answer to the message as double
     */
    @Override
    public double readDouble(String msgPrompt) {
        while (true) {
            try {
                return Double.parseDouble(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                System.out.println("Input error. Please try again.");
            }
        }
    }

    /**
     *
     * A slightly more complex method that takes in a message to display on the
     * console, and continually reprompts the user with that message until they
     * enter a double within the specified min/max range to be returned as the
     * answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the
     * user.
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return an double value as an answer to the message prompt within the
     * min/max range
     */
    @Override
    public double readDouble(String msgPrompt, double min, double max) {
        double result;
        do {
            result = readDouble(msgPrompt);
        } while (result < min || result > max);
        return result;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {

        BigDecimal amount;

        while (true) {
            try {
                return amount = new BigDecimal(this.readString(prompt));
            } catch (NumberFormatException e) {

                System.out.println("Input error. Please try again.");

            }
        }

    }

    @Override
    public BigDecimal readPositiveBigDecimal(String prompt, BigDecimal min) {

        BigDecimal amount = new BigDecimal(-1);
        do {

            amount = this.readBigDecimal(prompt);

        } while (amount.compareTo(min)<0);

        return amount;

    }

    @Override
    public LocalDate readDate(String prompt) {

        while (true) {
            String dateInput = readString(prompt);

            try {
                return LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("MM-dd-yyyy"));

            } catch (DateTimeParseException e) {

                System.out.println("invalid format. Use this format: MM-dd-yyyy");

            }
        }//end while

    }

    @Override
    public LocalDate readFutureDate(String prompt) {

        LocalDate orderDate;
        LocalDate today;

        do {

            orderDate = readDate(prompt);
            today = LocalDate.now();

        } while (!orderDate.isAfter(today));
        
        return orderDate;

    }

}
