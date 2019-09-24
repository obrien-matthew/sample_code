package dao;

import java.util.List;

import dto.CrimeData;
import dto.CrimeDataRequest;
import dto.CrimeRateData;
import dto.CrimeStatistics;
import dto.HousingData;
import dto.HousingListRequest;
import dto.HousingStatistics;
import dto.LocationDetails;


/**
 * DAO interface for DB access
 * 
 * @author wilson.v@husky.neu.edu
 * @author obrien.matt@husky.neu.edu
 */
public interface DatabaseDAOInt{
    // get location details from DB using zipcode 
    public List<LocationDetails> getLocationDetails(int zipCode);

    // get location information based on the request query
    public List<HousingData> getHousingList(HousingListRequest request);

    // get crime count grouped by zipcode
    public CrimeRateData getCrimeRateData();

    // get crime information based on the request query
    public List<CrimeData> getCrimeData(CrimeDataRequest request);

    // get average housing prices of different types of housing for the given zipcode
    public List<HousingStatistics> getHousingStatistics(Integer zipcode);

    // get average housing prices of different types of housing grouped by zipcode
    public List<HousingStatistics> getHousingStatistics();

    // get crime statictics for the given zipcode
    public List<CrimeStatistics> getCrimeStatistics(Integer zipcode);

    // get crime statistics grouped by zipcode
    public List<CrimeStatistics> getCrimeStatistics();
}