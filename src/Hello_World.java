import com.sun.deploy.util.ArrayUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jsoup.Jsoup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import org.jsoup.nodes.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Created by Khama on 2016-04-12.
 */
public class Hello_World extends JFrame{

    Crawler crawler;
    CrawlerPanel crawlerPanel;
    int selected;

    public static void main(String[] arg){
        /*
        for(int i = 0; i < links.size(); i++){
            System.out.print(i + ": ");
            processLink(links.get(i),selectors);
        }
        */

        new Hello_World().start();
    }

    public void start(){
        crawler = new Crawler();
        crawlerPanel = new CrawlerPanel(crawler.getElements(),new String[]{},crawler.getData(),crawler.getHeadings());
        JList elementList = crawlerPanel.getListElements().getList(),
                selectorList = crawlerPanel.getListSelectors().getList();
        Map<String, String[]> selectors = crawler.getSelectors();
        JButton add, remove;
        remove = crawlerPanel.getListElements().getRemove();

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedIndex = elementList.getSelectedIndex();
                if(selectedIndex != -1 ){
                    elementList.remove(selectedIndex);
                }

            }
        });

        elementList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                JList list = (JList)e.getSource();
                String listSelected;

                selected = list.getSelectedIndex();
                listSelected = (String)list.getSelectedValue();
                String[] selectorBody = selectors.get(listSelected);
                selectorList.setListData(selectorBody);
            }
        });


        add(crawlerPanel);
        setSize(400,400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
