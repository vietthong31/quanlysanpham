package model;

import java.sql.*;



import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataModel {
	
	private OracleProductDAO productDAO;
	private static ObservableList<Category> categories;
	private static ObservableList<Product> products;
	private ObservableList<String> nameList;
	private ObjectProperty<Product> selectedProduct = new SimpleObjectProperty<>();
	private ObjectProperty<Category> selectedCategory = new SimpleObjectProperty<>();
	
	
	public DataModel() {
		categories = productDAO.getCategories();
		products = productDAO.getProducts();
	}
	
	/**
	 * Constructor DataModel
	 * @param obj - đối tượng tương tác với CSDL.
	 */
	public DataModel(OracleProductDAO obj) {
		this.productDAO = obj;
		categories = productDAO.getCategories();
		products = productDAO.getProducts();
	}
	
	public Category getCategory(SearchType type, String id) {
		Category cate = productDAO.getCategory(type, id);
		return cate;
	}
	
	public Category getCategoryOfProduct(String id) {
		return productDAO.getCategoryOfProduct(id);
	}
	
	public void insertCategory(String id, String name) {
		boolean inserted = productDAO.insertCategory(id, name);
		if (inserted) {
			categories.add(new Category(id, name));
		}
	}
	
	public boolean updateCategory(String id, String name) {
		boolean updated = productDAO.updateCategory(id, name);
		if (updated) {
			for (int i = 0; i < categories.size(); i++) {
				Category cate = categories.get(i);
				if (cate.getCategoryID().equals(id)) {
					cate.setCategoryName(name);
				}
			}
		}
		return updated;
	}
	
	public boolean removeCategory(Category item) {
		boolean removed = productDAO.removeCategory(item);
		if (removed) {
			categories.remove(item);
		}
		return removed;
	}
	
	public ObservableList<Product> getProduct(SearchType type, String id) {
		ObservableList<Product> list = productDAO.getProduct(type, id);
		return list;
	}
	
	public void insertProduct(String id, String name, double price, int quantity, String desc, String iddanhmuc) {
		boolean inserted = productDAO.insertProduct(id, name, price, quantity, desc, iddanhmuc);
		if (inserted) {
			products.add(new Product(id, name, price, quantity, desc));
		}
	}
	
	public boolean updateProduct(String id, String name, double price, int quantity, String desc) {
		boolean updated = productDAO.updateProduct(id, name, price, quantity, desc);
		if (updated) {
			for (int i = 0; i < products.size(); i++) {
				Product prod = products.get(i);
				if (prod.getProductID().equals(id)) {
					prod.setProductName(name);
					prod.setUnitPrice(price);
					prod.setQuantity(quantity);
					prod.setDescription(desc);
				}
			}
		}
		return updated;
	}
	
	public boolean removeProduct(Product item) {
		boolean removed = productDAO.removeProduct(item);
		if (removed) {
			products.remove(item);
		}
		return removed;
	}

	
	// GETTER & SETTER
	public static final ObservableList<Product> getProducts() {
		return products;
	}
	
	public static final ObservableList<Category> getCategories() {
		return categories;
	}


	public final ObjectProperty<Product> selectedProductProperty() {
		return this.selectedProduct;
	}
	

	public final Product getSelectedProduct() {
		return this.selectedProductProperty().get();
	}
	

	public final void setSelectedProduct(final Product selectedProduct) {
		this.selectedProductProperty().set(selectedProduct);
	}
	

	public final ObjectProperty<Category> selectedCategoryProperty() {
		return this.selectedCategory;
	}
	

	public final Category getSelectedCategory() {
		return this.selectedCategoryProperty().get();
	}
	

	public final void setSelectedCategory(final Category selectedCategory) {
		this.selectedCategoryProperty().set(selectedCategory);
	}
	
	
	public final ObservableList<String> getNameList() {
		nameList = FXCollections.observableArrayList();
		for (int i = 0; i < categories.size(); i++) {
			nameList.add(categories.get(i).getCategoryName());
		}
		return nameList;
	}

}
