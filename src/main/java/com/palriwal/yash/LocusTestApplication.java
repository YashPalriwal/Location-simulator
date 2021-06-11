package com.palriwal.yash;

import com.palriwal.yash.dto.GoogleDirectionsRequest;
import com.palriwal.yash.dto.GoogleDirectionsResponse;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

public class LocusTestApplication{
    public static void main(String[] args) throws Exception{

    }

    public GoogleDirectionsResponse getGoogleDirectionsResponse(GoogleDirectionsRequest request){
        MultivaluedMap<String, String> queryParams = new MultivaluedHashMap<>();
        queryParams.add("key", "AIzaSyAEQvKUVouPDENLkQlCF6AAap1Ze-6zMos");
        queryParams.add("origin")
    }









}
