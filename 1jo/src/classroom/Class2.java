package classroom;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EventObject;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class Class2 extends JFrame{//강의실의 세부 사항
	JPanel p1, p2,p3;
	JLabel l1, l2, l3, l4;
	JTable jt;
	TextArea ta1, ta2;
	String data[]={"주중","주말"};
	String data2[][]={{"주중오전","주말오전"},
					  {"주중오후","주말오후"}};
	String sql;
	int com_cnt;
	String prj;
	String staff;
	int class_no, cnt;
	String schedule[][], cl_n[][];
	
	Connection conn=null;
	Statement stmt=null;
	ResultSet rs=null;
	
	FlowLayout fl=new FlowLayout();
	
	Class2(){}
	
	Class2(int no){
		p1=new JPanel();
		class_no=no;
		setResizable(false);
		l1=new JLabel();
		l1.setText("강의실 "+no+" 이용현황");
		p3=new JPanel();
		l4=new JLabel();
		
		p3.setLayout(new GridLayout(2,1));
		p3.add(l1);
		p3.add(l4);
		
		
		try{
			conn = GetConn.getConnection();
			sql = "select com_cnt, prj, s.staff_name from classroom c inner join staff s on c.staff_no=s.staff_no where room_no="+no;
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
		            ResultSet.CONCUR_UPDATABLE);
		
		rs = stmt.executeQuery(sql);
		
		rs.next();
		
		com_cnt=rs.getInt("com_cnt");
		prj=rs.getString("prj");
		staff=rs.getString("staff_name");
		
		
	}catch(SQLException err){
		System.out.println(err);
	}finally{
		try {if(rs!=null)rs.close();} catch (SQLException e) {}
		try {if(stmt!=null)stmt.close();} catch (SQLException e) {}
		try {if(conn!=null){
			GetConn.closeConnection();
			conn.close();
		}} catch (SQLException e) {}
	}
	
		l4.setText("관리자 :"+staff);
		
		l2=new JLabel();
		l2.setText("컴퓨터 대수 :"+com_cnt);
		
		l3=new JLabel();
		l3.setText("프로젝터 YN :"+prj);
		
		p2=new JPanel();
		p2.add(l2);
		p2.add(l3);
		
		try{//강의실 이용 현황을 시간표에 표시하기 위해 주중/주간, 오전/오후 값을 읽어온다.
			conn = GetConn.getConnection();
			sql = "select week, class, time from lecture where room_no="+no;
			stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
		            ResultSet.CONCUR_UPDATABLE);
		rs = stmt.executeQuery(sql);
		
		rs.last();
		int row=rs.getRow();
		schedule=new String[row][data.length];
		cl_n=new String[row][data.length];

		rs.beforeFirst();
		
		cnt=0;
		while(rs.next()){
			String week=rs.getString("week");
			String time=rs.getString("time");
			if(week.equals("Y")&&time.equals("Y")){
				schedule[cnt][0]="0";
				schedule[cnt][1]="0";
				cl_n[cnt][0]=rs.getString("class");
				cnt++;
			}else if(week.equals("Y")&&time.equals("N")){
				schedule[cnt][0]="0";
				schedule[cnt][1]="1";
				cl_n[cnt][0]=rs.getString("class");
				cnt++;
			}else if(week.equals("N")&&time.equals("Y")){
				schedule[cnt][0]="1";
				schedule[cnt][1]="0";
				cl_n[cnt][0]=rs.getString("class");
				cnt++;
			}else if(week.equals("N")&&time.equals("N")){
				schedule[cnt][0]="1";
				schedule[cnt][1]="1";
				cl_n[cnt][0]=rs.getString("class");
				cnt++;
			}
		}
		
	}catch(SQLException err){
		System.out.println(err);
	}finally{
		try {if(rs!=null)rs.close();} catch (SQLException e) {}
		try {if(stmt!=null)stmt.close();} catch (SQLException e) {}
		try {if(conn!=null){
			GetConn.closeConnection();
			conn.close();
		}} catch (SQLException e) {}
	}
	
		DefaultTableModel dtm = new DefaultTableModel(data2,data){//셀 수정 금지
			 public boolean isCellEditable(int row, int column){
			    return false;
			 }
			 
			};

	
		jt=new JTable(dtm){
			
			 int row_c, col_c;
	    	 String cln;
					    
			public Component prepareRenderer(TableCellRenderer tcr, int row, int column) {
		    	  //강의실 사용 현황을 시간표에 표시
		    	
		    	 Component c = super.prepareRenderer(tcr, row, column);
		    	 for(int i=0;i<schedule.length;i++){
		    		 row_c=Integer.parseInt(schedule[i][0]);
		    		 col_c=Integer.parseInt(schedule[i][1]);
		    		 cln=cl_n[i][0];
		    		 
		    		 if(row==row_c&&column==col_c){
		    			 //dtm.setValueAt(cln, row, column);
		    			c.setForeground(Color.black);
		    			c.setBackground(Color.PINK);		    			
		    			setValueAt(cl_n[i][0],row,column);
		    			 break;
		    			 
		    		 }else{
		    			c.setBackground(Color.lightGray);
		    			c.setForeground(Color.lightGray);

		    		 }
		    		 
		    	 }
				return c;
				
		      }
			
			
			
		    };
		    
		    jt.getTableHeader().setReorderingAllowed(false); // 이동 불가 
		    jt.getTableHeader().setResizingAllowed(false); // 크기 조절 불가
		
		JScrollPane jsc=new JScrollPane(jt);
		
		add("North",p3);
		add("Center",jsc);
		add("South",p2);
		
		setSize(300, 300);
		setVisible(true);
		
		
	}
		
	public static void main(String[] args){
			
	}

}
