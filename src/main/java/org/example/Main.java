package org.example;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {

        Timer myTimer = new Timer();
        AdminHelper adminHelper = new AdminHelper();
        TestCase test1 = new TestCase();
        TimerTask myTask = new TimerTask() {
            // ANSI escape code for bold text
            String ANSI_BOLD = "\u001B[1m";
            // ANSI escape code to reset text style
            String ANSI_RESET = "\u001B[0m";

            @Override
            public void run() {
                // Print the current date and time
                System.out.println(ANSI_BOLD + "Current Date and Time : " + adminHelper.currentDateAndTime() + ANSI_RESET);
                test1.postRequest();
                test1.postRequestToken();
                test1.putRequest();
                test1.getRequestCurrentAccount();
                test1.getRequestAccountsDemo();
                test1.getRequest();
            }
        };

        // Schedule the task to run every 5 minutes (5 * 60 * 1000 milliseconds)
        myTimer.scheduleAtFixedRate(myTask, 0L, 1 * 60 * 1000);
    }
}
