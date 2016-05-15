package student2;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

class Regist_admin_dialog extends JDialog {
	private DefaultTableModel model;
	private JTable tab_regist;
	private String[] title = { "등록 No.", "과정명", "강의실", "평일/주말", "주간/야간", "수강료", "결제 여부", "결제 날짜" };
	private JScrollPane scr_tab_regist;
	private JButton btn_pay, btn_regist, btn_reg_cancel;
	private JPanel pane_north, pane_north_west, pane_north_east;
	private String student_no;

	public Regist_admin_dialog(JTable tab_student) {
		this.student_no = tab_student.getValueAt(tab_student.getSelectedRow(), 0).toString();
		String name = tab_student.getValueAt(tab_student.getSelectedRow(), 1).toString();
		
		setTitle(name + " 수강 등록 관리");
		setModal(true);
		
		btn_pay = new JButton("결제");
		btn_regist = new JButton("추가 신청");
		btn_reg_cancel = new JButton("수강 취소");
		
		pane_north = new JPanel(new BorderLayout());
		pane_north_west = new JPanel();
		pane_north_east = new JPanel();

		model = new DefaultTableModel(title, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tab_regist = new JTable(model);
		scr_tab_regist = new JScrollPane(tab_regist);

		addWindowFocusListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				refresh_tab();
			}
			@Override
			public void windowGainedFocus(WindowEvent arg0) {
				refresh_tab();
				display();
			}
		});
		
		btn_pay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Regist_pay_dialog(student_no, tab_regist);
			}
		});
		btn_regist.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Register_dialog(tab_student);
			}
		});
		btn_reg_cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Connection conn = null;
				PreparedStatement pst = null;
				ResultSet rs = null;
//				private String[] title = { "수강 등록 번호", "과정명", "강의실", "주중/주말", "주간/야간", "수강료", "납부 여부", "납부 날짜" };
				String sql = "delete from regist where regist_no = ?";
				if(tab_regist.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "취소할 과정을 선택해주십시오.");
				} else {
					try {
						conn = GetConn.getConnection();
						pst = conn.prepareStatement(sql);
						pst.setString(1, tab_regist.getValueAt(tab_regist.getSelectedRow(), 0).toString());
						pst.executeQuery();
					} catch (SQLException sqle) {
						sqle.printStackTrace();
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
						} catch (SQLException sqle) {
							sqle.printStackTrace();
						}
					}
					JOptionPane.showMessageDialog(null, "취소가 완료되었습니다.");
				}
			}
		});
		
		pane_north_west.add(btn_regist);
		pane_north_west.add(btn_reg_cancel);
		pane_north_east.add(btn_pay);
		
		pane_north.add("East", pane_north_east);
		pane_north.add("West", pane_north_west);
		
		add("North", pane_north);
		add("Center", scr_tab_regist);
		
		tab_regist.getTableHeader().setReorderingAllowed(false);
		
		// 테이블 스크롤 정책
		scr_tab_regist.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scr_tab_regist.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		pack();
		setVisible(true);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
	} // Regist_admin_dialog()
	
	void refresh_tab() {
		model.setRowCount(0);
	} // refresh_tab();
	
	void display() {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "SELECT r.regist_no, l.lecture_name, l.room_no, l.week, l.time, l.lec_fee, r.paid, r.pay_date FROM regist r "
				+ "JOIN student s ON r.student_no = s.student_no "
				+ "JOIN lecture l ON r.lecture_no = l.lecture_no "
				+ "WHERE r.student_no = ?";
		try {
			conn = GetConn.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, student_no);
			pst.executeQuery();
			rs = pst.getResultSet();
			while (rs.next()) {
				String[] data = new String[8];
				data[0] = rs.getNString(1);	// 등록 번호
				data[1] = rs.getString(2);	// 과정명
				data[2] = rs.getNString(3);	// 강의실
				String week = null;
				if (rs.getString(4).equals("Y")||rs.getString(4).equals("y")) {
					week = "평일";
				} else {
					week = "주말";
				}
				data[3] = week;	// 평일/주말
				String time = null;
				if (rs.getString(5).equals("Y")||rs.getString(5).equals("y")) {
					time = "주간";
				} else {
					time = "야간";
				}
				data[4] = time;	// 주간/야간
				data[5] = new GetWon(rs.getInt(6)).changer();	// 수강료
				String paid = null;
				if(rs.getString(7).equals("Y")) {
					paid = "결제 완료";
				} else {
					paid = "미결제";
				}
				data[6] = paid;	// 납부 여부
				data[7] = rs.getString(8);	// 납부 날짜

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
}
