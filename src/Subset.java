import edu.princeton.cs.algs4.StdIn;

public class Subset {

	public static void main(String[] args) {
		int k = Integer.parseInt(args[0]);
		RandomizedQueue<String> myRandomQueue = new RandomizedQueue<>();

		while (!StdIn.isEmpty()) {
			myRandomQueue.enqueue(StdIn.readString());
		}

		for (int counter = 0; counter < k; counter++) {
			System.out.println(myRandomQueue.dequeue());
		}

	}

}
