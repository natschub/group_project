package edu.upenn.cit594.datamanagement;

import java.io.IOException;
import java.util.Map;

public interface populationReader {

    public Map<String, Integer> getPopulationData() throws IOException, CSVFormatException;

}
