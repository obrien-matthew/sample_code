package dao;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import dto.CrimeData;
import dto.CrimeDataRequest;
import dto.CrimeRateData;
import dto.CrimeStatistics;
import dto.CrimeRateDataByZipCode;
import dto.HousingData;
import dto.HousingListRequest;
import dto.HousingStatistics;
import dto.LocationDetails;
import utils.AirbnbRowCallback;
import utils.ApartmentsComCallback;
import utils.CrimeOffenseRowCallback;
import utils.CrimeRateRowCallback;
import utils.CrimeRowCallback;
import utils.CrimeStatisticsCallback;
import utils.DefaultPSSetter;
import utils.HousingStatisticsCallback;
import utils.LocationRowCallback;
import utils.RedfinRowCallback;

/**
 * Data Access Class for MySQL database
 * 
 * @author Wilson.v@husky.neu.edu
 * @author obrien.matt@husky.neu.edu
 */
@Repository
public class DatabaseDAO implements DatabaseDAOInt {
    private static Logger logger = LogManager.getLogger(DatabaseDAO.class);
    private JdbcTemplate jdbcTemplate;
    // request types for housing data 
    private final String AIRBNB = "airbnb";
    private final String APARTMENT = "lease";
    private final String REDFIN = "buy";

    @Autowired
    private void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<LocationDetails> getLocationDetails(int zipCode) {
        logger.debug("getting location details from MySQL for zipcode : " + zipCode);
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(zipCode);
        String query = "select * from location where postal_code = ?";
        return jdbcTemplate.query(query, new DefaultPSSetter(params), new LocationRowCallback());
    }

    public List<HousingData> getHousingList(HousingListRequest request) {
        logger.debug("getting housingList details from MySQL for request : " + request);

        String querySuffix = "";
        String queryPrefix = null;
        List<HousingData> result = new ArrayList<HousingData>();

        ArrayList<String> sb = new ArrayList<String>();
        ArrayList<Object> params = new ArrayList<Object>();
        if (request.getBeds() != null) {
            params.add(request.getBeds());
            sb.add("num_bed >= ?");
        }
        if (request.getBaths() != null) {
            params.add(request.getBaths());
            switch (request.getType()) {
            case AIRBNB:
                sb.add("num_bat >= ?");
                break;

            case REDFIN:
            case APARTMENT:
                sb.add("num_bath >= ?");
                break;

            }
        }
        if (request.getPostalCode() != null) {
            params.add(request.getPostalCode());
            sb.add("l.postal_code = ?");
        }
        if (request.getPriceRange() != null) {
            params.add(request.getPriceRange());
            switch (request.getType()) {

            case AIRBNB:
                sb.add("price_per_night <= ?");
                break;

            case APARTMENT:
                sb.add("rent <= ?");
                break;

            case REDFIN:
                sb.add("price <= ?");
                break;

            }

        }
        if (request.getStartDate() != null) {
            params.add(request.getStartDate());
            sb.add("date <= ?");
        }
        if (request.getEndDate() != null) {
            params.add(request.getEndDate());
            sb.add("date >= ?");
        }

        if (!sb.isEmpty()) {
            querySuffix = "where " + String.join(" and ", sb);
        }

        try {
            switch (request.getType()) {

            case AIRBNB:
                queryPrefix = "select * from airbnbListing as a join location as l using(location_id) ";
                logger.debug("QUERY : " + queryPrefix + querySuffix);
                result = jdbcTemplate.query(queryPrefix + querySuffix, new DefaultPSSetter(params),
                        new AirbnbRowCallback());
                break;

            case APARTMENT:
                queryPrefix = "select * from apartmentsHouseListing as a join location as l using(location_id) ";
                logger.debug("QUERY : " + queryPrefix + querySuffix);
                result = jdbcTemplate.query(queryPrefix + querySuffix, new DefaultPSSetter(params),
                        new ApartmentsComCallback());
                break;

            case REDFIN:
                queryPrefix = "select * from redfinHouseListing as a join location as l using(location_id) ";
                logger.debug("QUERY : " + queryPrefix + querySuffix);
                result = jdbcTemplate.query(queryPrefix + querySuffix, new DefaultPSSetter(params),
                        new RedfinRowCallback());
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public CrimeRateData getCrimeRateData() {
        logger.debug("getting overall crime rate data from mysql");
        List<CrimeRateDataByZipCode> query = jdbcTemplate.query(
                "select postal_code, count(*) as count from crimes join location using(location_id) group by postal_code",
                new CrimeRateRowCallback());
        return new CrimeRateData(query);
    }

    public List<CrimeData> getCrimeData(CrimeDataRequest request) {
        logger.debug("getting crimedata details from MySQL for request : " + request);

        String querySuffix = "";
        String queryPrefix = null;
        List<CrimeData> result = new ArrayList<CrimeData>();

        ArrayList<String> sb = new ArrayList<String>();
        ArrayList<Object> params = new ArrayList<Object>();

        if (request.getOffenseId() != null) {
            params.add(request.getOffenseId());
            sb.add("a.offense_id = ?");
        }

        if (request.getShooting()) {
            params.add(request.getShooting());
            sb.add("a.shooting = ?");
        }

        if (request.getDatetime() != null) {
            params.add(request.getDatetime());
            sb.add("a.datetime = ?");
        }

        if (request.getLatitude() != null) {
            params.add(request.getLatitude());
            sb.add("a.lat = ?");
        }

        if (request.getLongitude() != null) {
            params.add(request.getLongitude());
            sb.add("a.long = ?");
        }

        if (request.getLocation() != null) {
            params.add(request.getLocation());
            sb.add("a.location_id = ?");
        }

        if (request.getOffenseCode() != null) {
            params.add(request.getOffenseCode());
            sb.add("b.offense_code = ?");
        }

        if (request.getOffenseDesc() != null) {
            params.add(request.getOffenseDesc());
            sb.add("b.offense_desc = ?");
        }

        if (!sb.isEmpty()) {
            querySuffix = "where " + String.join(" and ", sb);
        }

        try {
            if (request.isFull()) {
                queryPrefix = "select * from crimes as a join offenseInfo as b ";
                logger.debug("QUERY : " + queryPrefix + querySuffix);
                result = jdbcTemplate.query(queryPrefix + querySuffix, new DefaultPSSetter(params),
                        new CrimeOffenseRowCallback());
            } else {
                queryPrefix = "select * from crimes as a ";
                logger.debug("QUERY : " + queryPrefix + querySuffix);
                result = jdbcTemplate.query(queryPrefix + querySuffix, new DefaultPSSetter(params),
                        new CrimeRowCallback());

            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<HousingStatistics> getHousingStatistics(Integer zipCode) {
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(zipCode);
        String query = "select l.postal_code, l.location_name, avg(price_per_night) as airbnb_price, avg(rent) as apartment_rent, avg(price) as redfin_price from location as l left join airbnbListing using(location_id) left join apartmentsHouseListing using(location_id) left join redfinHouseListing using(location_id) where l.postal_code = ?";
        return jdbcTemplate.query(query, new DefaultPSSetter(params), new HousingStatisticsCallback());
    }

    public List<HousingStatistics> getHousingStatistics() {
        String query = "select l.postal_code, l.location_name, avg(price_per_night) as airbnb_price , avg(rent) as apartment_rent, avg(price) as redfin_price from location as l left join airbnbListing using(location_id) left join apartmentsHouseListing using(location_id) left join redfinHouseListing using(location_id) group by l.postal_code";
        return jdbcTemplate.query(query, new HousingStatisticsCallback());
    }

    public List<CrimeStatistics> getCrimeStatistics(Integer zipCode) {
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(zipCode);
        String query = "select o.offense_code, count(*) as count from crimes as c left join offenseInfo as o using (offense_id) left join location as l using (location_id) where l.postal_code = ? group by o.offense_code order by count desc limit 5";
        return jdbcTemplate.query(query, new DefaultPSSetter(params), new CrimeStatisticsCallback());
    }

    public List<CrimeStatistics> getCrimeStatistics() {
        String query = "select offense_code, count(*) as count from crimes as c left join offenseInfo as o using (offense_id) group by o.offense_code order by count desc limit 5";
        return jdbcTemplate.query(query, new CrimeStatisticsCallback());
    }
}
