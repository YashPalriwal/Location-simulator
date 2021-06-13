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
    public List<LatLng> getFlagLatLngOnPath(List<com.google.maps.model.LatLng> wayPoints, LatLng startPoint, LatLng endPoint, Double flagDistance){

        List<LatLng> flagLocations = new ArrayList<>();
        Queue<com.google.maps.model.LatLng> waypointsQueue = new LinkedList<>();
        waypointsQueue.addAll(wayPoints);
        LatLng prevFlagPoint = startPoint;
        LatLng currentWayPoint = new LatLng();
        LatLng prevPoint = startPoint;
        Double prevDistance = 0.0;
        Double distance;
        while(!waypointsQueue.isEmpty()){
            com.google.maps.model.LatLng tempPoint = waypointsQueue.poll();
            currentWayPoint.setLatitude(tempPoint.lat);
            currentWayPoint.setLongitude(tempPoint.lng);
            distance = distanceService.getDistanceBetweenTwoLatLngs(prevPoint, currentWayPoint);

            if(Double.compare(distance+prevDistance, flagDistance*1.0) >= 0){   // if the distance from last flag greater than config value
                Double value = distance+prevDistance;
                Long numberOfSteps = Math.round((value/flagDistance)-0.5);       // floor of the round value
                Double distanceOfFlagFromPrevPoint = flagDistance*1.0 - prevDistance;
                Double distanceBetweenPrevAndCurrent = distance;
                while(numberOfSteps > 0){
                    LatLng flagCoordinate = computeFlagCoordinateService.getFlagLatLng(prevPoint, currentWayPoint, distanceOfFlagFromPrevPoint, distanceBetweenPrevAndCurrent);
                    flagLocations.add(flagCoordinate);
                    value = value - flagDistance;
                    distanceOfFlagFromPrevPoint += flagDistance;
                    numberOfSteps--;
                }
                prevDistance = value;
            }
            else{
                prevDistance = prevDistance + distance;     // if no flag is added just increase the prev distance
            }
            prevPoint.setLatitude(currentWayPoint.getLatitude());
            prevPoint.setLongitude(currentWayPoint.getLongitude());
        }
        return flagLocations;
    }
}
