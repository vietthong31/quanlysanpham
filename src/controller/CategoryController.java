package controller;


import model.Category;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import model.DataModel;

public class CategoryController {
	
	private DataModel model;
	private Dialog<ObservableMap<String, String>> inputDialog;
	private TextField idField;
	private TextField nameField;
	
	@FXML private ListView<Category> categoryListView;
	@FXML private Button newCategoryBtn;
	@FXML private Button updateCategoryBtn;
	@FXML private Button removeCategoryBtn;
	
	public void initModel(DataModel model) {
		this.model = model;
		categoryListView.setItems(DataModel.getCategories());
		
		model.selectedCategoryProperty().addListener((obs, old, current) -> {
			// Cho selectedProduct về null (nếu người dùng chọn 1 sản phẩm ban đầu sau đó chọn danh mục)
			model.setSelectedProduct(null);
		});
	}
	
	@FXML
	private void initialize() {
		System.out.println("CategoryController initialize()...");
		// Tạo Dialog
		GridPane inputForm = new GridPane();
		idField = new TextField();
		nameField = new TextField();
		inputForm.addRow(0, new Label("ID danh mục:"), idField);
		inputForm.addRow(1, new Label("Tên danh mục:"), nameField);
		inputForm.setHgap(10);
		inputForm.setVgap(10);
		
		inputDialog = new Dialog<>();
		inputDialog.getDialogPane().setContent(inputForm);
		inputDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		
		inputDialog.setResultConverter(btn -> {
			if (btn == ButtonType.OK) {
				ObservableMap<String, String> result = FXCollections.observableHashMap();
				result.put("id", idField.getText());
				result.put("name", nameField.getText());
				return result;
			}
			return null;
		});
	}
	
	@FXML
	private void clickListView() {
		Category selected = categoryListView.getSelectionModel().getSelectedItem();
		model.setSelectedCategory(selected);
		updateCategoryBtn.setDisable(false);
		removeCategoryBtn.setDisable(false);
	}
	
	@FXML
	private void createCategory() {
		inputDialog.setTitle("Tạo danh mục mới");
		Optional<ObservableMap<String, String>> input = inputDialog.showAndWait();
		input.ifPresent(map -> {
			String id = map.get("id");
			String name = map.get("name");
			model.insertCategory(id, name);
			idField.clear();
			nameField.clear();
		});
	}
	
	@FXML
	private void updateCategory() {
		inputDialog.setTitle("Cập nhật danh mục");
		Category selected = model.getSelectedCategory();
		idField.setText(selected.getCategoryID());
		nameField.setText(selected.getCategoryName());
		idField.setDisable(true);
		Optional<ObservableMap<String, String>> input = inputDialog.showAndWait();
		input.ifPresent(map -> {
			String id = map.get("id");
			String name = map.get("name");
			boolean updated = model.updateCategory(id, name);
			if (updated) {
				categoryListView.refresh();
			}
		});
	}
	
	@FXML
	public void removeCategory() {
		String catName = model.getSelectedCategory().getCategoryName();
		String content = "Xóa " + catName + " và sản phẩm cùng danh mục?";
		Alert removeConfirm = MainController.createAlert(AlertType.CONFIRMATION, "", content, "Xóa danh mục");
		Optional<ButtonType> btn = removeConfirm.showAndWait();
		btn.ifPresent(e -> {
			if (e.getButtonData() == ButtonData.OK_DONE) {
				model.removeCategory(model.getSelectedCategory());
			}
		});
	}
	
}
