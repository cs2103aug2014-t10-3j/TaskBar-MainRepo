//@author A0116760J
package gui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import util.DisplayData;


import com.sun.javafx.scene.control.skin.TableViewSkin;
import com.sun.javafx.scene.control.skin.VirtualFlow;


import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GUIUtility {
	public static void setDraggable(Node node, boolean draggable) {		
		if (draggable) {
			SimpleDoubleProperty x = new SimpleDoubleProperty();
			SimpleDoubleProperty y = new SimpleDoubleProperty();
			node.setOnMousePressed((event) -> {
		        double windowX = node.getScene().getWindow().getX() - event.getScreenX();
		        double windowY = node.getScene().getWindow().getY() - event.getScreenY();
		        x.set(windowX);
		        y.set(windowY);
		    });

		    node.setOnMouseDragged((event) -> {
		        node.getScene().getWindow().setX(event.getScreenX() + x.get());
		        node.getScene().getWindow().setY(event.getScreenY() + y.get());
		    });
		}
	}
	
	public static void setCloseBtn(Label closeBtn) {
		closeBtn.setOnMouseClicked((event) -> {
			Platform.exit();
			
		});
	}
	
	public static void setClockAndDate(Label clock, Label day) {
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
	}
	
	public static void setHelpBtn(Label button, Node target) {
		button.setOnMouseClicked((event) -> {
			toggleVisibility(button, target);
		});
	}
	
	public static void setFocus(Node node) {
		Platform.runLater(new Runnable() {			
			@Override
			public void run() {
				node.requestFocus();
			}
		});
	}
	
	public static void setClickable(Node node, boolean clickable) {
		if (!clickable) {
			node.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent ev) {
					ev.consume();
				}
			});
		}
	}
	
	public static void setKeyboardScrolling(TableView table, Node source, DisplayData data) {
		SimpleIntegerProperty checkFirstVisible = new SimpleIntegerProperty(-1);
		
		source.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				ObservableList<Node> nodes = ((TableViewSkin)table.getSkin()).getChildren();
				if (data.listOfTasksIsEmpty()) {
					return;
				}
				VirtualFlow flow = (VirtualFlow) nodes.get(1); 
				int firstVisible = flow.getFirstVisibleCell().getIndex();
				if (event.getCode()==KeyCode.UP) {
					table.scrollTo(firstVisible-1);
				} else if (event.getCode()==KeyCode.DOWN) {
					if (firstVisible!=checkFirstVisible.get())
						table.scrollTo(firstVisible+1);
					else
						table.scrollTo(firstVisible+2);
					checkFirstVisible.set(firstVisible);
				}
			}
		});
	}
	
	public static void defineTransition(SequentialTransition seqTrans, Node node) {
		FadeTransition ft1 = new FadeTransition(Duration.seconds(4.0), node);
		ft1.setToValue(1);
		ft1.setFromValue(1);
		FadeTransition ft2 = new FadeTransition(Duration.seconds(1.0),node);
		ft2.setToValue(0);
		ft2.setFromValue(1);
		seqTrans = new SequentialTransition(ft1, ft2);
	}
	
	public static void toggleVisibility(Label button, Node target) {
		if (!target.visibleProperty().get()) {
			target.setVisible(true);
			button.setText("Hide help");
		} else {
			target.setVisible(false);
			button.setText("Show help");
		}
	}
}
