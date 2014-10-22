package ide;

import taskbar.*;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

public class IDEController implements Initializable{
	@FXML private TextField textbox;
	@FXML private TableView<Data> table;
	@FXML private TableColumn<Data, String> descCol;
	@FXML private TableColumn<Data, String> tagCol;
	@FXML private TableColumn<Data, String> dateCol;
	@FXML private TableColumn<Data, String> timeCol;
	@FXML private Label status;
	
	private DisplayData data = new DisplayData();
	private Controller ctrl = new Controller();
	
	private ObservableList<Data> list = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		descCol.setCellValueFactory(new PropertyValueFactory<Data, String>("desc"));
		tagCol.setCellValueFactory(new PropertyValueFactory<Data, String>("tag"));
		dateCol.setCellValueFactory(new PropertyValueFactory<Data, String>("date"));
		timeCol.setCellValueFactory(new PropertyValueFactory<Data, String>("time"));
		
		configureTable();
		
		status.setOpacity(0);
		
		textbox.setOnAction((event) -> {
			data = ctrl.handleEnter(textbox.getText());
			showToUser(data);
			if (data.needToUpdateInputBox()) {
				textbox.setText(data.getInputText());
			} else {
				textbox.clear();
			}			
		});
		
		/*textbox.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String oldText, String newText) {
				data = ctrl.handleKeyTyped(oldText);
				showToUser(data);
			}
		});*/
		
	}
	
	private void configureTable() {
		table.setPlaceholder(new Label("There is nothing to show"));
		table.setItems(list);
		
	}
	
	private void showToUser(DisplayData data) {
		if (data.needToUpdate()) {
			list.clear();
			
			if (data.getListOfTasks()!=null) {
				for (Task t: data.getListOfTasks()) {
					Data newTask = new Data(t);
					list.add(newTask);
				}
			}
		}
		
		status.setText(data.getPrompt());
		status.setOpacity(1);
		fadeStatus();
	}
	
	private void fadeStatus() {
		FadeTransition ft = new FadeTransition(Duration.seconds(1.0),status);
		ft.setToValue(0);
		ft.setFromValue(1);
		ft.setDelay(Duration.seconds(2.0));
		ft.play();
	}
	
}
