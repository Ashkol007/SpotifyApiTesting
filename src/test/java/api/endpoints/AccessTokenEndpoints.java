package api.endpoints;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import api.tests.BaseTest;

public class AccessTokenEndpoints {
	
	
	 public static Response getAccessToken() {
	        Response response = given()
	                .header("Content-Type", "application/x-www-form-urlencoded")
	                .formParam("grant_type", "client_credentials")
	                .formParam("client_id", "c143956ff97a405fa52b69150c789f08")
	                .formParam("client_secret", "bed9627e08af45d89fe8e36b446a4b8b")
	        .when()
	                .post(Routes.getTokenUrl);

	        return response;
	    }

}
