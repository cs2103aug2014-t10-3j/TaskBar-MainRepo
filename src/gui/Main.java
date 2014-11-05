//@author A0116760J
package gui;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;


public class Main extends Application {
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
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void init() {
		Font.loadFont(getClass().getResource("../resources/segoeuil.ttf").toExternalForm(), 12);
		Font.loadFont(getClass().getResource("../resources/seguisb.ttf").toExternalForm(), 12);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
