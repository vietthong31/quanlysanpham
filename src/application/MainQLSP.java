package application;

import controller.CategoryController;
import controller.MainController;
import controller.ProductController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.DataModel;
import model.OracleProductDAO;

public class MainQLSP extends Application {
	
	private static Stage stage;
	private FXMLLoader mainLoader;
	private FXMLLoader categoryLoader;
	private FXMLLoader productLoader;
	
	@Override
	public void init() throws Exception {
		mainLoader = new FXMLLoader(getClass().getResource("/controller/Main.fxml"));
		categoryLoader = new FXMLLoader(getClass().getResource("/controller/CategoryList.fxml"));
		productLoader = new FXMLLoader(getClass().getResource("/controller/Product.fxml"));
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		
		// Load VBox node gốc
		VBox root = mainLoader.load();
		
		// Lấy controller của Main.fxml
		MainController mainController = mainLoader.getController();
		SplitPane pane = mainController.getSplitPane();
		
		// Load 2 VBox chứa thông tin Danh mục & Sản phẩm
		VBox categoryContainer = categoryLoader.load();
		VBox productContainer = productLoader.load();
		
		// Thêm 2 VBox vào SplitPane (của root)
		pane.getItems().addAll(categoryContainer, productContainer);
		pane.setDividerPositions(0.22);
		
		// Lấy controller gọi initModel
		CategoryController categoryController = categoryLoader.getController();
		ProductController productController = productLoader.getController();
		
		OracleProductDAO productDAO = new OracleProductDAO();
		DataModel model = new DataModel(productDAO);
		categoryController.initModel(model);
		productController.initModel(model);
		
		Scene scene = new Scene(root);
		primaryStage.setTitle("Quản lý sản phẩm");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public static Stage getStage() {
		return stage;
	}
}
