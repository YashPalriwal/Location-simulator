package com.palriwal.yash.service.distance;

import com.palriwal.yash.dto.LatLng;

import java.util.Objects;

import static java.lang.Math.*;
import static java.lang.StrictMath.asin;
import static java.lang.StrictMath.pow;

public class HaversineDistanceServiceImpl implements DistanceService{
    @Override
    public Double getDistanceBetweenTwoLatLngs(LatLng origin, LatLng destination){
        {
            // distance between latitudes
            // and longitudes
            double lat1 = origin.getLatitude();
            double lon1 = origin.getLongitude();
            double lat2 = destination.getLatitude();
            double lon2 = destination.getLongitude();

            if(Objects.isNull(origin) || Objects.isNull(destination) || origin.equals(destination))
                return 0.0;

            double dLat = (lat2 - lat1) *
                    PI / 180.0;
            double dLon = (lon2 - lon1) *
                    PI / 180.0;

            // convert to radians
            lat1 = (lat1) * PI / 180.0;
            lat2 = (lat2) * PI / 180.0;

            // apply formulae
            double a = pow(sin(dLat / 2), 2) +
                    pow(sin(dLon / 2), 2) *
                            cos(lat1) * cos(lat2);
            double rad = 6371;
            double c = 2 * asin(sqrt(a));
            return rad * c * 1000;  // distance in metres
        }
    }
}
