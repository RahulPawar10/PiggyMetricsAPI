package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdminHelper {

    public String currentDateAndTime() {
        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Define a custom date and time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Format the current date and time using the specified format
        String formattedDateTime = currentDateTime.format(formatter);

        // Print the formatted current date and time
      //  System.out.println("Current Date and Time: " + formattedDateTime);
        return formattedDateTime;
    }
}
