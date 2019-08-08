package Wolf;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class QueryUI implements ActionListener {

	private int clear;
	private JFrame mainFrame;
	private JComboBox<String> dbmsList;
	private JPanel panelBig;
	private JPanel panel;
	private JPanel panel2;
	private JPanel panel3;
	private JLabel label;
	private JTextArea dbmsArea;
	private JButton executeBtn;
	private JScrollPane scrollPanel;
	private JTable tblContainer;
	private DefaultTableModel model;

	public void intializeUI() {
		mainFrame = new JFrame();

		mainFrame.setName("My first window");
		mainFrame.setSize(850, 550);

		scrollPanel = new JScrollPane();
		scrollPanel.setPreferredSize(new Dimension(400, 400));

		model = new DefaultTableModel();

		tblContainer = new JTable(model);
		tblContainer.setModel(model);
		tblContainer.getAutoResizeMode();
		tblContainer.disable();
		scrollPanel.setViewportView(tblContainer);

		//String[] row1 = { "Yousuf", "24", "1", "hi" };
		//model.addRow(row1);

		panelBig = new JPanel();
		panelBig.setLayout(new BorderLayout());

		Vector<String> v = new Vector<String>();

		try {

			Connection conn = ConnectionManager.getConnection();

			Statement statement = conn.createStatement();

			statement.executeQuery("use ems");

			ResultSet rs = statement.executeQuery("show databases");

			while (rs.next()) {
				v.addElement(rs.getString(1));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		dbmsList = new JComboBox<String>(v);

		label = new JLabel();
		label.setText("Query Statement");
		label.setSize(50, 50);

		JLabel label2 = new JLabel();

		label2.setText("         ");

		dbmsArea = new JTextArea();
		dbmsArea.setPreferredSize(new Dimension(400, 400));

		panel = new JPanel();
		panel2 = new JPanel();

		panel.add(dbmsArea);
		panel2.add(scrollPanel);

		executeBtn = new JButton("Execute");
		executeBtn.setPreferredSize(new Dimension(100, 50));

		panelBig.add(dbmsList, BorderLayout.NORTH);
		// panelBig.add(label2,BorderLayout.CENTER);
		panelBig.add(panel, BorderLayout.WEST);
		panelBig.add(panel2, BorderLayout.EAST);
		panel3 = new JPanel();
		panel3.add(executeBtn);
		panelBig.add(panel3, BorderLayout.SOUTH);

	}

	public static void go() {

		QueryUI mainApp = new QueryUI();

		mainApp.intializeUI();

		mainApp.mainFrame.add(mainApp.panelBig);
		mainApp.executeBtn.addActionListener(mainApp);
		mainApp.panelBig.setVisible(true);
		mainApp.panel.setVisible(true);
		mainApp.panel2.setVisible(true);

		mainApp.mainFrame.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		// String[] columnNames = {" "};
		// model.removeRow(0);;
		// model.setColumnIdentifiers(columnNames);
		
		String[] columnNamesClear = {" "};
		
		model.setColumnIdentifiers(columnNamesClear);

		for(int i = 1; i <= clear;i++)
		{
			model.removeRow(0);
		}
		
		clear = 0;
		
		String strQuery = dbmsArea.getText().trim();

		String[] insert = strQuery.split(" ");

		String word = insert[0].toLowerCase();

		try {

			Connection conn = ConnectionManager.getConnection();

			Statement statement = conn.createStatement();
			String databaseName = (String) dbmsList.getSelectedItem();
			
			statement.executeQuery("use " + databaseName);
			
			if (word.equals("select")) {
						
				ResultSet rs = statement.executeQuery(strQuery);
				
				int index = rs.getMetaData().getColumnCount();
				
				String[] columnNames = new String[index];
				
				for(int i = 1; i <= index ; i++)
				{
					String columnName = rs.getMetaData().getColumnName(i);
					columnNames[i - 1] = columnName;
				}
				model.setColumnIdentifiers(columnNames);
				
				while(rs.next())
				{
				
				String[] row = new String[index];
				
				for(int i = 1; i <= index ; i++)
				{
					row[i - 1] = rs.getString(i);
				}
				
				++clear;
				model.addRow(row);
				}
				
				rs.close();

			} else if (word.equals("insert") || word.equals("update") || word.equals("delete")) {

				int count = statement.executeUpdate(strQuery);
				
				String[] columnNames = {"Result"};
				
				model.setColumnIdentifiers(columnNames);
				
				String[] row = new String[1];
				
				row[0] = String.valueOf(count);
				
				clear = 1;
				model.addRow(row);

			}
			else
			{
				
				JFrame error = new JFrame("Error: Not valid query");
				error.setLayout(new BorderLayout());
				JLabel errormessage = new JLabel();
				JPanel panel = new JPanel();
				panel.setPreferredSize(new Dimension(100,100));
				JLabel labl= new JLabel();
				errormessage.setText("Not valid query please try again");
				labl.setText("                                                   ");
				error.setVisible(true);
				error.setSize(300,110);
				panel.add(labl,BorderLayout.NORTH);
				panel.add(errormessage,BorderLayout.CENTER);
				panel.setVisible(true);
				error.add(panel);
			}

			conn.close();
			statement.close();

		}

		catch (Exception ex) {

				JFrame error = new JFrame("Error: Not valid query");
				error.setLayout(new BorderLayout());
				JLabel errormessage = new JLabel();
				JPanel panel = new JPanel();
				panel.setPreferredSize(new Dimension(100,100));
				JLabel labl= new JLabel();
				errormessage.setText("Not valid query please try again");
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
