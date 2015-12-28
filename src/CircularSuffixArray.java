
public class CircularSuffixArray {
	private String s;
	// in the original suffix array, the jth char in the ith permutation is
	// s.charAt((i+j)%s.length())
	private final static int R = 256;
	private int[] sortedSubarray;
	private static final int CUTOFF = 10; // cutoff to insertion sort

	private void lsdSort() {
		// LSD Sort acutally is too slow
		for (int i = s.length() - 1; i >= 0; i--) {
			lsdSort(i);
		}
	}

	private void lsdSort(int idx) {
		int[] count = new int[R + 1];
		for (int i = 0; i < sortedSubarray.length; i++) {
			count[s.charAt((sortedSubarray[i] + idx) % s.length()) + 1] += 1;
			// Note of the plus 1 because the count array is starting at 1
		}
		for (int i = 1; i < count.length; i++) {
			count[i] = count[i] + count[i - 1]; // cumulative
		}
		int[] newOrder = new int[sortedSubarray.length];
		for (int i = 0; i < newOrder.length; i++) {
			int newPosition = count[s.charAt((sortedSubarray[i] + idx) % s.length())];
			newOrder[newPosition] = sortedSubarray[i];// this is a tricky step
			count[s.charAt((sortedSubarray[i] + idx) % s.length())] += 1;
		}
		sortedSubarray = newOrder;
	}

	private void msdSort() {
		int[] newOrder = new int[sortedSubarray.length];
		// carry around this array to recycle it and save space
		msdSort(0, sortedSubarray.length, 0, newOrder);
	}

	private void msdSort(int start, int end, int idx, int[] newOrder) {
		// exit condition
		if (idx == s.length())
			return;

		if (end - start <= CUTOFF) {
			insertionSort(start, end, idx);
			return;
		}

		int[] count = new int[R + 2];
		// MSD need shift 2 poisitions. This is different than LSD
		for (int i = start; i < end; i++) {
			count[s.charAt((sortedSubarray[i] + idx) % s.length()) + 2] += 1;
			// after this, the count is like [0, 0, 2, 5, 8,...]
		}
		for (int i = 2; i < count.length; i++) {
			count[i] = count[i] + count[i - 1];
			// after this, the count is like [0, 0, 2, 7, 15,...]
		}
		for (int i = start; i < end; i++) {
			int newPoistion = count[s.charAt((sortedSubarray[i] + idx) % s.length()) + 1] + start;
			newOrder[newPoistion] = sortedSubarray[i];
			count[s.charAt((sortedSubarray[i] + idx) % s.length()) + 1] += 1;
			// after this, the count is like [0, 2, 7, 15,...]
		}

		for (int i = start; i < end; i++) {
			sortedSubarray[i] = newOrder[i];
		}

		// recursive msd sort the grouped sub arrays
		for (int i = 0; i < count.length - 2; i++) {
			msdSort(count[i] + start, count[i + 1] + start, idx + 1, newOrder);
		}

	}

	private void insertionSort(int start, int end, int idx) {
		for (int i=start; i<end; i++){
			for (int j=i+1; j<end; j++){
				if (less(idx, j, i)){
					int temp = sortedSubarray[j];
					sortedSubarray[j]=sortedSubarray[i];
					sortedSubarray[i]=temp;
				}
			}
		}

	}

	private boolean less(int idx, int a, int b) {
		for (int i = idx; i < s.length(); i++) {
			char charOfA = s.charAt((sortedSubarray[a] + i) % s.length());
			char charOfB = s.charAt((sortedSubarray[b] + i) % s.length());
			if (charOfA < charOfB)
				return true;
			else if (charOfA > charOfB)
				return false;
		}
		return false;
	}

	public CircularSuffixArray(String s) {
		// circular suffix array of s
		if (s == null) {
			throw new NullPointerException();
		}
		this.s = s;
		sortedSubarray = new int[s.length()];
		for (int i = 0; i < sortedSubarray.length; i++) {
			sortedSubarray[i] = i;
		}
		msdSort();
	}

	public int length() {
		// length of s
		return this.s.length();
	}

	public int index(int i) {
		// returns index of ith sorted suffix
		if (i >= s.length())
			throw new IndexOutOfBoundsException();

		return sortedSubarray[i];
	}

	public static void main(String[] args) {
		// unit testing of the methods (optional)
		String s = "ABRACADABRA!";
		CircularSuffixArray test = new CircularSuffixArray(s);
		for (int i = 0; i < s.length(); i++) {

			System.out.println(test.index(i));
		}
	}
}
