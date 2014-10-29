package taskbar;

public class Show extends Command {

	public Show(DisplayData dd, Storage s, String ui) {
		super(dd, s, ui);
	}

	public enum ShowCommandType {
		ALL, TODAY, TOMORROW, DONE, KEYWORD, LABEL;
	}

	@Override
	public boolean execute() {
		switch(Interpreter.interpretShow(userInput)){
		case ALL:
			setDisplayData("Showing all tasks that are not yet done.", storage.getAllNotDoneTasks());
			break;
		case TODAY:
			setDisplayData("Showing all tasks on today.", storage.getTodayTasks());
			break;
		case TOMORROW:
			setDisplayData("Showing all tasks on tomorrow.", storage.getTomorrowTasks());
			break;
		case DONE:
			setDisplayData("Showing all tasks that are already done.", storage.getAllDoneTasks());
			break;
		case KEYWORD:
			setDisplayData("<delete/update/complete> + <number> to perform action on a task in the list.", 
					storage.getTaskByKeyword(Interpreter.getParameter(userInput)));
			break;
		case LABEL:
			setDisplayData("<delete/update/complete> + <number> to perform action on a task in the list.",
					storage.getTaskByLabel(Interpreter.getParameter(userInput).substring(1)));
			break;
		}
		return true;
	}
}
