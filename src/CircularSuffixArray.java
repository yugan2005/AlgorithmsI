
public class CircularSuffixArray {
	private String s;
	// in the original suffix array, the jth char in the ith permutation is s.charAt((i+j)%s.length())
	private final static int R=256;
	private int[] sortedSubarray;
	
	private int[] lsdSort(int[] prevOrder, int idx){
		int[] count = new int[R+1];
		for (int i=0; i<prevOrder.length; i++){
			count[s.charAt((prevOrder[i]+idx)%s.length())+1] += 1; // Note of the plus 1 because the count array is starting at 1
		}
		
		for (int i=1; i<count.length; i++){
			count[i] = count[i]+count[i-1]; // cumulative
		}
		
		int[] lsdSortingOrder = new int[prevOrder.length];
		int[] currentOrder = new int[prevOrder.length];

		for (int i=0; i<lsdSortingOrder.length; i++){
			lsdSortingOrder[i] = count[s.charAt((prevOrder[i]+idx)%s.length())];
			currentOrder[lsdSortingOrder[i]] = prevOrder[i]; // this is a tricky step
			count[s.charAt((prevOrder[i]+idx)%s.length())] += 1;
		}
		return currentOrder;
	}
	
	
	public CircularSuffixArray(String s){
		// circular suffix array of s
		if (s==null || s.length()==0) {
			throw new NullPointerException();
		}
		this.s=s;
		int[] prevOrder = new int[s.length()];
		for (int i=0; i<prevOrder.length; i++){
			prevOrder[i]=i;
		}
		
		for (int i=this.s.length()-1; i>=0; i--){
			int[] lsdSortedOrder = lsdSort(prevOrder, i);
			prevOrder = lsdSortedOrder;
		}
		this.sortedSubarray = prevOrder;
		
		
	}

	public int length(){
		// length of s
		return this.s.length();
	}

	public int index(int i){
		// returns index of ith sorted suffix
		if (i>=s.length()) throw new IndexOutOfBoundsException();
		
		return sortedSubarray[i];
	}

	public static void main(String[] args){
		// unit testing of the methods (optional)
		String s = "ABRACADABRA!";
		CircularSuffixArray test = new CircularSuffixArray(s);
		for (int i=0; i<s.length(); i++){
			
			System.out.println(test.index(i));
		}
	}
}
