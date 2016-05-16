import javax.swing.*;
import java.awt.*;

public class CrawlerListPanel extends JPanel {
  private JList<String> list;
  private JPanel buttons;
  private JButton add, remove;
  private JScrollPane scroller;

  /*
  Accessors
   */
  JList<String> getList() {
    return list;
  }

  JButton getAdd() {
    return add;
  }

  JButton getRemove() {
    return remove;
  }

  /*
  Constructor
   */
  CrawlerListPanel(String[] items){
    list = new JList<>(items);

    scroller = new JScrollPane(list);

    add = new JButton();
    add.setText("add");
    
    remove = new JButton();
    remove.setText("rm");

    buttons = new JPanel();
    buttons.add(add);
    buttons.add(remove);

    setLayout(new BorderLayout());

    add(scroller);
    add(buttons, BorderLayout.PAGE_END);
  }
  
}
