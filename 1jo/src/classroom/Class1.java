package classroom;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;



public class Class1 extends JFrame implements ActionListener, MouseListener, ItemListener{
   JPanel p1, p2,p3, p4;
   JLabel l1;
   DefaultTableModel model;
   private JTable table;
   JTable table2;
   JScrollPane jsc;
   JButton jb1, jb2, jb3, jb4, jb5;
   JTextField tf1;
   Choice ch;
   JCheckBox c1,c2;
   JCheckBox g;
   
   String field[]={"���ǽ� ��ȣ","��ǻ�� ���","������Ʈ","���� ���� ��ȣ"};
   String field2[]={"���ǽ� ��ȣ","������Ʈ ����","��ǻ�� ���","���� ���� ��ȣ"};
   int cnt_n;
   FlowLayout fl=new FlowLayout();
   int total, cnt, row_c;
   boolean flag;
   
   JPopupMenu PopMenu=new JPopupMenu();
   JMenuItem []PopItem=new JMenuItem[1];
   
   Connection conn=null;
   Statement stmt=null;
   ResultSet rs=null;
   String room_data[][]=null;
   String room_data_in[][]=new String[1][1];
   
   void read(){//db���� ������ �о����
      try{
         conn = GetConn.getConnection();
         
         String sql = "select room_no, com_cnt, prj, staff_no from classroom order by room_no asc";

         stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                  ResultSet.CONCUR_UPDATABLE);
         rs = stmt.executeQuery(sql);
         
         rs.last();
         row_c=rs.getRow();//�������� ���ڵ� ��
         room_data=new String[row_c+1][field.length];//���ڵ带 �迭�� ����         
         
         rs.beforeFirst();
            
         cnt=0;
         while(rs.next()){
            room_data[cnt][0]=Integer.toString(rs.getInt("room_no"));
            room_data[cnt][1]=rs.getString("com_cnt");
            room_data[cnt][2]=rs.getString("prj");
            room_data[cnt][3]=Integer.toString(rs.getInt("staff_no"));
   
            cnt++;
         }
         
         /*room_data[cnt][0]="";
         room_data[cnt][1]="";
         room_data[cnt][2]="";
         room_data[cnt][3]="";*/

         
      }catch(SQLException err){
         System.out.println(err);
      }finally{
         try {if(rs!=null)rs.close();} catch (SQLException e) {}
         try {if(stmt!=null)stmt.close();} catch (SQLException e) {}
         try {
            if(conn!=null){
               GetConn.closeConnection();
               conn.close();
            }} catch (SQLException e) {}
      }
   }
   
   
   void data(){//���̺� ������ �ֱ�
      model=new DefaultTableModel(room_data, field){
         
        @Override
       public boolean isCellEditable(int row, int column) {
          if(row<table.getRowCount()-1&&column==0){
             return false;
          }else{
             return true;
          }
       }
         
      };
      table=new JTable(model);
      
      table.getTableHeader().setReorderingAllowed(false); // ���̺� �̵� �Ұ� 
      table.getTableHeader().setResizingAllowed(false); // ���̺� ũ�� ���� �Ұ�
      
      jsc=new JScrollPane(table);
      
   }
   
   public Class1(){      
      
      setResizable(false);   
      l1=new JLabel();
      p1=new JPanel();
      p2=new JPanel();
      p3=new JPanel();
      p4=new JPanel();
      
      l1.setText("���ǽ� ��Ȳ");
      
      read();
      data();
      
      ch=new Choice();
      for(String item : field2){
         ch.addItem(item);
      }
      tf1=new JTextField(7);
      jb1=new JButton("�˻�");
      jb2=new JButton("����");
      jb3=new JButton("�߰�");
      jb4=new JButton("����");
      
      p3.add(ch);
      p3.add(tf1);
      p3.add(jb1);
      p3.add(jb2);
      p3.add(jb3);
      p3.add(jb4);
      
      p2.add(l1);
      p2.add(p3);
   
      p2.setLayout(new GridLayout(2,1));
      
      add("North",p2);
      add("Center",jsc);

            
      table.addMouseListener(this);
      jb1.addActionListener(this);
      jb3.addActionListener(this);
      jb2.addActionListener(this);
      jb4.addActionListener(this);
      
      setSize(500,500);
      setVisible(true);

   }
   
   @Override
   public void actionPerformed(ActionEvent e) {
      ClassSelect cs;
      String fi;
      String r=tf1.getText();
      int no;
      String item=ch.getSelectedItem();
      String b_n=e.getActionCommand();
      
      int com_cnt, staff_no;
      String prj;
      
      if(b_n=="�˻�"){
         if(item==("���ǽ� ��ȣ")){//���ǽ� ��ȣ �˻�
            fi="room_no";
            try{
               cs=new ClassSelect(fi,Integer.parseInt(r));   
            }catch(java.lang.NumberFormatException er){//���� �̿��� Ÿ���� �� ���.
               JOptionPane.showMessageDialog(null, "���ڷ� �Է��� �ּ���.");
            }
            
         }else if(item=="������Ʈ ����"){//prj ���� �˻�
            fi="prj";
            if(r.equalsIgnoreCase("y")){
               no=1;
               cs=new ClassSelect(fi,no);
            }else if(r.equalsIgnoreCase("n")){
               no=0;
               cs=new ClassSelect(fi,no);
            }else{//n�� y�� �ƴ� �ٸ� ���� �� ���
               JOptionPane.showMessageDialog(null, "Y �Ǵ� N���θ� �Է��� �ּ���");
            }
            
         }else if(item=="���� ���� ��ȣ"){//���� ��ȣ �˻�
            fi="staff_no";
            try{
               cs=new ClassSelect(fi,Integer.parseInt(tf1.getText()));
            }catch(java.lang.NumberFormatException er){//���� �̿��� ���� �Էµ� ���
               JOptionPane.showMessageDialog(null, "���ڷ� �Է��� �ּ���.");
            }
         }else if(item=="��ǻ�� ���"){//��ǻ�� ��� �˻�
            fi="com_cnt";
            try{
               cs=new ClassSelect(fi,Integer.parseInt(tf1.getText()));
            }catch(java.lang.NumberFormatException er){//���� �̿��� ���� �Էµ� ���
                  JOptionPane.showMessageDialog(null, "���ڷ� �Է��� �ּ���.");
               }
            
         }
      }else{//������ �߰�
         //table.setValueAt(table.get, row, table.getSelectedColumn());
         
          try {//���� �Ǵ� ��Ŀ���� �������� �ʾƵ� edit ���� ���� �о�� �� �ֵ��� ��Ŀ�� ����
                  table.editCellAt(-1, -1);
              } catch (Exception ex) {}
          
            int row=table.getSelectedRow();
            
            int room_no=Integer.parseInt((String)table.getValueAt(row, 0));
                        
            com_cnt=Integer.parseInt((String)table.getValueAt(row, 1));
            prj=(String)table.getValueAt(row, 2);
            staff_no=Integer.valueOf(String.valueOf(table.getValueAt(row, 3)));
            
            int r_cnt=model.getRowCount();
            
         if(b_n=="����"){
            new ClassUpdate(room_no, com_cnt,prj,staff_no);
         }else if(b_n=="�߰�"){//�߰�
            new ClassInsert(room_no, com_cnt, prj, staff_no);
            for(int i=r_cnt-1;i>=0;i--){
               model.removeRow(i);
            }
         
         read();
         
         r_cnt++;

         for(int i=0;i<r_cnt;i++){
            model.addRow(room_data[i]);
         }
         
         }else{
            new ClassDelete(room_no);
         }
         
         
         
         for(int i=r_cnt-1;i>=0;i--){
            model.removeRow(i);
         }
         
         
         read();
         
         try{
            for(int i=0;i<r_cnt;i++){
               model.addRow(room_data[i]);
            }
         }catch(Exception er){
            
         }
         
      }
   }   
   

   @Override
   public void mouseClicked(MouseEvent arg0) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void mouseEntered(MouseEvent arg0) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void mouseExited(MouseEvent arg0) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void mousePressed(MouseEvent e) {//���ǽ� �� ���� Ȯ��â
      if(table.getSelectedColumn()==0&&table.getSelectedRow()!=table.getRowCount()-1){
         //"�߰�"�� �����ϴ� �� ����
         int row=table.getSelectedRow();
         int r=Integer.parseInt((String)table.getValueAt(row, 0));
         String field=null;
         int com_no, staff_no;
         String prj;
         
            if(e.getModifiersEx() == MouseEvent.BUTTON3_DOWN_MASK){
               table.editCellAt(table.getSelectedRow(), 0);
               
            }else if(e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK){
               Class2 cl2=new Class2(r);
            }
         
      }else if(table.getSelectedColumn()==3){
     
            if(e.getModifiersEx() == MouseEvent.BUTTON3_DOWN_MASK){
               //table.editCellAt(table.getSelectedRow(), 0);
               
            }else if(e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK){
               Class_Staff cs=new Class_Staff();               
            }
         
      }
      
   }

   @Override
   public void mouseReleased(MouseEvent arg0) {
   
   }

   @Override
   public void itemStateChanged(ItemEvent arg0) {
      
      
   }
   class Class_Staff extends JFrame {
      
      Connection conn=null;
      Statement stmt=null;
      ResultSet rs=null;
      String staff_data[][]=null;
      
      JLabel jl;
      String field[]={"���� ��ȣ","���� �̸�", "��簭�ǽ�"};
      int row;
      
      DefaultTableModel model;
      JTable table2;
      JScrollPane jsc;
      int staff_no2;
      
      Class_Staff(){
         renew();
      }
      
         
      void renew(){
         
         try{
         
         conn = GetConn.getConnection();
         String sql = "select count(*) from staff";
         stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                  ResultSet.CONCUR_UPDATABLE);
         rs = stmt.executeQuery(sql);
         rs.last();
         int row = rs.getInt(1);
         
         sql = "select s.staff_no, c.room_no from staff s inner join classroom c on s.staff_no=c.staff_no order by s.staff_no";
         stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                  ResultSet.CONCUR_UPDATABLE);
         rs = stmt.executeQuery(sql);
         rs.last();
         int r_1=rs.getRow();
         
         rs.beforeFirst();
         int cnt=0;
         int data[][]=new int[r_1][2];
         
         while(rs.next()){
            data[cnt][0]=rs.getInt("staff_no");            
            data[cnt][1]=rs.getInt("room_no");
            cnt++;
         }
         
         
         sql = "select staff_no, staff_name from staff";

         stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                  ResultSet.CONCUR_UPDATABLE);
         rs = stmt.executeQuery(sql);
         System.out.println(cnt);
         rs.last();
         //�������� ���ڵ� ��
         staff_data=new String[row][field.length];//���ڵ带 �迭�� ����         
         
         rs.beforeFirst();
            
         cnt=0;
         while(rs.next()){
            staff_data[cnt][0]=Integer.toString(rs.getInt(1));
            staff_data[cnt][1]=rs.getString(2);
            staff_data[cnt][2]="";
            
            for(int i=0;i<data.length;i++){
               if(staff_data[cnt][0].equals(String.valueOf(data[i][0]))){
                  staff_data[cnt][2]+=String.valueOf(data[i][1]);
                  if(i<data.length){
                     staff_data[cnt][2]+=",";
                  }
               }
            }
            
            cnt++;
         }
   
      }catch(SQLException err){
         System.out.println(err);
      }finally{
         try {if(rs!=null)rs.close();} catch (SQLException e) {}
         try {if(stmt!=null)stmt.close();} catch (SQLException e) {}
         try {
            if(conn!=null){
               GetConn.closeConnection();
               conn.close();
            }} catch (SQLException e) {}
      }
         
         
         model=new DefaultTableModel(staff_data, field){//�� ���� ����
             public boolean isCellEditable(int row, int column){
                   return false;
                }
                
               };
         table2=new JTable(model);
         
         table2.getTableHeader().setReorderingAllowed(false); // ���̺� �̵� �Ұ� 
         table2.getTableHeader().setResizingAllowed(false); // ���̺� ũ�� ���� �Ұ�
         
         
         int staff_no1;
         table2.addMouseListener(new MouseListener(){
            
            @Override
            public void mouseClicked(MouseEvent arg0) {
               // TODO Auto-generated method stub
               
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
               // TODO Auto-generated method stub
               
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
               // TODO Auto-generated method stub
               
            }

            @Override
            public void mousePressed(MouseEvent e) {
               
               if(e.getClickCount()==2){
                  int row = table2.getSelectedRow();                  
                  table.setValueAt(Integer.parseInt((String) table2.getValueAt(row, 0)), table.getSelectedRow(), table.getSelectedColumn());
                  dispose();
               }
               
            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
               // TODO Auto-generated method stub
               
            }
            
         });
         
         jsc=new JScrollPane(table2);
         
         add(jsc);
         
         setSize(300, 300);
         setVisible(true);

         
   }
      
   }
      
}