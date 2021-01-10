package jlib;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jlib.utils.Database;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * Application initializer.
 */
public class App extends Application {

	// holds a reference to the current app, for later calls from the controllers
	public static Application app;

	// used for moving the borderless login window
	private double xOffset = 0;
	private double yOffset = 0;

	// constructor used to add the current app reference to the static field
	public App() {
		app = this;
	}

	// application entry point
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// get the database full path and prepare the URL for connection by changing its
		// protocol/scheme to jdbc:sqlite:
		String databaseUrl = getClass().getResource("/jlib/storage/jlib.db").toString()
				.replace("file:", "jdbc:sqlite:");
		databaseUrl = URLDecoder.decode(databaseUrl, StandardCharsets.UTF_8);

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/jlib/views/login.fxml"));
		Parent window = fxmlLoader.load();

		// make a single connection to the database and reuse it throughout the
		// application
		Database.connect(databaseUrl);

		// the login window is borderless
		stage.initStyle(StageStyle.TRANSPARENT);

		// manually handle the window position and the close action:
		// when the mouse is pressed, get its coordinates
		window.setOnMousePressed(event -> {
			xOffset = event.getSceneX();
			yOffset = event.getSceneY();
		});

		// when the mouse is dragged, set the window's coordinates to the new position
		window.setOnMouseDragged(event -> {
			stage.setX(event.getScreenX() - xOffset);
			stage.setY(event.getScreenY() - yOffset);
		});

		// window setup
		Scene scene = new Scene(window);
		scene.setFill(Color.TRANSPARENT);
		stage.getIcons().add(new Image("/jlib/resources/logo.png"));
		stage.setTitle("jLib");
		stage.setScene(scene);
		stage.show();
	}
}