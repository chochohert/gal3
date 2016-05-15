package book;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.FlowLayout;
import javax.swing.JSplitPane;
import javax.swing.JInternalFrame;
import javax.swing.JComboBox;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SupplierInsert extends JFrame {
	// 공급자 입력.
	private JPanel contentPane;
	private JTextField tname;

	public SupplierInsert() {
		setResizable(false);
		setTitle("공급자 입력");
		setBounds(100, 100, 250, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);

		JButton insert = new JButton("저장");
		panel_1.add(insert);

		insert.addActionListener(new ActionListener() {
			// no_count.nextval
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Connection conn = null;
				PreparedStatement stat = null;
				if (tname.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "상호명을 입력 안했습니다", "값을 잘못 입력했습니다.", JOptionPane.WARNING_MESSAGE);
					return;
				}

				try {

					conn = GetConn.getConnection();
					String sql = "insert into supplier(supplier_no,supplier_name) values(supplier_cnt.nextval,?)";

					stat = conn.prepareStatement(sql);
					stat.setString(1, tname.getText());

					stat.executeUpdate();
					System.out.println("성공");

					dispose();

				} catch (SQLException err) {
					System.out.println(err);
				} finally {
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

		});

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(1, 4));

		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panel_5 = new JPanel();
		panel_2.add(panel_5);

		JLabel lname = new JLabel("상 호 : ");
		panel_5.add(lname);

		tname = new JTextField();
		panel_5.add(tname);
		tname.setColumns(10);

	}

}
