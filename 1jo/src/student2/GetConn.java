package student2;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class GetConn {
	private static Connection conn;
	
	private GetConn() {}
	// ������ private ó��

	static Connection getConnection() {
		if (conn != null) {
			return conn;
		}
		// conn ���� �̹� �����ϸ� �״�� ����

		try {
			Class.forName("oracle.jdbc.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			conn = DriverManager.getConnection(url, "academy", "1111");
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLException e) {
			System.out.println(e);
		}
		return conn;
		
	} // getConnection()
	
	static void closeConnection() {
		if (conn != null) {
			try {
				if(!conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}
}