import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by Khama on 2016-05-11.
 */
public class Crawler {
    private final String    SELECTOR_PATH = "./src/Selectors.csv",
                            LINKS_PATH = "./src/links.csv",
                            RESULTS_PATH = "./src/results.csv";
    private Map<String, List<String>> selectors;
    private String[][] table;

    /*
    Getters and Setters
     */

    void setTableRow(int index, String[] src) {
        String[] dest = table[index];
        System.arraycopy(src,0,dest,1,src.length);
    }

    List<String> getLinks(){
        List<String> links = new ArrayList<String>();
        for(int i = 0; i < table.length; i++){
            links.add(table[i][0]);
        }
        return links;
    }

    Map<String, List<String>> getSelectors(){
        return selectors;
    }

    String[] getElements(){
        Set<String> elements = selectors.keySet();

        return elements.toArray(new String[]{});
    }

    void setElements(String oldKey, String newKey){
        List selector = selectors.get(oldKey);
        selectors.remove(oldKey);
        selectors.put(newKey,selector);
    }

     String[] getHeadings(){
        String[]    elements = getElements(),
                    headings = new String[elements.length+1];
        headings[0] = "Links";

        for(int i = 0; i < elements.length; i++){
            headings[i+1] = elements[i];
        }
        return headings;
    }

     String [][] getTable(){
         return table;
    }

    /*
    Constructor
     */

    Crawler(){
        List<CSVRecord> selectorsCSV = getCSV(SELECTOR_PATH);
        selectors = loadSelectors(selectorsCSV);

        loadTable();
    }

    /*
    Functions
     */

    void processFiles(File[] files){
        for(int i = 0; i < files.length; i++){
            processFile(files[i]);
        }
    }

    void processFile(File f){
        List<String> links = extractLinks(f);
        String link;
        String[] results;

        for(int i = 0; i < links.size(); i++){
            link = links.get(i);
            results = processLink(link);
            setTableRow(i,results);
        }
    }

     String[] processLink(String link){
        String[] results = {};
        try{
            Document d = Jsoup.connect(link).get();
            Set<String> elements = selectors.keySet();
            results = new String[elements.size()];
            Object[] aElements = elements.toArray();
            String element, query;

            for(int i = 0; i < aElements.length; i++){
                element = (String)aElements[i];
                query = getSelectors().get(element).toString();
                query = query.substring(1,query.length()-1).trim(); // remove array brackets and trim

                if(query.isEmpty()){ // this won't be necessary when all of the elements have related queries
                    results[i] = "0";
                } else {
                    results[i] = d.select(query).isEmpty() ? "0" : "1";
                }
            }
            return results;
        } catch (IOException e){
            System.out.print("An io error occurred with Jsoup");
        }
        return results;
    }

    void save(){
        saveSelectors();
        saveResults();
    }

    /*
    Private helper functions
     */
    private int findLinksColumn(CSVParser parser, List<CSVRecord> records ){
        CSVRecord headings = records.get(0);
        for(int i = 0; i < headings.size(); i++){
            String entry = headings.get(i);
            if(entry.toLowerCase().equals("url")){
                return i;
            }
        }

        return 0;
    }

    private List<CSVRecord> getCSV(String path){
        File csvData = new File(path);
        CSVParser parser;

        try {
            parser = CSVParser.parse(csvData, Charset.defaultCharset(),CSVFormat.DEFAULT);
            return parser.getRecords();
        } catch (IOException e) {
            System.out.print("An io error occurred when trying to open to file " + path);
        }

        return null;
    }

    // opens the csv file containing the list of selectors and their elements
    private TreeMap<String, List<String>> loadSelectors (List<CSVRecord> records){
        TreeMap<String, List<String>> selectors = new TreeMap<>();
        for(int i = 0; i < records.size(); i++){
            CSVRecord record = records.get(i);
            String head = record.get(0);
            List<String> body = new ArrayList<>();

            for(int j = 1; j < record.size(); j++){
                body.add(record.get(j).trim());
            }
            selectors.put(head,body);
        }
        return selectors;
    }

    private void loadTable(){
        File csvData = new File(LINKS_PATH);
        CSVParser parser;
        int linksIndex = 0;
        List<CSVRecord> records;
        String link;

        try {
            parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.DEFAULT);
            records = parser.getRecords();
            linksIndex = findLinksColumn(parser,records);
            table = new String[records.size()-1][getElements().length+1]; // -1 one for the header, +1 for the link

            for(int i = 1; i < records.size(); i++){
                link = records.get(i).get(linksIndex);
                table[i-1][0] = link;
            }
        } catch (IOException e) {
            System.out.print("An io error occurred when retrieving the list of links");
        }
    }

    private List<String> extractLinks(File f){
        CSVParser parser;
        int linksIndex = 0;
        List<CSVRecord> records;
        String link;
        List<String> links = new ArrayList<String>();

        try {
            parser = CSVParser.parse(f, Charset.defaultCharset(), CSVFormat.DEFAULT);
            records = parser.getRecords();
            linksIndex = findLinksColumn(parser,records);
            for(int i = 0; i < records.size(); i++){
                link = records.get(i).get(linksIndex);
                links.add(link);
            }
            return links;
        } catch (IOException e) {
            System.out.print("An io error occurred when retrieving the list of links");
        }
        return links;
    }

    private void saveSelectors(){
        File f = new File(SELECTOR_PATH);
        try{
            FileWriter writer = new FileWriter(f);
            CSVPrinter printer = new CSVPrinter(writer,CSVFormat.DEFAULT);
            Object[]    elements = selectors.keySet().toArray();
            List<String> queries;
            String element;

            for(int i = 0; i < elements.length; i++){
                element = (String)elements[i];
                queries = selectors.get(element);
                queries.add(0,element);
                printer.printRecord(queries);
                printer.flush();
            }
            printer.close();
            writer.close();
        } catch (IOException e){
            System.out.println("there was an issue when saving the selectors");
        }
    }

    private void saveResults(){
        File f = new File(RESULTS_PATH);
        try {
            FileWriter writer = new FileWriter(f,true);
            CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);

            for(int i = 0; i < table.length; i++){
                printer.printRecord(table[i]);
                printer.flush();
            }
            printer.close();
            writer.close();
        } catch (IOException e){
            System.out.println("There was an issue when saving the results table");
        }

    }
}
