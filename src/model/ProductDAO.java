package model;

import javafx.collections.ObservableList;

public interface ProductDAO {
	public Category getCategory(SearchType type, String value);
	public ObservableList<Category> getCategories();
	public ObservableList<Product> getProduct(SearchType type, String value);
	public ObservableList<Product> getProducts();
}
