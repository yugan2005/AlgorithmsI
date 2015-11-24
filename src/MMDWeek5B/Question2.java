package MMDWeek5B;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class Question2 {

	public static void main(String[] args) {
		Point2D ab = new Point2D(5,10);
		Point2D cd = new Point2D(20,5);
		
//		double[] yellowUL = {6, 15};
//		double[] yellowLR = {13, 7};
//		double[] buleUL= {16, 19};
//		double[] buleLR = {25, 12};
		
		double[] yellowUL = {3,3};
		double[] yellowLR = {10, 1};
		double[] buleUL= {15, 14};
		double[] buleLR = {20, 10};
		
		
		RectHV blue = new RectHV(buleUL[0], buleLR[1], buleLR[0], buleUL[1]);
		RectHV yellow= new RectHV(yellowUL[0], yellowLR[1], yellowLR[0], yellowUL[1]);
		
//		StdDraw.setPenColor(StdDraw.BLUE);
//		blue.draw();
//		StdDraw.setPenColor(StdDraw.YELLOW);
//		yellow.draw();
//		StdDraw.setPenColor(StdDraw.BLACK);
//		StdDraw.setPenRadius(StdDraw.getPenRadius()*10);
//		ab.draw();
//		cd.draw();
//		StdDraw.show();

		System.out.println("point ab to Yellow distance is greater than");
		System.out.println(yellow.distanceTo(ab));
		System.out.println("point ab to Yellow distance is less than");
		System.out.println(distanceMaximum(ab, yellow));
		System.out.println("point ab to blue distance is greater than");
		System.out.println(blue.distanceTo(ab));
		System.out.println("point ab to blue distance is less than");
		System.out.println(distanceMaximum(ab, blue));
		System.out.println();
		System.out.println("point cd to Yellow distance is greater than");
		System.out.println(yellow.distanceTo(cd));
		System.out.println("point cd to Yellow distance is less than");
		System.out.println(distanceMaximum(cd, yellow));
		System.out.println("point cd to blue distance is greater than");
		System.out.println(blue.distanceTo(cd));
		System.out.println("point cd to blue distance is less than");
		System.out.println(distanceMaximum(cd, blue));
		

	}
	
	private static double distanceMaximum(Point2D p, RectHV rect) {
        double dx = 0.0, dy = 0.0;
        if      (p.x() < rect.xmin()) dx = p.x() - rect.xmax();
        else if (p.x() > rect.xmax()) dx = p.x() - rect.xmin();
        if      (p.y() < rect.ymin()) dy = p.y() - rect.ymax();
        else if (p.y() > rect.ymax()) dy = p.y() - rect.ymin();
        return Math.sqrt(dx*dx + dy*dy);
    }

}
