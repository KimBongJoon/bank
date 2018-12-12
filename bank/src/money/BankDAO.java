package money;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class BankDAO {

	BankDTO dto = new BankDTO();
	Scanner sc = new Scanner(System.in);

	//회원가입
	public void create() {		
		System.out.println("회원가입 페이지입니다.");
		System.out.print("아이디 : ");
		dto.setId(sc.next());
		System.out.print("이름 : ");
		dto.setName(sc.next());
		System.out.print("나이 : ");
		dto.setAge(sc.nextInt());
		System.out.print("전화번호 : ");
		dto.setTel(sc.next());

		try {	//DB에 입력한 아이디, 이름, 나이, 전화번호 저장
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "1234");

			String sql = "insert into member values (?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getName());
			ps.setInt(3, dto.getAge());
			ps.setString(4, dto.getTel());
			ps.executeUpdate();
			System.out.println("회원가입이 완료되었습니다.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//검색
	public void read() {
		ArrayList member = new ArrayList<>();
		
		System.out.println("(1)고객 정보 검색, (2)전체 고객 리스트");
		System.out.print("검색하실 정보 선택 : ");
		int input = sc.nextInt();
		
		switch(input) {
		case 1:
			System.out.println("아이디를 입력하세요.");
			System.out.print("아이디 : ");
			dto.setId(sc.next());
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "1234");

				//입력받은 아이디에 대한 정보를 member리스트에 저장
				String sql = "select * from member where id = '" + dto.getId() + "'";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql); 
				while(rs.next()) {
					member.add(rs.getString(1));	//아이디
					member.add(rs.getString(2));	//이름
					member.add(rs.getInt(3));		//나이
					member.add(rs.getString(4));	//전화번호
				}
				System.out.println("이름 : " + member.get(1));
				System.out.println("나이 : " + member.get(2));
				System.out.println("전화번호 : " + member.get(3));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 2:
			System.out.println("전체 고객 리스트입니다.");
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "1234");

				//전체 회원정보를 리스트에 저장
				String sql = "select * from member";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql); 
				while(rs.next()) {
					member.add(rs.getString(1));
					member.add(rs.getString(2));
					member.add(rs.getInt(3));
					member.add(rs.getString(4));
				}
				System.out.println("아이디\t이름\t나이\t전화번호");
				for(int i=0; i<member.size(); i++) {
					if(i!=0 && i%4 == 0) {		//4의 배수마다 줄바꿈, 첫번째 줄 제외
						System.out.println();
					}
					System.out.print(member.get(i) + "\t");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println();
			break;
		}
	}

	//정보 수정
	public void update() {		
		ArrayList memberId = new ArrayList<>();
		
		System.out.println("정보수정 페이지입니다. 아이디를 입력하세요.");
		System.out.print("아이디 : ");
		dto.setId(sc.next());
		System.out.print("변경하실 전화번호 : ");
		dto.setTel(sc.next());
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "1234");

			String sql = "select id from member";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql); 
			while(rs.next()) {
				memberId.add(rs.getString(1));	//memberId에 모든 회원들의 id를 저장
			}
			for(int i=0; i<memberId.size(); i++) {
				if(dto.getId().equals(memberId.get(i))) {	//입력받은 아이디와 리스트에 있는 아이디가 같으면 전화번호 수정
					String sql2 = "update member set tel = ? where id = ?";
					PreparedStatement ps = con.prepareStatement(sql2);
					ps.setString(1, dto.getTel());
					ps.setString(2, (String) memberId.get(i));			
					ps.executeUpdate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("수정이 완료되었습니다.");
	}

	//탈퇴
	public void delete() {
		System.out.println("삭제하실 아이디를 입력하세요.");
		System.out.print("아이디 : ");
		dto.setId(sc.next());
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "1234");

			String sql = "delete from member where id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getId());	//입력받은 아이디에 대한 정보 삭제
			ps.executeUpdate();
			System.out.println("회원정보가 삭제되었습니다.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
