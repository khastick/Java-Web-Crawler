import javax.swing.*;
import javax.swing.table.TableColumn;

/**
 * Created by Khama on 2016-05-06.
 */
public class CrawlerPanel extends JPanel {

    JScrollPane tableScroll;
    JTable tableResults;
    CrawlerListPanel listSelectors, listElements;

    public CrawlerPanel(String[] elements, String[] selectors, String[][] results, String[] headings){


        tableResults = new JTable(results, headings);
        tableResults.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableResults.setRowHeight(20);
        TableColumn column = null;
        for (int i = 0; i < 5; i++) {
            column = tableResults.getColumnModel().getColumn(i);
            column.setPreferredWidth(100);
        }

        listSelectors = new CrawlerListPanel(selectors);
        listElements = new CrawlerListPanel(elements);
        tableScroll = new JScrollPane(tableResults);

        add(listElements);
        add(listSelectors);
        add(tableScroll);
    }
}
