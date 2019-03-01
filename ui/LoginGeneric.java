package ui;
import javax.swing.JFrame;

import bank.Bank;
import template.ATM;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;;

public class LoginGeneric extends ATM{
	private JFrame frame;
	private JButton customers;
	private JButton admin;
	
	public LoginGeneric(String theme, Bank services) {
		super(services);
		createGenericLogin(theme);
	}
	
	
	public void createGenericLogin(String theme) {
		
		// create frame for very initial
		this.frame = new JFrame(theme);
		this.frame.setSize(300, 100);
		this.frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		this.frame.setVisible(false);
		
		// add buttons, textfield and etc. 
		addActiveComponent();
		// set layout style
		setLayoutStyle();
	}
	
	private void addActiveComponent() {
		this.customers = new JButton("For customers");
		this.customers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// jump to another page
				CustomerUI cu = new CustomerUI(getService());
				// destroy this page
				frame.dispose();
				cu.show();
			}
		});
		this.admin = new JButton("For bank administrators");
		this.admin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminUI au = new AdminUI(getService());
				frame.dispose();
				au.show();
			}
		});
		this.frame.add(customers); 
		this.frame.add(admin);
	}
	
	private void setLayoutStyle() {
		FlowLayout flayout = new FlowLayout(FlowLayout.CENTER);
		this.frame.setLayout(flayout);
	}
	
	
	
	public void show() {	
		this.frame.setVisible(true);
	}
}
