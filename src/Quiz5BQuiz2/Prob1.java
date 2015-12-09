package Quiz5BQuiz2;

public class Prob1 {
	private static final int  R=3;
	private static final char startChar = 'A';
	
	
	public static int[][] DFATable(String pattern){
		if (pattern.length()==0) return null;
		
		int numOfState = pattern.length();
		int[][] result = new int[R][numOfState];
		
		for (int i=0; i<R; i++){
			if (((char) startChar+i) != pattern.charAt(0)){
				result[i][0]=0;
			}
			else result[i][0]=1;
		}
		
		int xState = 0;
		
		for (int j=1; j<numOfState; j++){
			for (int i=0; i<R; i++){
				result[i][j] = result[i][xState];
			}
			result[pattern.charAt(j)-startChar][j]=j+1;
			xState = result[pattern.charAt(j)][xState];
		}
		return result;
		
	}

	public static void main(String[] args) {
		
		int[][] output = DFATable("BBCBBCBA".trim().split("\\s+").ap)

	}

}
