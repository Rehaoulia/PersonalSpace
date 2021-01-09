package net.main.tabs;

import net.main.*;
import net.main.frames.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;



public class GoalsTab extends JPanel implements ActionListener {
	static Goal openedGoal;
	String username;
	JToggleButton toggleButton;
	JProgressBar goalProgress;
	JScrollPane scroll;
	JList<Milestone> milestonesList;
	JTextField textField;
	JButton openBtn, addBtn, deleteBtn, addMilestoneBtn, deleteMilestoneBtn, backBtn;
	JComboBox<Goal> goalsList;
	JLabel listLabel, orLabel, titleLabel;
	JTextArea contentArea;
	JPanel mainCard, addCard;
	public GoalsTab(String loggedInUser) {
		
		username = loggedInUser;
		/*
		 * Initialising and configuring the first card
		*/
			//Creating the goals Combo-box
		goalsList = new JComboBox<Goal>(new DefaultComboBoxModel<Goal>(getGoals()));
		goalsList.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				Goal goal =  (Goal) value;
				setText(goal.getName());
				return this;
			}
		});
		goalsList.setBounds(180, 60 , 200, 30);
			//Creating the labels
		listLabel = new JLabel("Choose Goal: ");
		listLabel.setBounds(90, 60, 150, 30);
		orLabel = new JLabel("Or add a new goal");
		orLabel.setBounds(220, 140, 50,30);
			//Creating the "Open" button
		openBtn = new JButton("Open");
		openBtn.setBounds(400, 60, 80, 30);
		openBtn.addActionListener(this);
			//Creating the add button
		addBtn = new JButton("Add");
		addBtn.setBounds(340, 220, 100,30);
		addBtn.addActionListener(this);
			//Creating the text field
		textField = new JTextField("Name of the goal");
		textField.setBounds(180,220,150,30);
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(textField.getText().equals("Name of the goal")) {
				textField.setText("");
				}
			}
		});
			//Creating the first card and adding components
		mainCard = new JPanel();
		mainCard.add(goalsList);
		mainCard.add(listLabel);
		mainCard.add(openBtn);
		mainCard.add(orLabel);
		mainCard.add(addBtn);
		mainCard.add(textField);
		mainCard.setLayout(new BorderLayout());
		
		
		/*
		 * Initialising and configuring the second card
		 */
			//Creating the Labels
		titleLabel = new JLabel();
		titleLabel.setBounds(240,20, 200,50);
		titleLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
		contentArea = new JTextArea();
		contentArea.setBounds(300,120,250,40);
			//Creating the list
		milestonesList = new JList<Milestone>();
		milestonesList.setCellRenderer(new DefaultListCellRenderer() {
			@Override
		    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		        Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				((JLabel) renderer).setText(((Milestone) value).getTitle());
		        return renderer;
			}
		});
		milestonesList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
	            if (me.getClickCount() == 2) {
	               JList<Milestone> target = (JList<Milestone>) me.getSource();
	               int index = target.locationToIndex(me.getPoint());
	               if (index >= 0) {
	                  Milestone item = (Milestone) target.getModel().getElementAt(index);
	                  contentArea.setText(item.getContent());
	                  setToggleButton(toggleButton, item);
	               }
	            }
	         }
		});
		milestonesList.setVisibleRowCount(7);
			//Creating the scroll
		scroll = new JScrollPane(milestonesList);
		scroll.setBounds(20,120,200,90);
			//Creating the buttons
		deleteBtn = new JButton("X");
		deleteBtn.setBounds(500,20,50,30);
		deleteBtn.addActionListener(this);
		deleteBtn.setForeground(Color.red);
		addMilestoneBtn = new JButton("Add");
		addMilestoneBtn.setBounds(170,270,80,30);
		addMilestoneBtn.addActionListener(this);
		deleteMilestoneBtn = new JButton("Delete");
		deleteMilestoneBtn.addActionListener(this);
		deleteMilestoneBtn.setBounds(330,270,80,30);
		backBtn = new JButton("Back");
		backBtn.setBounds(40,20,70,30);
		backBtn.addActionListener(this);
		toggleButton = new JToggleButton("Incomplete");
		toggleButton.setBounds(440,180,100,30);
		toggleButton.addActionListener(this);
			//Creating the progress bar
		goalProgress = new JProgressBar();
		goalProgress.setValue(0);
		goalProgress.setStringPainted(true);
		goalProgress.setBounds(240,80,100,20);
		//Creating the second card and adding components
		addCard = new JPanel();
		addCard.add(titleLabel);
		addCard.add(scroll);
		addCard.add(addMilestoneBtn);
		addCard.add(deleteBtn);
		addCard.add(goalProgress);
		addCard.add(deleteMilestoneBtn);
		addCard.add(backBtn);
		addCard.add(contentArea);
		addCard.add(toggleButton);
		addCard.setLayout(new BorderLayout());
		
		//Adding cards to the GoalsTab
		add(mainCard, "main");
		add(addCard, "add");
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addBtn) {
			String name = textField.getText();
			if(name.length() > 25) {
				int input = JOptionPane.showConfirmDialog(null,"Goal Name must not exceed 30 characters", "Goal name too long", JOptionPane.DEFAULT_OPTION);
				System.out.println(input);
			}else {
				try {
					FileOutputStream fileOut = new FileOutputStream(username + "_goals.ser", true);
					AppendableObjectOutputStream out = new AppendableObjectOutputStream(fileOut);
					out.writeObject(new Goal(name));
					out.close();
					fileOut.close();
					DefaultComboBoxModel<Goal> model = new DefaultComboBoxModel<Goal>(getGoals());
					goalsList.setModel(model);
					mainCard.repaint();
					mainCard.revalidate();
				} catch(IOException i) {
					i.printStackTrace();
				}
			}
		} else if(e.getSource() == openBtn) {
			openedGoal = (Goal) goalsList.getSelectedItem();
			titleLabel.setText(openedGoal.getName());
			CardLayout c = (CardLayout) this.getLayout();
			c.next(this);
			milestonesList.setModel(createListModel());
			goalProgress.setValue(openedGoal.checkProgress());
		} else if(e.getSource()==deleteBtn) {
			Goal[] temp = getGoals();
			Goal[] output = new Goal[temp.length - 1];
			int j = 0;
			for(Goal goal : temp) {
				if(!goal.getName().equals(openedGoal.getName())) {
					output[j] = goal;
					j++;
				}
			}
			try {
				new FileOutputStream(username + "_goals.ser").close();
				FileOutputStream fileOut = new FileOutputStream(username + "_goals.ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				for(Goal goal : output) {
					out.writeObject(goal);
				}
				out.close();
				fileOut.close();
			} catch (IOException i) {
				i.printStackTrace();
			}
			goalsList.setModel(new DefaultComboBoxModel<Goal>(output));
			mainCard.repaint();
			CardLayout c = (CardLayout) this.getLayout();
			c.next(this);
		} else if(e.getSource() == backBtn) {
			CardLayout c = (CardLayout) this.getLayout();
			c.next(this);
		} else if(e.getSource() == addMilestoneBtn) {
			JFrame mainFrame = (JFrame) this.getRootPane().getParent();
			Goal[] temp = getGoals();
			AddFrame addFrame= new AddFrame(mainFrame,"New Milestone",true, "Milestone", null, temp, openedGoal, username);
			addFrame.create();
			openedGoal = addFrame.openedGoal;
			milestonesList.setModel(createListModel());
			addCard.repaint();
			goalProgress.setValue(openedGoal.checkProgress());
		} else if(e.getSource() == deleteMilestoneBtn) {
			Milestone milestone = milestonesList.getSelectedValue();
			openedGoal.getProgressList().remove(milestone);
			Goal[] output = getGoals();
			for(Goal goal : output) {
				if(goal.getName().equals(openedGoal.getName())) {
					goal.removeProgress(milestone);
				}
			}
			try {
				new FileOutputStream(username + "_goals.ser").close();
				FileOutputStream fileOut = new FileOutputStream(username + "_goals.ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				for(Goal goal : output) {
					out.writeObject(goal);
				}
				out.close();
				fileOut.close();
			} catch (IOException i) {
				i.printStackTrace();
			}
			milestonesList.setModel(createListModel());
			contentArea.setText("");
			mainCard.repaint();
			mainCard.revalidate();
			goalProgress.setValue(openedGoal.checkProgress());
		} else if(e.getSource()== toggleButton) {
			Goal[] output = getGoals();
			Milestone milestone = milestonesList.getSelectedValue();
			if(toggleButton.isSelected()) {
				toggleButton.setText("Complete");
				milestone.setState(true);
			} else {
				toggleButton.setText("Incomplete");
				milestone.setState(false);
			}
			for(Goal goal : output) {
				if(goal.getName().equals(openedGoal.getName())) {
					for(Milestone milstn : goal.getProgressList()) {
						if(milstn.getTitle().equals(milestone.getTitle())) {
							milstn.setState(milestone.getState());
						}
					}
				}
			}
			try {
				new FileOutputStream(username + "_goals.ser").close();
				FileOutputStream fileOut = new FileOutputStream(username + "_goals.ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				for(Goal goal : output) {
					out.writeObject(goal);
				}
				out.close();
				fileOut.close();
			} catch (IOException i) {
				i.printStackTrace();
			}
			goalProgress.setValue(openedGoal.checkProgress());
		}
		
		
	}
	
	private Goal[] getGoals() {
		ArrayList<Goal> goalsList= new ArrayList<Goal>();
		/* This part is to force the creation of the file so 
		 * that the Output Stream header is set once and when adding 
		 *  to the file again the object output stream writeStreamHeader will be
		 *  overridden to not create a new header. The purpose of this is to have
		 *  not more than one header for every input stream and avoid StreamCorruptedException  
		 */
		File file = new File(username + "_goals.ser");
		if(!file.exists()) {
			try {
				FileOutputStream fileOut = new FileOutputStream(username + "_goals.ser", true);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(new Goal("..."));
				out.close();
				fileOut.close();
			} catch(IOException i) {
				i.printStackTrace();
			}
		}
		try {
			FileInputStream fileIn = new FileInputStream(username + "_goals.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			while(in!= null) {
				try {
					goalsList.add((Goal) in.readObject());
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
		Goal[] goals = new Goal[goalsList.size()];
		for(int i = 0; i < goals.length; i++ ) {
			goals[i] = goalsList.get(i);
		}
		return goals;
	}
	
	private DefaultListModel<Milestone> createListModel() {
		DefaultListModel<Milestone> model = new DefaultListModel<Milestone>();
		if(openedGoal.getProgressList() != null) {
			for(Milestone milestone : openedGoal.getProgressList()) {
				model.addElement(milestone);
			}
		}
		return model;
	}
	
	private void setToggleButton(JToggleButton btn, Milestone milestone) {
		if(milestone.getState()==true) {
			btn.setSelected(true);
			btn.setText("Complete");
		}else {
			btn.setSelected(false);
			btn.setText("Incomplete");
		}
	}

	
}
