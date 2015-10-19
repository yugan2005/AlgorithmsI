import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.Point2D;


public class KdTreeTest {
	Point2D p1, p2, p3, p4, p5, p6;
	KdTree testTree;
	
	@Before
	public void init(){
		p1 = new Point2D(.2, .3);
		p2 = new Point2D(.4, .2);
		p3 = new Point2D(.4, .5);
		p4 = new Point2D(.3, .3);
		p5 = new Point2D(.1, .5);
		p6 = new Point2D(.4, .4);
		
		testTree = new KdTree();


	}

	@Test
	public void insertPointsShowCorrectLevels() {
		testTree.insert(p1);
		testTree.insert(p2);
		testTree.insert(p3);
		testTree.insert(p4);
		testTree.insert(p5);
		testTree.insert(p6);
		
		testTree.draw();
		
		assertThat(testTree.getLevel(p1), equalTo(0));
		assertThat(testTree.getLevel(p2), equalTo(1));
		assertThat(testTree.getLevel(p3), equalTo(2));
		assertThat(testTree.getLevel(p4), equalTo(3));
		assertThat(testTree.getLevel(p5), equalTo(1));
		assertThat(testTree.getLevel(p6), equalTo(3));

	}

}
