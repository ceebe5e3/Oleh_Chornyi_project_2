package chornyi.conferences.web.service;

import chornyi.conferences.db.entity.details.ConversationDetails;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConversationServiceTest {

    private static ConversationService conversationService;

    @BeforeAll
    static void init(){
        conversationService = new ConversationService();
    }

    @Test
    void getNonExistenceConversationDetailsById(){
        ConversationDetails conversationDetails = conversationService.getConversationDetailsById( 600L);
        assertNull(conversationDetails);
    }

    @Test
    void getExistenceConversationDetailsById(){
        ConversationDetails conversationDetails = conversationService.getConversationDetailsById(3L);
        assertEquals(conversationDetails.getId(), 3);
    }
}