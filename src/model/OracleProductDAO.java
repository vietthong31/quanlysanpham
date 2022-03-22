package model;

import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OracleProductDAO implements ProductDAO {
	
	private Connection conn;
	
	public OracleProductDAO() {
		conn = DBConnection.getConnection();
	}

	@Override
	public Category getCategory(SearchType type, String value) {
		Category cate = null;
		try {
			String query = "SELECT * FROM danhmuc ";
			switch (type) {
			case CATEGORYID:
				query += "WHERE iddanhmuc = ?";
				break;
			case CATEGORYNAME:
				query += "WHERE tendanhmuc = ?";
				break;
			default:
				query += "WHERE !?";
				break;
			}
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, value);
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				cate = new Category(result.getString(1), result.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cate;
	}

	@Override
	public ObservableList<Category> getCategories() {
		ObservableList<Category> list = FXCollections.observableArrayList();
		try {
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM danhmuc");
			while (result.next()) {
				Category cat = new Category(result.getString(1), result.getString(2));
				list.add(cat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ObservableList<Product> getProduct(SearchType type, String value) {
		ObservableList<Product> list = FXCollections.observableArrayList();
		try {
			String query = "SELECT * FROM SANPHAM ";
			switch (type) {
			case PRODUCTID:
				query += "WHERE idsanpham = ?";
				break;
			case CATEGORYID:
				query += "WHERE iddanhmuc = ?";
				break;
			}
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, value);
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				Product cat = new Product(result.getString(1), result.getString(2), result.getDouble(3), result.getInt(4), result.getString(5));
				list.add(cat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ObservableList<Product> getProducts() {
		ObservableList<Product> list = FXCollections.observableArrayList();
		try {
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM sanpham");
			while (result.next()) {
				Product cat = new Product(result.getString(1), result.getString(2), result.getDouble(3), result.getInt(4), result.getString(5));
				list.add(cat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public Category getCategoryOfProduct(String productID) {
		Product p = this.getProduct(SearchType.PRODUCTID, productID).get(0);
		Category cate = new Category();
		if (p != null) {
			try {
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM danhmuc WHERE iddanhmuc = (SELECT iddanhmuc FROM sanpham WHERE idsanpham = ?)");
				stmt.setString(1, productID);
				ResultSet result = stmt.executeQuery();
				while (result.next()) {
					cate = new Category(result.getString(1), result.getString(2));
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return cate;
	}
	
	public boolean insertCategory(String id, String name) {
		String insertStr = "INSERT INTO danhmuc VALUES (?, ?)";
		boolean inserted = false;
		try {
			PreparedStatement insertStmt = conn.prepareStatement(insertStr);
			insertStmt.setString(1, id);
			insertStmt.setString(2, name);
			int rowCount = insertStmt.executeUpdate();
			inserted = rowCount > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return inserted;
	}
	
	public boolean updateCategory(String id, String name) {
		String updateString = "UPDATE danhmuc SET tendanhmuc = ? WHERE iddanhmuc = ?";
		int updatedRow = 0;
		try {
			PreparedStatement updateStmt = conn.prepareStatement(updateString);
			updateStmt.setString(1, name);
			updateStmt.setString(2, id);
			updatedRow = updateStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (updatedRow > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean removeCategory(Category item) {
		String deleteStr = "DELETE FROM danhmuc WHERE iddanhmuc = ?";
		boolean deleted = false;
		try {
			PreparedStatement deleteStmt = conn.prepareStatement(deleteStr);
			deleteStmt.setString(1, item.getCategoryID());
			int rowCount = deleteStmt.executeUpdate();
			deleted = rowCount > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return deleted;
	}
	
	public boolean insertProduct(String id, String name, double price, int quantity, String desc, String iddanhmuc) {
		String insertStr = "INSERT INTO sanpham VALUES (?, ?, ?, ?, ?, ?)";
		boolean inserted = false;
		System.out.println(quantity);
		try {
			PreparedStatement insertStmt = conn.prepareStatement(insertStr);
			insertStmt.setString(1, id);
			insertStmt.setString(2, name);
			insertStmt.setDouble(3, price);
			insertStmt.setInt(4, quantity);
			insertStmt.setString(5, desc);
			insertStmt.setString(6, iddanhmuc);
			int rowCount = insertStmt.executeUpdate();
			inserted = rowCount > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return inserted;
	}
	
	public boolean updateProduct(String id, String name, double price, int quantity, String desc) {
		String updateString = "UPDATE sanpham SET tensanpham = ?, gia = ?, soluong = ?, mota = ? WHERE idsanpham = ?";
		int updatedRow = 0;
		try {
			PreparedStatement updateStmt = conn.prepareStatement(updateString);
			updateStmt.setString(1, name);
			updateStmt.setDouble(2, price);
			updateStmt.setInt(3, quantity);
			updateStmt.setString(4, desc);
			updateStmt.setString(5, id);
			updatedRow = updateStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (updatedRow > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean removeProduct(Product item) {
		String deleteStr = "DELETE FROM sanpham WHERE idsanpham = ?";
		boolean deleted = false;
		try {
			PreparedStatement deleteStmt = conn.prepareStatement(deleteStr);
			deleteStmt.setString(1, item.getProductID());
			int rowCount = deleteStmt.executeUpdate();
			deleted = rowCount > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return deleted;
	}

}
