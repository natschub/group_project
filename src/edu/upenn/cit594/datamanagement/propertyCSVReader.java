package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.utils.propertyData;

import java.io.IOException;
import java.util.*;


public class propertyCSVReader implements propertyReader{
    protected String filename;
    private CSVReader reader;
    private CSVLexer lexer;


    public propertyCSVReader(String filename) {
        this.filename = filename;
    }


        public Map<String, propertyData> getPropertyData() throws IOException, CSVFormatException {
            Map<String, propertyData> propertyDataMap = new HashMap<>();

            CSVLexer lexer = new CSVLexer(filename);
            CSVReader reader = new CSVReader(lexer);

            List<String> headerRow = Arrays.asList(reader.readRow());
            Integer zipcodeIndex = headerRow.indexOf("zip_code");
            Integer propertyValueIndex = headerRow.indexOf("market_value");
            Integer livableAreaIndex = headerRow.indexOf("total_livable_area");
            //System.out.println(zipcodeIndex + " " + propertyValueIndex + " " + livableAreaIndex);

            String[] row;
            while ((row = reader.readRow()) != null) {
                //System.out.println(row[0] + " " + row[73] + " " + row[65] + " " + row[35]);
                if (row[zipcodeIndex] != null && row[zipcodeIndex].length() >4) {
                    String zipCode = row[zipcodeIndex].substring(0, 5);
                    String zipCodeRegex = "[0-9]{5}";
                    if (zipCode.matches(zipCodeRegex)) {
                        Double propertyValue = parseUtilDouble(row[propertyValueIndex]);
                        Double livableArea = parseUtilDouble(row[livableAreaIndex]);


                        if (propertyDataMap.containsKey(zipCode)) {
                            propertyDataMap.get(zipCode).getPropertyValues().add(propertyValue);
                            propertyDataMap.get(zipCode).getLivableAreas().add(livableArea);
                        } else {
                            LinkedList<Double> newPropertyValues = new LinkedList<>();
                            newPropertyValues.add(propertyValue);

                            LinkedList<Double> newLivableAreas = new LinkedList<>();
                            newLivableAreas.add(livableArea);

                            propertyData newPropertyData = new propertyData(zipCode, newPropertyValues, newLivableAreas);
                            propertyDataMap.put(zipCode, newPropertyData);
                        }

                    }
                }
            }

            return propertyDataMap;

        }

    private Double parseUtilDouble(String input) {
        try {
            Double output =  Double.parseDouble(input);
            return output;
        }
        catch (NumberFormatException e){
            return null;
        }



    }


}
