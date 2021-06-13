package com.palriwal.yash.utils;

import com.palriwal.yash.dto.LatLng;

import java.util.List;
import java.util.Objects;

public class ResponseUtils {
    public static void printPretty(List<LatLng> result){
        if(Objects.isNull(result) || result.isEmpty())
        {
            System.out.println("Points A and B are too close, no flags can be placed...");
        }
        System.out.println("Coordinates of Flags excluding the start and end point : ");
        for(LatLng flagLatLng : result){
            System.out.println(flagLatLng.getLatLngAsString()+",");
        }
    }
}
