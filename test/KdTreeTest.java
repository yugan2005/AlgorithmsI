import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTreeTest {
	Point2D p1, p2, p3, p4, p5, p6;
	KdTree testTree;

	@Before
	public void init() {
		p1 = new Point2D(.2, .3);
		p2 = new Point2D(.4, .2);
		p3 = new Point2D(.4, .5);
		p4 = new Point2D(.3, .3);
		p5 = new Point2D(.1, .5);
		p6 = new Point2D(.4, .4);

		testTree = new KdTree();
		testTree.insert(p1);
		testTree.insert(p2);
		testTree.insert(p3);
		testTree.insert(p4);
		testTree.insert(p5);
		testTree.insert(p6);

	}

//	@Test
//	public void insertPointsShowCorrectLevels() {
//
//		assertThat(testTree.getLevel(p1), equalTo(0));
//		assertThat(testTree.getLevel(p2), equalTo(1));
//		assertThat(testTree.getLevel(p3), equalTo(2));
//		assertThat(testTree.getLevel(p4), equalTo(3));
//		assertThat(testTree.getLevel(p5), equalTo(1));
//		assertThat(testTree.getLevel(p6), equalTo(3));
//
//	}

	@Test
	public void findPointsUsingRange() {

		RectHV testRect = new RectHV(.1, .25, .35, .35);
		Iterable<Point2D> result = testTree.range(testRect);
		List<Point2D> findResult = new ArrayList<>();
		for (Point2D p : result)
			findResult.add(p);

		List<Point2D> actualResult = new ArrayList<>();
		actualResult.add(p1);
		actualResult.add(p4);

		assertThat(actualResult, containsInAnyOrder(findResult.toArray()));
		// The containsInAnyOrder is what I need use
		// The second better be a Array!
	}

	@Test
	public void findTheNearestPoint() {
		Point2D testPoint = new Point2D(0.29, 0.29);
		Point2D nearestPoint = testTree.nearest(testPoint);

		assertThat(nearestPoint, equalTo(p4));

	}

}
