package com.palriwal.yash;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.palriwal.yash.dto.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import java.lang.String;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.client.ClientBuilder;

public class LocusTestApplication{
    private ObjectMapper objectMapper = new ObjectMapper();
    public static void main(String[] args) throws Exception{
        LocusTestApplication locusTestApplication = new LocusTestApplication();
        LatLng origin = new LatLng();
        origin.setLatitude(12.37133);
        origin.setLongitude(76.4342);

        LatLng destination = new LatLng();
        destination.setLatitude(12.567153);
        destination.setLongitude(76.537422);

        GoogleDirectionsRequest googleDirectionsRequest = GoogleDirectionsRequest.builder().origin(origin).destination(destination).build();
        locusTestApplication.getGoogleDirectionsResponse(googleDirectionsRequest);
    }

    public String createDirectionsRequestUrl(GoogleDirectionsRequest request){
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
        return stringBuilder.toString();
    }

    public void getGoogleDirectionsResponse(GoogleDirectionsRequest request){

        String requestUrl = createDirectionsRequestUrl(request);
        System.out.println("Url : "+requestUrl);
        HttpGet get = new HttpGet(requestUrl);

        Client client = ClientBuilder.newClient();
        try {
//            Map<String, Object> response = client.target(requestUrl).request().get(HashMap.class);
            GoogleDirectionsResponse response = client.target(requestUrl).request().get(GoogleDirectionsResponse.class);
            System.out.println("got some response");
            String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(response);
//            List<Route> routes = (List<Route>) response.get("routes");
//            objectMapper.readValue(json, GoogleDirectionsResponse.class);
//            Map the response to the custom response DTO class
            System.out.println(json);
        }catch(Exception e){
            System.out.println("Exception caught :: "+e.getMessage());
            return;
        }
    }









}
