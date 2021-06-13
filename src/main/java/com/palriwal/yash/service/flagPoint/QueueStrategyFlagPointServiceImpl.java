package com.palriwal.yash.service.flagPoint;

import com.palriwal.yash.dto.LatLng;
import com.palriwal.yash.service.distance.DistanceService;
import com.palriwal.yash.service.distance.HaversineDistanceServiceImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class QueueStrategyFlagPointServiceImpl implements FlagPointService {
    private final DistanceService distanceService = new HaversineDistanceServiceImpl();
    private final ComputeFlagCoordinateService computeFlagCoordinateService = new LineDivisionComputeFlagCoordinateServiceImpl();

    @Override
    public List<LatLng> getFlagLatLngOnPath(List<com.google.maps.model.LatLng> wayPoints, LatLng startPoint, LatLng endPoint, Double flagDistance){

        List<LatLng> flagLocations = new ArrayList<>();
        Queue<com.google.maps.model.LatLng> waypointsQueue = new LinkedList<>(wayPoints);
        LatLng currentWayPoint = new LatLng();
        LatLng prevPoint = new LatLng();
        prevPoint.setLatitude(startPoint.getLatitude());
        prevPoint.setLongitude(startPoint.getLongitude());
        Double prevDistance = 0.0;
        Double distance;
        while(!waypointsQueue.isEmpty()){
            com.google.maps.model.LatLng tempPoint = waypointsQueue.poll();
            currentWayPoint.setLatitude(tempPoint.lat);
            currentWayPoint.setLongitude(tempPoint.lng);
            distance = distanceService.getDistanceBetweenTwoLatLngs(prevPoint, currentWayPoint);        // calculate approximate distance between adjacent very nearby points

            if(Double.compare(distance+prevDistance, flagDistance*1.0) >= 0){   // if the distance from last flag greater than config value
                Double distanceOfLastFlagFromCurrentPoint = distance+prevDistance;
                Long numberOfSteps = Math.round((distanceOfLastFlagFromCurrentPoint/flagDistance)-0.5);       // Number of flags that can be placed between current and prev point
                Double distanceOfFlagFromPrevPoint = flagDistance*1.0 - prevDistance;       // calculating the distance of first flag to place from the prev point
                Double distanceBetweenPrevAndCurrent = distance;                // distance between previous and current point
                while(numberOfSteps > 0){
                    LatLng flagCoordinate = computeFlagCoordinateService.getFlagLatLng(prevPoint, currentWayPoint, distanceOfFlagFromPrevPoint, distanceBetweenPrevAndCurrent);
                    flagLocations.add(flagCoordinate);
                    distanceOfLastFlagFromCurrentPoint = distanceOfLastFlagFromCurrentPoint - flagDistance;            // the distance of last flag from current point reduces by flagDistance after placing a new flag
                    distanceOfFlagFromPrevPoint += flagDistance;            // distance of next flag(if any) from previous point
                    numberOfSteps--;
                }
                prevDistance = distanceOfLastFlagFromCurrentPoint;          // previous distance from flag is updated after placing all the possible flags between current and previous points
            }
            else{
                prevDistance = prevDistance + distance;     // if no flag is added just increase the prev distance
            }
            prevPoint.setLatitude(currentWayPoint.getLatitude());       // update the previous point to the current point
            prevPoint.setLongitude(currentWayPoint.getLongitude());
        }
        return flagLocations;
    }
}
