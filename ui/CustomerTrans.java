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
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import bank.Bank;
import data.Currency;
import data.Dollar;
import session.Cookie;
import template.LoginATM;

public class CustomerTrans extends LoginATM{
	private JFrame Cframe;
	private final String hintcurrency = "Please enter which currency you want to transfer: ";
	private final String hintamount = "Please enter the amount of money to transfer: ";
	private final String hintaccount = "Please enter the account type: (Checking/Saving)";
	private final String hintreciver = "Please enter reciver' username: ";
	private final String hintreciveracc = "Please enter reciver' account type: (Checking/Saving)";
	private final String allcurrency = "USD/EUR/GBP/CHF/HKD/AUD/ESP/CNY/CAD"; 
	
	private JLabel Clabelcurrency;
	private JLabel Clabelamount;
	private JLabel Clabelaccount;
	private JLabel Clabelreciver;
	private JLabel Clabelreciveracc;
	private JLabel Clabellistallcurrency;

	private JLabel Message;
	private JTextField CTFaccount;
	private JTextField CTFcurrency;
	private JTextField CTFamount;
	private JTextField CTFreciver;
	private JTextField CTFreciveracc;
	private JPanel Cpane;
	private JPanel CBP;
	private JButton CbtnConfirm;
	private JButton CbtnBack;

	public CustomerTrans(Bank services, Cookie cookie) {
		super(services, cookie);
		// TODO Auto-generated constructor stub
		createUI();
	}
	
	private void createUI() {
		this.Cframe = new JFrame("Money transfer");
//		this.Cframe.setLayout(new FlowLayout());
		this.Cframe.setSize(350, 600);
		this.Cframe.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		this.Cframe.setVisible(false);
		
		addActiveComponent();
	}
	
	private void addActiveComponent() {
		this.Cpane = new JPanel(new GridLayout(12, 1, 0, 1));
		
		this.Clabelcurrency = new JLabel();
		this.Clabelcurrency.setText(this.hintcurrency);
		this.Cpane.add(Clabelcurrency);
		this.Clabellistallcurrency = new JLabel(this.allcurrency);
		this.Cpane.add(Clabellistallcurrency);
		this.CTFcurrency = new JTextField();
		this.Cpane.add(CTFcurrency);
		
		this.Clabelamount = new JLabel();
		this.Clabelamount.setText(this.hintamount);
		this.Cpane.add(Clabelamount);
		this.CTFamount = new JTextField();
		this.Cpane.add(CTFamount);
		
		this.Clabelaccount = new JLabel();
		this.Clabelaccount.setText(this.hintaccount);
		this.Cpane.add(Clabelaccount);
		this.CTFaccount = new JTextField();
		this.Cpane.add(CTFaccount);
		
		this.Clabelreciver = new JLabel();
		this.Clabelreciver.setText(this.hintreciver);
		this.Cpane.add(Clabelreciver);
		this.CTFreciver = new JTextField();
		this.Cpane.add(CTFreciver);
		
		this.Clabelreciveracc = new JLabel();
		this.Clabelreciveracc.setText(this.hintreciveracc);
		this.Cpane.add(Clabelreciveracc);
		this.CTFreciveracc = new JTextField();
		this.Cpane.add(CTFreciveracc);
		
		this.Message = new JLabel();
		this.Message.setText("");
		this.Message.setVisible(false);
		Font messageFont = new Font("Verdana",Font.BOLD,13);
		this.Message.setFont(messageFont);
		this.Cpane.add(Message);
		this.Cpane.setBorder(new LineBorder(Color.GRAY));
		
		this.CBP = new JPanel(new GridLayout(0, 2));
		this.CbtnConfirm = new JButton("Confirm");
		this.CbtnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cry = CTFcurrency.getText();
				String amt = CTFamount.getText();
				String act = CTFaccount.getText();
				String rev = CTFreciver.getText();
				String rea = CTFreciveracc.getText();
				if (checkValid(cry, amt, act, rea)) {
					Currency c = new Currency(Long.parseLong(amt), Dollar.valueOf(cry));
					if (getService().transfer(c, getCookie(), act, rev, rea)) {
						// trans success
						Message.setText("Transfer success");
						Message.setForeground(Color.green.darker());
						Message.setVisible(true);
						clearInput();
					} else {
						// trans failed
						Message.setText("Transfer fail");
						Message.setForeground(Color.red);
						Message.setVisible(true);
					}
				} else {
					// invalid argument
					Message.setText("Invalid input");
					Message.setForeground(Color.red);
					Message.setVisible(true);
				}
			}
		});
		
		this.CbtnBack = new JButton("Back");
		this.CbtnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// jump to CustomerATM
				CustomerATM ca = new CustomerATM(getService(), getCookie());
				Cframe.dispose();
				ca.show();
			}
		});

		this.CBP.add(this.CbtnConfirm);
		this.CBP.add(this.CbtnBack);		
		this.Cframe.add(this.Cpane, BorderLayout.CENTER);
		this.Cframe.add(this.CBP, BorderLayout.PAGE_END);
		
	}
	
	private void clearInput() {
		this.CTFaccount.setText("");
		this.CTFcurrency.setText("");
		this.CTFamount.setText("");
		this.CTFreciver.setText("");
		this.CTFreciveracc.setText("");;
	}

	private boolean checkValid(String currencyType, String amount, String account, String to_account) {
		boolean res = true;
		
		// check currency type
		try {
			Dollar.valueOf(currencyType);
		} catch( Exception e ) {
			res = false;
		}
		
		// check account && to_account
		if ((!account.equals("Checking") && !account.equals("Saving"))
				|| (!to_account.equals("Checking") && !to_account.equals("Saving"))) {
			res = false;
		}
		
		// check amount
		try {
			Long.parseLong(amount);
		} catch( Exception e ) {
			res = false;
		}
		
		return res;
	}
	
	
		
	public void show() {
		this.Cframe.setVisible(true);
	}
}
