package net.main.tabs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.*;

import net.main.Milestone;
import net.main.Note;
import net.main.Todo;
import net.main.frames.AddTodoFrame;

public class ToDoTab extends JPanel implements ActionListener{
	
	Todo[] todosArray;
	JComboBox<Todo> todoList;
	String loggedInUser;
	JToggleButton toggleBtn;
	JButton addBtn, deleteBtn;
	
	public ToDoTab(String loggedInUser) {
		
		this.loggedInUser = loggedInUser;
		
		todoList = new JComboBox<Todo>(new DefaultComboBoxModel<Todo>(getTodos()));
		todoList.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				Todo todo = (Todo) value;
				setText(todo.getName());
				setToggleButton(toggleBtn, todo);
				return this;
			}
		});
		todoList.setBounds(40,50,400,30);      
        
		toggleBtn = new JToggleButton();
		toggleBtn.setBounds(490,50,30,30);
		toggleBtn.addActionListener(this);
		
		addBtn = new JButton("Add");
		addBtn.setBounds(170,270,80,30);
		addBtn.addActionListener(this);
		
		deleteBtn = new JButton("Delete");
		deleteBtn.addActionListener(this);
		deleteBtn.setBounds(350,270,80,30);
		
		add(deleteBtn);
		add(addBtn);
		add(toggleBtn);
        add(todoList);
        setLayout(new BorderLayout());
	}
	
	private Todo[] getTodos() {
		ArrayList<Todo> todoList= new ArrayList<Todo>();
		
		File file = new File(loggedInUser + "_todos.ser");
		if(!file.exists()) {
			try {
				FileOutputStream fileOut = new FileOutputStream(loggedInUser + "_todos.ser", true);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(new Todo("..."));
				out.close();
				fileOut.close();
			} catch(IOException i) {
				i.printStackTrace();
			}
		}
		try {
			FileInputStream fileIn = new FileInputStream(loggedInUser + "_todos.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			while(in!= null) {
				try {
					todoList.add((Todo) in.readObject());
				} catch(EOFException eof) {
					break;
				}
			}
			fileIn.close();
			in.close();
		}catch(IOException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException c) {
			c.printStackTrace();
		}
		Todo[] todos = new Todo[todoList.size()];
		for(int i = 0; i < todos.length; i++ ) {
			todos[i] = todoList.get(i);
		}
		return todos;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==todoList) {
			boolean state = ((Todo) todoList.getSelectedItem()).getState();
			if(state == true) {
				toggleBtn.setBackground(Color.green);
			} else {
				toggleBtn.setBackground(Color.RED);
			}
		} else if(e.getSource()==toggleBtn) {
			Todo todo = (Todo) todoList.getSelectedItem();
			if(toggleBtn.isSelected()) {
				todo.changeState(true);
			} else {
				todo.changeState(false);
			}
			Todo[] output = getTodos();
			for(Todo td : output) {
				if(todo.getName().equals(td.getName())) {
					td.changeState(todo.getState());
				}
			}
			try {
				new FileOutputStream(loggedInUser + "_todos.ser").close();
				FileOutputStream fileOut = new FileOutputStream(loggedInUser + "_todos.ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				for(Todo td : output) {
					out.writeObject(td);
				}
				out.close();
				fileOut.close();
			} catch (IOException i) {
				i.printStackTrace();
			}
			todoList.setModel(new DefaultComboBoxModel<Todo>(getTodos()));			
			repaint();
		} if(e.getSource()==addBtn) {
			JFrame mainFrame = (JFrame) this.getRootPane().getParent();
			Todo[] todos = getTodos();
			AddTodoFrame addFrame = new AddTodoFrame(mainFrame, "Add todo", true, todos, loggedInUser);
			addFrame.create();
			todoList.setModel(new DefaultComboBoxModel<>(getTodos()));
			repaint();
		} else if(e.getSource()==deleteBtn) {
			Todo todo = (Todo) todoList.getSelectedItem();
			Todo[] output = getTodos();
			Todo[] temp = new Todo[output.length-1];
			int j=0;
			for(int i=0; i<output.length; i++) {
				if(!todo.getName().equals(output[i].getName())) {
					temp[j]=output[i];
					j++;
				}
			}
			try {
				new FileOutputStream(loggedInUser + "_todos.ser").close();
				FileOutputStream fileOut = new FileOutputStream(loggedInUser + "_todos.ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				for(Todo td : temp) {
					out.writeObject(td);
				}
				out.close();
				fileOut.close();
			} catch (IOException i) {
				i.printStackTrace();
			}
			todoList.setModel(new DefaultComboBoxModel<>(getTodos()));			
			repaint();
		}
		
	}
	
	private void setToggleButton(JToggleButton btn, Todo todo) {
		if(todo.getState()==true) {
			btn.setSelected(true);
			btn.setBackground(Color.green);
		}else {
			btn.setSelected(false);
			btn.setBackground(Color.RED);
		}
	}

}

