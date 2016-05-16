import org.testng.annotations.Test;

import static org.junit.Assert.*;

/**
 * Created by Khama on 2016-05-13.
 */
public class CrawlerTest {
    @org.junit.Test
    public void process() throws Exception {
        assert(false);
    }

    @org.junit.Test
    public void processLink() throws Exception {
        Crawler c = new Crawler();

        String link = "http://irb-cisr.gc.ca/fra/boacom/pubs/pages/dprrmr1213intern.aspx";
        String[] results = c.processLink(link);
        String[] unexpected = new String[results.length];
        for(int i = 0; i < unexpected.length; i++){
            unexpected[i] = "0";
        }
        assert(results != unexpected);
    }

    @org.junit.Test
    public void processFile() throws Exception {
        assert(false);
    }

}