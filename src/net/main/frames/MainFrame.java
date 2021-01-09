package net.main.frames;

import javax.swing.*;

import net.main.tabs.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.*;

public class MainFrame {
	JFrame frame;
	JTabbedPane tabbedPane;
	GoalsTab goalTab;
	NotesTab noteTab;
	ToDoTab todoTab;
	
	public void createWindow(String loggedInUser) {
		tabbedPane = new JTabbedPane();
		

		//Creating goals panel
		goalTab = new GoalsTab(loggedInUser);
		goalTab.setLayout(new CardLayout());
		
		//Creating notes panel
		noteTab = new NotesTab(loggedInUser);
		
		//Creating todos panel
		todoTab = new ToDoTab(loggedInUser);
		
		//Creating goals tab
		tabbedPane.addTab("Goals", goalTab);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		
		//Creating notes tab
		tabbedPane.addTab("Notes", noteTab);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_3);
		
		//Creating to-do tab
		tabbedPane.addTab("To-Do", todoTab);
    	tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);
		
		//Creating the frame
		frame = new JFrame(loggedInUser + "'s Space");
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
