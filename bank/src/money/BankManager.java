package money;

import java.util.Scanner;

public class BankManager {	

	public static void main(String[] args) {

		BankDAO dao = new BankDAO();
		Scanner sc = new Scanner(System.in);

		while(true) {
			System.out.println("(1)회원가입, (2)정보수정, (3)탈퇴, (4)검색, (0)종료");
			System.out.print("기능을 선택하세요 : ");
			int num = sc.nextInt();
			if(num == 0) {
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
			}

			switch(num) {
			case 1:
				dao.create();
				break;
			case 2:
				dao.update();
				break;
			case 3:
				dao.delete();
				break;
			case 4:
				dao.read();
				break;
			}
		}
	}
}
