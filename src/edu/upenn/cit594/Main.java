package edu.upenn.cit594;


import edu.upenn.cit594.datamanagement.CSVFormatException;
import edu.upenn.cit594.datamanagement.populationCSVReader;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws CSVFormatException, IOException {


    /*
    String regexRuntimeArg = "^--(?<name>.+?)=(?<value>.+)$";

    String covidFilename = args.covid;
    String propertiesFilename = args.properties;
    String populationFilename = args.population;
    String logFilename = args.population;
    or something to that effect....

     */

    String covidFilename = "/Users/nathanielschub/IdeaProjects/group_project/covid_data.csv";
    //String covidFilename = "/Users/nathanielschub/IdeaProjects/group_project/covid_data.json";
    String propertiesFilename = "/Users/nathanielschub/IdeaProjects/group_project/properties.csv";
    String populationFilename = "/Users/nathanielschub/IdeaProjects/group_project/population.csv";
    String logOutputFilename = "/Users/nathanielschub/IdeaProjects/group_projects/test_log_1.txt";

    //initialize logger
    //Logger log = Logger.getInstance();
    //log.setLoggerOutputFile(logOutputFilename);


    populationCSVReader reader = new populationCSVReader(populationFilename);
    System.out.println(reader.getPopulationData());

    }
}