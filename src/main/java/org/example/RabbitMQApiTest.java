package org.example;

import com.google.gson.Gson;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class RabbitMQApiTest {
    SoftAssert softAssert = new SoftAssert();
    @Test
    public void RabbitMQApiTestPublishMessage () {
       String queueName = "stackinsights";
        String message = "Hello, RabbitMQ!";

        // RabbitMQ API endpoint URL
        String apiUrl = "http://demo.stackinsights.ai:8087/api/exchanges/%2f/amq.default/publish";

        // RabbitMQ credentials
        String username = "guest";
        String password = "guest";

        // Prepare the request headers and body
        Map<Object, Object> requestBody = new HashMap<>();
        requestBody.put("properties", new HashMap<>());
        requestBody.put("routing_key", queueName);
        requestBody.put("payload", message);
        requestBody.put("payload_encoding", "string");

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
            softAssert.assertEquals(response.statusCode(), 200);
            System.out.println("Publish Message Response: " + response.body());
            softAssert.assertAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
