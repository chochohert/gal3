package student2;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

class Student_delete_ActionListener implements ActionListener {
	DefaultTableModel model;
	JTable tab_student;
	private int selectedRow;
	private String student_no;

	public Student_delete_ActionListener() {
	}

	public Student_delete_ActionListener(DefaultTableModel model, JTable tab_student) {
		this.model = model;
		this.tab_student = tab_student;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.selectedRow = tab_student.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(null, "삭제할 데이터를 선택해주십시오.");
		} else {
			this.student_no = tab_student.getValueAt(tab_student.getSelectedRow(), 0).toString();

			int confirm = JOptionPane.showConfirmDialog(null, "삭제하시겠습니까?");
			if (confirm == 0) {
				new Student_admin().delete(model, selectedRow, student_no);
			}
		}
	}
}
