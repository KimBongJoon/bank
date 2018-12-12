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

	//ȸ������
	public void create() {		
		System.out.println("ȸ������ �������Դϴ�.");
		System.out.print("���̵� : ");
		dto.setId(sc.next());
		System.out.print("�̸� : ");
		dto.setName(sc.next());
		System.out.print("���� : ");
		dto.setAge(sc.nextInt());
		System.out.print("��ȭ��ȣ : ");
		dto.setTel(sc.next());

		try {	//DB�� �Է��� ���̵�, �̸�, ����, ��ȭ��ȣ ����
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "1234");

			String sql = "insert into member values (?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getName());
			ps.setInt(3, dto.getAge());
			ps.setString(4, dto.getTel());
			ps.executeUpdate();
			System.out.println("ȸ�������� �Ϸ�Ǿ����ϴ�.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//�˻�
	public void read() {
		ArrayList member = new ArrayList<>();
		
		System.out.println("(1)�� ���� �˻�, (2)��ü �� ����Ʈ");
		System.out.print("�˻��Ͻ� ���� ���� : ");
		int input = sc.nextInt();
		
		switch(input) {
		case 1:
			System.out.println("���̵� �Է��ϼ���.");
			System.out.print("���̵� : ");
			dto.setId(sc.next());
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "1234");

				//�Է¹��� ���̵� ���� ������ member����Ʈ�� ����
				String sql = "select * from member where id = '" + dto.getId() + "'";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql); 
				while(rs.next()) {
					member.add(rs.getString(1));	//���̵�
					member.add(rs.getString(2));	//�̸�
					member.add(rs.getInt(3));		//����
					member.add(rs.getString(4));	//��ȭ��ȣ
				}
				System.out.println("�̸� : " + member.get(1));
				System.out.println("���� : " + member.get(2));
				System.out.println("��ȭ��ȣ : " + member.get(3));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 2:
			System.out.println("��ü �� ����Ʈ�Դϴ�.");
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "1234");

				//��ü ȸ�������� ����Ʈ�� ����
				String sql = "select * from member";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql); 
				while(rs.next()) {
					member.add(rs.getString(1));
					member.add(rs.getString(2));
					member.add(rs.getInt(3));
					member.add(rs.getString(4));
				}
				System.out.println("���̵�\t�̸�\t����\t��ȭ��ȣ");
				for(int i=0; i<member.size(); i++) {
					if(i!=0 && i%4 == 0) {		//4�� ������� �ٹٲ�, ù��° �� ����
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

	//���� ����
	public void update() {		
		ArrayList memberId = new ArrayList<>();
		
		System.out.println("�������� �������Դϴ�. ���̵� �Է��ϼ���.");
		System.out.print("���̵� : ");
		dto.setId(sc.next());
		System.out.print("�����Ͻ� ��ȭ��ȣ : ");
		dto.setTel(sc.next());
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "1234");

			String sql = "select id from member";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql); 
			while(rs.next()) {
				memberId.add(rs.getString(1));	//memberId�� ��� ȸ������ id�� ����
			}
			for(int i=0; i<memberId.size(); i++) {
				if(dto.getId().equals(memberId.get(i))) {	//�Է¹��� ���̵�� ����Ʈ�� �ִ� ���̵� ������ ��ȭ��ȣ ����
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
		System.out.println("������ �Ϸ�Ǿ����ϴ�.");
	}

	//Ż��
	public void delete() {
		System.out.println("�����Ͻ� ���̵� �Է��ϼ���.");
		System.out.print("���̵� : ");
		dto.setId(sc.next());
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "1234");

			String sql = "delete from member where id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getId());	//�Է¹��� ���̵� ���� ���� ����
			ps.executeUpdate();
			System.out.println("ȸ�������� �����Ǿ����ϴ�.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
