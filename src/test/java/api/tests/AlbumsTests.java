package api.tests;

import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Test;
import api.endpoints.AlbumsEndpoints;
import io.restassured.response.Response;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class AlbumsTests extends BaseTest {
	
	String accessToken = generateToken();
	String severalAlbumsId = "382ObEPsp2rxGrnsizN5TX,1A2GTWGtFfWp7KSQTwWOyo,2noRn2Aes5aoNVsU6iWThc";
	String getAlbumTrackId = "4aawyAB9vmqN3uQ7FjRGTy";
	
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
		        
		         		         
		         Assert.assertEquals(response.statusCode(), 200);
		         Assert.assertEquals(response.body().jsonPath().getString("album_type"), "album");
		         Assert.assertEquals(response.body().jsonPath().getString("id"),"4aawyAB9vmqN3uQ7FjRGTy");
		
	}
	
	@Test(priority=1)
	public void getAlbumsUnauthorized() {
		
		
		
		Response response = AlbumsEndpoints.getAlbums("BrDpKTttGqTet3uM_dE-fAfS9vo-rJoAGvGXRAOfCTsGUxZKO4-XzhNn9wEplANZfPRJPQRmTyR9Q36GHjvq8gqg20HmwStY3usE-T-0yfqC7fwf130UozrFC5DnDemSwlMH-VYYJTs");
		         response.then().log().all();
		         
		         Assert.assertEquals(response.statusCode(), 401);
		         Assert.assertEquals(response.body().jsonPath().getString("error.message"), "Invalid access token");  
	}
	
//	GET Several Albums
	
	@Test(priority=2)
	public void getSevevralAlbums() {
		
		
		Response response = AlbumsEndpoints.getSeveralAlbums(accessToken, "382ObEPsp2rxGrnsizN5TX,1A2GTWGtFfWp7KSQTwWOyo,2noRn2Aes5aoNVsU6iWThc");
//		         response.then().log().all();
		
		         response.then()
		         .body("albums.tracks.href",everyItem(startsWith("https://api.spotify.com/v1/albums/")))
		         .body("albums[0].id", notNullValue())
		         .body("albums[0].id", not(emptyOrNullString()))
		         .body("albums[0].label", notNullValue())
		         .time(lessThan(3000L))
		         ;
		         
//		         String popularity =  response.getBody().jsonPath().getString("copyrights");

                 
		         
		         Assert.assertEquals(response.getStatusCode(), 200);
		         Assert.assertEquals(response.body().jsonPath().getString("albums[0].album_type"), "album");
		         Assert.assertEquals(response.body().jsonPath().getString("albums[0].artists[0].name"), "Daft Punk");
		         
		
	}
	
//	Negative Tests
	
	@Test(priority=3)
	public void getSevevralAlbumsWithInvalidId() {
		
		
		Response response = AlbumsEndpoints.getSeveralAlbums(accessToken, "invalidid1A2GTWGtFfWp7KSQTwWOyo,2noRn2Aes5aoNVsU6iWThc");
		         response.then().log().all();
		         
		         Assert.assertEquals(response.getStatusCode(), 400);
		         Assert.assertEquals(response.body().jsonPath().getString("error.message"), "Invalid base62 id");
		         
		
	}
	

	@Test(priority=3)
	public void getSevevralAlbumsWithEmptyAuth() {
		
		         accessToken = "";   
		
		Response response = AlbumsEndpoints.getSeveralAlbums(accessToken,"382ObEPsp2rxGrnsizN5TX,1A2GTWGtFfWp7KSQTwWOyo,2noRn2Aes5aoNVsU6iWThc");
		         response.then().log().all();
		         
		         Assert.assertEquals(response.getStatusCode(), 400);
		         Assert.assertEquals(response.body().jsonPath().getString("error.message"), "Only valid bearer authentication supported");
		         
		
	}
	
	//Security testing

	@Test(priority=3)
	public void getSevevralAlbumsWithOutAuth(){
		  
		
		Response response = AlbumsEndpoints.getSeveralAlbumsWithoutAccessToken("382ObEPsp2rxGrnsizN5TX,1A2GTWGtFfWp7KSQTwWOyo,2noRn2Aes5aoNVsU6iWThc");
		         response.then().log().all();
		         
		         Assert.assertEquals(response.getStatusCode(), 401);
		         Assert.assertEquals(response.body().jsonPath().getString("error.message"), "No token provided");
		         
		
	}
	
//	Get Album Tracks
	@Test(priority=4)
	public void getAlbumTracks() {
		
		Response response = AlbumsEndpoints.getAlbumsTracks(getAlbumTrackId, accessToken);
		         response.then()
		         .body("items[0].artists.id", notNullValue())
                 .body("items[0].id", notNullValue())
                 .body("href", startsWith("https://api.spotify.com/v1/albums/4aawyAB9vmqN3uQ7FjRGT"))
                 .body("href", endsWithIgnoringCase("tracks?offset=0&limit=20"))
                 .body("items[0].artists[0].name", equalToIgnoringCase("pitbull"))
                 .body("items[0].artists[0].type", equalToIgnoringCase("artist"))
                 .body("items[0].artists[0].id", equalToIgnoringCase("0TnOYISbd1XYRBk9myaseg"))
                 .body("items[0].artists[0].href", equalTo("https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg"))
                 .body("items[0].artists.name",contains("Pitbull","Sensato") )
                 .body("items[0].artists.href", hasItem("https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg"))
                 .body("items", hasSize(greaterThan(10)));
		
	}



	
  	

}
