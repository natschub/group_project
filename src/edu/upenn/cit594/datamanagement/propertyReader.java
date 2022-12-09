package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.utils.propertyData;

import java.io.IOException;
import java.util.Map;

public interface propertyReader {

    public Map<String, propertyData> getPropertyData() throws IOException, CSVFormatException;

}
