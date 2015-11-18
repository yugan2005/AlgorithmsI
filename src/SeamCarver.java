
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.corba.se.impl.interceptors.PICurrent;

import edu.princeton.cs.algs4.Picture;

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
		// sequence of indices for horizontal seam
		int[] seam = new int[picture.width()];
		double globalMinimumCumulativeEnergy = Double.POSITIVE_INFINITY;

		for (int i = 0; i < picture.height(); i++) {
			int[][] pathTo = new int[picture.width()][picture.height()];
			double[][] cumulativeEnergy = new double[picture.width()][picture.height()];
			
			int col = 0;
			int row = i;
			cumulativeEnergy[col][row]=pictureEnergy[col][row];
			while (col<picture.width()-1){
				for (int nextRow : adj(row, "horizontal")){
					double currentCumulativeEnergy = cumulativeEnergy[col][row]+pictureEnergy[col+1][nextRow];
					if (cumulativeEnergy[col+1][nextRow]<1000 || cumulativeEnergy[col+1][nextRow]>currentCumulativeEnergy){
						//Not visited yet or need be relaxed
						cumulativeEnergy[col+1][nextRow]=currentCumulativeEnergy;
						pathTo[col+1][nextRow]=row;
					}
				}
				col += 1;
			}
			
			//find the minimum path for this row
			int minEndRow = 0;
			double currentMinCumulativeEnergy = Double.POSITIVE_INFINITY;
			for (int rowIdx = 0; rowIdx<picture.height(); rowIdx++){
				if (cumulativeEnergy[picture.width()-1][rowIdx]<currentMinCumulativeEnergy){
					minEndRow = rowIdx;
					currentMinCumulativeEnergy = cumulativeEnergy[picture.width()-1][rowIdx];
				}
			}
			
			if (currentMinCumulativeEnergy<globalMinimumCumulativeEnergy){
				int pathRowIdx = minEndRow;
				globalMinimumCumulativeEnergy = currentMinCumulativeEnergy;
				for (int colIdx=picture.width()-1; colIdx>0; colIdx--){
					seam[colIdx-1] = pathTo[colIdx][pathRowIdx];
					pathRowIdx = pathTo[colIdx][pathRowIdx];
				}
			}
			

		}
		return seam;
	}

//	public int[] findVerticalSeam() {
//		// sequence of indices for vertical seam
//	}
//
//	public void removeHorizontalSeam(int[] seam) {
//		// remove horizontal seam from current picture
//	}
//
//	public void removeVerticalSeam(int[] seam) {
//		// remove vertical seam from current picture
//	}

	private void getEnergy() {
		pictureEnergy = new double[picture.width()][picture.height()];
		for (int row = 0; row < picture.height(); row++) {
			for (int col = 0; col < picture.width(); col++) {
				pictureEnergy[col][row] = energy(col, row);
			}
		}
	}

	private Iterable<Integer> adj(int y, String axis) {
		int bound = 0;
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

		if (y == bound) {
			return Arrays.asList(y, y - 1);
		} else if (y == 0) {
			return Arrays.asList(y, y + 1);
		}
		return Arrays.asList(y - 1, y, y + 1);
	}
	
	public static void main(String[] args){
		Picture picture = new Picture("6x5.png");
		SeamCarver test = new SeamCarver(picture);
		for (int path:test.findHorizontalSeam()){
			System.out.println(path);
		}
	}

}
