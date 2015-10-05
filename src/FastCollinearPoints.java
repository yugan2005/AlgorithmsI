import java.util.Comparator;

import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.ResizingArrayQueue;
import edu.princeton.cs.algs4.ST;

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
		ST<Double,ResizingArrayQueue<Point>> tableOfSlope = new ST<>();
		
		for (int i=0; i<numOfPoints-3; i++){
			Point point = points[i];
			Comparator<Point> slopeComparator = point.slopeOrder();
			int numOtherPoints = numOfPoints-i-1;
			Point[] otherPoints = new Point[numOtherPoints];
			for (int j=0; j<numOtherPoints; j++){
				otherPoints[j]=points[i+j+1];
			}
			MergeX.sort(otherPoints, slopeComparator);
			int pointCounter = 1;
			double slope = point.slopeTo(otherPoints[0]);
			for (int j=1; j<numOtherPoints; j++){
				if (slope == point.slopeTo(otherPoints[j])) {
					pointCounter++;
				}
				else {
					if (pointCounter>=4) {
						if (tableOfSlope.get(slope)==null) {
							lineSegmentQueue.enqueue(new LineSegment(point, otherPoints[j-1]));
							ResizingArrayQueue<Point> pointWithThisSlope = new ResizingArrayQueue<>();
							pointWithThisSlope.enqueue(point);
							for (int k=j-pointCounter; k<j; k++){
								pointWithThisSlope.enqueue(otherPoints[k]);
							}
							tableOfSlope.put(slope, pointWithThisSlope);
						}
						else {
							
						}
						
					}
					
					
					slope = point.slopeTo(otherPoints[j]);
					
				}
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
