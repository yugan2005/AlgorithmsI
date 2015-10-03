package Week3Quiz1;

import java.lang.reflect.Array;

public class Merge<E extends Comparable<E>> {
	private int mergeCounter;
	private E[] originalArray;
	private int outputMergeNumber;
	
	Merge(E[] originalArray, int outputMergeNumber){
		this.originalArray = originalArray;
		mergeCounter = 0;
		this.outputMergeNumber = outputMergeNumber;
	}
	
	private void merge(E[] a, E[] aux, int lo, int mid, int hi){
		//copy the array
		for (int k=lo; k<=hi; k++){
			aux[k]=a[k];
		}
		
		//now merge
		int i=lo;
		int j= mid+1;
		
		for (int k=lo; k<=hi; k++){
			if (i>mid) { // i used up
				a[k]=aux[j];
				j++;
			}
			else if (j>hi) { // j used up
				a[k]=aux[i];
				i++;
			}
			else { // normal case
				if (aux[j].compareTo(aux[i])<0) {
					a[k]=aux[j];
					j++;
				}
				else {
					a[k]=aux[i];
					i++;
				}
			}
		}
		mergeCounter++;
		
	}
	
	private void sort(E[] a, E[] aux, int lo, int hi){
		if (hi<=lo) return;
		int mid = lo + (hi-lo)/2;
		sort(a, aux, lo, mid);
		sort(a, aux, mid+1, hi);
		merge(a, aux, lo, mid, hi);
		if (mergeCounter==outputMergeNumber) {
			String output = "";
			for (E ele : a){
				output = output + " " + ele.toString();
			}
			output.trim();
			System.out.println(output);
		}
	}
	
	public void sort(){
		// This creating the <E> generic array is kind of confusing
		// reference: http://tutorials.jenkov.com/java-reflection/arrays.html
		// also reference: http://stackoverflow.com/questions/529085/how-to-create-a-generic-array-in-java
		@SuppressWarnings("unchecked")
		E[] aux = (E[]) Array.newInstance(originalArray.getClass().getComponentType(), originalArray.length);
		sort(originalArray, aux, 0, originalArray.length-1);
	}
	
	public static void main(String[] args){
		Integer[] originalArray = {69, 99, 40, 35, 67, 27, 65, 34, 60, 80, 28, 70};
		Merge<Integer> prob1 = new Merge<Integer>(originalArray, 7);
		prob1.sort();
	}
	
	
	
	

}
