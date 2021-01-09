package net.main;

import java.io.Serializable;

public class Note implements Serializable {
	String title;
	String content;
	
	public Note() {
		this.title = "";
		this.content= "";
	}
	public Note(String title, String content) {
		this.title = title;
		this.content = content;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void modifyTitle(String title) {
		this.title = title;
	}
	
	public void modifyContent(String content) {
		this.content = content;
	}

}
