package chornyi.conferences.db.maprow;

import chornyi.conferences.db.entity.Conference;
import chornyi.conferences.db.entity.details.ConferenceDetails;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConferenceRowMapper implements RowMapper<Conference> {

    private static final String ID = "conference_id";
    private static final String NAME = "conference_name";
    private static final String LOCATION = "conference_location";
    private static final String DATETIME = "conference_datetime";
    private static final String NAME_EN = "conference_nameen_US";
    private static final String NAME_UA = "conference_nameuk_UA";
    private static final String LOCATION_EN = "conference_locationen_US";
    private static final String LOCATION_UA = "conference_locationuk_UA";


    @Override
    public Conference mapRow(ResultSet resultSet, String language) throws SQLException {
        return new Conference.Builder()
                .setId(resultSet.getLong(ID))
                .setName(resultSet.getString(NAME + language))
                .setLocation(resultSet.getString(LOCATION + language))
                .setDateTime(resultSet.getTimestamp(DATETIME).toLocalDateTime())
                .build();
    }

    public ConferenceDetails mapRowDetails(ResultSet resultSet) throws SQLException {
        return new ConferenceDetails.Builder()
                .setId(resultSet.getLong(ID))
                .setNameEN(resultSet.getString(NAME_EN))
                .setNameUA(resultSet.getString(NAME_UA))
                .setLocationEN(resultSet.getString(LOCATION_EN))
                .setLocationUA(resultSet.getString(LOCATION_UA))
                .setDateTime(resultSet.getTimestamp(DATETIME).toLocalDateTime())
                .build();
    }
}
