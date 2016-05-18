import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
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

    void elementEditAction(JList list){
        String oldElement = (String)list.getSelectedValue(),
                newElement = JOptionPane.showInputDialog("Edit Element",oldElement);
        crawler.setElements(oldElement,newElement);
        list.setListData(crawler.getElements());
    }

    void selectorEditAction(JList lstElement, JList lstSelector){
        String element = (String)lstElement.getSelectedValue(),
                selectedSelector,
                selector;
        Map<String, List<String>> selectors = crawler.getSelectors();
        List<String> selectorBody = selectors.get(element);

        selectedSelector = (String)lstSelector.getSelectedValue();
        selector = JOptionPane.showInputDialog("Edit Selector",selectedSelector);

        int index = selectorBody.indexOf(selectedSelector);
        selectorBody.set(index,selector);
        lstSelector.setListData(selectorBody.toArray());
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
        String selected;

        selected = (String)list.getSelectedValue();
        List<String> selectorBody = selectors.get(selected);
        lstSelector.setListData(selectorBody.toArray());
    }

    void processAction(){
        JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(this);
        File[] files = fc.getSelectedFiles();
        crawler.processFiles(files);
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
        JButton add, remove, edit, process;

        remove = crawlerPanel.getListElements().getRemove();
        add = crawlerPanel.getListElements().getAdd();
        edit = crawlerPanel.getListElements().getEdit();

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

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elementEditAction(elementList);
            }
        });
        remove = crawlerPanel.getListSelectors().getRemove();
        add = crawlerPanel.getListSelectors().getAdd();
        edit = crawlerPanel.getListSelectors().getEdit();

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

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectorEditAction(elementList,selectorList);
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
        setSize(800,700);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
