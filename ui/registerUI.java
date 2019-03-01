package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import bank.Bank;
import template.ATM;

public class registerUI extends ATM{
	private JFrame Cframe;
	private final String hintuserid = "Account id: ";
	private final String hintpwd = "Password: ";
	private final String hintpwdconfirm = "Please confirm password: ";
	private String registerType;
	private JLabel Clabeluserid;
	private JLabel Clabelpwd;
	private JLabel Clabelpwdconfrim;
	private JTextField CTFuserid;
	private JLabel Message;
	private JPasswordField CTFpwd;
	private JPasswordField CTFpwdconfirm;
	private JPanel Cpane;
	private JPanel CBP;
	private JButton CbtnBack;
	private JButton CbtnRegister;

	public registerUI(Bank services, String registerType) {
		super(services);
		this.registerType = registerType;
		createRegisterUI();
	}
	
	private void clearText() {
		CTFuserid.setText("");
		CTFpwd.setText("");
		CTFpwdconfirm.setText("");
	}

	private void createRegisterUI() {
		this.Cframe = new JFrame("Register");
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
		
		this.Clabelpwdconfrim = new JLabel();
		this.Clabelpwdconfrim.setText(this.hintpwdconfirm);
		this.Cpane.add(Clabelpwdconfrim);
		
		this.CTFpwdconfirm = new JPasswordField();
		this.Cpane.add(CTFpwdconfirm);
		
		this.Message = new JLabel();
		this.Message.setText("");
		this.Message.setVisible(false);
		Font messageFont = new Font("Verdana",Font.BOLD,13);
		this.Message.setFont(messageFont);
		this.Cpane.add(Message);

		this.Cpane.setBorder(new LineBorder(Color.GRAY));
		
		this.CBP = new JPanel(new GridLayout(0, 2));
		this.CbtnRegister = new JButton("Sign up");
		this.CbtnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// two passwords are not equaled
				if (!new String(CTFpwd.getPassword()).equals(new String(CTFpwdconfirm.getPassword()))) {
					Message.setText("Two passwords are not equaled.");
					Message.setForeground(Color.red);
					Message.setVisible(true);
//					System.out.println("case#1");
				} else {
					boolean success = getService().register(CTFuserid.getText(), CTFpwd.getPassword(), registerType);
					if (success) {
						// message show with green text
						Message.setText("Successfully signed up.");
						Message.setForeground(Color.green.darker());
						Message.setVisible(true);
						clearText();
						
					} else {
						// message show with red text
						Message.setText("Username already existed.");
						Message.setForeground(Color.red);
						Message.setVisible(true);
					}
				}
			}
		});
		this.CBP.add(this.CbtnRegister);
		
		
		this.CbtnBack = new JButton("Back to Login page");
		this.CbtnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// jump to register
				if (registerType.equals("Customer")) {
					CustomerUI cu = new CustomerUI(getService());
					Cframe.dispose();
					cu.show();
				} else if (registerType.equals("Admin")) {
					AdminUI au = new AdminUI(getService());
					Cframe.dispose();
					au.show();
				}
			}
		});
		this.CBP.add(this.CbtnBack);
		
		this.Cframe.add(this.Cpane, BorderLayout.CENTER);
		this.Cframe.add(this.CBP, BorderLayout.PAGE_END);
		
	}
		
	public void show() {
		this.Cframe.setVisible(true);
	}
}
