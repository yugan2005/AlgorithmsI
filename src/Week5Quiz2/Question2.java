package Week5Quiz2;

import java.util.List;

import edu.princeton.cs.algs4.Point2D;

public class Question2 {

	public static void main(String[] args) {

		Point2D a = new Point2D(.78, .7);
		Point2D b = new Point2D(.09, .19);
		Point2D c = new Point2D(.18, .55);
		Point2D d = new Point2D(.36, .77);
		Point2D e = new Point2D(.24, .83);
		Point2D f = new Point2D(.7, .58);
		Point2D g = new Point2D(.47, .21);
		Point2D h = new Point2D(.83, .54);

		KdTree myTree = new KdTree();
		myTree.insert(a);
		myTree.insert(b);
		myTree.insert(c);
		myTree.insert(d);
		myTree.insert(e);
		myTree.insert(f);
		myTree.insert(g);
		myTree.insert(h);
		
		List<Point2D> levelOrderPoints = myTree.levelOrder();
		
		for (Point2D point:levelOrderPoints) System.out.println(point);
	}

}
