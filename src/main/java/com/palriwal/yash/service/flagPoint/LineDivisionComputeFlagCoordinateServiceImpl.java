package com.palriwal.yash.service.flagPoint;

import com.palriwal.yash.dto.LatLng;

public class LineDivisionComputeFlagCoordinateServiceImpl implements ComputeFlagCoordinateService{
    @Override
    public LatLng getFlagLatLng(LatLng startPoint, LatLng endPoint, Double distanceFromStart, Double stepDistance){

        /*
         *   Coordinates of point(x,y) lying on line segment AB where A = (x1,y1) and B = (x2,y2)
         *   such that it is at a distance m1 from A and distance m2 from B can be calculated using the following formulas
         *   using simple coordinate geometry.
         *
         *   x = (m2*x1 + m1*x2)/(m1+m2);
         *   y = (m2*y1 + m1*y2)/(m1+m2);
         *
         */
        if(startPoint.equals(endPoint))
            return startPoint;          // Avoiding division by 0 case
        LatLng flagCoordinates = new LatLng();
        Double distanceFromEnd = stepDistance - distanceFromStart;
        if(!(Double.compare(stepDistance, 0.0) > 0))
            return null;
        Double flagLatitude = ((startPoint.getLatitude()*distanceFromEnd) + (endPoint.getLatitude()*distanceFromStart))/stepDistance;
        Double flagLongitude = ((startPoint.getLongitude()*distanceFromEnd) + (endPoint.getLongitude()*distanceFromStart))/stepDistance;
        flagCoordinates.setLatitude(flagLatitude);
        flagCoordinates.setLongitude(flagLongitude);
        return flagCoordinates;
    }
}
