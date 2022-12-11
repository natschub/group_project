package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.CSVFormatException;
import edu.upenn.cit594.datamanagement.covidReader;
import edu.upenn.cit594.datamanagement.populationReader;
import edu.upenn.cit594.datamanagement.propertyReader;
import edu.upenn.cit594.utils.covidDateData;
import edu.upenn.cit594.utils.propertyData;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


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

        for (String zipCode : populationMap.keySet()) {
            totalPop += populationMap.get(zipCode);
        }

        return totalPop;
    }

    public Map<String, Double> getVaccinationsPerCapita(String partialFull, String dateString) throws ParseException, CSVFormatException, IOException, org.json.simple.parser.ParseException {
        SortedMap<String, Double> vaccinesPerCapita = new TreeMap<>();

        Date date = new SimpleDateFormat("yy-MM-dd").parse(dateString);

        Map<String, Integer> populationMap = populationReader.getPopulationData();
        Map<String, Map<Date, covidDateData>> covidDateDataMap = covidReader.getCovidDateZipData();

        DecimalFormat df = new DecimalFormat("0.0000");
        df.setRoundingMode(RoundingMode.HALF_UP);
        df.setMinimumFractionDigits(4);
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(4);
        nf.setMaximumFractionDigits(4);

        for (String zipCode : populationMap.keySet()) {
            Integer zipCodePop = populationMap.get(zipCode);

            if (covidDateDataMap.get(zipCode) != null && covidDateDataMap.get(zipCode).get(date) != null) {


                if (partialFull.toLowerCase().trim().equals("partial")) {
                    if ((covidDateDataMap.get(zipCode).get(date)).getPartially_vaccinated() != null && (covidDateDataMap.get(zipCode).get(date)).getFully_vaccinated()!= 0) {
                        Double vaccineRate = ((covidDateDataMap.get(zipCode).get(date)).getPartially_vaccinated() / (zipCodePop.doubleValue()));
                        vaccinesPerCapita.put(zipCode, Double.parseDouble(df.format(vaccineRate)));
                    }
                } else if (partialFull.toLowerCase().trim().equals("full")) {
                    if ((covidDateDataMap.get(zipCode).get(date)).getFully_vaccinated() != null && (covidDateDataMap.get(zipCode).get(date)).getFully_vaccinated()!= 0) {
                        Double vaccineRate = ((covidDateDataMap.get(zipCode).get(date)).getFully_vaccinated() / (zipCodePop.doubleValue()));
                        vaccinesPerCapita.put(zipCode, Double.parseDouble(df.format(vaccineRate)));
                    }
                } else {
                    System.out.println("you need to either enter 'full' or 'partial'");
                    return null;
                }
            }


        }
        //for
        if (vaccinesPerCapita.isEmpty()) {return null;}
        else {return vaccinesPerCapita;}
    }

    public Integer getMarketValuePerCapita(String zipCode) throws CSVFormatException, IOException {
        Map<String, Integer> populationMap = populationReader.getPopulationData();
        Map<String, propertyData> propertyDataMap = propertyReader.getPropertyData();

        if (populationMap.get(zipCode) == null) {
            return 0;
        }

        Integer pop = populationMap.get(zipCode);
        List<Double> zipcodeMarketValues = propertyDataMap.get(zipCode).getPropertyValues();
        Double totalMarketValue = 0.0;
        for (Double propertyValue: zipcodeMarketValues) {
            if (propertyValue != null) {
                totalMarketValue += propertyValue;
            }
        }

        System.out.println(totalMarketValue + " " + pop);

        if (pop == 0) {return 0;}
        else { return new Double(totalMarketValue/pop).intValue();}

    }



}


