import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
		}
		try (InputStream input = new FileInputStream(new File("C:\\Users\\YG\\git\\AlgorithmsI\\src\\textfilesForTest\\abra.txt"))) {
			InputStream in = new BufferedInputStream(input);
			byte[] bufferedArray = new byte[1];
			while (in.read(bufferedArray)!=-1){
				char inputChar = (char) bufferedArray[0];
				Iterator<Character> iterator = codeTable.iterator();
				int idx=0;
				while (iterator.hasNext() && iterator.next()!=inputChar) idx++;
				iterator.remove();
				codeTable.addFirst(inputChar);
				System.out.println(String.format("%X ", idx));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

    // apply move-to-front decoding, reading from standard input and writing to standard output
	public static void decode(){
		
	}

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
	public static void main(String[] args){
//		if (args[0].equals("-")) {
//			encode();
//		}
		encode();
	}

}
