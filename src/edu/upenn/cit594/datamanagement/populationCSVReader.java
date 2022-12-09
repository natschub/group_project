package edu.upenn.cit594.datamanagement;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;


public class populationCSVReader implements populationReader{

    protected String filename;
    private CSVReader reader;
    private CSVLexer lexer;





    public populationCSVReader(String filename) {
        this.filename = filename;
    }

    public Map<String, Integer> getPopulationData() throws IOException, CSVFormatException {
        Map<String, Integer> populationMap = new HashMap<>();

        CSVLexer lexer = new CSVLexer(filename);
        CSVReader reader = new CSVReader(lexer);


        List<String> headerRow = Arrays.asList(reader.readRow());
        Integer zipcodeIndex = headerRow.indexOf("zip_code");
        Integer populationIndex = headerRow.indexOf("population");


        String[] row;
        while ((row = reader.readRow()) != null) {
            //System.out.println(row);
            String zipCode = row[zipcodeIndex];
            Integer pop = Integer.parseInt(row[populationIndex]);

            populationMap.put(zipCode, pop);
        }

        return populationMap;

    }

}
