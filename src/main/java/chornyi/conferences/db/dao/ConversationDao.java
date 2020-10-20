package chornyi.conferences.db.dao;

import chornyi.conferences.db.entity.Conversation;
import chornyi.conferences.db.entity.details.ConversationDetails;

import java.util.List;
import java.util.Map;

/**
 * Extends base interface {@link AbstractDao}, adding some special
 * methods for work with {@link Conversation}
 * entity in data source
 */

public interface ConversationDao extends AbstractDao<Conversation>{

    void add(long conferenceId, ConversationDetails conversation);

    void update(ConversationDetails conversationDetails);

    List<Conversation> getAllLinkedToConference(long conferenceId, String language);

    ConversationDetails getConversationDetailsById(long conversationId);

    Map<Long, List<Conversation>> getSpeakersConversations(List<Long> speakersIds, String language);

    int getConversationsCount();

    int getConversationsCountLinkedToConference(long conferenceId);

    List<Conversation> getPaginatedConversationsLinkedToConference(long conferenceId, Integer begin, Integer recordsPerPage, String language);
}
