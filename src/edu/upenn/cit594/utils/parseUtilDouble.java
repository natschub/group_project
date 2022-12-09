package edu.upenn.cit594.utils;

public class parseUtilDouble {

    public Double parseUtilDouble(String input) {
        try {
            Double output =  Double.parseDouble(input);
            return output;
        }
        catch (NumberFormatException e){
            return null;
        }

    }

}
