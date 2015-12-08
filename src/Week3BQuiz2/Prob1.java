package Week3BQuiz2;

public class Prob1 {
	
	public static void main(String[] args){
		String input = "1411 3411 3441 1424 4421 4224 3321 1331 1311 3424";
		String[] a = input.trim().split("\\s+");
		int W = a[0].length();
		
		int N = a.length;
        int R = 256;   // extend ASCII alphabet size
        String[] aux = new String[N];

        for (int d = W-1; d >= W-2; d--) {
            // sort by key-indexed counting on dth character

            // compute frequency counts
            int[] count = new int[R+1];
            for (int i = 0; i < N; i++)
                count[a[i].charAt(d) + 1]++;

            // compute cumulates
            for (int r = 0; r < R; r++)
                count[r+1] += count[r];

            // move data
            for (int i = 0; i < N; i++)
                aux[count[a[i].charAt(d)]++] = a[i];

            // copy back
            for (int i = 0; i < N; i++)
                a[i] = aux[i];
        }
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<N; i++){
        	sb.append(" "+a[i]);
        }
        System.out.println(sb.toString());
		
	}

}
