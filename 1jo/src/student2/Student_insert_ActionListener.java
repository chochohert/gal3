package student2;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

class Student_insert_ActionListener implements ActionListener {
	private DefaultTableModel model;
	private JTable tab_student;
	private JTextField tf_name, tf_jumin1, tf_jumin2, tf_addr;

	public Student_insert_ActionListener() {
	}

	public Student_insert_ActionListener(JTextField tf_name, JTextField tf_jumin1, JTextField tf_jumin2,
			JTextField tf_addr, DefaultTableModel model, JTable tab_student) {
		this.tf_name = tf_name;
		this.tf_jumin1 = tf_jumin1;
		this.tf_jumin2 = tf_jumin2;
		this.tf_addr = tf_addr;
		this.model = model;
		this.tab_student = tab_student;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			new Student_admin().insert(tf_name.getText(), Integer.parseInt(tf_jumin1.getText()),
					Integer.parseInt(tf_jumin2.getText()), tf_addr.getText());
		} catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(null, "주민등록번호를 입력하십시오.");
		}
		empty_fields();
		refresh_tab();
	}

	private void empty_fields() {
		tf_name.setText(null);
		tf_jumin1.setText(null);
		tf_jumin2.setText(null);
		tf_addr.setText(null);
	}

	private void refresh_tab() {
		model.setRowCount(0);
		new Student_admin().display(tab_student);
	}
}