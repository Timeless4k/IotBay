import org.junit.jupiter.api.*;
import model.DAO.DBConnector;
import model.DAO.CartDAO;
import model.Cart;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class CartDAOTest {
    private CartDAO cartDao;
    private Connection conn;

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        DBConnector connector = new DBConnector();
        conn = connector.openConnection();
        conn.setAutoCommit(false);
        cartDao = new CartDAO(conn);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        if (conn != null) {
            conn.rollback();
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    @Test
    public void testAddCart() throws SQLException {
        Cart newItem = new Cart(1, 1, 3);
        assertTrue(cartDao.addCart(newItem), "Should successfully add item to cart.");
    }

    @Test
    public void testUpdateCartItem() throws SQLException {
        Cart itemToUpdate = new Cart(1, 1, 5);
        cartDao.addCart(itemToUpdate);
        itemToUpdate.setQuantity(10);
        assertTrue(cartDao.updateCartItem(itemToUpdate), "Should successfully update item quantity in cart.");
    }

    @Test
    public void testRemoveCartItem() throws SQLException {
        Cart itemToRemove = new Cart(1, 2, 2);
        cartDao.addCart(itemToRemove);
        assertTrue(cartDao.removeCart(itemToRemove.getUserID(), itemToRemove.getProductID()), "Should successfully remove item from cart.");
    }

    @Test
    public void testGetCart() throws SQLException {
        long userID = 1;
        cartDao.addCart(new Cart(userID, 3, 1));
        assertFalse(cartDao.getCart(userID).isEmpty(), "Cart should not be empty.");
    }
}