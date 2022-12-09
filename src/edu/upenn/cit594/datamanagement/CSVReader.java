package edu.upenn.cit594.datamanagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class CSVReader {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 5130409650040L;
    private final CSVLexer lexer;

    public CSVReader(CSVLexer lexer) {
        this.lexer = lexer;
    }

    /**
     * This method uses the class's {@code CharacterReader} to read in just enough
     * characters to process a single valid CSV row, represented as an array of
     * strings where each element of the array is a field of the row. If formatting
     * errors are encountered during reading, this method throws a
     * {@code CSVFormatException} that specifies the exact point at which the error
     * occurred.
     *
     * @return a single row of CSV represented as a string array, where each element
     *         of the array is a field of the row; or {@code null} when there are no
     *         more rows left to be read.
     * @throws IOException        when the underlying reader encountered an error
     * @throws CSVFormatException when the CSV file is formatted incorrectly
     */

    public enum STATE {
        START, ESCAPEDFIELD, NOTESCAPEDFIELD
    }

    public String[] readRow() throws IOException, CSVFormatException {

        // System.out.println("STARTING");

        List<String> returnRow = new ArrayList<String>();
        // String[] returnRow = null;

        StringBuilder field = new StringBuilder();

        // init char and int reads;
        int intRead = lexer.read();
        char charRead = (char) intRead;
        char initialCharacter = charRead;
        if (Character.isWhitespace(initialCharacter)) {
            intRead = lexer.read();
            charRead = (char) intRead;
        }
        STATE state = STATE.START;

        while (intRead != -1) {

            switch (state) {
                case START:
                    switch (intRead) {
                        case 44:
                            returnRow.add("");
                            field = new StringBuilder();
                            break;
                        case 34:
                            state = STATE.ESCAPEDFIELD;
                            break;
                        case 13:
                            returnRow.add(field.toString());
                            field = new StringBuilder();
                            return (String[]) returnRow.toArray(new String[0]);
                        case 10:
                            returnRow.add(field.toString());
                            field = new StringBuilder();
                            state = STATE.START;
                            return (String[]) returnRow.toArray(new String[0]);
                        default:
                            field.append(charRead);
                            state = STATE.NOTESCAPEDFIELD;
                            break;
                    }
                    break;

                case NOTESCAPEDFIELD:
                    switch (intRead) {
                        case 44:
                            returnRow.add(field.toString());
                            field = new StringBuilder();
                            //System.out.println(field + " 44");
                            state = STATE.START;
                            break;
                        case 34:
                            //throw new CSVFormatException("you cannot use quotes in a non-escaped field", 0,0,0,0);
                            state = STATE.ESCAPEDFIELD;
                            break;
                        //field.append("'");
                        case 13:
                            returnRow.add(field.toString());
                            state = STATE.START;
                            field = new StringBuilder();
                            return (String[]) returnRow.toArray(new String[0]);
                        case 10:
                            returnRow.add(field.toString());
                            field = new StringBuilder();
                            state = STATE.START;
                            return (String[]) returnRow.toArray(new String[0]);
                        default:
                            field.append(charRead);
                            break;
                    }
                    break;

                case ESCAPEDFIELD:
                    switch (intRead) {
                        case -1:
                            throw new CSVFormatException("you are closing a file while you are in an escaped field", 0, 0, 0, 0);
                        case 34:
                            //int nextInt = lexer.read();
                            state = STATE.NOTESCAPEDFIELD;
                            break;
                        //System.out.println("escaped double quote " + returnRow);state = STATE.NOTESCAPEDFIELD;
                        default:
                            field.append(charRead);
                            break;
                    }
                    break;
            }

            intRead = lexer.read();
            try {
                charRead = (char) intRead;
                //System.out.println(charRead + " " + intRead + " " + state);
            } catch (Exception e) {
                System.out.println("not a char that can be printed");
            }
        }
        return null;

    }

        ///
        /// extra set of cases here
        // return (String[]) returnRow.toArray(new String[0]);
    }





