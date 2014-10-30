package gui;

import util.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.ResourceBundle;

import com.sun.javafx.scene.control.skin.TableViewSkin;
import com.sun.javafx.scene.control.skin.VirtualFlow;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
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
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
	private SequentialTransition seqTrans;
	private VirtualFlow flow;
	double windowX,windowY;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		addDragListener();
		
		setCloseBtn();
		setClockAndDate();
		
		status.setOpacity(0);
		defineTransition();
		
		configureTable();
		
		setTextboxOnFocus();
		textbox.setOnAction((event) -> {
			data = ctrl.handleEnter(textbox.getText());
			showToUser(data);		
		});
		
		
	}
	
	private void addDragListener() {		
	    pane.setOnMousePressed((event) -> {
	        windowX = pane.getScene().getWindow().getX() - event.getScreenX();
	        windowY = pane.getScene().getWindow().getY() - event.getScreenY();
	    });

	    pane.setOnMouseDragged((event) -> {
	        pane.getScene().getWindow().setX(event.getScreenX() + windowX);
	        pane.getScene().getWindow().setY(event.getScreenY() + windowY);
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

		table.setItems(list);
		data = ctrl.loadAllTasks();
		showToUser(data);
		
		textbox.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				ObservableList<Node> nodes = ((TableViewSkin)table.getSkin()).getChildren();
				if (data.listOfTasksIsEmpty()) {
					return;
				}
				flow = (VirtualFlow) nodes.get(1); 
				int firstVisible = flow.getFirstVisibleCell().getIndex();
				if (event.getCode()==KeyCode.UP) {
					table.scrollTo(firstVisible-1);
				} else if (event.getCode()==KeyCode.DOWN) {
					table.scrollTo(firstVisible+1);
				}
			}
		});
		
		
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
