package net.main;

public class Todo extends Goal{
	
	public Todo(String name){
		this.name = name;
		this.state = false;
	}
	
	public boolean getState() {
		return this.state;
	}
}
