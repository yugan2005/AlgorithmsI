import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.Queue;

public class BurrowsWheeler {
	// apply Burrows-Wheeler encoding, reading from standard input and writing
	// to standard output
	public static void encode() {
		StringBuilder sb = new StringBuilder();
		while (!BinaryStdIn.isEmpty()) {
			sb.append((char) BinaryStdIn.readChar(8));
		}
		String inputStr = sb.toString();
		CircularSuffixArray csa = new CircularSuffixArray(inputStr);
		int first = -1;
		char[] lastColumn = new char[inputStr.length()];

		for (int i = 0; i < csa.length(); i++) {
			lastColumn[i] = inputStr.charAt((csa.index(i) + inputStr.length() - 1) % inputStr.length());
			if (csa.index(i) == 0)
				first = i;
		}
		BinaryStdOut.write(first, 32);
		for (int i = 0; i < lastColumn.length; i++) {
			BinaryStdOut.write(lastColumn[i], 8);
		}
		BinaryStdOut.flush();

	}

	// apply Burrows-Wheeler decoding, reading from standard input and writing
	// to standard output
	public static void decode() {
		int first = BinaryStdIn.readInt();
		ArrayList<Character> lastColumn = new ArrayList<>();
		while (!BinaryStdIn.isEmpty()) {
			lastColumn.add(BinaryStdIn.readChar(8));
		}
		int[] next = new int[lastColumn.size()];
		ArrayList<Character> firstColumn = new ArrayList<>(lastColumn);
		Collections.sort(firstColumn);

		Map<Character, Queue<Integer>> lastColumnMapQueue = new HashMap<>();
		for (int i = 0; i < lastColumn.size(); i++) {
			if (lastColumnMapQueue.get(lastColumn.get(i)) == null) {
				Queue<Integer> idxQueue = new Queue<>();
				lastColumnMapQueue.put(lastColumn.get(i), idxQueue);
			}
			Queue<Integer> idxQueue = lastColumnMapQueue.get(lastColumn.get(i));
			idxQueue.enqueue(i);
		}

		for (int i = 0; i < firstColumn.size(); i++) {
			char headChar = firstColumn.get(i);
			next[i] = lastColumnMapQueue.get(headChar).dequeue();
		}

		int idx = first;
		for (int i = 0; i < firstColumn.size(); i++) {
			BinaryStdOut.write(firstColumn.get(idx));
			idx = next[idx];
		}
		BinaryStdOut.flush();

	}

//	private static void hexDump() {
//		InputStream in = System.in;
//		int input;
//		StringBuilder sb = new StringBuilder();
//		try {
//			while ((input = in.read()) != -1) {
//				sb.append(String.format("%02X", input) + " ");
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println(sb.toString().trim());
//
//	}

	// if args[0] is '-', apply Burrows-Wheeler encoding
	// if args[0] is '+', apply Burrows-Wheeler decoding
	public static void main(String[] args) {
		
		if (args[0].equals("-")){
			encode();
		}
		else if (args[0].equals("+")) {
			decode();
		}
		
//		File temp = new File("temp_BurrowsWheeler.txt");
//		PrintStream stdOut = System.out;
//
//		if (args[0].equals("-")) {
//			String fileName = args[1];
//			File file = new File(fileName);
//			try (InputStream in = new FileInputStream(file);
//					PrintStream out = new PrintStream(new FileOutputStream(temp))) {
//				System.setIn(in);
//				System.setOut(out);
//				encode();
//
//			} catch (FileNotFoundException e1) {
//				e1.printStackTrace();
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//
//			try (InputStream in = new FileInputStream(temp)) {
//				System.setIn(new FileInputStream(temp));
//				System.setOut(stdOut);
//				hexDump();
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		} else if (args[0].equals("+")) {
//			try (InputStream in = new FileInputStream(temp)) {
//				System.setIn(new FileInputStream(temp));
//				System.setOut(stdOut);
//				decode();
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}

	}
}
