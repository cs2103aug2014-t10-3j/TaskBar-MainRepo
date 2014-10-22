package taskbar;

public enum CommandType {
	ADD, DELETE, UPDATE, COMPLETE,
	SHOW, //this type is recognized also when no command keyword is present.
	UNDO
}