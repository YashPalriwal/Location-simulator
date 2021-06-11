package com.palriwal.yash;

import com.palriwal.yash.dto.GoogleDirectionsRequest;
import com.palriwal.yash.dto.GoogleDirectionsResponse;
import com.palriwal.yash.dto.LatLng;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import java.lang.String;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import java.util.HashMap;
import java.util.Map;

public class LocusTestApplication{
    public static void main(String[] args) throws Exception{
        LocusTestApplication locusTestApplication = new LocusTestApplication();
        LatLng origin = LatLng.builder()
                .latitude(12.37133)
                .longitude(76.4342)
                .build();
        LatLng destination = LatLng.builder()
                .latitude(13.3434)
                .longitude(77.3434)
                .build();

        GoogleDirectionsRequest googleDirectionsRequest = GoogleDirectionsRequest.builder().origin(origin).destination(destination).build();
        locusTestApplication.getGoogleDirectionsResponse(googleDirectionsRequest);
    }

    public void getGoogleDirectionsResponse(GoogleDirectionsRequest request){
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("key", "AIzaSyAEQvKUVouPDENLkQlCF6AAap1Ze-6zMos");
        queryParams.put("origin", request.getOrigin().getLatLngAsString());
        queryParams.put("destination", request.getDestination().getLatLngAsString());

        String baseUrl = "https://maps.googleapis.com";
        String endPoint = "/maps/api/directions/json";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(baseUrl).append(endPoint);

        final Boolean[] firstElement = {true};
        queryParams.forEach((key,val) -> {
            if(firstElement[0]){
                stringBuilder.append("?").append(key).append("=").append(val);
                firstElement[0] = false;
            }
            else
                stringBuilder.append("&").append(key).append("=").append(val);
        });

        String requestUrl = stringBuilder.toString();
        System.out.println("Url : "+requestUrl);
        HttpGet get = new HttpGet(requestUrl);


    }









}
