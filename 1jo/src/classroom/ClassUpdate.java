package classroom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ClassUpdate {
	Connection conn=null;
	PreparedStatement stmt=null;
	ResultSet rs=null;
	
	ClassUpdate(int no, int com_cnt, String prj,int staff_no){
		
		int room_no=no;
		
		try{
			
			conn = GetConn.getConnection();
					
			String sql = "UPDATE classroom set com_cnt=? , prj=upper(?) , staff_no=? where room_no=?";
		
			stmt=conn.prepareStatement(sql);
	
			
			stmt.setInt(1, com_cnt);
			stmt.setString(2,prj);
			stmt.setInt(3, staff_no);
			stmt.setInt(4, room_no);
			stmt.executeQuery();	
			
			JOptionPane.showMessageDialog(null, "수정되었습니다. 중복된 강의실 번호는 자동 수정됩니다.");
			
		
		}catch(SQLException err){
			System.out.println(err);
			JOptionPane.showMessageDialog(null, "올바르지 않은 값이 추가되었습니다.");

		}finally{
			try {if(rs!=null)rs.close();} catch (SQLException e) {}
			try {if(stmt!=null)stmt.close();} catch (SQLException e) {}
			try {
				if(conn!=null){
					GetConn.closeConnection();
					conn.close();
				}
			} catch (SQLException e) {}
			//아직 close 되지 않았을 때에만 close 실행. 보다 안전한 실행.
		}
	}
}
