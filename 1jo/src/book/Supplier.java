package book;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Supplier extends JFrame implements ActionListener,WindowFocusListener{
	// 공급자 보기.
	JPanel contentPane;
	JTable table;
	private JTextField textField;
	 DB_book_connect connect = new DB_book_connect();
	DefaultTableModel model;
	String[][] content;
	JButton serch;

	final String[] list = { "공급자 번호", "상호", "거래 교재", "수정" };

	public Supplier() {
		setResizable(false);
		setTitle("공급자 정보");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		System.out.println("공급자정보");
		this.addWindowFocusListener(this);
		
		// 바텀 시
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);

		JButton supplier_insert = new JButton("공급자 입력");
		panel.add(supplier_insert);
		
		supplier_insert.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 작
				SupplierInsert frame = new SupplierInsert();
				frame.setVisible(true);
				content=connect.setSuppliercontent();
				model = new DefaultTableModel(content, list);
				table.setModel(model);
			}
		});

		content = connect.setSuppliercontent();
		// 중앙 시작
		model = new DefaultTableModel(content, list);
		table = new JTable(model);
		JScrollPane scroll = new JScrollPane(table);
		contentPane.add(scroll, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);

		textField = new JTextField();
		panel_1.add(textField);
		textField.setColumns(10);

		serch = new JButton("검색");

		panel_1.add(serch);
		table.addMouseListener(new SupplierTable_mouseListener());
		serch.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("검색")) {
			String input = String.valueOf(textField.getText());
			content = connect.serchSupplier(input);
			model = new DefaultTableModel(content, list);
			table.setModel(model);

		}

	}

	class SupplierTable_mouseListener extends MouseAdapter {
		public void mouseReleased(MouseEvent e) {
			int row = table.getSelectedColumn();
			int col = table.getSelectedRow();
			String value = (String) model.getValueAt(col, 0);
			if (e.getClickCount() == 2 && table.getSelectedColumn() == 3) {				
				String supplier_name = (String) model.getValueAt(col, 1);
				String supplier_no = (String) model.getValueAt(col, 0);
				connect.updateSupplierTable(supplier_name, supplier_no);
				content=connect.setSuppliercontent();
				model = new DefaultTableModel(content, list);
				table.setModel(model);

			} else if (e.getClickCount() == 2 && table.getSelectedColumn() == 2) {
				System.out.println("거래정보");
				Payment frame = new Payment(value);
				frame.setVisible(true);
			}
		}
	}

	@Override
	public void windowGainedFocus(WindowEvent e) {
		content=connect.setSuppliercontent();
		model = new DefaultTableModel(content, list);
		table.setModel(model);
		
	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
