package chornyi.conferences.web.service;

import chornyi.conferences.db.dao.ConversationDao;
import chornyi.conferences.db.dao.DaoFactory;
import chornyi.conferences.db.entity.Conversation;
import chornyi.conferences.db.entity.details.ConversationDetails;

import java.util.List;
import java.util.Map;

/**
 * Contains base operation with conversations
 */

public class ConversationService{

    private ConversationDao conversationDao = DaoFactory.getInstance().createConversationDao();

    public ConversationDetails getConversationDetailsById(Long conversationId) {
        return conversationDao.getConversationDetailsById(conversationId);
    }

    public void addConversationToConference(long conferenceId, ConversationDetails conversationDetails) {
        conversationDao.add(conferenceId, conversationDetails);
    }

    public void update(ConversationDetails conversationDetails) {
        conversationDao.update(conversationDetails);
    }

    public boolean delete(long id) {
        return conversationDao.delete(id);
    }

    public List<Conversation> getAllConversation(String language) {
        return conversationDao.getAll(language);
    }

    public Map<Long, List<Conversation>> getSpeakersConversations(List<Long> speakersIds, String language) {
        return conversationDao.getSpeakersConversations(speakersIds, language);
    }

    public int getConversationsCount() {
        return conversationDao.getConversationsCount();
    }

    public int getConversationsCountLinkedToConference(long conferenceId) {
        return conversationDao.getConversationsCountLinkedToConference(conferenceId);
    }

    public List<Conversation> getAllLinkedToConference(long conferenceId, String language) {
        return conversationDao.getAllLinkedToConference(conferenceId, language);
    }

    public List<Conversation> getPaginatedConversationsLinkedToConference(long conferenceId, Integer begin, Integer recordsPerPage, String language) {
        return conversationDao.getPaginatedConversationsLinkedToConference(conferenceId, begin, recordsPerPage, language);
    }
}
