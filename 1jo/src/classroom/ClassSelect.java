package classroom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ClassSelect extends JFrame{
	JTable jt;
	Connection conn=null;
	Statement stmt=null;
	ResultSet rs=null;
	String data[][];
	String field_title[]={"강의실 번호","컴퓨터 대수","프로젝트 유무","관리 직원 번호","관리 직원 이름"};
	int cnt;
	String sql, sql2;
	ClassSelect(String field,int no){		
		try{
			conn = GetConn.getConnection();
			sql = "select room_no,com_cnt, prj, c.staff_no, s.staff_name from classroom c inner join staff s on c.staff_no=s.staff_no where ";
			
			
			if(field=="room_no"){
				sql2=sql+"room_no="+no;			
			}else if(field=="com_cnt"){
				sql2=sql+"com_cnt="+no;
			}else if(field=="prj"){
				if(no==1){
					sql2=sql+"prj='Y'";
				}else{
					sql2=sql+"prj='N'";
				}
			}else{
				sql2=sql+"c.staff_no="+no;
			}
			
			
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
		            ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sql2);
			
			rs.last();
			if(rs.getRow()==0){
				try{
				JOptionPane.showMessageDialog(null, "데이터가 존재하지 않습니다.");
				return;
				}catch(java.lang.NullPointerException er){
					
				}
				
			}else{
			data=new String[rs.getRow()][field_title.length];
			rs.beforeFirst();
			
			
			cnt=0;
			while(rs.next()){
				data[cnt][0]=Integer.toString(rs.getInt("room_no"));
				data[cnt][1]=Integer.toString(rs.getInt("com_cnt"));
				data[cnt][2]=rs.getString("prj");
				data[cnt][3]=Integer.toString(rs.getInt("staff_no"));
				data[cnt][4]=rs.getString("staff_name");
				cnt++;
			}
			}
			
		}catch(SQLException err){
			System.out.println(err);
		}finally{
			try {if(rs!=null)rs.close();} catch (SQLException e) {}
			try {if(stmt!=null)stmt.close();} catch (SQLException e) {}
			try {if(conn!=null){
				GetConn.closeConnection();
				conn.close();
			}} catch (SQLException e) {}
		}
		
		
		DefaultTableModel dtm = new DefaultTableModel(data,field_title){  //셀 수정 금지
			 public boolean isCellEditable(int row, int column){
			    return false;
			 }
			};
		
		jt=new JTable(dtm);
		
		jt.getTableHeader().setReorderingAllowed(false); // 이동 불가 
		jt.getTableHeader().setResizingAllowed(false); // 크기 조절 불가
		
		
		JScrollPane jsc=new JScrollPane(jt);
		
		add(jsc);
		
		setSize(500,500);
		setVisible(true);
	}

}
