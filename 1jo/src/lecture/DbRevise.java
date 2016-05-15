package lecture;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DbRevise {
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	int a, b;

	public DbRevise(int lec_no, String tea_name, String bo_name, int rno, String lec_name, int lec_fee, String we,
			String ti, String lastD) {

		conn = GetConn.getConnection();
		String title[] = { "���ǹ�ȣ", "�����", "����", "���ǽ�", "���Ǹ�", "������", "����/�ָ�", "����/����", "��û������", "���ǽ�����" };
		DefaultTableModel model = new DefaultTableModel(title, 0);

		// teacher_name�� �������� teacher_no ��������
		try {
			String sql1 = "Select teacher_no From teacher where teacher_name = '" + tea_name + "'";
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sql1);

			rs.next();
			a = rs.getInt("teacher_no");
		} catch (Exception e1) {
			System.out.println("teacher_no");
		}

		// book_name�� �������� book_no ��������
		try {
			String sql2 = "Select book_no From book where book_name = '" + bo_name + "'";
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sql2);

			rs.next();
			b = rs.getInt("book_no");
		} catch (Exception e2) {
			System.out.println("book_no");
		}

		// ���� �ѷ��ֱ�
		try {

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String sql = "select teacher_no, week, time from  lecture where week=upper('" + we + "') AND time=upper('"
					+ ti + "') and teacher_no in(" + a + ")";
			rs = stmt.executeQuery(sql);
			rs.last();
			if (rs.getRow() > 0) {
				if (rs.getRow() == 1) {
					sql = "select teacher_no,lecture_no, week, time from  lecture where week=upper('" + we
							+ "') AND time=upper('" + ti + "') and lecture_no in(" + lec_no + ")";
					rs = stmt.executeQuery(sql);
					rs.last();
					if (rs.next()) {
						JOptionPane.showMessageDialog(null, "���ǽð��� ��Ĩ�ϴ�.");
						return;
					} else {
						sql = "select teacher_no, week, time from  lecture where week=upper('" + we
								+ "') AND time=upper('" + ti + "') and teacher_no=" + a + " and lecture_no not in("
								+ lec_no + ")";
						rs = stmt.executeQuery(sql);

						if (rs.next()) {
							JOptionPane.showMessageDialog(null, "���ǽð��� ��Ĩ�ϴ�.");
							return;
						}
					}

				}
			} else {
				sql = "select room_no, week, time from  lecture where week=upper('" + we + "') AND time=upper('" + ti
						+ "') and room_no in(" + rno + ")";
				rs = stmt.executeQuery(sql);
				rs.last();
				if (rs.getRow() == 0) {
				} else {
					JOptionPane.showMessageDialog(null, "������ �ִ� ���� �ð����Դϴ�.");
					return;
				}
			}
		} catch (Exception e8) {
			System.out.println("update1 " + e8);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}

				else if (conn != null) {
					GetConn.closeConnection();
					conn.close();
				} else if (rs != null) {
					rs.close();
				}
			} catch (Exception e3) {
			}
		}

		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			if (!we.equalsIgnoreCase("n") && !we.equalsIgnoreCase("Y") && !ti.equalsIgnoreCase("Y")
					&& !ti.equalsIgnoreCase("n")) {
				JOptionPane.showMessageDialog(null, "N, Y�� �Է� ���ּ���");
				JOptionPane.showMessageDialog(null, "N, Y�� ��밡���մϴ�");
				return;
			}

			String sql1 = "UPDATE lecture set lecture_no=" + lec_no + ", teacher_no =" + a + ", book_no = " + b
					+ ", room_no = " + rno + " ," + " lecture_name = '" + lec_name + "', lec_fee = " + lec_fee
					+ ", week = UPPER('" + we + "'), time = UPPER('" + ti + "'), eroll_end_date = to_date('" + lastD
					+ "', 'YYYYMMDD') where lecture_no = " + lec_no;

			rs = stmt.executeQuery(sql1);

			rs = stmt.executeQuery("update book_order set order_date = +to_date('" + lastD + "', 'YYYYMMDD') where book_no=" + b);

		} catch (SQLException sq) {
			System.out.println(sq);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}

				else if (conn != null) {
					GetConn.closeConnection();
					conn.close();
				} else if (rs != null) {
					rs.close();
				}
			} catch (Exception e3) {
			}
		}
	}

}