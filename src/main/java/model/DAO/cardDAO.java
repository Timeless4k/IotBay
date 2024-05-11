package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.card;

public class cardDAO {
    private Connection conn;
    private PreparedStatement getCardsForUserSt;

    public cardDAO(Connection connection) throws SQLException {
        this.conn = connection;

        // Prepare statement
        getCardsForUserSt = conn.prepareStatement(
            "SELECT * FROM Card WHERE UserID = ?");
    }

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
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                System.err.println("Error closing result set: " + e.getMessage());
            }
        }
        return cardList;
    }

    public boolean createCard(card newCard) {
        PreparedStatement createCardSt = null;
        try {
            createCardSt = conn.prepareStatement(
                "INSERT INTO Card (CardNumber, CardHolderName, CardExpiry, CardCVV, UserID) VALUES (?, ?, ?, ?, ?)");
            createCardSt.setLong(1, newCard.getCardNumber());
            createCardSt.setString(2, newCard.getCardHolderName());
            createCardSt.setString(3, newCard.getCardExpiry());
            createCardSt.setInt(4, newCard.getCardCVV());
            createCardSt.setLong(5, newCard.getUserID());
    
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
