package edu.upenn.cit594;


import edu.upenn.cit594.datamanagement.covidJSONReader;
import edu.upenn.cit594.datamanagement.populationCSVReader;
import edu.upenn.cit594.datamanagement.propertyCSVReader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.ui.UI;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception {


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
        //String propertiesFilename = "/Users/nathanielschub/IdeaProjects/group_project/properties.csv";
        String propertiesFilename = "/Users/nathanielschub/IdeaProjects/group_project/quirky_props.csv";
        String populationFilename = "/Users/nathanielschub/IdeaProjects/group_project/population.csv";
    String logOutputFilename = "/Users/nathanielschub/IdeaProjects/group_project/test_log_1.txt";


    Logger logger = Logger.getInstance();
    logger.setLoggerOutputFile(logOutputFilename);
    StringBuilder filenamesString = new StringBuilder();
    for (String filename : args) {
        filenamesString.append(filename);
    }
    logger.log(filenamesString.toString());

    //initialize logger
    //Logger log = Logger.getInstance();
    //log.setLoggerOutputFile(logOutputFilename);


    populationCSVReader readerPop = new populationCSVReader(populationFilename);
    System.out.println(readerPop.getPopulationData());


    propertyCSVReader readerProp = new propertyCSVReader(propertiesFilename);

    System.out.println(readerProp.getPropertyData());
    //System.out.println(readerProp.getPropertyData());

    //covidCSVReader readerCovid = new covidCSVReader(covidFilename);
    covidJSONReader readerCovid = new covidJSONReader(covidFilename);

    Date testDate = new SimpleDateFormat("yy-MM-dd").parse("2021-03-25");
    System.out.println(readerCovid.getCovidDateZipData().get("19102").get(testDate).getNeg());

    Processor processor = new Processor(readerPop, readerCovid, readerProp);




    System.out.println(processor.getTotalPopulatonAll());


    System.out.println("COVID DATE DATA32521--- " + processor.getVaccinationsPerCapita("full","2021-03-25"));
    UI ui = new UI(processor, covidFilename, propertiesFilename, populationFilename);
    ui.displayMenu();

    DecimalFormat df = new DecimalFormat("0.0000");
    df.setRoundingMode(RoundingMode.HALF_UP);
    Double testD = 0.15000000;

    System.out.println(df.format(testD));

    System.out.println(processor.getMarketValuePerCapita("19137" ));

    String msg = "test";
    System.out.println(System.currentTimeMillis() + " " + msg + "\n");
    /*
    Date testDate = new SimpleDateFormat("yy-MM-dd").parse("2021-03-25");
    System.out.println(readerCovid.getCovidDateZipData().get("19102").get(testDate).getNeg());
    */

    }
}