package edu.upenn.cit594.utils;

import java.util.LinkedList;

public class AverageMarketValue implements Average {

	@Override
	public int getAverage(propertyData data) {
		// TODO Auto-generated method stub
		LinkedList<Double> mv = data.getLivableAreas();
		Double sum = 0.00;
		for(int i=0; i<mv.size();i++) {
			sum += mv.get(i);
		}
		return (int) (sum/mv.size());
	}
	
}