package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import dto.CrimeData;

/**
 * Row callback for database response
 * 
 * @author obrien.matt@husky.neu.edu
 */
public class CrimeRowCallback implements RowMapper<CrimeData> {

    @Override
    public CrimeData mapRow(ResultSet rs, int rowNum) throws SQLException {
        CrimeData result = new CrimeData();

        result.setOffenseId(rs.getInt("offense_id"));
        result.setShooting(rs.getInt("shooting"));
        result.setDatetime(rs.getString("datetime"));
        result.setLatitude(rs.getDouble("lat"));
        result.setLongitude(rs.getDouble("long"));
        result.setLocation(rs.getInt("location_id"));

        return result;
    }
}