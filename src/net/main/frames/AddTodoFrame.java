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


import net.main.Todo;

public class AddTodoFrame extends JDialog implements ActionListener {
	
	String username;
	Todo[] todos;
	JTextField name;
	JButton submitBtn;
	public Todo todo;
	
	public AddTodoFrame(Frame owner, String title, boolean modal, Todo[] todos, String username) {
		super(owner, title, modal);
		this.username = username;
		this.todos = todos;
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
	
		
		submitBtn = new JButton("Submit");
		submitBtn.setBounds(100,100,80,30);
		submitBtn.addActionListener(this);
		
		add(name);
		add(submitBtn);
		setSize(350,200);
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
			Todo[] temp = new Todo[todos.length+1];
			for(int i=0; i<todos.length; i++) {
				temp[i] = todos[i];
			}
			temp[todos.length] = new Todo(name.getText());
			todos  = temp;
			
			try {
				new FileOutputStream(username + "_todos.ser").close();
				FileOutputStream fileOut = new FileOutputStream(username + "_todos.ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				for(Todo td : todos) {
					out.writeObject(td);
				}
				out.close();
				fileOut.close();
			} catch (IOException i) {
				i.printStackTrace();
			}
		} 
		this.dispose();
	}
	
	
	
}
