import javax.swing.*;

/**
 * Created by Khama on 2016-05-06.
 */
public class CrawlerPanel extends JPanel {

    JTable tableResults;
    CrawlerListPanel listSelectors, listElements;

    public CrawlerPanel(String[] elements, String[] selectors, String[][] results, String[] headings){

        listSelectors = new CrawlerListPanel(selectors);
        listElements = new CrawlerListPanel(elements);
        tableResults = new JTable(results, headings);

        add(listElements);
        add(listSelectors);
        add(tableResults);
    }
}
