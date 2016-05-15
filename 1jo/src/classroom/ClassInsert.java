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
			//�ùٸ��� ���� ���� ���� ���
			JOptionPane.showMessageDialog(null, "prj �������� Y �Ǵ� N���θ� �Է��� �ּ���");
			return;
			
		}
		room_no=no;
		String sql = "insert into classroom values("+room_no+","+com_cnt+","+"upper('"+prj+"')"+","+staff_no+")";
		stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
	            ResultSet.CONCUR_UPDATABLE);
		
		
		try{
			stmt.executeUpdate(sql);
			JOptionPane.showMessageDialog(null, "�ԷµǾ����ϴ�.");
			//new Class1();
	
		}catch(java.sql.SQLIntegrityConstraintViolationException err){
			//�������� �ʴ� ���� ���� ���
			JOptionPane.showMessageDialog(null, "�����ϴ� ���ǽ� ��ȣ�Դϴ�.");
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
		//���� close ���� �ʾ��� ������ close ����. ���� ������ ����.
	}
	}
}
