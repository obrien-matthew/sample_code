package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import dto.CrimeStatistics;

/**
 * Row callback for database response
 * 
 * @author obrien.matt@husky.neu.edu
 */
public class CrimeStatisticsCallback implements RowMapper<CrimeStatistics> {

    @Override
    public CrimeStatistics mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CrimeStatistics(rs.getString("offense_code"), rs.getInt("count"));
    }
}