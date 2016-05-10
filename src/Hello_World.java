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

    public static List<CSVRecord> getCSV(String path){
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

    public static String[][] getTableFill(List<String> links, List<CSVRecord> selectors){
        String[][] filler = new String[links.size()][selectors.size()];

        for(int i = 0; i < links.size(); i++){
            filler[i][0] = links.get(i);
        }

        for(int i = 0; i < selectors.size(); i++){
            CSVRecord record = selectors.get(i);
            filler[0][i+1] = record.get(0);
        }

        return filler;
    }

    public static String[] getHeadings(List<CSVRecord> selectors){
        String [] headings = new String[selectors.size()];
        for(int i = 0; i < selectors.size(); i++) {
            CSVRecord record = selectors.get(i);
            headings[i] = record.get(0);
        }
        return headings;
    }

    public static String [][] getData(List<String> links, List<CSVRecord> selectors){
        String[][] data = new String[links.size()][selectors.size()];
        for(int i = 0; i < links.size(); i++){
            data[i][0] = links.get(i);
        }
        return data;
    }

    public static void main(String[] arg){

        List<String> links = getLinks();

        List<CSVRecord> selectors = getCSV("./src/Selectors.csv");
        //System.out.print(selectors);

        /*
        for(int i = 0; i < links.size(); i++){
            System.out.print(i + ": ");
            processLink(links.get(i),selectors);
        }
        */
        new Hello_World().start(links,selectors);
    }

    public void start(List<String> links, List<CSVRecord> selectors){

        String[] elements = new String[]{"d","e","f"};

        String[] select = new String[]{"a","b","c"};



        add(new CrawlerPanel(elements,select,getData(links,selectors),getHeadings(selectors)));
        setSize(400,400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
