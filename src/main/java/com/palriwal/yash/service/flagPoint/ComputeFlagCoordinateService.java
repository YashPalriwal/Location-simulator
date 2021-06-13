package com.palriwal.yash.service.flagPoint;

import com.palriwal.yash.dto.LatLng;

public interface ComputeFlagCoordinateService {
    LatLng getFlagLatLng(LatLng startPoint, LatLng endPoint, Double distanceFromStart, Double stepDistance);
}
