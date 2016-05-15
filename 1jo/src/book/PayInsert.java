package book;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class PayInsert extends JFrame implements ActionListener{
	// ��� ����
	JPanel contentPane;
	JButton btnNewButton_1;
	private DB_book_connect connect=new DB_book_connect();
	String value;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public PayInsert(String value) {
		this.value=value;
		setResizable(false);
		setTitle("��� ����");
		setBounds(100, 100, 226, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 205, 67);
		contentPane.add(panel);

		JLabel lblNewLabel = new JLabel("���� �Ͻðڽ��ϱ�?");
		panel.add(lblNewLabel);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(5, 69, 200, 33);
		contentPane.add(panel_1);

		JButton btnNewButton = new JButton("��");
		panel_1.add(btnNewButton);
		
				
		btnNewButton.addActionListener(this);

		btnNewButton_1 = new JButton("�ƴϿ�");
		btnNewButton_1.addActionListener(this);
		panel_1.add(btnNewButton_1);

	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("�ƴϿ�")){
			dispose();
		}
		if(e.getActionCommand().equals("��")){
			connect.payinsert(value);
			Payment payment =new Payment("a");
			dispose();
		}
		
	}

}
