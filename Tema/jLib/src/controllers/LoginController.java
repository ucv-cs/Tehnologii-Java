package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController {
	@FXML
	private Button close, login;

	@FXML
	private TextField username;

	@FXML
	private PasswordField password;

	@FXML
	private Label info;

	/**
	 * Closes the app window
	 */
	@FXML
	private void closeWindow() {
		Platform.exit();
	}

	/**
	 * After a successful login, display the main window
	 *
	 * @throws Exception
	 */
	@FXML
	private void showDashboard() throws Exception {

		if (true) {// username.getText().equals("admin") && password.getText().equals("pass")) {
			Parent window = FXMLLoader.load(getClass().getResource("../views/main.fxml"));
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
		} else {
			info.setText("Wrong login information. Try again!");
		}

	}
}
