package chornyi.conferences.db.maprow;

import chornyi.conferences.db.entity.Conversation;
import chornyi.conferences.db.entity.details.ConversationDetails;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConversationRowMapper implements RowMapper<Conversation> {

    private static final String ID = "conversation_id";
    private static final String TOPIC = "conversation_topic";
    private static final String DATETIME = "conversation_datetime";
    private static final String SPEAKER_ID = "speaker_id";
    private static final String TOPIC_EN = "conversation_topicen_US";
    private static final String TOPIC_UA = "conversation_topicuk_UA";

    @Override
    public Conversation mapRow(ResultSet resultSet, String language) throws SQLException {
        return new Conversation.Builder()
                .setId(resultSet.getLong(ID))
                .setTopic(resultSet.getString(TOPIC + language))
                .setDateTime(resultSet.getTimestamp(DATETIME).toLocalDateTime())
                .setSpeakerId(resultSet.getLong(SPEAKER_ID))
                .build();
    }

    public ConversationDetails mapRowDetails(ResultSet resultSet) throws SQLException {
        return new ConversationDetails.Builder()
                .setId(resultSet.getLong(ID))
                .setTopicEN(resultSet.getString(TOPIC_EN))
                .setTopicUA(resultSet.getString(TOPIC_UA))
                .setDateTime(resultSet.getTimestamp(DATETIME).toLocalDateTime())
                .setSpeakerId(resultSet.getLong(SPEAKER_ID))
                .build();
    }
}
