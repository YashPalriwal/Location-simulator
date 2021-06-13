package com.palriwal.yash.service.flagPoint;

import com.palriwal.yash.dto.LatLng;

import java.util.List;

public interface FlagPointService {
    List<LatLng> getFlagLatLngOnPath(List<com.google.maps.model.LatLng> wayPoints, LatLng startPoint, LatLng endPoint, Double flagDistance);
}
