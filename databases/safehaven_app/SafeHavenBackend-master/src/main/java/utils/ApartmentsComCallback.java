package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import dto.ApartmentsCom;
import dto.HousingData;

/**
 * Row callback for database response
 * 
 * @author wilson.v@husky.neu.edu
 */
public class ApartmentsComCallback implements RowMapper<HousingData> {

    @Override
    public ApartmentsCom mapRow(ResultSet rs, int rowNum) throws SQLException {
        ApartmentsCom result = new ApartmentsCom();

        result.setStreet(rs.getString("street"));
        result.setRent(rs.getInt("rent"));
        result.setLatitude(rs.getDouble("latitude"));
        result.setLongitude(rs.getDouble("longitude"));
        result.setBeds(rs.getInt("num_bed"));
        result.setSqft(rs.getInt("sqrft"));
        result.setBaths(rs.getInt("num_bath"));
        result.setLeaseInfo(rs.getString("lease_info"));
        result.setLocationName(rs.getString("location_name"));
        result.setPostalCode(rs.getInt("postal_code"));

        return result;
    }

}