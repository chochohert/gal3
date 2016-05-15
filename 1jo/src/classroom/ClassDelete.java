package classroom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class ClassDelete {
	
	ClassDelete(int no){
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		int room_no=no;
		
		try{
			Class.forName("oracle.jdbc.OracleDriver");
			String url="jdbc:oracle:thin:@localhost:1521:orcl";
			conn = DriverManager.getConnection
					  (url, "academy", "1111");
					
			String sql = "DELETE from classroom where room_no=?";
		
			stmt=conn.prepareStatement(sql);
			
			stmt.setInt(1, room_no);
			
			try{
			stmt.executeQuery();
			JOptionPane.showMessageDialog(null, "�����Ǿ����ϴ�.");
			}catch(java.sql.SQLSyntaxErrorException err){
				JOptionPane.showMessageDialog(null, "Ʈ���ŷ� ���� ������ �Ұ��� �մϴ�.");
			}
			
			
		}catch(ClassNotFoundException err){
			System.out.println("Ŭ������ ã�� �� �����ϴ�."+err);
		}catch(SQLException err){
			System.out.println(err);
		}finally{
			try {if(rs!=null)rs.close();} catch (SQLException e) {}
			try {if(stmt!=null)stmt.close();} catch (SQLException e) {}
			try {if(conn!=null)conn.close();} catch (SQLException e) {}
			//���� close ���� �ʾ��� ������ close ����. ���� ������ ����.
		}
	}
}
