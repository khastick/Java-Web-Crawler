import javax.swing.*;
import java.awt.*;

public class CrawlerListPanel extends JPanel {
  private JList<String> list;
  private JPanel buttons;
  private JButton add;
  private JButton remove;



  private JButton edit;
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

  JButton getEdit() {
    return edit;
  }

  void setButtonEnable(Boolean state){
        remove.setEnabled(state);
    edit.setEnabled(state);
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
    remove.setEnabled(false);

    edit = new JButton();
    edit.setText("edit");
    edit.setEnabled(false);

    buttons = new JPanel();
    buttons.add(add);
    buttons.add(remove);
    buttons.add(edit);

    setLayout(new BorderLayout());

    add(scroller);
    add(buttons, BorderLayout.PAGE_END);
  }
  
}
