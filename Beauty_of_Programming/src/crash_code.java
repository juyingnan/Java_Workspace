import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class crash_code {

	public static int compare(String s1, String s2, int min_dis) {
		int total_un = 0;
		int s1_len = s1.length();
		if (s1.contains(s2))
			return 0;
		for (int k = 0; k < s1_len; k++) {
			if (s1.charAt(k) != s2.charAt(k)) {
				total_un++;
				if (total_un >= min_dis) {
					return total_un;
				}
			}
		}
		return total_un;
	}

	public static void main(String[] args) throws FileNotFoundException {

		int T = 0; // Total test data number
		Scanner in = new Scanner(System.in)
				.useDelimiter("\\n");

		T = Integer.valueOf(in.next().trim());
		int[] results = new int[T];
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < T; i++) {
			// The first line of test data
			String S1, S2;
			S1 = in.next();
			S2 = in.next();
			int S1_len, S2_len;
			S1_len = S1.length();
			S2_len = S2.length();
			int min_dis = S2.length();

			for (int j = 0; j < S1_len - S2_len + 1; j++) {
				// compare

				int total_un = 0;
				total_un = compare(S1.substring(j, j + S2_len), S2, min_dis);
				// find new min
				if (total_un < min_dis) {
					min_dis = total_un;
				}

			}
			System.out.println("Case #" + (i + 1) + ": " + min_dis);
		}

		long endTime = System.currentTimeMillis();
		System.out.println("Duration£º " + (endTime - startTime) + "ms");
	}
}