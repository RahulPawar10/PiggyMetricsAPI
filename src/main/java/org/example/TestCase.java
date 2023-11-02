package org.example;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import io.restassured.http.Method;
import io.restassured.response.Response;

import java.util.Random;

public class TestCase {
    public String jsonFile;
    public String authorizationToken;
    public String password;
    public String username;

    SoftAssert softAssert = new SoftAssert();

    Response response;


    @Test
    public void postRequestToCreateUserNameAndPassword() {
        try {
            //Specify Base URL
            RestAssured.baseURI = "https://demo-app.stackinsights.ai/";

            //Request Object
            RequestSpecification httpRequestSpecification = RestAssured.given();

            // Set headers if required
            httpRequestSpecification.header("Content-Type", "application/json");

            //Create username And password
            JSONObject testdata = new JSONObject();
            Random random = new Random();
            username = "ABC" + random.nextInt();
            testdata.put("username", username);
            password = "123" + random.nextInt();
            testdata.put("password", password);

            httpRequestSpecification.body(testdata.toString());

            //Response Object

            response = (Response) httpRequestSpecification.request(Method.POST, "accounts/");

            // Getting the status code
            int statusCode = response.getStatusCode();
            //Verify the Status Code
            softAssert.assertEquals(statusCode, 200);

            // System.out.println(response.getBody().asString());
            System.out.println("username->" + username);
            System.out.println("password->" + password);
            System.out.println("postRequest"+" Run");
            softAssert.assertAll();
        } catch (AbstractMethodError e) {
            e.printStackTrace();
        }
    }
    @Test(priority = 1)
    public void postRequestToCreateToken() {
        try {
            // Specify Base URL
            RestAssured.baseURI = "https://demo-app.stackinsights.ai";

            // Request Object
            RequestSpecification httpRequestSpecification = RestAssured.given();

            // Set form parameters
            httpRequestSpecification
                    .formParam("scope", "ui")
                    .formParam("username", username)
                    .formParam("password", password)
                    .formParam("grant_type", "password");

            //Set Authorization
            httpRequestSpecification.request().header("Authorization", "Basic YnJvd3Nlcjo=");

            // Send POST request and get response
            response = httpRequestSpecification.request(Method.POST, "/uaa/oauth/token");

            softAssert.assertEquals(response.getStatusCode(), 200);

            // Get authorization token from the response
            authorizationToken = response.jsonPath().getString("access_token");


            // Print response details
            // System.out.println("Response Body: " + responseBody);
            //System.out.println("Authorization Token: " + authorizationToken);
            System.out.println("postRequestToken"+" Run");
            softAssert.assertAll();
        } catch (AbstractMethodError e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 2,invocationCount = 10)
    public void putRequestNotificationService() {
        try {
            //Specify Base URL
            RestAssured.baseURI = "https://demo-app.stackinsights.ai/";

            //Request Object
            RequestSpecification httpRequestSpecification = RestAssured.given();

            // Set headers if required
            httpRequestSpecification.header("Content-Type", "application/json");

            httpRequestSpecification.auth().oauth2(authorizationToken);
            String Name=username;
            String email = "abc"+Math.random()+"@gmail.com";
            boolean active = true;
            String frequency = "MONTHLY";
            String requestBody = "{\"accountName\":\""+Name+"\",\"email\":\"" + email + "\",\"scheduledNotifications\":{\"REMIND\":{\"active\":" + active + ",\"frequency\":\"" + frequency + "\"}}}";

            httpRequestSpecification.body(requestBody);

            //Response Object
            response = httpRequestSpecification.request(Method.PUT, "notifications/recipients/current");
            softAssert.assertEquals(response.getStatusCode(), 200);
            System.out.println("putRequestNotificationService"+" Run");
            softAssert.assertAll();
        } catch (AbstractMethodError e) {
            e.printStackTrace();
        }
    }
    @Test(priority = 2)
    public void putRequestToAddAccountDetails() {
        try {
            //Specify Base URL
            RestAssured.baseURI = "https://demo-app.stackinsights.ai/";

            //Request Object
            RequestSpecification httpRequestSpecification = RestAssured.given();

            // Set headers if required
            httpRequestSpecification.header("Content-Type", "application/json");

            httpRequestSpecification.auth().oauth2(authorizationToken);

            httpRequestSpecification.body("{\"note\":\"Test1\",\"incomes\":[{\"title\":\"TestAuto1\",\"icon\":\"wallet\",\"currency\":\"USD\",\"period\":\"MONTH\",\"amount\":\"100000\",\"converted\":\"1000.000\"},{\"title\":\"TestAuto2\",\"icon\":\"wallet\",\"currency\":\"USD\",\"period\":\"MONTH\",\"amount\":\"100000\",\"converted\":\"1000.000\"},{\"title\":\"TestAuto3\",\"icon\":\"wallet\",\"currency\":\"USD\",\"period\":\"MONTH\",\"amount\":\"100000\",\"converted\":\"1000.000\"}],\"expenses\":[{\"title\":\"Test1\",\"icon\":\"cart\",\"currency\":\"USD\",\"period\":\"MONTH\",\"amount\":\"1000\",\"converted\":\"1000.000\"},{\"title\":\"Test2\",\"icon\":\"cart\",\"currency\":\"USD\",\"period\":\"MONTH\",\"amount\":\"1000\",\"converted\":\"1000.000\"},{\"title\":\"Test3\",\"icon\":\"cart\",\"currency\":\"USD\",\"period\":\"MONTH\",\"amount\":\"1000\",\"converted\":\"1000.000\"},{\"title\":\"Test4\",\"icon\":\"cart\",\"currency\":\"USD\",\"period\":\"MONTH\",\"amount\":\"1000\",\"converted\":\"1000.000\"}],\"saving\":{\"amount\":100,\"capitalization\":true,\"deposit\":true,\"currency\":\"USD\",\"interest\":\"0\"}}\n");

            //Response Object
            response = httpRequestSpecification.request(Method.PUT, "accounts/current");//200
            softAssert.assertEquals(response.getStatusCode(), 200);
            System.out.println("putRequest"+" Run");
            softAssert.assertAll();
        } catch (AbstractMethodError e) {
            e.printStackTrace();
        }
    }
    @Test(priority = 3)
    public void getRequestCurrentAccountDetails() {
        try {
            //Specify Base URL
            RestAssured.baseURI = "https://demo-app.stackinsights.ai";
            //Request Object
            RequestSpecification httpReuestRequestSpecification = RestAssured.given();
            httpReuestRequestSpecification.auth().oauth2(authorizationToken);

            //Response Object
            response = httpReuestRequestSpecification.request(Method.GET, "/accounts/current");
            softAssert.assertEquals(response.getStatusCode(), 200);

            System.out.println("getRequestCurrentAccount"+" Run");
            softAssert.assertAll();
        } catch (AbstractMethodError e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 3)
    public void getRequestAccountsDemo() {
        try {
            //Specify Base URL
            RestAssured.baseURI = "https://demo-app.stackinsights.ai";
            //Request Object
            RequestSpecification httpReuestRequestSpecification = RestAssured.given();
            httpReuestRequestSpecification.auth().oauth2(authorizationToken);

            //Response Object
            response = httpReuestRequestSpecification.request(Method.GET, "/accounts/demo");
            softAssert.assertEquals(response.getStatusCode(), 200);
            // System.out.println("Get Body: " + response.getBody().asString());
            System.out.println("getRequestAccountsDemo"+" Run");
            softAssert.assertAll();
        } catch (AbstractMethodError e) {
            e.printStackTrace();
        }
    }


    @Test(priority = 3)
    public void getRequest() {
        try {
            //Specify Base URL
            RestAssured.baseURI = "https://demo-app.stackinsights.ai";
            //Request Object
            RequestSpecification httpReuestRequestSpecification = RestAssured.given();
            httpReuestRequestSpecification.auth().oauth2(authorizationToken);

            //Response Object
            response = httpReuestRequestSpecification.request(Method.GET, "/");
            softAssert.assertEquals(response.getStatusCode(), 200);
            System.out.println("getRequest"+" Run");
            softAssert.assertAll();
        } catch (AbstractMethodError e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 3)
    public void getRequestNotificationService() {
        try {
            //Specify Base URL
            RestAssured.baseURI = "https://demo-app.stackinsights.ai";
            //Request Object
            RequestSpecification httpReuestRequestSpecification = RestAssured.given();
            httpReuestRequestSpecification.auth().oauth2(authorizationToken);

            //Response Object
            response = httpReuestRequestSpecification.request(Method.GET, "notifications/recipients/current");
            softAssert.assertEquals(response.getStatusCode(), 200);
            //System.out.println("Get Body: " + response.getBody().asString());
            System.out.println(response.getBody().asString());
            softAssert.assertAll();
        } catch (AbstractMethodError e) {
            e.printStackTrace();
        }
    }
}
