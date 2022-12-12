package edu.upenn.cit594.utils;

import java.util.LinkedList;

public class AverageLivableArea implements Average {

	@Override
	public int getAverage(propertyData data) {
		// TODO Auto-generated method stub
		LinkedList<Double> la = data.getLivableAreas();
		Double sum = 0.00;
		for(int i=0; i<la.size();i++) {
			sum += la.get(i);
		}
		return (int) (sum/la.size());
	}
	
}