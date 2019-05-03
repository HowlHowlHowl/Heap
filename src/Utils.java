
public class Utils {
	public static int intPow(int a, int b) {
		int result = 1;
		while(b > 0) {
			result *= a;
			b--;
		}

		return result;
	}

	public static int intLog2(int a) {
		//System.out.print("Log2 of " + a);
		int result = 0;
		while(a > 1) {
			a = a >> 1;
			result++;
		}
		//System.out.println(": " + result);

		return result;
	}
}
