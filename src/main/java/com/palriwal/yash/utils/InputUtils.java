package com.palriwal.yash.utils;

import com.palriwal.yash.dto.GoogleDirectionsRequest;
import com.palriwal.yash.dto.LatLng;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class InputUtils {

    public static GoogleDirectionsRequest getInputFromConsole(){
        LatLng origin = new LatLng();
        LatLng destination = new LatLng();
        log.debug("Started Application...");
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("Enter Origin latitude : ");
            origin.setLatitude(in.nextDouble());
            System.out.print("Enter Origin longitude : ");
            origin.setLongitude(in.nextDouble());

            System.out.print("Enter Destination latitude : ");
            destination.setLatitude(in.nextDouble());
            System.out.print("Enter Destination longitude : ");
            destination.setLongitude(in.nextDouble());
        }catch(Exception e){
            System.out.println("Please enter a valid latitude/longitude! Aborting..");
            return null;
        }

        try {
            GoogleDirectionsRequest googleDirectionsRequest = GoogleDirectionsRequest.builder().origin(origin).destination(destination).build();
            return googleDirectionsRequest;
        }catch(Exception e){
            log.error("Bad Request");
        }
        return null;
    }
}
