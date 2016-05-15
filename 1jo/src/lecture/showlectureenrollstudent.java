package lecture;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;



public class showlectureenrollstudent extends JFrame implements ActionListener{
	
	
	private JPanel panel;
	JPanel panel_1;
	JScrollPane scrollPane;
	final String [] header={"학생 번호","이름","납부 날짜"};
	private String [][] content;
	private DefaultTableModel model;
	private JTable table;
	private JTextField textField;
	private JButton oK_bnt;
	private JButton exit_bnt;
	private  DB_showstudent_connect connect= new DB_showstudent_connect();
	
	public showlectureenrollstudent(int lecture_no) {
		setSize(400, 500);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		content=connect.searchTime(lecture_no);
		
		model=new DefaultTableModel(content,header){
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		
		
		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setEnabled(false);
		scrollPane.setViewportView(table);
		table.setModel(model);
	
		

		
		panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		

		
		
		
		setTitle("수강생 보기");
		
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("확인")){
			dispose();
									
		}else if(e.getActionCommand().equals("나가기")){
			dispose();
		}		
	}
	
	class DB_showstudent_connect {

		ResultSet rs;
		Connection conn;	
		Statement stmt;
		String schedule[][];
		

		public  DB_showstudent_connect() {
			stmt = null;
			rs = null;
			conn = null;

		}

		String[][] searchTime(int lecture_no) {
			
			String sql;
			try {// 강의실 이용 현황을 시간표에 표시하기 위해 주중/주간, 오전/오후 값을 읽어온다.
				conn = GetConn.getConnection();
				sql="select r.student_no, s.student_name, r.pay_date from regist r join student s on r.student_no = s.student_no where r.lecture_no ="+lecture_no;
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
		                  ResultSet.CONCUR_UPDATABLE);
				rs=stmt.executeQuery(sql);
				rs.last();
				int size=rs.getRow();
				
				schedule = new String[size][3];
				
				rs.beforeFirst();
				
				int cnt=0;
				
				while (rs.next()) {
					
					schedule[cnt]=new String[3];
					schedule[cnt][0]=rs.getString(1);
					schedule[cnt][1]=rs.getString(2);
					schedule[cnt][2]=rs.getString(3);
					cnt++;
				}
				
				

			} catch (SQLException err) {
				System.out.println(err);
			} finally {
				try {
					if (rs != null)
						rs.close();
				} catch (SQLException e) {
				}
				try {
					if (stmt != null)
						stmt.close();
				} catch (SQLException e) {
				}
				try {
					if (conn != null) {
						GetConn.closeConnection();
						conn.close();
					}
				} catch (SQLException e) {
				}
				return schedule;
			}
			
			
		}

	}

	
}





