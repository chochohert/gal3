package staff;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class del extends JFrame {
	
	private JPanel contentPane;
	private JTextField no;
	//private JTextField name;
	public del() {
		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);

		JButton btndel = new JButton("삭제");
		panel.add(btndel);

		JButton btnall = new JButton("취소");
		panel.add(btnall);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		
		JLabel lblNewLabel_1 = new JLabel("번호 입력으로 지우기.");
		panel_2.add(lblNewLabel_1);

		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		
				 JLabel lblNewLabel = new JLabel("번호");
				 panel_3.add(lblNewLabel);
				 
				 		 no = new JTextField();
				 		 panel_3.add(no);
				 		 no.setColumns(10);
		
		btndel.addActionListener(new ActionListener() {
			Connection conn;
			PreparedStatement stmt;
		    ResultSet rs;
			@Override
			public void actionPerformed(ActionEvent e) {
		        try {
		        	Class.forName("oracle.jdbc.OracleDriver");
				    String url = "jdbc:oracle:thin:@localhost:1521:orcl";
					conn = DriverManager.getConnection(url, "academy", "1111");
		        	
		            String sql = "delete from staff where staff_no = ?";

		            stmt = conn.prepareStatement(sql);
		            stmt.setInt(1, Integer.parseInt(no.getText()));
		            stmt.executeUpdate();
		            System.out.println("성공");
		            no.setText("");
		            
		        }catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					 System.out.println("클래스를 찾을 수 없습니다." + e1);

				}  catch (SQLException se) {
		            System.out.println(se.getMessage());
		            System.out.println("실패");
		        }finally{
		        	try {
						stmt.close();
					} catch (SQLException err) {
					}
					try {
						conn.close();
					} catch (SQLException err) {
					}
		        }
				
			}
		});

		btnall.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				staffall up = new staffall();
				up.setVisible(true);
				dispose();
			}
		});

	}
}
