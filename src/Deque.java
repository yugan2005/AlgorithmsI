import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	private Item[] dequeArray;
	private int head, tail;
	private int size;
	private final int initialSize = 5;

	@SuppressWarnings("unchecked")
	public Deque() {
		// construct an empty deque
		size = 0;
		dequeArray = (Item[]) new Object[initialSize];
		// Here is tricky. What I used is:
		// When enqueue: add to the queue first, then move the pointer
		// When dequeue: move the pointer first, the take the object out.
		// We have to initialize head and tail in the following way
		// Also another pitfall is when resizing the array, need put head and
		// tail pointer carefully
		// Also because the way that I put the pointer, the array cannot be
		// full, It needs at least 3 space empty at any time.
		// Also the iterator need be consistent!
		head = 0;
		tail = 1;
		size = 0;
	}

	public boolean isEmpty() {
		// is the deque empty?
		return size == 0;
	}

	public int size() {
		// return the number of items on the deque
		return size;
	}

	public void addFirst(Item item) {
		if (item == null)
			throw new NullPointerException(
					"Null item can not be added to the deque");
		// add the item to the front
		if (size == dequeArray.length-3) {
			changeArraySize(2 * dequeArray.length);
		}
		size++;
		dequeArray[head] = item;
		head = (head + dequeArray.length - 1) % dequeArray.length;
	}

	public void addLast(Item item) {
		// add the item to the end
		if (item == null)
			throw new NullPointerException(
					"Input value is NULL, and not acceptable");
		if (size == dequeArray.length-3) {
			changeArraySize(2 * dequeArray.length);
		}
		size++;
		dequeArray[tail] = item;
		tail = (tail + 1) % dequeArray.length;
	}

	public Item removeFirst() {
		// remove and return the item from the front
		if (size == 0)
			throw new NoSuchElementException(
					"Deque is empty, not remove any more element");
		if (size == (dequeArray.length / 4) && size > initialSize) {
			changeArraySize(dequeArray.length/2);
		}
		size--;
		head = (head + 1) % dequeArray.length;
		Item returnItem = dequeArray[head];
		dequeArray[head] = null;
		return returnItem;
	}

	public Item removeLast() {
		if (size == 0)
			throw new NoSuchElementException(
					"Deque is empty, not remove any more element");
		// remove and return the item from the end
		if (size == (dequeArray.length / 4) && size > initialSize) {
			changeArraySize(dequeArray.length/2);
		}
		size--;
		tail = (tail + dequeArray.length - 1) % dequeArray.length;
		Item returnItem = dequeArray[tail];
		dequeArray[tail] = null;
		return returnItem;
	}

	@Override
	public Iterator<Item> iterator() {
		return new DequeIterator();
	}

	private class DequeIterator implements Iterator<Item> {
		int iteratorHead = head;
		int iteratorTail = tail;
		int iteratorSize = size;

		@Override
		public boolean hasNext() {
			return iteratorSize != 0;
		}

		@Override
		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException("Deque is empty");
			iteratorHead = (iteratorHead + 1) % dequeArray.length;
			iteratorSize--;
			Item returnItem = dequeArray[iteratorHead];
			return returnItem;
		}

		public void remove() {
			throw new UnsupportedOperationException(
					"Remove method is not supported for Iterator");
		}

	}

	private void changeArraySize(int changedSize) {
		@SuppressWarnings("unchecked")
		Item[] newArray = (Item[]) new Object[changedSize];
		for (int i = 0; i < size; i++) {
			// leave the first of the array empty for the head pointer
			newArray[i + 1] = dequeArray[(head + i +1) % dequeArray.length];
		}
		head = 0;
		// put the tail pointer one after the last element
		tail = size + 1;
		dequeArray = newArray;
	}

	public static void main(String[] args) {
		Deque<Integer> deque = new Deque<Integer>();
		// deque.addLast(0);
		// System.out.println(deque.removeLast());
		// deque.addFirst(5);
		// System.out.println(deque.removeLast());

		// deque.addFirst(0);
		// System.out.println(deque.isEmpty());
		// deque.addFirst(2);
		// System.out.println(deque.removeLast());

		System.out.println(deque.isEmpty());
		deque.addFirst(1);
		System.out.println(deque.isEmpty());
		deque.addFirst(3);
		deque.addFirst(4);
		deque.addFirst(5);
		System.out.println(deque.isEmpty());
		deque.addFirst(7);
		deque.addFirst(8);
		System.out.println(deque.removeLast());
	}

}