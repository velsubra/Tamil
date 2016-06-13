package test.jsoup;

import my.interest.lang.tamil.TamilUtils;

import my.interest.lang.tamil.impl.job.ExecuteManager;
import my.interest.tamil.util.WebPageSpellChecker;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.junit.Test;
import tamil.lang.TamilFactory;
import tamil.lang.api.job.JobManager;
import tamil.lang.api.job.JobResultChunk;
import tamil.lang.api.job.JobResultSnapShot;

import java.util.List;

/**
 * Created by velsubra on 6/10/16.
 */
public class JsoupTest {

    @Test
    public void testWiki() throws Exception {
        String webPageAddress = "https://ta.wikipedia.org/wiki/%E0%AE%B5%E0%AE%BF%E0%AE%95%E0%AF%8D%E0%AE%95%E0%AE%BF%E0%AE%AA%E0%AF%8D%E0%AE%AA%E0%AF%80%E0%AE%9F%E0%AE%BF%E0%AE%AF%E0%AE%BE";
        int pageSize = 1024;

        Document doc = null;
        if (TamilUtils.isHttpUrl(webPageAddress) || TamilUtils.isHttpsUrl(webPageAddress)) {

            doc = Jsoup.connect(webPageAddress).header("Accept-Encoding", "gzip, deflate")

                    .maxBodySize(pageSize * 1024)
                    .timeout(600000)
                    .get();
            ;
        } else {
            doc = new Document(webPageAddress);
        }
        process(doc);
        System.out.println("*************************");
        System.out.println(doc.html());
    }

    void process(Element elem) {
        List<Node> nodes = elem.childNodes();
        System.out.println("Node # " + nodes.size());
        for (Node node : nodes) {
            if (Element.class.isAssignableFrom(node.getClass())) {
                process((Element) node);
            } else if (TextNode.class.isAssignableFrom(node.getClass())) {
                TextNode text = (TextNode) node;
                String val = text.text();

                System.out.println(val);
                System.out.println("------------------");
                text.text("<p>This is what I have changed</p>");
            }

        }

    }

    @Test
    public void testSpellCheck() throws Exception {
        try {
            ExecuteManager.start();
            JobManager manager = TamilFactory.getJobManager("jobs/browse/spellcheck/webpage");
            long jobid = manager.submit(new WebPageSpellChecker("https://ta.wikipedia.org/wiki/%E0%AE%B5%E0%AE%BF%E0%AE%95%E0%AF%8D%E0%AE%95%E0%AE%BF%E0%AE%AA%E0%AF%8D%E0%AE%AA%E0%AF%80%E0%AE%9F%E0%AE%BF%E0%AE%AF%E0%AE%BE",
                    "browse-submit.gv","browse-view.gv","script.js", "style.css"), JSONObject.class);
            JobResultSnapShot<JSONObject> resultSnapShot = manager.findJobResultSnapShot(jobid, JSONObject.class);

            while (true) {
                if (resultSnapShot == null) {
                    throw new Exception("Not found");
                }
                System.out.println(resultSnapShot.getStatus().getCompletionPercent() + " %");

                if (resultSnapShot.isDone()) break;
                Thread.currentThread().sleep(100);
                resultSnapShot = manager.findJobResultSnapShot(jobid, JSONObject.class);

            }
            String html = resultSnapShot.getProperty(WebPageSpellChecker.PROP_HTML);
            System.out.println(html);
            JobResultChunk<JSONObject> chunk = resultSnapShot.getNewResults(0);

            for (JSONObject object : chunk.getChunk()) {
                System.out.println(object.get(WebPageSpellChecker.PROP_TEXT_ID) +":"+ object.get(WebPageSpellChecker.PROP_TEXT_PROCESSED));
            }

        } finally {
            ExecuteManager.stop();
        }

    }
}
