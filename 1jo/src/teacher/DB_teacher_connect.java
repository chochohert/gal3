package teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_teacher_connect {

	PreparedStatement pstmt;
	ResultSet rs;
	Connection conn;
	Statement stmt;
	String[][] teacherTable;

	public DB_teacher_connect() {
		stmt = null;
		rs = null;
		conn = null;
		pstmt = null;
	}

	int[][] CountStudentRegisterlecture() {

		String sql;
		int Table[][] = null;
		Statement st = null;
		try {

			conn = GetConn.getConnection();
			sql = "select count(student_no) ,lecture_no from regist group by lecture_no";
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = st.executeQuery(sql);
			rs.last();
			Table = new int[rs.getRow()][2];

			rs.beforeFirst();

			int cnt = 0;

			while (rs.next()) {

				Table[cnt] = new int[2];
				Table[cnt][0] = rs.getInt(1);
				Table[cnt][1] = rs.getInt(2);
				cnt++;
			}

		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (st != null)
					st.close();
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

			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return Table;
		}

	}

	String[][] setClassbyteacher(int teacher_no) {
		String sql;
		int data[][] = CountStudentRegisterlecture();
		try {

			conn = GetConn.getConnection();
			sql = "select count(*) from lecture where teacher_no= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, teacher_no);
			rs = pstmt.executeQuery();
			rs.next();
			int size = rs.getInt(1);
			teacherTable = new String[size][7];

			sql = "select lecture_no,room_no,week,time,class,lecture_name from lecture where teacher_no= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, teacher_no);
			rs = pstmt.executeQuery();

			int cnt = 0;

			while (rs.next()) {

				teacherTable[cnt] = new String[7];
				teacherTable[cnt][0] = rs.getString(1);
				teacherTable[cnt][1] = rs.getString(2);

				if (rs.getString(3).equals("Y")) {
					teacherTable[cnt][2] = "주중";
				} else {
					teacherTable[cnt][2] = "주말";
				}

				if (rs.getString(4).equals("Y")) {
					teacherTable[cnt][3] = "오전";
				} else {
					teacherTable[cnt][3] = "오후";
				}

				teacherTable[cnt][4] = rs.getString(5);
				teacherTable[cnt][5] = rs.getString(6);

				for (int i = 0; i < data.length; i++) {
					if (teacherTable[cnt][0].equals(String.valueOf(data[i][1]))) {
						teacherTable[cnt][6] = String.valueOf(data[i][0]);
					}
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
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return teacherTable;
		}
	}

	@SuppressWarnings("finally")
	String[][] searchTeacherTable(String input) {
		String sql;
		try {

			conn = GetConn.getConnection();
			sql = "select count(*) from teacher where teacher_name like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + input + "%");
			rs = pstmt.executeQuery();
			rs.next();
			int size = rs.getInt(1);

			sql = "select teacher_no,teacher_name,teacher_jumin1,teacher_addr,regular,license_no,license_name,teacher_pay "
					+ "from teacher where teacher_name like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + input + "%");
			rs = pstmt.executeQuery();

			teacherTable = new String[size][9];
			int cnt = 0;

			while (rs.next()) {

				teacherTable[cnt] = new String[9];
				teacherTable[cnt][0] = rs.getString(1);
				teacherTable[cnt][1] = rs.getString(2);
				teacherTable[cnt][2] = rs.getString(3);
				teacherTable[cnt][3] = rs.getString(4);
				teacherTable[cnt][4] = rs.getString(5);
				teacherTable[cnt][5] = rs.getString(6);
				teacherTable[cnt][6] = rs.getString(7);
				teacherTable[cnt][7] = rs.getString(8);
				teacherTable[cnt][8] = "강의 보기";
				cnt++;

			}

			return teacherTable;

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
			return teacherTable;
		}
	}

	void updateTeacher(String name, int jumin1, String addr, String regular, int li_no, String li_name, int pay) {
		try {

			conn = GetConn.getConnection();

			String sql = "insert into teacher(teacher_no,teacher_name,teacher_jumin1,teacher_jumin2,teacher_addr,regular,license_no,license_name,teacher_pay)"
					+ " values(teacher_cnt.nextval,?,?,?,?,?,?,?,?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setInt(2, jumin1);
			pstmt.setInt(3, jumin1);

			pstmt.setString(4, addr);
			pstmt.setString(5, regular);

			pstmt.setInt(6, li_no);
			pstmt.setString(7, li_name);
			pstmt.setInt(8, pay);
			pstmt.executeUpdate();

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

	void deleteTeacher(String no) {
		try {

			conn = GetConn.getConnection();

			String sql = "delete teacher where teacher_no = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);
			pstmt.executeUpdate();

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

	@SuppressWarnings("finally")
	String[][] setTeacherTable() {
		String sql;
		try {

			conn = GetConn.getConnection();
			sql = "select count(*) from teacher";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			int size = rs.getInt(1);

			sql = "select teacher_no,teacher_name,teacher_jumin1,teacher_jumin2,teacher_addr,regular,license_no,license_name,teacher_pay from teacher";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			teacherTable = new String[size][9];
			int cnt = 0;

			while (rs.next()) {

				teacherTable[cnt] = new String[9];
				teacherTable[cnt][0] = rs.getString(1);
				teacherTable[cnt][1] = rs.getString(2);
				teacherTable[cnt][2] = rs.getString(3);
				teacherTable[cnt][3] = rs.getString(5);
				teacherTable[cnt][4] = rs.getString(6);
				teacherTable[cnt][5] = rs.getString(7);
				teacherTable[cnt][6] = rs.getString(8);
				teacherTable[cnt][7] = rs.getString(9);
				teacherTable[cnt][8] = "강의 보기";
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
			return teacherTable;
		}
	}

}
