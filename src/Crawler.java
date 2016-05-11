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

    Map<String, String[]> selectors;
    List<String> links;

    public List<String> getLinks(){
        return links;
    }

    public Map<String, String[]> getSelectors(){
        return selectors;
    }

    public String[] getSelectors(String element){
        return selectors.get(element);
    }

    public Crawler(){
        links = extractLinks();

        List<CSVRecord> selectorsCSV = getCSV("./src/Selectors.csv");
        selectors = extractSelectors(selectorsCSV);
    }

    public String[] getElements(){
        Set<String> elements = selectors.keySet();

        return elements.toArray(new String[]{});
    }

    public  void process(Document d, String selector){
        String s = d.select(selector).isEmpty() ? "F" : "T";
        System.out.print(s);
    }

    public  void processLink(String link, ArrayList<String> selectors){
        try{
            Document d = Jsoup.connect(link).get();

            for (String selector : selectors) {
                process(d,selector);
            }
            System.out.println();
        } catch (IOException e){
            System.out.print("An io error occurred with Jsoup");
        }
    }

    public  int findLinksColumn(CSVParser parser, List<CSVRecord> records ){

        CSVRecord headings = records.get(0);
        for(int i = 0; i < headings.size(); i++){
            String entry = headings.get(i);
            if(entry.toLowerCase().equals("url")){
                return i;
            }
        }

        return 0;

    }

    public  ArrayList<String> extractLinks(){
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

    public  List<CSVRecord> getCSV(String path){
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

    public  String[] getHeadings(){
        String[] elements = getElements();
        String [] headings = new String[elements.length+1];
        headings[0] = "Links";

        for(int i = 0; i < elements.length; i++){
            headings[i+1] = elements[i];
        }
        return headings;
    }

    public  String [][] getData(){
        String[][] data = new String[links.size()][selectors.size()];
        for(int i = 0; i < links.size(); i++){
            data[i][0] = links.get(i);
        }
        return data;
    }

    // opens the csv file containing the list of selectors and their elements
    public  TreeMap<String, String[]> extractSelectors (List<CSVRecord> records){
        TreeMap<String, String[]> selectors = new TreeMap<>();
        for(int i = 0; i < records.size(); i++){
            CSVRecord record = records.get(i);
            String head = (String)record.get(0);
            String[] body = new String[record.size()-1];

            for(int j = 1; j < record.size(); j++){
                body[j-1] = record.get(j);
            }
            selectors.put(head,body);
        }
        return selectors;
    }
}
