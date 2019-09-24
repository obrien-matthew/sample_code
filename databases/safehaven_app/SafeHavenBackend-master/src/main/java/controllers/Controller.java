package controllers;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dto.LocationDetails;
import dto.HousingData;
import dto.CrimeData;
import dto.CrimeDataRequest;
import dto.CrimeRateData;
import dto.CrimeStatistics;
import dto.HousingListRequest;
import dto.HousingStatistics;
import services.DatabaseService;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Rest API Controller, Handle all endpoints provided by the backend 
 * 
 * @author wilson.v@husky.neu.edu
 * @author obrien.matt@husky.neu.edu
 */

@RestController
public class Controller {

    @Autowired
    private DatabaseService dbservice;

    Logger logger = LogManager.getLogger(Controller.class);

    @RequestMapping(value = "/locationdetails", method = RequestMethod.GET)
    public ResponseEntity<String> getZip(@RequestParam int zipcode) {
        LocationDetails location = dbservice.getLocationDetails(zipcode);
        ObjectMapper mapper = new ObjectMapper();
        String responseMessage = "{}";

        try {
            responseMessage = mapper.writeValueAsString(location);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        logger.debug("Sending : " + responseMessage);

        return ResponseEntity.ok().body(responseMessage);
    }

    @RequestMapping(value = "/housinglist", method = RequestMethod.POST)
    public ResponseEntity<String> housingList(@RequestBody HousingListRequest request) {
        List<HousingData> data = dbservice.getHousingList(request);
        ObjectMapper mapper = new ObjectMapper();
        String responseMessage = "{}";

        try {
            responseMessage = mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(responseMessage);
    }

    @RequestMapping(value = "/crimedata", method = RequestMethod.POST)
    public ResponseEntity<String> crimeData(@RequestBody CrimeDataRequest request) {
        List<CrimeData> data = dbservice.getCrimeData(request);
        ObjectMapper mapper = new ObjectMapper();
        String responseMessage = "{}";

        try {
            responseMessage = mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(responseMessage);
    }

    @RequestMapping(value = "/crimerates", method = RequestMethod.GET)
    public ResponseEntity<String> getCrimeRates() {
        CrimeRateData crimeRates = dbservice.getCrimeRateData();
        ObjectMapper mapper = new ObjectMapper();
        String responseMessage = "{}";

        try {
            responseMessage = mapper.writeValueAsString(crimeRates);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        logger.debug("Sending : " + responseMessage);
        return ResponseEntity.ok().body(responseMessage);
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public ResponseEntity<String> getStatistics(@RequestParam Optional<Integer> zipcode) {
        ObjectMapper mapper = new ObjectMapper();
        String responseMessage = "{}";

        try {
            if (!zipcode.isPresent()) {
                logger.debug("ZIPCODE is not Provided");
                List<HousingStatistics> result = dbservice.getHousingStatistics(null);
                responseMessage = mapper.writeValueAsString(result);
            } else {
                logger.debug("ZIPCODE provided : " + zipcode.get());
                HousingStatistics result = dbservice.getHousingStatistics(zipcode.get()).get(0);
                responseMessage = mapper.writeValueAsString(result);
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        logger.debug("Sending : " + responseMessage);
        return ResponseEntity.ok().body(responseMessage);
    }

    @RequestMapping(value = "/crime-statistics", method = RequestMethod.GET)
    public ResponseEntity<String> getCrimeStatistics(@RequestParam Optional<Integer> zipcode) {
        ObjectMapper mapper = new ObjectMapper();
        String responseMessage = "{}";

        try {
            if (!zipcode.isPresent()) {
                logger.debug("ZIPCODE is not Provided");
                List<CrimeStatistics> result = dbservice.getCrimeStatistics(null);
                responseMessage = mapper.writeValueAsString(result);
            } else {
                logger.debug("ZIPCODE provided : " + zipcode.get());
                List<CrimeStatistics> result = dbservice.getCrimeStatistics(zipcode.get());
                responseMessage = mapper.writeValueAsString(result);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        logger.debug("Sending : " + responseMessage);
        return ResponseEntity.ok().body(responseMessage);
    }
}