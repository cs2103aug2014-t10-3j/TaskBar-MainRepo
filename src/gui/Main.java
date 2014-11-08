//@author A0116760J


package gui;
	
import util.Logging;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

/**
 * Main serves as the starting point of the program, which
 * generates the stage for the GUI and loads all necessary
 * resources, including FXML layout, CSS stylesheets, icon
 * images and font files.
 *
 */
public class Main extends Application {
	/**
	 * The main entry point of the JavaFX application
	 * 
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			AnchorPane root = new AnchorPane();
			root = FXMLLoader.load(getClass().getResource("Layout.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("appearance.css").toExternalForm());
			scene.setFill(null);
			primaryStage.setTitle("ET - Task scheduling made easy!");
			primaryStage.getIcons().add(new Image ("resources/icon.png"));
			primaryStage.setScene(scene);
			primaryStage.show();
			Logging.getInstance().info("Primary stage successfully constructed");
		} catch(Exception e) {
			Logging.getInstance().warning("Failure to construct primary stage");
			e.printStackTrace();
		}
	}
	/**
	 * All initial setup can be done in this method,
	 * before the stage is constructed 
	 * 
	 */
	@Override
	public void init() {
		try {
			Font.loadFont(getClass().getResource("../resources/segoeuil.ttf").toExternalForm(), 12);
			Font.loadFont(getClass().getResource("../resources/seguisb.ttf").toExternalForm(), 12);
			Logging.getInstance().info("Fonts loaded to system");
		} catch (NullPointerException e) {
			Logging.getInstance().warning("Failure to load Fonts resources");
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
