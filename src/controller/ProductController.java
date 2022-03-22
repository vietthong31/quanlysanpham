package controller;

import java.util.Optional;
import java.util.regex.Pattern;

import model.Category;
import model.Product;
import model.SearchType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.DataModel;

public class ProductController {
	
	private DataModel model;
	
	// TableView & TableColumn
	@FXML private TableView<Product> productTableView;
	@FXML private TableColumn<Product, String> idCol;
	@FXML private TableColumn<Product, String> nameCol;
	@FXML private TableColumn<Product, Integer> priceCol;
	@FXML private TableColumn<Product, Integer> quantityCol;
	@FXML private TableColumn<Product, String> descCol;
	
	// Form controls
	@FXML private ChoiceBox<String> choicebox;
	@FXML private TextField productID;
	@FXML private TextField productName;
	@FXML private TextField price;
	@FXML private TextField quantity;
	@FXML private TextArea description;
	
	@FXML private Button saveProductBtn;
	@FXML private Button removeProductBtn;
	
	
	@FXML
	public void initialize() {
		System.out.println("ProductController initialize()...");
		
		productTableView.setPlaceholder(new Text("Không có sản phẩm."));
		// Thiết lập ô giá trị của cột trong bảng
		idCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
		nameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
		priceCol.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
	}
	
	/**
	 * Cập nhật giá trị "selectedProduct" của model, bật nút Remove. 
	 */
	@FXML
	public void clickTableView() {
		Product selected = productTableView.getSelectionModel().getSelectedItem();
		model.setSelectedProduct(selected);
		if (selected != null) {
			removeProductBtn.setDisable(false);
		}
		boolean x = Pattern.matches("\\d*", "2300000");
		System.out.println(x);
	}
	
	/**
	 * - Khởi tạo model <br>
	 * - setItems cho TableView <br>
	 * - Thêm Listener khi chọn danh mục, sản phẩm khác <br>
	 * @param model
	 */
	public void initModel(DataModel model) {
		this.model = model;
		
		// Khởi tạo hiển thị tất cả sản phẩm
		productTableView.setItems(DataModel.getProducts());
		choicebox.setItems(model.getNameList());
		
		model.selectedCategoryProperty().addListener((obs, oldCate, newCate) -> {
			String id = newCate.getCategoryID();
			// Hiển thị sản phẩm trong danh mục được chọn
			productTableView.setItems(model.getProduct(SearchType.CATEGORYID, id));
		});
		
		// setText cho fields khi click 1 dòng sản phẩm.
		model.selectedProductProperty().addListener((obs, oldProduct, currentProduct) -> {
			if (currentProduct != null) {
				Category cate = model.getCategoryOfProduct(currentProduct.getProductID());
				if (cate != null) {
					choicebox.setValue(cate.getCategoryName());
				}
				productID.setText(currentProduct.getProductID());
				productName.setText(currentProduct.getProductName());
				price.setText(currentProduct.getUnitPrice() + "");
				quantity.setText(currentProduct.getQuantity() + "");
				description.setText(currentProduct.getDescription());
			} else {
				choicebox.getSelectionModel().clearSelection();
				this.clearField();
			}
		});
	}
	
	@FXML
	private void createProduct() {
		if (model.getSelectedProduct() != null) {
			productTableView.getSelectionModel().clearSelection();
			model.setSelectedProduct(null);
			choicebox.requestFocus();
			removeProductBtn.setDisable(true);
		}
	}
	
	@FXML
	private void updateProduct() {
		String tendanhmuc = choicebox.getValue();
		String id = productID.getText();
		String name = productName.getText();
		String price = this.price.getText();
		String quantity = this.quantity.getText();
		String desc = description.getText();
		
		boolean valid = validateField(id, name, price, quantity, desc, tendanhmuc);
		if (!valid) {
			Alert warn = MainController.createAlert(AlertType.WARNING, "", "Chưa nhập thông tin", "Nhập thông tin sản phẩm!");
			warn.show();
			return;
		}
		
		double priceNum = Double.parseDouble(price.isEmpty() ? "0.0" : price);
		int quantityNum = Integer.parseInt(quantity.isEmpty() ? "0" : quantity);
		// Tạo sản phẩm mới
		if (model.getSelectedProduct() == null) {
			Category cate = model.getCategory(SearchType.CATEGORYNAME, tendanhmuc);
			String iddanhmuc = cate.getCategoryID();
			model.insertProduct(id, name, priceNum, quantityNum, desc, iddanhmuc);
			this.clearField();
		} else {
			// Cập nhật sản phẩm
			model.updateProduct(id, name, priceNum, quantityNum, desc);
		}
	}
	
	@FXML
	private void removeProduct() {
		Product current = model.getSelectedProduct();
		Alert confirm = MainController.createAlert(AlertType.CONFIRMATION, "", "Xóa " + current.getProductName() + " ?", "Xóa sản phẩm");
		Optional<ButtonType> btnType = confirm.showAndWait();
		btnType.ifPresent(e -> {
			if (e.getButtonData() == ButtonData.OK_DONE) {
				model.removeProduct(current);
				model.setSelectedProduct(productTableView.getSelectionModel().getSelectedItem());
			}
		});
	}
	
	/**
	 * Xác thực giá trị từ các field trong form
	 * @param id ID sản phẩm
	 * @param name Tên sản phẩm
	 * @param price Đơn giá
	 * @param quantity Số lượng
	 * @param desc Mô tả
	 * @param tendanhmuc Tên danh mục
	 * @return boolean
	 */
	private boolean validateField(String id, String name, String price, String quantity, String desc, String tendanhmuc) {
		boolean isValid = true;
		
		if (id.isEmpty() || name.isEmpty() || tendanhmuc.isEmpty()) {
			isValid = false;
		}
		
		boolean rightPrice = Pattern.matches("\\d*", price);
		boolean rightQuantity = Pattern.matches("\\d*", quantity);
		
		System.out.println(rightPrice + " " + rightQuantity);
		
		if (!rightPrice || !rightQuantity) {
			isValid = false;
		}
		
		return isValid;
	}
	
	private void clearField() {
		choicebox.getSelectionModel().clearSelection();
		productID.setText("");
		productName.setText("");
		price.setText("");
		quantity.setText("");
		description.setText("");
	}

}
