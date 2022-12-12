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
 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////   
    public int getAverageValues(String zipCode, Average propertyAttribute) {
    	Map<String, propertyData> propertyDataMap = propertyReader.getPropertyData();
    	if (populationMap.get(zipCode) == null) {
            return 0;
        }
    	propertyData data = populationMap.get(zipCode);
    	
    	return propertyAttribute.getAverage(data);
    	
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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


 public List<String> getDeathRatesperCapitaMostLeastExpensive(String dateString) throws CSVFormatException, IOException, ParseException, org.json.simple.parser.ParseException {
        Map<String, Integer> populationMap = populationReader.getPopulationData();
        Map<String, propertyData> propertyDataMap = propertyReader.getPropertyData();
        Map<String, Map<Date, covidDateData>> covidDateDataMap = covidReader.getCovidDateZipData();

        List<String> outputList = new ArrayList<>();

        Date date = new SimpleDateFormat("yy-MM-dd").parse(dateString);

        // get average market value per zipcode
        List<String> allZipCodes = new ArrayList(populationMap.keySet());
        SortedMap<Integer, String> marketValueByZip = new TreeMap<>();


        for (String zipcode: allZipCodes) {
            //System.out.println("ZIP CODE 3.7: " + zipcode);
            Integer averageMarketVal;
            LinkedList<Double> mv =  propertyDataMap.get(zipcode).getPropertyValues();
            Double sum = 0.00;
            Integer nonNulls = 0;
            for (int i = 0; i < mv.size(); i++) {
                if (mv.get(i) != null) {
                    sum += mv.get(i);
                    nonNulls++;
                }
            }

            if (nonNulls > 0) {
                averageMarketVal = (int) (sum / nonNulls);
            } else {
                averageMarketVal = 0;
            }
            marketValueByZip.put(averageMarketVal, zipcode);
        }
        //SortedMap<Integer, String> bottomFiveExpensive = marketValueByZip.tailMap(5);
        //SortedMap<Integer, String> topFiveExpensive = marketValuePerCapita.tailMap(5);
        //System.out.print("bottom 5 " + bottomFiveExpensive);
        DecimalFormat df = new DecimalFormat("0.00");


        for (Integer marketVal:  marketValueByZip.keySet()) {
            String zipCode = marketValueByZip.get(marketVal);

            if (covidDateDataMap.get(zipCode) != null && covidDateDataMap.get(zipCode).get(date) != null) {
                Integer zipCodePop = populationMap.get(zipCode);

                if ((covidDateDataMap.get(zipCode).get(date)).getDeaths() != null && (covidDateDataMap.get(zipCode).get(date)).getDeaths() != 0) {
                    Double deathRate = ((covidDateDataMap.get(zipCode).get(date)).getDeaths() *10000 / (zipCodePop.doubleValue()));
                    StringBuilder stringToAppend = new StringBuilder();
                    stringToAppend.append("Zipcode: " + zipCode + " Avg Property Market Value $" + marketVal + " Deaths per 10K Popoulation as of Entered Date: " + df.format(deathRate));
                    outputList.add(stringToAppend.toString());
                }


            }


        }
        return outputList;
    }


