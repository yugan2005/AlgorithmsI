package Week5Quiz2;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

/**
 * @author yug This version of KdTree is based on normal Binary Search Tree and
 *         it is not re-balanced. According to literature, the red-black tree's
 *         algorithm can not be used to rebalance the KdTree. Reference: Russell
 *         A. Brown, 2015, Building a Balanced k-d Tree in O(kn log n) Time, the
 *         Journal ofComputer Graphics Techniques
 *         https://drive.google.com/a/google
 *         .com/folderview?id=0Bzocz6-01VsNT1hBSFRLbDdZTEk&usp=sharing So, this
 *         KdTree does not support delete operation, and it will randomize the
 *         input I will write another version by using Russell's implementation
 *         of KdTree
 *
 */
public class KdTree {

	private class Node {
		private int level; // number of levels in the tree. root=0;
		private int number; // number of nodes in subtrees
		private double key; // sorted by key
		private Node left, right; // left and right subtrees;
		private Point2D p; // associated point
		private RectHV rect; // the axis-aligned rectangle corresponding to node

		public Node(Point2D p, int level, int number, Node parentNode) {
			this.p = p;
			this.level = level;
			this.number = number;
			this.key = (level % 2 == 0) ? p.x() : p.y();
			if (parentNode == null) {
				this.rect = new RectHV(0, 0, 1, 1);
			} else {
				RectHV parentRect = parentNode.rect;
				if (level % 2 == 0) { // parentNode is dividing by y
					if (p.y() < parentNode.key) // take the bottom part
						this.rect = new RectHV(parentRect.xmin(), parentRect.ymin(), parentRect.xmax(), parentNode.key);
					else
						this.rect = new RectHV(parentRect.xmin(), parentNode.key, parentRect.xmax(), parentRect.ymax());
				} else { // arentNode is dividing by x
					if (p.x() < parentNode.key) // take the left part
						this.rect = new RectHV(parentRect.xmin(), parentRect.ymin(), parentNode.key, parentRect.ymax());
					else
						// take the right part
						this.rect = new RectHV(parentNode.key, parentRect.ymin(), parentRect.xmax(), parentRect.ymax());
				}
			}
		}
	}

	private Node root;

	public KdTree() {
		// construct an empty set of points
	}

	public boolean isEmpty() {
		// is the set empty?
		return size() == 0;
	}

	public int size() {
		// number of points in the set
		return size(root);
	}

	private int size(Node node) {
		if (node == null)
			return 0;
		return node.number;
	}

	/**
	 * if the key is smaller go to the left subtress if the key is EQUAL or
	 * larger, go to the right if the point p is already inside the kdTree,
	 * replace it.
	 * 
	 * @param p
	 */
	public void insert(Point2D p) {
		// add the point to the set (if it is not already in the set)
		if (p == null)
			throw new NullPointerException("The input to insert method is null!");
		root = insert(root, p, 0, null);
	}

	private Node insert(Node node, Point2D p, int level, Node parentNode) {
		if (node == null)
			return new Node(p, level, 1, parentNode); // the last 1 is the
														// number
		Double pKey = (level % 2 == 0) ? p.x() : p.y();
		int cmp = pKey.compareTo(node.key);
		if (cmp < 0) {
			node.left = insert(node.left, p, level + 1, node);
		} else if (cmp > 0) {
			node.right = insert(node.right, p, level + 1, node);
		} else {
			if (node.p.equals(p))
				node.p = p; // replace
			else {
				node.right = insert(node.right, p, level + 1, node);
			}
		}
		node.number = 1 + size(node.left) + size(node.right);
		return node;
	}

	public boolean contains(Point2D p) {
		// does the set contain point p?
		if (p == null)
			throw new NullPointerException("The input to contains method is null!");
		return get(p) != null;
	}

	private Point2D get(Point2D p) {
		return get(root, p, 0);
	}

	private Point2D get(Node node, Point2D p, int level) {
		if (node == null)
			return null;
		Double pKey = (level % 2 == 0) ? p.x() : p.y();
		int cmp = pKey.compareTo(node.key);

		if (cmp < 0) {
			return get(node.left, p, level + 1);
		} else if (cmp > 0) {
			return get(node.right, p, level + 1);
		} else {
			if (node.p.equals(p))
				return node.p;
			else {
				return get(node.right, p, level + 1);
			}
		}
	}

	public void draw() {
		// draw all points to standard draw
		draw(root);
	}

	private void draw(Node node) {
		if (node == null)
			return;
		draw(node.left);
		draw(node.right);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(StdDraw.getPenRadius() * 10);
		node.p.draw();
		StdDraw.setPenRadius();
		if (node.level == 0) // root use black color
			StdDraw.setPenColor(StdDraw.BLACK);
		else if (node.level % 2 == 0)
			StdDraw.setPenColor(StdDraw.BLUE);
		else
			StdDraw.setPenColor(StdDraw.RED);
		node.rect.draw();

		// need draw the last line for the terminal nodes
		if (node.left == null && node.right == null) {
			if (node.level % 2 == 1) {
				StdDraw.setPenColor(StdDraw.BLUE);
				double y0 = node.p.y();
				double y1 = y0;
				double x0 = node.rect.xmin();
				double x1 = node.rect.xmax();
				StdDraw.line(x0, y0, x1, y1);
			} else {
				StdDraw.setPenColor(StdDraw.RED);
				double x0 = node.p.x();
				double x1 = x0;
				double y0 = node.rect.ymin();
				double y1 = node.rect.ymax();
				StdDraw.line(x0, y0, x1, y1);
			}
		}
	}

	// /**
	// * This method is for test only, need delete it afterwards
	// *
	// * @param p
	// * @return
	// */
	// public int getLevel(Point2D p) {
	// // TODO need be deleted
	// if (getNode(p) == null)
	// return -1;
	// else
	// return getNode(p).level;
	// }

	private Node getNode(Point2D p) {
		return getNode(root, p, 0);
	}

	private Node getNode(Node node, Point2D p, int level) {
		if (node == null)
			return null;
		Double pKey = (level % 2 == 0) ? p.x() : p.y();
		int cmp = pKey.compareTo(node.key);

		if (cmp < 0) {
			return getNode(node.left, p, level + 1);
		} else if (cmp > 0) {
			return getNode(node.right, p, level + 1);
		} else {
			if (node.p.equals(p))
				return node;
			else {
				return getNode(node.right, p, level + 1);
			}
		}
	}

	public Iterable<Point2D> range(RectHV rect) {
		// all points that are inside the rectangle
		if (rect == null)
			throw new NullPointerException("The input to range method is null!");
		Bag<Point2D> result = new Bag<>();
		range(rect, root, result);
		return result;
	}

	private void range(RectHV rect, Node node, Bag<Point2D> result) {
		if (node == null)
			return;
		if (!node.rect.intersects(rect))
			return;
		if (rect.contains(node.p))
			result.add(node.p);
		range(rect, node.left, result);
		range(rect, node.right, result);
	}

	public Point2D nearest(Point2D p) {
		// a nearest neighbor in the set to point p; null if the set is empty
		if (p == null)
			throw new NullPointerException("The input to nearest method is null!");
		if (root == null)
			return null;
		Node currentNode = root;
		Point2D closestPoint = root.p;
		return nearest(p, currentNode, closestPoint);
	}

	private Point2D nearest(Point2D p, Node currentNode, Point2D closestPoint) {
		double closestDistanceSQ = closestPoint.distanceSquaredTo(p);
		if (currentNode == null)
			return closestPoint;
		if (p.equals(currentNode.p)) {
			closestPoint = currentNode.p;
			return closestPoint;
		}

		if (currentNode.rect.distanceSquaredTo(p) > closestDistanceSQ)
			return closestPoint;

		if (currentNode.p.distanceSquaredTo(p) < closestDistanceSQ) {
			closestPoint = currentNode.p;
			closestDistanceSQ = currentNode.p.distanceSquaredTo(p);
		}

		boolean goLeft = false;
		if (currentNode.level % 2 == 0)
			goLeft = (p.x() < currentNode.p.x()) ? true : false;
		else
			goLeft = (p.y() < currentNode.p.y()) ? true : false;

		if (goLeft) {
			closestPoint = nearest(p, currentNode.left, closestPoint);
			closestPoint = nearest(p, currentNode.right, closestPoint);
		} else {
			closestPoint = nearest(p, currentNode.right, closestPoint);
			closestPoint = nearest(p, currentNode.left, closestPoint);
		}

		return closestPoint;
	}

	public List<Point2D> levelOrder() {
		List<Point2D> returnList = new ArrayList<Point2D>();

		Queue<Node> queue = new ArrayDeque<Node>();

		queue.add(root);

		while (queue.size() != 0) {
			Node currentNode = queue.remove();
			if (currentNode == null)
				continue;
			returnList.add(currentNode.p);
			if (currentNode.left!=null) queue.add(currentNode.left);
			if (currentNode.right!=null) queue.add(currentNode.right);
		}
		return returnList;

	}

	public static void main(String[] args) {
		// unit testing of the methods (optional)
		Point2D p1 = new Point2D(.2, .3);
		Point2D p2 = new Point2D(.4, .2);
		Point2D p3 = new Point2D(.4, .5);
		Point2D p4 = new Point2D(.3, .3);
		Point2D p5 = new Point2D(.1, .5);
		Point2D p6 = new Point2D(.4, .4);

		KdTree testTree = new KdTree();
		testTree.insert(p1);
		testTree.insert(p2);
		testTree.insert(p3);
		testTree.insert(p4);
		testTree.insert(p5);
		testTree.insert(p6);

		testTree.draw();
	}

}
