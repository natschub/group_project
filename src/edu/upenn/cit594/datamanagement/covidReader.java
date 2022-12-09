package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.utils.covidDateData;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

public interface covidReader {

    public Map<String, Map<Date, covidDateData>> getCovidDateZipData() throws IOException, CSVFormatException, ParseException, org.json.simple.parser.ParseException;

}
