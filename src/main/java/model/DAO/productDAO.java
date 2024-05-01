package model.DAO;

import java.util.ArrayList;
import java.sql.*;
import model.product;

public class productDAO {
    private PreparedStatement readSt;
	private String readQuery = "SELECT * FROM productdata";


    public productDAO(Connection connection) throws SQLException {
		connection.setAutoCommit(true);
		readSt = connection.prepareStatement(readQuery);
	}

	public ArrayList<product> fetchProducts() throws SQLException {
		ResultSet rs = readSt.executeQuery();

		ArrayList<product> products = new ArrayList<product>(); // ArrayList to hold products
		while (rs.next()) { // go to next item in rs table then run
			long pID = rs.getLong(1); //retrieve variables from db query
            String pName = rs.getString(2);
            String pStatus = rs.getString(3);
            String pReleaseDate = rs.getString(4);
            long pStockLevel = rs.getLong(5);
            String pDescription = rs.getString(6);
			String pType = rs.getString(7);
            long pPrice = rs.getLong(8);

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

	public boolean addProduct(long pID, String Name, String Status, String rDate, long Slevel, String desc, long price) throws SQLException{
		return true; // temp for failing to update db
	}

	public boolean removeProduct() throws SQLException{
		return true; // temp for failing to update db
	}

	public boolean updateProduct() throws SQLException{
		return true; // temp for failing to update db
	}
}