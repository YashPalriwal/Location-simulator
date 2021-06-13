package com.palriwal.yash.service.flagPoint;

import com.palriwal.yash.dto.LatLng;

public interface ComputeFlagCoordinateService {
    LatLng getFlagLatLng(LatLng startPoint, LatLng endPoint, Long distanceFromStart, Long stepDistance);
}
