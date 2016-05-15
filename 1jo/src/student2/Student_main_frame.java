package student2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class Student_main_frame extends JFrame {
	JTextField tf_name, tf_jumin1, tf_addr;
	JLabel lab_name, lab_jumin1, lab_jumin2, lab_addr;
	JButton btn_ins, btn_del, btn_srch, btn_mod, btn_regist, btn_reg_admin;
	JTable tab_student;
	String[] title = { "�й�", "�̸�", "�������", "�����", "�ּ�" };
	JScrollPane scr_tab_student;
	JPanel pane_north, pane_insert, pane_option;
	DefaultTableModel model;
	JSeparator sep_1, sep_2;
	JPasswordField tf_jumin2;

	public Student_main_frame() {
		super("������ ����");

		model = new DefaultTableModel(title, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tab_student = new JTable(model);
		scr_tab_student = new JScrollPane(tab_student);

		pane_north = new JPanel(new BorderLayout());
		pane_insert = new JPanel();
		pane_option = new JPanel();

		lab_name = new JLabel("�̸� : ");
		tf_name = new JTextField(5);
		lab_jumin1 = new JLabel("�ֹε�Ϲ�ȣ : ");
		tf_jumin1 = new JTextField(6);
		lab_jumin2 = new JLabel(" - ");
		tf_jumin2 = new JPasswordField(7);
		lab_addr = new JLabel("�ּ� : ");
		tf_addr = new JTextField(30);

		btn_ins = new JButton("�Է�");

		sep_1 = new JSeparator(SwingConstants.VERTICAL);
		sep_2 = new JSeparator(SwingConstants.VERTICAL);

		btn_del = new JButton("����");
		btn_srch = new JButton("�˻�");
		btn_mod = new JButton("����");
		btn_regist = new JButton("���� ��û");
		btn_reg_admin = new JButton("���� ��� ���� / ����");

		// ���� �Է� �Ұ�
		tf_name.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (Character.isDigit(e.getKeyChar())) {
					e.consume();
				}
			}
		});
		tf_name.setDocument(new JTextFieldLimit(5));

		// �ֹι�ȣ ���� �Է� �Ұ�
		tf_jumin1.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (!Character.isDigit(e.getKeyChar())) {
					e.consume();
				}
			}
		});
		tf_jumin1.setDocument(new JTextFieldLimit(6));

		tf_jumin2.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (!Character.isDigit(e.getKeyChar())) {
					e.consume();
				}
			}
		});
		tf_jumin2.setDocument(new JTextFieldLimit(7));

		pane_insert.add(lab_name);
		pane_insert.add(tf_name);

		pane_insert.add(lab_jumin1);
		pane_insert.add(tf_jumin1);

		pane_insert.add(lab_jumin2);
		pane_insert.add(tf_jumin2);

		pane_insert.add(lab_addr);
		pane_insert.add(tf_addr);

		pane_insert.add(btn_ins);
		// pane_north.add(sep_1);
		pane_option.add(btn_srch);
		pane_option.add(btn_del);
		pane_option.add(btn_mod);
		pane_option.add(sep_2);
		pane_option.add(btn_regist);
		pane_option.add(btn_reg_admin);

		btn_ins.addActionListener(
				new Student_insert_ActionListener(tf_name, tf_jumin1, tf_jumin2, tf_addr, model, tab_student));
		btn_del.addActionListener(new Student_delete_ActionListener(model, tab_student));
		btn_srch.addActionListener(new Student_search_ActionListener());
		btn_mod.addActionListener(new Student_modify_ActionListener(tab_student));
		btn_regist.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tab_student.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "�������� �������ֽʽÿ�.");
				} else {
					new Register_dialog(tab_student);
				}
			}
		});
		
		btn_reg_admin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tab_student.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "�������� �������ֽʽÿ�.");
				} else {
					new Regist_admin_dialog(tab_student);
				}
			}
		});

		tab_student.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					new Regist_admin_dialog(tab_student);
				}
			}
		});

		// ���̺� �÷� ��ġ ���� �Ұ�
		tab_student.getTableHeader().setReorderingAllowed(false);
		for (int cnt = 0; cnt < tab_student.getColumnCount(); cnt++) {
			tab_student.getColumnModel().getColumn(cnt).setResizable(false);
		}

		pane_north.add("North", pane_insert);
		pane_north.add("South", pane_option);

		add("North", pane_north);
		add("Center", scr_tab_student);

		// ���̺� �ʱ�ȭ
		addWindowFocusListener(new WindowAdapter() {
			@Override
			public void windowGainedFocus(WindowEvent e) {
				refresh_tab();
			}

			@Override
			public void windowOpened(WindowEvent e) {
				refresh_tab();
			}

			@Override
			public void windowActivated(WindowEvent e) {
				refresh_tab();
			}
		});

		pack();
		setMinimumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 2,
				Toolkit.getDefaultToolkit().getScreenSize().height / 2));
		setVisible(true);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	// ���̺� ���� ��ħ
	void refresh_tab() {
		model.setRowCount(0);
		new Student_admin().display(tab_student);
	}

	// �ؽ�Ʈ �ʵ� ����
	void empty_fields() {
		tf_name.setText(null);
		tf_jumin1.setText(null);
		tf_jumin2.setText(null);
		tf_addr.setText(null);
	}

	public static void main(String[] args) {
		new Student_main_frame();
	}
}
