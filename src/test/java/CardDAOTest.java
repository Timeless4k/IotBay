import model.card;
import model.DAO.DBConnector;
import model.DAO.cardDAO;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CardDAOTest {
    private cardDAO cardDao;
    private Connection conn;

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        DBConnector connector = new DBConnector();
        conn = connector.openConnection();
        conn.setAutoCommit(false);  // Start transaction to not impact actual database data
        cardDao = new cardDAO(conn);
        System.out.println("Setup: Connection opened and DAO initialized.");
    }

    @AfterEach
    public void tearDown() throws SQLException {
        if (conn != null) {
            System.out.println("TearDown: Rolling back any changes.");
            conn.rollback(); // Rollback transaction to avoid saving test data
            conn.close();
            System.out.println("TearDown: Connection closed.");
        }
    }

    @Test
    @Order(1)
    public void testConnectionNotNull() {
        System.out.println("Test: Checking if database connection is not null.");
        assertNotNull(conn, "Database connection should not be null");
    }

//     @Test
//     @Order(2)
//     public void testCreateCard() throws SQLException {
//         System.out.println("Test: Attempting to create a card.");
//         card newCard = new card(163216161, 1234567890123456L, "John Doe", "2026-04-01", 123, 1111111113);
//         boolean isCreated = cardDao.createCard(newCard);
//         assertTrue(isCreated, "Card should be created successfully");

//         System.out.println("Test: Fetching card by ID after creation.");
//         card fetchedCard = cardDao.getCardById(newCard.getCardID());
//         assertNotNull(fetchedCard, "Card should be readable after creation");
//         assertEquals("John Doe", fetchedCard.getCardHolderName(), "Card Holder Name should match");
//     }

//     @Test
//     @Order(3)
//     public void testReadCard() throws SQLException {
//         System.out.println("Test: Pre-inserting a card for read operation.");
//         card testCard = new card(0, 1234567890123456L, "John Doe", "2026-04-01", 123, 1111111113);
//         cardDao.createCard(testCard);

//         System.out.println("Test: Reading the card from the database.");
//         card fetchedCard = cardDao.getCardById(testCard.getCardID());
//         assertNotNull(fetchedCard, "Card should exist and be fetched correctly");
//     }

//     @Test
//     @Order(4)
//     public void testUpdateCard() throws SQLException {
//         System.out.println("Test: Pre-inserting a card for update operation.");
//         card testCard = new card(0, 1234567890123456L, "John Doe", "2026-04-01", 123, 1111111113);
//         cardDao.createCard(testCard);
//         testCard.setCardHolderName("Updated Name");

//         System.out.println("Test: Updating the card's holder name.");
//         boolean isUpdated = cardDao.updateCard(testCard);
//         assertTrue(isUpdated, "Card update should be successful");

//         System.out.println("Test: Verifying updated details.");
//         card updatedCard = cardDao.getCardById(testCard.getCardID());
//         assertEquals("Updated Name", updatedCard.getCardHolderName(), "Card Holder Name should be updated");
//     }

//     @Test
//     @Order(5)
//     public void testDeleteCard() throws SQLException {
//         System.out.println("Test: Pre-inserting a card for delete operation.");
//         card testCard = new card(0, 1234567890123456L, "John Doe", "2026-04-01", 123, 1111111113);
//         cardDao.createCard(testCard);

//         System.out.println("Test: Deleting the card.");
//         boolean isDeleted = cardDao.deleteCard(testCard.getCardID());
//         assertTrue(isDeleted, "Card should be deletable");

//         System.out.println("Test: Verifying deletion.");
//         card deletedCard = cardDao.getCardById(testCard.getCardID());
//         assertNull(deletedCard, "Card should be null after deletion");
//     }
// }
