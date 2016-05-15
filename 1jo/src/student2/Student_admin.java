package student2;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

class Student_admin {
	public Student_admin() {

	}

	public void insert(String name, int jumin1, int jumin2, String addr) {
		if (new Jumin1_integrity(jumin1).check() == false) {
			JOptionPane.showMessageDialog(null, "�ֹε�Ϲ�ȣ ���ڸ��� Ȯ���ϼ���.");
			return;
		}
		if (new jumin2_integrity(jumin2).check() == false) {
			JOptionPane.showMessageDialog(null, "�ֹε�Ϲ�ȣ ���ڸ��� Ȯ���ϼ���.");
			return;
		}

		Connection conn = null;
		PreparedStatement pst = null;
		String sql = "insert into student values(seq_student_no.nextVal, ?, ?, ?, sysdate, ?)";
		try {
			conn = GetConn.getConnection();
			pst = conn.prepareStatement(sql);

			pst.setString(1, name);
			pst.setInt(2, jumin1);
			pst.setInt(3, jumin2);
			pst.setString(4, addr);

			pst.executeUpdate();
			// System.out.println("�Է� ����");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "�̸�, �ּҰ� ����ų� �ߺ��� �ֹ� ��ȣ �Դϴ�.");
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
				if (conn != null) {
					GetConn.closeConnection();
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
	} // insert(String name, int jumin1, int jumin2, String addr)

	public void delete(DefaultTableModel model, int selectedRow, String student_no) {
		model.removeRow(selectedRow);

		Connection conn = null;
		PreparedStatement pst = null;
		String sql = "delete from student where student_no = ?";
		try {
			conn = GetConn.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, student_no);
			pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
				if (conn != null) {
					GetConn.closeConnection();
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
	} // delete(JTable table, int student_no)

	public void display(JTable table) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		String sql = "select student_no, student_name, student_jumin1, reg_date, student_addr from student order by student_no";
		try {
			conn = GetConn.getConnection();
			pst = conn.prepareStatement(sql);
			pst.executeQuery();
			rs = pst.getResultSet();
			while (rs.next()) {
				String[] data = new String[5];
				data[0] = rs.getNString(1); // �й�
				data[1] = rs.getString(2); // �л� �̸�
				data[2] = rs.getNString(3); // �������
				data[3] = rs.getString(4); // �����
				data[4] = rs.getString(5); // �ּ�

				model.addRow(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (conn != null) {
					GetConn.closeConnection();
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
	} // display(JTable table)

	public void modify(int student_no, String student_name, int jumin1, String addr) {
		Connection conn = null;
		PreparedStatement pst = null;
		String sql = "update student set student_name = ?, student_jumin1 = ?, student_addr = ? where student_no = ?";
		try {
			conn = GetConn.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, student_name);
			pst.setInt(2, jumin1);
			pst.setString(3, addr);
			pst.setInt(4, student_no);
			pst.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "�̸�, �ּҰ� �ԷµǾ����� Ȯ���ϼ���.");
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
				if (conn != null) {
					GetConn.closeConnection();
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
	} // modify(int student_no, String student_name, int jumin1, int jumin2,
		// String addr)

	// �ֹι�ȣ ���ڸ� üũ Ŭ����
	private class Jumin1_integrity {
		private String jumin1;

		public Jumin1_integrity(int jumin1) {
			this.jumin1 = String.valueOf(jumin1);
		}

		public boolean check() {
			boolean result;
			if (jumin1.substring(2, 3).equals("0") || jumin1.substring(2, 3).equals("1")) {
				if (jumin1.substring(4, 5).equals("0") || jumin1.substring(4, 5).equals("1")
						|| jumin1.substring(4, 5).equals("2") || jumin1.substring(4, 5).equals("3")) {
					result = true;
				} else {
					result = false;
				}
			} else {
				result = false;
			}
			return result;
		}
	}

	// �ֹι�ȣ ���ڸ� üũ Ŭ����
	private class jumin2_integrity {
		private String jumin2;

		public jumin2_integrity(int jumin2) {
			this.jumin2 = String.valueOf(jumin2);
		}

		public boolean check() {
			boolean result;
			if (jumin2.substring(0, 1).equals("1") || jumin2.substring(0, 1).equals("2")
					|| jumin2.substring(0, 1).equals("3") || jumin2.substring(0, 1).equals("4")) {
				result = true;
			} else {
				result = false;
			}
			return result;
		}
	}
}
