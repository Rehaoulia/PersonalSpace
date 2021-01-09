package net.main.tabs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.*;

import net.main.Note;
import net.main.frames.AddFrame;

public class NotesTab extends JPanel implements ActionListener {
	
	String loggedInUser;
	JTextArea contentArea;
	JList<Note> notesList;
	JTextField noteFiled;
	JScrollPane scroll;
	JButton addBtn, deleteBtn, saveBtn;
	
	public NotesTab(String loggedInUser) {
		
		this.loggedInUser = loggedInUser;

		notesList = new JList<Note>();
		notesList.setCellRenderer(new DefaultListCellRenderer() {
			@Override
		    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		        Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				((JLabel) renderer).setText(((Note) value).getTitle());
		        return renderer;
			}
		});
		notesList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
	            if (me.getClickCount() == 2) {
	               JList<Note> target = (JList<Note>) me.getSource();
	               int index = target.locationToIndex(me.getPoint());
	               if (index >= 0) {
	                  Note item =  target.getSelectedValue();
	                  contentArea.setText(item.getContent());
	               }
	            }
	         }
		});
		notesList.setModel(createListModel());
		notesList.setVisibleRowCount(15);
		
		contentArea = new JTextArea();
		contentArea.setBounds(300,50,250,200);
		
		scroll = new JScrollPane(notesList);
		scroll.setBounds(20,50,200,200);
		
		addBtn = new JButton("Add");
		addBtn.setBounds(170,270,80,30);
		addBtn.addActionListener(this);
		
		deleteBtn = new JButton("Delete");
		deleteBtn.addActionListener(this);
		deleteBtn.setBounds(350,270,80,30);
		
		saveBtn = new JButton("Save");
		saveBtn.setBounds(260,270,80,30);
		saveBtn.addActionListener(this);
		
		add(deleteBtn);
		add(addBtn);
		add(saveBtn);
		add(scroll);
		add(contentArea);
		setLayout(new BorderLayout());
	}
	
	private Note[] getNotes() {
		ArrayList<Note> notesList= new ArrayList<Note>();
		
		File file = new File(loggedInUser + "_notes.ser");
		if(!file.exists()) {
			try {
				FileOutputStream fileOut = new FileOutputStream(loggedInUser + "_notes.ser", true);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(new Note("...",""));
				out.close();
				fileOut.close();
			} catch(IOException i) {
				i.printStackTrace();
			}
		}
		try {
			FileInputStream fileIn = new FileInputStream(loggedInUser + "_notes.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			while(in!= null) {
				try {
					notesList.add((Note) in.readObject());
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
		Note[] notes = new Note[notesList.size()];
		for(int i = 0; i < notes.length; i++ ) {
			notes[i] = notesList.get(i);
		}
		return notes;
	}
	
	private DefaultListModel<Note> createListModel() {
		DefaultListModel<Note> model = new DefaultListModel<Note>();
		Note[] notes = getNotes();
		for(Note note : notes) {
			model.addElement(note);
		}
		return model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==addBtn) {
			JFrame mainFrame = (JFrame) this.getRootPane().getParent();
			Note[] temp = getNotes();
			AddFrame addFrame= new AddFrame(mainFrame,"New Note",true, "Note", temp, null, null, loggedInUser);
			addFrame.create();
			notesList.setModel(createListModel());
			repaint();
		} else if(e.getSource()==deleteBtn) {
			Note note = notesList.getSelectedValue();
			Note[] output = getNotes();
			Note[] temp = new Note[output.length-1];
			int j=0;
			for(int i=0; i<output.length; i++) {
				if(!note.getTitle().equals(output[i].getTitle())) {
					temp[j]=output[i];
					j++;
				}
			}
			try {
				new FileOutputStream(loggedInUser + "_notes.ser").close();
				FileOutputStream fileOut = new FileOutputStream(loggedInUser + "_notes.ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				for(Note n : temp) {
					out.writeObject(n);
				}
				out.close();
				fileOut.close();
			} catch (IOException i) {
				i.printStackTrace();
			}
			notesList.setModel(createListModel());
			contentArea.setText("");
			repaint();
		}else if(e.getSource()== saveBtn) {
			Note note = notesList.getSelectedValue();
			Note[] output = getNotes();
			for(Note n : output) {
				if(n.getTitle().equals(note.getTitle())) {
					n.modifyContent(contentArea.getText());
				}
			}
			try {
				new FileOutputStream(loggedInUser + "_notes.ser").close();
				FileOutputStream fileOut = new FileOutputStream(loggedInUser + "_notes.ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				for(Note n : output) {
					out.writeObject(n);
				}
				out.close();
				fileOut.close();
			} catch (IOException i) {
				i.printStackTrace();
			}
			notesList.setModel(createListModel());
			repaint();
		}
		
	}
	
}
