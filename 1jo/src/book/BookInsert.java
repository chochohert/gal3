package book;

import java.awt.BorderLayout;
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
import java.awt.Choice;

public class BookInsert extends JFrame {
	// 교재 입력.
	private JPanel contentPane;
	private JTextField tname;
	private JTextField tpublisher;
	private JTextField tprice;
	private Choice supplier_choice;
	private DB_book_connect connect = new DB_book_connect();

	public BookInsert() {
		setResizable(false);
		setTitle("교재 등록");
		setBounds(100, 100, 250, 240);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

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

		JLabel lname = new JLabel("교재 이름 : ");
		lname.setEnabled(false);
		panel_5.add(lname);

		tname = new JTextField();
		panel_5.add(tname);
		tname.setColumns(10);

		JPanel panel_7 = new JPanel();
		panel_7.setBounds(0, 40, 224, 42);
		panel_2.add(panel_7);

		JLabel lblNewLabel_2 = new JLabel("출 판 사 : ");
		lblNewLabel_2.setEnabled(false);
		panel_7.add(lblNewLabel_2);

		tpublisher = new JTextField();
		panel_7.add(tpublisher);
		tpublisher.setColumns(10);

		JPanel panel_6 = new JPanel();
		panel_6.setBounds(0, 82, 224, 73);
		panel_2.add(panel_6);
		panel_6.setLayout(null);

		JLabel lblNewLabel = new JLabel("교 재 비 : ");
		lblNewLabel.setEnabled(false);
		lblNewLabel.setBounds(23, 8, 56, 15);
		panel_6.add(lblNewLabel);

		tprice = new JTextField();
		tprice.setBounds(84, 5, 116, 21);
		panel_6.add(tprice);
		tprice.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("공 급 자 : ");
		lblNewLabel_1.setEnabled(false);
		lblNewLabel_1.setBounds(22, 48, 57, 15);
		panel_6.add(lblNewLabel_1);

		supplier_choice = new Choice();
		supplier_choice.setBounds(84, 42, 116, 25);
		panel_6.add(supplier_choice);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 165, 224, 33);
		panel_2.add(panel_1);

		JButton insert = new JButton("저장");
		panel_1.add(insert);
		
		

		insert.addActionListener(new ActionListener() {
			// no_count.nextval
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Connection conn = null;
				PreparedStatement stat = null;
				ResultSet rs=null;
				if (tname.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "책 제목을 입력 안했습니다", "값을 잘못 입력했습니다.", JOptionPane.WARNING_MESSAGE);
					return;
				} else if (tpublisher.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "출판사를 입력 안했습니다.", "값을 잘못 입력했습니다.", JOptionPane.WARNING_MESSAGE);
					return;
				} else if (tprice.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "가격을 입력하지 않았습니다.", "값을 잘못 입력했습니다.",
							JOptionPane.WARNING_MESSAGE);
					return;
				} else if (tprice.getText().matches("[^0-9]")) {
					JOptionPane.showMessageDialog(null, "가격에는 정수만 입력해주세요", "값을 잘못 입력했습니다.",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				try {

					conn = GetConn.getConnection();
					String sql = "select supplier_no  from supplier where supplier_name=? ";
					stat = conn.prepareStatement(sql);
					stat.setString(1, supplier_choice.getSelectedItem());
					rs=stat.executeQuery();
					rs.next();
					int supp_no=rs.getInt(1);
					
					sql = "insert into book(book_no,supplier_no,book_name,publisher,book_price) values(book_order_cnt.nextval,?,?,?,?)";
					stat = conn.prepareStatement(sql);
					stat.setInt(1,supp_no);
					stat.setString(2, tname.getText());
					stat.setString(3, tpublisher.getText());
					stat.setInt(4, Integer.parseInt(tprice.getText()));
					stat.executeUpdate();
					
									

				} catch (SQLException err) {
					JOptionPane.showConfirmDialog(null, "다시 입력하세요.");
				} finally {
					try {
						stat.close();
					} catch (SQLException err) {
						err.printStackTrace();
					}
					try {
						GetConn.closeConnection();
						conn.close();
					} catch (SQLException err) {
						err.printStackTrace();
					}
					try{
						rs.close();
					}catch(SQLException err){
						err.printStackTrace();
					}
					dispose();
				}
			}

		});

		String choice[] = connect.supplierBook();

		for (int i = 0; i < choice.length; i++) {
			supplier_choice.add(choice[i]);
		}
	}
}
