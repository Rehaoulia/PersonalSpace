package net.main.frames;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

import net.main.Goal;
import net.main.Milestone;
import net.main.Note;

public class AddFrame extends JDialog implements ActionListener {
	
	String username;
	public Goal openedGoal;
	Goal[] goals;
	Note[] notes;
	JTextField name, content;
	JButton submitBtn;
	public Object object;
	String type;
	
	public AddFrame(Frame owner, String title, boolean modal, String type, Note[] notes, Goal[] goals, Goal openedGoal, String username) {
		super(owner, title, modal);
		this.username = username;
		this.openedGoal = openedGoal;
		this.goals = goals;
		this.notes = notes;
		this.type = type;
		name = new JTextField("Name");
		name.setBounds(20,20,300,30);
		name.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(name.getText().equals("Name")) {
				name.setText("");
				}
			}
		});
		
		content = new JTextField("Content");
		content.setBounds(20,50,300,200);
		content.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(content.getText().equals("Content")) {
				content.setText("");
				}
			}
		});
		
		submitBtn = new JButton("Submit");
		submitBtn.setBounds(100,400,80,30);
		submitBtn.addActionListener(this);
		
		add(name);
		add(content);
		add(submitBtn);
		setSize(350,600);
		setLayout(new BorderLayout());
		setResizable(false);
		setLocationRelativeTo(null);
	}
	
	public void create() {
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == submitBtn) {
			if(type.equals("Milestone")) {
				object = (Milestone) new Milestone(name.getText(), content.getText(), false);
				for(Goal goal : goals) {
					if(goal.getName().equals(openedGoal.getName())) {
						goal.addProgress((Milestone) this.object);
						openedGoal.addProgress((Milestone) this.object);
					}
				}
				try {
					new FileOutputStream(username + "_goals.ser").close();
					FileOutputStream fileOut = new FileOutputStream(username + "_goals.ser");
					ObjectOutputStream out = new ObjectOutputStream(fileOut);
					for(Goal goal : goals) {
						out.writeObject(goal);
					}
					out.close();
					fileOut.close();
				} catch (IOException i) {
					i.printStackTrace();
				}
			} else if(type.equals("Note")) {
				object = (Note) new Note(name.getText(), content.getText());
				Note[] temp = new Note[notes.length+1];
				for(int i=0; i<notes.length; i++) {
					temp[i]=notes[i];
				}
				temp[notes.length] = (Note) object;
				notes=temp;
				try {
					new FileOutputStream(username + "_notes.ser").close();
					FileOutputStream fileOut = new FileOutputStream(username + "_notes.ser");
					ObjectOutputStream out = new ObjectOutputStream(fileOut);
					for(Note note : notes) {
						out.writeObject(note);
					}
					out.close();
					fileOut.close();
				} catch (IOException i) {
					i.printStackTrace();
				}
			}
		} 
		this.dispose();
	}
	
	
	
}
