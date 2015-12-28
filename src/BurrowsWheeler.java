import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

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

	}

	private static void hexDump() {
		InputStream in = System.in;
		int input;
		StringBuilder sb = new StringBuilder();
		try {
			while ((input = in.read()) != -1) {
				sb.append(String.format("%02X", input) + " ");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(sb.toString().trim());

	}

	// if args[0] is '-', apply Burrows-Wheeler encoding
	// if args[0] is '+', apply Burrows-Wheeler decoding
	public static void main(String[] args) {
		String fileName = args[1];
		File file = new File(fileName);
		File temp = new File("temp_BurrowsWheeler.txt");
		PrintStream stdOut = System.out;

		try (InputStream in = new FileInputStream(file);
				PrintStream out = new PrintStream(new FileOutputStream(temp))) {
			System.setIn(in);
			System.setOut(out);
			if (args[0].equals("-")) {
				encode();
			}

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try (InputStream in = new FileInputStream(temp)) {
			System.setIn(new FileInputStream(temp));
			System.setOut(stdOut);
			hexDump();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
