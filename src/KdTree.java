import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Queue;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

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
			this.key = (level % 2 == 0) ? p.x() : p.y();
			if (parentNode == null) {
				this.rect = new RectHV(0, 0, 1, 1);
			} else {
				RectHV parentRect = parentNode.rect;
				if (level % 2 == 0) { // parentNode is dividing by y
					if (p.y() < parentNode.key) // take the bottom part
						this.rect = new RectHV(parentRect.xmin(),
								parentRect.ymin(), parentRect.xmax(),
								parentNode.key);
					else
						this.rect = new RectHV(parentRect.xmin(),
								parentNode.key, parentRect.xmax(),
								parentRect.ymax());
				} else { // arentNode is dividing by x
					if (p.x() < parentNode.key) // take the left part
						this.rect = new RectHV(parentRect.xmin(),
								parentRect.ymin(), parentNode.key,
								parentRect.ymax());
					else
						// take the right part
						this.rect = new RectHV(parentNode.key,
								parentRect.ymin(), parentRect.xmax(),
								parentRect.ymax());
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
			throw new NullPointerException(
					"The input to insert method is null!");
		boolean matchedPrevious = false;
		insert(root, p, 0, matchedPrevious, null);
	}

	private Node insert(Node node, Point2D p, int level,
			boolean matchedPrevious, Node parentNode) {
		if (node == null)
			return new Node(p, level, 1, parentNode); // the last 1 is the
														// number
		Double pKey = (level % 2 == 0) ? p.x() : p.y();
		int cmp = pKey.compareTo(node.key);
		if (cmp < 0) {
			matchedPrevious = false;
			node.left = insert(node.left, p, level + 1, matchedPrevious, node);
		} else if (cmp > 0) {
			matchedPrevious = false;
			node.right = insert(node.right, p, level + 1, matchedPrevious, node);
		} else {
			if (matchedPrevious)
				node.p = p; // replace
			else {
				matchedPrevious = true;
				node.right = insert(node.right, p, level + 1, matchedPrevious,
						node);
			}
		}
		node.number = 1 + size(node.left) + size(node.right);
		return node;
	}

	public boolean contains(Point2D p) {
		// does the set contain point p?
		if (p == null)
			throw new NullPointerException(
					"The input to contains method is null!");
		return get(p) != null;
	}

	private Point2D get(Point2D p) {
		boolean matchedPrevious = false;
		return get(root, p, 0, matchedPrevious);
	}

	private Point2D get(Node node, Point2D p, int level, boolean matchedPrevious) {
		if (node == null)
			return null;
		Double pKey = (level % 2 == 0) ? p.x() : p.y();
		int cmp = pKey.compareTo(node.key);
		
		if (cmp<0){
			matchedPrevious=false;
			return get(node.left, p, level+1, matchedPrevious);
		}
		else if (cmp>0){
			matchedPrevious = false;
			return get(node.right, p, level+1, matchedPrevious);
		}
		else {
			if (matchedPrevious) return node.p;
			else {
				matchedPrevious = true;
				return get(node.right, p, level+1, matchedPrevious);
			}
		}
	}

	public void draw() {
		// draw all points to standard draw
		Queue<KdTree.Node> queue = new ArrayDeque<KdTree.Node>();
		queue.add(root);
		while (!queue.isEmpty()){
			Node node=queue.remove();
			if (node==null) continue;
			queue.add(node.left);
			queue.add(node.right))
		}
		
	}

	public Iterable<Point2D> range(RectHV rect) {
		// all points that are inside the rectangle
		if (rect == null)
			throw new NullPointerException("The input to range method is null!");
		// TODO
		return null;
	}

	public Point2D nearest(Point2D p) {
		// a nearest neighbor in the set to point p; null if the set is empty
		if (p == null)
			throw new NullPointerException(
					"The input to nearest method is null!");
		// TODO
		return null;

	}

	private class TwoDTreeSet {
		private class Node {
			private int level; // number of levels in the tree. root=0;
			private int number; // number of nodes in subtrees
			private double key; // sorted by key
			private Node left, right; // left and right subtrees;
			private Point2D p; // associated point

			public Node(Point2D p, int level, int number) {
				this.p = p;
				this.level = level;
				if (level % 2 == 0)
					this.key = p.x();
				else
					this.key = p.y();
			}
		}

		private Node root;

		public TwoDTreeSet() {
		}

	}

	public static void main(String[] args) {
		// unit testing of the methods (optional)
	}

}
