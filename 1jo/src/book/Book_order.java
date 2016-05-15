package book;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;


public class Book_order extends JFrame {
	// 책 구입
	private JPanel contentPane;
	private JTextField bookname;
	private JTextField supplier;
	private JTextField ordercnt;
	Connection conn = null;
	PreparedStatement stat = null;
	ResultSet rs = null;

	public Book_order(String value, String value2) {
		setResizable(false);

		setTitle("교재 구입");
		setBounds(100, 100, 350, 260);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(1, 4));

		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panel_5 = new JPanel();
		panel_2.add(panel_5);

		JLabel lblNewLabel = new JLabel("교재 번호 : ");
		panel_5.add(lblNewLabel);

		JComboBox bookcombo = new JComboBox();
		panel_5.add(bookcombo);

		JLabel lblNewLabel_2 = new JLabel("교재 이름 :");
		panel_5.add(lblNewLabel_2);

		bookname = new JTextField();
		panel_5.add(bookname);
		bookname.setColumns(10);

		try {

			conn = GetConn.getConnection();
			String sql = "select * from book";
			stat = conn.prepareStatement(sql);

			rs = stat.executeQuery();
			bookcombo.addItem("선택");
			while (rs.next()) {
				bookcombo.addItem(rs.getString("book_no"));
			}
			bookcombo.setSelectedItem(value);
			bookname.setText(value2);
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

		JPanel panel_7 = new JPanel();
		panel_2.add(panel_7);

		JLabel lblNewLabel_1 = new JLabel("공급자 번호");
		panel_7.add(lblNewLabel_1);

		JComboBox suppliercombo = new JComboBox();
		panel_7.add(suppliercombo);

		JLabel lblNewLabel_3 = new JLabel("공급자 이름");
		panel_7.add(lblNewLabel_3);

		supplier = new JTextField();
		panel_7.add(supplier);
		supplier.setColumns(10);

		try {
			conn = GetConn.getConnection();

			String sql = "select * from supplier";
			stat = conn.prepareStatement(sql);

			rs = stat.executeQuery();
			suppliercombo.addItem("선택");
			while (rs.next()) {
				suppliercombo.addItem(rs.getString("supplier_no"));
			}
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

		JPanel panel_6 = new JPanel();
		panel_2.add(panel_6);

		JLabel lblNewLabel_4 = new JLabel("주문 수량 : ");
		panel_6.add(lblNewLabel_4);

		ordercnt = new JTextField();
		panel_6.add(ordercnt);
		ordercnt.setColumns(10);

		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3);
		
				JButton order = new JButton("구매");
				panel_3.add(order);
				
						order.addActionListener(new ActionListener() {
				
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								Connection conn = null;
								PreparedStatement stat = null;
				
								try {
									conn = GetConn.getConnection();
				
									String sql = "insert into book_order(order_no,order_cnt,order_date,supplier_no,book_no,paid) values(book_order_cnt.nextval,?,sysdate,?,?,'N')";
				
									stat = conn.prepareStatement(sql);
									stat.setString(1, ordercnt.getText());
									stat.setString(2, (String) suppliercombo.getSelectedItem());
									stat.setString(3, (String) bookcombo.getSelectedItem());
				
									System.out.println(suppliercombo.getActionCommand());
				
									stat.executeUpdate();
									System.out.println("성공");
				
									dispose();
				
								} catch (SQLException err1) {
									System.out.println(err1);
								} finally {
									try {
										stat.close();
									} catch (SQLException err1) {
									}
									try {
										GetConn.closeConnection();
										conn.close();
									} catch (SQLException err1) {
									}
								}
							}
				
						});

		bookcombo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getItem() != "선택") {
					try {
						conn = GetConn.getConnection();

						String sql = "select * from book where book_no =?";
						stat = conn.prepareStatement(sql);
						stat.setString(1, (String) e.getItem());
						rs = stat.executeQuery();
						rs.next();
						System.out.println();
						bookname.setText(rs.getString("book_name"));

					} catch (SQLException err) {
						System.out.println(err);
					} finally {
						try {
							rs.close();
						} catch (SQLException err) {
						}
						try {
							stat.close();
						} catch (SQLException err) {
						}
						try {

							GetConn.closeConnection();
							conn.close();
						} catch (SQLException err) {
						}

					}

				}
			}
		});
		suppliercombo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getItem() != "선택") {
					try {
						conn = GetConn.getConnection();

						String sql = "select * from supplier where supplier_no =?";
						stat = conn.prepareStatement(sql);
						stat.setString(1, (String) e.getItem());
						rs = stat.executeQuery();
						rs.next();
						System.out.println();
						supplier.setText(rs.getString("supplier_name"));

					} catch (SQLException err) {
						System.out.println(err);
					} finally {
						try {
							rs.close();
						} catch (SQLException err) {
						}
						try {
							stat.close();
						} catch (SQLException err) {
						}
						try {
							GetConn.closeConnection();
							conn.close();
						} catch (SQLException err) {
						}

					}

				}
			}
		});
	}
}
