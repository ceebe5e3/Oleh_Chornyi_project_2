package chornyi.conferences.web.service;

import chornyi.conferences.db.dao.ConferenceDao;
import chornyi.conferences.db.dao.DaoFactory;
import chornyi.conferences.db.entity.Conference;
import chornyi.conferences.db.entity.details.ConferenceDetails;

import java.util.List;
import java.util.Map;

/**
 * Contains base operation with conferences
 */

public class ConferenceService  {

    private ConferenceDao conferenceDao = DaoFactory.getInstance().createConferenceDao();

    public boolean add(ConferenceDetails conferenceDetails) {
        return conferenceDao.add(conferenceDetails);
    }

    public void update(ConferenceDetails conferenceDetails) {
        conferenceDao.update(conferenceDetails);
    }

    public boolean delete(Long id) {
        return conferenceDao.delete(id);
    }

    public int getConferencesCount() {
        return conferenceDao.getConferencesCount();
    }

    public List<Conference> getAllPastConferences(String language) {
        return conferenceDao.getAllPast(language);
    }

    public List<Conference> getAllUpcomingConferences(String language) {
        return conferenceDao.getAllUpcoming(language);
    }

    public Conference getConferenceById(long conferenceId, String language) {
        return conferenceDao.getById(conferenceId, language);
    }

    public ConferenceDetails getConferenceDetailsById(long conferenceId) {
        return conferenceDao.getConferenceDetailsById(conferenceId);
    }

    public List<Conference> getAllConferences(String language) {
        return conferenceDao.getAll(language);
    }

    public Map<Long, Integer> getNumberConversations() {
        return conferenceDao.getNumberConversations();
    }

    public Map<Long, Integer> getNumberUsers() {
        return conferenceDao.getNumberUsers();
    }

    public Map<Long, Integer> getNumberSpeakers() {
        return conferenceDao.getNumberSpeakers();
    }

    public int getPastConferencesCount() {
        return conferenceDao.getPastConferencesCount();
    }

    public int getUpcomingConferencesCount() {
        return conferenceDao.getUpcomingConferencesCount();
    }

    public List<Conference> getPaginatedConferences(Integer begin, Integer recordsPerPage, String language) {
        return conferenceDao.getPaginatedConferences(begin, recordsPerPage, language);
    }

    public List<Conference> getPastPaginatedConferences(Integer begin, Integer recordsPerPage, String language) {
        return conferenceDao.getPastPaginatedConferences(begin, recordsPerPage, language);
    }

    public List<Conference> getUpcomingPaginatedConferences(Integer begin, Integer recordsPerPage, String language) {
        return conferenceDao.getUpcomingPaginatedConferences(begin, recordsPerPage, language);
    }
}
