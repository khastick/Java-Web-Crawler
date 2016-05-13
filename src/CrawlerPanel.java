import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

/**
 * Created by Khama on 2016-05-06.
 */
public class CrawlerPanel extends JPanel {

    private JScrollPane tableScroll, listSelectorsScroll, listElement;
    private JTable tableResults;
    private CrawlerListPanel listSelectors, listElements;
    private JButton process;

    JButton getProcess() {
        return process;
    }

    CrawlerListPanel getListSelectors() {
        return listSelectors;
    }

    CrawlerListPanel getListElements() {
        return listElements;
    }

    JTable getTableResults() {
        return tableResults;
    }

    void setTableRow(int index, String[] row){
        for(int i = 0; i < row.length; i++){
            tableResults.getModel().setValueAt(row[i], index, i+1);
        }
    }

    CrawlerPanel(String[] elements, String[] selectors, String[][] results, String[] headings){
        tableResults = new JTable(results, headings);
        tableResults.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableResults.setRowHeight(20);
        TableColumn column = null;
        for (int i = 0; i < 5; i++) {
            column = tableResults.getColumnModel().getColumn(i);
            column.setPreferredWidth(100);
        }

        process = new JButton("Process");
        listSelectors = new CrawlerListPanel(selectors);
        listElements = new CrawlerListPanel(elements);
        tableScroll = new JScrollPane(tableResults);

        add(process);
        add(listElements);
        add(listSelectors);
        add(tableScroll);
    }
}
