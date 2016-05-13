import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by Khama on 2016-05-11.
 */
public class Crawler {

    private Map<String, List<String>> selectors;
    private List<String> links;
    private String[][] table;

    /*
    Getters and Setters
     */

    void setTableRow(int index, String[] row) {
        table[index] = row;
    }

    List<String> getLinks(){
        return links;
    }

    Map<String, List<String>> getSelectors(){
        return selectors;
    }

    List<String> getSelectors(String element){
        return selectors.get(element);
    }

    String[] getElements(){
        Set<String> elements = selectors.keySet();

        return elements.toArray(new String[]{});
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

     String [][] getData(){
        String[][] data = new String[links.size()][selectors.size()+1];
        for(int i = 0; i < links.size(); i++){
            data[i][0] = links.get(i);
        }
        return data;
    }

    /*
    Constructor
     */

    Crawler(){
        links = extractLinks();

        List<CSVRecord> selectorsCSV = getCSV("./src/Selectors.csv");
        selectors = extractSelectors(selectorsCSV);

        initializeTable();
    }

    /*
    Functions
     */

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
    private TreeMap<String, List<String>> extractSelectors (List<CSVRecord> records){
        TreeMap<String, List<String>> selectors = new TreeMap<>();
        for(int i = 0; i < records.size(); i++){
            CSVRecord record = records.get(i);
            String head = record.get(0);
            List<String> body = new ArrayList<>();

            for(int j = 1; j < record.size(); j++){
                body.add(record.get(j));
            }
            selectors.put(head,body);
        }
        return selectors;
    }

    private ArrayList<String> extractLinks(){
        String pathToLinks = "./src/links.csv";
        File csvData = new File(pathToLinks);
        CSVParser parser;
        int linksIndex = 0;
        ArrayList<String> links = new ArrayList<String>();
        List<CSVRecord> records;

        try {
            parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.DEFAULT);
            records = parser.getRecords();
            linksIndex = findLinksColumn(parser,records);

            for (CSVRecord record : records) {
                links.add(record.get(linksIndex));
            }
        } catch (IOException e) {
            System.out.print("An io error occurred when retreiving the list of links");
        }
        links.remove(0); // remove the heading
        return links;
    }

    private void initializeTable(){
        Object[] elements = selectors.keySet().toArray();
        table = new String[links.size()][elements.length];

    }
}
