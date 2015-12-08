package Week3BQuiz2;

public class Prob3 {
	
	public static void sort(String[] a) {
        sort(a, 0, a.length-1, 0);
    }

    // return the dth character of s, -1 if d = length of s
    private static int charAt(String s, int d) { 
        assert d >= 0 && d <= s.length();
        if (d == s.length()) return -1;
        return s.charAt(d);
    }


    // 3-way string quicksort a[lo..hi] starting at dth character
    private static void sort(String[] a, int lo, int hi, int d) { 

        if (hi <= lo) {
            return;
        }

        int lt = lo, gt = hi;
        int v = charAt(a[lo], d);
        int i = lo + 1;
        while (i <= gt) {
            int t = charAt(a[i], d);
            if      (t < v) exch(a, lt++, i++);
            else if (t > v) exch(a, i, gt--);
            else              i++;
        }
        
        if (d==0){
        	StringBuilder sb = new StringBuilder();
        	for (int j=0; j<a.length; j++) sb.append(" "+a[j]);
        	System.out.println(sb.toString());
        }

        // a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi]. 
        sort(a, lo, lt-1, d);
        if (v >= 0) sort(a, lt, gt, d+1);
        sort(a, gt+1, hi, d);
    }


    // exchange a[i] and a[j]
    private static void exch(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }



	public static void main(String[] args) {
		String input = "5432 3153 6552 4465 6316 5311 5656 1334 5346 2244";
		String[] a = input.trim().split("\\s+");
		
        sort(a);
	}

}
