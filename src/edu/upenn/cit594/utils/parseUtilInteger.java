package edu.upenn.cit594.utils;

public class parseUtilInteger {

    public Integer parseUtilInteger(String input) {
        try {
            Integer output =  Integer.parseInt(input);
            return output;
        }
        catch (NumberFormatException e){
            return null;
        }

    }

}
