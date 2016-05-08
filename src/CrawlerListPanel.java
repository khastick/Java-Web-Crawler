public class CrawlerListPanel extends JPanel{
  JList<String> list;
  JButton add, remove;
  
  public CrawlerListPanel(){
    list = new JList<String>();
    
    add = new Jbutton();
    add.setText("add");
    
    remove = new Jbutton();
    remove.add("remove");
  }
  
}
