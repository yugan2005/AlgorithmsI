package Week6BQuiz2;

import java.util.HashMap;
import java.util.Map;

public class Prob2 {
	//This is the LZW Trie
	//This problem is expansion so actually use HashMap
	
	private final int R=128; // 7-bit ASCII
	
	private Map<Integer, String> ST = new HashMap<>();
	
	Prob2(){
		for (int i=0; i<R; i++){
			ST.put(i, String.format("%s", (char) i));
		}
	}
	
	
	

	public static void main(String[] args) {
		String input = "41 41 43 81 42 43 42 84 84 43 80";
		String[] inputs = input.trim().split("\\s+");
		int[] codePoints = new int[inputs.length];
		for (int i=0; i<codePoints.length; i++){
			codePoints[i]=Integer.parseInt(inputs[i], 16);
		}
		
		Prob2 test = new Prob2();
		
		int codePointIdx = test.R+1;
		String prevStr = test.ST.get(codePoints[0]);
		StringBuilder sb = new StringBuilder(prevStr);
		for (int i=1; i<codePoints.length; i++){
			if (codePointIdx==codePoints[i]) {
				test.ST.put(codePointIdx, prevStr+prevStr.charAt(0));
			}
			
			if (codePoints[i]==128){
				break;
			}
			
			String currentStr = test.ST.get(codePoints[i]);
			sb.append(currentStr);
			test.ST.put(codePointIdx, prevStr+currentStr.charAt(0));
			prevStr=currentStr;
			codePointIdx++;
		}
		String result = sb.toString();
		// pad space
		sb = new StringBuilder();
		for (int i=0; i<result.length();i++){
			sb.append(result.charAt(i)+" ");
		}
		System.out.println(sb.toString().trim());
		for (int i=129; i<=test.ST.size(); i++)
			System.out.println(String.format("%X\t%s", i, test.ST.get(i)));
		

	}

}
