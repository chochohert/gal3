package teacher;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class teacherup extends JFrame {

	private JPanel contentPane;
	private JTextField tno;
	private JTextField tname;
	private JTextField tju1;
	private JTextField tju2;
	private JTextField taddr;
	private JTextField tli_no;
	private JTextField tli_name;
	private JTextField tpay;

	private Connection conn = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;

	public teacherup(String teacher_no, String teacher_name, String teacher_jumin, String teacher_addr, String regular,
			String license_no, String license_name, String teacher_pay) {

		// String jumin[] = teacher_jumin.split("-");
		System.out.println(regular);
		setResizable(false);
		setBounds(100, 100, 300, 380);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setTitle("강사 정보 수정");
		contentPane.setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 277, 305);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblNewLabel = new JLabel("번호");
		lblNewLabel.setBounds(12, 8, 70, 18);
		panel_2.add(lblNewLabel);

		tno = new JTextField();
		tno.setBounds(94, 5, 116, 21);
		tno.setEnabled(false);
		panel_2.add(tno);
		tno.setColumns(10);

		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("이름");
		lblNewLabel_1.setBounds(12, 8, 81, 18);
		panel_3.add(lblNewLabel_1);

		tname = new JTextField();
		tname.setBounds(95, 5, 116, 21);
		panel_3.add(tname);
		tname.setColumns(10);

		JPanel panel_4 = new JPanel();
		panel.add(panel_4);
		panel_4.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("주민번호");
		lblNewLabel_2.setBounds(12, 8, 100, 15);
		panel_4.add(lblNewLabel_2);

		tju1 = new JTextField();
		tju1.setBounds(94, 5, 116, 21);
		tju1.setEnabled(false);
		panel_4.add(tju1);
		tju1.setColumns(10);

	

		JPanel panel_5 = new JPanel();
		panel.add(panel_5);
		panel_5.setLayout(null);

		JLabel lblNewLabel_4 = new JLabel("주소");
		lblNewLabel_4.setBounds(12, 8, 100, 15);
		panel_5.add(lblNewLabel_4);

		taddr = new JTextField();
		taddr.setBounds(94, 5, 116, 21);
		panel_5.add(taddr);
		taddr.setColumns(10);

		JPanel panel_6 = new JPanel();
		panel.add(panel_6);
		panel_6.setLayout(null);

		JLabel lblNewLabel_5 = new JLabel("자격증번호");
		lblNewLabel_5.setBounds(12, 8, 100, 15);
		panel_6.add(lblNewLabel_5);

		tli_no = new JTextField();
		tli_no.setBounds(94, 5, 116, 21);
		panel_6.add(tli_no);
		tli_no.setColumns(10);

		JPanel panel_7 = new JPanel();
		panel.add(panel_7);
		panel_7.setLayout(null);

		JLabel lblNewLabel_6 = new JLabel("자격증이름");
		lblNewLabel_6.setBounds(12, 8, 100, 15);
		panel_7.add(lblNewLabel_6);

		tli_name = new JTextField();
		tli_name.setBounds(94, 5, 116, 21);
		panel_7.add(tli_name);
		tli_name.setColumns(10);

		JPanel panel_8 = new JPanel();
		panel.add(panel_8);
		panel_8.setLayout(null);

		JLabel lblNewLabel_8 = new JLabel("존속 여부");
		lblNewLabel_8.setBounds(12, 9, 100, 15);
		panel_8.add(lblNewLabel_8);
		ButtonGroup group = new ButtonGroup();

		JRadioButton rdbtnY = new JRadioButton("Y");
		rdbtnY.setBounds(94, 5, 33, 23);
		JRadioButton rdbtnN = new JRadioButton("N");
		rdbtnN.setBounds(164, 5, 35, 23);

		group.add(rdbtnY);
		group.add(rdbtnN);
		panel_8.add(rdbtnY);
		panel_8.add(rdbtnN);

		JPanel panel_9 = new JPanel();
		panel.add(panel_9);
		panel_9.setLayout(null);

		JLabel lblNewLabel_7 = new JLabel("시급");
		lblNewLabel_7.setBounds(12, 8, 100, 15);
		panel_9.add(lblNewLabel_7);

		tpay = new JTextField();
		tpay.setBounds(94, 5, 116, 21);
		panel_9.add(tpay);
		tpay.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(5, 311, 277, 31);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JButton btnup = new JButton("수정");
		btnup.setBounds(49, 5, 83, 23);
		panel_1.add(btnup);

		JButton btnback = new JButton("취소");
		btnback.setBounds(156, 5, 83, 23);
		panel_1.add(btnback);

		tno.setText(teacher_no);
		tname.setText(teacher_name);
		tju1.setText(teacher_jumin);
		// tju2.setText(jumin[1]);
		taddr.setText(teacher_addr);
		if (regular.equals("Y")) {
			rdbtnY.setSelected(true);
		} else if (regular.equals("N")) {
			rdbtnN.setSelected(true);
		}
		tli_no.setText(license_no);
		tli_name.setText(license_name);
		tpay.setText(teacher_pay);

		btnup.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (isfullField()) {

					if (tju1.getText().length() != 6) {
						JOptionPane.showMessageDialog(null, "주민번호를 확인해 주세요.");
					} else {
						try {
							Class.forName("oracle.jdbc.OracleDriver");
							String url = "jdbc:oracle:thin:@localhost:1521:orcl";
							conn = DriverManager.getConnection(url, "academy", "1111");

							String sql = "update teacher set teacher_name = ?,teacher_addr = ?,regular = ?"
									+ ",license_no = ?,license_name = ?,teacher_pay = ? where teacher_no = ?";

							stmt = conn.prepareStatement(sql);
							stmt.setString(1, tname.getText());
							stmt.setString(2, taddr.getText());
							// stmt.setString(3, tju1.getText());
							// stmt.setString(4, tju2.getText());
							// stmt.setString(4, taddr.getText());
							if (rdbtnN.isSelected()) {
								stmt.setString(3, "N");

							} else if (rdbtnY.isSelected()) {
								stmt.setString(3, "Y");
							}

							stmt.setString(4, tli_no.getText());
							stmt.setString(5, tli_name.getText());
							stmt.setString(6, tpay.getText());

							stmt.setString(7, tno.getText());

							stmt.executeUpdate();
							System.out.println("성공");

							tno.setText("");
							tname.setText("");
							tju1.setText("");
							taddr.setText("");

							tli_no.setText("");
							tli_name.setText("");
							tpay.setText("");
							// tju2.setText("");

							new teacher().setVisible(true);
							;
							dispose();

						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							System.out.println("클래스를 찾을 수 없습니다." + e1);

						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							System.out.println(e1);

						} finally {
							try {
								stmt.close();

							} catch (SQLException err) {
							}
							try {
								conn.close();

							} catch (SQLException err) {
							}
						}

					}

				} else {
					JOptionPane.showMessageDialog(null, "수정하려면 입력해주세요.");
				}

			}
		});

		btnback.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});

	}

	boolean isfullField() {

		if (tno.getText().equals("") || taddr.getText().equals("") || 
				 tpay.getText().equals("") || tli_name.getText().equals("")
				|| tli_no.getText().equals("") || tname.getText().equals("")) {
			return false;
		}

		return true;

	}

}
