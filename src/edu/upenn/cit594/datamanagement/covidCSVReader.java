package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.utils.covidDateData;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.ParseException;

public class covidCSVReader implements covidReader{
    protected String filename;
    private CSVReader reader;
    private CSVLexer lexer;


    public covidCSVReader(String filename) {this.filename = filename; }

@Override
    public Map<String, Map<Date, covidDateData>> getCovidDateZipData() throws IOException, CSVFormatException, ParseException {
        Map<String, Map<Date, covidDateData>> covidDateDataMap = new HashMap<>();

        CSVLexer lexer = new CSVLexer(filename);
        CSVReader reader = new CSVReader(lexer);


        List<String> headerRow = Arrays.asList(reader.readRow());
        Integer zipcodeIndex = headerRow.indexOf("zip_code");
        Integer negIndex = headerRow.indexOf("NEG");
        Integer posIndex = headerRow.indexOf("POS");
        Integer deathsIndex = headerRow.indexOf("deaths");
        Integer hospitalizedIndex = headerRow.indexOf("hospitalized");
        Integer partiallyVaccinatedIndex = headerRow.indexOf("partially_vaccinated");
        Integer fullyVaccinatedIndex = headerRow.indexOf("fully_vaccinated");
        Integer boostedIndex = headerRow.indexOf("boosted");
        Integer dateIndex = headerRow.indexOf("etl_timestamp");

        System.out.println(deathsIndex);

        String[] row;
        while ((row = reader.readRow()) != null) {
            if (row[zipcodeIndex] != null && row[zipcodeIndex].length() > 4) {
                String zipCode = row[zipcodeIndex].substring(0, 5);
                String zipCodeRegex = "[0-9]{5}";
                if (zipCode.matches(zipCodeRegex)) {
                    Integer NEG = parseUtilInteger(row[negIndex]);
                    Integer POS = parseUtilInteger(row[posIndex]);
                    Integer deaths = parseUtilInteger(row[deathsIndex]);
                    Integer hospitalized = parseUtilInteger(row[hospitalizedIndex]);
                    Integer partiallyVaccinated = parseUtilInteger(row[partiallyVaccinatedIndex]);
                    Integer fullyVaccinated = parseUtilInteger(row[fullyVaccinatedIndex]);
                    Integer boosted = parseUtilInteger(row[boostedIndex]);
                    Date date = null;
                    Boolean validDate;
                    try {
                        //SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
                        date = new SimpleDateFormat("yy-MM-dd").parse(row[dateIndex]);
                        //formattedDate =
                        validDate = true;
                    } catch (ParseException e) {
                        validDate = false;
                    }
                    if (validDate == true) {
                        //System.out.print(date);
                        covidDateData newCovidDateData = new covidDateData(date, zipCode, NEG, POS, hospitalized, partiallyVaccinated, fullyVaccinated, deaths);


                        if (covidDateDataMap.containsKey(zipCode)) {
                            covidDateDataMap.get(zipCode).put(date,newCovidDateData);
                        }
                        else {
                            Map<Date, covidDateData> newDateMap =  new HashMap<>();
                            newDateMap.put(date, newCovidDateData);
                            covidDateDataMap.put(zipCode, newDateMap);
                        }
                    }


                }

            }
        }

        return covidDateDataMap;
    }

    private Integer parseUtilInteger(String input) {

        try {
            Integer output =  Integer.parseInt(input);
            return output;
        }
        catch (NumberFormatException e){
            return null;
        }

    }

}
