package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import dto.HousingStatistics;

/**
 * Row callback for database response
 * 
 * @author wilson.v@husky.neu.edu
 */
public class HousingStatisticsCallback implements RowMapper<HousingStatistics> {

    @Override
    public HousingStatistics mapRow(ResultSet rs, int rowNum) throws SQLException {
        HousingStatistics stat = new HousingStatistics();
        stat.setZipCode(rs.getInt("postal_code"));
        stat.setLocationName(rs.getString("location_name"));
        stat.setAvgAirbnbPerNight(rs.getDouble("airbnb_price"));
        stat.setAvgRentPerMonth(rs.getDouble("apartment_rent"));
        stat.setAvgPriceToBuy(rs.getDouble("redfin_price"));
        return stat;
    }

}