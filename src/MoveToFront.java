//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.PrintStream;
import java.util.Iterator;
import java.util.LinkedList;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
	private static final int R = 256;


	// apply move-to-front encoding, reading from standard input and writing to
	// standard output
	public static void encode(){
		LinkedList<Character> codeTable = new LinkedList<>();
		for (int i=0; i<R; i++){
			codeTable.add((char) i);
		}
		
		while (!BinaryStdIn.isEmpty()){
			char inputChar = BinaryStdIn.readChar(8);
			Iterator<Character> iterator = codeTable.iterator();
			int idx=0;
			while (iterator.hasNext() && iterator.next()!=inputChar) idx++;
			iterator.remove();
			codeTable.addFirst(inputChar);
			BinaryStdOut.write(idx, 8);
		}
		BinaryStdOut.flush();
		
	}

    // apply move-to-front decoding, reading from standard input and writing to standard output
	public static void decode(){
		LinkedList<Character> codeTable = new LinkedList<>();
		for (int i=0; i<R; i++){
			codeTable.add((char) i);
		}
		StringBuilder sb = new StringBuilder();
		while (!BinaryStdIn.isEmpty()){
			int idx = BinaryStdIn.readInt(8);
			char outputChar = codeTable.remove(idx);
			codeTable.addFirst(outputChar);
			sb.append(outputChar);
		}
		System.out.println(sb.toString());
	}
	
//	private static void hexDump(){
//		InputStream in = System.in;
//		int input;
//		StringBuilder sb = new StringBuilder();
//		try {
//			while ((input = in.read()) != -1){
//				sb.append(String.format("%02X", input)+" ");
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println(sb.toString().trim());
//        
//    }
	

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
	public static void main(String[] args){
		if (args[0].equals("-")){
			encode();
		}
		else if (args[0].equals("+")) {
			decode();
		}
		
//		// reset the system in and out for unix piping
//		PrintStream stdout = System.out;
//		File tempFile = new File("temp.txt");
//		
//		if (args[0].equals("-")) {
//
//			
//			try (InputStream input = new BufferedInputStream(new FileInputStream(new File(args[1])));
//					PrintStream output = new PrintStream(new FileOutputStream(tempFile))) {
//				System.setIn(input);
//				System.setOut(output);
//					encode();
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			
//			System.setOut(stdout);
//			
//			
//			try(InputStream in = new BufferedInputStream(new FileInputStream(tempFile))) {
//				System.setIn(in);
//				hexDump();
//				
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		else if (args[0].equals("+")) {
//			try(InputStream in = new BufferedInputStream(new FileInputStream(tempFile))) {
//				System.setIn(in);
//				decode();
//				
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}

	}

}
