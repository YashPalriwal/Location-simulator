package com.palriwal.yash.utils;

import com.palriwal.yash.dto.LatLng;

import java.util.List;
import java.util.Objects;

public class ResponseUtils {
    public static void printPretty(List<LatLng> result){
        if(Objects.isNull(result) || result.isEmpty())
            return;
        for(LatLng flagLatLng : result){
            System.out.println(flagLatLng.getLatLngAsString()+",");
        }
    }
}
