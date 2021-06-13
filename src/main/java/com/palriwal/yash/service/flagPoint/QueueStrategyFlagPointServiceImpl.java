package com.palriwal.yash.service.flagPoint;

import com.palriwal.yash.dto.LatLng;
import com.palriwal.yash.service.distance.DistanceService;
import com.palriwal.yash.service.distance.HaversineDistanceServiceImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class QueueStrategyFlagPointServiceImpl implements FlagPointService {
    private DistanceService distanceService = new HaversineDistanceServiceImpl();
    private ComputeFlagCoordinateService computeFlagCoordinateService = new LineDivisionComputeFlagCoordinateServiceImpl();

    @Override
    public List<LatLng> getFlagLatLngOnPath(List<com.google.maps.model.LatLng> wayPoints, LatLng startPoint, LatLng endPoint, Long flagDistance){

        List<LatLng> flagLocations = new ArrayList<>();
        Queue<com.google.maps.model.LatLng> waypointsQueue = new LinkedList<>();
        waypointsQueue.addAll(wayPoints);
        LatLng prevFlagPoint = startPoint;
        LatLng currentWayPoint = new LatLng();
        Long distance = 0L;
        while(!waypointsQueue.isEmpty()){
            com.google.maps.model.LatLng tempPoint = waypointsQueue.poll();
            currentWayPoint.setLatitude(tempPoint.lat);
            currentWayPoint.setLongitude(tempPoint.lng);
            distance = Math.round(distanceService.getDistanceBetweenTwoLatLngs(prevFlagPoint, currentWayPoint));
            if(distance >= flagDistance){

           /*    start ->  prevFlagPoint
                 end -> currentWayPoint
                 startToEndDistance -> haversineDistance --> distance
                 FlagDistance
                    --> return a list of flagPoints and update prevFlagPoint
            */
                Long numberOfSteps = distance/flagDistance;
                while(numberOfSteps > 0){
                    LatLng flagCoordinate = computeFlagCoordinateService.getFlagLatLng(prevFlagPoint, currentWayPoint, flagDistance, distance);
                    prevFlagPoint = flagCoordinate;
                    distance = distance - flagDistance;
                    flagLocations.add(flagCoordinate);
                    numberOfSteps--;
                }

            }
        }
        return flagLocations;
    }
}
