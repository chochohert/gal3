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
	private String[] title = { "과정 번호", "과정명", "강사", "교재", "강의실", "수강료", "주중/주말", "반", "주간/야간", "신청 만료일" };
	private JButton btn_submit, btn_close;
	private String student_no;

	public Register_frame(JTable tab_student) {
		this.student_no = tab_student.getValueAt(tab_student.getSelectedRow(), 0).toString();

		setTitle("수강 신청");

		model = new DefaultTableModel(title, 0) {

			@Override
			public boolean isCellEditable(int arg0, int arg1) {
				return false;
			}
		};
		tab_lecture = new JTable(model);
		scr_tab_lecture = new JScrollPane(tab_lecture);

		pane_south = new JPanel();
		btn_submit = new JButton("신청");
		btn_close = new JButton("닫기");

		pane_south.add(btn_submit);
		pane_south.add(btn_close);

		add("Center", scr_tab_lecture);
		add("South", pane_south);

		btn_submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tab_lecture.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "신청할 과정을 선택해주십시오.");
				} else {
					regist();
					JOptionPane.showMessageDialog(null, "신청이 완료되었습니다.");
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
				data[0] = rs.getNString(1); // 과정 번호
				data[1] = rs.getString(2); // 과정 이름
				data[2] = rs.getNString(3); // 강사 번호
				data[3] = rs.getNString(4); // 책 번호
				data[4] = rs.getNString(5); // 강의실 번호
				data[5] = new GetWon(rs.getInt(6)).changer(); // 수강료
				String week = null;
				if (rs.getString(7).equals("Y")) {
					week = "평일";
				} else {
					week = "주말";
				}
				data[6] = week; // 주중/주말
				data[7] = rs.getString(8); // 반
				String time = null;
				if (rs.getString(7).equals("Y")) {
					time = "주간";
				} else {
					time = "야간";
				}
				data[8] = time; // 주간/야간
				data[9] = rs.getString(10); // 신청 마감일

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
