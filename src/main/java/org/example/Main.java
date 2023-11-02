package org.example;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class Main {
    public static void main(String[] args) throws IOException {

        Timer myTimer = new Timer();
        AdminHelper adminHelper = new AdminHelper();
        TestCase test1 = new TestCase();
        RabbitMQApiTest rabbitMQApiTest=new RabbitMQApiTest();
        RabbitMQApiTestGet rabbitMQApiTestGet=new RabbitMQApiTestGet();
        TimerTask myTask = new TimerTask() {
            // ANSI escape code for bold text
            String ANSI_BOLD = "\u001B[1m";
            // ANSI escape code to reset text style
            String ANSI_RESET = "\u001B[0m";

            @Override
            public void run() {
                // Print the current date and time
                System.out.println(ANSI_BOLD + "Current Date and Time : " + adminHelper.currentDateAndTime() + ANSI_RESET);
                test1.postRequestToCreateUserNameAndPassword();
                test1.postRequestToCreateToken();
                test1.putRequestNotificationService();
                test1.putRequestToAddAccountDetails();
                test1.getRequestCurrentAccountDetails();
                test1.getRequestAccountsDemo();
                test1.getRequest();
                test1.getRequestNotificationService();
                rabbitMQApiTest.RabbitMQApiTestPublishMessage();
                rabbitMQApiTestGet.RabbitMQApiTestGetMessagePost();
            }
        };

        // Schedule the task to run every 5 minutes (5 * 60 * 1000 milliseconds)
        myTimer.scheduleAtFixedRate(myTask, 0L, 1 * 60 * 1000);
    }
}
