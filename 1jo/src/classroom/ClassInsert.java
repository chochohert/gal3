package classroom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ClassInsert {

	Connection conn=null;
	Statement stmt=null;
	int room_no;

	
	ClassInsert(int no, int com_cnt, String prj, int staff_no){

	try{
		conn = GetConn.getConnection();	
		if(!prj.equalsIgnoreCase("N")&&!prj.equalsIgnoreCase("Y")){
			//올바르지 못한 값이 들어올 경우
			JOptionPane.showMessageDialog(null, "prj 유무에는 Y 또는 N으로만 입력해 주세요");
			return;
			
		}
		room_no=no;
		String sql = "insert into classroom values("+room_no+","+com_cnt+","+"upper('"+prj+"')"+","+staff_no+")";
		stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	            ResultSet.CONCUR_UPDATABLE);
		
		
		try{
			stmt.executeUpdate(sql);
			JOptionPane.showMessageDialog(null, "입력되었습니다.");
			//new Class1();
	
		}catch(java.sql.SQLIntegrityConstraintViolationException err){
			//존재하지 않는 값이 들어올 경우
			JOptionPane.showMessageDialog(null, "존재하는 강의실 번호입니다.");
			return;
		}
		
	
		
	}catch(SQLException err){
		System.out.println(err);
	}finally{
		try {if(stmt!=null)stmt.close();} catch (SQLException e) {}
		try {if(conn!=null)conn.close();} catch (SQLException e) {}
		try{if(conn!=null){
			GetConn.closeConnection();
			conn.close();
		}}catch(SQLException e){}
		//아직 close 되지 않았을 때에만 close 실행. 보다 안전한 실행.
	}
	}
}
