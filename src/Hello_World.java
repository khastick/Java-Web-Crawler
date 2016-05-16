import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Created by Khama on 2016-04-12.
 */
public class Hello_World extends JFrame{

    private Crawler crawler;
    private CrawlerPanel crawlerPanel;
    int selected;

    /*
    Main
     */
    public static void main(String[] arg){
        new Hello_World().start();
    }

    void elementAddAction(JList list){
        String element = JOptionPane.showInputDialog("Add Element");
        Map<String, List<String>> selectors = crawler.getSelectors();
        selectors.put(element,new ArrayList<String>());
        list.setListData(crawler.getElements());
    }

    void elementRemoveAction(JList list){
        String element = (String)list.getSelectedValue();
        crawler.getSelectors().remove(element);
        list.setListData(crawler.getElements());
    }

    void selectorAddAction(JList lstElement, JList lstSelector){
        String selector = JOptionPane.showInputDialog("Add Selector");
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

    void processAction(){
        List<String> links = crawler.getLinks();
        String link;
        String[] results;

            for(int i = 0; i < links.size(); i++){
                link = links.get(i);
                results = crawler.processLink(link);
                crawler.setTableRow(i,results);
                crawlerPanel.setTableRow(i,results);
            }
    }

    public void start(){
        crawler = new Crawler();
        crawlerPanel = new CrawlerPanel(
                crawler.getElements(),
                new String[]{},
                crawler.getTable(),
                crawler.getHeadings()
        );
        JList   elementList = crawlerPanel.getListElements().getList(),
                selectorList = crawlerPanel.getListSelectors().getList();
        Map<String, List<String>> selectors = crawler.getSelectors();
        JButton add, remove, process;

        remove = crawlerPanel.getListElements().getRemove();
        add = crawlerPanel.getListElements().getAdd();
        process = crawlerPanel.getProcess();

        process.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processAction();
            }
        });

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

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                crawler.save();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        add(crawlerPanel);
        setSize(400,400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
