package controller;

import java.util.Optional;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;

public class MainController {
	
	
	@FXML private Text heading;
	@FXML private SplitPane splitPane;
	
	@FXML
	private void initialize() {
		System.out.println("MainController initialize()...");
	}

	@FXML
	public void closeWindow() {
		Alert closeApp = createAlert(AlertType.CONFIRMATION, "", "Thoát chương trình?", "Thoát");
		Optional<ButtonType> OK = closeApp.showAndWait();
		if (OK.isPresent() && OK.get() == ButtonType.OK) {
			Platform.exit();
		}
	}
	
	@FXML
	public void popupAppInfo() {
		Alert info = createAlert(AlertType.INFORMATION, "Ứng dụng quản lý sản phẩm", "", "Về chương trình");
		info.show();
	}
	
	public static Alert createAlert(AlertType type, String header, String content, String title) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		return alert;
	}
	
	public SplitPane getSplitPane() {
		return splitPane;
	}
	
}
