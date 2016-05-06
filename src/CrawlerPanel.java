import javax.swing.*;

/**
 * Created by Khama on 2016-05-06.
 */
public class CrawlerPanel extends JPanel {
    JList<String> listElements, listSelectors;
    JButton butElementsAdd, butElementsRemove, butSelectorsAdd, butSelectorsRemove;
    JTable tableResults;

    public CrawlerPanel(String[] elements, String[] selectors, String[][] results, String[] headings){

        listElements = new JList<String>(elements);
        listSelectors = new JList<String>(selectors);

        butElementsAdd = new JButton();
        butElementsAdd.setText("Add");

        butElementsRemove = new JButton();
        butElementsRemove.setText("Remove");

        butSelectorsAdd = new JButton();
        butSelectorsAdd.setText("Add");

        butSelectorsRemove = new JButton();
        butSelectorsRemove.setText("Remove ");

        tableResults = new JTable(results, headings);

        add(listElements);
        add(butElementsAdd);
        add(butElementsRemove);

        add(listSelectors);
        add(butSelectorsAdd);
        add(butSelectorsRemove);

        add(tableResults);
    }
}
