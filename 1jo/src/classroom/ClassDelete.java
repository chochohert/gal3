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
			JOptionPane.showMessageDialog(null, "삭제되었습니다.");
			}catch(java.sql.SQLSyntaxErrorException err){
				JOptionPane.showMessageDialog(null, "트리거로 인해 삭제가 불가능 합니다.");
			}
			
			
		}catch(ClassNotFoundException err){
			System.out.println("클래스를 찾을 수 없습니다."+err);
		}catch(SQLException err){
			System.out.println(err);
		}finally{
			try {if(rs!=null)rs.close();} catch (SQLException e) {}
			try {if(stmt!=null)stmt.close();} catch (SQLException e) {}
			try {if(conn!=null)conn.close();} catch (SQLException e) {}
			//아직 close 되지 않았을 때에만 close 실행. 보다 안전한 실행.
		}
	}
}
