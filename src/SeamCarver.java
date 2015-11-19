
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.corba.se.impl.interceptors.PICurrent;

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;

public class SeamCarver {
	private Picture picture;
	double[][] pictureEnergy;

	public SeamCarver(Picture picture) {
		// create a seam carver object based on the given picture
		this.picture = new Picture(picture);
		getEnergy();
	}

	public Picture picture() {
		// current picture
		return picture;
	}

	public int width() {
		// width of current picture
		return picture.width();
	}

	public int height() {
		// height of current picture
		return picture.height();
	}

	public double energy(int x, int y) {
		// energy of pixel at column x and row y
		if (x == picture.width() - 1 || x == 0 || y == picture.height() - 1 || y == 0) {
			return 1000;
		}

		int neighborXLeft = x - 1;
		int neighborXRight = x + 1;
		int neighborYUp = y - 1;
		int neighborYDown = y + 1;
		Color rgbUp = picture.get(x, neighborYUp);
		Color rgbDown = picture.get(x, neighborYDown);
		Color rgbLeft = picture.get(neighborXLeft, y);
		Color rgbRight = picture.get(neighborXRight, y);

		int colorEnergy = (rgbUp.getRed() - rgbDown.getRed()) * (rgbUp.getRed() - rgbDown.getRed())
				+ (rgbUp.getBlue() - rgbDown.getBlue()) * (rgbUp.getBlue() - rgbDown.getBlue())
				+ (rgbUp.getGreen() - rgbDown.getGreen()) * (rgbUp.getGreen() - rgbDown.getGreen());
		double energy = 0;
		energy += colorEnergy;
		colorEnergy = (rgbLeft.getRed() - rgbRight.getRed()) * (rgbLeft.getRed() - rgbRight.getRed())
				+ (rgbLeft.getBlue() - rgbRight.getBlue()) * (rgbLeft.getBlue() - rgbRight.getBlue())
				+ (rgbLeft.getGreen() - rgbRight.getGreen()) * (rgbLeft.getGreen() - rgbRight.getGreen());
		energy += colorEnergy;

		energy = Math.sqrt(energy);
		return energy;
	}

	public int[] findHorizontalSeam() {
		// // sequence of indices for horizontal seam
		// int[] seam = new int[picture.width()];
		// double globalMinimumCumulativeEnergy = Double.POSITIVE_INFINITY;
		//
		// for (int i = 0; i < picture.height(); i++) {
		// int[][] pathTo = new int[picture.width()][picture.height()];
		// double[][] cumulativeEnergy = new
		// double[picture.width()][picture.height()];
		//
		// int col = 0;
		// int row = i;
		// cumulativeEnergy[col][row]=pictureEnergy[col][row];
		// while (col<picture.width()-1){
		// for (int nextRow : adj(row, "horizontal")){
		// double currentCumulativeEnergy =
		// cumulativeEnergy[col][row]+pictureEnergy[col+1][nextRow];
		// if (cumulativeEnergy[col+1][nextRow]<1000 ||
		// cumulativeEnergy[col+1][nextRow]>currentCumulativeEnergy){
		// //Not visited yet or need be relaxed
		// cumulativeEnergy[col+1][nextRow]=currentCumulativeEnergy;
		// pathTo[col+1][nextRow]=row;
		// }
		// }
		// col += 1;
		// }
		//
		// //find the minimum path for this row
		// int minEndRow = 0;
		// double currentMinCumulativeEnergy = Double.POSITIVE_INFINITY;
		// for (int rowIdx = 0; rowIdx<picture.height(); rowIdx++){
		// if
		// (cumulativeEnergy[picture.width()-1][rowIdx]<currentMinCumulativeEnergy){
		// minEndRow = rowIdx;
		// currentMinCumulativeEnergy =
		// cumulativeEnergy[picture.width()-1][rowIdx];
		// }
		// }
		//
		// if (currentMinCumulativeEnergy<globalMinimumCumulativeEnergy){
		// int pathRowIdx = minEndRow;
		// globalMinimumCumulativeEnergy = currentMinCumulativeEnergy;
		// for (int colIdx=picture.width()-1; colIdx>0; colIdx--){
		// seam[colIdx-1] = pathTo[colIdx][pathRowIdx];
		// pathRowIdx = pathTo[colIdx][pathRowIdx];
		// }
		// }
		//
		//
		// }
		// return seam;
		// }
		String axis = "horizontal";
		int bound = picture.width() - 1;
		int otherBound = picture.height();

		int[][] pathTo = new int[picture.width()][picture.height()];
		double[][] cumulativeEnergy = new double[picture.width()][picture.height()];

		for (int i = 0; i < otherBound; i++) {
			cumulativeEnergy[0][i] = pictureEnergy[0][i];
		}

		for (int i = 0; i < bound; i++) {
			for (int j = 0; j < otherBound; j++) {
				for (int k : adj(j, axis)) {
					if ((cumulativeEnergy[i + 1][k] < 1000)
							|| (cumulativeEnergy[i + 1][k] > pictureEnergy[i + 1][k] + cumulativeEnergy[i][j])) {
						pathTo[i + 1][k] = j;
						cumulativeEnergy[i + 1][k] = pictureEnergy[i + 1][k] + cumulativeEnergy[i][j];
					}

				}
			}
		}

		double minEnergy = Double.POSITIVE_INFINITY;
		int endPoint = 0;

		for (int i = 0; i < otherBound; i++) {
			if (cumulativeEnergy[bound][i] < minEnergy) {
				minEnergy = cumulativeEnergy[bound][i];
				endPoint = i;
			}
		}

		Stack<Integer> path = new Stack<>();
		path.push(endPoint);

		for (int i = bound; i > 0; i--) {
			path.push(pathTo[i][endPoint]);
			endPoint = pathTo[i][endPoint];
		}

		int[] result = new int[bound + 1];
		int idx = 0;
		for (int point : path) {
			result[idx] = point;
			idx++;
		}

		return result;

	}

	// public int[] findVerticalSeam() {
	// // sequence of indices for vertical seam
	// }
	//
	// public void removeHorizontalSeam(int[] seam) {
	// // remove horizontal seam from current picture
	// }
	//
	// public void removeVerticalSeam(int[] seam) {
	// // remove vertical seam from current picture
	// }



	private void getEnergy() {
		pictureEnergy = new double[picture.width()][picture.height()];
		for (int row = 0; row < picture.height(); row++) {
			for (int col = 0; col < picture.width(); col++) {
				pictureEnergy[col][row] = energy(col, row);
			}
		}
	}

	private Iterable<Integer> adj(int i, String axis) {
		List<Integer> result = new ArrayList<>(3);
		result.add(i);
		int bound;
		switch (axis) {
		case "horizontal":
			bound = picture.height() - 1;
			break;
		case "vertical":
			bound = picture.width() - 1;
			break;
		default:
			throw new IllegalArgumentException("can only be horizontal, or vertical");
		}

		if (i != 0) {
			result.add(i - 1);
		}
		if (i != bound) {
			result.add(i + 1);
		}
		return result;
	}

	public static void main(String[] args) {
		Picture picture = new Picture("6x5.png");
		SeamCarver test = new SeamCarver(picture);
		for (int path : test.findHorizontalSeam()) {
			System.out.println(path);
		}
	}

}
