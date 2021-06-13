package com.palriwal.yash.api;

import com.palriwal.yash.dto.GoogleDirectionsRequest;
import com.palriwal.yash.dto.GoogleDirectionsResponse;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class GoogleDirectionsApi {
    public GoogleDirectionsResponse getGoogleDirectionsApiResponse(GoogleDirectionsRequest request){
        String requestUrl = createDirectionsRequestUrl(request);
        log.info("Url : {}", requestUrl);
        Client client = ClientBuilder.newClient();
        try{
            GoogleDirectionsResponse response = client.target(requestUrl).request().get(GoogleDirectionsResponse.class);
            return response;
        }catch(Exception e){
            log.error("Error while getting response from directions api :: {}", e.getMessage());
        }
        return null;
    }

    private String createDirectionsRequestUrl(GoogleDirectionsRequest request){
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("key", "AIzaSyAEQvKUVouPDENLkQlCF6AAap1Ze-6zMos");      // keep in config
        queryParams.put("origin", request.getOrigin().getLatLngAsString());
        queryParams.put("destination", request.getDestination().getLatLngAsString());

        String baseUrl = "https://maps.googleapis.com";     // keep in config
        String endPoint = "/maps/api/directions/json";      // keep in config
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
}
