package student2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTable;

class Student_modify_ActionListener implements ActionListener {
	private JTable table;
	private int student_no;
	private String student_name;
	private String jumin1;
	private String addr;

	public Student_modify_ActionListener() {
	}

	public Student_modify_ActionListener(JTable table) {
		this.table = table;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (table.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(null, "수정할 데이터를 선택해주십시오.");
		} else {
			this.student_no = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
			this.student_name = table.getValueAt(table.getSelectedRow(), 1).toString();
			this.jumin1 = table.getValueAt(table.getSelectedRow(), 2).toString();
			this.addr = table.getValueAt(table.getSelectedRow(), 4).toString();
			new Student_modify_Dialog(student_no, student_name, jumin1, addr);
		}
	}
}