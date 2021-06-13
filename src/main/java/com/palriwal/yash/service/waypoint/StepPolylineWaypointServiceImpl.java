package com.palriwal.yash.service.waypoint;

import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.LatLng;
import com.palriwal.yash.dto.Legs;
import com.palriwal.yash.dto.Route;
import com.palriwal.yash.dto.Steps;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

@Slf4j
public class StepPolylineWaypointServiceImpl implements waypointService{
    @Override
    public List<com.google.maps.model.LatLng> getWaypoints(Route route){
        if(Objects.isNull(route) || Objects.isNull(route.getLegs()) || route.getLegs().isEmpty())
            return null;
        List<Legs> legs = route.getLegs();
        List<Steps> steps = new ArrayList<>();
        for(Legs leg : legs){                   // generalizing for multiple waypoints
            if(!Objects.isNull(leg.getSteps()))
                steps.addAll(leg.getSteps());   // adding all the steps of a leg
        }

        List<com.google.maps.model.LatLng> wayPoints = new ArrayList<>();
        for(Steps step : steps){
            // decoding the polyline of each step to get a list of lat,lng on road
            try {
                wayPoints.addAll(PolylineEncoding.decode(step.getPolyline().getPoints()));
            }catch(Exception e){
                log.error("Error decoding polyline : {}, error message : {}", step.getPolyline().getPoints(), e.getMessage());
            }
        }
        return wayPoints;
    }
}
