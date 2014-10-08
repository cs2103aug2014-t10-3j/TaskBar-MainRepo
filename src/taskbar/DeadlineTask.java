package taskbar;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class DeadlineTask extends Task {
	private LocalDateTime deadline;
	
	public DeadlineTask(String description, ArrayList<String> labels, int importance, LocalDateTime deadline) {
		super(description, labels, importance);
		this.deadline = deadline; 
	}

	public LocalDateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}
}
