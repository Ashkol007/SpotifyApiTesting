package api.tests;

import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Test;
import api.endpoints.AlbumsEndpoints;
import io.restassured.response.Response;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AlbumsTests extends BaseTest {
	
	
	@Test(priority=1)
	public void getAlbums() {
		
		
		Response response = AlbumsEndpoints.getAlbums(generateToken());
		         response.then().log().all();
		         
		        response.then()
		        .body("album_type", equalTo("album"))
	            .body("id", equalTo("4aawyAB9vmqN3uQ7FjRGTy"))
	            .body("total_tracks", greaterThan(0))
	            .body("available_markets", hasItems("AT", "BE", "BG"))
	            .body("images", hasSize(greaterThan(0)))
	            .body("images.url", everyItem(startsWith("https://")));
		        
		        System.out.println("upc : "+ response.body().jsonPath().getString("artists[0].external_urls"));
		         		         
		         Assert.assertEquals(response.statusCode(), 200);
		         Assert.assertEquals(response.body().jsonPath().getString("album_type"), "album");
		         Assert.assertEquals(response.body().jsonPath().getString("id"),"4aawyAB9vmqN3uQ7FjRGTy");
		
	}
	
	@Test(priority=1)
	public void getAlbumsUnauthorized() {
		
		
		
		Response response = AlbumsEndpoints.getAlbums("BrDpKTttGqTet3uM_dE-fAfS9vo-rJoAGvGXRAOfCTsGUxZKO4-XzhNn9wEplANZfPRJPQRmTyR9Q36GHjvq8gqg20HmwStY3usE-T-0yfqC7fwf130UozrFC5DnDemSwlMH-VYYJTs");
		         response.then().log().all();
		         
		         Assert.assertEquals(response.statusCode(), 401);
		
	}
	
	
  	

}
