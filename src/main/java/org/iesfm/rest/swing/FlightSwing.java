
package org.iesfm.rest.swing;

import org.iesfm.rest.Flight;
import org.iesfm.rest.FlightAPI;
import org.iesfm.rest.client.FlightClient;
import org.iesfm.rest.exceptions.ErrorHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.*;
import java.util.List;

public class FlightSwing {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Aerolínea");
        JPanel panel = new JPanel();

        FlightClient flightAPI = new FlightClient(
                new RestTemplateBuilder()
                        .errorHandler(new ErrorHandler())
                        .rootUri("http://localhost:8080")
                        .build()

        );

        List<Flight> flights = flightAPI.list(null);
        for(Flight flight: flights) {
            panel.add(new JLabel(flight.toString()));
        }

        try{
            Flight flight = flightAPI.getFlight("no existe");
        } catch (HttpClientErrorException e){
            JOptionPane.showMessageDialog(frame, "No se ha encontrado el vuelo");
        }

        ResponseEntity<Void> response = flightAPI.createFlight(new Flight("2312", "Madrid", "Budapest"));
        if (response.getStatusCodeValue() == HttpStatus.CREATED.value()){
            JOptionPane.showMessageDialog(frame, "Se ha creado el vuelo");
        } else if (response.getStatusCodeValue() == HttpStatus.CONFLICT.value()){
            JOptionPane.showMessageDialog(frame, "Ya existía el vuelo");
        }

        flightAPI.deleteFlight("56465");

        frame.add(panel);
        frame.setVisible(true);
        frame.setBounds(0, 0, 600, 600);
    }
}