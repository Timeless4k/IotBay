import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import model.card;
import model.DAO.DBConnector;
import model.DAO.cardDAO;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class CardDAOTest {

    private Connection conn;
    private cardDAO cardDao;

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        DBConnector connector = new DBConnector();
        conn = connector.openConnection();
        conn.setAutoCommit(false);
        cardDao = new cardDAO(conn);
        System.out.println("Setup complete: Database connected and DAO initialized.");
    }

    @AfterEach
    public void tearDown() throws Exception {
        conn.rollback(); // Rollback transaction to avoid saving test data
        conn.close();
    }

    @Test
    public void testGetCardsForUser() {
        List<card> cards = cardDao.getCardsForUser(1111111117); // Assuming user ID 1 exists
        assertNotNull(cards);
        // Add more assertions as needed
    }

    @Test
    public void testGetCardById() {
        card retrievedCard = cardDao.getCardById(1111111117); // Assuming card ID 1 exists
        assertNotNull(retrievedCard);
        // Add more assertions as needed
    }

    // @Test
    // public void testCreateCard() throws SQLException {
    //     // Assuming card ID 1 is available for testing
    //     card newCard = new card(116161611, 1234567890123456L, "John Doe", "2026-04-30", 123, 1111111111); // Sample card data

    //     // Start transaction
    //     conn.setAutoCommit(false);

    //     try {
    //         boolean created = cardDao.createCard(newCard);
    //         assertTrue(created, "Card creation failed when it should have succeeded.");
    //         System.out.println("Card creation test passed: Card was successfully created.");

    //         card fetchedCard = cardDao.getCardById(116161611); // Assuming card ID 1 exists
    //         assertNotNull(fetchedCard, "Failed to fetch the card after creation.");
    //         assertEquals(newCard.getCardNumber(), fetchedCard.getCardNumber(), "Card number should match");
    //         assertEquals(newCard.getCardHolderName(), fetchedCard.getCardHolderName(), "Card holder name should match");
    //         assertEquals(newCard.getCardExpiry(), fetchedCard.getCardExpiry(), "Card expiry should match");
    //         assertEquals(newCard.getCardCVV(), fetchedCard.getCardCVV(), "Card CVV should match");
    //         assertEquals(newCard.getUserID(), fetchedCard.getUserID(), "User ID should match");
    //         System.out.println("Fetch after creation test passed: Card details match.");
    //     } finally {
    //         // Roll back transaction
    //         conn.rollback();
    //     }
    // }


    @Test
    public void testUpdateCard() {
        // Assuming an existing card with ID 1
        card existingCard = cardDao.getCardById(1);
        assertNotNull(existingCard, "Existing card should be fetched");

        // Update card details
        existingCard.setCardHolderName("Jane Doe");
        existingCard.setCardCVV(456);

        // Update the card and verify
        boolean updated = cardDao.updateCard(existingCard);
        assertTrue(updated, "Card should be updated successfully");

        card updatedCard = cardDao.getCardById(1);
        assertEquals(existingCard.getCardHolderName(), updatedCard.getCardHolderName(), "Updated card holder name should match");
        assertEquals(existingCard.getCardCVV(), updatedCard.getCardCVV(), "Updated card CVV should match");
    }

    // @Test
    // public void testDeleteCard() {
    //     // Assuming an existing card with ID 1
    //     boolean deleted = cardDao.deleteCard(116461641);
    //     assertTrue(deleted, "Card should be deleted successfully");

    //     // Attempt to fetch the deleted card
    //     card deletedCard = cardDao.getCardById(116461641);
    //     assertNull(deletedCard, "Deleted card should not be found");
    // }
}
