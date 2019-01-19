package io.brunovargas.isobar.utils;

public enum SortingOrder {

	ASC(1),
	DESC(-1);
	
	private int comparableFactor;
	
	private SortingOrder(int comparableFactor){
		this.comparableFactor = comparableFactor;
	}

	public int getComparableFactor() {
		return comparableFactor;
	}
}
