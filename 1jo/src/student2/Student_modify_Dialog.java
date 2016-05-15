package student2;


import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

class Student_modify_Dialog extends JDialog implements ActionListener {
	private JTextField tf_name, tf_jumin1, tf_addr;
	private JLabel lab_explain, lab_name, lab_jumin1, lab_addr;
	private JButton btn_ok, btn_cancel;
	private JPanel pane1, pane2, pane3, pane4, pane5;
	private int student_no;
	
	public Student_modify_Dialog(int student_no, String student_name, String jumin1, String addr) {
		setTitle("수정");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(5, 0));

		pane1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pane2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pane3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pane4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pane5 = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		lab_explain = new JLabel("* 수정할 내용을 입력하고 확인을 누르세요.");

		btn_ok = new JButton("확인");
		btn_cancel = new JButton("취소");

		pane1.add(lab_explain);

		tf_name = new JTextField(5);
		tf_jumin1 = new JTextField(6);
		tf_addr = new JTextField(30);
		
		lab_name = new JLabel("이름 : ");
		lab_jumin1 = new JLabel("생년월일 : ");
		lab_addr = new JLabel("주소 : ");

		pane2.add(lab_name);
		pane2.add(tf_name);

		pane3.add(lab_jumin1);
		pane3.add(tf_jumin1);
		tf_jumin1.setColumns(10);

		pane4.add(lab_addr);
		pane4.add(tf_addr);
		tf_addr.setColumns(20);

		pane5.add(btn_ok);
		pane5.add(btn_cancel);

		btn_ok.addActionListener(this);
		btn_cancel.addActionListener(this);
		
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
		
		getContentPane().add(pane1);
		getContentPane().add(pane2);
		getContentPane().add(pane3);
		getContentPane().add(pane4);
		getContentPane().add(pane5);
		
		tf_name.setText(student_name);
		tf_jumin1.setText(jumin1);
		tf_addr.setText(addr);

		setModal(true);
		setSize(380, 220);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("확인")) {
			// 새로운 데이터 내용
			try {
				String student_name = tf_name.getText();
				int jumin1 = Integer.parseInt(tf_jumin1.getText());
				String addr = tf_addr.getText();

				// DB내용 수정 메소드 호출
				new Student_admin().modify(student_no, student_name, jumin1, addr);
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(null, "생년 월일을 입력하십시오.");
			}
			// 창 종료
			dispose();
		} else {
			// 취소 버튼을 누르면 창 종료
			dispose();
		}
	}
}