// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertNull;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.List;

// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.TestInstance;

// import model.card;
// import model.DAO.DBConnector;
// import model.DAO.cardDAO;

// @TestInstance(TestInstance.Lifecycle.PER_METHOD)
// public class CardDAOTest {
//     private cardDAO cardDao;
//     private Connection conn;
//     private static final long EXISTING_USER_ID = 1111111111L;
//     private long cardId;

//     @BeforeEach
//     public void setUp() throws SQLException, ClassNotFoundException {
//         DBConnector connector = new DBConnector();
//         conn = connector.openConnection();
//         conn.setAutoCommit(false);
//         cardDao = new cardDAO(conn);
//         cardId = System.currentTimeMillis(); // Use current time as unique card ID
//         System.out.println("Setup complete: Database connected and DAO initialized.");
//     }

//     @AfterEach
//     public void tearDown() {
//         System.out.println("Starting cleanup: Attempting to roll back any changes.");
//         try {
//             if (conn != null) {
//                 conn.rollback();
//                 conn.setAutoCommit(true);
//                 conn.close();
//                 System.out.println("Cleanup complete: Connection closed and changes rolled back.");
//             }
//         } catch (SQLException e) {
//             System.err.println("Error during cleanup: " + e.getMessage());
//         }
//     }

//     @Test
//     public void testCreateCard() throws SQLException {
//         // Step 1: Create Card
//         card newCard = new card(5665469469416L, 1234567812345678L, "John Doe", "2026-04-30", 123, EXISTING_USER_ID);
//         System.out.println("Creating card: " + newCard);

//         boolean createResult = false;
//         try {
//             createResult = cardDao.createCard(newCard);
//         } catch (Exception e) {
//             System.err.println("Exception during card creation: " + e.getMessage());
//             e.printStackTrace();
//         }
//         assertTrue(createResult, "Card creation failed when it should have succeeded.");
//         System.out.println("Card creation test passed: Card was successfully created.");

//         // Commit the transaction to ensure the card is saved in the database
//         conn.commit();
//         System.out.println("Transaction committed.");

//         // Directly query the database to check if the card was inserted
//         try (PreparedStatement checkCardSt = conn.prepareStatement("SELECT * FROM Card WHERE CardID = ?")) {
//             checkCardSt.setLong(1, cardId);
//             ResultSet rs = checkCardSt.executeQuery();
//             if (rs.next()) {
//                 System.out.println("Card found in the database with ID: " + cardId);
//             } else {
//                 System.out.println("Card not found in the database with ID: " + cardId);
//             }
//         }
//     }

//     @Test
//     public void testGetCardById() throws SQLException {
//         // Assume a card with ID cardId was created in a previous test
//         card fetchedCard = cardDao.getCardById(1111111117);
//         assertNotNull(fetchedCard, "Failed to fetch the card after creation.");
//         assertEquals("Alex Abagale", fetchedCard.getCardHolderName(), "Mismatch in the card holder name of the created card.");
//         System.out.println("Fetch after creation test passed: Card details match.");
//     }

//     @Test
//     public void testUpdateCard() {
//         // Assuming an existing card with ID 1
//         card existingCard = cardDao.getCardById(1);
//         assertNotNull(existingCard, "Existing card should be fetched");

//         // Update card details
//         existingCard.setCardHolderName("Jane Doe");
//         existingCard.setCardCVV(456);

//         // Update the card and verify
//         boolean updated = cardDao.updateCard(existingCard);
//         assertTrue(updated, "Card should be updated successfully");

//         card updatedCard = cardDao.getCardById(1);
//         assertEquals(existingCard.getCardHolderName(), updatedCard.getCardHolderName(), "Updated card holder name should match");
//         assertEquals(existingCard.getCardCVV(), updatedCard.getCardCVV(), "Updated card CVV should match");
//     }

//     @Test
//     public void testGetCardsForUser() throws SQLException {
//         List<card> cardList = cardDao.getCardsForUser(EXISTING_USER_ID);
//         assertNotNull(cardList, "Card list should not be null.");
//         System.out.println("Get cards for user test passed: Fetched cards match the expected count.");
//     }

//     @Test
//     public void testDeleteCard() throws SQLException {
//         // Create a card to ensure it exists
//         card newCard = new card(cardId, 1234567812345678L, "John Doe", "2026-04-30", 123, EXISTING_USER_ID);
//         cardDao.createCard(newCard);
//         conn.commit();

//         // Ensure no payments are linked to this card ID
//         PreparedStatement deletePaymentsSt = conn.prepareStatement("DELETE FROM Payments WHERE CardID = ?");
//         deletePaymentsSt.setLong(1, cardId);
//         deletePaymentsSt.executeUpdate();
//         System.out.println("Payments unlinked for testDeleteCard.");

//         // Delete the card
//         boolean deleteResult = cardDao.deleteCard(cardId);
//         System.out.println("Deletion test passed: Card was successfully deleted.");

//         card deletedCard = cardDao.getCardById(cardId);
//         assertNull(deletedCard, "Deleted card fetch test failed: card should not be found.");
//         System.out.println("Verification of deletion passed: No card found post-deletion.");
//     }
// }
