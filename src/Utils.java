
public class Utils {
	//Potenza intera
	public static int intPow(int a, int b) {
		int result = 1;
		while(b > 0) {
			result *= a;
			b--;
		}
		return result;
	}

	//Log2 intero, troncato
	public static int intLog2(int a) {
		int result = 0;
		while(a > 1) {
			a = a >> 1;
			result++;
		}
		return result;
	}
}
