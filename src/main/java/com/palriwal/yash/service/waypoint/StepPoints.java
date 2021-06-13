package com.palriwal.yash.service.waypoint;

import com.palriwal.yash.dto.Legs;
import com.palriwal.yash.dto.Route;
import com.palriwal.yash.dto.Steps;

import java.util.*;

public class StepPoints implements WayPointService{
    public List<com.google.maps.model.LatLng> getWaypoints(Route route){
        if(Objects.isNull(route) || Objects.isNull(route.getLegs()))
            return null;
        Set<com.google.maps.model.LatLng> stepStartEndPoints = new HashSet<>();
        for(Legs leg : route.getLegs()){
            if(!Objects.isNull(leg.getSteps()) && !leg.getSteps().isEmpty()){
                for(Steps step : leg.getSteps()){
                    if(!Objects.isNull(step) && !Objects.isNull(step.getStartLocation()) && !Objects.isNull(step.getEndLocation())){
                        stepStartEndPoints.add(new com.google.maps.model.LatLng(step.getStartLocation().getLatitude(), step.getStartLocation().getLongitude()));
                        stepStartEndPoints.add(new com.google.maps.model.LatLng(step.getEndLocation().getLatitude(), step.getEndLocation().getLongitude()));
                    }
                }
            }
        }
        List<com.google.maps.model.LatLng> waypoints = new ArrayList<>();
        waypoints.addAll(stepStartEndPoints);
        return waypoints;
    }

}
