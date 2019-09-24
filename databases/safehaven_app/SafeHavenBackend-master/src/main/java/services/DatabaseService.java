package services;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.DatabaseDAO;
import dto.CrimeRateData;
import dto.CrimeRateDataByZipCode;
import dto.CrimeData;
import dto.CrimeDataRequest;
import dto.CrimeStatistics;
import dto.HousingData;
import dto.HousingListRequest;
import dto.HousingStatistics;
import dto.LocationDetails;

/**
 * database service : business logic layer over the database
 * 
 * @author wilson.v@husky.neu.edu
 * @author obrien.matt@husky.neu.edu
 */
@Service("databaseService")
public class DatabaseService {
    private static Logger logger = LogManager.getLogger(DatabaseService.class);

    @Autowired
    DatabaseDAO dataBaseDAO;

    public LocationDetails getLocationDetails(int zipcode) {
        logger.debug("Recieved getLocationDetails request : " + zipcode);
        List<LocationDetails> location = dataBaseDAO.getLocationDetails(zipcode);
        if (location != null && location.size() >= 1) {
            return location.get(0);
        }
        return new LocationDetails();
    }

    public CrimeRateData getCrimeRateData() {
        CrimeRateData crimerate = dataBaseDAO.getCrimeRateData();
        logger.debug("Database service triggered, sending " + crimerate);
        if (crimerate != null) {
            return crimerate;
        }
        return new CrimeRateData(new ArrayList<CrimeRateDataByZipCode>());
    }

    public List<CrimeData> getCrimeData(CrimeDataRequest request) {
        logger.debug("Recieved CrimeData request : " + request);
        return dataBaseDAO.getCrimeData(request);
    }

    public List<HousingData> getHousingList(HousingListRequest request) {
        logger.debug("Recieved getHousingList request : " + request);
        return dataBaseDAO.getHousingList(request);
    }

    public List<HousingStatistics> getHousingStatistics(Integer zipcode) {
        logger.debug("Recieved getHousingStatistics request : " + zipcode);
        if (zipcode == null) {
            return dataBaseDAO.getHousingStatistics();
        }
        return dataBaseDAO.getHousingStatistics(zipcode);
    }

    public List<CrimeStatistics> getCrimeStatistics(Integer zipcode) {
        logger.debug("Recieved getCrimeStatistics request : " + zipcode);
        if (zipcode == null) {
            return dataBaseDAO.getCrimeStatistics();
        }
        return dataBaseDAO.getCrimeStatistics(zipcode);
    }
}
