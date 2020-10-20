package chornyi.conferences.db.dao.impl;

import chornyi.conferences.db.dao.ConversationDao;
import chornyi.conferences.db.entity.Conversation;
import chornyi.conferences.db.entity.details.ConversationDetails;
import chornyi.conferences.db.maprow.ConversationRowMapper;
import chornyi.conferences.db.maprow.RowMapper;
import chornyi.conferences.db.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * {@link ConversationDao} implementation, provided DAO-logic for {@link Conversation}
 * entity with MySQL DataBase
 */

public class ConversationDaoImpl implements ConversationDao {

    private static final String INSERT = "INSERT INTO conversations (conversation_topicen_US, conversation_topicuk_UA, conversation_datetime, speaker_id) VALUES (?,?,?,?)";
    private static final String UPDATE = "UPDATE conversations SET conversation_topicen_US=?, conversation_topicuk_UA=?, conversation_datetime=?, speaker_id=? WHERE conversation_id=?";
    private static final String DELETE = "DELETE FROM conversations WHERE conversation_id=?";
    private static final String SELECT_BY_ID = "SELECT * FROM conversations WHERE conversation_id=?";
    private static final String SELECT_ALL = "SELECT * FROM conversations ORDER BY conversation_datetime ASC";
    private static final String SELECT_ALL_LINKED_CONFERENCE = "SELECT conversations.conversation_id, conversation_topicen_US, conversation_topicuk_UA, conversation_datetime, speaker_id FROM conversations JOIN conversations_conferences concof ON conversations.conversation_id = concof.conversation_id AND concof.conference_id=?";
    private static final String SELECT_SPEAKER_ID = "SELECT * FROM conversations WHERE speaker_id=?";
    private static final String COUNT = "SELECT COUNT(conversation_id) FROM conversations";
    private static final String SELECT_ALL_LINKED_CONFERENCE_PAGINATED = "SELECT conversations.conversation_id, conversation_topicen_US, conversation_topicuk_UA, conversation_datetime, speaker_id FROM conversations JOIN conversations_conferences concof ON conversations.conversation_id = concof.conversation_id AND concof.conference_id=? ORDER BY conversation_datetime LIMIT ?, ?";
    private static final String COUNT_LINKED_CONFERENCE = "SELECT COUNT(conversation_id) FROM conversations_conferences WHERE conference_id=?";
    private static final String INSERT_CONFERENCE = "INSERT INTO conversations_conferences (conversation_id, conference_id) VALUES (?,?)";

    private static final Logger logger = LogManager.getLogger(ConversationDaoImpl.class);
    private DataSource dataSource = ConnectionPool.getDataSource();
    private RowMapper<Conversation> conversationRowMapper = new ConversationRowMapper();

    @Override
    public void add(long conferenceId, ConversationDetails conversation) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement conversationStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CONFERENCE)) {
            connection.setAutoCommit(false);
            conversationStatement.setString(1, conversation.getTopicEN());
            conversationStatement.setString(2, conversation.getTopicUA());
            conversationStatement.setTimestamp(3, Timestamp.valueOf(conversation.getDateTime()));
            conversationStatement.setLong(4, conversation.getSpeakerId());
            int affectedLine = conversationStatement.executeUpdate();
            if (affectedLine == 0)
                throw new SQLException("Create user error, no line was affected.");

            try (ResultSet generatedKeys = conversationStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    preparedStatement.setLong(1, generatedKeys.getInt(1));
                    preparedStatement.setLong(2, conferenceId);
                    preparedStatement.execute();
                }
                else {
                    throw new SQLException("Create user error, no ID obtained.");
                }
            }
            connection.commit();
        } catch (SQLException e) {
            logger.error("ConversationDaoImpl: getAllLinkedToConference: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public void update(ConversationDetails conversationDetails) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, conversationDetails.getTopicEN());
            preparedStatement.setString(2, conversationDetails.getTopicUA());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(conversationDetails.getDateTime()));
            preparedStatement.setLong(4, conversationDetails.getSpeakerId());
            preparedStatement.setLong(5, conversationDetails.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("ConversationDaoImpl: update: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public List<Conversation> getAllLinkedToConference(long conferenceId, String language) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_LINKED_CONFERENCE)) {
            List<Conversation> conversations = new ArrayList<>();
            preparedStatement.setLong(1, conferenceId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                conversations.add(conversationRowMapper.mapRow(resultSet, language));
            return conversations;
        } catch (SQLException e) {
            logger.error("ConversationDaoImpl: getAllLinkedToConference: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public ConversationDetails getConversationDetailsById(long conversationId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            ConversationDetails conversationDetails = null;
            preparedStatement.setLong(1, conversationId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                conversationDetails = new ConversationRowMapper().mapRowDetails(resultSet);
            return conversationDetails;
        } catch (SQLException e) {
            logger.error("ConversationDaoImpl: getConversationDetailsById: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public Map<Long, List<Conversation>> getSpeakersConversations(List<Long> speakersIds, String language) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SPEAKER_ID)) {
            Map<Long, List<Conversation>> conversationSpeakers = new HashMap<>();
            for (Long speakerId : speakersIds){
                List<Conversation> conversations = new ArrayList<>();
                preparedStatement.setLong(1, speakerId);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next())
                    conversations.add(conversationRowMapper.mapRow(resultSet, language));
                conversationSpeakers.put(speakerId, conversations);
            }
            return conversationSpeakers;
        } catch (SQLException e) {
            logger.error("ConversationDao: getSpeakersConversations: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public int getConversationsCount() {
        int conversationsAmount = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement conferenceStatement = connection.prepareStatement(COUNT)) {
            ResultSet resultSet = conferenceStatement.executeQuery();
            if (resultSet.next())
                conversationsAmount = resultSet.getInt(1);
            return conversationsAmount;
        } catch (SQLException e) {
            logger.error("ConversationDaoImpl: getConversationsCount: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public int getConversationsCountLinkedToConference(long conferenceId) {
        int conversationAmount = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_LINKED_CONFERENCE)) {
            preparedStatement.setLong(1, conferenceId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                conversationAmount = resultSet.getInt(1);
            return conversationAmount;
        } catch (SQLException e) {
            logger.error("ConversationDaoImpl: getConversationsCountLinkedToConference: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public List<Conversation> getPaginatedConversationsLinkedToConference(long conferenceId, Integer begin, Integer recordsPerPage, String language) {
        ConversationRowMapper conversationRowMapper = new ConversationRowMapper();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_LINKED_CONFERENCE_PAGINATED)) {
            List<Conversation> paginatedConversationList = new ArrayList<>();
            preparedStatement.setLong(1, conferenceId);
            preparedStatement.setInt(2, begin);
            preparedStatement.setInt(3, recordsPerPage);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                paginatedConversationList.add(conversationRowMapper.mapRow(resultSet, language));
            return paginatedConversationList;
        } catch (SQLException e) {
            logger.error("ConversationDaoImpl: getPaginatedConversationsLinkedToConference: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public boolean add(Conversation conversation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Conversation conversation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            logger.error("ConversationDaoImpl: delete: " + e);
        }
        return false;
    }

    @Override
    public Conversation getById(long id, String language) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            Conversation conversation = null;
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                conversation = conversationRowMapper.mapRow(resultSet, language);
            return conversation;
        } catch (SQLException e) {
            logger.error("ConversationDaoImpl: getById: " + e);
            throw new RuntimeException();
        }
    }

    @Override
    public List<Conversation> getAll(String language) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            List<Conversation> conversations = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                conversations.add(conversationRowMapper.mapRow(resultSet, language));
            return conversations;
        } catch (SQLException e) {
            logger.error("ConversationDaoImpl: getAll: " + e);
            throw new RuntimeException();
        }
    }
}
