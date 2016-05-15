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
import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

class Regist_pay_dialog extends JDialog{
	private DefaultTableModel model;
	private String[] title = { "과목명", "수강료" };
	private JScrollPane scr_tab_pay;
	private JTable tab_pay;
	private JPanel pane_pay, pane_lab, pane_btn;
	private JButton btn_ok, btn_cancel;
	private JLabel lab_pay;
	private String student_no;

	public Regist_pay_dialog(String student_no, JTable tab_regist) {
		this.student_no = student_no;
		
		setTitle("수강료 결제");
		setModal(true);
		
		model = new DefaultTableModel(title, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tab_pay = new JTable(model);
		scr_tab_pay = new JScrollPane(tab_pay);
		pane_pay = new JPanel(new BorderLayout());
		pane_lab = new JPanel();
		pane_btn = new JPanel();

		lab_pay = new JLabel("결제하시겠습니까?");

		btn_ok = new JButton("결제");
		btn_cancel = new JButton("취소");

		pane_lab.add(lab_pay);

		pane_btn.add(btn_ok);
		pane_btn.add(btn_cancel);

		pane_pay.add("Center", pane_lab);
		pane_pay.add("South", pane_btn);

		add("Center", scr_tab_pay);
		add("South", pane_pay);

		btn_ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 학번이 모두 같은 row들이기 때문에 첫번째 row의 학번을 선택하도록 변수 설정
				int default_row = 0;

				// 결제 버튼 눌렀을 때 regist.paid = 'Y'로 바꾸기
				// regist.pay_date = sysdate로 바꾸기
				Connection conn = null;
				PreparedStatement pst = null;
				ResultSet rs = null;
				String sql = "update regist set paid = 'Y', pay_date = sysdate where student_no = ?";
				try {
					conn = GetConn.getConnection();
					pst = conn.prepareStatement(sql);
					// 학번으로 수정
					pst.setString(1, student_no);
					pst.executeUpdate();
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

				JOptionPane.showMessageDialog(null, "결제가 완료되었습니다.");
				dispose();
			}
		});

		btn_cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		addWindowFocusListener(new WindowAdapter() {
			@Override
			public void windowGainedFocus(WindowEvent arg0) {
				model.setRowCount(0);
				display();
			}
		});

		tab_pay.getColumnModel().getColumn(0).setPreferredWidth(250);
		tab_pay.getColumnModel().getColumn(0).setResizable(false);
		tab_pay.getColumnModel().getColumn(1).setResizable(false);

		pack();
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	// 결제 정보 출력
	void display() {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		// 학번이 모두 같은 row들이기 때문에 첫번째 row의 학번을 선택하도록 변수 설정
		int default_row = 0;

		String sql = "SELECT l.lecture_name, l.lec_fee FROM regist r JOIN lecture l ON r.lecture_no = l.lecture_no WHERE l.eroll_end_date >= sysdate and r.student_no = ?";
		try {
			conn = GetConn.getConnection();
			pst = conn.prepareStatement(sql);
			// 학번으로 조회
			pst.setString(1, student_no);
			pst.executeQuery();
			rs = pst.getResultSet();
			int sum = 0;
			while (rs.next()) {
				String[] data = new String[2];
				data[0] = rs.getString(1);						// 과정명
				data[1] = new GetWon(rs.getInt(2)).changer();	// 수강료
				sum += rs.getInt(2);

				model.addRow(data);
			}
			// 마지막 행에 수강료 총계 표시
			String[] total = new String[2];
			total[0] = "총계";
			total[1] = new GetWon(sum).changer();

			model.addRow(total);
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
