//@author A0116760J
package gui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import util.DisplayData;
import util.Logging;

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
import javafx.util.Duration;

/**
 * A library class that provides static methods used for
 * setting up the GUI in the primary stage
 *
 */
public class GUIUtility {
	/**
	 * Sets up the node for dragging using the mouse. <p>
	 * This method is useful for dragging an undecorated stage
	 * 
	 * @param node the node under effect
	 * @param draggable <code>node</code> can be dragged if <code>draggable</code> is <code>true</code>
	 */
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
	
	/**
	 * Sets up a <code>Label</code> objects to be used as the Close button
	 * 
	 * @param closeBtn the <code>Label</code> object to be used
	 */
	public static void setCloseBtn(Label closeBtn) {
		closeBtn.setOnMouseClicked((event) -> {
			Logging.getInstance().info("Program closed");
			Platform.exit();	
		});
	}
	

	/**
	 * Sets up two <code>Label</code> objects to be used as the Date and Time display
	 * 
	 * @param clock the <code>Label</code> object used for displaying time
	 * @param day the <code>Label</code> object used for displaying date
	 */
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
	
	
	/**
	 * Sets up a <code>Label</code> object to be used as the visibility
	 * switch of another node. The text of the label changes accordingly
	 * 
	 * @param button the <code>Label</code> object used as the button
	 * @param target the node under control
	 * @param showText prompt text on the <code>Label</code> for show
	 * @param hideText prompt text on the <code>Label</code> for hide
	 */
	public static void setHelpBtn(Label button, Node target, String showText, String hideText) {
		button.setOnMouseClicked((event) -> {
			toggleVisibility(button, target, showText, hideText);
		});
	}
	
	/**
	 * Sets the cursor focus to be on the specified <code>Node</code>
	 * 
	 * @param node the <code>Node</code> to be focused on
	 */
	public static void setFocus(Node node) {
		Platform.runLater(new Runnable() {			
			@Override
			public void run() {
				node.requestFocus();
			}
		});
	}
	
	/**
	 * Determines if the specified <code>node</code> can be clicked
	 * using the mouse
	 * 
	 * @param node the <code>Node</code> under control
	 * @param clickable if <code>false</code>, makes the node invisible to mouse clicks
	 */
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
	
	/**
	 * Sets up a remote scrolling system for a <code>TableView</code>\
	 * The <code>table</code> can be scrolled using up/down arrow keys while
	 * focus is on the <code>source</code>
	 * 
	 * @param table the <code>TableView</code> under control
	 * @param source the controller source of the table
	 * @param data contains information of the data being displayed by the table 
	 */
	
	@SuppressWarnings("rawtypes")
	public static void setKeyboardScrolling(TableView table, Node source, DisplayData data) {
		SimpleIntegerProperty checkFirstVisible = new SimpleIntegerProperty(-1);
		
		source.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@SuppressWarnings("unchecked")
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
	
	/**
	 * Defines a custom fade transition where a node is shown for
	 * a period of time before being faded to transparency
	 * 
	 * @param node		the node under control
	 * @param showTime	number of seconds for which the node is shown 
	 * @param fadeTime	number of seconds for which the fading lasts 
	 * @return the desired transition as a <code>SequentialTransition</code>
	 */
	public static SequentialTransition defineTransition(Node node, double showTime, double fadeTime) {
		FadeTransition ft1 = new FadeTransition(Duration.seconds(showTime), node);
		ft1.setToValue(1);
		ft1.setFromValue(1);
		FadeTransition ft2 = new FadeTransition(Duration.seconds(fadeTime),node);
		ft2.setToValue(0);
		ft2.setFromValue(1);
		SequentialTransition seqTrans = new SequentialTransition(ft1, ft2);
		return seqTrans;
	}
	
	/**
	 * Toggles the visibility of the <code>target</code> while
	 * changing the text of the <code>button</code> accordingly
	 * 
	 * @param button	the controller button
	 * @param target	the node under control
	 * @param showText prompt text on the <code>button</code> for show
	 * @param hideText prompt text on the <code>button</code> for hide
	 */
	public static void toggleVisibility(Label button, Node target, String showText, String hideText) {
		if (!target.visibleProperty().get()) {
			target.setVisible(true);
			button.setText(hideText);
		} else {
			target.setVisible(false);
			button.setText(showText);
		}
	}
}
