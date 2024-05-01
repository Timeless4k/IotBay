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
			int pID = rs.getInt(0); //retrieve variables from db query
            String pName = rs.getString(1);
            String pStatus = rs.getString(2);
            String pReleaseDate = rs.getString(3);
            int pStockLevel = rs.getInt(4);
            String pDescription = rs.getString(5);
			String pType = rs.getString(6);
            int pPrice = rs.getInt(7);

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

	public boolean addProduct() throws SQLException{
		return true; // temp for failing to update db
	}

	public boolean removeProduct() throws SQLException{
		return true; // temp for failing to update db
	}

	public boolean updateProduct() throws SQLException{
		return true; // temp for failing to update db
	}
}