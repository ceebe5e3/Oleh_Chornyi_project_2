package chornyi.conferences.db.dao;

import chornyi.conferences.db.entity.Role;
import chornyi.conferences.db.entity.User;
import chornyi.conferences.db.entity.details.UserDetails;
import chornyi.conferences.exception.DaoException;

import java.util.List;
import java.util.Map;

/**
 * Extends base interface {@link AbstractDao}, adding some special
 * methods for work with {@link User}
 * entity in data source
 */

public interface UserDao extends AbstractDao<User> {

    boolean add(UserDetails user) throws DaoException;

    User getByLogin(String login, String language);

    Role getUserRole(String login);

    boolean isUserExist(String login);

    boolean checkUserPassword(String login, String password);

    List<User> getAllSpeakers(String language);

    List<Long> getConferencesIdsForUser(long userId);

    void registerForConference(long userId, long conferenceId);

    void unregisterFromConference(long userId, long conferenceId);

    List<Long> getSpeakersIds();

    List<User> getPaginatedSpeakers(Integer begin, Integer recordsPerPage, String language);

}
