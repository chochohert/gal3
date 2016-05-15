package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DB_main_connect {

	PreparedStatement stmt;
	ResultSet re;
	Object content[][];
	boolean flag;
	Connection conn;
	Statement st;
	int order_no;

	public DB_main_connect() {
		stmt = null;
		re = null;
		conn = null;
		st = null;
	}

	int returnOrder_no(String lecture_name) {

		try {

			conn = GetConn.getConnection();
			stmt = conn.prepareStatement("select order_no from book_oreder_no2 where lecture_name=?");
			stmt.setString(1, lecture_name);
			re = stmt.executeQuery();
			re.next();
			order_no = re.getInt(1);
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
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
			return order_no;
		}
	}

	void register_Student(String name, int jumin1, int jumin2, String addr, boolean paid, String lec_name,
			int lec_fee) {
		int order_no1 = returnOrder_no(lec_name);
		int lecture_no = 0;
		try {

			conn = GetConn.getConnection();
			stmt = conn.prepareStatement("insert into student values(seq_student_no.nextVal,?,?,?,sysdate,?)");
			stmt.setString(1, name);
			stmt.setInt(2, jumin1);
			stmt.setInt(3, jumin2);
			stmt.setString(4, addr);
			stmt.executeUpdate();

			stmt = conn.prepareStatement(
					"select student_no from student where student_name=? and student_jumin1=? and student_jumin2=?");
			stmt.setString(1, name);
			stmt.setInt(2, jumin1);
			stmt.setInt(3, jumin2);
			re = stmt.executeQuery();
			re.next();
			int student_num = re.getInt(1);

			stmt = conn.prepareStatement("select lecture_no from lecture where lecture_name=?");
			stmt.setString(1, lec_name);
			re = stmt.executeQuery();
			re.next();
			lecture_no = re.getInt(1);

			if (paid) {

				stmt = conn.prepareStatement("insert into regist values(seq_regist_no.nextVal,?,?,?,sysdate)");
				stmt.setInt(1, student_num);
				stmt.setInt(2, lecture_no);
				stmt.setString(3, "Y");
				stmt.executeUpdate();
			} else {

				stmt = conn.prepareStatement(
						"insert into regist(regist_no,student_no,lecture_no, paid) values(seq_regist_no.nextVal,?,?,?)");
				stmt.setInt(1, student_num);
				stmt.setInt(2, lecture_no);
				stmt.setString(3, "N");
				stmt.executeUpdate();

			}

			
			stmt = conn.prepareStatement("update book_order set order_cnt=order_cnt+1 where order_no=?");
			stmt.setInt(1, order_no1);
			stmt.executeUpdate();

			JOptionPane.showMessageDialog(null, "등록되었습니다.");

		} catch (SQLException e) {
			System.out.println(e);
			JOptionPane.showMessageDialog(null, "데이터 형식이 올바르지 않습니다.");
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
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
				if (re != null) {
					re.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	String setBasicInfo() {

		String Data = "";

		try {

			conn = GetConn.getConnection();
			stmt = conn.prepareStatement("select count(*) from student ");
			re = stmt.executeQuery();
			re.next();
			Data += re.getString(1) + "\t";
			stmt = conn.prepareStatement("select count(*) from lecture ");
			re = stmt.executeQuery();
			re.next();
			Data += re.getString(1)+"\t";
			stmt = conn.prepareStatement("select count(*) from teacher ");
			re = stmt.executeQuery();
			re.next();
			Data += re.getString(1)+"\t";
			stmt = conn.prepareStatement("select count(*) from staff ");
			re = stmt.executeQuery();
			re.next();
			Data += re.getString(1);

			return Data;

		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
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
		}
		return null;
	}

	@SuppressWarnings("finally")
	Object[][] getContentBooktable() {

		try {

			conn = GetConn.getConnection();
			stmt = conn.prepareStatement("select count(*) from book_order_info ");
			re = stmt.executeQuery();
			re.next();
			int size = re.getInt(1);
			stmt = conn.prepareStatement("select * from book_order_info ");
			re = stmt.executeQuery();

			content = new Object[size][6];

			int cnt = 0;

			while (re.next()) {

				content[cnt][0] = new Object();
				content[cnt][0] = re.getInt(1);
				content[cnt][1] = new Object();
				content[cnt][1] = re.getString(2);
				content[cnt][2] = new Object();
				content[cnt][2] = re.getInt(3);
				content[cnt][3] = new Object();
				content[cnt][3] = re.getDate(4);
				content[cnt][4] = new Object();
				content[cnt][4] = re.getString(5);
				content[cnt][5] = new Object();
				content[cnt][5] = "지급";
				cnt++;
			}

			return content;

		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
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
			return content;
		}

	}

	void updateBookData(String pub) {

		try {
			conn = GetConn.getConnection();
			// 업데이트 될 때 order_payment로 넘어가야 함

			String sql = "select bo.order_no, b.book_price " + "from book_order bo inner join book b "
					+ "on bo.book_no = b.book_no where b.book_no=?";

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, pub);
			re = stmt.executeQuery();
			re.next();
			String no = re.getString(1);// 오더 번호
			int price = re.getInt(2);// 가격

			sql = "select lecture_no from book_order o inner join lecture l on l.book_no=o.book_no where order_no=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, no);
			re = stmt.executeQuery();
			re.next();
			int lecture_no = re.getInt(1);// 강의 번호

			sql = "select count(student_no) from regist group by lecture_no having lecture_no=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, lecture_no);
			re = stmt.executeQuery();
			re.next();
			int cnt = re.getInt(1);// 학생수 강의당

			sql = "insert into order_payment values(?,?,sysdate)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, no);
			stmt.setInt(2, price * cnt);
			stmt.executeQuery();

			sql = "update book_order set paid = ? ,order_cnt= ? where order_no = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "Y");
			stmt.setInt(2, cnt);
			stmt.setString(3, no);
			int flag=stmt.executeUpdate();
			
			if(flag==1){
				JOptionPane.showMessageDialog(null, "대금이 지급되었습니다.");
			}

		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
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
				if (re != null)
					re.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@SuppressWarnings("finally")
	Object[][] getContentRegistertable() {

		try {
			conn = GetConn.getConnection();
			stmt = conn.prepareStatement("select count(*) from register_lecture_on_time ");
			re = stmt.executeQuery();
			re.next();
			int size = re.getInt(1);
			stmt = conn.prepareStatement(
					"select room_no, lecture_name, lec_fee, week, class, time, eroll_end_date, teacher_name from register_lecture_on_time ");
			re = stmt.executeQuery();

			content = new Object[size][8];

			int cnt = 0;

			while (re.next()) {

				content[cnt][0] = new Object();
				content[cnt][0] = re.getString(1);
				content[cnt][1] = new Object();
				content[cnt][1] = re.getString(2);
				content[cnt][2] = new Object();
				content[cnt][2] = re.getString(8);
				content[cnt][3] = new Object();
				content[cnt][3] = re.getInt(3);
				content[cnt][4] = new Object();

				String class_name = re.getString(5);
				String time = re.getString(6);
				String week = re.getString(4);
				String class_info = "";

				if (week.equalsIgnoreCase("Y")) {
					class_info += "주중 ";
				} else {
					class_info += "주말 ";
				}
				if (time.equalsIgnoreCase("Y")) {
					class_info += "오전 ";
				} else {
					class_info += "오후 ";
				}
				class_info += class_name + "반";
				content[cnt][4] = class_info;
				content[cnt][5] = new Object();
				content[cnt][5] = String.valueOf(re.getDate(7));
				content[cnt][6] = new Object();
				content[cnt][6] = "등록";
				cnt++;
			}

		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
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
			return content;
		}

	}

}
