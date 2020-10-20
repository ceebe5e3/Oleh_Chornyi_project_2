package chornyi.conferences.db.dao.impl;

import chornyi.conferences.db.dao.UserDao;
import chornyi.conferences.db.entity.Role;
import chornyi.conferences.db.entity.User;
import chornyi.conferences.db.entity.details.UserDetails;
import chornyi.conferences.db.maprow.UserRowMapper;
import chornyi.conferences.db.pool.ConnectionPool;
import chornyi.conferences.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link UserDao} implementation, provided DAO-logic for {@link User}
 * entity with MySQL DataBase
 */

public class UserDaoImpl implements UserDao {

    private static final String INSERT = "INSERT INTO users (user_login, user_password, user_first_nameen_US, user_first_nameuk_UA, user_last_nameen_US, user_last_nameuk_UA, user_email, user_role) VALUES (?,?,?,?,?,?,?,?)";
    private static final String SELECT_BY_ID = "SELECT * FROM users WHERE user_id=?";
    private static final String SELECT_BY_LOGIN = "SELECT * FROM  users WHERE user_login=?";
    private static final String SELECT_ALL = "SELECT * FROM users";
    private static final String SELECT_ALL_SPEAKERS = "SELECT * FROM users WHERE user_role='SPEAKER'";
    private static final String SELECT_ALL_SPEAKERS_ID = "SELECT user_id FROM users WHERE user_role='SPEAKER'";
    private static final String SELECT_ALL_SPEAKERS_PAGINATED = "SELECT * FROM(SELECT * FROM users WHERE user_role='SPEAKER' ORDER BY user_login ASC LIMIT ?, ?) AS c";
    private static final String SELECT_CONFERENCE_ID = "SELECT uc.conference_id FROM users_conferences AS uc JOIN conferences AS c ON c.conference_id = uc.conference_id WHERE user_id=? ORDER BY c.conference_datetime";
    private static final String INSERT_CONFERENCE_REGISTER_USER = "INSERT INTO users_conferences (user_id, conference_id) VALUES (?,?)";
    private static final String DELETE_CONFERENCE_UNREGISTER_USER = "DELETE FROM users_conferences WHERE user_id=? AND conference_id=?";

    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    private DataSource dataSource = ConnectionPool.getDataSource();
    private UserRowMapper userRowMapper = new UserRowMapper();


    @Override
    public boolean add(UserDetails user) throws DaoException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstNameEN());
            preparedStatement.setString(4, user.getFirstNameUA());
            preparedStatement.setString(5, user.getLastNameEN());
            preparedStatement.setString(6, user.getLastNameUA());
            preparedStatement.setString(7, user.getEmail());
            preparedStatement.setString(8, user.getRole().name());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("UserDaoImpl: add: " + e);
            return false;
        }
    }

    @Override
    public User getByLogin(String login, String language) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_LOGIN)) {
            User user = null;
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                user = userRowMapper.mapRow(resultSet, language);
            return user;
        } catch (SQLException e) {
            logger.error("UserDaoImpl: getByLogin: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public Role getUserRole(String login) {
        return getByLogin(login, "en_US").getRole();
    }

    @Override
    public boolean isUserExist(String login) {
        return getByLogin(login, "en_US") != null;
    }

    @Override
    public boolean checkUserPassword(String login, String password) {
        return BCrypt.checkpw(password, getByLogin(login, "en_US").getPassword());
    }

    @Override
    public List<User> getAllSpeakers(String language) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SPEAKERS)) {
            List<User> users = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                users.add(userRowMapper.mapRow(resultSet, language));
            return users;
        } catch (SQLException e) {
            logger.error("UserDaoImpl: getAllSpeakers: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public List<Long> getConferencesIdsForUser(long userId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CONFERENCE_ID)) {
            List<Long> conferencesIds = new ArrayList<>();
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                conferencesIds.add(resultSet.getLong("conference_id"));
            return conferencesIds;
        } catch (SQLException e) {
            logger.error("UserDaoImpl: getConferencesIdsForUser: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public void registerForConference(long userId, long conferenceId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CONFERENCE_REGISTER_USER)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, conferenceId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("UserDaoImpl: registerForConference: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public void unregisterFromConference(long userId, long conferenceId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CONFERENCE_UNREGISTER_USER)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, conferenceId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("UserDaoImpl: unregisterFromConference: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public List<Long> getSpeakersIds() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SPEAKERS_ID)) {
            List<Long> speakersIds = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                speakersIds.add(resultSet.getLong("user_id"));
            return speakersIds;
        } catch (SQLException e) {
            logger.error("UserDaoImpl: getSpeakersIds: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public List<User> getPaginatedSpeakers(Integer begin, Integer recordsPerPage, String language) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SPEAKERS_PAGINATED)) {
            List<User> speakers = new ArrayList<>();
            preparedStatement.setInt(1, begin);
            preparedStatement.setInt(2, recordsPerPage);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                speakers.add(userRowMapper.mapRow(resultSet, language));
            return speakers;
        } catch (SQLException e) {
            logger.error("UserDaoImpl: getPaginatedSpeakers: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public boolean add(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User getById(long id, String language) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            User user = null;
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                user = userRowMapper.mapRow(resultSet, language);
            return user;
        } catch (SQLException e) {
            logger.error("UserDaoImpl: getById: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public List<User> getAll(String language) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            List<User> users = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                users.add(userRowMapper.mapRow(resultSet, language));
            return users;
        } catch (SQLException e) {
            logger.error("UserDaoImpl: getAll: " + e);
            throw new RuntimeException();
        }
    }
}
