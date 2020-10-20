package chornyi.conferences.db.maprow;

import chornyi.conferences.db.entity.Role;
import chornyi.conferences.db.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    private static final String ID = "user_id";
    private static final String LOGIN = "user_login";
    private static final String FIRST_NAME = "user_first_name";
    private static final String LAST_NAME = "user_last_name";
    private static final String PASSWORD = "user_password";
    private static final String EMAIL = "user_email";
    private static final String ROLE = "user_role";

    @Override
    public User mapRow(ResultSet resultSet, String language) throws SQLException {
       return new User.Builder()
               .setId(resultSet.getLong(ID))
               .setLogin(resultSet.getString(LOGIN))
               .setFirstName(resultSet.getString(FIRST_NAME + language))
               .setLastName(resultSet.getString(LAST_NAME + language))
               .setPassword(resultSet.getString(PASSWORD))
               .setEmail(resultSet.getString(EMAIL))
               .setRole(Role.valueOf(resultSet.getString(ROLE)))
               .build();
    }
}
