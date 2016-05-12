import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

/**
 * Created by Khama on 2016-05-06.
 */
public class CrawlerPanel extends JPanel {

    JScrollPane tableScroll, listSelectorsScroll, listElement;
    JTable tableResults;
    CrawlerListPanel listSelectors, listElements;
    JButton butProcess;

    public JButton getButProcess() {
        return butProcess;
    }

    public CrawlerListPanel getListSelectors() {
        return listSelectors;
    }

    public CrawlerListPanel getListElements() {
        return listElements;
    }

    public JTable getTableResults() {
        return tableResults;
    }

    public CrawlerPanel(String[] elements, String[] selectors, String[][] results, String[] headings){
        tableResults = new JTable(results, headings);
        tableResults.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableResults.setRowHeight(20);
        TableColumn column = null;
        for (int i = 0; i < 5; i++) {
            column = tableResults.getColumnModel().getColumn(i);
            column.setPreferredWidth(100);
        }

        butProcess = new JButton("Process");
        listSelectors = new CrawlerListPanel(selectors);
        listElements = new CrawlerListPanel(elements);
        tableScroll = new JScrollPane(tableResults);

        add(butProcess);
        add(listElements);
        add(listSelectors);
        add(tableScroll);
    }
}
