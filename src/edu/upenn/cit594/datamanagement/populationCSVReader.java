package edu.upenn.cit594.datamanagement;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class populationCSVReader {

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


        String[] row;
        while ((row = reader.readRow()) != null) {
            System.out.println(row);
            String zipCode = row[0];
            Integer pop = Integer.parseInt(row[1]);

            populationMap.put(zipCode, pop);
        }

        return populationMap;

    }

}
