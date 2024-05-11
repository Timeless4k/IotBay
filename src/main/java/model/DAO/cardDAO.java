package model.DAO;

import model.card;
import java.sql.*;
import java.util.Random;

public class cardDAO {
    private Connection conn;
    private Random random = new Random();

    public cardDAO(Connection conn) {
        this.conn = conn;
    }

    public long generateUniqueCardID() throws SQLException {
        long cardID;
        while (true) {
            cardID = Math.abs(random.nextLong());
            if (!cardIDExists(cardID)) {
                break;
            }
        }
        return cardID;
    }

    private boolean cardIDExists(long cardID) throws SQLException {
        String sql = "SELECT count(*) FROM CardInformation WHERE CardID = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, cardID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public void createCard(card card) throws SQLException {
        long newCardID = generateUniqueCardID();
        card.setCardID(newCardID);  // Set the generated CardID to the card object

        String sql = "INSERT INTO CardInformation (CardID, CardNumber, CardHolderName, CardExpiry, CardCVV, UserID) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, card.getCardID());
            statement.setLong(2, card.getCardNumber());
            statement.setString(3, card.getCardHolderName());
            statement.setString(4, card.getCardExpiry());
            statement.setInt(5, card.getCardCVV());
            statement.setLong(6, card.getUserID());
            statement.executeUpdate();
        }
    }

    public card getCardById(long cardID) throws SQLException {
        String sql = "SELECT * FROM CardInformation WHERE CardID = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, cardID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new card(
                        resultSet.getLong("CardID"),
                        resultSet.getLong("CardNumber"),
                        resultSet.getString("CardHolderName"),
                        resultSet.getString("CardExpiry"),
                        resultSet.getInt("CardCVV"),
                        resultSet.getLong("UserID")
                    );
                }
            }
        }
        return null;
    }

    public void updateCard(card card) throws SQLException {
        String sql = "UPDATE CardInformation SET CardNumber = ?, CardHolderName = ?, CardExpiry = ?, CardCVV = ?, UserID = ? WHERE CardID = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, card.getCardNumber());
            statement.setString(2, card.getCardHolderName());
            statement.setString(3, card.getCardExpiry());
            statement.setInt(4, card.getCardCVV());
            statement.setLong(5, card.getUserID());
            statement.setLong(6, card.getCardID());
            statement.executeUpdate();
        }
    }

    public void deleteCard(long cardID) throws SQLException {
        String sql = "DELETE FROM CardInformation WHERE CardID = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, cardID);
            statement.executeUpdate();
        }
    }
}
