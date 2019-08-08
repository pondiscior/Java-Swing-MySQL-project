package Wolf;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class WolfUI implements ActionListener {
	
	private JFrame mainFrame;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	private JPanel panel5;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JComboBox<String> dbmsList;
	private JTextField urlText;
	private JTextField usernameText;
	private JPasswordField passwordText;
	private JButton executeBtn;
	

	
	public void intializeUI() {
		mainFrame = new JFrame();
		
		mainFrame.setName("My first window");
		mainFrame.setSize(400,400);
		
		GridLayout gl = new GridLayout(5,0);
		
		gl.setHgap(10);

		
		mainFrame.setLayout(gl);
		
		executeBtn = new JButton("Execute");
		
		executeBtn.setSize(50,100);
		
		Vector<String> v = new Vector<String>();
		v.addElement("sql");
		v.addElement("mongo");
		

		dbmsList = new JComboBox<String>(v);
		
		urlText = new JTextField();
		urlText.setPreferredSize(new Dimension(200,20));
		usernameText = new JTextField();
		usernameText.setPreferredSize(new Dimension(200,20));
		passwordText = new JPasswordField();
		passwordText.setPreferredSize(new Dimension(200,20));
		
		label1 = new JLabel();
		label1.setText("Choose database");
		label2 = new JLabel();
		label2.setText("Enter Url");
		label3 = new JLabel();
		label3.setText("Enter username");
		label4 = new JLabel();
		label4.setText("Enter password");
		
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		panel5 = new JPanel();
		
		panel1.add(label1);
		panel1.add(dbmsList);
		
		panel2.add(label2);
		panel2.add(urlText);

		panel3.add(label3);
		panel3.add(usernameText);
		
		panel4.add(label4);
		panel4.add(passwordText);
		
		//panel5.add(dbmsArea);
		panel5.add(executeBtn);
		
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		


		WolfUI mainApp = new WolfUI();
		
		mainApp.intializeUI();
		mainApp.executeBtn.addActionListener(mainApp);
		mainApp.mainFrame.add(mainApp.panel1);
		mainApp.panel1.setVisible(true);
		mainApp.mainFrame.add(mainApp.panel2);
		mainApp.panel2.setVisible(true);
		mainApp.mainFrame.add(mainApp.panel3);
		mainApp.panel3.setVisible(true);
		mainApp.mainFrame.add(mainApp.panel4);
		mainApp.panel4.setVisible(true);
		mainApp.mainFrame.add(mainApp.panel5);
		mainApp.panel5.setVisible(true);
		
		mainApp.mainFrame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		boolean status=false;
		// TODO Auto-generated method stub
		if (e.getSource()==executeBtn) {

			//lbl.setText((String) textBox1.getText()+ " " + textBox2.getText()+ " " + textBox3.getText());
			
			String URL = urlText.getText();
			String username = usernameText.getText();
			String password = passwordText.getText();
			
			try {
				ConnectionManager.getConnection(URL, username, password);
				status = true;
			} catch (Exception e1) {
				System.out.println("Please enter correct information");
				
			}
		}
		if (status) {
			
			if(((String) dbmsList.getSelectedItem()).equals("sql"))
			{
			QueryUI.go();
			mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
			}
			else
			{
				JFrame error = new JFrame("Error: Unsupported");
				error.setLayout(new BorderLayout());
				JLabel errormessage = new JLabel();
				JPanel panel = new JPanel();
				panel.setPreferredSize(new Dimension(100,100));
				JLabel labl= new JLabel();
				errormessage.setText("Mongodb currently not supported");
				labl.setText("                                                   ");
				error.setVisible(true);
				error.setSize(300,110);
				panel.add(labl,BorderLayout.NORTH);
				panel.add(errormessage,BorderLayout.CENTER);
				panel.setVisible(true);
				error.add(panel);
			}

		}
		else{

			JFrame error = new JFrame("Error: Not Connected");
			error.setLayout(new BorderLayout());
			JLabel errormessage = new JLabel();
			JPanel panel = new JPanel();
			panel.setPreferredSize(new Dimension(100,100));
			JLabel labl= new JLabel();
			errormessage.setText("Please enter correct information");
			labl.setText("                                                   ");
			error.setVisible(true);
			error.setSize(300,110);
			panel.add(labl,BorderLayout.NORTH);
			panel.add(errormessage,BorderLayout.CENTER);
			panel.setVisible(true);
			error.add(panel);
		}
		}
	}
