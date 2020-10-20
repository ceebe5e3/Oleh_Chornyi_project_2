package chornyi.conferences.db.dao.impl;

import chornyi.conferences.db.dao.ConferenceDao;
import chornyi.conferences.db.dao.ConversationDao;
import chornyi.conferences.db.dao.DaoFactory;
import chornyi.conferences.db.dao.UserDao;

public class BaseDao extends DaoFactory {

    @Override
    public ConferenceDao createConferenceDao() {
        return new ConferenceDaoImpl();
    }

    @Override
    public ConversationDao createConversationDao() {
        return new ConversationDaoImpl();
    }

    @Override
    public UserDao createUserDao() {
        return new UserDaoImpl();
    }
}
