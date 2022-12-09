package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.utils.covidDateData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
//import utils.parseUtilInteger;

public class covidJSONReader implements covidReader {
    protected String filename;
    public covidJSONReader(String filename) {this.filename = filename;}

    @Override
    public Map<String, Map<Date, covidDateData>> getCovidDateZipData() throws IOException, CSVFormatException, ParseException, org.json.simple.parser.ParseException {
        Map<String, Map<Date, covidDateData>> covidDateDataMap = new HashMap<>();

        Object obj = new JSONParser().parse(new FileReader(filename));
        //JSONObject jo = (JSONObject) obj;
        JSONArray ja = (JSONArray) obj;
        Iterator covidIterator = ja.iterator();

        while (covidIterator.hasNext()) {
            Integer NEG = null;
            Integer POS = null;
            Integer deaths = null;
            Integer hospitalized = null;
            Integer partiallyVaccinated = null;
            Integer fullyVaccinated = null;
            Integer boosted = null;

            JSONObject covidJSON = (JSONObject) covidIterator.next();
            String zipCode = covidJSON.get("zip_code").toString();
            if (zipCode != null && zipCode.length() > 4) {
                zipCode = zipCode.substring(0, 5);
                String zipCodeRegex = "[0-9]{5}";

                if (zipCode.matches(zipCodeRegex)) {


                    if (covidJSON.get("NEG") != null) {NEG = parseUtilInteger(covidJSON.get("NEG").toString());}
                    else {NEG = null;}

                    if (covidJSON.get("POS") != null) {POS = parseUtilInteger(covidJSON.get("POS").toString());}
                    else POS = null;}

                    if (covidJSON.get("deaths") != null) {deaths = parseUtilInteger(covidJSON.get("deaths").toString());}
                    else {deaths= null;}

                    if (covidJSON.get("hospitalized") != null) {hospitalized = parseUtilInteger(covidJSON.get("hospitalized").toString());}
                    else {hospitalized = null;}

                    if (covidJSON.get("partially_vaccinated") != null) {partiallyVaccinated = parseUtilInteger(covidJSON.get("partially_vaccinated").toString());}
                    else {partiallyVaccinated = null;}

                    if (covidJSON.get("fully_vaccinated") != null) {fullyVaccinated = parseUtilInteger(covidJSON.get("fully_vaccinated").toString());}
                    else {fullyVaccinated = null;}

                    if (covidJSON.get("boosted") != null) {NEG = parseUtilInteger(covidJSON.get("boosted").toString());}
                    else {boosted = null;}

                    Date date = null;
                    Boolean validDate;
                    try {
                        date = new SimpleDateFormat("yy-MM-dd").parse((String) covidJSON.get("etl_timestamp"));
                        validDate = true;
                    } catch (ParseException e) {
                        validDate = false;
                    }
                    if (validDate == true) {
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
