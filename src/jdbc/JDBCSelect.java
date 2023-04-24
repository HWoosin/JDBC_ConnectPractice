package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class JDBCSelect {

	public static void main(String[] args) {

		String sql = "SELECT * from members";
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String uid = "hr";
		String upw = "hr";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; //selet문에서만 사용하는 객체.
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url,uid,upw);
			pstmt = conn.prepareStatement(sql);
			
			//select문은 executeQuery()를 통해 ResultSet 객체를 받아온다.
			//rs는 select문의 쿼리 실행 결과 전체를 들고있다.
			rs = pstmt.executeQuery();
			
			/*
			 * select 쿼리문의 실행결과, 조회할 데이터가 단 한줄이라도 존재한다면
			 * rs 객체의 next() 메서드는 true값을 리터하면서 해당 행을 지목한다.
			 * 그렇기 때문에 rs에게 데이터를 읽어올때는 반드시 next()를 먼저 호출해서 데이터의 존재유무부터 물어봐야한다.
			 * next() 메서드를 호출해야함 행 하나가 지목되면서 데이터를 불러들일 수 있다.
			 */
			
			//rs는 한 행씩 가져온다
//			System.out.println(rs);  
			
			//조회할 데이터 행의 개수가 여러개라면 반복문을 이용해서 처리하는게 좋다.
			//만약 조회할 데이터가 한 행이라면 굳이 반복문 열필요없이 if문으로 처리
			int count = 0;
			while(rs.next()) {
				/*
				 * select의 실행결과 컬럼을 읽어오려면
				 * rs의 getString(), getInt(), getDouble()...의 메서드를 사용해서 리턴받는다(컬럼의 타입에 따라)
				 */
				String id = rs.getString("mem_id");
				String pw = rs.getString("mem_pw");
				String name = rs.getString("mem_name");
				int age = rs.getInt("mem_age");
				
				//LocalDateTime -> Timestamp: Timestamp.valueof(LocalDateTime);
				//Timestamp -> LocalDateTime: Timestamp.toLocalDateTime();
				LocalDateTime regdate = rs.getTimestamp("mem_regdate").toLocalDateTime();
				
				System.out.printf("아이디: %s\n",id);
				System.out.printf("비밀번호: %s\n",pw);
				System.out.printf("이름: %s\n",name);
				System.out.printf("나이: %d\n",age);
				System.out.printf("가입일: %s\n",regdate);
				System.out.println("=================================================");
				count++;
			}
			System.out.println("조회된 행의 갯수 : "+count);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
