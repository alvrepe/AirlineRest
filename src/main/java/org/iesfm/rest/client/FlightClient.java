package org.iesfm.rest.client;

import org.iesfm.rest.Flight;
import org.iesfm.rest.FlightAPI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightClient implements FlightAPI {

    private RestTemplate restTemplate;

    public FlightClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Flight> list(String origin) {
        if (origin == null) {

            Flight[] flights = restTemplate.getForObject(
                    "/flights",
                    Flight[].class
            );
            return Arrays.asList(flights);

        } else {

            Map<String, Object> params = new HashMap<>();
            params.put("origin", origin);
            Flight[] flights = restTemplate.getForObject(
                    "/flights",
                    Flight[].class,
                    params
            );
            return Arrays.asList(flights);

        }
    }

    @Override
    public Flight getFlight(String flightNumber) {
        return restTemplate.getForObject("/flights" + flightNumber, Flight.class);

    }

    @Override
    public ResponseEntity<Void> createFlight(Flight flight) {
        return restTemplate.postForEntity("/flights", flight, Void.class);
         
    }

    @Override
    public void updateFlight(String flightNumber, Flight flight) {
        restTemplate.put("/flights", flightNumber, flight);
    }

    @Override
    public void deleteFlight(String flightNumber) {
        restTemplate.delete("/flights", flightNumber);
    }
}
