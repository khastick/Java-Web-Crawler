import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;

/**
 * Created by Khama on 2016-05-06.
 */
public class CrawlerPanel extends JPanel {

    private JScrollPane tableScroll;
    private JTable table;
    private CrawlerListPanel listSelectors, listElements;
    private JButton process;

    /*
    Accessors
     */
    JButton getProcess() {
        return process;
    }

    CrawlerListPanel getListSelectors() {
        return listSelectors;
    }

    CrawlerListPanel getListElements() {
        return listElements;
    }

    /*
    Constructor
     */
    CrawlerPanel(String[] elements, String[] selectors, String[][] results, String[] headings){
        table = new JTable(results, headings);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setRowHeight(20);
        TableColumn column = null;
        for (int i = 0; i < 5; i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(100);
        }

        process = new JButton("Process");
        process.setPreferredSize(new Dimension(100,100));

        listSelectors = new CrawlerListPanel(selectors);
        listElements = new CrawlerListPanel(elements);
        tableScroll = new JScrollPane(table);

        add(process);
        add(listElements);
        add(listSelectors);
        add(tableScroll, BorderLayout.LINE_END);
    }
}
