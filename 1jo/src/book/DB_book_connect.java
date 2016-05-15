package book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DB_book_connect {

	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	private ResultSet rs;
	private String[][] bookcontent;
	private String[][] suppliercontent;
	private String[][] orederpaymentcontent;
	private String[] supplier_name;

	public DB_book_connect() {
		conn = null;
		stat = null;
		rs = null;
		pstat = null;
	}

	String[] supplierBook() {
		String sql;

		try {
			conn = GetConn.getConnection();
			sql = "select count(*) from supplier";
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();
			rs.next();
			int size = rs.getInt(1);
			supplier_name = new String[size];
			sql = "select supplier_name from supplier";
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();
			int cnt = 0;

			while (rs.next()) {
				supplier_name[cnt] = rs.getString(1);
				cnt++;
			}

		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (pstat != null)
					pstat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					GetConn.closeConnection();
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return supplier_name;
		}
	}

	void payinsert(String value) {
		try {

			conn = GetConn.getConnection();
			String sql= "select lecture_no from book_order o inner join lecture l on l.book_no=o.book_no where order_no=?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, value);
			rs=pstat.executeQuery();
			rs.next();
			int lecture_no1=rs.getInt(1);
			
			sql= "select count(student_no) from regist group by lecture_no having lecture_no=?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, lecture_no1);
			rs=pstat.executeQuery();
			rs.next();
			int cnt=rs.getInt(1);
			
			sql = "insert into order_payment(order_no,book_fee,fee_date) values (?,(select  book_price*? from book b inner join book_order o on b.book_no = o.book_no where order_no = ?),sysdate )";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, value);
			pstat.setInt(2, cnt);
			pstat.setString(3, value);
			pstat.executeUpdate();

			sql = "update book_order set paid ='Y',order_cnt=? where  order_no =?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, cnt);
			pstat.setString(2, value);
			pstat.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (pstat != null)
					pstat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					GetConn.closeConnection();
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	@SuppressWarnings("finally")
	String[][] serchorderPaymentTable(String input, int sel, String selection) {
		String sql;

		try {
			conn = GetConn.getConnection();
			if (sel == 1) {
				sql = "select count(*)	from book_order o join book b on o.BOOK_NO = b.BOOK_NO"
						+ " join SUPPLIER s on o.SUPPLIER_NO = s.SUPPLIER_NO where b.book_name like ?";
			} else {
				sql = "select count(*)	from book_order o join book b on o.BOOK_NO = b.BOOK_NO "
						+ "join SUPPLIER s on o.SUPPLIER_NO = s.SUPPLIER_NO where s.supplier_name like ?";
			}

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, "%" + input + "%");
			rs = pstat.executeQuery();
			rs.next();
			int size = rs.getInt(1);

			System.out.println("공급자  " + size);

			if (sel == 1) {
				if (selection.equals("A")) {
					sql = "select o.order_no, b.book_name ,s.supplier_name , o.order_cnt ,o.order_date ,o.paid  "
							+ "from book_order o inner join book b on o.BOOK_NO = b.BOOK_NO inner join SUPPLIER s on o.SUPPLIER_NO = s.SUPPLIER_NO "
							+ "where b.book_name like ? ";
				} else if (selection.equals("Y")) {
					sql = "select o.order_no, b.book_name ,s.supplier_name , o.order_cnt ,o.order_date ,o.paid  "
							+ "from book_order o inner join book b on o.BOOK_NO = b.BOOK_NO inner join SUPPLIER s on o.SUPPLIER_NO = s.SUPPLIER_NO "
							+ "where b.book_name like ? and o.paid = 'Y'";
				} else if (selection.equals("N")) {
					sql = "select o.order_no, b.book_name ,s.supplier_name , o.order_cnt ,o.order_date ,o.paid  "
							+ "from book_order o inner join book b on o.BOOK_NO = b.BOOK_NO inner join SUPPLIER s on o.SUPPLIER_NO = s.SUPPLIER_NO "
							+ "where b.book_name like ? and o.paid = 'N'";
				}

			} else {
				if (selection.equals("A")) {
					sql = "select o.order_no, b.book_name ,s.supplier_name , o.order_cnt ,o.order_date ,o.paid  "
							+ "from book_order o inner join book b on o.BOOK_NO = b.BOOK_NO inner join SUPPLIER s on o.SUPPLIER_NO = s.SUPPLIER_NO "
							+ "where s.supplier_name like ?";

				} else if (selection.equals("Y")) {
					sql = "select o.order_no, b.book_name ,s.supplier_name , o.order_cnt ,o.order_date ,o.paid  "
							+ "from book_order o inner join book b on o.BOOK_NO = b.BOOK_NO inner join SUPPLIER s on o.SUPPLIER_NO = s.SUPPLIER_NO "
							+ "where s.supplier_name like ? and o.paid = 'Y'";

				} else if (selection.equals("N")) {
					sql = "select o.order_no, b.book_name ,s.supplier_name , o.order_cnt ,o.order_date ,o.paid  "
							+ "from book_order o inner join book b on o.BOOK_NO = b.BOOK_NO inner join SUPPLIER s on o.SUPPLIER_NO = s.SUPPLIER_NO "
							+ "where s.supplier_name like ? and o.paid = 'N'";

				}
			}

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, "%" + input + "%");
			rs = pstat.executeQuery();

			orederpaymentcontent = new String[size][7];

			int cnt = 0;

			while (rs.next()) {

				orederpaymentcontent[cnt] = new String[7];
				orederpaymentcontent[cnt][0] = rs.getString(1);
				orederpaymentcontent[cnt][1] = rs.getString(2);
				orederpaymentcontent[cnt][2] = rs.getString(3);
				orederpaymentcontent[cnt][3] = rs.getString(4);
				orederpaymentcontent[cnt][4] = rs.getString(5);
				orederpaymentcontent[cnt][5] = rs.getString(6);
				if (orederpaymentcontent[cnt][5].equals("N")) {
					orederpaymentcontent[cnt][6] = "대금하기";
				} else {
					orederpaymentcontent[cnt][6] = "";
				}
				cnt++;

			}

		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (pstat != null)
					pstat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					GetConn.closeConnection();
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return orederpaymentcontent;
		}

	}

	@SuppressWarnings("finally")
	String[][] notorderPaymentTable() {
		String sql;
		int size;
		try {
			conn = GetConn.getConnection();

			sql = "select count(*) from book_order o join book b on o.BOOK_NO = b.BOOK_NO "
					+ "join SUPPLIER s on o.SUPPLIER_NO = s.SUPPLIER_NO where  o.paid ='N' and o.order_date<sysdate";
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery(sql);
			rs.next();
			size = rs.getInt(1);

			sql = "select order_no, book_name,supplier_name , order_cnt ,order_date ,paid"
					+ " from book_order o inner join book b on o.BOOK_NO = b.BOOK_NO inner join SUPPLIER s on o.SUPPLIER_NO = s.SUPPLIER_NO "
					+ "where  o.paid ='N' and o.order_date<sysdate";
			stat = conn.prepareStatement(sql);
			rs = stat.executeQuery(sql);

			orederpaymentcontent = new String[size][7];

			int cnt = 0;

			while (rs.next()) {

				orederpaymentcontent[cnt] = new String[7];
				orederpaymentcontent[cnt][0] = rs.getString(1);
				orederpaymentcontent[cnt][1] = rs.getString(2);
				orederpaymentcontent[cnt][2] = rs.getString(3);
				orederpaymentcontent[cnt][3] = rs.getString(4);
				orederpaymentcontent[cnt][4] = rs.getString(5);
				orederpaymentcontent[cnt][5] = rs.getString(6);
				if (rs.getString(6).equals("N")) {
					orederpaymentcontent[cnt][6] = "대금하기";
				}

				cnt++;

			}

		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (pstat != null)
					pstat.close();
				if (stat != null)
					stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					GetConn.closeConnection();
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return orederpaymentcontent;
		}

	}

	String[][] payorderPaymentTable() {
		String sql;
		int size;
		try {
			conn = GetConn.getConnection();

			sql = "select count(*) from book_order o join book b on o.BOOK_NO = b.BOOK_NO join SUPPLIER s on o.SUPPLIER_NO = s.SUPPLIER_NO where  o.paid ='Y' and o.order_date<sysdate";
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery(sql);
			rs.next();
			size = rs.getInt(1);

			

			sql = "select order_no, book_name,supplier_name , order_cnt ,order_date ,paid"
					+ " from book_order o inner join book b on o.BOOK_NO = b.BOOK_NO inner join SUPPLIER s on o.SUPPLIER_NO = s.SUPPLIER_NO "
					+ "where  o.paid ='Y' and o.order_date<sysdate";
			stat = conn.prepareStatement(sql);
			rs = stat.executeQuery(sql);

			orederpaymentcontent = new String[size][7];

			int cnt = 0;

			while (rs.next()) {

				orederpaymentcontent[cnt] = new String[7];
				orederpaymentcontent[cnt][0] = rs.getString(1);
				orederpaymentcontent[cnt][1] = rs.getString(2);
				orederpaymentcontent[cnt][2] = rs.getString(3);
				orederpaymentcontent[cnt][3] = rs.getString(4);
				orederpaymentcontent[cnt][4] = rs.getString(5);
				orederpaymentcontent[cnt][5] = rs.getString(6);
				if (rs.getString(6).equals("N")) {
					orederpaymentcontent[cnt][6] = "대금하기";
				}

				cnt++;

			}

		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (pstat != null)
					pstat.close();
				if (stat != null)
					stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					GetConn.closeConnection();
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return orederpaymentcontent;
		}
	}

	@SuppressWarnings("finally")
	String[][] setorderPaymentTable() {
		String sql;
		int size;

		try {
			conn = GetConn.getConnection();

			sql = "select count(*)	from book_order o join book b on o.BOOK_NO = b.BOOK_NO join SUPPLIER s on o.SUPPLIER_NO = s.SUPPLIER_NO where o.order_date<sysdate";
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery(sql);
			rs.next();
			size = rs.getInt(1);

		

			sql = "select order_no, book_name,supplier_name , order_cnt ,order_date ,paid "
					+ "from book_order o inner join book b on o.BOOK_NO = b.BOOK_NO inner join SUPPLIER s on o.SUPPLIER_NO = s.SUPPLIER_NO "
					+ "inner join lecture l on b.book_no=l.book_no where o.order_date<sysdate";

			stat = conn.prepareStatement(sql);
			rs = stat.executeQuery(sql);

			orederpaymentcontent = new String[size][7];

			int cnt = 0;

			while (rs.next()) {

				orederpaymentcontent[cnt] = new String[7];
				orederpaymentcontent[cnt][0] = rs.getString(1);
				orederpaymentcontent[cnt][1] = rs.getString(2);
				orederpaymentcontent[cnt][2] = rs.getString(3);
				orederpaymentcontent[cnt][3] = rs.getString(4);
				orederpaymentcontent[cnt][4] = rs.getString(5);
				orederpaymentcontent[cnt][5] = rs.getString(6);
				if (rs.getString(6).equals("N")) {
					orederpaymentcontent[cnt][6] = "대금하기";
				}

				cnt++;

			}

		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (pstat != null)
					pstat.close();
				if (stat != null)
					stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					GetConn.closeConnection();
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return orederpaymentcontent;
		}

	}

	@SuppressWarnings("finally")
	String[][] serchSupplier(String input) {
		String sql;
		try {
			conn = GetConn.getConnection();
			sql = "select count(*) from supplier where supplier_name like ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, "%" + input + "%");
			rs = pstat.executeQuery();
			rs.next();
			int size = rs.getInt(1);

			sql = "select supplier_no,supplier_name from supplier where supplier_name like ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, "%" + input + "%");
			rs = pstat.executeQuery();

			suppliercontent = new String[size][4];

			int cnt = 0;

			while (rs.next()) {

				suppliercontent[cnt] = new String[4];
				suppliercontent[cnt][0] = rs.getString(1);
				suppliercontent[cnt][1] = rs.getString(2);
				suppliercontent[cnt][2] = "거래 정보";
				suppliercontent[cnt][3] = "수정";
				cnt++;

			}

			;

		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (pstat != null)
					pstat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					GetConn.closeConnection();
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return suppliercontent;
		}

	}

	void updateSupplierTable(String name, String no) {
		try {

			conn = GetConn.getConnection();
			String sql = "update supplier set supplier_name = ? where supplier_no =?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, name);
			pstat.setInt(2, Integer.valueOf(no));
			pstat.executeQuery();

		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (pstat != null)
					pstat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					GetConn.closeConnection();
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@SuppressWarnings("finally")
	String[][] setSuppliercontent() {

		String sql;

		try {
			conn = GetConn.getConnection();

			sql = "select count(*) from supplier";
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			rs.next();
			int size = rs.getInt(1);

			System.out.println("공급자  " + size);

			sql = "select supplier_no,supplier_name from supplier";
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);

			suppliercontent = new String[size][4];

			int cnt = 0;

			while (rs.next()) {

				suppliercontent[cnt] = new String[4];
				suppliercontent[cnt][0] = rs.getString(1);
				suppliercontent[cnt][1] = rs.getString(2);
				suppliercontent[cnt][2] = "거래 정보";
				suppliercontent[cnt][3] = "수정";
				cnt++;

			}

			return suppliercontent;

		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (stat != null)
					stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					GetConn.closeConnection();
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return suppliercontent;
		}

	}

	void deleteBookTableCell(String book_no) {
		System.out.println("삭제");

		try {

			conn = GetConn.getConnection();
			String sql = "delete book where book_no = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, Integer.valueOf(book_no));
			pstat.executeQuery();

		} catch (SQLException e) {
			System.out.println(e);
			JOptionPane.showMessageDialog(null, "이미 강의에서 쓰고 있는 책입니다.");
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (pstat != null)
					pstat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					GetConn.closeConnection();
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	String[] supplier_name() {
		String name[] = null;
		try {

			conn = GetConn.getConnection();
			stat = conn.createStatement();
			rs = stat.executeQuery("select count(*) from book");
			rs.next();
			int size = rs.getInt(1);
			
			String sql = "select s.supplier_name "
					+ "from supplier s inner join book b"
					+ " on b.supplier_no=s.supplier_no";
			
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();		
			
			name=new String[size];			
			
			int cnt=0;
			
			while(rs.next()){
				name[cnt]=rs.getString(1);
				cnt++;
			}
		

		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (pstat != null)
					pstat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					GetConn.closeConnection();
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return name;
		}

	}

	@SuppressWarnings("finally")
	String[][] setBookContent() {
		
		String name[]=supplier_name();

		try {

			conn = GetConn.getConnection();
			stat = conn.createStatement();
			rs = stat.executeQuery("select count(*) from book");
			rs.next();
			int size = rs.getInt(1);

			String sql = "select book_no,book_name,publisher,book_price from book";
			stat = conn.createStatement();

			rs = stat.executeQuery(sql);
					

			bookcontent = new String[size][7];

			int cnt = 0;

			while (rs.next()) {

				bookcontent[cnt] = new String[7];
				bookcontent[cnt][0] = rs.getString(1);
				bookcontent[cnt][1] = rs.getString(2);
				bookcontent[cnt][2] = rs.getString(3);
				bookcontent[cnt][3] = rs.getString(4);
				bookcontent[cnt][4] = name[cnt];
				bookcontent[cnt][5] = "수정";
				bookcontent[cnt][6] = "삭제";
				cnt++;
			}

		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (stat != null)
					stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					GetConn.closeConnection();
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return bookcontent;
		}

	}

	@SuppressWarnings("finally")
	String[][] serchBookContent(String input1, int sel) {
		String sql;
		try {

			if (sel == 1) {
				sql = "select count(*) from book where book_name like ?";
			} else {
				sql = "select count(*) from book where publisher like ?";
			}

			conn = GetConn.getConnection();
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, "%" + input1 + "%");
			rs = pstat.executeQuery();
			rs.next();
			int size = rs.getInt(1);
			System.out.println(size);

			if (sel == 1) {
				sql = "select book_no,book_name,publisher,book_price,supplier_no from book where book_name like ?";
			} else {
				sql = "select book_no,book_name,publisher,book_price,supplier_no from book where publisher like ?";
			}

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, "%" + input1 + "%");
			rs = pstat.executeQuery();

			bookcontent = new String[size][7];

			int cnt = 0;

			while (rs.next()) {

				bookcontent[cnt] = new String[7];
				bookcontent[cnt][0] = rs.getString(1);
				bookcontent[cnt][1] = rs.getString(2);
				bookcontent[cnt][2] = rs.getString(3);
				bookcontent[cnt][3] = rs.getString(4);
				bookcontent[cnt][4] = "";
				bookcontent[cnt][5] = "수정";
				bookcontent[cnt][6] = "삭제";
				cnt++;
			}

		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (pstat != null)
					pstat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					GetConn.closeConnection();
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return bookcontent;
		}

	}

}
