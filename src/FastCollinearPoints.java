import java.util.Comparator;

import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.ResizingArrayQueue;

public class FastCollinearPoints {
	
	private ResizingArrayQueue<LineSegment> lineSegmentQueue;
	private int segmentCounter = 0;

	public FastCollinearPoints(Point[] points) {
		// finds all line segments containing 4 or more points
		for (Point point : points) {
			if (point == null)
				throw new NullPointerException("Points cannot be null!");
		}

		int numOfPoints = points.length;

		Quick.sort(points);
		for (int i = 0; i < numOfPoints - 1; i++) {
			if (points[i] == points[i + 1])
				throw new IllegalArgumentException("No duplicated points are allowed!");
		}

		lineSegmentQueue = new ResizingArrayQueue<>();
		
		for (int i=0; i<numOfPoints; i++){
			Point point = points[i];
			Comparator<Point> slopeComparator = point.slopeOrder();
			Point[] otherPoints = new Point[numOfPoints-1];
			for (int j=0; j<i; j++){
				otherPoints[j]=points[i];
			}
			for (int j=i; j<numOfPoints-2; j++){
				otherPoints[j]=points[i+1];
			}
			
			
		}
		
		
	}

	public int numberOfSegments() {
		// the number of line segments
		return 0;
	}

	public LineSegment[] segments() {
		// the line segments
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
