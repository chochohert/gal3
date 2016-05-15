package staff;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.ScrollPaneConstants;


public class staffall extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;
	private JTextField tname;
	private JTextField tju1, tjumin2;
	private JPasswordField tju2;
	private JTextField taddr;
	private JTextField tsal;
	DefaultTableModel model;

	private Connection conn = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	String[] list = { "번호", "이름", "주민번호", "주소", "월급", "담당 강의실" };
	String staff_no = "";
	String staff_name = "";
	String staff_jumin = "";
	String staff_addr = "";
	String staff_sal = "";
	private DB_staff_connect connect = new DB_staff_connect();
	private String[][] content;
	private JTextField serch;

	public staffall() {

		setBounds(100, 100, 1100, 380);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setTitle("직원 관리");
		contentPane.setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(5, 314, 1034, 33);
		contentPane.add(panel);
		setResizable(false);
		serch = new JTextField();
		panel.add(serch);
		serch.setColumns(10);

		JButton btncho = new JButton("검색");
		panel.add(btncho);

		JButton btnin = new JButton("추가");
		panel.add(btnin);

		JButton btnup = new JButton("수정");
		panel.add(btnup);

		JButton btnde = new JButton("삭제");
		panel.add(btnde);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(5, 5, 295, 309);
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("이름");
		lblNewLabel_1.setBounds(12, 30, 60, 18);
		panel_3.add(lblNewLabel_1);

		tname = new JTextField();
		tname.setBounds(72, 27, 116, 21);
		panel_3.add(tname);
		tname.setColumns(10);

		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4);
		panel_4.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("주민번호");
		lblNewLabel_2.setBounds(12, 30, 99, 18);
		panel_4.add(lblNewLabel_2);

		tju1 = new JTextField();
		tju1.setBounds(72, 27, 72, 21);
		panel_4.add(tju1);
		tju1.setColumns(6);

		JLabel label = new JLabel("-");
		label.setBounds(156, 30, 6, 15);
		panel_4.add(label);

		tju2 = new JPasswordField();
		tju2.setBounds(182, 27, 83, 21);
		panel_4.add(tju2);
		tju2.setDocument(new JTextFieldLimit(7));

		JPanel panel_5 = new JPanel();
		panel_1.add(panel_5);
		panel_5.setLayout(null);

		JLabel lblNewLabel_3 = new JLabel("주소");
		lblNewLabel_3.setBounds(12, 33, 81, 18);
		panel_5.add(lblNewLabel_3);

		taddr = new JTextField();
		taddr.setBounds(72, 30, 210, 21);
		panel_5.add(taddr);
		taddr.setColumns(20);

		JPanel panel_6 = new JPanel();
		panel_1.add(panel_6);
		panel_6.setLayout(null);

		JLabel lblNewLabel_4 = new JLabel("월급");
		lblNewLabel_4.setBounds(12, 32, 78, 15);
		panel_6.add(lblNewLabel_4);

		tsal = new JTextField();
		tsal.setBounds(72, 29, 116, 21);
		panel_6.add(tsal);
		tsal.setColumns(10);

		JPanel panel_7 = new JPanel();
		panel_7.setBounds(0, 0, 0, 0);
		contentPane.add(panel_7);

		content = connect.setstaffTable();

		// 테이블
		model = new DefaultTableModel(content, list);
		table = new JTable();
		table.setModel(model);
		panel_7.add(table);

		JScrollPane scroll = new JScrollPane(table);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(312, 5, 739, 309);
		scroll.setEnabled(false);
		table.setPreferredScrollableViewportSize(new Dimension(800, 500));
		contentPane.add(scroll);

		table.getColumn("주민번호").setPreferredWidth(110);
		table.getColumn("주소").setPreferredWidth(200);

		table.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				int row = table.getSelectedColumn();
				int col = table.getSelectedRow();
				
				

				if (e.getClickCount() == 1) {
					staff_no = (String) model.getValueAt(col, 0);
					staff_name = (String) model.getValueAt(col, 1);
					staff_jumin = (String) model.getValueAt(col, 2);
					staff_addr = (String) model.getValueAt(col, 3);
					staff_sal = (String) model.getValueAt(col, 4);
				}
			}
		});

		// 추가
		btnin.addActionListener(this);
		// 삭제
		btnde.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (table.getSelectedColumn() == -1) {
					JOptionPane.showMessageDialog(null, "직원를 선택해주십시오.");
				} else {
					connect.deletestaff(staff_no);
					content = connect.setstaffTable();
					model = new DefaultTableModel(content, list);
					table.setModel(model);
					JOptionPane.showMessageDialog(null, "강의실을 다시 배정해주세요.");
				}
			}
		});
		// 수정
		btnup.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (table.getSelectedColumn() == -1) {
					JOptionPane.showMessageDialog(null, "직원를 선택해주십시오.");
				} else {

					staffup up = new staffup(staff_no, staff_name, staff_jumin, staff_addr, staff_sal);

					up.setVisible(true);

				}
			}
		});
		// 검색
		btncho.addActionListener(this);

		tju1.addKeyListener(new keyListener());
		tju2.addKeyListener(new keyListener());
		tju1.setDocument(new inputNumberOnly());
		tju2.setDocument(new inputNumberOnly());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("추가")) {
			tjumin2=new JTextField(7);
			tjumin2=tju2;
			if (new Jumin1_integrity(Integer.parseInt(tju1.getText())).check() == false) {
				JOptionPane.showMessageDialog(null, "주민등록번호 앞자리를 확인하세요.");
				return;
			}
			if (new jumin2_integrity(Integer.parseInt(tjumin2.getText())).check() == false) {
				JOptionPane.showMessageDialog(null, "주민등록번호 뒷자리를 확인하세요.");
				return;
			}

			if (isfullField()) {
				String regular = null;
				String name = tname.getText();
				
				int jumin1 = Integer.parseInt(tju1.getText());
				int jumin2 = Integer.parseInt(tjumin2.getText());
				String addr = taddr.getText();
				int sal = Integer.parseInt(tsal.getText());

				if (tju1.getText().length() != 6 && tjumin2.getText().length() != 7) {
					JOptionPane.showMessageDialog(null, "주민번호를 확인해 주세요.");
				} else {
					connect.updatestaff(name, jumin1, jumin2, addr, sal);
					content = connect.setstaffTable();
					model = new DefaultTableModel(content, list);
					table.setModel(model);
					tju1.setText("");
					tju2.setText("");
					tname.setText("");
					tsal.setText("");
					taddr.setText("");
				}
			} else {
				JOptionPane.showMessageDialog(null, "추가하려면 입력해주세요.");
			}

		} else if (e.getActionCommand().equals("검색")) {
			String input = serch.getText();
			content = connect.searchstaffTable(input);
			model = new DefaultTableModel(content, list);
			table.setModel(model);
		}
	}

	boolean isfullField() {

		if (tju1.getText().equals("") || tjumin2.getText().equals("") || tname.getText().equals("")
				|| tsal.getText().equals("") || taddr.getText().equals("")) {
			return false;
		}

		return true;

	}

	class keyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (tju1.getText().length() >=6) {
				tju1.setText(tju1.getText().substring(0, 5));
			} else if (e.getComponent().equals(tju2)) {
				tjumin2=tju2;
				if (tjumin2.getText().length() >= 7) {
					tju2.setText(tjumin2.getText().substring(0, 6));
				}
			}
		}
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

class inputNumberOnly extends PlainDocument {

	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null)
			return;
		if (str.charAt(0) >= '0' && str.charAt(0) <= '9')
			super.insertString(offset, str, attr);
	}

}
