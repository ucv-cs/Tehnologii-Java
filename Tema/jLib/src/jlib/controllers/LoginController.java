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
	private Connection connection;

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
		connection = Database.connection;
		ResultSet resultSet;
		PreparedStatement statement = connection
				.prepareStatement("SELECT * FROM librarians WHERE username=? AND password=?;");
		statement.setString(1, username.getText());
		statement.setString(2, password.getText());
		resultSet = statement.executeQuery();

		if (resultSet.next()) {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/jlib/views/main.fxml"));
			Parent window = fxmlLoader.load();

			// pass data to MainController: logged in librarian id, name and photo
			MainController mainController = fxmlLoader.getController();
			mainController.currentLibrarianId = Integer.parseInt(resultSet.getString(1));
			mainController.lblLoggedLibrarian.setText(resultSet.getString(4));

			// any exception related to the image URL results in the picture not being
			// dispalyed, but the execution should continue
			ImagePattern imagePattern;
			try {
				Image image = new Image(resultSet.getString(6));
				imagePattern = new ImagePattern(image.isError() ? null : image);
			} catch (Exception e) {
				imagePattern = null;
			}
			mainController.loggedLibrarian.setFill(imagePattern);

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
