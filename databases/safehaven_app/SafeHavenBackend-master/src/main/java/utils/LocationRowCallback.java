package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import dto.LocationDetailCenter;
import dto.LocationDetails;

/**
 * Row callback for database response
 * 
 * @author wilson.v@husky.neu.edu
 */
public class LocationRowCallback implements RowMapper<LocationDetails> {

    @Override
    public LocationDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        LocationDetailCenter center = new LocationDetailCenter(rs.getDouble("latitude"), rs.getDouble("longitude"));
        LocationDetails loc = new LocationDetails(rs.getString("location_name"), center);
        return loc;
    }

}