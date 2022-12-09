package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.CSVFormatException;
import edu.upenn.cit594.datamanagement.covidReader;
import edu.upenn.cit594.datamanagement.populationReader;
import edu.upenn.cit594.datamanagement.propertyReader;
import java.util.Map;

import java.io.IOException;
import java.text.ParseException;


public class Processor {
    protected covidReader covidReader;
    protected populationReader populationReader;
    protected propertyReader propertyReader;

    public Processor(populationReader populationReader, covidReader covidReader, propertyReader propertyReader) {
        this.covidReader = covidReader;
        this.populationReader = populationReader;
        this.propertyReader = propertyReader;
    }

    public Integer getTotalPopulatonAll() throws CSVFormatException, IOException {
        Map<String, Integer> populationMap = populationReader.getPopulationData();
        Integer totalPop = 0;

        for (String zipCode: populationMap.keySet()) {
            totalPop += populationMap.get(zipCode);
        }

        return totalPop;





    }
}
