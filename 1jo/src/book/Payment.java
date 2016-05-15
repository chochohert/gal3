package book;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Payment extends JFrame {
	// 대금 정보.
	DefaultTableModel model;
	private JPanel contentPane;
	private JTable table;
	private JButton btnNewButton;
	private JTextField textField;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public Payment(String value) {

		setTitle("대금 정보");
		setResizable(false);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		String[] list = { "거래번호", "교재이름", "공급자이름", "거래수량", "책단가", "총액", "대금날자" };
		// 중앙 시작
		DefaultTableModel model = new DefaultTableModel(list, 0);
		JTable table = new JTable(model);
		JScrollPane scroll = new JScrollPane(table);
		contentPane.add(scroll, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);

		textField = new JTextField();
		panel_1.add(textField);
		textField.setColumns(10);

		JButton serch = new JButton("검색");

		panel_1.add(serch);

		JButton allserch = new JButton("전체 보기");

		panel_1.add(allserch);
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;
		try {

			conn = GetConn.getConnection();
			if (value.equals("a")) {

				sql = "select o.order_no ,book.book_name, s.supplier_name,b.order_cnt,book_price,book_fee,fee_date    from order_payment o inner join book_order b on o.order_no = b.order_no inner join book  on b.book_no = book.book_no inner join supplier s on b.SUPPLIER_NO = s.supplier_no";
			} else {

				sql = "select o.order_no ,book.book_name, s.supplier_name,b.order_cnt,book_price,book_fee,fee_date    from order_payment o inner join book_order b on o.order_no = b.order_no inner join book  on b.book_no = book.book_no inner join supplier s on b.SUPPLIER_NO = s.supplier_no where s.supplier_no="
						+ value;
			}
			stat = conn.createStatement();
			stat.executeQuery(sql); // 명령어를 전달하고 실행하고 결과값을 들고오는 역활

			rs = stat.executeQuery(sql);

			while (rs.next()) {

				String[] row = new String[7];

				row[0] = rs.getString(1);
				row[1] = rs.getString(2);
				row[2] = rs.getString(3);
				row[3] = rs.getString(4);
				row[4] = rs.getString(5);
				row[5] = rs.getString(6);
				row[6] = rs.getString(7);

				model.addRow(row);

			}
			serch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					for (int i = model.getRowCount() - 1; i >= 0; i--) {
						model.removeRow(i);
					}
					System.out.println("수정");
					Connection conn = null;
					Statement stat = null;
					ResultSet rs = null;
					try {

						conn = GetConn.getConnection();
						String sql = "select o.order_no ,book.book_name, s.supplier_name,b.order_cnt,book_price,book_fee,fee_date    from order_payment o inner join book_order b on o.order_no = b.order_no inner join book  on b.book_no = book.book_no inner join supplier s on b.SUPPLIER_NO = s.supplier_no where book.book_name like '%"
								+ textField.getText() + "%'or s.supplier_name like '%" + textField.getText() + "%' ";
						System.out.println(sql);
						stat = conn.createStatement();
						stat.executeQuery(sql);
						rs = stat.executeQuery(sql);

						while (rs.next()) {

							String[] row = new String[7];

							row[0] = rs.getString(1);
							row[1] = rs.getString(2);
							row[2] = rs.getString(3);
							row[3] = rs.getString(4);
							row[4] = rs.getString(5);
							row[5] = rs.getString(6);
							row[6] = rs.getString(7);

							model.addRow(row);

						}
						rs.close();
						stat.close();
						GetConn.closeConnection();
						conn.close();
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
			});

			allserch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					for (int i = model.getRowCount() - 1; i >= 0; i--) {
						model.removeRow(i);
					}
					System.out.println("수정");
					Connection conn = null;
					Statement stat = null;
					ResultSet rs = null;
					try {

						conn = GetConn.getConnection();
						String sql = "select o.order_no ,b.book_name, s.supplier_name,b.order_cnt,b.book_price,o.book_fee,o.fee_date "
								+ "  from order_payment o inner join book_order b on o.order_no = b.order_no "
								+ "inner join book  on b.book_no = book.book_no"
								+ " inner join supplier s on b.SUPPLIER_NO = s.supplier_no";
						System.out.println(sql);
						stat = conn.createStatement();
						stat.executeQuery(sql);
						rs = stat.executeQuery(sql);

						while (rs.next()) {

							String[] row = new String[7];

							row[0] = rs.getString(1);
							row[1] = rs.getString(2);
							row[2] = rs.getString(3);
							row[3] = rs.getString(4);
							row[4] = rs.getString(5);
							row[5] = rs.getString(6);
							row[6] = rs.getString(7);

							model.addRow(row);

						}
						rs.close();
						stat.close();
						GetConn.closeConnection();
						conn.close();
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
			});

		} catch (SQLException err) {
			System.out.println(err);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
			}
			try {
				stat.close();
			} catch (SQLException e) {
			}
			try {
				GetConn.closeConnection();

				conn.close();
			} catch (SQLException e) {
			}
		}

	}

}
