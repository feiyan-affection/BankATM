package template;

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
import ui.AdminATM;
import ui.CustomerATM;
import ui.LoginGeneric;

public class LoginInputTemplate extends LoginATM {
	private JFrame Cframe;
	private final String allcurrency = "USD/EUR/GBP/CHF/HKD/AUD/ESP/CNY/CAD"; 
	private String theme = "";
	private String hintcurrency = "";
	private String hintamount = "";
	private String hintaccount = "";
	private String success = "";
	private String fail = "";
	private PageFunc function = PageFunc.MISS_MATCH;
	private UserType type;
	
	private JLabel Clabelcurrency;
	private JLabel Clabelamount;
	private JLabel Clabelaccount;
	private JLabel Clabellistallcurrency;
	private JLabel Message;
	private JTextField CTFaccount;
	private JTextField CTFcurrency;
	private JTextField CTFamount;
	private JPanel Cpane;
	private JPanel CBP;
	private JButton CbtnConfirm;
	private JButton CbtnBack;

	public LoginInputTemplate(Bank services, Cookie cookie,
			String theme, String label1, String label2, String label3, UserType type,
			PageFunc func, String successmsg, String failmsg) {
		super(services, cookie);
		// TODO Auto-generated constructor stub
		this.theme = theme;
		hintcurrency = label1;
		hintamount = label2;
		hintaccount = label3;
		success = successmsg;
		fail = failmsg;
		function = func;
		this.type = type;

		createUI();
	}

	
	private void createUI() {
		this.Cframe = new JFrame(theme);
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
		this.Cpane = new JPanel(new GridLayout(9, 1, 0, 1));
		
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
		
		if (function.equals(PageFunc.Loan) 
				|| function.equals(PageFunc.PayLoan)) {
			Clabelaccount.setVisible(false);
			CTFaccount.setVisible(false);
		}
		
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
				
				if (autoCheck(cry, amt, act, function)) {
					Currency c = new Currency(Long.parseLong(amt), Dollar.valueOf(cry));
					boolean res = false;
					switch(function) {
					case Save:
						res = getService().save_session(c, getCookie(), act);
						break;
					case CreateAcc:
						res = getService().create(c, getCookie(), act);
						break;
					case Loan:
						res = getService().requestLoans(c, getCookie());
						break;
					case PayLoan:
						res = getService().takeoutLoans(c, getCookie());
						break;
					default:
						res = false;
					}
					if (res) {
						// save success
						Message.setText(success);
						Message.setForeground(Color.green.darker());
						Message.setVisible(true);
						clearInput();
					} else {
						// save failed
						Message.setText(fail);
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
				LoginATM rf;
				switch (type) {
				case Customer:
					rf = new CustomerATM(getService(), getCookie());
					Cframe.dispose();
					rf.show();
					break;
				case Admin:
					rf = new AdminATM(getService(), getCookie());
					Cframe.dispose();
					rf.show();
					break;
				default:
					System.out.println("LoginInputTemplate miss match type");
					LoginGeneric l = new LoginGeneric("Welcome to ATM", getService());
					Cframe.dispose();
					l.show();
				}
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
	}
	
	private boolean autoCheck(String arg1, String arg2, String arg3, PageFunc type) {
		switch(type) {
		case Save:
			return checkValid1(arg1, arg2, arg3);
		case CreateAcc:
			return checkValid1(arg1, arg2, arg3);
		case Loan:
			return checkValid2(arg1, arg2);
		case PayLoan:
			return checkValid2(arg1, arg2);
		default:
			System.out.println("LoginInputTemplate autoCheck miss match type");
			return false;
		}
	}
	
	private boolean checkValid1(String currencyType, String amount, String account) {
		boolean res = true;
		
		// check currency type
		try {
			Dollar.valueOf(currencyType);
		} catch( Exception e ) {
			res = false;
		}
		
		// check account 
		if (!account.equals("Checking") && !account.equals("Saving")) {
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
	
	private boolean checkValid2(String currencyType, String amount) {
		boolean res = true;
		
		// check currency type
		try {
			Dollar.valueOf(currencyType);
		} catch( Exception e ) {
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
