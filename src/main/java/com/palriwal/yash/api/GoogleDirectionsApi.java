package com.palriwal.yash.api;

import com.palriwal.yash.config.ConstantsAndConfig;
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
        queryParams.put("key", ConstantsAndConfig.DIRECTIONS_API_KEY);      // keep in config
        queryParams.put("origin", request.getOrigin().getLatLngAsString());
        queryParams.put("destination", request.getDestination().getLatLngAsString());

        StringBuilder requestUrl = new StringBuilder();
        requestUrl.append(ConstantsAndConfig.DIRECTIONS_API_HOST).append(ConstantsAndConfig.DIRECTIONS_API_ENDPOINT);

        final Boolean[] firstElement = {true};
        queryParams.forEach((key,val) -> {
            if(firstElement[0]){
                requestUrl.append("?").append(key).append("=").append(val);
                firstElement[0] = false;
            }
            else
                requestUrl.append("&").append(key).append("=").append(val);
        });
        return requestUrl.toString();
    }
}
