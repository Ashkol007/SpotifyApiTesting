package api.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeSuite;

import api.endpoints.Routes;

public class BaseTest {

    protected static String accessToken;
    private static long expiryTime;

    @BeforeSuite
    public static String generateToken() {
        if (accessToken == null || System.currentTimeMillis() >= expiryTime) {
            Response response = RestAssured.given()
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .formParam("grant_type", "client_credentials")
	                .formParam("client_id", "c143956ff97a405fa52b69150c789f08")
	                .formParam("client_secret", "bed9627e08af45d89fe8e36b446a4b8b")
                    .post(Routes.getTokenUrl);

            accessToken = response.jsonPath().getString("access_token");
            System.out.println("Access Token: " + accessToken);

            int expiresIn = response.jsonPath().getInt("expires_in"); // usually 3600 sec
            expiryTime = System.currentTimeMillis() + (expiresIn - 10) * 1000;
        }
        
        return accessToken;
    }
}

