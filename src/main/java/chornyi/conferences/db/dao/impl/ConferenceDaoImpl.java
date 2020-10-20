package chornyi.conferences.db.dao.impl;

import chornyi.conferences.db.dao.ConferenceDao;
import chornyi.conferences.db.entity.Conference;
import chornyi.conferences.db.entity.details.ConferenceDetails;
import chornyi.conferences.db.maprow.ConferenceRowMapper;
import chornyi.conferences.db.maprow.RowMapper;
import chornyi.conferences.db.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * {@link ConferenceDao} implementation, provided DAO-logic for {@link Conference}
 * entity with MySQL DataBase
 */

public class ConferenceDaoImpl implements ConferenceDao {

    private static final String INSERT = "INSERT INTO conferences (conference_nameen_US, conference_nameuk_UA, conference_datetime, conference_locationen_US, conference_locationuk_UA)  VALUES (?,?,?,?,?)";
    private static final String UPDATE = "UPDATE conferences SET conference_nameen_US=?, conference_nameuk_UA=?, conference_datetime=?, conference_locationen_US=?, conference_locationuk_UA=? WHERE conference_id=?";
    private static final String DELETE = "DELETE FROM conferences WHERE conference_id=?";
    private static final String SELECT_BY_ID = "SELECT * FROM conferences WHERE conferences.conference_id=?";
    private static final String SELECT_ALL = "SELECT * FROM conferences ORDER BY conference_datetime ASC";
    private static final String SELECT_ALL_PAST = "SELECT * FROM conferences WHERE conference_datetime < NOW()";
    private static final String SELECT_ALL_UPCOMING = "SELECT * FROM conferences WHERE conference_datetime >= NOW()";
    private static final String SELECT_PAGINATED = "SELECT * FROM(SELECT * FROM conferences ORDER BY conference_datetime ASC limit ?, ?) AS c";
    private static final String COUNT = "SELECT COUNT(conference_id) FROM conferences";
    private static final String COUNT_PAST = "SELECT COUNT(conference_id) FROM conferences WHERE conference_datetime < NOW()";
    private static final String SELECT_PAGINATED_PAST = "SELECT * FROM(SELECT * FROM conferences WHERE conference_datetime < NOW() ORDER BY conference_datetime ASC limit ?, ?) AS c";
    private static final String COUNT_UPCOMING = "SELECT COUNT(conference_id) FROM conferences WHERE conference_datetime >= NOW()";
    private static final String SELECT_PAGINATED_UPCOMING = "SELECT * FROM(SELECT * FROM conferences WHERE conference_datetime >= NOW() ORDER BY conference_datetime ASC limit ?, ?) AS c";
    private static final String SELECT_CONVERSATION_COUNT = "SELECT c.conference_id AS conference_id, COUNT(concof.conversation_id) AS conversation_count FROM conferences AS c JOIN conversations_conferences concof ON c.conference_id = concof.conference_id GROUP BY c.conference_id";
    private static final String SELECT_USER_COUNT = "SELECT c.conference_id AS conference_id, COUNT(uc.user_id) AS user_count FROM conferences AS c JOIN users_conferences uc ON c.conference_id = uc.conference_id GROUP BY c.conference_id";
    private static final String SELECT_SPEAKER_COUNT = "SELECT COUNT(*) AS speaker_count FROM (SELECT speaker_id from conversations WHERE conversation_id IN (SELECT conversation_id FROM conversations_conferences WHERE conference_id=?) GROUP BY speaker_id) AS records";

    private final static Logger logger = LogManager.getLogger(ConferenceDaoImpl.class);

    private DataSource dataSource = ConnectionPool.getDataSource();
    private RowMapper<Conference> conferenceRowMapper = new ConferenceRowMapper();

    @Override
    public boolean add(ConferenceDetails conference) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            preparedStatement.setString(1, conference.getNameEN());
            preparedStatement.setString(2, conference.getNameUA());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(conference.getDateTime()));
            preparedStatement.setString(4, conference.getLocationEN());
            preparedStatement.setString(5, conference.getLocationUA());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("ConferenceDaoImpl: add: " + e);
        }
        return false;
    }

    @Override
    public void update(ConferenceDetails conference) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, conference.getNameEN());
            preparedStatement.setString(2, conference.getNameUA());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(conference.getDateTime()));
            preparedStatement.setString(4, conference.getLocationEN());
            preparedStatement.setString(5, conference.getLocationUA());
            preparedStatement.setLong(6, conference.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("ConferenceDaoImpl: update: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public ConferenceDetails getConferenceDetailsById(long conferenceId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            ConferenceDetails conferenceDetails = null;
            preparedStatement.setLong(1, conferenceId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                conferenceDetails = new ConferenceRowMapper().mapRowDetails(resultSet);
            return conferenceDetails;
        } catch (SQLException e) {
            logger.error("ConferenceDaoImpl: getConferenceDetailsById: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public List<Conference> getAll(String language) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            List<Conference> conferences = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                conferences.add(conferenceRowMapper.mapRow(resultSet, language));
            return conferences;
        } catch (SQLException e) {
            logger.error("ConferenceDaoImpl: getAll: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public List<Conference> getAllPast(String language) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PAST)) {
            List<Conference> conferences = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                conferences.add(conferenceRowMapper.mapRow(resultSet, language));
            return conferences;
        } catch (SQLException e) {
            logger.error("ConferenceDaoImpl: getAllPast: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public List<Conference> getAllUpcoming(String language) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_UPCOMING)) {
            List<Conference> conferences = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                conferences.add(conferenceRowMapper.mapRow(resultSet, language));
            return conferences;
        } catch (SQLException e) {
            logger.error("ConferenceDaoImpl: getAllUpcoming: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public Map<Long, Integer> getNumberConversations() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CONVERSATION_COUNT)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<Long, Integer> conversationNumber = new HashMap<>();
            while (resultSet.next())
                conversationNumber.put(resultSet.getLong("conference_id"), resultSet.getInt("conversation_count"));
            return conversationNumber;
        } catch (SQLException e) {
            logger.error("ConferenceDaoImpl: getNumberConversations: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public Map<Long, Integer> getNumberUsers() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_COUNT)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<Long, Integer> userNumber = new HashMap<>();
            while (resultSet.next())
                userNumber.put(resultSet.getLong("conference_id"), resultSet.getInt("user_count"));
            return userNumber;
        } catch (SQLException e) {
            logger.error("ConferenceDaoImpl: getNumberUsers: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public Map<Long, Integer> getNumberSpeakers() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SPEAKER_COUNT)) {
            Map<Long, Integer> speakerNumber = new HashMap<>();
            List<Long> conferencesIds = getConferencesIds();
            for (Long conferenceId : conferencesIds){
                preparedStatement.setLong(1, conferenceId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next())
                    speakerNumber.put(conferenceId, resultSet.getInt("speaker_count"));
            }
            return speakerNumber;
        } catch (SQLException e) {
            logger.error("ConferenceDaoImpl: getNumberSpeakers: " + e);
            throw new RuntimeException();
        }
    }

    private List<Long> getConferencesIds() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            List<Long> conferencesIds = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                conferencesIds.add(resultSet.getLong("conference_id"));
            return conferencesIds;
        } catch (SQLException e) {
            logger.error("ConferenceDaoImpl: getConferencesIds: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public int getConferencesCount() {
        int conferenceAmount = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                conferenceAmount = resultSet.getInt(1);
            return conferenceAmount;
        } catch (SQLException e) {
            logger.error("ConferenceDaoImpl: getConferencesCount: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public int getPastConferencesCount() {
        int conferenceAmount = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_PAST)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                conferenceAmount = resultSet.getInt(1);
            return conferenceAmount;
        } catch (SQLException e) {
            logger.error("ConferenceDaoImpl: getPastConferencesCount: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public int getUpcomingConferencesCount() {
        int conferenceAmount = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_UPCOMING)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                conferenceAmount = resultSet.getInt(1);
            return conferenceAmount;
        } catch (SQLException e) {
            logger.error("ConferenceDaoImpl: getUpcomingConferencesCount: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public List<Conference> getPaginatedConferences(int begin, int recordsPerPage, String language) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PAGINATED)) {
            return getConferences(begin, recordsPerPage, language, preparedStatement);
        } catch (SQLException e) {
            logger.error("ConferenceDaoImpl: getPaginatedConferences: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public List<Conference> getPastPaginatedConferences(int begin, int recordsPerPage, String language) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PAGINATED_PAST)) {
            return getConferences(begin, recordsPerPage, language, preparedStatement);
        } catch (SQLException e) {
            logger.error("ConferenceDaoImpl: getPastPaginatedConferences: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public List<Conference> getUpcomingPaginatedConferences(int begin, int recordsPerPage, String language) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PAGINATED_UPCOMING)) {
            return getConferences(begin, recordsPerPage, language, preparedStatement);
        } catch (SQLException e) {
            logger.error("ConferenceDao: getUpcomingPaginatedConferences: " + e);
            throw new RuntimeException();
        }
    }

    private List<Conference> getConferences(int begin, int recordsPerPage, String language, PreparedStatement statement) throws SQLException {
        List<Conference> conferences = new ArrayList<>();
        statement.setInt(1, begin);
        statement.setInt(2, recordsPerPage);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next())
            conferences.add(conferenceRowMapper.mapRow(resultSet, language));
        return conferences;
    }

    @Override
    public boolean add(Conference conference) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Conference conference) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(long id) {
        try(Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("ConferenceDaoImpl: delete: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public Conference getById(long id, String language) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =connection.prepareStatement(SELECT_BY_ID)) {
            Conference conference = null;
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                conference = conferenceRowMapper.mapRow(resultSet, language);
            return conference;
        } catch (SQLException e) {
            logger.error("ConferenceDaoImpl: getById: " + e);
            throw new RuntimeException();
        }
    }
}
