package com.palriwal.yash.service.distance;

import com.palriwal.yash.dto.LatLng;

public interface DistanceService {
    Double getDistanceBetweenTwoLatLngs(LatLng origin, LatLng destination);
}
