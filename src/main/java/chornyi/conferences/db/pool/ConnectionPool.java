package chornyi.conferences.db.pool;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

public class ConnectionPool {

    private volatile static DataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (ConnectionPool.class) {
                if (dataSource == null) {
                    BasicDataSource basicDataSource = new BasicDataSource();
                    ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
                    basicDataSource.setDriverClassName(resourceBundle.getString("sql.driver"));
                    basicDataSource.setUrl(resourceBundle.getString("sql.url"));
                    basicDataSource.setUsername(resourceBundle.getString("sql.user"));
                    basicDataSource.setPassword(resourceBundle.getString("sql.password"));
                    basicDataSource.setMinIdle(5);
                    basicDataSource.setMaxIdle(10);
                    basicDataSource.setMaxOpenPreparedStatements(100);
                    dataSource = basicDataSource;
                }
            }
        }
        return dataSource;
    }
}
