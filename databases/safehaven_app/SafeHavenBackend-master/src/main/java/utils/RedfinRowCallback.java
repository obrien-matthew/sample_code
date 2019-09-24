package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import dto.HousingData;
import dto.Redfin;

/**
 * Row callback for database response
 * 
 * @author wilson.v@husky.neu.edu
 */
public class RedfinRowCallback implements RowMapper<HousingData> {

    @Override
    public Redfin mapRow(ResultSet rs, int rowNum) throws SQLException {
        Redfin result = new Redfin();

        result.setStreet(rs.getString("street"));
        result.setPrice(rs.getInt("price"));
        result.setLatitude(rs.getDouble("latitude"));
        result.setLongitude(rs.getDouble("longitude"));
        result.setSquarefeet(rs.getInt("square_feet"));
        result.setBeds(rs.getInt("num_bed"));
        result.setBaths(rs.getInt("num_bath"));
        result.setLocationName(rs.getString("location_name"));
        result.setPpsqft(rs.getInt("ppsqft"));
        result.setLotSize(rs.getInt("lot_size"));
        result.setComments(rs.getString("comments"));
        result.setIsNewConstruction(rs.getString("is_new_construction"));
        result.setYearBuilt(rs.getInt("year_built"));
        result.setPostalCode(rs.getInt("postal_code"));

        return result;
    }

}