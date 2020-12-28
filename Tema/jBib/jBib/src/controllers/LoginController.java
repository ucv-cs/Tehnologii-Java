package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

	@FXML
	private void ShowDashboard(/* ActionEvent event */) throws Exception {
		info.setText("information text about the login");

		Parent window = FXMLLoader.load(getClass().getResource("../views/dashboard.fxml"));
		Scene dashboard = new Scene(window);

		Stage stage = new Stage();//(Stage) login.getScene().getWindow();
		stage.setScene(dashboard);
		stage.initStyle(StageStyle.DECORATED);
		stage.show();
	}
}
