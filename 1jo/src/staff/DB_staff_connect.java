package staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DB_staff_connect {

	PreparedStatement pstmt;
	ResultSet rs;
	Connection conn;
	Statement stmt;
	String[][] staffTable;

	public DB_staff_connect() {
		stmt = null;
		rs = null;
		conn = null;
		pstmt = null;
	}

	@SuppressWarnings("finally")
	String[][] searchstaffTable(String input) {
		String sql;
		int data[][] = serchClassroom();
		boolean flag = false;
		try {

			conn = GetConn.getConnection();
			sql = "select count(*) from staff where staff_name like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + input + "%");
			rs = pstmt.executeQuery();
			rs.next();
			int size = rs.getInt(1);
			// System.out.println(size);
			sql = "select s.staff_no,s.staff_name,s.staff_jumin1,s.staff_jumin2,s.staff_addr,s.staff_sal "
					+ "from staff s where s.staff_name like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + input + "%");
			rs = pstmt.executeQuery();

			staffTable = new String[size][6];
			int cnt = 0;

			while (rs.next()) {

				staffTable[cnt] = new String[6];
				staffTable[cnt][0] = rs.getString(1);
				staffTable[cnt][1] = rs.getString(2);
				staffTable[cnt][2] = rs.getString(3);
				staffTable[cnt][3] = rs.getString(5);
				staffTable[cnt][4] = rs.getString(6);
				staffTable[cnt][5] = "";
				for (int i = 0; i < data.length; i++) {
					if (staffTable[cnt][0].equals(String.valueOf(data[i][1]))) {
						staffTable[cnt][5] += String.valueOf(data[i][0]) + ", ";
						flag = true;
					}
				}
				if (!flag) {
					staffTable[cnt][5] = "";
				}

				cnt++;

			}

		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					GetConn.closeConnection();
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return staffTable;
		}
	}

	void updatestaff(String name, int jumin1, int jumin2, String addr, int sal) {
		try {

			conn = GetConn.getConnection();

			String sql = "insert into staff(staff_no,staff_name,staff_jumin1,staff_jumin2,staff_addr,staff_sal)"
					+ " values(staff_cnt.nextval,?,?,?,?,?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setInt(2, jumin1);
			pstmt.setInt(3, jumin2);
			pstmt.setString(4, addr);
			pstmt.setInt(5, sal);
			pstmt.executeUpdate();

			JOptionPane.showMessageDialog(null, "입력되었습니다.");

		} catch (SQLException err) {
			System.out.println(err);
		} catch (Exception err) {
			err.getMessage();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException err) {
				err.printStackTrace();
			}
			try {
				if (conn != null) {
					GetConn.closeConnection();
					conn.close();
				}
			} catch (SQLException err) {
				err.printStackTrace();
			}

		}
	}

	void deletestaff(String no) {
		try {

			conn = GetConn.getConnection();

			String sql = "delete staff where staff_no = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);
			pstmt.executeUpdate();
			System.out.println("성공");

		} catch (SQLException err) {
			System.out.println(err);
		} catch (Exception err) {
			err.getMessage();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException err) {
				err.printStackTrace();
			}
			try {
				if (conn != null) {
					GetConn.closeConnection();
					conn.close();
				}
			} catch (SQLException err) {
				err.printStackTrace();
			}

		}
	}

	int[][] serchClassroom() {
		String sql;
		int data[][] = null;
		try {

			conn = GetConn.getConnection();
			sql = "select room_no,staff_no from classroom order by staff_no,room_no";
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sql);
			rs.last();
			data = new int[rs.getRow()][2];

			rs.beforeFirst();

			int cnt = 0;
			while (rs.next()) {

				data[cnt] = new int[2];
				data[cnt][0] = rs.getInt(1);
				data[cnt][1] = rs.getInt(2);
				cnt++;
			}

		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					GetConn.closeConnection();
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return data;
		}

	}

	@SuppressWarnings("finally")
	String[][] setstaffTable() {
		String sql;
		int data[][] = serchClassroom();
		boolean flag = false;
		try {

			conn = GetConn.getConnection();
			sql = "select count(*) from staff";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			int size = rs.getInt(1);

			sql = "select staff_no,staff_name,staff_jumin1,staff_jumin2,staff_addr,staff_sal from staff ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			staffTable = new String[size][6];
			int cnt = 0;

			while (rs.next()) {

				staffTable[cnt][0] = rs.getString(1);
				staffTable[cnt][1] = rs.getString(2);
				staffTable[cnt][2] = rs.getString(3);
				staffTable[cnt][3] = rs.getString(5);
				staffTable[cnt][4] = rs.getString(6);
				staffTable[cnt][5] = "";
				for (int i = 0; i < data.length; i++) {
					if (staffTable[cnt][0].equals(String.valueOf(data[i][1]))) {
						staffTable[cnt][5] += String.valueOf(data[i][0]) + ", ";
						flag = true;
					}
				}

				if (flag == false) {
					staffTable[cnt][5] = "";
				}

				cnt++;
			}

		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					GetConn.closeConnection();
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return staffTable;
		}
	}

}
