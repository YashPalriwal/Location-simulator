package com.palriwal.yash.aggregator;

import com.palriwal.yash.dto.GoogleDirectionsRequest;
import com.palriwal.yash.dto.GoogleDirectionsResponse;
import com.palriwal.yash.dto.LatLng;
import com.palriwal.yash.service.flagPoint.FlagPointService;
import com.palriwal.yash.service.flagPoint.QueueStrategyFlagPointServiceImpl;
import com.palriwal.yash.service.waypoint.StepPolylineWayPointServiceImpl;
import com.palriwal.yash.service.waypoint.WayPointService;
import com.palriwal.yash.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class FlagResponseAggregator {
    private FlagPointService flagPointService = new QueueStrategyFlagPointServiceImpl();
    private WayPointService wayPointService = new StepPolylineWayPointServiceImpl();
    private ResponseUtils responseUtils = new ResponseUtils();

    public List<LatLng> getFlagCoordinateResponse(GoogleDirectionsRequest request){
        if(Objects.isNull(request) || Objects.isNull(request.getOrigin()) || Objects.isNull(request.getDestination()))
        {
            log.error("Bad Request");
            return null;
        }


        String requestUrl = createDirectionsRequestUrl(request);
        System.out.println("Url : "+requestUrl);
        Client client = ClientBuilder.newClient();
        GoogleDirectionsResponse response = client.target(requestUrl).request().get(GoogleDirectionsResponse.class);
        //   should get from HTTPUtils
        Long flagDistance = 1500L; // get from config

        if(!Objects.isNull(response) && !Objects.isNull(response.getRoutes()) && !response.getRoutes().isEmpty())
        {
            List<com.google.maps.model.LatLng> waypoints = wayPointService.getWaypoints(response.getRoutes().get(0));
            List<LatLng> flagCoordinates = flagPointService.getFlagLatLngOnPath(waypoints, request.getOrigin(), request.getDestination(), flagDistance);
            return flagCoordinates;
        }
        return null;
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
}
