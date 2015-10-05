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
				throw new IllegalArgumentException("No duplicated points are allowed!");
		}

		lineSegmentQueue = new ResizingArrayQueue<>();
		ST<Double, SET<Point>> tableOfSlope = new ST<>();

		for (int i = 0; i < numOfPoints - 3; i++) {
			Point point = points[i];
			int numOtherPoints = numOfPoints - i - 1;
			Point[] otherPoints = new Point[numOtherPoints];
			for (int j = 0; j < numOtherPoints; j++) {
				otherPoints[j] = points[i + j + 1];
			}
			MergeX.sort(otherPoints, point.slopeOrder());
			int pointCounter = 1;
			double slope = point.slopeTo(otherPoints[0]);
			for (int j = 1; j < numOtherPoints; j++) {
				if (slope == point.slopeTo(otherPoints[j])) {
					pointCounter++; // Don't forget when it reaches the end of
									// array
					if (j != numOtherPoints - 1)
						continue;
				}

				int idxEndPoint = j - 1;
				if (j == numOtherPoints - 1)
					idxEndPoint = j;

				if (pointCounter >= 3) {
					// new slope, save into the ST
					if (tableOfSlope.get(slope) == null) {
						lineSegmentQueue.enqueue(new LineSegment(point, otherPoints[idxEndPoint]));
						segmentCounter++;
						SET<Point> pointWithThisSlope = new SET<>();
						pointWithThisSlope.add(point);
						for (int k = idxEndPoint - pointCounter + 1; k <= idxEndPoint; k++) {
							pointWithThisSlope.add(otherPoints[k]);
						}
						tableOfSlope.put(slope, pointWithThisSlope);
					}
					// existing slope, but different lines
					else if (!tableOfSlope.get(slope).contains(point)) {
						lineSegmentQueue.enqueue(new LineSegment(point, otherPoints[idxEndPoint]));
						segmentCounter++;
						SET<Point> pointWithThisSlope = tableOfSlope.get(slope);
						pointWithThisSlope.add(point);
						for (int k = idxEndPoint - pointCounter + 1; k <= idxEndPoint; k++) {
							pointWithThisSlope.add(otherPoints[k]);
						}
						tableOfSlope.put(slope, pointWithThisSlope);
					}
				}

				// reset slope and pointCounter
				slope = point.slopeTo(otherPoints[j]);
				pointCounter = 1;
			}
		}

	}

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
