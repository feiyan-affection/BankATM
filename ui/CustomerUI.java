package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import bank.Bank;
import session.Cookie;
import session.MakeCookie;
import template.ATM;

public class CustomerUI extends ATM{
	private JFrame Cframe;
	private final static String hintuserid = "Account id: ";
	private final static String hintpwd = "password: ";
	private JLabel Clabeluserid;
	private JLabel Clabelpwd;
	private JLabel Message;
	private JTextField CTFuserid;
	private JPasswordField CTFpwd;
	private JPanel Cpane;
	private JPanel CBP;
	private JButton CbtnLogin;
	private JButton CbtnRegister;

	public CustomerUI(Bank services) {
		super(services);
		createCustomerUI();
	}

	private void createCustomerUI() {
		this.Cframe = new JFrame("Customer Login");
//		this.Cframe.setLayout(new FlowLayout());
		this.Cframe.setSize(350, 300);
		this.Cframe.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		this.Cframe.setVisible(false);
		
		addActiveComponent();
	}
	
	private void addActiveComponent() {
		this.Cpane = new JPanel(new GridLayout(7, 1, 0, 1));
		
		this.Clabeluserid = new JLabel();
		this.Clabeluserid.setText(this.hintuserid);
		this.Cpane.add(Clabeluserid);
		
		this.CTFuserid = new JTextField();
		this.Cpane.add(CTFuserid);
		
		this.Clabelpwd = new JLabel();
		this.Clabelpwd.setText(this.hintpwd);
		this.Cpane.add(Clabelpwd);
		
		this.CTFpwd = new JPasswordField();
		this.Cpane.add(CTFpwd);
		
		this.Message = new JLabel();
		this.Message.setText("");
		this.Message.setVisible(false);
		Font messageFont = new Font("Verdana",Font.BOLD,13);
		this.Message.setFont(messageFont);
		this.Cpane.add(Message);
		this.Cpane.setBorder(new LineBorder(Color.GRAY));
		
		this.CBP = new JPanel(new GridLayout(0, 2));
		this.CbtnLogin = new JButton("Login");
		this.CbtnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// authenticate username & password
				boolean success = getService().login(CTFuserid.getText(), CTFpwd.getPassword(), "Customer");
				if (success) {
					// jump to ATM page
					CustomerATM ca = new CustomerATM(
							getService(), MakeCookie.makeCookie(CTFuserid.getText(), new String(CTFpwd.getPassword()), "Customer"));
					Cframe.dispose();
					ca.show();
				} else {
					// error msg
					Message.setText("Login failed.");
					Message.setForeground(Color.red);
					Message.setVisible(true);
				}
			}
		});
		this.CBP.add(this.CbtnLogin);
		this.CbtnRegister = new JButton("Register");
		this.CbtnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// jump to register
				registerUI ri = new registerUI(getService(), "Customer");				
				Cframe.dispose();
				ri.show();
			}
		});
		this.CBP.add(this.CbtnRegister);
		
		this.Cframe.add(this.Cpane, BorderLayout.CENTER);
		this.Cframe.add(this.CBP, BorderLayout.PAGE_END);
		
	}
		
	public void show() {
		this.Cframe.setVisible(true);
	}
}
