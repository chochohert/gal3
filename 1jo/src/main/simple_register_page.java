package main;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Choice;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JCheckBox;

class inputNumberOnly extends PlainDocument {

	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null)
			return;
		if (str.charAt(0) >= '0' && str.charAt(0) <= '9')
			super.insertString(offset, str, attr);
	}

}

public class simple_register_page extends JFrame implements ActionListener, KeyListener {
	private JPanel panel;
	private JTextField name_field, jumin_field1, jumin_f2, addr_field1, addr_field2;
	private JPasswordField jumin_field2;
	private DB_main_connect connect;
	private JCheckBox paid;
	private JButton register_btn;
	private Choice state_choice;
	private String lec_name, lec_fee;

	public simple_register_page(String lec_name, String lec_fee) {
		setTitle("간편 수강생 등록");
		getContentPane().setLayout(null);
		setSize(430, 250);
		connect = new DB_main_connect();
		this.lec_name = lec_name;
		this.lec_fee = lec_fee;
		setResizable(false);
		panel = new JPanel();
		panel.setBounds(0, 0, 412, 211);
		getContentPane().add(panel);
		panel.setLayout(null);

		name_field = new JTextField();
		name_field.setBounds(75, 27, 116, 21);
		panel.add(name_field);
		name_field.setColumns(10);

		jumin_field1 = new JTextField(6);
		jumin_field1.setBounds(75, 58, 116, 21);
		panel.add(jumin_field1);
		jumin_field1.setColumns(10);
		jumin_field1.setDocument(new inputNumberOnly());

		JLabel name_label = new JLabel("이름");
		name_label.setEnabled(false);
		name_label.setBounds(12, 30, 57, 15);
		panel.add(name_label);

		JLabel jumin_label = new JLabel("주민 번호");
		jumin_label.setEnabled(false);
		jumin_label.setBounds(12, 58, 70, 21);
		panel.add(jumin_label);

		jumin_field2 = new JPasswordField(7);
		jumin_field2.setBounds(217, 58, 116, 21);
		panel.add(jumin_field2);
		jumin_field2.setColumns(10);
		jumin_field2.setDocument(new inputNumberOnly());

		JLabel adress_field = new JLabel("주 소");
		adress_field.setEnabled(false);
		adress_field.setBounds(12, 101, 57, 15);
		panel.add(adress_field);

		addr_field1 = new JTextField();
		addr_field1.setBounds(217, 100, 116, 21);
		panel.add(addr_field1);
		addr_field1.setColumns(10);

		addr_field2 = new JTextField();
		addr_field2.setBounds(75, 138, 283, 21);
		panel.add(addr_field2);
		addr_field2.setColumns(10);

		JLabel dash = new JLabel("-");
		dash.setBounds(197, 59, 24, 18);
		panel.add(dash);

		register_btn = new JButton("등 록");
		register_btn.setBounds(301, 175, 97, 23);
		panel.add(register_btn);

		state_choice = new Choice();
		state_choice.setForeground(Color.GRAY);
		state_choice.setBackground(Color.WHITE);
		state_choice.add("서울특별시");
		state_choice.add("경기도");
		state_choice.add("충청남도");
		state_choice.add("충청북도");
		state_choice.add("강원도");
		state_choice.add("전라남도");
		state_choice.add("전라북도");
		state_choice.add("경상남도");
		state_choice.add("경상북도");
		state_choice.add("부산광역시");
		state_choice.add("인천광역시");
		state_choice.add("광주광역시");
		state_choice.add("대전광역시");
		state_choice.add("세종자치시");
		state_choice.add("제주자치도");
		state_choice.setBounds(75, 101, 118, 21);
		panel.add(state_choice);

		paid = new JCheckBox("현장 결제");
		paid.setBounds(197, 175, 77, 23);
		panel.add(paid);

		register_btn.addActionListener(this);
		jumin_field1.addKeyListener(this);
		jumin_field2.addKeyListener(this);
	}

	boolean isfullField() {
		jumin_f2=jumin_field2;

		if (jumin_field1.getText().equals("") || jumin_f2.getText().equals("") || name_field.getText().equals("")
				|| addr_field1.getText().equals("") || addr_field2.getText().equals("")) {
			return false;
		}

		return true;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		jumin_f2=jumin_field2;
		
		if (new Jumin1_integrity(Integer.parseInt(jumin_field1.getText())).check() == false) {
			JOptionPane.showMessageDialog(null, "주민등록번호 앞자리를 확인하세요.");
			return;
		}
		if (new jumin2_integrity(Integer.parseInt(jumin_f2.getText())).check() == false) {
			JOptionPane.showMessageDialog(null, "주민등록번호 뒷자리를 확인하세요.");
			return;
		}

		if (e.getActionCommand().equals("등 록")) {

			if (isfullField()) {
				String lec_na = lec_name;
				int lec_fee_no = Integer.valueOf(lec_fee);
				String addr = state_choice.getSelectedItem() + " " + addr_field1.getText() + " "
						+ addr_field2.getText();

				connect.register_Student(name_field.getText(), Integer.parseInt(jumin_field1.getText()), Integer.parseInt(jumin_f2.getText()), addr,
						paid.isSelected(), lec_na, lec_fee_no);

			}else{
				JOptionPane.showMessageDialog(null, "등록하려면 입력하세요");
			}

			dispose();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		jumin_f2=jumin_field2;
		if (e.getComponent().equals(jumin_field1)) {
			if (jumin_field1.getText().length() >= 6) {
				jumin_field1.setText(jumin_field1.getText().substring(0, 5));
			}
		} else if (e.getComponent().equals(jumin_field2)) {
			if (jumin_f2.getText().length() >= 7) {
				jumin_field2.setText(jumin_f2.getText().substring(0, 6));
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	
	private class Jumin1_integrity {
		private String jumin1;

		public Jumin1_integrity(int jumin1) {
			this.jumin1 = String.valueOf(jumin1);
		}

		public boolean check() {
			boolean result;
			if (jumin1.substring(2, 3).equals("0") || jumin1.substring(2, 3).equals("1")) {
				if (jumin1.substring(4, 5).equals("0") || jumin1.substring(4, 5).equals("1")
						|| jumin1.substring(4, 5).equals("2") || jumin1.substring(4, 5).equals("3")) {
					result = true;
				} else {
					result = false;
				}
			} else {
				result = false;
			}
			return result;
		}
	}

	// 주민번호 뒷자리 체크 클래스
	private class jumin2_integrity {
		private String jumin2;

		public jumin2_integrity(int jumin2) {
			this.jumin2 = String.valueOf(jumin2);
		}

		public boolean check() {
			boolean result;
			if (jumin2.substring(0, 1).equals("1") || jumin2.substring(0, 1).equals("2")
					|| jumin2.substring(0, 1).equals("3") || jumin2.substring(0, 1).equals("4")) {
				result = true;
			} else {
				result = false;
			}
			return result;
		}
	}
}
