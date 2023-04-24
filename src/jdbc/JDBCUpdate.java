package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class JDBCUpdate {

	public static void main(String[] args) {

		//ID를 입력받기
		//수정할 이름고 이메일도 입력
		//지목한 ID의 이름과 이메일을 새로운 값으로 수정
		//(ID는 존재하는 ID를 넣어주셔야 수정이 가능하다.)\
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("변경할 회원의 ID:");
		String id = sc.next();
		
		System.out.print("변경할 이름:");
		String name = sc.next();
		
		System.out.print("변경할 나이:");
		int age = sc.nextInt();
		
		String UpdateSql = "UPDATE members set mem_name = ?, mem_age= ? where mem_id = ?";
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String uid = "hr";
		String upw = "hr";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection(url,uid,upw);
			pstmt = conn.prepareStatement(UpdateSql);
			
			pstmt.setString(3,id);
			pstmt.setString(1,name);
			pstmt.setInt(2,age);
			
			int rn =pstmt.executeUpdate();
			
			if(rn ==1) {
				System.out.println("DB에 회원정보 수정 성공!");
			}
			else {
				System.out.println("DB에 회원정보 수정 실패!");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			/*
			 * 4. DB 연동 객체들 자원 반납.
			 * -원활한 JDBC 프로그래밍을 위해 사용한 자원을 반납.
			 */
			try {
				pstmt.close();
				conn.close();
				sc.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		}
	}

}
