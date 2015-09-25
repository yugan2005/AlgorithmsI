import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	private Item[] dequeArray;
	private int head, tail;
	private int size;
	private final int initialSize = 10;
	
	@SuppressWarnings("unchecked")
	public Deque(){
		// construct an empty deque
		size = 0;
		dequeArray = (Item[]) new Object[initialSize];
		head =0;
		tail =0;
		size = 0;
	}
	
	public boolean isEmpty() {
		// is the deque empty?
		return size==0;
	}
	
	public int size() {
		// return the number of items on the deque
		return size;
	}
	
	public void addFirst(Item item) {
		// add the item to the front
		if (size==dequeArray.length) {
			changeArraySize(2*size);
		}
		size++;
		dequeArray[(head+dequeArray.length-1)%dequeArray.length]=item;
		head = (head+dequeArray.length-1)%dequeArray.length;
	}
	
	private void changeArraySize(int changedSize) {
		@SuppressWarnings("unchecked")
		Item[] newArray = (Item[]) new Object[changedSize];
		for (int i=0; i<size; i++){
			newArray[i] = dequeArray[(head+i)%dequeArray.length];
		}
		head = 0;
		tail = size-1;
		dequeArray=newArray;
	}

	public void addLast(Item item){
		// add the item to the end
		if (item==null) throw new NullPointerException("Input value is NULL, and not acceptable");
		if (size==dequeArray.length) {
			changeArraySize(2*size);
		}
		size++;
		dequeArray[(tail+1)%dequeArray.length]=item;
		tail = (tail+1)%dequeArray.length;
	}
	
	public Item removeFirst(){
		// remove and return the item from the front
		if (size<=(dequeArray.length/4)) {
			changeArraySize(2*size);
		}
		size--;
		Item returnItem = dequeArray[head];
		dequeArray[head] = null;
		head = (head+1)%dequeArray.length;
		return returnItem;
	}
	
	public Item removeLast(){
		// remove and return the item from the end
		if (size<=(dequeArray.length/4)) {
			changeArraySize(2*size);
		}
		size--;
		Item returnItem = dequeArray[tail];
		dequeArray[tail] = null;
		tail = (tail+dequeArray.length-1)%dequeArray.length;
		return returnItem;
	}
	
	@Override
	public Iterator<Item> iterator() {
		return new DequeIterator();
	}
	
	class DequeIterator implements Iterator<Item> {
		int iteratorHead = head;
		int iteratorTail = tail;

		@Override
		public boolean hasNext() {			
			return iteratorHead!=iteratorTail;
		}

		@Override
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException("Deque is empty");
			Item returnItem = dequeArray[iteratorHead];
			iteratorHead = (iteratorHead+1)%dequeArray.length;
			return returnItem;
		}
		
		public void remove(){
			throw new UnsupportedOperationException("Remove method is not supported for Iterator");
		}
		
	}
	
	public static void main(String[] args){
		// unit testing
	}


}
