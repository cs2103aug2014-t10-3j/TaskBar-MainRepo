package util;

public enum CommandType {
	NONE,
	ADD, DELETE, UPDATE, COMPLETE,
	SHOW, //this type is recognized also when no command keyword is present.
	UNDO
}