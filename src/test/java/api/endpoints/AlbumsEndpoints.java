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
	
     public static Response getSeveralAlbums(String accessToken,String albumIds) {
		
		
		Response response = given()
				            .header("Authorization","Bearer " + accessToken)
		                    .queryParam("ids", albumIds)
		                    .when()
		                    .get(Routes.getSeveralAlbums);
				
		return response;
		
	}
     
     public static Response getSeveralAlbumsWithoutAccessToken(String albumIds) {
 		
 		
 		Response response = given()
 		                    .queryParam("ids", albumIds)
 		                    .when()
 		                    .get(Routes.getSeveralAlbums);
 				
 		return response;
 		
 	} 
     
     
     public static Response getAlbumsTracks(String albumTracksId,String accessToken) {
  		
  		
  		Response response = given()
  				            .header("Authorization","Bearer "+ accessToken)
  		                    .pathParam("id", albumTracksId)
  		                    .when()
  		                    .get(Routes.getAlbumTracks);
  				
  		return response;
  		
  	}  
	

}
