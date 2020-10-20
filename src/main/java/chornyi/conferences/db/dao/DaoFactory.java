package chornyi.conferences.db.dao;

import chornyi.conferences.db.dao.impl.BaseDao;

/**
 * General factory for data access layer
 */

public abstract class DaoFactory {

    private static volatile DaoFactory daoFactory;

    public abstract ConferenceDao createConferenceDao();

    public abstract ConversationDao createConversationDao();

    public abstract UserDao createUserDao();

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    daoFactory = new BaseDao();
                }
            }
        }
        return daoFactory;
    }

}
