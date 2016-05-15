package main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import book.*;
import classroom.*;
import student2.*;
import staff.*;
import teacher.*;
import lecture.*;

public class main_page extends JFrame implements ActionListener, WindowFocusListener {
	private JPanel academy_info, content_book, content_register;
	private JScrollPane book_table_scroll, register_table_scroll;
	private DB_main_connect connect;
	private DefaultTableModel book_model, register_model;
	private JTable register_table, book_table;
	private JLabel lecture_sum, student_sum, time;
	private JTextField student_sum_field, lecture_sum_field, time_field;
	private JMenuBar main_page_menu;
	private JMenu mn_Menu, exit_menu;
	private JMenuItem staff_bnt, teacher_bnt, lecture_bnt, book_bnt, student_bnt, classroom_bnt, exit_bnt;
	private Object book_content[][], regiser_content[][];
	final String book_header[] = { "교재 번호", "교재 이름", "교재 갯수", "주문 날짜", "공급자 이름", "대금지급" };
	final String register_header[] = { "강의실", "강의 이름", "강사", "강의비", "시간", "마감일", "등록" };
	private JTextField staff_sum_Field;
	private JTextField teacher_sum_Field;

	public main_page() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(690, 450);
		setTitle("학원 관리 ver.1.1.0");
		getContentPane().setLayout(null);
		setResizable(false);
		connect = new DB_main_connect();
		setResizable(false);
		content_book = new JPanel();
		content_book.setLocation(0, 110);
		content_book.setBorder(
				new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\uAC04\uD3B8 \uB300\uAE08 \uC9C0\uAE09 ",
						TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		content_book.setBackground(SystemColor.menu);
		getContentPane().add(content_book);

		content_book.setSize(672, 134);
		content_book.setLayout(null);
		book_content = connect.getContentBooktable();

		book_table = new JTable();
		book_table.setCellSelectionEnabled(true);
		book_table.setBackground(Color.WHITE);
		book_model = new DefaultTableModel(book_content, book_header) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int arg0, int arg1) {
				return false;
			}
		};

		book_table.setModel(book_model);
		book_table.getTableHeader().setReorderingAllowed(false);
		book_table_scroll = new JScrollPane(book_table);
		book_table_scroll.setSize(648, 99);
		book_table_scroll.setLocation(12, 25);

		content_book.add(book_table_scroll);

		content_register = new JPanel();
		content_register.setForeground(SystemColor.menu);
		content_register.setLocation(0, 257);
		content_register.setSize(672, 155);
		content_register.setBorder(new TitledBorder(null, "\uAC04\uD3B8 \uC218\uAC15 \uB4F1\uB85D",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		content_register.setBackground(SystemColor.menu);

		getContentPane().add(content_register);
		content_register.setLayout(null);

		register_table = new JTable();
		register_table.setBackground(Color.WHITE);

		register_table_scroll = new JScrollPane(register_table);
		register_table_scroll.setSize(648, 120);
		register_table_scroll.setLocation(12, 25);
		content_register.add(register_table_scroll);

		regiser_content = connect.getContentRegistertable();

		register_model = new DefaultTableModel(regiser_content, register_header) {
			@Override
			public boolean isCellEditable(int arg0, int arg1) {
				return false;
			}
		};

		register_table.setModel(register_model);

		academy_info = new JPanel();
		academy_info.setBorder(new LineBorder(Color.LIGHT_GRAY));
		academy_info.setBounds(10, 31, 662, 69);
		getContentPane().add(academy_info);
		academy_info.setLayout(null);

		time = new JLabel("현재 시간");
		time.setBounds(12, 10, 69, 20);
		academy_info.add(time);

		time_field = new JTextField();
		time_field.setEditable(false);
		time_field.setBounds(78, 10, 163, 20);
		time_field.setBackground(Color.WHITE);
		academy_info.add(time_field);

		student_sum_field = new JTextField();
		student_sum_field.setBackground(Color.WHITE);
		student_sum_field.setEditable(false);
		student_sum_field.setBounds(337, 10, 63, 20);
		academy_info.add(student_sum_field);
		student_sum_field.setColumns(10);

		student_sum = new JLabel("총 수강생 인원");
		student_sum.setBounds(253, 12, 105, 17);
		academy_info.add(student_sum);

		lecture_sum = new JLabel("총 개설 과정");
		lecture_sum.setBounds(412, 11, 78, 18);
		academy_info.add(lecture_sum);

		lecture_sum_field = new JTextField();
		lecture_sum_field.setBackground(Color.WHITE);
		lecture_sum_field.setEditable(false);
		lecture_sum_field.setBounds(502, 10, 124, 20);
		academy_info.add(lecture_sum_field);
		lecture_sum_field.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("직원 수");
		lblNewLabel.setBounds(253, 39, 57, 15);
		academy_info.add(lblNewLabel);
		
		staff_sum_Field = new JTextField();
		staff_sum_Field.setEditable(false);
		staff_sum_Field.setBounds(337, 37, 63, 22);
		academy_info.add(staff_sum_Field);
		staff_sum_Field.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("강사 수");
		lblNewLabel_1.setBounds(422, 44, 57, 15);
		academy_info.add(lblNewLabel_1);
		
		teacher_sum_Field = new JTextField();
		teacher_sum_Field.setEditable(false);
		teacher_sum_Field.setBounds(502, 36, 124, 23);
		academy_info.add(teacher_sum_Field);
		teacher_sum_Field.setColumns(10);

		main_page_menu = new JMenuBar();
		main_page_menu.setBounds(0, 0, 684, 21);
		getContentPane().add(main_page_menu);

		mn_Menu = new JMenu("메뉴");
		main_page_menu.add(mn_Menu);

		exit_menu = new JMenu("나가기");
		main_page_menu.add(exit_menu);

		teacher_bnt = new JMenuItem("강사 관리");
		mn_Menu.add(teacher_bnt);

		student_bnt = new JMenuItem("수강생 관리");
		mn_Menu.add(student_bnt);

		lecture_bnt = new JMenuItem("과정 관리");
		mn_Menu.add(lecture_bnt);

		book_bnt = new JMenuItem("교재 관리");
		mn_Menu.add(book_bnt);

		classroom_bnt = new JMenuItem("강의실 관리");
		mn_Menu.add(classroom_bnt);

		staff_bnt = new JMenuItem("직원 관리");
		mn_Menu.add(staff_bnt);

		exit_bnt = new JMenuItem("나가기");
		exit_menu.add(exit_bnt);

		book_table.addMouseListener(new table_listener());
		register_table.addMouseListener(new table_listener());
		exit_bnt.addActionListener(this);
		book_bnt.addActionListener(this);
		classroom_bnt.addActionListener(this);
		student_bnt.addActionListener(this);
		teacher_bnt.addActionListener(this);
		staff_bnt.addActionListener(this);
		lecture_bnt.addActionListener(this);

		addWindowFocusListener(this);

		setTimeField time = new setTimeField();
		time.start();

	}

	void setBisicInfo() {
		String academy_info_set_data = connect.setBasicInfo();
		StringTokenizer st = new StringTokenizer(academy_info_set_data, "\t");
		student_sum_field.setText(st.nextToken().trim());
		lecture_sum_field.setText(st.nextToken().trim());
		teacher_sum_Field.setText(st.nextToken().trim());
		staff_sum_Field.setText(st.nextToken().trim());
	}

	class setTimeField extends Thread {
		@Override
		public void run() {
			while (true) {
				Calendar calendar = Calendar.getInstance();
				java.util.Date date = calendar.getTime();
				String today = (new SimpleDateFormat("yyyy년MM월dd일HH시mm분").format(date));
				time_field.setText(today);
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String sel = e.getActionCommand();

		if (sel.equals("수강생 관리")) {
			Student_main_frame student_window = new Student_main_frame();
			student_window.setVisible(true);

		} else if (sel.equals("직원 관리")) {
			staffall staff_window = new staffall();
			staff_window.setVisible(true);

		} else if (sel.equals("강사 관리")) {
			teacher teacher_window = new teacher();
			teacher_window.setVisible(true);
		} else if (sel.equals("과정 관리")) {
			CulumMain lecture_window = new CulumMain();
			lecture_window.setVisible(true);
		} else if (sel.equals("교재 관리")) {
			Book book_window = new Book();
			book_window.setVisible(true);
			System.out.println(sel);

		} else if (sel.equals("강의실 관리")) {
			Class1 classroom_window = new Class1();
			classroom_window.setVisible(true);

		} else if (sel.equals("나가기")) {
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		// TODO 메인 페이지
		main_page main = new main_page();
		main.setVisible(true);
	}

	class table_listener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			if (e.getComponent().equals(book_table)) {

				int col = book_table.getSelectedColumn();

				if (col == 5) {
					int row = book_table.getSelectedRow();// 교재를 공급하는 회사는 1곳.
					Object value = book_table.getValueAt(row, 0);

					connect.updateBookData(String.valueOf(value));
					book_content = connect.getContentBooktable();
					book_model = new DefaultTableModel(book_content, book_header);
					book_table.setModel(book_model);

				}
			} else if (e.getComponent().equals(register_table)) {

				int col = register_table.getSelectedColumn();

				if (col == 6) {
					int row = register_table.getSelectedRow();

					Object value = register_table.getValueAt(row, 1);
					Object value2 = register_table.getValueAt(row, 3);

					new simple_register_page(String.valueOf(value), String.valueOf(value2)).setVisible(true);

					regiser_content = connect.getContentRegistertable();
					register_model = new DefaultTableModel(regiser_content, register_header);
					register_table.setModel(register_model);

				}
			}
		}
	}

	@Override
	public void windowGainedFocus(WindowEvent arg0) {
		book_content = connect.getContentBooktable();
		book_model = new DefaultTableModel(book_content, book_header);
		book_table.setModel(book_model);
		regiser_content = connect.getContentRegistertable();
		register_model = new DefaultTableModel(regiser_content, register_header);
		register_table.setModel(register_model);
		setBisicInfo();
	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		// TODO Auto-generated method stub

	}
}
