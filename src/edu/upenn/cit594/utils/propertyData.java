package edu.upenn.cit594.utils;

import java.util.LinkedList;


public class propertyData {

    private final String zipcode;

    private final LinkedList<Double> propertyValues;
    private final LinkedList<Double> livableAreas;

    public propertyData(String zipcode, LinkedList<Double> propertyValues, LinkedList<Double> livableAreas) {
    this.zipcode = zipcode;
    this.propertyValues = propertyValues;
    this.livableAreas = livableAreas;
    }

    public String getZipcode() {return zipcode;}
    public LinkedList<Double> getPropertyValues() {return propertyValues;}
    public LinkedList<Double> getLivableAreas() {return livableAreas;}


}