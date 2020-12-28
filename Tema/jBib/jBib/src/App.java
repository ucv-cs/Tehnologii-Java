import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.DbUtil;

public class App extends Application {
	private Stage stage;
	private double xOffset = 0;
	private double yOffset = 0;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// the login window is borderless, so we need to handle manually the window
		// position and the close action
		Parent window = FXMLLoader.load(getClass().getResource("views/login.fxml"));

		this.stage = stage;

		//var window = ChangeScene("login");
		stage.initStyle(StageStyle.TRANSPARENT);

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
		stage.getIcons().add(new Image("resources/logo.png"));
		stage.setTitle("jBib");
		stage.setScene(scene);
		stage.show();
	}

	public Parent ChangeScene(String fxml) throws Exception {
		Parent window = FXMLLoader.load(getClass().getResource("views/" + fxml + ".fxml"));
		stage.getScene().setRoot(window);
		return window;
	}
}