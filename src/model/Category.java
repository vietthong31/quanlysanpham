package model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Category {
	
	private StringProperty categoryID = new SimpleStringProperty();
	private StringProperty categoryName = new SimpleStringProperty();

	
	public Category() {
	}
	
	@Override
	public String toString() {
		return getCategoryName();
	}
	
	public Category(String id, String name) {
		this.categoryID = new SimpleStringProperty(id);
		this.categoryName = new SimpleStringProperty(name);
	}
	
	// GETTERS & SETTERS
	public final StringProperty categoryNameProperty() {
		return this.categoryName;
	}
	

	public final String getCategoryName() {
		return this.categoryNameProperty().get();
	}
	

	public final void setCategoryName(final String categoryName) {
		this.categoryNameProperty().set(categoryName);
	}

	public final StringProperty categoryIDProperty() {
		return this.categoryID;
	}
	

	public final String getCategoryID() {
		return this.categoryIDProperty().get();
	}
	

	public final void setCategoryID(final String categoryID) {
		this.categoryIDProperty().set(categoryID);
	}
	
	
}
