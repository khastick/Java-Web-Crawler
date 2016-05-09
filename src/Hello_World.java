import com.sun.deploy.util.ArrayUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.jsoup.nodes.*;

import javax.swing.*;

/**
 * Created by Khama on 2016-04-12.
 */
public class Hello_World extends JFrame{
    // decompose
    public static void process(Document d, String selector){
        String s = d.select(selector).isEmpty() ? "F" : "T";
        System.out.print(s);
    }

    public static void processLink(String link, ArrayList<String> selectors){
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

    public static int findLinksColumn(CSVParser parser, List<CSVRecord> records ){

            CSVRecord headings = records.get(0);
            for(int i = 0; i < headings.size(); i++){
                String entry = headings.get(i);
                if(entry.toLowerCase().equals("url")){
                    return i;
                }
            }

       return 0;

    }

    public static ArrayList<String> getLinks(){
        String pathToLinks = "./src/links.csv";

        File csvData = new File(pathToLinks);
        CSVParser parser;
        int linksIndex = 0;
        ArrayList<String> links = new ArrayList<String>();
        List<CSVRecord> records;

        try {
            parser = CSVParser.parse(csvData, Charset.defaultCharset(),CSVFormat.DEFAULT);
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

    public static ArrayList<String> getS(){
        String path = "./src/Selectors";

        File csvData = new File(pathToLinks);
        CSVParser parser;
        int linksIndex = 0;
        ArrayList<String> links = new ArrayList<String>();
        List<CSVRecord> records;

        try {
            parser = CSVParser.parse(csvData, Charset.defaultCharset(),CSVFormat.DEFAULT);
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

    public static HashMap<String,String[]> getSelectors(){

        String pathToSelectors = "./src/Selectors";
        HashMap<String,String[]> decompMap = new HashMap<>();

        try{
            for (String fileLine : Files.readAllLines(Paths.get(pathToSelectors))) {
                String[] line = fileLine.split(",");
                String element = line[0];
                String[] selectors = Arrays.copyOfRange(line,1,line.length);
                decompMap.put(element, selectors);
            }
        } catch (IOException e){
            System.out.print("An io error occurred when receiving the selectors");
        }

        return selectors;
    }

    public static void main(String[] arg){

        ArrayList<String> links = getLinks();

        ArrayList<String> selectors = getSelectors();
        /*
        for(int i = 0; i < links.size(); i++){
            System.out.print(i + ": ");
            processLink(links.get(i),selectors);
        }
        */
        new Hello_World().start();
    }

    public void start(String[] links, String[] selectors){

        String[] selectors = new String[]{"a","b","c"}, elements = new String[]{"d","e","f"};

        String[] headings = new String[]{"links"} + elements;
        ArrayList<String> h;
        h.addAll()
        String[][] results = new String[][]{
                {"1","2","3"},
                {"4","5","6"},
                {"7","8","9"}
        };

        add(new CrawlerPanel(elements,selectors,results,headings));
        setSize(400,400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
