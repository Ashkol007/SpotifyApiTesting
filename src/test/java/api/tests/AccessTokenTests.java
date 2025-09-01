package api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.AccessTokenEndpoints;
import io.restassured.response.Response;

public class AccessTokenTests extends BaseTest {
	
	
	@Test()
	public void getAccessToken() {
		
		Response  response = AccessTokenEndpoints.getAccessToken();
		          response.then().log().all();
		          
		          String accessToken =  generateToken();		          
		          System.out.println("BaseClass AccessToken: "+ accessToken);
		          
		          Assert.assertEquals(response.statusCode(), 200);
		
	} 
	

}
