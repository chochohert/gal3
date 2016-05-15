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
			
			JOptionPane.showMessageDialog(null, "�����Ǿ����ϴ�. �ߺ��� ���ǽ� ��ȣ�� �ڵ� �����˴ϴ�.");
			
		
		}catch(SQLException err){
			System.out.println(err);
			JOptionPane.showMessageDialog(null, "�ùٸ��� ���� ���� �߰��Ǿ����ϴ�.");

		}finally{
			try {if(rs!=null)rs.close();} catch (SQLException e) {}
			try {if(stmt!=null)stmt.close();} catch (SQLException e) {}
			try {
				if(conn!=null){
					GetConn.closeConnection();
					conn.close();
				}
			} catch (SQLException e) {}
			//���� close ���� �ʾ��� ������ close ����. ���� ������ ����.
		}
	}
}
