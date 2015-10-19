import java.util.TreeSet;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
	private TreeSet<Point2D> treeSet;

	public PointSET() {
		// construct an empty set of points
		treeSet = new TreeSet<Point2D>();
	}

	public boolean isEmpty() {
		// is the set empty?
		return treeSet.isEmpty();
	}

	public int size() {
		// number of points in the set
		return treeSet.size();
	}

	public void insert(Point2D p) {
		// add the point to the set (if it is not already in the set)
		if (p==null) throw new NullPointerException("The input to insert method is null!");
		treeSet.add(p);
	}

	public boolean contains(Point2D p) {
		// does the set contain point p?
		if (p==null) throw new NullPointerException("The input to contains method is null!");
		return treeSet.contains(p);
	}

	public void draw() {
		// draw all points to standard draw
		for (Point2D p:treeSet) p.draw();
	}

	public Iterable<Point2D> range(RectHV rect) {
		// all points that are inside the rectangle
		if (rect==null) throw new NullPointerException("The input to range method is null!");
		Bag<Point2D> result = new Bag<Point2D>();
		for (Point2D p:treeSet) {
			if (rect.contains(p)) result.add(p);
		}
		return result;
	}

	public Point2D nearest(Point2D p) {
		// a nearest neighbor in the set to point p; null if the set is empty
		if (p==null) throw new NullPointerException("The input to nearest method is null!");
		
		Point2D nearestPoint = null;
		double minDistance = Double.MAX_VALUE;
		for (Point2D q:treeSet){
			if (p.distanceSquaredTo(q)<minDistance) {
				minDistance = p.distanceSquaredTo(q);
				nearestPoint = q;
			}
		}
		return nearestPoint;
	}

	public static void main(String[] args) {
		// unit testing of the methods (optional)
	}

}
