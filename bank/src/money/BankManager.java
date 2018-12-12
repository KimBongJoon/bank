package money;

import java.util.Scanner;

public class BankManager {	

	public static void main(String[] args) {

		BankDAO dao = new BankDAO();
		Scanner sc = new Scanner(System.in);

		while(true) {
			System.out.println("(1)ȸ������, (2)��������, (3)Ż��, (4)�˻�, (0)����");
			System.out.print("����� �����ϼ��� : ");
			int num = sc.nextInt();
			if(num == 0) {
				System.out.println("���α׷��� �����մϴ�.");
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
