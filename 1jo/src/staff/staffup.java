package staff;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class staffup extends JFrame {
	private JTextField no;
	private JTextField name;
	private JTextField ju1;
	private JTextField addr;
	private JTextField sal;
	private JPanel contentPane;

	private Connection conn = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	String[] list = { "번호", "이름", "주민번호", "주소", "월급" };

	public staffup(String staff_no, String staff_name, String staff_jumin, String staff_addr, String staff_sal) {
		setBounds(100, 100, 280, 330);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);
		System.out.println(staff_name);
		setTitle("직원 정보 수정");
		contentPane.setLayout(null);
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(5, 254, 265, 48);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JButton btnup = new JButton("수정");
		btnup.setBounds(45, 10, 78, 23);
		panel_1.add(btnup);

		JButton btncen = new JButton("취소");
		btncen.setBounds(136, 10, 89, 23);
		panel_1.add(btncen);

		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 265, 249);
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblNewLabel = new JLabel("번호");
		lblNewLabel.setBounds(12, 8, 62, 15);
		panel_2.add(lblNewLabel);

		no = new JTextField();
		no.setBounds(89, 5, 116, 21);
		no.setEnabled(false);
		panel_2.add(no);
		no.setColumns(10);

		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("이름");
		lblNewLabel_1.setBounds(12, 8, 75, 15);
		panel_3.add(lblNewLabel_1);

		name = new JTextField();
		name.setBounds(89, 5, 116, 21);
		panel_3.add(name);
		name.setColumns(10);

		JPanel panel_4 = new JPanel();
		panel.add(panel_4);
		panel_4.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("주민번호");
		lblNewLabel_2.setBounds(12, 8, 83, 15);
		panel_4.add(lblNewLabel_2);

		ju1 = new JTextField();
		ju1.setBounds(89, 5, 72, 21);
		ju1.setEnabled(false);
		panel_4.add(ju1);
		ju1.setColumns(6);

		JLabel lblNewLabel_5 = new JLabel("-");
		lblNewLabel_5.setBounds(170, 8, 6, 15);
		panel_4.add(lblNewLabel_5);

		JPanel panel_5 = new JPanel();
		panel.add(panel_5);
		panel_5.setLayout(null);

		JLabel lblNewLabel_3 = new JLabel("주소");
		lblNewLabel_3.setBounds(12, 8, 82, 15);
		panel_5.add(lblNewLabel_3);

		addr = new JTextField();
		addr.setBounds(89, 5, 116, 21);
		panel_5.add(addr);
		addr.setColumns(10);

		JPanel panel_6 = new JPanel();
		panel.add(panel_6);
		panel_6.setLayout(null);

		JLabel lblNewLabel_4 = new JLabel("월급");
		lblNewLabel_4.setBounds(12, 8, 89, 15);
		panel_6.add(lblNewLabel_4);

		sal = new JTextField();
		sal.setBounds(89, 5, 116, 21);
		panel_6.add(sal);
		sal.setColumns(10);

		JPanel panel_7 = new JPanel();
		panel_7.setBounds(348, 5, 10, 249);
		contentPane.add(panel_7);

		no.setText(staff_no);
		name.setText(staff_name);
		ju1.setText(staff_jumin);
		addr.setText(staff_addr);
		sal.setText(staff_sal);

		btnup.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (isfullField()) {

					if (ju1.getText().length() != 6) {
						JOptionPane.showMessageDialog(null, "주민번호를 확인해 주세요.");
					} else {
						try {
							Class.forName("oracle.jdbc.OracleDriver");
							String url = "jdbc:oracle:thin:@localhost:1521:orcl";
							conn = DriverManager.getConnection(url, "academy", "1111");

							String sql = "update staff set staff_name = ? , staff_jumin1 = ?, staff_addr = ? , staff_sal = ? where staff_no = ?";

							stmt = conn.prepareStatement(sql);
							stmt.setString(1, name.getText());
							stmt.setString(2, ju1.getText());
							stmt.setString(3, addr.getText());
							stmt.setString(4, sal.getText());
							stmt.setString(5, no.getText());

							stmt.executeUpdate();
							System.out.println("성공");
							new staffall().setVisible(true);
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
		btncen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
	}

	boolean isfullField() {

		if (ju1.getText().equals("") || name.getText().equals("") || sal.getText().equals("")
				|| addr.getText().equals("")) {
			return false;
		}

		return true;

	}

}
