package edu.upenn.cit594.utils;
import java.util.Map;

public class ZipcodeData {



    private final String zipcode;
    private final Integer propertyValue;
    private final Integer livableSquareFootage;
    private final Map<String, covidDateData> covidDateData;

    public ZipcodeData(String zipcode, Integer propertyValue, Integer livableSquareFootage, Map<String, covidDateData> covidDateData) {

        this.zipcode = zipcode;
        this.propertyValue = propertyValue;
        this.livableSquareFootage = livableSquareFootage;
        this.covidDateData = covidDateData;

    }

    public String getZipcode() {return zipcode;}
    public Integer getPropertyValue() {return propertyValue; }
    public Integer getLivableSquareFootage() {return livableSquareFootage; }
    public Map<String, covidDateData> getCovidDateData() {return covidDateData; }



}