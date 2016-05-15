package book;

import java.awt.Choice;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
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

public class Bookupdate extends JFrame {

	private JPanel contentPane;
	private JTextField tname;
	private JTextField tpublisher;
	private JTextField tprice;
	private Choice supplier_choice;
	private DB_book_connect connect = new DB_book_connect();

	public Bookupdate(String book_name,String pub_name,String book_price,String book_no,String supplier_name) {
		setTitle("���� ����");
		setBounds(100, 100, 250, 240);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 224, 219);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(1, 4));

		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(null);

		JPanel panel_5 = new JPanel();
		panel_5.setBounds(0, 0, 224, 42);
		panel_2.add(panel_5);

		JLabel lname = new JLabel("���� �̸� : ");
		lname.setEnabled(false);
		panel_5.add(lname);

		tname = new JTextField();
		panel_5.add(tname);
		tname.setColumns(10);
		tname.setText(book_name);

		JPanel panel_7 = new JPanel();
		panel_7.setBounds(0, 40, 224, 42);
		panel_2.add(panel_7);

		JLabel lblNewLabel_2 = new JLabel("�� �� �� : ");
		lblNewLabel_2.setEnabled(false);
		panel_7.add(lblNewLabel_2);

		tpublisher = new JTextField();
		panel_7.add(tpublisher);
		tpublisher.setColumns(10);
		tpublisher.setText(pub_name);

		JPanel panel_6 = new JPanel();
		panel_6.setBounds(0, 82, 224, 73);
		panel_2.add(panel_6);
		panel_6.setLayout(null);

		JLabel lblNewLabel = new JLabel("�� �� �� : ");
		lblNewLabel.setEnabled(false);
		lblNewLabel.setBounds(23, 8, 56, 15);
		panel_6.add(lblNewLabel);

		tprice = new JTextField();
		tprice.setBounds(84, 5, 116, 21);
		panel_6.add(tprice);
		tprice.setColumns(10);
		tprice.setText(book_price);

		JLabel lblNewLabel_1 = new JLabel("�� �� �� : ");
		lblNewLabel_1.setEnabled(false);
		lblNewLabel_1.setBounds(22, 48, 57, 15);
		panel_6.add(lblNewLabel_1);

		supplier_choice = new Choice();
		supplier_choice.setBounds(84, 42, 116, 25);
		panel_6.add(supplier_choice);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 165, 224, 33);
		panel_2.add(panel_1);

		JButton insert = new JButton("����");
		panel_1.add(insert);
		

		insert.addActionListener(new ActionListener() {
			// no_count.nextval
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Connection conn = null;
				PreparedStatement pstat = null;
				ResultSet rs = null;
				if (tname.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "å ������ �Է� ���߽��ϴ�", "���� �߸� �Է��߽��ϴ�.", JOptionPane.WARNING_MESSAGE);
					return;
				} else if (tpublisher.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "���ǻ縦 �Է� ���߽��ϴ�.", "���� �߸� �Է��߽��ϴ�.", JOptionPane.WARNING_MESSAGE);
					return;
				} else if (tprice.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "������ �Է����� �ʾҽ��ϴ�.", "���� �߸� �Է��߽��ϴ�.",
							JOptionPane.WARNING_MESSAGE);
					return;
				} else if (tprice.getText().matches("[^0-9]")) {
					JOptionPane.showMessageDialog(null, "���ݿ��� ������ �Է����ּ���", "���� �߸� �Է��߽��ϴ�.",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				try {

					conn = GetConn.getConnection();
					
					String sql="select supplier_no from supplier where supplier_name=?";
					pstat = conn.prepareStatement(sql);
					pstat.setString(1, supplier_choice.getSelectedItem());
					rs=pstat.executeQuery();
					rs.next();
					int no=rs.getInt(1);
					
					sql = "update book set book_name = ?, publisher = ? , book_price = ?,supplier_no=? where book_no =?";
					pstat = conn.prepareStatement(sql);
					pstat.setString(1, tname.getText());
					pstat.setString(2, tpublisher.getText());
					pstat.setInt(3, Integer.valueOf(tprice.getText()));
					pstat.setInt(4, no);
					pstat.setInt(5, Integer.valueOf(book_no));
					pstat.executeQuery();

				} catch (SQLException err) {
					System.out.println(err);
				} catch (Exception er) {
					er.getMessage();
				} finally {
					try {
						if (pstat != null)
							pstat.close();
					} catch (SQLException err) {
						err.printStackTrace();
					}
					try {
						if (conn != null) {
							GetConn.closeConnection();
							conn.close();
						}
					} catch (SQLException errr) {
						errr.printStackTrace();
					}
					try {
						if (rs != null) {
							rs.close();
						}
					} catch (SQLException errr) {
						errr.printStackTrace();
					}
					dispose();
				}
			}

		});

		String choice[] = connect.supplierBook();
		for (int i = 0; i < choice.length; i++) {
			supplier_choice.add(choice[i]);
		}
		supplier_choice.select(supplier_name);

	}

}
