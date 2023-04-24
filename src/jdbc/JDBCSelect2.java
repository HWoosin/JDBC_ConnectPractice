package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class JDBCSelect2 {

	public static void main(String[] args) {

		/*
		 * 사용자에게 Scanner를 입력하여 job_id를 입력받고
		 * 입력받은 job_id에 해당하는 사람들의 first_name, salary를 콘솔창에 출력 (employees테이블 사용)
		 * 조회된 내용이 없다면 조회결과가 없다고 출력
		 */
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("아이디를 입력해주세요: ");
		String id = sc.next();
		
		String selectSql = "select first_name, salary from employees where job_id = ?";
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String uid = "hr";
		String upw = "hr";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url,uid,upw);
			pstmt = conn.prepareStatement(selectSql);
			
			pstmt.setString(1,id);
			
			rs = pstmt.executeQuery();
			
			int count =0;
			while(rs.next()) {
				String name = rs.getString("first_name");
				int salary = rs.getInt("salary");
				
				System.out.printf("이름: %s\n",name);
				System.out.printf("월급: %d\n",salary);
				System.out.println("=================================================");
				count++;
			}
			if(count == 0) {
				System.out.println("없는 아이디입니다.");
			}
		}
		catch(Exception e) {
			
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
