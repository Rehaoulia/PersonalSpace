package net.main;

import java.io.Serializable;
import java.util.ArrayList;

public class Goal implements Serializable {
	
	String name;
	boolean state;
	ArrayList<Milestone> progress;
	int percentage;

	public Goal() {
		this.name = "Name this goal";
		this.state = false;
		this.progress = null;
		this.percentage = 0;
	} 
	
	public Goal(String goalname) {
		this.name = goalname;
		this.state = false;
		this.progress = null;
		this.percentage = 0;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void changeState(boolean state) {
		this.state = state;
	}

	
	public int checkProgress() {
		int count = 0;
		if(progress == null) {
			return 0;
		}
		for(Milestone milestone : progress) {
			if(milestone.getState() == true) {
				count += 1;
			}
		}
		if(count == this.progress.size()) {
			this.changeState(true);
		}
		this.percentage = (count*100/progress.size());
		return percentage;
	}
	
	public void addProgress(Milestone milestone) {
		if(this.progress == null) {
			this.progress = new ArrayList<Milestone>();
		}
		this.progress.add(milestone);
	}
	
	public void removeProgress(Milestone milestone) {
		Milestone temp=null;
		for(Milestone mil : this.progress) {
			if(mil.getTitle().equals(milestone.getTitle())) {
				temp = mil;
			}
		}
		this.progress.remove(temp);
	}
	
	public ArrayList<Milestone> getProgressList(){
		return this.progress;
	}
}
