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

    void elementAddAction(JList list){
        String element = (String)JOptionPane.showInputDialog("Add Element");
        Map<String, List<String>> selectors = crawler.getSelectors();
        selectors.put(element,new ArrayList<String>());
        list.setListData(crawler.getElements());
    }

    void elementRemoveAction(JList list){
        String element = (String)list.getSelectedValue();
        crawler.selectors.remove(element);
        list.setListData(crawler.getElements());
    }

    void selectorAddAction(JList lstElement, JList lstSelector){
        String selector = (String)JOptionPane.showInputDialog("Add Selector");
        String element = (String)lstElement.getSelectedValue();
        Map<String, List<String>> selectors = crawler.getSelectors();
        List<String> selectorBody = selectors.get(element);

        selectorBody.add(selector);
        lstSelector.setListData(selectorBody.toArray());
    }

    void selectorRemoveAction(JList lstElement, JList lstSelector){
        String selector = (String)lstSelector.getSelectedValue(),
                element = (String)lstElement.getSelectedValue();
        Map<String, List<String>> selectors = crawler.getSelectors();
        List<String> selectorBody = selectors.get(element);

        selectorBody.remove(selector);
        lstSelector.setListData(selectorBody.toArray());
    }

    void elementListSelect(ListSelectionEvent e, Map<String,List<String>> selectors,JList lstSelector ){
        JList list = (JList)e.getSource();
        String listSelected;

        selected = list.getSelectedIndex();
        listSelected = (String)list.getSelectedValue();
        List<String> selectorBody = selectors.get(listSelected);
        lstSelector.setListData(selectorBody.toArray());
    }

    public void start(){
        crawler = new Crawler();
        crawlerPanel = new CrawlerPanel(crawler.getElements(),new String[]{},crawler.getData(),crawler.getHeadings());
        JList elementList = crawlerPanel.getListElements().getList(),
                selectorList = crawlerPanel.getListSelectors().getList();
        Map<String, List<String>> selectors = crawler.getSelectors();
        JButton add, remove;

        remove = crawlerPanel.getListElements().getRemove();
        add = crawlerPanel.getListElements().getAdd();

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elementAddAction(elementList);
            }
        });

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elementRemoveAction(elementList);
            }
        });

        remove = crawlerPanel.getListSelectors().getRemove();
        add = crawlerPanel.getListSelectors().getAdd();

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectorAddAction(elementList,selectorList);
            }
        });

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectorRemoveAction(elementList,selectorList);
            }
        });

        elementList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                elementListSelect(e,selectors,selectorList);
            }
        });

        add(crawlerPanel);
        setSize(400,400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
