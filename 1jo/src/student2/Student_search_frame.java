package student2;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

class Student_search_frame extends JFrame implements ActionListener {
	private JTable tab_srch;
	private String[] title = { "학번", "이름", "생년월일", "등록일", "주소"};
	private JScrollPane scr_search;
	private JPanel pane_search, pane_delmod, pane_north;
	private JTextField tf_name, tf_jumin1;
	private JLabel lab_name, lab_jumin1;
	private JButton btn_srch, btn_mod, btn_del;
	private DefaultTableModel model;

	public Student_search_frame() {
		super("수강생 검색");

		model = new DefaultTableModel(title, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tab_srch = new JTable(model);

		tf_name = new JTextField(5);
		tf_jumin1 = new JTextField(6);
		btn_srch = new JButton("검색");
		btn_mod = new JButton("수정");
		btn_del = new JButton("삭제");

		lab_name = new JLabel("이름 : ");
		lab_jumin1 = new JLabel("생년월일(YYMMDD) : ");

		scr_search = new JScrollPane(tab_srch);
		pane_search = new JPanel();
		pane_north = new JPanel(new BorderLayout());
		pane_delmod = new JPanel();

		pane_search.add(lab_name);
		pane_search.add(tf_name);
		pane_search.add(lab_jumin1);
		pane_search.add(tf_jumin1);
		pane_search.add(btn_srch);
		
		pane_delmod.add(btn_mod);
		pane_delmod.add(btn_del);

		pane_north.add("East", pane_delmod);
		pane_north.add("Center", pane_search);

		add("Center", scr_search);
		add("North", pane_north);
		
		// 숫자 입력 불가
		tf_name.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(Character.isDigit(e.getKeyChar())) {
					e.consume();
				}
			}
		});
		tf_name.setDocument(new JTextFieldLimit(5));
		
		// 생년월일 문자 입력 불가
		tf_jumin1.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				if(!Character.isDigit(e.getKeyChar())) {
					e.consume();
				}
			}
		});
		tf_jumin1.setDocument(new JTextFieldLimit(6));
		
		btn_del.addActionListener(new Student_delete_ActionListener(model, tab_srch));
		btn_mod.addActionListener(new Student_modify_ActionListener(tab_srch));

		btn_srch.addActionListener(this);
		
		tab_srch.getTableHeader().setReorderingAllowed(false);

		pack();
		setVisible(true);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		model.setRowCount(0);
		if (!(tf_name.getText().equals("")) && tf_jumin1.getText().equals("")) {
			search(tab_srch, tf_name.getText());
		} else if (!(tf_name.getText().equals("")) && !(tf_jumin1.getText().equals(""))) {
			search(tab_srch, tf_name.getText(), Integer.parseInt(tf_jumin1.getText()));
		} else {
			JOptionPane.showMessageDialog(null, "이름은 반드시 입력해야 합니다.");
		}
	}
	
	private void search(JTable tab_srch, String name) {
		Connection conn = null;
		PreparedStatement pst = null;
		DefaultTableModel model = (DefaultTableModel) tab_srch.getModel();
		String sql = "select student_no, student_name, student_jumin1, reg_date, student_addr from student where student_name = ? order by student_no";
		try {
			conn = GetConn.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, name);
			pst.executeQuery();
			ResultSet rs = pst.getResultSet();
			while (rs.next()) {
				String[] data = new String[6];
				data[0] = rs.getNString(1);
				data[1] = rs.getString(2);
				data[2] = rs.getNString(3);
				data[3] = rs.getString(4);
				data[4] = rs.getString(5);

				model.addRow(data);
			}
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
			} catch (SQLException e) {}
		}
	} // search(JTable table, String name)
	
	private void search(JTable tab_srch, String name, int birthDate) {
		Connection conn = null;
		PreparedStatement pst = null;
		DefaultTableModel model = (DefaultTableModel) tab_srch.getModel();
		String sql = "select student_no, student_name, student_jumin1, reg_date, student_addr from student where student_name = ? order by student_no";
		try {
			conn = GetConn.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, name);
			pst.executeQuery();
			ResultSet rs = pst.getResultSet();
			while (rs.next()) {
				String[] data = new String[6];
				data[0] = rs.getNString(1);
				data[1] = rs.getString(2);
				data[2] = rs.getNString(3);
				data[3] = rs.getString(4);
				data[4] = rs.getString(5);

				model.addRow(data);
			}
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
			} catch (SQLException e) {}
		}
	} // search(JTable table, String name, int birthDate)
}
