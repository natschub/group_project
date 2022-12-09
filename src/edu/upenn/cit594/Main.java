package edu.upenn.cit594;


import edu.upenn.cit594.datamanagement.CSVFormatException;
import edu.upenn.cit594.datamanagement.covidCSVReader;
import edu.upenn.cit594.datamanagement.covidJSONReader;
import edu.upenn.cit594.datamanagement.populationCSVReader;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws CSVFormatException, IOException, ParseException, org.json.simple.parser.ParseException {


    /*
    String regexRuntimeArg = "^--(?<name>.+?)=(?<value>.+)$";

    String covidFilename = args.covid;
    String propertiesFilename = args.properties;
    String populationFilename = args.population;
    String logFilename = args.population;
    or something to that effect....

     */

    //String covidFilename = "/Users/nathanielschub/IdeaProjects/group_project/covid_data.csv";
    String covidFilename = "/Users/nathanielschub/IdeaProjects/group_project/covid_data.json";
    String propertiesFilename = "/Users/nathanielschub/IdeaProjects/group_project/properties.csv";
    String populationFilename = "/Users/nathanielschub/IdeaProjects/group_project/population.csv";
    String logOutputFilename = "/Users/nathanielschub/IdeaProjects/group_projects/test_log_1.txt";

    //initialize logger
    //Logger log = Logger.getInstance();
    //log.setLoggerOutputFile(logOutputFilename);


    populationCSVReader readerPop = new populationCSVReader(populationFilename);
    System.out.println(readerPop.getPopulationData());


    //propertyCSVReader readerProp = new propertyCSVReader(propertiesFilename);
    //System.out.println(readerProp.getPropertyData());

    //covidCSVReader readerCovid = new covidCSVReader(covidFilename);
    covidJSONReader readerCovid = new covidJSONReader(covidFilename);

    Date testDate = new SimpleDateFormat("yy-MM-dd").parse("2021-03-25");
    System.out.println(readerCovid.getCovidDateZipData().get("19102").get(testDate).getNeg());

    /*
    Date testDate = new SimpleDateFormat("yy-MM-dd").parse("2021-03-25");
    System.out.println(readerCovid.getCovidDateZipData().get("19102").get(testDate).getNeg());
    */

    }
}