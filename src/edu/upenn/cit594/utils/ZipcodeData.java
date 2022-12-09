package edu.upenn.cit594.utils;
import java.util.Map;

public class ZipcodeData {



    private final String zipcode;
    private final Integer propertyValue;
    private final Integer livableArea;
    private Map<String, covidDateData> covidDateData;

    public ZipcodeData(String zipcode, Integer propertyValue, Integer livableArea, Map<String, covidDateData> covidDateData) {

        this.zipcode = zipcode;
        this.propertyValue = propertyValue;
        this.livableArea = livableArea;
        this.covidDateData = covidDateData;

    }

    public String getZipcode() {return zipcode;}
    public Integer getPropertyValue() {return propertyValue; }
    public Integer getLivableArea() {return livableArea; }
    public Map<String, covidDateData> getCovidDateData() {return covidDateData; }


    public void setCovidDateData(Map<String, covidDateData> covidDateData) {
        this.covidDateData = covidDateData;
    }
}