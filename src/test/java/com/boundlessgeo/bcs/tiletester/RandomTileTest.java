package com.boundlessgeo.bcs.tiletester;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.boundlessgeo.bcs.tiletester.utilities.Pixel;
import com.boundlessgeo.bcs.tiletester.utilities.TileCoordConverter;

public class RandomTileTest extends FunctionalTest {
    @Test
    public void basicPingTest() {
        given().log().all().when().get("/5/5/11.png").then().statusCode(200);
    }
    @Test
    public void responseTimeTest() {
        long time = 
    	given().log().all().when().get("/5/5/11.png").then().extract().response().time();
        
        assertThat(time, lessThan(5000L));
    }
    //aero-poly,agriculture,amenity-areas,beach,building,forest,grass,highway-label,park,parking-area,placenames-medium,route-bridge-0
    //route-bridge-1,route-bridge-2,route-bridge-3,route-bridge-4,route-bridge-5,route-fill, route-line, route-tunnels,route-turning-circles, water-outline,water,wetland
    
    @Test
    public void pingAeroPoly(){
    	Pixel meters = dbu.getMetersCenterOfRandomFeature("agriculture");
    	Integer z = 5;
    	TileCoordConverter tcc = new TileCoordConverter();
    	Pixel tile = tcc.metersToTile(meters.getX(), meters.getY(), 5);
    	String tmsaddress = tcc.getTMSTileAddressString(z, tile);
    	given().log().all().when().get(tmsaddress).then().statusCode(200);
    }

}
