package lecture;

import java.awt.Button;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

class classRe extends JFrame implements ActionListener, KeyListener {

	JTextField fie_lecture_name, fie_lec_fee, fie_cstu, fie_cl_name, fie_cstart;

	JButton regiBu, cancelBu, timeTable_bnt;

	inputNumberOnly onlynum = new inputNumberOnly();
	JLabel la_cstu, la_cstart;

	Choice classroom;
	private Choice book_1;
	private Choice teacher_1;
	Choice daychoice, monthchoice, yearchoice;

	int rn, tn, bn = 0;

	String t;

	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	private JTextField week_Field;
	private JTextField time_Field;
	private JLabel lblNewLabel_4;

	// 과정등록
	public classRe() throws SQLException {
		setResizable(false);
		setSize(450, 300);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(13, 10, 409, 85);
		getContentPane().add(panel);
		panel.setLayout(null);
		classroom = new Choice();
		classroom.setBounds(292, 10, 107, 26);
		panel.add(classroom);
		fie_cl_name = new JTextField("", 10);
		fie_cl_name.setBounds(60, 54, 116, 21);
		// fie_cl_name.setDocument(new inputNumberOnly());
		panel.add(fie_cl_name);
		teacher_1 = new Choice();
		teacher_1.setBounds(298, 54, 101, 21);
		panel.add(teacher_1);
		fie_lecture_name = new JTextField("", 10);
		fie_lecture_name.setBounds(60, 10, 116, 21);
		panel.add(fie_lecture_name);

		JLabel lblNewJgoodiesLabel = new JLabel("\uAC15\uC758\uC2E4 \uBC88\uD638");
		lblNewJgoodiesLabel.setEnabled(false);
		lblNewJgoodiesLabel.setBounds(200, 16, 114, 15);
		panel.add(lblNewJgoodiesLabel);

		JLabel lblNewJgoodiesLabel_1 = new JLabel("\uAC15\uC88C\uBA85");
		lblNewJgoodiesLabel_1.setEnabled(false);
		lblNewJgoodiesLabel_1.setBounds(0, 13, 114, 15);
		panel.add(lblNewJgoodiesLabel_1);

		JLabel label = new JLabel("\uBC18\uC774\uB984");
		label.setEnabled(false);
		label.setBounds(0, 60, 114, 15);
		panel.add(label);

		JLabel lblNewJgoodiesLabel_2 = new JLabel("\uB2F4\uB2F9\uAC15\uC0AC");
		lblNewJgoodiesLabel_2.setEnabled(false);
		lblNewJgoodiesLabel_2.setBounds(200, 60, 114, 15);
		panel.add(lblNewJgoodiesLabel_2);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(12, 105, 410, 42);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		week_Field = new JTextField();
		week_Field.setEditable(false);
		week_Field.setBounds(95, 10, 49, 21);
		panel_1.add(week_Field);
		week_Field.setColumns(10);

		JLabel lblNewLabel = new JLabel("\uC8FC\uC911/\uC8FC\uB9D0");
		lblNewLabel.setEnabled(false);
		lblNewLabel.setBounds(0, 13, 100, 15);
		panel_1.add(lblNewLabel);

		time_Field = new JTextField();
		time_Field.setEditable(false);
		time_Field.setBounds(236, 10, 49, 21);
		panel_1.add(time_Field);
		time_Field.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("\uC624\uC804/\uC624\uD6C4");
		lblNewLabel_1.setEnabled(false);
		lblNewLabel_1.setBounds(167, 13, 100, 15);
		panel_1.add(lblNewLabel_1);

		timeTable_bnt = new JButton("시간표");
		timeTable_bnt.setBounds(320, 5, 90, 31);
		panel_1.add(timeTable_bnt);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(12, 157, 410, 135);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		book_1 = new Choice();
		book_1.setBounds(48, 4, 136, 21);
		panel_2.add(book_1);
		fie_lec_fee = new JTextField("", 10);
		fie_lec_fee.setBounds(282, 4, 116, 21);
		fie_lec_fee.setDocument(new inputNumberOnly());
		panel_2.add(fie_lec_fee);

		yearchoice = new Choice();
		yearchoice.setBounds(92, 38, 60, 21);
		panel_2.add(yearchoice);

		for (int i = 2016; i < 2030; i++) {
			yearchoice.add(String.valueOf(i));
		}

		monthchoice = new Choice();
		monthchoice.setBounds(195, 38, 60, 21);
		panel_2.add(monthchoice);
		for (int i = 1; i < 13; i++) {
			if (i <= 9) {
				monthchoice.add("0" + String.valueOf(i));
			} else {
				monthchoice.add(String.valueOf(i));
			}
		}

		daychoice = new Choice();
		daychoice.setBounds(308, 38, 50, 22);

		for (int i = 1; i < 32; i++) {
			if (i <= 9) {
				daychoice.add("0" + String.valueOf(i));
			} else {
				daychoice.add(String.valueOf(i));
			}
		}

		panel_2.add(daychoice);
		regiBu = new JButton("등록");
		regiBu.setBounds(183, 73, 85, 23);
		panel_2.add(regiBu);
		cancelBu = new JButton("취소");
		cancelBu.setBounds(294, 73, 85, 23);
		panel_2.add(cancelBu);

		JLabel lblNewLabel_2 = new JLabel("\uB144");
		lblNewLabel_2.setEnabled(false);
		lblNewLabel_2.setBounds(158, 31, 35, 36);
		panel_2.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("\uC6D4");
		lblNewLabel_3.setEnabled(false);
		lblNewLabel_3.setBounds(261, 41, 36, 21);
		panel_2.add(lblNewLabel_3);

		lblNewLabel_4 = new JLabel("\uC77C");
		lblNewLabel_4.setEnabled(false);
		lblNewLabel_4.setBounds(364, 40, 32, 23);
		panel_2.add(lblNewLabel_4);

		JLabel lblNewJgoodiesLabel_3 = new JLabel("\uC218\uAC15\uB8CC");
		lblNewJgoodiesLabel_3.setEnabled(false);
		lblNewJgoodiesLabel_3.setBounds(212, 10, 114, 15);
		panel_2.add(lblNewJgoodiesLabel_3);

		JLabel lblNewJgoodiesLabel_4 = new JLabel("\uAD50\uC7AC");
		lblNewJgoodiesLabel_4.setEnabled(false);
		lblNewJgoodiesLabel_4.setBounds(0, 10, 114, 15);
		panel_2.add(lblNewJgoodiesLabel_4);

		JLabel lblNewJgoodiesLabel_5 = new JLabel("\uB4F1\uB85D\uB9C8\uAC10\uC77C");
		lblNewJgoodiesLabel_5.setEnabled(false);
		lblNewJgoodiesLabel_5.setBounds(0, 42, 114, 15);
		panel_2.add(lblNewJgoodiesLabel_5);

		// 취소버튼
		cancelBu.addActionListener(this);

		// 등록버튼
		regiBu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// DB업데이트 후 창 닫기

				PreparedStatement pstmt = null;

				if (isfullField()) {
					try {
						conn = GetConn.getConnection();
						String lec_name = fie_lecture_name.getText();

						// String
						int lec_fee = Integer.parseInt(fie_lec_fee.getText());
						String cl_name = fie_cl_name.getText();

						String week = week_Field.getText();
						String sql_week;

						if (week.equals("주중")) {
							sql_week = "Y";
						} else {
							sql_week = "N";
						}

						String time = time_Field.getText();

						String sql_time;

						if (time.equals("오전")) {
							sql_time = "Y";
						} else {
							sql_time = "N";
						}

						String lec_lasydate = yearchoice.getSelectedItem() + monthchoice.getSelectedItem()
								+ daychoice.getSelectedItem();

						int no = Integer.valueOf(classroom.getSelectedItem());

						String sql = "select com_cnt from classroom where room_no = ?";
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, no);
						rs = pstmt.executeQuery();
						rs.next();
						int com_cnt = rs.getInt(1);

						sql = "select teacher_no from teacher where TEACHER_NAME like ?";
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, teacher_1.getSelectedItem());
						rs = pstmt.executeQuery();
						rs.next();
						int t_no = rs.getInt(1);

						sql = "select book_no from book where book_name = ?";
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, book_1.getSelectedItem());
						rs = pstmt.executeQuery();
						rs.next();
						int book_no = rs.getInt(1);

						sql = "insert into lecture VALUES(seq_lecture_no.nextVal, ?,?, ?, ?,?, UPPER(?),?,UPPER(?),?, to_date(?, 'YYYYMMDD'))";
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, t_no);
						pstmt.setInt(2, book_no);
						pstmt.setInt(3, no);
						pstmt.setString(4, lec_name);
						pstmt.setInt(5, lec_fee);
						pstmt.setString(6, sql_week);
						pstmt.setString(7, cl_name);
						pstmt.setString(8, sql_time);
						pstmt.setInt(9, com_cnt);
						pstmt.setString(10, lec_lasydate);
						rs = pstmt.executeQuery();

						sql = "select supplier_no from book where book_no=?";
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, book_no);
						rs = pstmt.executeQuery();
						rs.next();
						int sub_no = rs.getInt(1);

						sql = "insert into book_order(order_no,order_date,supplier_no,book_no,paid,order_cnt)"
								+ " values(book_order_cnt.nextval,?,?,?,'N',0)";
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, lec_lasydate);
						pstmt.setInt(2, sub_no);
						pstmt.setInt(3, book_no);
						rs = pstmt.executeQuery();

					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "이미 선택된 교재입니다.");
						
					} finally {
						try {
							if (pstmt != null) {
								pstmt.close();
							}
							if (conn != null) {
								GetConn.closeConnection();
								conn.close();
							}
						} catch (Exception e9) {
						}
						dispose();
					}
				} else {
					JOptionPane.showMessageDialog(null, "추가하려면 입력해주세요.");
				}

			}
		});

		timeTable_bnt.addActionListener(this);
		setchoice();

	}

	void setchoice() {
		try {

			conn = GetConn.getConnection();

			String sql0 = "select room_no from classroom";
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sql0);

			// Choice에 강의시 번호를 받아와서 선택

			while (rs.next()) {
				classroom.add(String.valueOf(rs.getInt(1)));
			}

			// 담당강사 - 강사 테이블에서 강사이름을 받아오고 Choice로 보여져서 한명만 선택

			String sql = "select teacher_name FROM teacher";
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				teacher_1.add(String.valueOf(rs.getString(1)));

			}

			String sql1 = "select book_name FROM book";
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sql1);

			while (rs.next()) {

				book_1.addItem(String.valueOf(rs.getString("book_name")));

			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException re) {
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException err) {
			}
			try {
				if (conn != null) {
					GetConn.closeConnection();
					conn.close();
				}
			} catch (SQLException errr) {
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	boolean isfullField() {

		if (fie_cl_name.getText().equals("") || fie_lec_fee.getText().equals("")
				|| fie_lecture_name.getText().equals("")) {
			return false;
		}

		return true;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("시간표")) {
			timetable time_t = new timetable(classroom.getSelectedItem(), teacher_1.getSelectedItem());
			time_t.setVisible(true);
		} else if (e.getActionCommand().equals("취소")) {
			dispose();
		}
	}

	class timetable extends JFrame implements ActionListener {
		private JTable table;
		JScrollPane timetable_scroll;
		private DefaultTableModel model;
		String header[] = { "", "주중", "주말" };
		String content[][] = { { "오전" }, { "오후" } };

		JButton btnNewButton;
		private int value;
		private String time, week;

		private DB_timetable_connect connect = new DB_timetable_connect();

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("확인")) {

				if (table.getSelectedColumn() == -1) {
					JOptionPane.showMessageDialog(null, "시간을 선택해주세요.");
				} else {
					week_Field.setText(week);
					time_Field.setText(time);
					dispose();

				}
			}
		}

		public timetable(String value, String teacher_name) {

			this.value = Integer.valueOf(value);
			// schedule = connect.searchTime(this.value);
			getContentPane().setLayout(null);

			timetable_scroll = new JScrollPane();
			timetable_scroll.setBounds(0, 0, 430, 70);

			getContentPane().add(timetable_scroll);
			table = new JTable();

			timetable_scroll.setViewportView(table);

			content = connect.searchTime(this.value, teacher_name);

			model = new DefaultTableModel(content, header) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};

			table.setModel(model);
			table.getColumnModel().getColumn(0).setMaxWidth(200);
			table.getColumnModel().getColumn(1).setMaxWidth(200);
			table.getColumnModel().getColumn(2).setMaxWidth(200);
	

			btnNewButton = new JButton("확인");
			btnNewButton.setBounds(130, 69, 150, 23);
			getContentPane().add(btnNewButton);

			btnNewButton.addActionListener(this);
			table.addMouseListener(new table_listener());

			setSize(450, 140);
		}

		class table_listener extends MouseAdapter {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (e.getComponent().equals(table)) {

					int col = table.getSelectedColumn();
					int row = table.getSelectedRow();// 교재를 공급하는 회사는 1곳.
					Object value = table.getValueAt(row, col);
					String input = String.valueOf(value);
					
										
					if (!input.equals("")) {
						JOptionPane.showMessageDialog(null, "다른 시간을 선택하세요.");
					} else{

						if (col == 1) {
							week = "주중";
						} else if (col == 2) {
							week = "주말";
						}

						if (row == 0) {
							time = "오전";
						} else if (row == 1) {
							time = "오후";
						}
					}
				}
			}

		}
	}
}

class inputNumberOnly extends PlainDocument {

	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null)
			return;
		if (str.charAt(0) >= '0' && str.charAt(0) <= '9')
			super.insertString(offset, str, attr);
	}

}