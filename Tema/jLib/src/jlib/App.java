package jlib;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Application initializer.
 */
public class App extends Application {

	// holds a reference to the current app, for later calls from the controllers
	public static Application app;

	// used for moving the borderless login window
	private double xOffset = 0;
	private double yOffset = 0;

	public App() {
		app = this;
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// the login window is borderless
		Parent window = FXMLLoader.load(getClass().getResource("/jlib/views/login.fxml"));
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