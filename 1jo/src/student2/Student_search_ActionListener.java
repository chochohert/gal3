package student2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;

class Student_search_ActionListener implements ActionListener{
	
	public Student_search_ActionListener() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new Student_search_frame();
	}
}
