package my.interest.tamil.util;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import my.interest.lang.tamil.EncodingUtil;
import my.interest.lang.tamil.StringUtils;
import my.interest.lang.tamil.TamilUtils;
import my.interest.lang.tamil.impl.PropertyFinderForResource;
import my.interest.lang.util.NameValuePair;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import org.jsoup.select.Elements;
import tamil.lang.TamilCharacterLookUpContext;
import tamil.lang.TamilFactory;
import tamil.lang.api.job.JobContext;
import tamil.lang.api.job.JobManager;
import tamil.lang.api.job.JobResultSnapShot;
import tamil.lang.api.job.JobRunnable;

import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by velsubra on 6/10/16.
 */
public abstract class AbstractJSoupJob implements JobRunnable<JSONObject> {

    private String dataUrl = null;
    private String submiturl = null;
    private String viewurl = null;
    private String scripturl = null;
    private String cssurl = null;

    public static final String  tamil_result_id_prefix ="tamil_result_id";

    public AbstractJSoupJob(String dataUrl, String submiturl, String viewurl, String scripturl, String cssurl) {
        this.dataUrl = dataUrl;
        this.submiturl = submiturl;
        this.viewurl = viewurl;
        this.scripturl = scripturl;
        this.cssurl = cssurl;

    }

    public static final String PROP_HTML = "html";
    public static final String PROP_URL = "url";
    public static final String PROP_HOTSPOTS_COUNT = "count";

    public static final String PROP_TEXT_ID = "textId";
    //public static final String PROP_TEXT_ORIGINAL = "textOriginal";
    public static final String PROP_TEXT_PROCESSED = "textProcessed";

    static int pageSize_KB = 1024;
    List<JSONObject> extracts = new ArrayList<JSONObject>();


    void processText(Element elem) throws Exception {
        List<Node> nodes = elem.childNodes();
        Node title  = elem.ownerDocument().getElementsByTag("title").first();

        for (Node node : nodes) {
            if (title == node)  {
                continue;
            };
            if (Element.class.isAssignableFrom(node.getClass())) {
                processText((Element) node);
            } else if (TextNode.class.isAssignableFrom(node.getClass())) {
                TextNode text = (TextNode) node;
                String val = text.text();
                if (val != null && isToProcess(val)) {

                    JSONObject object = new JSONObject();
                    String id = String.valueOf(extracts.size());
                    object.put(PROP_TEXT_ID, id);
                    object.put(PROP_TEXT_PROCESSED, val);
                    // object.put(PROP_TEXT_ORIGINAL, val);


                    Element p = elem.ownerDocument().createElement("span");
                    p.attr("id", "tamil_" + id);
                    p.text("...");
                    text.replaceWith(p);
                    extracts.add(object);
                }

            }

        }

    }

    void replaceLinks(Document doc) throws Exception {
        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");

        for (Element src : media) {
            String absSrc = src.attr("abs:src");
            src.attr("src", absSrc);
        }
        String last = null;
        for (Element link : links) {
            String abs = link.attr("abs:href");
            String absDecoded = java.net.URLDecoder.decode(abs, TamilUtils.ENCODING);

            NameValuePair<Long, Integer> processed = null;
            if (!absDecoded.equals(last)) {
                processed = url_To_JobId_To_Count.get(absDecoded);
            }
            String url = this.submiturl;
            if (!new URI(url).isAbsolute()) {
                url = "http://"+ url;
            }
            URL submit = new URL(url);
            String query = submit.getQuery();
            if (query == null) {
                query ="?au=" + URLEncoder.encode( EncodingUtil.encode(abs), TamilUtils.ENCODING);
            } else {
                query =   "&au=" +  URLEncoder.encode( EncodingUtil.encode(abs), TamilUtils.ENCODING);
            }
            link.attr("href",  this.submiturl + query);



            if (processed != null) {
                Element sup = doc.createElement("sup");
                Element a = doc.createElement("a");
                a.attr("class", "latest");
                a.attr("title", " ஏற்கனவே செயல்படுத்தப்பட்ட முடிவுகளுக்கு இங்கேசுட்டவும்:  "+ absDecoded);
                a.attr("href", this.viewurl + "?jobid=" + processed.getName());
                a.text("(?# " + processed.getValue().toString() + ")");
                sup.appendChild(a);
                link.before(sup);

            }
//            Element sub = doc.createElement("sub");
//            Element a = doc.createElement("a");
//           // a.attr("class", "actual_link");
//           // a.attr("target", "_blank");
//           // a.attr("title", "மெய்யானசுட்டி");
//            a.attr("href", abs);
//            a.text("------------");
//            sub.appendChild(a);
//           link.before(sub);

            last = absDecoded;

        }

        for (Element imp : imports) {
            String abs = imp.attr("abs:href");
            imp.attr("href", abs);
        }
    }

    /**
     * returns the processed string and the number of processed points.
     *
     * @param text
     * @return
     * @throws Exception
     */
    public abstract NameValuePair<String, Integer> process(String text) throws Exception;

    public boolean isToProcess(String text) throws Exception {
        return TamilCharacterLookUpContext.isPotentiallyTamilText(text);
    }


    private void setRefRefreshMessage(JobContext<JSONObject> context, String message) {
        context.setServerProperty(PROP_HTML, "<html><head>\n" +
                "<meta http-equiv=\"refresh\" content=\"2\">\n" +
                "</head><body>காத்திருங்கள் ..." + message + "  </body></html>");
        context.setStatusMessage(message);
    }

    private Map<String, NameValuePair<Long, Integer>> url_To_JobId_To_Count = new HashMap<String, NameValuePair<Long, Integer>>();

    private void loadLastProcessed(JobContext<JSONObject> context)throws Exception {
        JobManager manager = TamilFactory.getJobManager(context.getJobCategory());
        List<Long> list = manager.listJobIds(100);
        for (long id : list) {
            JobResultSnapShot<JSONObject> object = manager.findJobResultSnapShot(id, JSONObject.class);
            if (object == null) {
                continue;
            }
            String url = object.getClientProperty(PROP_URL);
            if (url != null) {
                String absDecoded = java.net.URLDecoder.decode(url, TamilUtils.ENCODING);
                String countStr = object.getClientProperty(PROP_HOTSPOTS_COUNT);
                int count = 0;
                if (countStr != null) {
                    count = Integer.parseInt(countStr);
                }
                NameValuePair<Long, Integer> already_loaded = url_To_JobId_To_Count.get(absDecoded);
                if (already_loaded != null) {
                    if (already_loaded.getName() >= id) {
                        continue;
                    }
                }
                url_To_JobId_To_Count.put(absDecoded, new NameValuePair<Long, Integer>(id, count));
            }

        }

    }

    private void insertScript(String src, Document doc) {

        Element script = doc.createElement("script");
        script.attr("src", src);
        script.attr("type", "text/javascript");
        script.attr("charset", TamilUtils.ENCODING);
        Element body = doc.body();
        if (body == null) {
            doc.appendChild(script);
        } else {
            body.appendChild(script);
        }

    }

    private void insertCSS(String src, Document doc) {

        Element script = doc.createElement("link");
        script.attr("href", src);
        script.attr("type", "text/css");
        script.attr("rel", "stylesheet");
        Element head = doc.head();
        if (head == null) {
            doc.appendChild(script);
        } else {
            head.appendChild(script);
        }

    }
    public InputStream getInputStreamOverProxy(String url) throws Exception{
        System.out.println("---------");
        System.out.println(url);
        System.out.println("---------");
        StringBuffer buffer = new StringBuffer();

        for (int i = 0 ; i < url.length() ;i ++) {
            char c = url.charAt(i);
            boolean code = c > 127;
            if (c == '^') {
                code = true;
            }
            if (c == ' ') {
                buffer.append("+");
               continue;
            }

            if (c == '\n') {
                code = true;
            }

            if (code) {
                String coded =java.net.URLEncoder.encode(""+c, TamilUtils.ENCODING);
                buffer.append(coded);
            } else {
                buffer.append((char)c);
            }
        }
        url = buffer.toString();
        Client client = Client.create();
        System.out.println("---- -----");
        System.out.println(url);
        System.out.println("---- -----");
        WebResource resource = client.resource(url);
        ClientResponse resp = resource.get(ClientResponse.class);
        return  resp.getEntityInputStream();
    }
    public void run(JobContext<JSONObject> context) throws Exception {
        loadLastProcessed(context);
        String decodedURL = java.net.URLDecoder.decode(dataUrl, TamilUtils.ENCODING);
        context.setClientProperty(PROP_URL,  decodedURL);
        context.setAutoFlush(true);
        InputStream in = getInputStreamOverProxy(decodedURL);

        Document doc = null;
        try {
            context.setTitleMessage("Processing the page: " + decodedURL);
            doc = Jsoup.parse(in, TamilUtils.ENCODING, dataUrl);

//            if (TamilUtils.isHttpUrl(this.dataUrl) || TamilUtils.isHttpsUrl(this.dataUrl)) {
//
//                doc = Jsoup.connect(this.dataUrl).header("Accept-Encoding", "gzip, deflate")
//
//                        .maxBodySize(pageSize_KB * 1024)
//                        .timeout(600000)
//                        .get();
//                ;
//            } else {
//                doc = new Document(this.dataUrl);
//            }
        } catch (Exception e) {
            context.setTitleMessage("Reading document failed.:" + e.getMessage());
            context.setServerProperty(PROP_HTML, "<html><body> Failed:" + this.dataUrl + "\n<br/> Full message::" + e.toString() + " </body> </html>");
            throw e;
        }
        setRefRefreshMessage(context, "Reading all text");
        context.setTitleId(decodedURL);
        processText(doc);
        setRefRefreshMessage(context, "All text read. Now, processing links");
        replaceLinks(doc);
        setRefRefreshMessage(context, "All links processed.");
        //Insert the script
        // inssertScript("${R_JQUERY_JS_PATH}", doc);
        // inssertScript("${R_PLATFORM_JS_PATH}", doc);
        if (this.scripturl != null) {
            insertScript(this.scripturl + "?jobid=" + context.getJobId(), doc);
        }
        if (this.cssurl != null) {
            insertCSS(this.cssurl, doc);
        }

        String content = doc.html();
//        content = StringUtils.replaceFor$(new String(content), new PropertyFinderForResource(app, resource, bindingMap), false, true).getBytes();

        context.setServerProperty(PROP_HTML, content);


        context.setStatusMessage("Processing  " + extracts.size() + " total text regions ...");

        int count = 0;
        context.setAutoFlush(false);

        for (JSONObject object : extracts) {

            // NameValuePair<String, Integer> processed = process((String) object.get(PROP_TEXT_ORIGINAL));
            NameValuePair<String, Integer> processed = process((String) object.get(PROP_TEXT_PROCESSED));
            if (processed == null || processed.getName() == null) {
                processed = new NameValuePair<String, Integer>("", 0);
            }
           // totalProcessedPoints += processed.getValue();
            context.setClientProperty(PROP_HOTSPOTS_COUNT, String.valueOf(processed.getValue()));
            object.put(PROP_TEXT_PROCESSED, processed.getName());
            context.addResult(object);
            count++;
            context.setStatusMessage("#" + count + " of " + extracts.size() + " text regions .Total Search Results#:" + String.valueOf(processed.getValue()));
            context.setPercentOfCompletion((int) (100.0 * count / extracts.size()));
            context.flush();
        }


    }
}
