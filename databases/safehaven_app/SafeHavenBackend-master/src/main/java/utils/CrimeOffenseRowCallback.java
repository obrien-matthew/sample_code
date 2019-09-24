package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import dto.CrimeData;
import dto.CrimeOffenseData;

/**
 * Row callback for database response
 * 
 * @author obrien.matt@husky.neu.edu
 */
public class CrimeOffenseRowCallback implements RowMapper<CrimeData> {

    @Override
    public CrimeData mapRow(ResultSet rs, int rowNum) throws SQLException {
        CrimeOffenseData result = new CrimeOffenseData();

        result.setOffenseId(rs.getInt("offense_id"));
        result.setShooting(rs.getInt("shooting"));
        result.setDatetime(rs.getString("datetime"));
        result.setLatitude(rs.getDouble("lat"));
        result.setLongitude(rs.getDouble("long"));
        result.setLocation(rs.getInt("location_id"));
        result.setOffenseCode(rs.getString("offense_code"));
        result.setOffenseDesc(rs.getString("offense_desc"));

        return result;
    }
}