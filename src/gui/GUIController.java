package gui;

import taskbar.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.Duration;

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
	double x,y;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		addDragListener();
		
		setCloseBtn();
		setClockAndDate();
		
		configureTable();
		
		status.setOpacity(0);
		
		setTextboxOnFocus();
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
	
	private void addDragListener() {		
	    pane.setOnMousePressed((event) -> {
	        x = pane.getScene().getWindow().getX() - event.getScreenX();
	        y = pane.getScene().getWindow().getY() - event.getScreenY();
	    });

	    pane.setOnMouseDragged((event) -> {
	        pane.getScene().getWindow().setX(event.getScreenX() + x);
	        pane.getScene().getWindow().setY(event.getScreenY() + y);
	    });
	}
	
	private void setCloseBtn() {
		closeBtn.setOnMouseClicked((event) -> {
			Platform.exit();			
		});
	}
	
	private void setClockAndDate() {
		DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("  EEE, dd MMM yy");
		DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("  HH:mm:ss");
		Timeline tline = new Timeline(new KeyFrame(Duration.seconds(0),
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						LocalDateTime now = LocalDateTime.now();
						clock.setText(now.format(timeFmt));
						day.setText(now.format(dateFmt));				
					}
				}
			), new KeyFrame(Duration.seconds(1))
		);
		tline.setCycleCount(Animation.INDEFINITE);
		tline.play();
	};
	
	private void configureTable() {
		table.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent ev) {
				ev.consume();
			}
		});
		
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
						return -1;
					}						
				} else {
					if (o2=="") {
						return 1;
					} else {
						DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd.MM.yy");
						LocalDate d1 = LocalDate.parse(o1.substring(0, 8), dateFmt);
						LocalDate d2 = LocalDate.parse(o2.substring(0, 8), dateFmt);
						return d1.compareTo(d2);
					}
				}
				
			}
		});
		
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
								} else if (!taskData.getDeadline().toLocalDate().isAfter(today.plusDays(2))) {
									getStyleClass().add("nearDeadline");
								}
							}
						}
					}
				};
				return row;
			}
		});
		timeCol.setCellValueFactory(new PropertyValueFactory<Data, String>("time"));
		table.setItems(list);
		
		showToUser(ctrl.loadAllTasks());
	}
	
	private void setTextboxOnFocus() {
		Platform.runLater(new Runnable() {			
			@Override
			public void run() {
				textbox.requestFocus();
			}
		});
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
