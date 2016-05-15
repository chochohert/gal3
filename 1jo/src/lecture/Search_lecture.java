package lecture;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Search_lecture extends JFrame{
	JTextField tf_lecName;
	JButton btn_srch;
	JTable tab_srch_lec;
	JLabel lab_srch;
	JScrollPane scr_tab_srch_lec;
	DefaultTableModel model;
	String [] title = {"과정명", "담당 강사", "강의실"};
	JPanel pane_north;
	
	public Search_lecture() {
		model = new DefaultTableModel(title , 0);
		tab_srch_lec = new JTable(model);
		tf_lecName = new JTextField(30);
		lab_srch = new JLabel("과정명 : ");
		btn_srch = new JButton("검색");
		scr_tab_srch_lec = new JScrollPane(tab_srch_lec);
		pane_north = new JPanel();
		
		pane_north.add(lab_srch);
		pane_north.add(tf_lecName);
		pane_north.add(btn_srch);
		
		add("North", pane_north);
		add("Center", scr_tab_srch_lec);
		
		btn_srch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				search(tab_srch_lec, tf_lecName.getText());
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	private void search(JTable tab_srch, String name) {
		Connection conn = null;
		PreparedStatement pst = null;
		DefaultTableModel model = (DefaultTableModel) tab_srch.getModel();
		String sql = "select l.lecture_name, t.teacher_name, l.room_no from lecture l join teacher t on l.teacher_no = t.teacher_no where lecture_name like ? order by lecture_no";
		int r_cnt=model.getRowCount();
		 for(int i=r_cnt-1;i>=0;i--){
             model.removeRow(i);
          }
		
		try {
			conn = GetConn.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, "%"+name+"%");
			pst.executeQuery();
			ResultSet rs = pst.getResultSet();
			while (rs.next()) {
				String[] data = new String[3];
				data[0] = rs.getNString(1);
				data[1] = rs.getString(2);
				data[2] = rs.getNString(3);

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
	}
}
