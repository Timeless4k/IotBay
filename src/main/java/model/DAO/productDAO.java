package model.DAO;

import java.util.ArrayList;

import javax.naming.spi.DirStateFactory.Result;

import java.sql.*;
import model.product;

public class productDAO {
    private PreparedStatement fetchProdSt;
	private PreparedStatement addProductSt;
	private PreparedStatement removeProductSt;
	private PreparedStatement updateProductSt;
	private PreparedStatement searchProductSt;
	private String readQuery = "SELECT * FROM productdata";
	private String CreateQuery = "INSERT INTO productdata VALUES (?,?,?,?,?,?,?,?)";
	private String DeleteQuery = "DELETE FROM productdata WHERE ProductID=?";
	private String UpdateQuery = "UPDATE productdata SET ProductName = '?', ProductStatus = '?', ProductReleaseDate = '?', ProductStockLevel = ?, ProductDescription = '?', ProductType = '?', ProductCost = ? WHERE ProductID=?";
	private String SearchQuery = "SELECT * FROM productdata WHERE ? = '?'";
    public productDAO(Connection connection) throws SQLException {
		connection.setAutoCommit(true);
		fetchProdSt = connection.prepareStatement(readQuery);
		addProductSt = connection.prepareStatement(CreateQuery);
		removeProductSt = connection.prepareStatement(DeleteQuery);
		updateProductSt = connection.prepareStatement(UpdateQuery);
		searchProductSt = connection.prepareStatement(SearchQuery);
	}

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

	public void addProduct(long pID, String Name, String Status, String rDate, long Slevel, String desc, String pType, double price) throws SQLException{
		addProductSt.setLong(1, pID);
		addProductSt.setString(2, Name);
		addProductSt.setString(3, Status);
		addProductSt.setString(4, rDate);
		addProductSt.setLong(5, Slevel);
		addProductSt.setString(6, desc);
		addProductSt.setString(7, pType);
		addProductSt.setDouble(8, price);
		addProductSt.executeUpdate();
	}

	public void removeProduct(long pID) throws SQLException{
		removeProductSt.setLong(1, pID);
		removeProductSt.executeUpdate();
	}

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


	public ArrayList<product> searchProdBy(String type, String query) throws SQLException{
		searchProductSt.setString(1, type);
		searchProductSt.setString(2, query);
		ResultSet rs = searchProductSt.executeQuery();

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
}