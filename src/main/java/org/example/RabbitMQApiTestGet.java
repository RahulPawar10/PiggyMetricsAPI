package org.example;

import com.google.gson.Gson;
import org.testng.annotations.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class RabbitMQApiTestGet {

    @Test
    public void RabbitMQApiTestGetMessagePost () {
        String queueName = "stackinsights"; // Replace with your queue name
        String message = "Hello, RabbitMQ!"; // Message to publish

        // RabbitMQ API endpoint URL
        String apiUrl = "http://demo.stackinsights.ai:8087/api/queues/%2F/stackinsights/get";

        // RabbitMQ credentials
        String username = "guest";
        String password = "guest";

        // Prepare the request headers and body
        Map<Object, Object> requestBody = new HashMap<>();
        requestBody.put("properties", new HashMap<>());
        requestBody.put("name", queueName);
        requestBody.put("ackmode", "ack_requeue_false");
        requestBody.put("encoding", "auto");
        requestBody.put("count","10");

        // Build the HTTP request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " +
                        java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes()))
                .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(requestBody)))
                .build();

        // Send the request and handle the response
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Publish Message Status Code: " + response.statusCode());
            System.out.println("Publish Message Response: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
