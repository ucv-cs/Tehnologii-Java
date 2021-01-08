package jlib.controllers;

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
import jlib.utils.Database;

/**
 * Controller for the login window.
 */
public class LoginController {
	protected Connection connection;

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
		// make a single connection and reuse it throughout the application
		connection = Database.connect();

		ResultSet resultSet;
		PreparedStatement statement = connection
				.prepareStatement("SELECT * FROM librarians WHERE username=? AND password=?;");
		statement.setString(1, username.getText());
		statement.setString(2, password.getText());
		resultSet = statement.executeQuery();

		if (resultSet.next()) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/jlib/views/main.fxml"));
			Parent window = loader.load();

			MainController mainController = loader.getController();
			mainController.currentLibrarianId = Integer.parseInt(resultSet.getString(1));
			mainController.lblLoggedLibrarian.setText(resultSet.getString(4));

			try {
				Image librarianPhoto = new Image(resultSet.getString(6));
				if (!resultSet.getString(6).isEmpty() && !librarianPhoto.isError()) {
					mainController.loggedLibrarian.setFill(new ImagePattern(librarianPhoto));
				}
			} catch (Exception e) {
				 e.printStackTrace();
			}

			Scene dashboard = new Scene(window);

			// close the login stage
			Stage currentStage = (Stage) login.getScene().getWindow();
			currentStage.close();

			// create a new stage
			Stage stage = new Stage();
			stage.setScene(dashboard);
			stage.initStyle(StageStyle.DECORATED);
			stage.getIcons().add(new Image("/jlib/resources/logo.png"));
			stage.setTitle("jLib - Easy Library Management");
			stage.show();

		} else {
			info.setText("Wrong login information. Try again!");
		}
		statement.close();
	}
}
