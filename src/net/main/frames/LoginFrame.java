package net.main.frames;

import java.awt.Color;

import java.awt.event.*;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.*;

import net.main.User;

public class LoginFrame implements ActionListener {

	JFrame frame;
	JLabel usernameLabel, passwordLabel, errorLabel;
	JTextField usernametext;
	JPasswordField passwordtext;
	JButton loginButton, registerButton;
	
	public void createWindow() {
		
		frame = new JFrame("Personal Space");
		
		usernameLabel = new JLabel("Username : ");
		usernameLabel.setBounds(10,10,80,25);
		passwordLabel = new JLabel("Password : ");
		passwordLabel.setBounds(10, 40, 80, 25);
		
		errorLabel = new JLabel();
    	errorLabel.setForeground(Color.RED);
		errorLabel.setBounds(50,70, 200, 30);
		
		
		usernametext = new JTextField();
		usernametext.setBounds(100,10,160,25);
		passwordtext = new JPasswordField();
		passwordtext.setBounds(100, 40, 160, 25);
		
		loginButton = new JButton("Login");
		loginButton.setBounds(10,110,90,25);
		loginButton.addActionListener(this);
		
		registerButton = new JButton("Register");
		registerButton.setBounds(180,110,90,25);
		registerButton.addActionListener(this);
		
		frame.add(usernameLabel);
		frame.add(usernametext);
		frame.add(passwordLabel);
		frame.add(passwordtext);
		frame.add(loginButton);
		frame.add(registerButton);
		frame.add(errorLabel);

		frame.setSize(300, 180);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		String username = usernametext.getText();
		String password = new String(passwordtext.getPassword());
		if(e.getSource() == loginButton) {
			if(checkCredentials(username, password)) {
				int input = JOptionPane.showConfirmDialog(null, "Successful Login","Success", JOptionPane.DEFAULT_OPTION);
				System.out.println(input);
				MainFrame mainFrame = new MainFrame();
				mainFrame.createWindow(username);
				this.frame.setVisible(false);
				
			} else {
				int input = JOptionPane.showConfirmDialog(null, "Incorrect username or password","Error", JOptionPane.DEFAULT_OPTION);
				System.out.println(input);
			}
		} else if(e.getSource()==registerButton) {
			try {
				if(checkCredentials(username)) {
					int input = JOptionPane.showConfirmDialog(null,"Username already exists", "Error", JOptionPane.DEFAULT_OPTION);
					System.out.println(input);
				} else {
					FileOutputStream fileOut = new FileOutputStream(username + ".ser", true);
					ObjectOutputStream out = new ObjectOutputStream(fileOut);
					out.writeObject(new User(username, password));
					out.close();
					fileOut.close();
					int input = JOptionPane.showConfirmDialog(null, "User "+ username +" successfully added", "Successful Registration", JOptionPane.DEFAULT_OPTION);
					System.out.println(input);
				}
			} catch (IOException i) {
				i.printStackTrace();
			}
		}
	}
	
	private boolean checkCredentials(String username, String password) {
		boolean res = false;
		try {
			File file = new File(username + ".ser");
			if(file.exists()) {
				FileInputStream fileIn = new FileInputStream(username + ".ser");
				ObjectInputStream in = new ObjectInputStream(fileIn);
				while(in != null) {
					try {
						User user = (User) in.readObject();
						if(username.equals(user.getUsername()) && password.equals(user.getPassword())) {
							res = true;
							break;
						}
							
					} catch(EOFException eof) {
						break;
					} 
				}
			in.close();
			fileIn.close();
			}
		} catch(IOException i) {
			int diag = JOptionPane.showConfirmDialog(null, "There was a problem loading your credentials", "Error", JOptionPane.DEFAULT_OPTION);
			System.out.println(diag);
			i.printStackTrace();
		} catch(ClassNotFoundException c) {
			int diag = JOptionPane.showConfirmDialog(null, "There was a problem loading your credentials", "Error", JOptionPane.DEFAULT_OPTION);
			System.out.println(diag);
			c.printStackTrace();
		}
		return res;
	}	
	
	private boolean checkCredentials(String username) {
		File file = new File(username + ".ser");
		if(file.exists()) {
			return true;
		} else {
			return false;
		}
	}	


}

