package Quiz5BQuiz2;
// This is Boyer-Moore

import java.util.ArrayList;
import java.util.List;

public class Prob2 {
	public static final int R = 256;

	public static int[] buildJumpTable(String pattern) {
		int l = pattern.length();
		int[] result = new int[R];
		for (int i = 0; i < R; i++) {
			result[i] = -1;
		}
		for (int i = 0; i < l; i++) {
			result[pattern.charAt(i)] = i;
		}
		return result;
	}

	public static void main(String[] args) {
		String pattern = "S S B U S I N";
		pattern = String.join("", pattern.trim().split("\\s+"));
		int[] jumpTable = buildJumpTable(pattern);

		List<Character> compared = new ArrayList<>();

		String input = String.join("",
				"H E W A S S I N K I N G A A A B U S I N E S S B U S I N E S".trim().split("\\s+"));
		for (int i = 0; i < input.length() - pattern.length();) {
			boolean matched = true;
			for (int j = pattern.length() - 1; j >= 0; j--) {
				// current character in input is i+j, and in pattern is j
				if (j == pattern.length() - 1)
					compared.add(input.charAt(i + j));
				if (pattern.charAt(j) != input.charAt(i + j)) {
					matched = false;
					if (jumpTable[input.charAt(i + j)] == -1) {
						// jump to one position after the current position
						i = i + j + 1;
					} else {
						// move 1 or jump to the rightmost position
						// jump to rightmost position means after alignment, {i (head in the input after alignment) + jumpTable[]} is
						// current position { i (current head) + j}
						// so new_i = i+j-jumpTable[]
						i = Math.max(i + 1, i + j - jumpTable[input.charAt(i+j)]);
					}
					break;
				}

			}
			if (matched)
				break;
		}

		StringBuilder result = new StringBuilder();
		for (char c : compared) {
			result.append(c + " ");
		}

		System.out.println(result.toString().trim());

	}

}
