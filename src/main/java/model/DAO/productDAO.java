package model.DAO;

import java.util.ArrayList;
import java.sql.*;
import model.product;

public class productDAO {
	private Connection conn; // store active connection here
    private PreparedStatement fetchProdSt;
	private PreparedStatement addProductSt;
	private PreparedStatement removeProductSt;
	private PreparedStatement updateProductSt;
	private PreparedStatement searchProductNameSt;
	private PreparedStatement searchProductTypeSt;
	private String readQuery = "SELECT * FROM productdata";
	private String CreateQuery = "INSERT INTO productdata VALUES (?,?,?,?,?,?,?,?)";
	private String DeleteQuery = "DELETE FROM productdata WHERE ProductID=?";
	private String UpdateQuery = "UPDATE productdata SET ProductName = '?', ProductStatus = '?', ProductReleaseDate = '?', ProductStockLevel = ?, ProductDescription = '?', ProductType = '?', ProductCost = ? WHERE ProductID=?";
	private String SearchQueryName = "SELECT * FROM productdata WHERE ProductName LIKE ? ";
	private String SearchQueryType = "SELECT * FROM productdata WHERE ProductType LIKE ? ";

    public productDAO(Connection connection) throws SQLException {
		this.conn = connection;
		conn.setAutoCommit(false);
		fetchProdSt = conn.prepareStatement(readQuery);
		addProductSt = conn.prepareStatement(CreateQuery);
		removeProductSt = conn.prepareStatement(DeleteQuery);
		updateProductSt = conn.prepareStatement(UpdateQuery);
		searchProductNameSt = conn.prepareStatement(SearchQueryName);
		searchProductTypeSt = conn.prepareStatement(SearchQueryType);
	}

	
	/**
	 * This Method retreives products from the DB using result sets and prepared statements
	 * @return ArrayList containing all products
	 * @throws SQLException
	 */
	public ArrayList<product> fetchProducts() throws SQLException {
		ResultSet rs = fetchProdSt.executeQuery();

		ArrayList<product> products = new ArrayList<product>(); // ArrayList to hold products
		while (rs.next()) { // go to next item in rs table then run
			long pID = rs.getLong(1); //retrieve variables from db query
            String pName = rs.getString(2);
            String pStatus = rs.getString(3);
            String pReleaseDate = rs.getString(4);
            long pStockLevel = rs.getLong(5);
            String pDescription = rs.getString(6);
			String pType = rs.getString(7);
            double pPrice = rs.getLong(8);

			product p = new product();
			p.setpID(pID);
			p.setName(pName);
			p.setStatus(pStatus);
			p.setReleaseDate(pReleaseDate);
			p.setStock(pStockLevel);
			p.setDescription(pDescription);
			p.setType(pType);
			p.setPrice(pPrice);

			products.add(p); // add object to end of product array
		}

		return products;
	}
	/**
	 * Adds a row to the DB using given params
	 * @param pID
	 * @param Name
	 * @param Status
	 * @param rDate
	 * @param Slevel
	 * @param desc
	 * @param pType
	 * @param price
	 * @throws SQLException
	 */
	public boolean addProduct(long pID, String Name, String Status, String rDate, long Slevel, String desc, String pType, double price) throws SQLException{
		addProductSt.setLong(1, pID);
		addProductSt.setString(2, Name);
		addProductSt.setString(3, Status);
		addProductSt.setString(4, rDate);
		addProductSt.setLong(5, Slevel);
		addProductSt.setString(6, desc);
		addProductSt.setString(7, pType);
		addProductSt.setDouble(8, price);
		return (addProductSt.executeUpdate()>0);
	}
	/**
	 * Removes a product from the DB based off product ID
	 * @param pID
	 * @throws SQLException
	 */
	public void removeProduct(long pID) throws SQLException{
		removeProductSt.setLong(1, pID);
		removeProductSt.executeUpdate();
	}
	/**
	 * Should be used by first reading old entry then allowing the user to alter then updating the DB row based off
	 * product ID
	 * @param pID
	 * @param Name
	 * @param Status
	 * @param rDate
	 * @param Slevel
	 * @param desc
	 * @param pType
	 * @param price
	 * @throws SQLException
	 */
	public void updateProduct(long pID, String Name, String Status, String rDate, long Slevel, String desc, String pType, double price) throws SQLException{
		updateProductSt.setLong(8, pID);
		updateProductSt.setString(1, Name);
		updateProductSt.setString(2, Status);
		updateProductSt.setString(3, rDate);
		updateProductSt.setLong(4, Slevel);
		updateProductSt.setString(5, desc);
		updateProductSt.setString(6, pType);
		updateProductSt.setDouble(7, price);
		updateProductSt.executeUpdate();
	}

	/**
	 * Similar to regular search except that it uses a where statement for filtering
	 * @param type The name of the column to search, either ProductName or ProductType
	 * @param query The phrase to search for
	 * @return an ArrayList Containing the searched Products
	 * @throws SQLException
	 */
	public ArrayList<product> searchProdBy(String type, String query) throws SQLException{
		ResultSet rs;
		if(type.equals("ProductName")) {
			searchProductNameSt.setString(1, "%" + query + "%");
			rs = searchProductNameSt.executeQuery();
		} else {
			searchProductTypeSt.setString(1, "%" + query + "%");
			rs = searchProductTypeSt.executeQuery();
		}
		ArrayList<product> products = new ArrayList<product>(); // ArrayList to hold products
		while (rs.next()) { // go to next item in rs table then run
			long pID = rs.getLong(1); //retrieve variables from db query
            String pName = rs.getString(2);
            String pStatus = rs.getString(3);
            String pReleaseDate = rs.getString(4);
            long pStockLevel = rs.getLong(5);
            String pDescription = rs.getString(6);
			String pType = rs.getString(7);
            double pPrice = Double.parseDouble(rs.getString(8));

			product p = new product();
			p.setpID(pID);
			p.setName(pName);
			p.setStatus(pStatus);
			p.setReleaseDate(pReleaseDate);
			p.setStock(pStockLevel);
			p.setDescription(pDescription);
			p.setType(pType);
			p.setPrice(pPrice);

			products.add(p); // add object to end of product array
		}

		return products;
	}
}