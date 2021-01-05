package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.Database;

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
	 * Closes the app window.
	 */
	@FXML
	private void closeWindow() {
		Platform.exit();
	}

	/**
	 * After a successful login, display the main window.
	 *
	 * @throws Exception
	 */
	@FXML
	private void showDashboard() throws Exception {
		Connection connection = Database.connect();
		ResultSet result = null;
		PreparedStatement statement = connection
				.prepareStatement("SELECT * FROM librarians WHERE username=? AND password=?;");
		statement.setString(1, username.getText());
		statement.setString(2, password.getText());
		result = statement.executeQuery();

		if (result.next()) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/main.fxml"));
			Parent window = loader.load();

			MainController mainController = loader.getController();
			mainController.currentLibrarianId = Integer.parseInt(result.getString(1));
			mainController.lblLoggedLibrarian.setText(result.getString(4));
			if (result.getString(6) != null) {
				mainController.loggedLibrarian.setFill(new ImagePattern(new Image("file:" + result.getString(6))));
			}

			Scene dashboard = new Scene(window);

			// close the login stage
			Stage currentStage = (Stage) login.getScene().getWindow();
			currentStage.close();

			// create a new stage
			Stage stage = new Stage();
			stage.setScene(dashboard);
			stage.initStyle(StageStyle.DECORATED);
			stage.getIcons().add(new Image("resources/logo.png"));
			stage.setTitle("jLib - Easy Library Management");
			stage.show();

		} else {
			info.setText("Wrong login information. Try again!");
		}
		statement.close();
	}
}
