//@author A0116760J
package gui;

import util.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.ResourceBundle;

import javafx.animation.SequentialTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextFlow;
import logic.Controller;

/**
 * The controller class for the entire GUI. The class is in charge of
 * transferring the user input to the main logic of the programme and
 * displays the feedback to users
 * 
 */
public class GUIController implements Initializable{
	@FXML private AnchorPane outerPane;
	@FXML private AnchorPane pane;
	
	@FXML private Label day;
	@FXML private Label clock;
	@FXML private Label closeBtn;
	
	@FXML private TextField textbox;
	
	@FXML private TableView<TableData> table;
	@FXML private TableColumn<TableData, String> noCol;
	@FXML private TableColumn<TableData, String> descCol;
	@FXML private TableColumn<TableData, String> tagCol;
	@FXML private TableColumn<TableData, String> dateCol;
	@FXML private TableColumn<TableData, String> timeCol;
	
	@FXML private Label status;
	
	@FXML private Label helpBtn;
	@FXML private TextFlow helpText;
	
	private DisplayData data = new DisplayData();
	private Controller ctrl = new Controller();
	
	private ObservableList<TableData> list = FXCollections.observableArrayList();
	private SequentialTransition seqTrans ;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		GUIUtility.setDraggable(pane, true);
		
		GUIUtility.setCloseBtn(closeBtn);
		GUIUtility.setClockAndDate(clock, day);
		GUIUtility.setHelpBtn(helpBtn, helpText, "Show help", "Hide help");
		
		status.setOpacity(0);
		seqTrans = GUIUtility.defineTransition(status,4,1);
		
		configureTable();
		
		helpText.setVisible(false);
		
		GUIUtility.setFocus(textbox);
		
		//
		textbox.setOnAction((event) -> {
			Logging.getInstance().info("Command entered \"" + textbox.getText() + "\"");
			data = ctrl.handleEnter(textbox.getText());
			showToUser(data);		
		});
		
		textbox.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.isControlDown()) {
					if (event.getCode()==KeyCode.H) {
						Logging.getInstance().info("Ctrl-H pressed");
						GUIUtility.toggleVisibility(helpBtn, helpText, "Show help", "Hide help");
					}
					if (event.getCode()==KeyCode.Z || event.getCode()==KeyCode.Y) {
						Logging.getInstance().info("Ctrl-" + event.getCode() + " pressed");
						data = ctrl.handleHotkey(event);
						showToUser(data);
					}
				}
			}
		});
		
	}
	
	/**
	 * Sets up the <code>TableView</code> for displaying the tasks list.
	 * This includes setting up the data and the format of display, implementing
	 * color coding and some other operations
	 * 
	 */
	private void configureTable() {
		try {
			table.setItems(list);
			data = ctrl.loadAllTasks();
						
			showToUser(data);
			table.setRowFactory(new ColorCodedRow<>());
			
			GUIUtility.setClickable(table, false);
			
			noCol.setCellValueFactory(new PropertyValueFactory<TableData, String>("order"));
			descCol.setCellValueFactory(new PropertyValueFactory<TableData, String>("desc"));
			descCol.setComparator(new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					return o1.compareToIgnoreCase(o2);
				}
			});
			tagCol.setCellValueFactory(new PropertyValueFactory<TableData, String>("tag"));
			dateCol.setCellValueFactory(new PropertyValueFactory<TableData, String>("date"));
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
			timeCol.setCellValueFactory(new PropertyValueFactory<TableData, String>("time"));
			table.setPlaceholder(new Label("There is nothing to show"));
			
			GUIUtility.setKeyboardScrolling(table, textbox, data);
			Logging.getInstance().info("Table view successfully configured");
		} catch (Exception e) {
			Logging.getInstance().warning("Table view configuration fails");
			e.printStackTrace();
		}
	}
	
	/**
	 * Produces the visible feedback to the users using the <code>DisplayData</code>
	 * object returned by the logic
	 * 
	 * @param data an object containing information about how to show feedback to user
	 */
	private void showToUser(DisplayData data) {
		assert data!=null;
		
		if (data.needToUpdate()) {
			list.clear();
			
			if (data.getListOfTasks()!=null) {
				for (Task t: data.getListOfTasks()) {
					TableData newTask = new TableData(t,list.size()+1);
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
	
}
