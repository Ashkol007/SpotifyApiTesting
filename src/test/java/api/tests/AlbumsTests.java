package api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.AlbumsEndpoints;
import io.restassured.response.Response;

public class AlbumsTests extends BaseTest {
	
	
	@Test(priority=1)
	public void getAlbums() {
		
		
		
		Response response = AlbumsEndpoints.getAlbums(generateToken());
		         response.then().log().all();
		         
		         Assert.assertEquals(response.statusCode(), 200);
		
	}
	
	
  	

}
