package utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.PreparedStatementSetter;

/**
 * DafaultPSSetter takes a List of parameters in the order in which they have to
 * be substituted in the prepared statement and substitutes it.
 * 
 * @author wilson.v@husky.neu.edu
 */
public class DefaultPSSetter implements PreparedStatementSetter {

    private List<Object> params;

    public DefaultPSSetter(List<Object> params) {
        this.params = params;
    }

    @Override
    public void setValues(PreparedStatement ps) throws SQLException {

        int ctr = 1;
        for (Object p : params) {
            ps.setObject(ctr, p);
            ctr++;
        }
    }

}