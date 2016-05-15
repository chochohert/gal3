package book;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.jws.WebParam.Mode;
import javax.swing.AbstractListModel;
import java.awt.Choice;

public class Book extends JFrame implements ActionListener,WindowFocusListener {
	// 교재 초기화면
	JPanel contentPane;
	JTable table;
	JTextField textField;
	DefaultTableModel model;
	DB_book_connect connect = new DB_book_connect();
	JButton serch;
	String[][] content;
	JScrollPane scroll;
	JPanel panel_1;
	JButton payment;
	JButton order;
	JButton supplier;
	JButton book_insert;
	JPanel panel;
	final String[] list = { "교재번호", "교재이름", "출판사", "교재비","공급자","수정","삭제" };
	private JList list_1;
	private Choice choice;

	/**
	 * Launch the application.
	 */

	public Book() {
		setTitle("교재");
		addWindowFocusListener(this);
		setResizable(false);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		panel = new JPanel();
		panel.setBounds(5, 224, 424, 33);
		contentPane.add(panel);

		book_insert = new JButton("교재 입력");
		panel.add(book_insert);
		book_insert.addActionListener(new ActionListener() {
			// 교재입력 버튼.
			@Override
			public void actionPerformed(ActionEvent e) {
				// 작
				new BookInsert().setVisible(true);
			}
		});

		supplier = new JButton("공급자");
		panel.add(supplier);
		supplier.addActionListener(new ActionListener() {
			// 공급자 창으로 가는 버튼
			@Override
			public void actionPerformed(ActionEvent e) {
				Supplier frame = new Supplier();
				frame.setVisible(true);
			}
		});

		order = new JButton("주문 정보");
		panel.add(order);

		order.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				OrderPayment frame = new OrderPayment();
				frame.setVisible(true);
			}
		});

		payment = new JButton("대금 정보");
		panel.add(payment);

		payment.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Payment frame = new Payment("a");
				frame.setVisible(true);
			}
		});

		content = connect.setBookContent();

		model = new DefaultTableModel(content, list);
		table = new JTable(model);
		scroll = new JScrollPane(table);
		scroll.setBounds(5, 34, 417, 189);
		contentPane.add(scroll);

		panel_1 = new JPanel();
		panel_1.setBounds(5, 5, 424, 33);
		contentPane.add(panel_1);

		choice = new Choice();
		choice.add("교재이름");
		choice.add("출판사");
		panel_1.setLayout(null);
		choice.setBounds(56, 6, 77, 17);
		panel_1.add(choice);

		textField = new JTextField();
		textField.setBounds(139, 6, 116, 21);
		panel_1.add(textField);
		textField.setColumns(10);

		serch = new JButton("검색");
		serch.setBounds(260, 5, 57, 23);

		panel_1.add(serch);

		table.addMouseListener(new table_mouseListener());

		serch.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String sel = e.getActionCommand();

		if (sel.equals("검색")) {
			String input = String.valueOf(textField.getText());
			if (choice.getSelectedItem().equals("교재이름")) {
				content = connect.serchBookContent(input, 1);
				model = new DefaultTableModel(content, list);
				table.setModel(model);
			} else if (choice.getSelectedItem().equals("출판사")) {

				content = connect.serchBookContent(input, 2);
				model = new DefaultTableModel(content, list);
				table.setModel(model);
			}
		}
	}

	class table_mouseListener extends MouseAdapter {

		public void mouseReleased(MouseEvent e) {
			int row = table.getSelectedColumn();
			int col = table.getSelectedRow();
			String value = (String) model.getValueAt(col, 0);
			// 선택한 줄의 no값.
			if (e.getClickCount() == 2 && table.getSelectedColumn() == 4) {
				System.out.println("구입");
				Book_order frame = new Book_order((String) model.getValueAt(col, 0), (String) model.getValueAt(col, 1));
				frame.setVisible(true);
			} else if (e.getClickCount() == 2 && table.getSelectedColumn() == 5) {

				String book_name = (String) model.getValueAt(col, 1);
				String pub_name = (String) model.getValueAt(col, 2);
				String book_price = (String) model.getValueAt(col, 3);
				String book_no = (String) model.getValueAt(col, 0);
				String supplier_name=(String) model.getValueAt(col, 4);
				new Bookupdate(book_name, pub_name, book_price, book_no,supplier_name).setVisible(true);
				content = connect.setBookContent();
				model = new DefaultTableModel(content, list);
				table.setModel(model);

			} else if (e.getClickCount() == 2 && table.getSelectedColumn() == 6) {

				String book_no = (String) model.getValueAt(col, 0);
				connect.deleteBookTableCell(book_no);
				content = connect.setBookContent();
				model = new DefaultTableModel(content, list);
				table.setModel(model);
				
			}
		}
	}

	@Override
	public void windowGainedFocus(WindowEvent e) {
		// TODO Auto-generated method stub
		content = connect.setBookContent();
		model = new DefaultTableModel(content, list);
		table.setModel(model);
		
	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
