package MMDWeek5B;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;

public class Question1 {
	public static void main(String[] args) {
		double[][] pointsCoordinates = new double[][] { { 28, 145 },
				{ 65, 140 }, { 50, 130 }, { 38, 115 }, { 55, 118 }, { 50, 90 },
				{ 63, 88 }, { 43, 83 }, { 50, 60 }, { 50, 30 }, { 25, 125 },
				{ 44, 105 }, { 29, 97 }, { 35, 63 }, { 55, 63 }, { 42, 57 },
				{ 23, 40 }, { 64, 37 }, { 33, 22 }, { 55, 20 } };

		Point2D[] points = new Point2D[20];
		for (int i = 0; i < pointsCoordinates.length; i++) {
			points[i] = new Point2D(pointsCoordinates[i][0],
					pointsCoordinates[i][1]);
		}

		List<List<Point2D>> clusters = new ArrayList<List<Point2D>>();
		for (int idx = 10; idx < pointsCoordinates.length; idx++) {
			List<Point2D> cluster = new ArrayList<Point2D>();
			cluster.add(points[idx]);
			clusters.add(cluster);
		}

		double[][] oldCentroid = getCentroid(clusters);
		System.out.println("The original centroids:");
		for (int i = 0; i < oldCentroid.length; i++) {
			System.out.println("(" + oldCentroid[i][0] + ", "
					+ oldCentroid[i][1] + ")");
		}

		for (int i = 0; i < 10; i++) {
			int assignedCluster = -1;
			double minDistance = Double.MAX_VALUE;
			for (int j = 0; j < 10; j++) {
				double distance = Math.pow((points[i].x() - oldCentroid[j][0]),
						2) + Math.pow((points[i].y() - oldCentroid[j][1]), 2);
				if (distance < minDistance) {
					assignedCluster = j;
					minDistance = distance;
				}
			}
			clusters.get(assignedCluster).add(points[i]);
		}
		
		for (int i=0; i<10; i++){
			System.out.println("The cluster "+i+" has points:");
			for (Point2D point:clusters.get(i)){
				System.out.println(point);
			}
			System.out.println();
		}
		
		double[][] newCentroid = getCentroid(clusters);
		System.out.println("The new centroids:");
		for (int i = 0; i < newCentroid.length; i++) {
			System.out.println("(" + newCentroid[i][0] + ", "
					+ newCentroid[i][1] + ")");
		}
		
		List<List<Point2D>> newClusters = new ArrayList<List<Point2D>>();
		for (int i = 0; i < 10; i++) {
			List<Point2D> cluster = new ArrayList<Point2D>();
			newClusters.add(cluster);
		}
		
		for (int i=0; i<20; i++){
			int assignedCluster = -1;
			double minDistance = Double.MAX_VALUE;
			for (int j = 0; j < 10; j++) {
				double distance = Math.pow((points[i].x() - newCentroid[j][0]),
						2) + Math.pow((points[i].y() - newCentroid[j][1]), 2);
				if (distance < minDistance) {
					assignedCluster = j;
					minDistance = distance;
				}
			}
			newClusters.get(assignedCluster).add(points[i]);
		}
		
		for (int i=0; i<10; i++){
			System.out.println("The cluster "+i+" has points:");
			for (Point2D point:newClusters.get(i)){
				System.out.println(point);
			}
			System.out.println();
		}


	}

	private static double[][] getCentroid(List<List<Point2D>> clusters) {
		double[][] centroid = new double[clusters.size()][2];
		int idx = 0;
		for (List<Point2D> cluster : clusters) {
			centroid[idx] = new double[2];
			for (Point2D point : cluster) {
				centroid[idx][0] += point.x();
				centroid[idx][1] += point.y();
			}
			centroid[idx][0] /= cluster.size();
			centroid[idx][1] /= cluster.size();
			idx++;
		}
		return centroid;
	}

}
