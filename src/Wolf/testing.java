package Wolf;
import java.util.Scanner;

public class testing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		int num = scanner.nextInt();
		int i;
		int result = 1;

		for (i = 1; i <= num; i++) {
			result = result * i;
			if (i == num) {
				System.out.println(result);
			}

		}

	}
}
