package lecture;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUpdate {
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	DBUpdate(){
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = GetConn.getConnection();
			String sql = "UPDATE lecture set lec_fee = 30 where lec_fee = 10";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			stmt = conn.createStatement(); //포장
			stmt.executeUpdate(sql);
		}
		catch (ClassNotFoundException e) {
			System.out.println("클래스 못 찾음");
		}
		 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		 finally{								
				try {if(stmt != null) stmt.close();
				} catch (SQLException e) {			}
				
				try {if(conn != null) conn.close();
				} catch (SQLException e) {			}
		 }

	}
}
