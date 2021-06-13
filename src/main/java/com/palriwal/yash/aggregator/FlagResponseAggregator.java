package com.palriwal.yash.aggregator;

import com.palriwal.yash.api.GoogleDirectionsApi;
import com.palriwal.yash.config.ConstantsAndConfig;
import com.palriwal.yash.dto.GoogleDirectionsRequest;
import com.palriwal.yash.dto.GoogleDirectionsResponse;
import com.palriwal.yash.dto.LatLng;
import com.palriwal.yash.service.flagPoint.FlagPointService;
import com.palriwal.yash.service.flagPoint.QueueStrategyFlagPointServiceImpl;
import com.palriwal.yash.service.waypoint.OverviewPolylineWayPointServiceImpl;
import com.palriwal.yash.service.waypoint.StepPoints;
import com.palriwal.yash.service.waypoint.StepPolylineWayPointServiceImpl;
import com.palriwal.yash.service.waypoint.WayPointService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

@Slf4j
public class FlagResponseAggregator {
    private FlagPointService flagPointService = new QueueStrategyFlagPointServiceImpl();
    private WayPointService wayPointService = new StepPolylineWayPointServiceImpl();
    private GoogleDirectionsApi directionsApi = new GoogleDirectionsApi();

    public List<LatLng> getFlagCoordinateResponse(GoogleDirectionsRequest request){
        if(Objects.isNull(request) || Objects.isNull(request.getOrigin()) || Objects.isNull(request.getDestination()))
        {
            log.error("Bad Request");
            return null;
        }
        GoogleDirectionsResponse response = directionsApi.getGoogleDirectionsApiResponse(request);

        if(!Objects.isNull(response) && !Objects.isNull(response.getRoutes()) && !response.getRoutes().isEmpty())
        {
            List<com.google.maps.model.LatLng> waypoints = wayPointService.getWaypoints(response.getRoutes().get(0));
            List<LatLng> flagCoordinates = flagPointService.getFlagLatLngOnPath(waypoints, request.getOrigin(), request.getDestination(), ConstantsAndConfig.FLAG_DISTANCE);
            return flagCoordinates;
        }
        return null;
    }
}
