package book;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Choice;
import javax.swing.JRadioButton;

public class OrderPayment extends JFrame implements ActionListener, WindowFocusListener, ItemListener {
	// 주문 정보
	JPanel contentPane;
	JTable table;
	DefaultTableModel model;
	private JTextField textField;
	DB_book_connect connect = new DB_book_connect();
	Choice choice;
	final String[] list = { "주문번호", "교재 이름", "공급자 이름", "주문수량", "주문날자", "대금 정보", "대금하기" };
	String content[][];
	private JRadioButton paidradio_bnt2, paidradio_bnt1;
	private JRadioButton paidradio_bnt;
	private ButtonGroup group;
	JPanel panel_1;

	JScrollPane scroll;

	/**
	 * Launch the application.
	 */

	public OrderPayment() {

		setTitle("주문 정보");
		setResizable(false);
		setBounds(100, 100, 480, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		content = connect.setorderPaymentTable();
		// 중앙 시작
		model = new DefaultTableModel(content, list);
		table = new JTable();
		table.setModel(model);
		scroll = new JScrollPane(table);
		scroll.setBounds(5, 66, 447, 186);
		contentPane.add(scroll);
		scroll.setEnabled(false);
		panel_1 = new JPanel();
		panel_1.setBounds(5, 5, 447, 51);
		contentPane.add(panel_1);

		table.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				int row = table.getSelectedColumn();
				int col = table.getSelectedRow();

				String value = (String) model.getValueAt(col, 0);
				if (e.getClickCount() == 2 && table.getSelectedColumn() == 6
						&& model.getValueAt(col, 6).equals("대금하기")) {
					System.out.println("대금하기");
					PayInsert frame = new PayInsert(value);
					frame.setVisible(true);

				}
			}
		});

		panel_1.setLayout(null);

		choice = new Choice();
		choice.add("교재이름");
		choice.add("공급자");
		choice.setBounds(10, 6, 84, 16);
		panel_1.add(choice);
		textField = new JTextField();
		textField.setBounds(100, 6, 116, 21);
		panel_1.add(textField);
		textField.setColumns(10);

		JButton serch = new JButton("검색");
		serch.setBounds(226, 6, 94, 21);

		panel_1.add(serch);

		group = new ButtonGroup();

		paidradio_bnt1 = new JRadioButton("미납 대금 보기");
		paidradio_bnt1.setBounds(162, 33, 121, 23);
		group.add(paidradio_bnt1);

		paidradio_bnt2 = new JRadioButton("완납 목록 보기");
		paidradio_bnt2.setBounds(299, 33, 121, 23);
		group.add(paidradio_bnt2);

		paidradio_bnt = new JRadioButton("전체 보기");
		paidradio_bnt.setSelected(true);
		paidradio_bnt.setBounds(37, 33, 121, 23);
		group.add(paidradio_bnt);

		panel_1.add(paidradio_bnt1);
		panel_1.add(paidradio_bnt2);
		panel_1.add(paidradio_bnt);

		serch.addActionListener(this);
		paidradio_bnt.addItemListener(this);
		paidradio_bnt2.addItemListener(this);
		paidradio_bnt1.addItemListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("검색")) {
			String input = String.valueOf(textField.getText());
			System.out.println(input);
			if (choice.getSelectedItem().equals("교재이름")) {
				if (paidradio_bnt2.isSelected()) {
					content = connect.serchorderPaymentTable(input, 1, "Y");
				} else if (paidradio_bnt.isSelected()) {
					content = connect.serchorderPaymentTable(input, 1, "A");
				} else if (paidradio_bnt1.isSelected()) {
					content = connect.serchorderPaymentTable(input, 1, "N");
				}
			} else if (choice.getSelectedItem().equals("공급자")) {

				if (paidradio_bnt2.isSelected()) {
					content = connect.serchorderPaymentTable(input, 2, "Y");
				} else if (paidradio_bnt.isSelected()) {
					content = connect.serchorderPaymentTable(input, 2, "A");
				} else if (paidradio_bnt1.isSelected()) {
					content = connect.serchorderPaymentTable(input, 2, "N");
				}

			}
			model = new DefaultTableModel(content, list);
			table.setModel(model);
		}

	}

	@Override
	public void windowGainedFocus(WindowEvent arg0) {
		content = connect.setorderPaymentTable();
		model = new DefaultTableModel(content, list);
		table.setModel(model);
	}

	@Override
	public void windowLostFocus(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		AbstractButton sel = (AbstractButton) e.getItemSelectable();
		if (e.getStateChange() == ItemEvent.SELECTED) {
			if (sel.getText().equals("전체 보기")) {
				content = connect.setorderPaymentTable();
				model = new DefaultTableModel(content, list);
				table.setModel(model);
			} else if (sel.getText().equals("미납 대금 보기")) {
				System.out.println("미납");
				content = connect.notorderPaymentTable();
				model = new DefaultTableModel(content, list);
				table.setModel(model);
			} else {
				content = connect.payorderPaymentTable();
				model = new DefaultTableModel(content, list);
				table.setModel(model);
			}
		}
	}
}
