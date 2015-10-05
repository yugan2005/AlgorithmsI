import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.ResizingArrayQueue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
	private ResizingArrayQueue<LineSegment> lineSegmentQueue;
	private int segmentCounter = 0;

	public BruteCollinearPoints(Point[] points) {
		// finds all line segments containing 4 points
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

		for (int i = 0; i < numOfPoints - 3; i++) {
			for (int j = i + 1; j < numOfPoints - 2; j++) {
				for (int k = j + 1; k < numOfPoints - 1; k++) {
					for (int m = k + 1; m < numOfPoints; m++) {
						if (points[i].slopeTo(points[j]) == points[j].slopeTo(points[k])
								&& points[j].slopeTo(points[k]) == points[k].slopeTo(points[m])) {
							lineSegmentQueue.enqueue(new LineSegment(points[i], points[m]));
							segmentCounter += 1;
						}
					}
				}
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
		BruteCollinearPoints collinear = new BruteCollinearPoints(points);
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}
	}

}