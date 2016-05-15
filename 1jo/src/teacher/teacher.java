package teacher;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import javax.swing.ScrollPaneConstants;

public class teacher extends JFrame implements ActionListener {

	private JTextField name_field;
	private JTextField ju1, jumin2;
	private JTextField addr_field;
	private JTextField li_no;
	private JPasswordField ju2;
	private JTextField li_name_field;
	private JTextField pay_field;
	private JPanel contentPane, tablePanle;
	private JTable table;
	DefaultTableModel model;

	private Connection conn = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	String[] list = { "��ȣ", "�̸�", "�ֹι�ȣ", "�ּ�", "���ӿ���", "�ڰ�����ȣ", "�ڰ����̸�", "����","���� ����" };
	String teacher_no = "";
	String teacher_name = "";
	String teacher_jumin = "";
	String teacher_addr = "";
	String regular = "";
	String license_no = "";
	String license_name = "";
	String teacher_pay = "";
	private JTextField serch;
	private DB_teacher_connect connect = new DB_teacher_connect();
	private String[][] content;
	JRadioButton rdbtnY, rdbtnN;

	public teacher() {
		setResizable(false);
		setBounds(100, 100, 450, 300);
		tablePanle = new JPanel();
		tablePanle.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(tablePanle);

		setTitle("���� ����");

		setBounds(100, 100, 1080, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		tablePanle.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(5, 329, 1060, 33);
		getContentPane().add(panel_1);

		serch = new JTextField();
		panel_1.add(serch);
		serch.setColumns(10);

		JButton btncho = new JButton("�˻�");
		panel_1.add(btncho);

		JButton btnin = new JButton("�߰�");
		panel_1.add(btnin);

		JButton btnup = new JButton("����");
		panel_1.add(btnup);

		JButton btndel = new JButton("����");
		panel_1.add(btndel);

		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 278, 323);
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panel_3 = new JPanel();
		panel.add(panel_3);

		JLabel lblNewLabel_1 = new JLabel("�̸�");
		panel_3.add(lblNewLabel_1);

		name_field = new JTextField();
		panel_3.add(name_field);
		name_field.setColumns(10);

		JPanel panel_4 = new JPanel();
		panel.add(panel_4);

		JLabel lblNewLabel_2 = new JLabel("�ֹι�ȣ");
		panel_4.add(lblNewLabel_2);

		ju1 = new JTextField();
		panel_4.add(ju1);
		ju1.setColumns(6);

		JLabel lblNewLabel_5 = new JLabel("-");
		panel_4.add(lblNewLabel_5);

		ju2 = new JPasswordField();
		panel_4.add(ju2);
		ju2.setColumns(7);

		JPanel panel_5 = new JPanel();
		panel.add(panel_5);

		JLabel lblNewLabel_3 = new JLabel("�ּ�");
		panel_5.add(lblNewLabel_3);

		addr_field = new JTextField();
		panel_5.add(addr_field);
		addr_field.setColumns(10);

		JPanel panel_6 = new JPanel();
		panel.add(panel_6);

		JLabel lblNewLabel_4 = new JLabel("�ڰ�����ȣ");
		panel_6.add(lblNewLabel_4);

		li_no = new JTextField();
		panel_6.add(li_no);
		li_no.setColumns(10);

		JPanel panel_8 = new JPanel();
		panel.add(panel_8);

		JLabel lblNewLabel_6 = new JLabel("�ڰ����̸�");
		panel_8.add(lblNewLabel_6);

		li_name_field = new JTextField();
		panel_8.add(li_name_field);
		li_name_field.setColumns(10);

		JPanel panel_9 = new JPanel();
		panel.add(panel_9);

		JLabel lblNewLabel_7 = new JLabel("���ӿ���");
		panel_9.add(lblNewLabel_7);

		ButtonGroup group = new ButtonGroup();
		rdbtnY = new JRadioButton("Y");

		rdbtnN = new JRadioButton("N");
		group.add(rdbtnY);
		group.add(rdbtnN);

		panel_9.add(rdbtnY);
		panel_9.add(rdbtnN);

		JPanel panel_10 = new JPanel();
		panel.add(panel_10);

		JLabel label = new JLabel("�ñ�");
		panel_10.add(label);

		pay_field = new JTextField();
		panel_10.add(pay_field);
		pay_field.setColumns(10);

		JPanel panel_7 = new JPanel();
		panel_7.setBounds(0, 0, 0, 0);
		tablePanle.add(panel_7);

		content = connect.setTeacherTable();
		// ���̺�
		model = new DefaultTableModel(content, list);

		table = new JTable();
		table.setBorder(new LineBorder(SystemColor.activeCaptionBorder));
		table.setModel(model);
		panel_7.add(table);

		JScrollPane scroll = new JScrollPane(table);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(287, 5, 778, 323);
		scroll.setEnabled(false);
		table.setPreferredScrollableViewportSize(new Dimension(800, 500));
		tablePanle.add(scroll);

		table.getColumn("�ֹι�ȣ").setPreferredWidth(110);
		table.getColumn("�ּ�").setPreferredWidth(200);

		table.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {

				int row = table.getSelectedColumn();
				int col = table.getSelectedRow();
				Object value=model.getValueAt(col, 8);

				if (e.getClickCount() == 1) {
					teacher_no = (String) model.getValueAt(col, 0);
					teacher_name = (String) model.getValueAt(col, 1);
					teacher_jumin = (String) model.getValueAt(col, 2);
					teacher_addr = (String) model.getValueAt(col, 3);
					regular = (String) model.getValueAt(col, 4);
					license_no = (String) model.getValueAt(col, 5);
					license_name = (String) model.getValueAt(col, 6);
					teacher_pay = (String) model.getValueAt(col, 7);
				}else if(value.equals("���� ����")){
					new showClassbyteacher(Integer.valueOf(teacher_no)).setVisible(true);
				}
			}
		});

		// �߰�
		btnin.addActionListener(this);

		// ����
		btnup.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (table.getSelectedColumn() == -1) {
					JOptionPane.showMessageDialog(null, "���縦 �������ֽʽÿ�.");
				} else {
					teacherup up = new teacherup(teacher_no, teacher_name, teacher_jumin, teacher_addr, regular,
							license_no, license_name, teacher_pay);
					up.setVisible(true);
					dispose();
				}
			}
		});
		// ����
		btndel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedColumn() == -1) {
					JOptionPane.showMessageDialog(null, "���縦 �������ֽʽÿ�.");
				} else {
					connect.deleteTeacher(teacher_no);
					content = connect.setTeacherTable();
					model = new DefaultTableModel(content, list);
					table.setModel(model);
					JOptionPane.showMessageDialog(null, "���ǵ� �Բ� �����Ǿ����ϴ�.");
				}

			}
		});
		// ���ư���
		// btncen.addActionListener(this);
		// �˻�
		btncho.addActionListener(this);
		ju1.addKeyListener(new keyListener());
		ju2.addKeyListener(new keyListener());
		ju1.setDocument(new inputNumberOnly());
		ju2.setDocument(new inputNumberOnly());
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("�߰�")) {
			jumin2=new JTextField(7);
			jumin2 = ju2;
			if (new Jumin1_integrity(Integer.parseInt(ju1.getText())).check() == false) {
				JOptionPane.showMessageDialog(null, "�ֹε�Ϲ�ȣ ���ڸ��� Ȯ���ϼ���.");
				return;
			}
			if (new jumin2_integrity(Integer.parseInt(jumin2.getText())).check() == false) {
				JOptionPane.showMessageDialog(null, "�ֹε�Ϲ�ȣ ���ڸ��� Ȯ���ϼ���.");
				return;
			}
			if (isfullField()) {
				String regular = null;
				String name = name_field.getText();
				int jumin1 = Integer.parseInt(ju1.getText());
				
				String addr = addr_field.getText();

				if (rdbtnN.isSelected()) {
					regular = "N";
				} else if (rdbtnY.isSelected()) {
					regular = "Y";
				}

				int licence_no = Integer.parseInt(li_no.getText());
				String licence_name = li_name_field.getText();
				int pay = Integer.parseInt(pay_field.getText());

				if (ju1.getText().length() != 6 && jumin2.getText().length() != 7) {
					JOptionPane.showMessageDialog(null, "�ֹι�ȣ�� Ȯ���� �ּ���.");
				} else {
					connect.updateTeacher(name, jumin1, addr, regular, licence_no, licence_name, pay);
					content = connect.setTeacherTable();
					model = new DefaultTableModel(content, list);
					table.setModel(model);
					ju1.setText("");
					ju2.setText("");
					name_field.setText("");
					pay_field.setText("");
					addr_field.setText("");
					li_no.setText("");
					li_name_field.setText("");

				}

			} else {
				JOptionPane.showMessageDialog(null, "�߰��Ϸ��� �Է����ּ���.");
			}
		} else if (e.getActionCommand().equals("�˻�")) {
			String input = serch.getText();
			content = connect.searchTeacherTable(input);
			model = new DefaultTableModel(content, list);
			table.setModel(model);
		}
	}

	boolean isfullField() {
		jumin2=new JTextField(7);
		jumin2 = ju2;
		if (ju1.getText().equals("") || jumin2.getText().equals("") || name_field.getText().equals("")
				|| pay_field.getText().equals("") || addr_field.getText().equals("") || li_no.getText().equals("")
				|| li_name_field.getText().equals("")) {
			return false;
		} else {

			return true;
		}
	}

	class keyListener extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			jumin2=new JTextField(7);
			jumin2 = ju2;
			if (ju1.getText().length() >= 6) {
				ju1.setText(ju1.getText().substring(0, 5));
			} else if (e.getComponent().equals(ju2)) {
				if (jumin2.getText().length() >= 7) {
					ju2.setText(jumin2.getText().substring(0, 6));
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

	// �ֹι�ȣ ���ڸ� üũ Ŭ����
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
