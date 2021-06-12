package com.palriwal.yash.service.Distance;

import com.palriwal.yash.dto.LatLng;

public interface DistanceService {
    Double getDistanceBetweenTwoLatLngs(LatLng origin, LatLng destination);
}
