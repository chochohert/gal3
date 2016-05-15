package teacher;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.FlowLayout;

public class showClassbyteacher extends JFrame implements ActionListener{
	
	private JPanel panel;
	JPanel panel_1;
	JScrollPane scrollPane;
	final String [] header={"강의 번호","강의실","주중/주말","오전/오후","반","과정명","인원"};
	private String [][] content;
	private DefaultTableModel model;
	private JTable table;
	private JTextField textField;
	private JButton oK_bnt;
	private JButton exit_bnt;
	private DB_teacher_connect connect=new DB_teacher_connect();
	
	public showClassbyteacher(int teacher_no) {
		setSize(400, 500);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		content=connect.setClassbyteacher(teacher_no);
		
		model=new DefaultTableModel(content,header){
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		
		
		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setEnabled(false);
		scrollPane.setViewportView(table);
		table.setModel(model);
		table.getColumnModel().getColumn(0).setMaxWidth(60);
		table.getColumnModel().getColumn(1).setMaxWidth(60);
		table.getColumnModel().getColumn(2).setMaxWidth(60);
		table.getColumnModel().getColumn(3).setMaxWidth(60);
		table.getColumnModel().getColumn(4).setMaxWidth(60);
		table.getColumnModel().getColumn(5).setMinWidth(120);
		table.getColumnModel().getColumn(6).setMaxWidth(60);
		

		
		panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		textField = new JTextField();
		panel_1.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("\uAC80\uC0C9");
		lblNewLabel.setEnabled(false);
		panel_1.add(lblNewLabel);
		
		oK_bnt = new JButton("확인");
		panel_1.add(oK_bnt);
		
		exit_bnt = new JButton("나가기");
		panel_1.add(exit_bnt);
		setTitle("배정 강의 보기");
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("확인")){
			
									
		}else if(e.getActionCommand().equals("나가기")){
			dispose();
		}		
	}
}
