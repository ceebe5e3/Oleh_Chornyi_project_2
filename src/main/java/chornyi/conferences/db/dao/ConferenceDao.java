package chornyi.conferences.db.dao;

import chornyi.conferences.db.entity.Conference;
import chornyi.conferences.db.entity.details.ConferenceDetails;

import java.util.List;
import java.util.Map;

/**
 * Extends base interface {@link AbstractDao}, adding some special
 * methods for work with {@link Conference}
 * entity in data source
 */

public interface ConferenceDao extends AbstractDao<Conference> {

    boolean add(ConferenceDetails conference);

    void update(ConferenceDetails conference);

    ConferenceDetails getConferenceDetailsById(long conferenceId);

    List<Conference> getAll(String language);

    List<Conference> getAllPast(String language);

    List<Conference> getAllUpcoming(String language);

    Map<Long, Integer> getNumberConversations();

    Map<Long, Integer> getNumberUsers();

    Map<Long, Integer> getNumberSpeakers();

    int getConferencesCount();

    int getPastConferencesCount();

    int getUpcomingConferencesCount();

    List<Conference> getPaginatedConferences(int begin, int recordsPerPage, String language);

    List<Conference> getPastPaginatedConferences(int begin, int recordsPerPage, String language);

    List<Conference> getUpcomingPaginatedConferences(int begin, int recordsPerPage, String language);
}
