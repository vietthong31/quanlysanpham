package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Product {
	private StringProperty productID = new SimpleStringProperty();
	private StringProperty productName = new SimpleStringProperty();
	private DoubleProperty unitPrice = new SimpleDoubleProperty();
	private IntegerProperty quantity = new SimpleIntegerProperty();
	private StringProperty description = new SimpleStringProperty();
	
	public Product() {
		// TODO Auto-generated constructor stub
	}
	
	public Product(String id, String name) {
		setProductID(id);
		setProductName(name);
	}

	public Product(String id, String name, double unitPrice, int quantity, String desc) {
		setProductID(id);
		setProductName(name);
		setUnitPrice(unitPrice);
		setQuantity(quantity);
		setDescription(desc);
	}
	
	@Override
	public String toString() {
		String desc = String.format("ID: %s - Tên: %s", productID, productName);
		return desc;
	}

	public final StringProperty productIDProperty() {
		return this.productID;
	}
	

	public final String getProductID() {
		return this.productIDProperty().get();
	}
	

	public final void setProductID(final String productID) {
		this.productIDProperty().set(productID);
	}
	

	public final StringProperty productNameProperty() {
		return this.productName;
	}
	

	public final String getProductName() {
		return this.productNameProperty().get();
	}
	

	public final void setProductName(final String productName) {
		this.productNameProperty().set(productName);
	}
	

	public final DoubleProperty unitPriceProperty() {
		return this.unitPrice;
	}
	

	public final double getUnitPrice() {
		return this.unitPriceProperty().get();
	}
	

	public final void setUnitPrice(final double unitPrice) {
		this.unitPriceProperty().set(unitPrice);
	}
	

	public final IntegerProperty quantityProperty() {
		return this.quantity;
	}
	

	public final int getQuantity() {
		return this.quantityProperty().get();
	}
	

	public final void setQuantity(final int quantity) {
		this.quantityProperty().set(quantity);
	}
	

	public final StringProperty descriptionProperty() {
		return this.description;
	}
	

	public final String getDescription() {
		return this.descriptionProperty().get();
	}
	

	public final void setDescription(final String description) {
		this.descriptionProperty().set(description);
	}
	
	
	
	
	
}
