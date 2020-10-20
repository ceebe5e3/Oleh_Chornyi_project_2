package chornyi.conferences.web.service;

import chornyi.conferences.db.dao.DaoFactory;
import chornyi.conferences.db.dao.UserDao;
import chornyi.conferences.db.entity.Role;
import chornyi.conferences.db.entity.User;
import chornyi.conferences.db.entity.details.UserDetails;

import java.util.List;
import java.util.Map;

/**
 * Contains base operation with user
 */

public class UserService {

    private UserDao userDao = DaoFactory.getInstance().createUserDao();

    public User getById(long userId, String language){
        return userDao.getById(userId, language);
    }

    public Role getRole(String login) {
        return userDao.getUserRole(login);
    }

    public String getByLoginRole(String login, String language) {
        return userDao.getByLogin(login, language).getRole().toString().toLowerCase();
    }

    public boolean isUserExist(String login) {
        return userDao.isUserExist(login);
    }

    public boolean checkPassword(String login, String password) {
        return userDao.checkUserPassword(login, password);
    }

    public void register(UserDetails userDetails) {
        userDao.add(userDetails);
    }

    public long getUserId(String login, String language) {
        return userDao.getByLogin(login, language).getId();
    }

    public List<User> getAllUsers(String language) {
        return userDao.getAll(language);
    }

    public List<User> getAllSpeakers(String language) {
        return userDao.getAllSpeakers(language);
    }

    public List<Long> getConferencesIdsUser(long userId) {
        return userDao.getConferencesIdsForUser(userId);
    }

    public void registerForConference(long userId, long conferenceId) {
        userDao.registerForConference(userId, conferenceId);
    }

    public void unregisterFromConference(long userId, long conferenceId) {
        userDao.unregisterFromConference(userId, conferenceId);
    }

    public List<Long> getSpeakersIds() {
        return userDao.getSpeakersIds();
    }

    public List<User> getPaginatedSpeakers(Integer begin, Integer recordsPerPage, String language) {
        return userDao.getPaginatedSpeakers(begin, recordsPerPage, language);
    }


}
