package Week3BQuiz2;

public class Prob2 {
	
    private static int R = 256;   // extend ASCII alphabet size
    private static int globalCounter = 0;


	// return dth character of s, -1 if d = length of string
	private static int charAt(String s, int d) {
		assert d >= 0 && d <= s.length();
		if (d == s.length())
			return -1;
		return s.charAt(d);
	}

	// sort from a[lo] to a[hi], starting at the dth character
	private static void sort(String[] a, int lo, int hi, int d, String[] aux) {

		// cutoff to insertion sort for small subarrays
		if (hi <= lo) {
			return;
		}
		globalCounter ++;

		// compute frequency counts
		int[] count = new int[R + 2];
		for (int i = lo; i <= hi; i++) {
			int c = charAt(a[i], d);
			count[c + 2]++;
		}

		// transform counts to indicies
		for (int r = 0; r < R + 1; r++)
			count[r + 1] += count[r];

		// distribute
		for (int i = lo; i <= hi; i++) {
			int c = charAt(a[i], d);
			aux[count[c + 1]++] = a[i];
		}

		// copy back
		for (int i = lo; i <= hi; i++)
			a[i] = aux[i - lo];
		
		if (globalCounter == 3) {
			StringBuilder sb = new StringBuilder();
			for (int i=0; i<a.length; i++)
				sb.append(" "+a[i]);
			System.out.println(sb.toString());
		}

		// recursively sort for each character (excludes sentinel -1)
		for (int r = 0; r < R; r++)
			sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1, aux);
	}
	

	public static void main(String[] args) {
		
		String input = "1132 3221 3131 1234 3323 1331 1122 1112 1114 4111 1443 4441 3223 4412 2111";
		String[] a = input.trim().split("\\s+");
		
		int N = a.length;
        String[] aux = new String[N];
        sort(a, 0, N-1, 0, aux);


	}

}
