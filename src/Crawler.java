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

    Map<String, List<String>> selectors;
    List<String> links;

    public List<String> getLinks(){
        return links;
    }

    public Map<String, List<String>> getSelectors(){
        return selectors;
    }

    public List<String> getSelectors(String element){
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

    public String generateJSoupQuery(String element){
        String query="";
        List<String> selectorBody = selectors.get(element);
        for(int i = 0; i < selectorBody.size()-1; i++){
            query += selectorBody.get(i) + ",";
        }
        query += selectorBody.get(selectorBody.size()-1); // add last element
        return query;
    }

    public  String process(Document d, String selector){
        String s = d.select(selector).isEmpty() ? "0" : "1";
        return s;
    }

    public  String[] processLink(String link, ArrayList<String> lstSelector){
        String[] results = {};
        try{
            Document d = Jsoup.connect(link).get();

            Set<String> elements = selectors.keySet();
            results = new String[elements.size()];
            String[] aElements = (String[])elements.toArray();

            for(int i = 0; i < aElements.length; i++){
                results[i] = process(d,selectors.get(aElements[i]).toString());
            }
            return results;
        } catch (IOException e){
            System.out.print("An io error occurred with Jsoup");
        }
        return results;
    }

    public String[][] processFile(List<String> links, List<String> lstSelectors){
        String[][] results= {};

        return results;
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

    List<CSVRecord> getCSV(String path){
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
    TreeMap<String, List<String>> extractSelectors (List<CSVRecord> records){
        TreeMap<String, List<String>> selectors = new TreeMap<>();
        for(int i = 0; i < records.size(); i++){
            CSVRecord record = records.get(i);
            String head = (String)record.get(0);
            List<String> body = new ArrayList<>();

            for(int j = 1; j < record.size(); j++){
                body.add(record.get(j));
            }
            selectors.put(head,body);
        }
        return selectors;
    }

    ArrayList<String> extractLinks(){
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
}
