package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.card;

public class cardDAO {
    private Connection conn;
    private PreparedStatement getCardsForUserSt;
    private PreparedStatement getCardByIdSt;
    private PreparedStatement deleteCardSt;
    private PreparedStatement createCardSt;
    private PreparedStatement updateCardSt;

    /**
     * Constructor to initialize the cardDAO with a database connection and prepare statements.
     * 
     * @param connection the database connection
     * @throws SQLException if a database access error occurs
     */
    public cardDAO(Connection connection) throws SQLException {
        this.conn = connection;

        // Prepare SQL statements
        getCardsForUserSt = conn.prepareStatement(
            "SELECT * FROM Card WHERE UserID = ?");
        getCardByIdSt = conn.prepareStatement(
            "SELECT * FROM Card WHERE CardID = ?");
        deleteCardSt = conn.prepareStatement(
            "DELETE FROM Card WHERE CardID = ?");
        createCardSt = conn.prepareStatement(
            "INSERT INTO Card (CardID, CardNumber, CardHolderName, CardExpiry, CardCVV, UserID) VALUES (?, ?, ?, ?, ?, ?)");
        updateCardSt = conn.prepareStatement(
            "UPDATE Card SET CardNumber = ?, CardHolderName = ?, CardExpiry = ?, CardCVV = ? WHERE CardID = ? AND UserID = ?");
    }

    /**
     * Retrieves a list of cards for a specific user.
     * 
     * @param userID the ID of the user
     * @return a list of cards associated with the user
     */
    public List<card> getCardsForUser(long userID) {
        List<card> cardList = new ArrayList<>();
        ResultSet rs = null;
        try {
            getCardsForUserSt.setLong(1, userID);
            rs = getCardsForUserSt.executeQuery();
            while (rs.next()) {
                card card = extractCardFromResultSet(rs);
                cardList.add(card);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching cards for user: " + e.getMessage());
        } finally {
            // Close ResultSet (PreparedStatement will be closed when the connection is closed)
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        return cardList;
    }

    /**
     * Retrieves a card by its ID.
     * 
     * @param cardId the ID of the card
     * @return the card object if found, otherwise null
     */
    public card getCardById(long cardId) {
        ResultSet rs = null;
        try {
            getCardByIdSt.setLong(1, cardId);
            rs = getCardByIdSt.executeQuery();
            if (rs.next()) {
                return extractCardFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching card by ID: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                System.err.println("Error closing result set: " + e.getMessage());
            }
        }
        return null;
    }

    /**
     * Deletes a card by its ID.
     * 
     * @param cardId the ID of the card to be deleted
     * @return true if the card was successfully deleted, false otherwise
     */
    public boolean deleteCard(long cardId) {
        PreparedStatement deleteCardSt = null;
        try {
            deleteCardSt = conn.prepareStatement("DELETE FROM Card WHERE CardID = ?");
            deleteCardSt.setLong(1, cardId);
            int rowsAffected = deleteCardSt.executeUpdate();
            System.out.println("Attempt to delete card ID " + cardId + " affected " + rowsAffected + " rows.");
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting card: " + e.getMessage());
            return false;
        } finally {
            try {
                if (deleteCardSt != null) {
                    deleteCardSt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing prepared statement: " + e.getMessage());
            }
        }
    }
       
    /**
     * Creates a new card in the database.
     * 
     * @param newCard the card object to be created
     * @return true if the card was successfully created, false otherwise
     */
    public boolean createCard(card newCard) {
        PreparedStatement createCardSt = null;
        try {
            // Generate unique card ID
            long cardId = generateUniqueCardId();
            
            createCardSt = conn.prepareStatement(
                "INSERT INTO Card (CardID, CardNumber, CardHolderName, CardExpiry, CardCVV, UserID) VALUES (?, ?, ?, ?, ?, ?)");
            createCardSt.setLong(1, cardId);
            createCardSt.setLong(2, newCard.getCardNumber());
            createCardSt.setString(3, newCard.getCardHolderName());
            createCardSt.setString(4, newCard.getCardExpiry());
            createCardSt.setInt(5, newCard.getCardCVV());
            createCardSt.setLong(6, newCard.getUserID());
    
            int rowsAffected = createCardSt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error creating card: " + e.getMessage());
            return false;
        } finally {
            try {
                if (createCardSt != null) createCardSt.close();
            } catch (SQLException e) {
                System.err.println("Error closing prepared statement: " + e.getMessage());
            }
        }
    }

    /**
     * Generates a unique card ID.
     * 
     * @return a unique card ID
     */
    private long generateUniqueCardId() {
        // Generate a random card ID and check if it already exists in the database
        Random random = new Random();
        long cardId;
        boolean uniqueId = false;
        do {
            cardId = Math.abs(1000000000000000L + random.nextLong());
            uniqueId = isCardIdUnique(cardId);
        } while (!uniqueId);
        return cardId;
    }

    /**
     * Checks if a card ID is unique.
     * 
     * @param cardId the card ID to check
     * @return true if the card ID is unique, false otherwise
     */
    private boolean isCardIdUnique(long cardId) {
        ResultSet rs = null;
        try {
            PreparedStatement checkCardIdSt = conn.prepareStatement(
                "SELECT COUNT(*) AS count FROM Card WHERE CardID = ?");
            checkCardIdSt.setLong(1, cardId);
            rs = checkCardIdSt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count == 0; // If count is 0, card ID is unique
            }
        } catch (SQLException e) {
            System.err.println("Error checking card ID uniqueness: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                System.err.println("Error closing result set: " + e.getMessage());
            }
        }
        // In case of an error, consider the card ID as non-unique
        return false;
    }

    /**
     * Updates an existing card in the database.
     * 
     * @param cardToUpdate the card object with updated details
     * @return true if the card was successfully updated, false otherwise
     */
    public boolean updateCard(card cardToUpdate) {
        try {
            updateCardSt.setLong(1, cardToUpdate.getCardNumber());
            updateCardSt.setString(2, cardToUpdate.getCardHolderName());
            updateCardSt.setString(3, cardToUpdate.getCardExpiry());
            updateCardSt.setInt(4, cardToUpdate.getCardCVV());
            updateCardSt.setLong(5, cardToUpdate.getCardID());
            updateCardSt.setLong(6, cardToUpdate.getUserID()); // Set the UserID parameter
            int rowsAffected = updateCardSt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating card: " + e.getMessage());
            return false;
        }
    }

    /**
     * Extracts a card object from a ResultSet.
     * 
     * @param rs the ResultSet containing card data
     * @return a card object
     * @throws SQLException if a database access error occurs
     */
    private card extractCardFromResultSet(ResultSet rs) throws SQLException {
        card card = new card();
        card.setCardID(rs.getLong("CardID"));
        card.setCardNumber(rs.getLong("CardNumber"));
        card.setCardHolderName(rs.getString("CardHolderName"));
        card.setCardExpiry(rs.getString("CardExpiry"));
        card.setCardCVV(rs.getInt("CardCVV"));
        card.setUserID(rs.getLong("UserID"));
        return card;
    }
}
