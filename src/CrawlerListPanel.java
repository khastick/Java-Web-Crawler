import javax.swing.*;
import java.awt.*;

public class CrawlerListPanel extends JPanel {
  JList<String> list;
  JPanel buttons;
  JButton add, remove;
  JScrollPane scroller;

  public JList<String> getList() {
    return list;
  }

  public JButton getAdd() {
    return add;
  }

  public JButton getRemove() {
    return remove;
  }

  public CrawlerListPanel(String[] items){
    list = new JList<String>(items);

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
