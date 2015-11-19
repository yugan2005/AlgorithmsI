
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;

public class SeamCarver {
	private Picture picture;
	private double[][] pictureEnergy;

	public SeamCarver(Picture picture) {
		// create a seam carver object based on the given picture
		if (picture == null)
			throw new NullPointerException();
		this.picture = new Picture(picture);
		getEnergy();
	}

	public Picture picture() {
		// current picture
		return new Picture(picture);
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
		if (x < 0 || x >= width() || y < 0 || y >= height())
			throw new IndexOutOfBoundsException();
		if (x == width() - 1 || x == 0 || y == height() - 1 || y == 0) {
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

		String axis = "horizontal";
		int bound = width() - 1;
		int otherBound = height();

		int[][] pathTo = new int[width()][height()];
		double[][] cumulativeEnergy = new double[width()][height()];

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

	public int[] findVerticalSeam() {
		// sequence of indices for vertical seam
		String axis = "vertical";
		int bound = height() - 1;
		int otherBound = width();

		int[][] pathTo = new int[width()][height()];
		double[][] cumulativeEnergy = new double[width()][height()];

		for (int i = 0; i < otherBound; i++) {
			cumulativeEnergy[i][0] = pictureEnergy[i][0];
		}

		for (int i = 0; i < bound; i++) {
			for (int j = 0; j < otherBound; j++) {
				for (int k : adj(j, axis)) {
					if ((cumulativeEnergy[k][i + 1] < 1000)
							|| (cumulativeEnergy[k][i + 1] > pictureEnergy[k][i + 1] + cumulativeEnergy[j][i])) {
						pathTo[k][i + 1] = j;
						cumulativeEnergy[k][i + 1] = pictureEnergy[k][i + 1] + cumulativeEnergy[j][i];
					}

				}
			}
		}

		double minEnergy = Double.POSITIVE_INFINITY;
		int endPoint = 0;

		for (int i = 0; i < otherBound; i++) {
			if (cumulativeEnergy[i][bound] < minEnergy) {
				minEnergy = cumulativeEnergy[i][bound];
				endPoint = i;
			}
		}

		Stack<Integer> path = new Stack<>();
		path.push(endPoint);

		for (int i = bound; i > 0; i--) {
			path.push(pathTo[endPoint][i]);
			endPoint = pathTo[endPoint][i];
		}

		int[] result = new int[bound + 1];
		int idx = 0;
		for (int point : path) {
			result[idx] = point;
			idx++;
		}

		return result;
	}

	public void removeHorizontalSeam(int[] seam) {
		// remove horizontal seam from current picture
		if (seam == null)
			throw new NullPointerException();
		if (seam.length != width() || illegalSeam(seam, "horizontal"))
			throw new IllegalArgumentException();
		if (height() <= 1)
			throw new IllegalArgumentException();

		Picture newPicture = new Picture(width(), height() - 1);
		for (int i = 0; i < width(); i++) {
			for (int j = 0; j < height() - 1; j++) {
				if (j < seam[i]) {
					newPicture.set(i, j, picture.get(i, j));
				} else if (j >= seam[i]) {
					newPicture.set(i, j, picture.get(i, j + 1));
				}

			}
		}
		this.picture = newPicture;
		getEnergy();
	}

	private boolean illegalSeam(int[] seam, String axis) {
		int bound = 0;
		switch (axis) {
		case "horizontal":
			bound = height() - 1;
			break;
		case "vertical":
			bound = width() - 1;
			break;
		}
		for (int i = 0; i < seam.length; i++) {
			if (seam[i] < 0 || seam[i] > bound)
				return true;
			if (i != seam.length - 1 && (seam[i] - seam[i + 1]) * (seam[i] - seam[i + 1]) > 1)
				return true;
		}
		return false;
	}

	public void removeVerticalSeam(int[] seam) {
		// remove vertical seam from current picture
		if (seam == null)
			throw new NullPointerException();
		if (seam.length != height())
			throw new IllegalArgumentException();
		if (illegalSeam(seam, "vertical"))
			throw new IllegalArgumentException();
		if (width() <= 1)
			throw new IllegalArgumentException();

		Picture newPicture = new Picture(width() - 1, height());
		for (int i = 0; i < width() - 1; i++) {
			for (int j = 0; j < height(); j++) {
				if (i < seam[j]) {
					newPicture.set(i, j, picture.get(i, j));
				} else if (i >= seam[j]) {
					newPicture.set(i, j, picture.get(i + 1, j));
				}

			}
		}
		this.picture = newPicture;
		getEnergy();
	}

	private void getEnergy() {
		pictureEnergy = new double[width()][height()];
		for (int row = 0; row < height(); row++) {
			for (int col = 0; col < width(); col++) {
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
			bound = height() - 1;
			break;
		case "vertical":
			bound = width() - 1;
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
		Picture picture = new Picture("IMG_20150817_081229.jpg");
		SeamCarver test = new SeamCarver(picture);
		test.picture().show();
		// System.out.println("energy");
		// for (double[] col : test.pictureEnergy) {
		// String thisCol = "";
		// for (double cell : col) {
		// thisCol += String.format(" %.2f,", cell);
		// }
		// System.out.println(thisCol);
		// }
		// System.out.println("End of energy map");
		// System.out.println("horizontal seam");
		// int idx = 0;
		// for (int path : test.findHorizontalSeam()) {
		// System.out.println(test.pictureEnergy[idx][path]);
		// idx++;
		// }
//		for (int i = 0; i < 300; i++) {
//			int[] seam = test.findVerticalSeam();
//			test.removeVerticalSeam(seam);
//		}
		for (int i = 0; i <00; i++) {
			int[] seam = test.findHorizontalSeam();
			test.removeHorizontalSeam(seam);
		}
		test.picture().show();
		// System.out.println("energy");
		// for (double[] col : test.pictureEnergy) {
		// String thisCol = "";
		// for (double cell : col) {
		// thisCol += String.format(" %.2f,", cell);
		// }
		// System.out.println(thisCol);
		// }
		// System.out.println("End of energy map");
		// System.out.println("vertical seam");
		// idx = 0;
		// for (int path : test.findVerticalSeam()) {
		// System.out.println(test.pictureEnergy[path][idx]);
		// idx++;
		// }
	}

}
