package taskbar;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event extends Task {
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	
	public Event(String discription, ArrayList<String> labels, int importance, LocalDateTime start, 
			LocalDateTime end) {
		super(discription, labels, importance);
		startTime = start;
		endTime = end;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

}
