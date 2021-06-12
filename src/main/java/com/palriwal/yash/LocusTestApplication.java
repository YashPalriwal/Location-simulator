package com.palriwal.yash;

import com.google.maps.internal.PolylineEncoding;
import com.palriwal.yash.dto.*;
import java.lang.String;
import javax.ws.rs.client.Client;
import java.util.*;
import javax.ws.rs.client.ClientBuilder;

import static java.lang.Math.*;
import static java.lang.StrictMath.asin;
import static java.lang.StrictMath.pow;

public class LocusTestApplication{
    public static void main(String[] args) throws Exception{
        LocusTestApplication locusTestApplication = new LocusTestApplication();
        LatLng origin = new LatLng();
        LatLng destination = new LatLng();

        Scanner in = new Scanner(System.in);
        System.out.print("Enter Origin latitude : ");
        origin.setLatitude(in.nextDouble());
        System.out.print("Enter Origin longitude : ");
        origin.setLongitude(in.nextDouble());

        System.out.print("Enter Destination latitude : ");
        destination.setLatitude(in.nextDouble());
        System.out.print("Enter Destination longitude : ");
        destination.setLongitude(in.nextDouble());

        GoogleDirectionsRequest googleDirectionsRequest = GoogleDirectionsRequest.builder().origin(origin).destination(destination).build();
        locusTestApplication.getGoogleDirectionsResponse(googleDirectionsRequest);
    }

    public String createDirectionsRequestUrl(GoogleDirectionsRequest request){
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("key", "AIzaSyAEQvKUVouPDENLkQlCF6AAap1Ze-6zMos");
        queryParams.put("origin", request.getOrigin().getLatLngAsString());
        queryParams.put("destination", request.getDestination().getLatLngAsString());

        String baseUrl = "https://maps.googleapis.com";
        String endPoint = "/maps/api/directions/json";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(baseUrl).append(endPoint);

        final Boolean[] firstElement = {true};
        queryParams.forEach((key,val) -> {
            if(firstElement[0]){
                stringBuilder.append("?").append(key).append("=").append(val);
                firstElement[0] = false;
            }
            else
                stringBuilder.append("&").append(key).append("=").append(val);
        });
        return stringBuilder.toString();
    }

    public List<com.google.maps.model.LatLng> getAllWaypointsFromSteps(List<Steps> steps){
        List<com.google.maps.model.LatLng> wayPoints = new ArrayList<>();
        for(Steps step : steps){
            wayPoints.addAll(PolylineEncoding.decode(step.getPolyline().getPoints()));
        }
//        for(com.google.maps.model.LatLng latLng : wayPoints){
//            System.out.println(latLng.lat+","+latLng.lng+",");
//        }
        return wayPoints;
    }

    public List<LatLng> getFlagLatLngsOnPath(List<Steps> steps, LatLng startPoint, LatLng endPoint, Long flagDistance){
        if(Objects.isNull(steps) || steps.isEmpty())
            return null;
        List<LatLng> flagLocations = new ArrayList<>();

        Queue<com.google.maps.model.LatLng> waypointsQueue = new LinkedList<>();
        waypointsQueue.addAll(getAllWaypointsFromSteps(steps));
        LatLng prevFlagPoint = startPoint;
        LatLng currentWayPoint = new LatLng();
        Long distance = 0L;
        while(!waypointsQueue.isEmpty()){
            com.google.maps.model.LatLng tempPoint = waypointsQueue.poll();
            currentWayPoint.setLatitude(tempPoint.lat);
            currentWayPoint.setLongitude(tempPoint.lng);
            distance = Math.round(haversineDistance(prevFlagPoint.getLatitude(),prevFlagPoint.getLongitude(), currentWayPoint.getLatitude(),currentWayPoint.getLongitude()));
            if(distance >= flagDistance){

           /*    start ->  prevFlagPoint
                 end -> currentWayPoint
                 startToEndDistance -> haversineDistance --> distance
                 FlagDistance
                    --> return a list of flagPoints and update prevFlagPoint
            */
                Long numberOfSteps = distance/flagDistance;
                while(numberOfSteps > 0){
                    LatLng flagCoordinate = getFlagLatLngOnStep(prevFlagPoint, currentWayPoint, flagDistance, distance);
                    prevFlagPoint = flagCoordinate;
                    distance = distance - flagDistance;
                    flagLocations.add(flagCoordinate);
                    numberOfSteps--;
                }

            }
        }
        return flagLocations;
    }

//    public LatLng snapToNearestRoadPoint(LatLng point, List<com.google.maps.model.LatLng> polylinePointsOnStep){
//        LatLng result = new LatLng();
//        Double minDistance = Double.MAX_VALUE;
//        if(Objects.isNull(polylinePointsOnStep) || polylinePointsOnStep.isEmpty())
//            return null;
//        for(com.google.maps.model.LatLng roadPoint : polylinePointsOnStep){
//            Double approxDistance = haversineDistance(roadPoint.lat, roadPoint.lng, point.getLatitude(), point.getLongitude());
//            if(Double.compare( approxDistance, minDistance) < 0){
//                result.setLatitude(roadPoint.lat);
//                result.setLongitude(roadPoint.lng);
//                minDistance = approxDistance;
//            }
//        }
//        return result;
//    }

    public Double haversineDistance(Double lat1, Double lon1, Double lat2, Double lon2){
        {
            // distance between latitudes
            // and longitudes
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
            return rad * c * 1000;
        }
    }

    public LatLng getFlagLatLngOnStep(LatLng startPoint, LatLng endPoint, Long distanceFromStart, Long stepDistance){
        LatLng flagCoordinates = new LatLng();
        Long distanceFromEnd = stepDistance - distanceFromStart;
        if(!(Double.compare(stepDistance, 0.0) > 0))
            return null;
        /*
        *   x_ = (m2*x1 + m1*x2)/(m1+m2);
         */
        Double flagLatitude = ((startPoint.getLatitude()*distanceFromEnd) + (endPoint.getLatitude()*distanceFromStart))/stepDistance;
        Double flagLongitude = ((startPoint.getLongitude()*distanceFromEnd) + (endPoint.getLongitude()*distanceFromStart))/stepDistance;
        flagCoordinates.setLatitude(flagLatitude);
        flagCoordinates.setLongitude(flagLongitude);
        return flagCoordinates;
    }

    public void printPretty(List<LatLng> result){
        if(Objects.isNull(result) || result.isEmpty())
            return;
        for(LatLng flagLatLng : result){
            System.out.println(flagLatLng.getLatLngAsString()+",");
        }
    }

    public void getGoogleDirectionsResponse(GoogleDirectionsRequest request){

        String requestUrl = createDirectionsRequestUrl(request);
        System.out.println("Url : "+requestUrl);
        Client client = ClientBuilder.newClient();
        try {
            GoogleDirectionsResponse response = client.target(requestUrl).request().get(GoogleDirectionsResponse.class);
            // For two points
            List<LatLng> result = getFlagLatLngsOnPath(response.getRoutes().get(0).getLegs().get(0).getSteps(), request.getOrigin(), request.getDestination(), 10000L);
            printPretty(result);
        }catch(Exception e){
            System.out.println("Exception caught :: "+e.getMessage() + e.getStackTrace());
            return;
        }
    }









}
