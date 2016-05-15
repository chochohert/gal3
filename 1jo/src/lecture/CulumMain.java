package lecture;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CulumMain extends JFrame implements WindowFocusListener, ActionListener {

	JTable table;
	JButton regiBu, reBu, closeBu, delBu, srchBu;
	JPanel panel;
	JScrollPane scroll;

	DefaultTableModel model;

	String title[] = { "강의번호", "강사명", "교재", "강의실", "강의명", "수강료", "주중/주말", "오전/오후", "현재 수강 인원", "최대 인원", "신청마감일","수강생 보기" };
	String content[][];
	Connection conn = null;
	Statement stmt = null;
	Statement stmt2 = null;
	ResultSet rs = null;
	ResultSet rs2 = null;

	@SuppressWarnings("finally")
	String[][] renew() {
		String data[][] = null;
		int cntstudent[][] = cntRegisterlecturestudent();
		// 메인 초기 테이블 보이기
		try {
			conn = GetConn.getConnection();

			String sql = "select lecture_no, t.teacher_name, b.book_name, r.room_no, lecture_name, lec_fee, week, time, r.com_cnt , eroll_end_date from lecture l inner join teacher t on l.teacher_no = t.teacher_no "
					+ " inner join book b on l.book_no= b.book_no inner join classroom r on l.room_no=r.room_no order by eroll_end_date";
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sql);
			rs.last();
			data = new String[rs.getRow()][12];
			int cnt = 0;

			rs.beforeFirst();

			while (rs.next()) {

				data[cnt][0] = String.valueOf(rs.getInt(1));
				data[cnt][1] = rs.getString(2);
				data[cnt][2] = rs.getString(3);
				data[cnt][3] = String.valueOf(rs.getInt(4));
				data[cnt][4] = rs.getString(5);
				data[cnt][5] = String.valueOf(rs.getInt(6));
				data[cnt][6] = rs.getString(7);
				data[cnt][7] = rs.getString(8);

				for (int i = 0; i < cntstudent.length; i++) {

					if (rs.getInt(1) == cntstudent[i][1]) {// 강의
						// 번호
						data[cnt][8] = String.valueOf(cntstudent[i][0]);// 현재 수강
																		// 인원

					}

				}

				data[cnt][9] = rs.getInt(9) + "";// 최대인원
				data[cnt][10] = String.valueOf(rs.getDate(10));
				data[cnt][11]="수강생 보기";
				cnt++;
			}

		} catch (Exception e1) {
			// System.out.println(e1);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();

				} else if (stmt2 != null) {
					stmt2.close();
				} else if (conn != null) {
					GetConn.closeConnection();
					conn.close();
				} else if (rs != null) {
					rs.close();
					rs2.close();
				}
			} catch (Exception e4) {
				e4.printStackTrace();
			}

			return data;
		}
	}

	int[][] cntRegisterlecturestudent() {
		int data[][] = null;
		try {
			conn = GetConn.getConnection();
			String sql = "select count(student_no) ,lecture_no from regist group by lecture_no";
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sql);
			rs.last();
			int size = rs.getRow();
			data = new int[size][2];
			rs.beforeFirst();

			int cnt = 0;
			while (rs.next()) {
				data[cnt][0] = rs.getInt(1);// 인원
				data[cnt][1] = rs.getInt(2);// 강의 번호
				cnt++;
			}

		} catch (SQLException sq) {
			sq.printStackTrace();

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
		return data;

	}

	void deleteLecture() {

		try {
			conn = GetConn.getConnection();
			int lec_no2 = Integer.parseInt((String) table.getValueAt(table.getSelectedRow(), 0));
			String sql1 = "Delete FROM lecture where lecture_no =" + lec_no2;
			stmt = conn.createStatement();
			stmt.executeQuery(sql1);

		} catch (SQLException sq) {
			sq.printStackTrace();

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
	}

	public CulumMain() {
		setResizable(false);
		table = new JTable();
		content = renew();
		model = new DefaultTableModel(content, title);
		table.setModel(model);
		panel = new JPanel();
		scroll = new JScrollPane(table);
		regiBu = new JButton("과정등록");
		reBu = new JButton("수정");
		closeBu = new JButton("닫기");
		delBu = new JButton("삭제");
		srchBu = new JButton("검색");

		addWindowFocusListener(this);

		add("Center", scroll);
		panel.add(regiBu);
		panel.add(reBu);
		panel.add(delBu);
		panel.add(srchBu);
		panel.add(closeBu);
		add("South", panel);

		closeBu.addActionListener(this);

		// 등록버튼
		regiBu.addActionListener(this);

		// 수정버튼
		reBu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// DB수정
				try {
					table.editCellAt(-1, -1);
				} catch (Exception e1) {
				}

				int row = table.getSelectedRow();
				int lec_no = Integer.parseInt((String) table.getValueAt(row, 0));
				String tea_name = (String) table.getValueAt(row, 1);
				String bo_name = (String) table.getValueAt(row, 2);
				int rno = Integer.parseInt((String) table.getValueAt(row, 3));
				String lec_name = (String) table.getValueAt(row, 4);
				int lec_fee = Integer.parseInt((String) table.getValueAt(row, 5));
				String we = (String) table.getValueAt(row, 6);
				String ti = (String) table.getValueAt(row, 7);
				String lastD = (String) table.getValueAt(row, 10);
				lastD = lastD.replace("-", "");

				new DbRevise(lec_no, tea_name, bo_name, rno, lec_name, lec_fee, we, ti, lastD);
				content = renew();
				model = new DefaultTableModel(content, title);
				table.setModel(model);

			}
		});
		
		srchBu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Search_lecture();
			}
		});

		// 삭제 버튼
		delBu.addActionListener(this);

		setSize(900, 550);
		setVisible(true);
		table.addMouseListener(new table_listener());
	}

	@Override
	public void windowGainedFocus(WindowEvent e) {
		// TODO Auto-generated method stub
		content = renew();
		model = new DefaultTableModel(content, title);
		table.setModel(model);
	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		// TODO Auto-generated method stub

	}
	class table_listener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			if (e.getComponent().equals(table)) {

				int col = table.getSelectedColumn();

				if (col == 11) {
					
					int row = table.getSelectedRow();// 교재를 공급하는 회사는 1곳.
					Object value = table.getValueAt(row, 0);
					System.out.println(value);
					new showlectureenrollstudent(Integer.valueOf(String.valueOf(value))).setVisible(true);

				}
			} 
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("삭제")) {
			deleteLecture();
			content = renew();
			model = new DefaultTableModel(content, title);
			table.setModel(model);
		} else if (e.getActionCommand().equals("닫기")) {
			dispose();

		} else if (e.getActionCommand().equals("과정등록")) {
			try {
				classRe cr = new classRe();
				cr.setVisible(true);

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}