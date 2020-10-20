package chornyi.conferences.web.service;

import chornyi.conferences.db.entity.Conference;
import chornyi.conferences.db.entity.details.ConferenceDetails;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConferenceServiceTest {

    private static ConferenceService conferenceService;
    private static final String LANGUAGE = "en_US";

    @BeforeAll
    static void init() {
        conferenceService = new ConferenceService();
    }

    @Test
    void getNonExistenceConferenceById() {
        Conference conference = conferenceService.getConferenceById(600, LANGUAGE);
        assertNull(conference);
    }

    @Test
    void getExistenceConferenceById() {
        Conference conference = conferenceService.getConferenceById(1, LANGUAGE);
        assertEquals(conference.getId(), 1);
    }

    @Test
    void getNonExistenceConferenceDetailsById() {
        ConferenceDetails conferenceDetails = conferenceService.getConferenceDetailsById(600);
        assertNull(conferenceDetails);
    }

    @Test
    void getExistenceConferenceDetailsById() {
        ConferenceDetails conferenceDetails = conferenceService.getConferenceDetailsById(1);
        assertEquals(conferenceDetails.getId(), 1);

    }
}