package net.main;

import java.io.Serializable;

public class Milestone extends Note implements Serializable{
	
	boolean state;
	
	public Milestone(String title, String content, boolean state) {
		super(title, content);
		this.state =  state;
	}
	
	public boolean getState() {
		return this.state;
	}
	
	public void setState(boolean bool) {
		this.state=bool;
	}
}
