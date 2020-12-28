package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController {
	@FXML
	private Button close, login;

	@FXML
	private Label info;

	/**
	 * Closes the app window
	 */
	@FXML
	private void CloseWindow() {
		Stage stage = (Stage) close.getScene().getWindow();
		stage.close();
	}

	/**
	 * After a successful login, display the main window
	 *
	 * @throws Exception
	 */
	@FXML
	private void ShowDashboard() throws Exception {
		info.setText("information text about the login");

		Parent window = FXMLLoader.load(getClass().getResource("../views/dashboard.fxml"));
		Scene dashboard = new Scene(window);

		// close the login stage
		Stage oldStage = (Stage) login.getScene().getWindow();
		oldStage.close();

		// create a new stage
		Stage stage = new Stage();
		stage.setScene(dashboard);
		stage.initStyle(StageStyle.DECORATED);
		stage.getIcons().add(new Image("resources/logo.png"));
		stage.setTitle("jBib - Easy Library Management");
		stage.show();
	}
}
