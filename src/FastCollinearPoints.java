import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.ResizingArrayQueue;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

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
				throw new IllegalArgumentException(
						"No duplicated points are allowed!");
		}

		lineSegmentQueue = new ResizingArrayQueue<>();
		ST<Double, SET<Point>> tableOfSlope = new ST<>();

		for (int i = 0; i < numOfPoints; i++) {
			Point point = points[i];
			int numOtherPoints = numOfPoints - 1;
			Point[] otherPoints = new Point[numOtherPoints];
			for (int j = 0; j < numOtherPoints; j++) {
				if (j < i)
					otherPoints[j] = points[j];
				else
					otherPoints[j] = points[j + 1];
			}
			MergeX.sort(otherPoints, point.slopeOrder());
			int pointCounter = 1;
			double slope = point.slopeTo(otherPoints[0]);
			for (int j = 1; j < numOtherPoints; j++) {
				double currentSlope = point.slopeTo(otherPoints[j]);
				if (slope == currentSlope) {
					pointCounter++;
					// Don't forget when it reaches the end of array
					if (j != numOtherPoints - 1)
						continue;
				}

				int idxEndPoint = j - 1;
				if (j == numOtherPoints - 1 && slope == currentSlope)
					idxEndPoint = j;

				if (pointCounter >= 3) {
					// new slope, save into the ST OR existing slope, but
					// different lines
					if (tableOfSlope.get(slope) == null
							|| !tableOfSlope.get(slope).contains(point)) {
						segmentCounter++;
						SET<Point> pointWithThisSlope = new SET<>();
						pointWithThisSlope.add(point);
						for (int k = idxEndPoint - pointCounter + 1; k <= idxEndPoint; k++) {
							pointWithThisSlope.add(otherPoints[k]);
						}
						LineSegment segment = new LineSegment(
								pointWithThisSlope.min(),
								pointWithThisSlope.max());
						lineSegmentQueue.enqueue(segment);
						if (tableOfSlope.get(slope) == null)
							tableOfSlope.put(slope, pointWithThisSlope);
						else
							tableOfSlope.put(slope, tableOfSlope.get(slope)
									.union(pointWithThisSlope));
					}
				}
				// reset slope and pointCounter
				slope = currentSlope;
				pointCounter = 1;
			}
		}

	}

	// private class AbsSlopeOrderComparator implements Comparator<Point>{
	// private Point thisPoint;
	//
	// public AbsSlopeOrderComparator(Point thisPoint) {
	// this.thisPoint=thisPoint;
	// }
	//
	// @Override
	// public int compare(Point o1, Point o2) {
	// double slope1 = Math.abs(thisPoint.slopeTo(o1));
	// double slope2 = Math.abs(thisPoint.slopeTo(o2));
	// return ((Double) slope1).compareTo(slope2);
	// }
	//
	// }

	public int numberOfSegments() {
		// the number of line segments
		return segmentCounter;
	}

	public LineSegment[] segments() {
		// the line segments
		LineSegment[] lineSegments = new LineSegment[segmentCounter];
		int idx = 0;
		for (LineSegment eachSegment : lineSegmentQueue) {
			lineSegments[idx] = eachSegment;
			idx++;
		}
		return lineSegments;
	}

	public static void main(String[] args) {
		// read the N points from a file
		In in = new In(args[0]);
		int N = in.readInt();
		Point[] points = new Point[N];
		for (int i = 0; i < N; i++) {
			int x = in.readInt();
			int y = in.readInt();
			points[i] = new Point(x, y);
		}

		// draw the points
		StdDraw.show(0);
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		for (Point p : points) {
			p.draw();
		}
		StdDraw.show();

		// print and draw the line segments
		FastCollinearPoints collinear = new FastCollinearPoints(points);
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}
	}

}
