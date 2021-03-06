import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {
	private Board board31, board32;
	private int[][] block31, block32;

	@Before
	public void setUp() {
		block31 = new int[3][3];
		block32 = new int[3][3];

		block31[0] = new int[] { 8, 1, 3 };
		block31[1] = new int[] { 4, 0, 2 };
		block31[2] = new int[] { 7, 6, 5 };

		block32[0] = new int[] { 0, 1, 3 };
		block32[1] = new int[] { 4, 2, 5 };
		block32[2] = new int[] { 7, 8, 6 };

		board31 = new Board(block31);
		board32 = new Board(block32);

	}

	@Test
	public void testHamming() {
		assertThat(board31.hamming(), equalTo(5));
	}

	@Test
	public void testManhattan() {
		assertThat(board31.manhattan(), equalTo(10));
		assertThat(board32.manhattan(), equalTo(4));
	}

	@Test
	public void testNeighbors() {
		int[][] block31NeighborsByHand = new int[3][3];
		ArrayList<Board> board31NeighborsByHand = new ArrayList<>();

		block31NeighborsByHand[0] = new int[] { 8, 0, 3 };
		block31NeighborsByHand[1] = new int[] { 4, 1, 2 };
		block31NeighborsByHand[2] = new int[] { 7, 6, 5 };
		board31NeighborsByHand.add(new Board(block31NeighborsByHand));

		block31NeighborsByHand[0] = new int[] { 8, 1, 3 };
		block31NeighborsByHand[1] = new int[] { 0, 4, 2 };
		block31NeighborsByHand[2] = new int[] { 7, 6, 5 };
		board31NeighborsByHand.add(new Board(block31NeighborsByHand));

		block31NeighborsByHand[0] = new int[] { 8, 1, 3 };
		block31NeighborsByHand[1] = new int[] { 4, 6, 2 };
		block31NeighborsByHand[2] = new int[] { 7, 0, 5 };
		board31NeighborsByHand.add(new Board(block31NeighborsByHand));

		block31NeighborsByHand[0] = new int[] { 8, 1, 3 };
		block31NeighborsByHand[1] = new int[] { 4, 2, 0 };
		block31NeighborsByHand[2] = new int[] { 7, 6, 5 };
		board31NeighborsByHand.add(new Board(block31NeighborsByHand));

		Iterable<Board> neighborsBoard31 = board31.neighbors();

		// This is the traditional JUnit test method
		// for (Board eachNeighborBoard:neighborsBoard31){
		// assertTrue("Not contain",
		// board31NeighborsByHand.contains(eachNeighborBoard));
		// }

		// This is the advanced one, but remember it only works for Array. Need
		// Convert.
		assertThat(neighborsBoard31,
				containsInAnyOrder(board31NeighborsByHand.toArray()));
	}

	@Test
	public void testEquals() {
		Board board33 = new Board(block31);
		assertThat(board31.equals(board33), is(true));
		assertThat(board31.equals(board32), is(false));
	}

	@Test
	public void testToString() {
		StringBuilder boardStrBuilder = new StringBuilder();
		boardStrBuilder.append("3");
		boardStrBuilder.append(System.lineSeparator());
		boardStrBuilder.append(" 8 1 3");
		boardStrBuilder.append(System.lineSeparator());
		boardStrBuilder.append(" 4 0 2");
		boardStrBuilder.append(System.lineSeparator());
		boardStrBuilder.append(" 7 6 5");
		boardStrBuilder.append(System.lineSeparator());
		String board31StringByHand = boardStrBuilder.toString();
		
		String board31String = board31.toString();
		
		assertThat(board31String, equalTo(board31StringByHand));
	}

}
