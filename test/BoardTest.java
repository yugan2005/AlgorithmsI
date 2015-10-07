import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {
	private Board board31, board32;
	private int[][] block31, block32;
	
	@Before
	public void setUp(){
		block31 = new int[3][3];
		block32 = new int[3][3];
		
		block31[0]=new int[]{8,1,3};
		block31[1]=new int[]{4,0,2};
		block31[2]=new int[]{7,6,5};
		
		block32[0]=new int[]{0,1,3};
		block32[1]=new int[]{4,2,5};
		block32[2]=new int[]{7,8,6};
		
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
	public void testTwin() {
		fail("Not yet implemented");
	}

	@Test
	public void testNeighbors() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testEquals() {
		Board board33 = new Board(block31);
		assertThat(board31.equals(board33), is(true));
		assertThat(board31.equals(board32), is(false));
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

}
