package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.border.LineBorder;

import bank.Bank;
import session.Cookie;
import template.LoginATM;
import template.LoginInputTemplate;
import template.PageFunc;
import template.UserType;

public class CustomerATM extends LoginATM {
	private JFrame Cframe;
	private JLabel Message;
	private JPanel Cpane1;
	private JPanel Cpane2;
	private JPanel CMessagePane;
	private JButton btnSave;
	private JButton btnTransfer;
	private JButton btnView;
	private JButton btnReqLoan;
	private JButton btnTakeoutLoan;
	private JButton btnAccount;
	private JButton btnViewTrans;
	private JButton btnClear;
	private JButton btnExit;
	
//	public CustomerATM(Bank services, String username, char[] password, String type) {
	public CustomerATM(Bank services, Cookie cookie) {
		super(services, cookie);
		createATMLayout();
	}
	
	private void createATMLayout() {
		this.Cframe = new JFrame(" magic ATM ");
//		this.Cframe.setLayout(new FlowLayout());
		this.Cframe.setSize(800, 500);
		this.Cframe.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		this.Cframe.setVisible(false);
		
		addActiveComponent();
	}
	
	private void addActiveComponent() {
		this.Cpane1 = new JPanel(new GridLayout(8, 3, 1, 2));
		this.Cpane1.setPreferredSize(new Dimension(200, 800));
		
		this.btnSave = new JButton("Save");
		this.btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// jump to new page
				LoginInputTemplate lit = new LoginInputTemplate(getService(), getCookie(), 
						"Save money", 
						"Please enter which currency you want to save: ",
						"Please enter the amount of money to save: ",
						"Please enter the account Type: (Checking/Saving)",
						UserType.Customer , PageFunc.Save, 
						"Save success", "Save fail");
				Cframe.dispose();
				lit.show();
			}
		});
		this.Cpane1.add(btnSave);
		
		this.btnTransfer = new JButton("Transfer");
		this.btnTransfer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// jump to new page
				CustomerTrans cts = new CustomerTrans(getService(), getCookie());
				Cframe.dispose();
				cts.show();
			}
		});
		this.Cpane1.add(btnTransfer);
		
		// if this user lack any type of account
		// add a button to allow to create
		if (!getService().hasChecking(getCookie()) || !getService().hasSaving(getCookie())) {
			this.btnAccount = new JButton("Create account");
			this.btnAccount.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// jump to new page
					LoginInputTemplate lit = new LoginInputTemplate(getService(), getCookie(), 
							"Create Account", 
							"Please enter which currency you want to save: ",
							"Please enter the amount of money to initilize account: ",
							"Please enter the account Type: (Checking/Saving)",
							UserType.Customer , PageFunc.CreateAcc, 
							"Create success", "Create fail");
					Cframe.dispose();
					lit.show();
				}
			});
			this.Cpane1.add(btnAccount);
		}
		
		// if this user own either Checking or Saving account
		// add a button to allow to view
		if (getService().hasChecking(getCookie()) || getService().hasSaving(getCookie())) {
			this.btnView = new JButton("View account");
			this.btnView.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// show account detail
					String t = getService().viewAccount(getCookie());
					setMSGPanel(t, 20);
				}
			});
			this.Cpane1.add(btnView);
		}
		
		this.btnReqLoan = new JButton("Request loans");
		this.btnReqLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// jump to new page
				LoginInputTemplate lit = new LoginInputTemplate(getService(), getCookie(), 
						"Lauch loans", 
						"Please enter which currency you want to loan: ",
						"Please enter the amount of money you want: ",
						"",
						UserType.Customer , PageFunc.Loan, 
						"Get loans success", "Get loans fail");
				Cframe.dispose();
				lit.show();
			}
		});
		
		this.btnTakeoutLoan = new JButton("Takeout loans");
		this.btnTakeoutLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// jump to new page
				LoginInputTemplate lit = new LoginInputTemplate(getService(), getCookie(), 
						"Pay loans", 
						"Please enter which currency you want to pay for: ",
						"Please enter the amount of money you want to pay: ",
						"",
						UserType.Customer , PageFunc.PayLoan, 
						"Pay loans success", "Pay loans fail");
				Cframe.dispose();
				lit.show();
			}
		});
		this.btnViewTrans = new JButton("View transaction");
		this.btnViewTrans.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String v = getService().viewTransactions(getCookie());
				setMSGPanel(v, 13);
			}
		});
		
		this.btnClear = new JButton("Clear");
		this.btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String v = "";
				setMSGPanel(v, 20);
			}
		});
		
		this.btnExit = new JButton("Log out");
		this.btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// jump to new page
				LoginGeneric lg = new LoginGeneric("Welcome to ATM", getService());
				Cframe.dispose();
				lg.show();
			}
		});
		this.Cpane2 = new JPanel(new GridLayout(8, 3, 1, 2));
		this.Cpane2.add(btnReqLoan);
		this.Cpane2.add(btnTakeoutLoan);
		this.Cpane2.add(btnViewTrans);
		this.Cpane2.add(btnClear);
		this.Cpane2.add(btnExit);
		this.Cpane2.setPreferredSize(new Dimension(200, 800));

		
		this.CMessagePane = new JPanel(new FlowLayout());		
		this.Message = new JLabel();
		this.Message.setText("");
		this.Message.setVisible(false);
		Font messageFont = new Font("Verdana",Font.BOLD,20);
		this.Message.setFont(messageFont);
		this.CMessagePane.add(Message);
		this.CMessagePane.setBorder(new LineBorder(Color.black));
		
		
		this.Cframe.add(this.Cpane1, BorderLayout.LINE_START);
		this.Cframe.add(this.CMessagePane, BorderLayout.CENTER);
		this.Cframe.add(this.Cpane2, BorderLayout.LINE_END);
		
	}
		
	public void show() {
		this.Cframe.setVisible(true);
	}
	
	private void setMSGPanel(String str, int fontsize) {
		Message.setText("");
		Font messageFont = new Font("Verdana",Font.BOLD, fontsize);
		Message.setFont(messageFont);
		String html_head = "<html><body><p>";
		String html_end = "</p></body></html>";
		String res = html_head + str.replaceAll("\n", "<br/>") + html_end;
		Message.setText(res);
		Message.setVisible(true);
	}
		
}
