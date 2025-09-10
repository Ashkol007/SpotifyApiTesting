package api.tests;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.endsWithIgnoringCase;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;

import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.AlbumsEndpoints;
import api.models.AlbumTracksResponse;
import io.restassured.response.Response;

public class AlbumsTests extends BaseTest {
	
	String accessToken = generateToken();
	String severalAlbumsId = "382ObEPsp2rxGrnsizN5TX,1A2GTWGtFfWp7KSQTwWOyo,2noRn2Aes5aoNVsU6iWThc";
	String getAlbumTrackId = "4aawyAB9vmqN3uQ7FjRGTy";
	String invalidId = "0011invalid8899";
	@Test(priority=1)
	public void getAlbums() {
		
		
		Response response = AlbumsEndpoints.getAlbums(accessToken);
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
	@Test(priority=4,groups= {"Functional"})
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
		         
		         Assert.assertEquals(response.statusCode(), 200);
		
	}
	
	@Test(priority=4,groups= {"Negative"})
    public void getAlbumTrackswithInvalidId() {
		
		Response response = AlbumsEndpoints.getAlbumsTracks(invalidId, accessToken);
		         response.then()
		         .body("error.status", equalTo(400))
		         .body("error.message", equalToIgnoringCase("Invalid base62 id"));
		         
		         Assert.assertEquals(response.statusCode(), 400);
		         Assert.assertEquals(response.body().jsonPath().getString("error.message"), "Invalid base62 id");
		
	}
	
	@Test(priority=5,groups= {"Functional"})
	public void getAlbumTracksPOJO() {
		
		Response response = AlbumsEndpoints.getAlbumsTracks(getAlbumTrackId, accessToken);
		                                                                   
		   AlbumTracksResponse albumTracks =  response.then().extract().as(AlbumTracksResponse.class);
		   	
		   	   System.out.println("Album href: "+ albumTracks.getHref());
			   System.out.println("Album limit: "+ albumTracks.getLimit());
			   System.out.println("Album offset: "+ albumTracks.getOffset());
			   System.out.println("Album total: "+ albumTracks.getTotal());
			   
			   System.out.println("First track id: "+ albumTracks.getItems().get(0).getId());
			   System.out.println("First track name: "+ albumTracks.getItems().get(0).getName());
			   
			   System.out.println("First track first artist id: "+ albumTracks.getItems().get(0).getArtists().get(0).getId());
			   System.out.println("First track first artist name: "+ albumTracks.getItems().get(0).getArtists().get(0).getName());
			   
			   System.out.println("First track second artist id: "+ albumTracks.getItems().get(0).getArtists().get(1).getId());
			   System.out.println("First track second artist name: "+ albumTracks.getItems().get(0).getArtists().get(1).getName());
			   
			   System.out.println("First track available markets: "+ Arrays.toString(albumTracks.getItems().get(0).getAvailable_markets().toArray()));
			   
			   Assert.assertEquals(albumTracks.getHref(), "https://api.spotify.com/v1/albums/4aawyAB9vmqN3uQ7FjRGTy/tracks?offset=0&limit=20");
			   Assert.assertTrue(albumTracks.getLimit() == 20);
			   Assert.assertTrue(albumTracks.getOffset() == 0);
			   Assert.assertTrue(albumTracks.getTotal() == 18);
			   
			   Assert.assertEquals(albumTracks.getItems().get(0).getId(), "6OmhkSOpvYBokMKQxpIGx2");
			   Assert.assertEquals(albumTracks.getItems().get(0).getName(), "Global Warming (feat. Sensato)");
			   
			   Assert.assertEquals(albumTracks.getItems().get(0).getArtists().get(0).getId(), "0TnOYISbd1XYRBk9myaseg");
			   Assert.assertEquals(albumTracks.getItems().get(0).getArtists().get(0).getName(), "Pitbull");
			   Assert.assertTrue(albumTracks.getItems().get(0).getAvailable_markets().size() >10);
			   Assert.assertTrue(albumTracks.getItems().get(0).getAvailable_markets().contains("AT"));
                      		
			   
		         
		         Assert.assertEquals(response.statusCode(), 200);
		
	}



	
  	

}
