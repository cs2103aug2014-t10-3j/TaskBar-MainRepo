package gui;

import util.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.Duration;
import logic.Controller;

public class GUIController implements Initializable{
	@FXML private AnchorPane outerPane;
	@FXML private AnchorPane pane;
	
	@FXML private Label day;
	@FXML private Label clock;
	@FXML private Label closeBtn;
	
	@FXML private TextField textbox;
	
	@FXML private TableView<Data> table;
	@FXML private TableColumn<Data, String> noCol;
	@FXML private TableColumn<Data, String> descCol;
	@FXML private TableColumn<Data, String> tagCol;
	@FXML private TableColumn<Data, String> dateCol;
	@FXML private TableColumn<Data, String> timeCol;
	
	@FXML private Label status;
	
	private DisplayData data = new DisplayData();
	private Controller ctrl = new Controller();
	
	private ObservableList<Data> list = FXCollections.observableArrayList();
	private SequentialTransition seqTrans ;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		GUIUtility.setDraggable(pane, true);
		
		GUIUtility.setCloseBtn(closeBtn);
		GUIUtility.setClockAndDate(clock, day);
		
		status.setOpacity(0);
		defineTransition();
		
		configureTable();
		
		GUIUtility.setFocus(textbox);
		textbox.setOnAction((event) -> {
			data = ctrl.handleEnter(textbox.getText());
			showToUser(data);		
		});
		textbox.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.isControlDown()) {
					if (event.getCode()==KeyCode.H) {
						
					}
					if (event.getCode()==KeyCode.Z || event.getCode()==KeyCode.Y) {
						data = ctrl.handleHotkey(event);
						showToUser(data);
					}
				}
				
			}
			
		});
	}
	
	private void configureTable() {
		GUIUtility.setClickable(table, false);
		
		noCol.setCellValueFactory(new PropertyValueFactory<Data, String>("order"));
		descCol.setCellValueFactory(new PropertyValueFactory<Data, String>("desc"));
		descCol.setComparator(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareToIgnoreCase(o2);
			}
		});
		tagCol.setCellValueFactory(new PropertyValueFactory<Data, String>("tag"));
		dateCol.setCellValueFactory(new PropertyValueFactory<Data, String>("date"));
		dateCol.setComparator(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				if (o1=="") {
					if (o2=="") {
						return 0;
					} else {
						return 1;
					}						
				} else {
					if (o2=="") {
						return -1;
					} else {
						DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd.MM.yy");
						LocalDate d1 = LocalDate.parse(o1.substring(0, 8), dateFmt);
						LocalDate d2 = LocalDate.parse(o2.substring(0, 8), dateFmt);
						return d1.compareTo(d2);
					}
				}
				
			}
		});
		timeCol.setCellValueFactory(new PropertyValueFactory<Data, String>("time"));
		table.setPlaceholder(new Label("There is nothing to show"));
		table.setRowFactory(new Callback<TableView<Data>, TableRow<Data>>() {
			@Override
			public TableRow<Data> call(TableView<Data> tv) {
				TableRow<Data> row = new TableRow<Data>() {
					@Override
					protected void updateItem(Data task, boolean empty) {
						super.updateItem(task, empty);
						getStyleClass().remove("pastDeadline");
						getStyleClass().remove("nearDeadline");
						Data currentTask = empty ? null : (Data) getItem();
						if (currentTask!=null) {
							Task taskData = task.getTask();
							LocalDate today = LocalDateTime.now().toLocalDate();
							if (taskData.isDeadLineTask()) {
								if (!taskData.getDeadline().toLocalDate().isAfter(today)) {
									getStyleClass().add("pastDeadline");
								} else if (!taskData.getDeadline().toLocalDate().isAfter(today.plusDays(3))) {
									getStyleClass().add("nearDeadline");
								}
							}
						}
					}
				};
				return row;
			}
		});

		table.setItems(list);
		data = ctrl.loadAllTasks();
		showToUser(data);
		
		GUIUtility.setKeyboardScrolling(table, textbox, data);
	}
	
	
	
	private void showToUser(DisplayData data) {
		if (data.needToUpdate()) {
			list.clear();
			
			if (data.getListOfTasks()!=null) {
				for (Task t: data.getListOfTasks()) {
					Data newTask = new Data(t,list.size()+1);
					list.add(newTask);
				}
			}
		}
		if (data.needToUpdateInputBox()) {
			textbox.setText(data.getInputText());
		}
		status.setText(data.getPrompt());
		status.setOpacity(1);
		fadeStatus();
	}
	
	private void fadeStatus() {		
		seqTrans.stop();
		seqTrans.play();
	}
	
	private void defineTransition() {
		FadeTransition ft1 = new FadeTransition(Duration.seconds(4.0), status);
		ft1.setToValue(1);
		ft1.setFromValue(1);
		FadeTransition ft2 = new FadeTransition(Duration.seconds(1.0),status);
		ft2.setToValue(0);
		ft2.setFromValue(1);
		seqTrans = new SequentialTransition(ft1, ft2);
	}
	
}
