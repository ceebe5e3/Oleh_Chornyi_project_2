package chornyi.conferences.db.maprow;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contains logic for mapping ResultSet row to identifiable object
 *
 * @param <T> the type of result object
 */

public interface RowMapper<T> {

    /**
     * Maps ResultSet row to identifiable object
     *
     * @param resultSet with cursor in a row containing object parameters to map
     * @return result
     * @throws SQLException if the columnLabel is not valid; if a database access error
     *                      occurs or this method is called on a closed result set
     */

    T mapRow(ResultSet resultSet, String language)throws SQLException;
}
