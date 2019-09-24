package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import dto.Airbnb;
import dto.HousingData;

/**
 * Row callback for database response
 * 
 * @author wilson.v@husky.neu.edu
 */
public class AirbnbRowCallback implements RowMapper<HousingData> {

    @Override
    public Airbnb mapRow(ResultSet rs, int rowNum) throws SQLException {
        Airbnb result = new Airbnb();

        result.setPricePerNight(rs.getInt("price_per_night"));
        result.setRatingScore(rs.getInt("rating_score"));
        result.setReviewsPerMonth(rs.getInt("reviews_per_month"));
        result.setLatitude(rs.getDouble("latitude"));
        result.setLongitude(rs.getDouble("longitude"));
        result.setHostIsSuperHost(rs.getBoolean("host_is_superhost"));
        result.setRoomType(rs.getString("room_type"));
        result.setBeds(rs.getInt("num_bed"));
        result.setBaths(rs.getInt("num_bed"));
        result.setLocationName(rs.getString("location_name"));

        return result;
    }

}