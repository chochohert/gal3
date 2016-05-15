package student2;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Register_frame extends JFrame {
	private JTable tab_lecture;
	private JScrollPane scr_tab_lecture;
	private JPanel pane_south;
	private DefaultTableModel model;
	private String[] title = { "���� ��ȣ", "������", "����", "����", "���ǽ�", "������", "����/�ָ�", "��", "�ְ�/�߰�", "��û ������" };
	private JButton btn_submit, btn_close;
	private String student_no;

	public Register_frame(JTable tab_student) {
		this.student_no = tab_student.getValueAt(tab_student.getSelectedRow(), 0).toString();

		setTitle("���� ��û");

		model = new DefaultTableModel(title, 0) {

			@Override
			public boolean isCellEditable(int arg0, int arg1) {
				return false;
			}
		};
		tab_lecture = new JTable(model);
		scr_tab_lecture = new JScrollPane(tab_lecture);

		pane_south = new JPanel();
		btn_submit = new JButton("��û");
		btn_close = new JButton("�ݱ�");

		pane_south.add(btn_submit);
		pane_south.add(btn_close);

		add("Center", scr_tab_lecture);
		add("South", pane_south);

		btn_submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tab_lecture.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "��û�� ������ �������ֽʽÿ�.");
				} else {
					regist();
					JOptionPane.showMessageDialog(null, "��û�� �Ϸ�Ǿ����ϴ�.");
				}
			}
		});
		btn_close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		tab_lecture.getTableHeader().setReorderingAllowed(false);

		display();

		pack();
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private void display() {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		DefaultTableModel model = (DefaultTableModel) tab_lecture.getModel();
		String sql = "select l.lecture_no, l.lecture_name, t.teacher_name, b.book_name, l.room_no, l.lec_fee, l.week, l.class, l.time, l.eroll_end_date from lecture l "
				+ "join teacher t on l.teacher_no = t.teacher_no "
				+ "join book b on l.book_no = b.book_no "
				+ "order by l.lecture_no";
		try {
			conn = GetConn.getConnection();
			pst = conn.prepareStatement(sql);
			pst.executeQuery();
			rs = pst.getResultSet();
			while (rs.next()) {
				String[] data = new String[10];
				data[0] = rs.getNString(1); // ���� ��ȣ
				data[1] = rs.getString(2); // ���� �̸�
				data[2] = rs.getNString(3); // ���� ��ȣ
				data[3] = rs.getNString(4); // å ��ȣ
				data[4] = rs.getNString(5); // ���ǽ� ��ȣ
				data[5] = new GetWon(rs.getInt(6)).changer(); // ������
				String week = null;
				if (rs.getString(7).equals("Y")) {
					week = "����";
				} else {
					week = "�ָ�";
				}
				data[6] = week; // ����/�ָ�
				data[7] = rs.getString(8); // ��
				String time = null;
				if (rs.getString(7).equals("Y")) {
					time = "�ְ�";
				} else {
					time = "�߰�";
				}
				data[8] = time; // �ְ�/�߰�
				data[9] = rs.getString(10); // ��û ������

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
	} // display()

	private void regist() {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String lecture_no = tab_lecture.getValueAt(tab_lecture.getSelectedRow(), 0).toString();
		String sql = "insert into regist(regist_no, student_no, lecture_no) values (seq_regist_no.nextVal, ?, ?)";
		try {
			conn = GetConn.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, student_no);
			pst.setString(2, lecture_no);
			pst.executeUpdate();
			
			pst=conn.prepareStatement("select order_no from book_oreder_no where lecture_no=?");
			pst.setString(1, lecture_no);
			rs=pst.executeQuery();
			rs.next();
			int order_no=rs.getInt(1);
			
			pst=conn.prepareStatement("update book_order set order_cnt=order_cnt+1 where order_no=?");
			pst.setInt(1, order_no);
			pst.executeUpdate();
			
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
	} // regist()
}
