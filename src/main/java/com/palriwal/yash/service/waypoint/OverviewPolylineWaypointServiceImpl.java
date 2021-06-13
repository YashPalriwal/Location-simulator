package com.palriwal.yash.service.waypoint;

import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.LatLng;
import com.palriwal.yash.dto.Route;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class OverviewPolylineWaypointServiceImpl implements waypointService{
    @Override
    public List<LatLng> getWaypoints(Route route) {
        if(Objects.isNull(route) || Objects.isNull(route.getOverviewPolyline()) || Objects.isNull(route.getOverviewPolyline().getPoints()))
            return null;

        List<com.google.maps.model.LatLng> wayPoints = new ArrayList<>();
        try {
            wayPoints.addAll(PolylineEncoding.decode(route.getOverviewPolyline().getPoints()));
        }catch(Exception e){
            log.error("Error decoding polyline : {}, error message : {}", route.getOverviewPolyline().getPoints(), e.getMessage());
        }
        return wayPoints;
    }
}
