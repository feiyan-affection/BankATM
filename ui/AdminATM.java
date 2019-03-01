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
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import bank.Bank;
import session.Cookie;
import template.LoginATM;

public class AdminATM extends LoginATM {
	private JFrame Cframe;
	private JLabel Message;
	private JPanel Cpane1;
	private JPanel Cpane2;
	private JPanel CMessagePane;
	
	private JTextField tfcheck;
	private JButton btnCheckup;
	private JButton btnViewPoor;
	private JButton btnViewReport;
	private JButton btnClear;
	private JButton btnExit;
	private JButton btnChargeInterest;
	
//	public CustomerATM(Bank services, String username, char[] password, String type) {
	public AdminATM(Bank services, Cookie cookie) {
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
		
		this.btnCheckup = new JButton("Check this guy!");
		this.btnCheckup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// check info of this guy
				String v = getService().spyOneclient(tfcheck.getText(), getCookie());
				setMSGPanel(v, 20);
			}
		});		
		this.btnViewPoor = new JButton("View all debt");
		this.btnViewPoor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// display all customers that own debt
				String v = getService().collectAllPoorGuys(getCookie());
				setMSGPanel(v, 20);
			}
		});
		this.Cpane1.add(btnViewPoor);
		
		this.tfcheck = new JTextField();
		this.Cpane1.add(tfcheck);
		this.Cpane1.add(btnCheckup);

		
		this.btnViewReport = new JButton("Collect Reports");
		this.btnViewReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// display reports on all daily transactions
				String ms = getService().collectReport(getCookie());
				setMSGPanel(ms, 13);		
			}
		});
		
		this.btnChargeInterest = new JButton("Charge Interest");
		this.btnChargeInterest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// display reports on all daily transactions
				String ms = getService().viewProfit(getCookie());	
				setMSGPanel(ms, 20);
			}
		});

		
		this.btnClear = new JButton("Clear");
		this.btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// clear message panel
				String ms = "";
				setMSGPanel(ms, 20);		
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
		this.Cpane2.add(btnViewReport);
		this.Cpane2.add(btnChargeInterest);
		this.Cpane2.add(btnClear);
		this.Cpane2.add(btnExit);
		this.Cpane2.setPreferredSize(new Dimension(200, 800));

		
		this.CMessagePane = new JPanel(new FlowLayout());		
		this.Message = new JLabel();
		this.Message.setText("");
		this.Message.setVisible(false);
		Font messageFont = new Font("Verdana",Font.BOLD,13);
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
