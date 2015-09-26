import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private int size;
	private Item[] queueArray;
	private final int INITIAL_SIZE = 10;

	@SuppressWarnings("unchecked")
	public RandomizedQueue() {
		// construct an empty randomized queue
		size = 0;
		queueArray = (Item[]) new Object[INITIAL_SIZE];
	}

	public boolean isEmpty() {
		// is the queue empty?
		return size == 0;
	}

	public int size() {
		// return the number of items on the queue
		return size;
	}

	public void enqueue(Item item) {
		// add the item
		if (item == null)
			throw new NullPointerException("Cannot add null item");
		if (size == queueArray.length)
			resize(2 * size);
		queueArray[size] = item;
		size++;
	}

	public Item dequeue() {
		// remove and return a random item
		if (size == 0)
			throw new NoSuchElementException("No more item to be dequeued");
		if ((size <= queueArray.length / 4) && size > INITIAL_SIZE)
			resize(queueArray.length / 4);
		int randomIdx = StdRandom.uniform(size);
		Item returnItem = queueArray[randomIdx];
		queueArray[randomIdx] = queueArray[size - 1];
		size--;
		return returnItem;
	}

	public Item sample() {
		// return (but do not remove) a random item
		if (size == 0)
			throw new NoSuchElementException("No more item to be sampled");

		int randomIdx = StdRandom.uniform(size);
		return queueArray[randomIdx];
	}

	private void resize(int newSize) {
		@SuppressWarnings("unchecked")
		Item[] newArray = (Item[]) new Object[newSize];

		for (int i = 0; i < size; i++) {
			newArray[i] = queueArray[i];
		}

		queueArray = newArray;
	}

	@Override
	public Iterator<Item> iterator() {
		// TODO Auto-generated method stub
		return new randomizedQueueIterator();
	}

	private class randomizedQueueIterator implements Iterator<Item> {
		int[] randomIdxes = new int[size];
		int idx = 0;

		private randomizedQueueIterator() {
			StdRandom.shuffle(randomIdxes);
		}

		@Override
		public boolean hasNext() {
			return idx != size;
		}

		@Override
		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException("No more elements left");
			Item returnItem = queueArray[randomIdxes[idx]];
			idx++;
			return returnItem;
		}

		public void remove() {
			throw new UnsupportedOperationException("remove is not supported");
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
