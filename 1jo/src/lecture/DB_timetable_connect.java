package lecture;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_timetable_connect {

	ResultSet rs;
	Connection conn;	
	PreparedStatement stmt;
	String schedule[][];
	

	public DB_timetable_connect() {
		stmt = null;
		rs = null;
		conn = null;

	}

	String[][] searchTime(int room_no,String teacher_name) {
		
		String sql;
		try {// 강의실 이용 현황을 시간표에 표시하기 위해 주중/주간, 오전/오후 값을 읽어온다.
			conn = GetConn.getConnection();
			sql="select teacher_no from teacher where teacher_name=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, teacher_name);
			rs = stmt.executeQuery();
			rs.next();
			int teacher_no=rs.getInt(1);
			sql = "select week,class, time, lecture_name,room_no from lecture where room_no=? or teacher_no =?" ;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, room_no);
			stmt.setInt(2, teacher_no);
			rs = stmt.executeQuery();

			schedule = new String[2][3];
			
			schedule[0][0] = new String();
			schedule[0][0] = "오전";
			schedule[0][1] = new String();
			schedule[0][2] = new String();
			schedule[1][0]= new String();
			schedule[1][0]= "오후";
			schedule[1][1]= new String();
			schedule[1][2]= new String();

			
			while (rs.next()) {
				
				String week = rs.getString(1);
				String time = rs.getString(3);
				
				if (week.equals("Y") && time.equals("Y")) {
					schedule[0][1] += rs.getInt(5)+"번 강의실 "+rs.getString(2)+"반"+" 과정 "+rs.getString(4);					
				} else if (week.equals("N") && time.equals("Y")) {
					schedule[0][2] += rs.getInt(5)+"번 강의실 "+rs.getString(2)+"반"+" 과정 "+rs.getString(4);					
				} else if (week.equals("Y") && time.equals("N")) {
					schedule[1][1] += rs.getInt(5)+"번 강의실 "+rs.getString(2)+"반"+" 과정 "+rs.getString(4);					
				} else if (week.equals("N") && time.equals("N")) {
					schedule[1][2] += rs.getInt(5)+"번 강의실 "+rs.getString(2)+"반"+" 과정"+rs.getString(4);					
				}
				
				
				
			}
			
			for(int i=0;i<schedule.length;i++){
				for(int j=0;j<schedule[1].length;j++){
					System.out.print(schedule[i][j]+"\t");
				}
				System.out.println();
			}
			
			
			return schedule;

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
