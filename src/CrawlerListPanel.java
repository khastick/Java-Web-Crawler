import javax.swing.*;
import java.awt.*;

public class CrawlerListPanel extends JPanel {
  JList<String> list;
  JPanel buttons;
  JButton add, remove;
  
  public CrawlerListPanel(String[] items){
    list = new JList<String>(items);
    
    add = new JButton();
    add.setText("add");
    
    remove = new JButton();
    remove.setText("rm");

    buttons = new JPanel();
    buttons.add(add);
    buttons.add(remove);

    setLayout(new BorderLayout());

    add(list);
    add(buttons, BorderLayout.PAGE_END);
  }
  
}
