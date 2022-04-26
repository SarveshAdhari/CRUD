import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class Crud implements ActionListener {
	
	JFrame menu,create,delete,update;
	JButton menuBtn[] = new JButton[3];
	JButton cBtn,uBtn,dBtn;
	Font myFont = new Font("Courier New", Font.BOLD, 28);
	Font btnFont = new Font("Courier New", Font.BOLD, 24);
	Font tabFont = new Font("Courier New", Font.PLAIN, 18);
	JPanel menuHolder, dispHolder;
	JLabel menuLabel;
	JTable table;
	JScrollPane sp;
	
	//Components for Create Frame
	JButton sub,subcan;
	JTextField crtTf;
	
	//Components for Delete Frame
	JPanel upCan;
	JTextField upTf1,upTf2;
	JButton upd,upcan;
	
	//Components for Delete Frame
	JPanel delCan;
	JTextField delTf;
	JButton del,delcan;
	
	//Arrays for storing information from database
	String colNames[] = {"Task Id","Task Description"};
	
	Crud(){
		
		//Creating the menu frame
		menu = new JFrame("ToDo-Menu");
		//Other parameters of menu frame
		menu.setSize(850,500);
		menu.setLayout(null);
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Heading
		menuLabel = new JLabel("To-Do");
		menuLabel.setBounds(120,20,100,50);
		menuLabel.setFont(myFont);
		
		//Panel for buttons
		menuHolder = new JPanel();
		menuHolder.setBounds(40,180,250,200);
		menuHolder.setLayout(new GridLayout(3,1,10,10));
		
		//Create Button
		cBtn = new JButton("Add Task");
		menuBtn[0] = cBtn;
		menuHolder.add(cBtn);
		uBtn = new JButton("Update Task");
		menuBtn[1] = uBtn;
		menuHolder.add(uBtn);
		//Delete Button
		dBtn = new JButton("Delete Task");
		menuBtn[2] = dBtn;
		menuHolder.add(dBtn);
		
		for(int i=0;i<3;i++) {
			menuBtn[i].addActionListener(this);
			menuBtn[i].setFont(btnFont);
			menuBtn[i].setFocusable(false);
		}
		
		//Adding The Reading Panel
		dispHolder = new JPanel();
		dispHolder.setBounds(310,10,520,440);
		dispHolder.setBorder(BorderFactory.createMatteBorder(
                0, 5, 0, 0, Color.gray));
		
		
		//Connecting to database
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection cobj = DriverManager.getConnection("jdbc:mysql://localhost:3306/sarvesh","root","root");
			Statement stmt = cobj.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				    ResultSet.CONCUR_READ_ONLY);
			ResultSet rsobj = rs(stmt);
			int rows = getRowCount(rsobj);
			int cols = getColCount(rsobj);
			
			Object[][] data = new Object[rows][cols];
			
			//Going to first row
			rsobj.beforeFirst();
			
			//Code to store data into an object
			int i=0;
			while(rsobj.next()) {
				int j=0;
				data[i][j++] = rsobj.getInt(1);
				data[i][j++] = rsobj.getString(2);
				i++;
			}
			
			//Creating a table
			table = new JTable(data,colNames);
			table.setBounds(320,20,500,400);
			table.setFont(tabFont);
			table.setRowHeight(30);
			table.setEnabled(false);
			sp = new JScrollPane(table);
			
			//Changing Column 1 width
			TableColumnModel colMod = table.getColumnModel();
			TableColumn TC_TaskId = colMod.getColumn(1);
			TC_TaskId.setPreferredWidth(400);
			
			
			//Closing all the objects
			stmt.close();
			rsobj.close();
			cobj.close();
			
			
			dispHolder.add(sp);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Adding Components to frame(Menu)
		menu.add(menuLabel);
		menu.add(menuHolder);
		menu.add(dispHolder);
		
		
		//Setting Visible
		menu.setVisible(true);
		
		
		//Create Frame
		create = new JFrame("Add a task");
		create.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		create.setLayout(null);
		create.setSize(500,300);
		
		//Adding A Heading
		JLabel addTaskLabel = new JLabel("Add A New Task");
		addTaskLabel.setFont(myFont);
		addTaskLabel.setBounds(120,50,250,50);
		
		
		//Adding TextField and label(Task Description)
		JLabel tkDes = new JLabel("Enter Task Description");
		tkDes.setBounds(20,120,140,30);
		crtTf = new JTextField();
		crtTf.setBounds(20,150,440,35);
		
		//Adding a submit and cancel button
		
		JPanel subCan = new JPanel();
		subCan.setBounds(20,210,200,40);
		subCan.setLayout(new GridLayout(1,2,10,10));
		
		
		sub = new JButton("Submit");
		sub.setFocusable(false);
		subcan = new JButton("Cancel");
		subcan.setFocusable(false);
		
		sub.addActionListener(this);
		subcan.addActionListener(this);
		
		subCan.add(sub);
		subCan.add(subcan);
		
		
		
		//Adding Components to Frame
		create.add(addTaskLabel);
		create.add(tkDes);
		create.add(crtTf);
		create.add(subCan);
		
		
		//Update Frame
		update = new JFrame("Delete a task");
		update.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		update.setLayout(null);
		update.setSize(500,400);
		
		//Adding A Heading
		JLabel upTaskLabel = new JLabel("Update A Task");
		upTaskLabel.setFont(myFont);
		upTaskLabel.setBounds(120,50,250,50);
		
		
		//Adding TextField and label(Task Id)
		JLabel upId = new JLabel("Enter Task Id");
		upId.setBounds(20,120,140,30);
		upTf1 = new JTextField();
		upTf1.setBounds(20,150,440,35);
		
		//Adding TextField and label(Task Description)
		JLabel upDes = new JLabel("Enter Task Description");
		upDes.setBounds(20,190,140,30);
		upTf2 = new JTextField();
		upTf2.setBounds(20,220,440,35);
		
		//Adding a submit and cancel button
		
		upCan = new JPanel();
		upCan.setBounds(20,270,200,40);
		upCan.setLayout(new GridLayout(1,2,10,10));
		
		
		upd = new JButton("Update");
		upd.setFocusable(false);
		upcan = new JButton("Cancel");
		upcan.setFocusable(false);
		
		upd.addActionListener(this);
		upcan.addActionListener(this);
		
		upCan.add(upd);
		upCan.add(upcan);
		
		
		
		//Adding Components to Frame
		update.add(upTaskLabel);
		update.add(upId);
		update.add(upTf1);
		update.add(upDes);
		update.add(upTf2);
		update.add(upCan);
		
		
		
		//Delete Frame
		delete = new JFrame("Delete a task");
		delete.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		delete.setLayout(null);
		delete.setSize(500,300);
		
		//Adding A Heading
		JLabel delTaskLabel = new JLabel("Delete A Task");
		delTaskLabel.setFont(myFont);
		delTaskLabel.setBounds(120,50,250,50);
		delTf = new JTextField();
		delTf.setBounds(20,150,440,35);
		
		
		//Adding TextField and label(Task Description)
		JLabel delDes = new JLabel("Enter Task Id");
		delDes.setBounds(20,120,140,30);
		
		//Adding a submit and cancel button
		
		delCan = new JPanel();
		delCan.setBounds(20,210,200,40);
		delCan.setLayout(new GridLayout(1,2,10,10));
		
		
		del = new JButton("Delete");
		del.setFocusable(false);
		delcan = new JButton("Cancel");
		delcan.setFocusable(false);
		
		del.addActionListener(this);
		delcan.addActionListener(this);
		
		delCan.add(del);
		delCan.add(delcan);
		
		
		
		//Adding Components to Frame
		delete.add(delTaskLabel);
		delete.add(delDes);
		delete.add(delTf);
		delete.add(delCan);
		
		
		
		
		
	}
	
	//Method to return row count
	private int getRowCount(ResultSet rs) {
		try {
			if(rs!=null) {
				rs.last();
				return rs.getRow();
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return 0;
	}
	
	//Method to get column count
	private int getColCount(ResultSet rs) {
		try {
			if(rs!=null) {
				return rs.getMetaData().getColumnCount();
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return 0;
	}
	
	private ResultSet rs(Statement stmt) throws SQLException {
		ResultSet rsobj = stmt.executeQuery("Select * from crud");
		return rsobj;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//If Add Task Button Is Pressed on menu
		if(e.getSource()==cBtn) {
			menu.dispose();
			
			//Setting Frame Visible
			create.setVisible(true);
			
		}
		//If Cancel is Pressed on the Add a task frame
		if(e.getSource()==subcan) {
			create.setVisible(false);
			
			menu.setVisible(true);
		}
		//If Cancel is Pressed on the Update a task frame
		if(e.getSource()==upcan) {
			update.setVisible(false);
			
			menu.setVisible(true);
		}
		//If Cancel is Pressed on the Delete a task frame
		if(e.getSource()==delcan) {
			delete.setVisible(false);
			
			menu.setVisible(true);
		}
		
		//If Submit is Pressed on the Add a task frame
		if(e.getSource()==sub) {
			//Database Code for insert
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection cobj = DriverManager.getConnection("jdbc:mysql://localhost:3306/sarvesh","root","root");
				Statement stmt = cobj.createStatement();
				String inp=crtTf.getText();
				String qry = "insert into crud (task) values('"+inp+"')";
				int num = stmt.executeUpdate(qry);
				System.out.println("Number of lines inserted are "+num);
				stmt.close();
				cobj.close();
				crtTf.setText("");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
					
		create.dispose();
		Crud.main(null);
		}
		
		//If Delete is pressed on menu
		if(e.getSource()==dBtn) {
			menu.dispose();
			delete.setVisible(true);
		}
		//Database code for Delete
		if(e.getSource()==del) {
			
			//Database Code
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection cobj = DriverManager.getConnection("jdbc:mysql://localhost:3306/sarvesh","root","root");
				Statement stmt = cobj.createStatement();
				String inp=delTf.getText();
				String qry = "delete from crud where tid="+inp;
				int num = stmt.executeUpdate(qry);
				System.out.println("Number of lines deleted are "+num);
				stmt.close();
				cobj.close();
				delTf.setText("");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
						
			delete.dispose();
			Crud.main(null);
			}
		
			//If Update is pressed on menu
			if(e.getSource()==uBtn) {
				menu.dispose();
				update.setVisible(true);
			}
			//Database code for update
			if(e.getSource()==upd) {
				
				//Database Code
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection cobj = DriverManager.getConnection("jdbc:mysql://localhost:3306/sarvesh","root","root");
					Statement stmt = cobj.createStatement();
					String tId=upTf1.getText();
					String tDes=upTf2.getText();
					String qry = "update crud set task='"+tDes+"' where tid="+tId;
					int num = stmt.executeUpdate(qry);
					System.out.println("Number of lines updated are "+num);
					stmt.close();
					cobj.close();
					delTf.setText("");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
							
				update.dispose();
				Crud.main(null);
			}
				
		
	}
	
	public static void main(String[] args) {
		Crud c = new Crud();

	}

}
