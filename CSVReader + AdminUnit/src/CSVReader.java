import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CSVReader {
    BufferedReader reader;
    String delimiter;
    boolean hasHeader;
    String[] current;

    List<String> columnLabels = new ArrayList<>();
    Map<String,Integer> columnLabelsToInt = new HashMap<>();

    static final String DELIMITER = ",";
    static final String TIME_FORMAT = "HH:mm";
    static final String DATE_FORMAT = "dd.MM.yyyy";

    CSVReader(String filename) throws IOException {
        this(new BufferedReader(new FileReader(filename)), DELIMITER, true);
    }

    CSVReader(String filename, String delimiter) throws IOException {
        this(new BufferedReader(new FileReader(filename)), DELIMITER, true);
    }

    CSVReader(String filename, String delimiter, boolean hasHeader) throws IOException {
        this.reader = new BufferedReader(new FileReader(filename));
        this.delimiter = delimiter;
        this.hasHeader = hasHeader;
        if(hasHeader) parseHeader();
    }

    CSVReader(Reader reader) throws IOException {
        this(reader, DELIMITER, true);
    }

    CSVReader(Reader reader, String delimiter) throws IOException {
        this(reader,delimiter,true);
    }

    CSVReader(Reader reader, String delimiter, boolean hasHeader) throws IOException {
        this.reader = new BufferedReader(reader);
        this.delimiter = delimiter;
        this.hasHeader = hasHeader;
        if(hasHeader)parseHeader();
    }

    String[] getCurrent(){
        return this.current;
    }

    private void parseHeader() throws IOException {
        if (!next()) {
            return;
        }

        String[] header = new String[current.length];

        for (int i = 0; i < current.length; i++) {
            this.columnLabels.add(current[i]);
            this.columnLabelsToInt.put(current[i], i);
            header[i] = current[i];
        }
    }

    boolean next() throws IOException {
        String line = reader.readLine();
        if(line == null){
            return false;
        }
        current = line.split(this.delimiter + "(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

        return true;
    }

    List<String> getColumnLabels(){
        return columnLabels;
    }

    int getRecordLength(){
        return current.length;
    }

    boolean isMissing(int columnIndex) throws IllegalArgumentException{
        if(columnIndex >= this.columnLabels.size() || columnIndex < 0){
            throw new IllegalArgumentException("Columns index out of bounds!");
        }

        if(columnIndex >= this.getRecordLength()){
            return true;
        }

        return this.current[columnIndex].isEmpty();
    }

    boolean isMissing(String columnLabel) throws IllegalArgumentException{
        if(! columnLabels.contains(columnLabel)){
            throw new IllegalArgumentException("That column does not exists.");
        }
        return isMissing(this.columnLabelsToInt.get(columnLabel));
    }

    public String get(int columnIndex) throws IllegalArgumentException{
        if(this.isMissing(columnIndex)){
            return null;
        }
        return current[columnIndex];
    }

    public String get(String columnLabel) throws IllegalArgumentException{
        if(this.isMissing(columnLabel)){
            return "";
        }
        return current[columnLabelsToInt.get(columnLabel)];
    }

    public int getInt(int columnIndex) throws SecurityException{
        if(this.isMissing(columnIndex)){
            return 0;
        }
        return Integer.parseInt(this.get(columnIndex));
    }

    public int getInt(String columnLabel) throws SecurityException{
        if(this.isMissing(columnLabel)){
            return 0;
        }
        return Integer.parseInt(this.get(columnLabel));
    }

    public long getLong(int columnIndex) throws SecurityException{
        if(this.isMissing(columnIndex)){
            return 0;
        }
        return Long.parseLong(this.get(columnIndex));
    }

    public long getLong(String columnLabel) throws SecurityException{
        if(this.isMissing(columnLabel)){
            return 0;
        }
        return Long.parseLong(this.get(columnLabel));
    }

    public double getDouble(int columnIndex)throws SecurityException {
        if(this.isMissing(columnIndex)){
            return 0;
        }
        return Double.parseDouble(this.get(columnIndex));
    }

    public double getDouble(String columnLabel)throws SecurityException {
        if(this.isMissing(columnLabel)){
            return 0;
        }
        return Double.parseDouble(this.get(columnLabel));
    }


    LocalTime getTime(int columnIndex, String format) {
        return LocalTime.parse(this.get(columnIndex), DateTimeFormatter.ofPattern(format));
    }

    LocalTime getTime(String columnLabel, String format){
        return LocalTime.parse(this.get(columnLabel),DateTimeFormatter.ofPattern(format));
    }

    LocalTime getTime(int columnIndex){
        return LocalTime.parse(this.get(columnIndex), DateTimeFormatter.ofPattern(TIME_FORMAT));
    }

    LocalTime getTime(String columnLabel){
        return LocalTime.parse(this.get(columnLabel),DateTimeFormatter.ofPattern(TIME_FORMAT));
    }

    LocalDate getDate(int columnIndex, String format){
        return LocalDate.parse(this.get(columnIndex), DateTimeFormatter.ofPattern(format));
    }

    LocalDate getDate(String columnLabel, String format){
        return LocalDate.parse(this.get(columnLabel),DateTimeFormatter.ofPattern(format));
    }

    LocalDate getDate(int columnIndex){
        return LocalDate.parse(this.get(columnIndex), DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    LocalDate getDate(String columnLabel){
        return LocalDate.parse(this.get(columnLabel),DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public static void main(String[] args) throws IOException {
        CSVReader reader = new CSVReader("titanic-part.csv",",",true);
        while(reader.next()){
            int id = reader.getInt(0);
            String name = reader.get(3);
            double fare = reader.getDouble(9);
        }
    }
}
