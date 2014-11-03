package gui;

import java.time.LocalDate;
import java.time.LocalDateTime;

import util.Task;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class ColorCodedRow<T> implements Callback<TableView<T>, TableRow<T>> {

	@Override
	public TableRow<T> call(TableView<T> tv) {
		TableRow<T> row = new TableRow<T>() {
			@Override
			protected void updateItem(T task, boolean empty) {
				super.updateItem(task, empty);
				getStyleClass().remove("pastDeadline");
				getStyleClass().remove("nearDeadline");
				getStyleClass().remove("done");
				Data currentTask = empty ? null : (Data) getItem();
				LocalDate today = LocalDateTime.now().toLocalDate();
				if (currentTask!=null) {
					Task taskData = ((Data)task).getTask();
					if (taskData.isDone()) {
						getStyleClass().add("done");
					} else {
						if (taskData.isDeadLineTask()) {
							if (!taskData.getDeadline().toLocalDate().isAfter(today)) {
								getStyleClass().add("pastDeadline");
							} else if (!taskData.getDeadline().toLocalDate().isAfter(today.plusDays(3))) {
								getStyleClass().add("nearDeadline");
							}
						}
						if (taskData.isEvent()) {
							if (taskData.getEndTime().toLocalDate().isBefore(today)) {
								getStyleClass().add("pastDeadline");
							}
						}
					}
				}
			}
		};
		return row;
	}
}
