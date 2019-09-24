package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import dto.CrimeRateDataByZipCode;

/**
 * Row callback for database response
 * 
 * @author obrien.matt@husky.neu.edu
 */
public class CrimeRateRowCallback implements RowMapper<CrimeRateDataByZipCode> {

    @Override
    public CrimeRateDataByZipCode mapRow(ResultSet rs, int rowNum) throws SQLException {
        CrimeRateDataByZipCode result = new CrimeRateDataByZipCode(rs.getInt("postal_code"), rs.getDouble("count"));
        return result;
    }
}