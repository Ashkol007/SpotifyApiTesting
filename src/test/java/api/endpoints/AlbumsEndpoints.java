package api.endpoints;

import static io.restassured.RestAssured.*;

import api.tests.BaseTest;
import io.restassured.response.Response;

public class AlbumsEndpoints {
	
	
	public static Response getAlbums(String accessToken) {
		
		
		Response response = given()
				            
		                  .header("Authorization","Bearer " + accessToken)
		                  .when()
		                  .get(Routes.getAlbums);
				
		return response;
		
	}
	

}
