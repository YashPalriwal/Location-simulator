package com.palriwal.yash;

import com.palriwal.yash.aggregator.FlagResponseAggregator;
import com.palriwal.yash.dto.*;
import com.palriwal.yash.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.String;
import java.util.*;

@Slf4j
public class LocusTestApplication{
    public static void main(String[] args){
        LocusTestApplication locusTestApplication = new LocusTestApplication();
        FlagResponseAggregator flagResponseAggregator = new FlagResponseAggregator();
        ResponseUtils responseUtils = new ResponseUtils();
        responseUtils.printPretty(flagResponseAggregator.getFlagCoordinateResponse(locusTestApplication.getInputFromConsole()));
    }

    GoogleDirectionsRequest getInputFromConsole(){
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
