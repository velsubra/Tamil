package my.interest.lang.tamil;

import tamil.util.regex.SimpleMatcher;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.util.JAXBSource;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TamilUtils extends EzhuththuUtils {


    public final static long ONE_SECOND = 1000;
    public final static long SECONDS = 60;

    public final static long ONE_MINUTE = ONE_SECOND * 60;
    public final static long MINUTES = 60;

    public final static long ONE_HOUR = ONE_MINUTE * 60;
    public final static long HOURS = 24;

    public final static long ONE_DAY = ONE_HOUR * 24;

    public final static long ONE_YEAR = ONE_DAY * 365;

    private TamilUtils() {

    }

    public static  InputStream getInputStreamOverProxy(String u) throws Exception{

        System.out.println("Reading url from Tamil Utils  :" + u);
        java.net.URL url = new java.net.URL(u);
        System.out.println("Reading url from Tamil Utils external form  :" +url.toExternalForm());
        System.out.println("Reading url from Tamil Utils string form  :" +url.toString());
        InputStream in = null;
        String proxy = System.getProperty("http.proxyHost");
        int port = 80;
        if (proxy != null && u.toLowerCase().startsWith("http")) {
            in = url.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxy, port))).getInputStream();
        } else {
            in = url.openConnection().getInputStream();
        }
        return  in;
    }

    public static boolean isEmpty(String value) {
        return (value == null || value.trim().length() == 0);
    }

    public static <T> T deSerializeNonRootElement(String filePath, Class<T> clazz) throws Exception {
        return deSerializeNonRootElement(new FileInputStream(filePath), clazz);
    }

    public static <T> T deSerializeNonRootElement(InputStream in, Class<T> clazz) {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Thread.currentThread().setContextClassLoader(clazz.getClassLoader());
            return context.createUnmarshaller().unmarshal(new StreamSource(in), clazz).getValue();
        } catch (Exception e1) {
            throw new RuntimeException(e1);
        } finally {
            Thread.currentThread().setContextClassLoader(loader);
        }
    }

    public static <T> T deSerializeNonRootElement(JAXBContext context, ClassLoader cl, InputStream in, Class<T> clazz) throws Exception {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(cl);
            return context.createUnmarshaller().unmarshal(new StreamSource(in), clazz).getValue();
        } finally {
            Thread.currentThread().setContextClassLoader(loader);
        }
    }


    public static String toXMLJAXB(Object object) {
        if (object == null) return null;
        try {
            return new String(toXMLJAXBData(object), TamilUtils.ENCODING);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean isValidJAXBClass(Class aClass) {

        return aClass.getAnnotation(XmlType.class) != null;


    }


    public static Object deSerialize(JAXBContext context, ClassLoader cl, InputStream in) throws Exception {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(cl);
            return context.createUnmarshaller().unmarshal(in);
        } finally {
            Thread.currentThread().setContextClassLoader(loader);
        }
    }


    public static <T> T deepCopyJAXB(T object, Class<T> clazz) {
        if (object == null) return null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            JAXBElement<T> contentObject = new JAXBElement<T>(new QName(clazz.getSimpleName()), clazz, object);
            JAXBSource source = new JAXBSource(jaxbContext, contentObject);
            return jaxbContext.createUnmarshaller().unmarshal(source, clazz).getValue();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }


    public static byte[] toXMLJAXBData(Object object) {
        if (object == null) return null;
        try {
            Class clazz = object.getClass();
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            JAXBElement contentObject = new JAXBElement(new QName(clazz.getSimpleName()), clazz, object);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            marshaller.marshal(contentObject, out);
            byte[] data = out.toByteArray();
            return data;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean isHttpUrl(String urlString) throws Exception {
        return isProtocolMatching(urlString, "http");
    }

    public static boolean isHttpsUrl(String urlString) {
        try {
            return isProtocolMatching(urlString, "https");
        } catch (Exception var2) {

            return false;
        }
    }

    public static boolean isFtpUrl(String urlString) throws Exception {
        return isProtocolMatching(urlString, "ftp");
    }

    public static boolean isProtocolMatching(String urlStr, String pcol) throws Exception {
        if (urlStr == null) {
            return false;
        } else {
            URL url = null;

            try {
                url = new URL(urlStr);
            } catch (MalformedURLException var4) {
                return urlStr.startsWith(pcol + ":");
            }

            String p = url.getProtocol();
            return p == null ? false : p.equalsIgnoreCase(pcol);
        }
    }


    public static boolean isFileExistingNFS(File f) {

        try {
            InputStream in = new FileInputStream(f);
            try {
                in.close();
            } catch (Exception e) {

            }
            return true;
        } catch (FileNotFoundException fne) {
            return false;
        } catch (Exception e) {
            return f.exists();
        }
    }

    public static int[] flattenList(List<int[]> list) {
        int length = 0;
        for (int[] representation : list) {
            length += representation.length;
        }
        int[] full = new int[length];
        int index = 0;
        for (int[] representation : list) {
            for (int codepoint : representation) {
                full[index++] = codepoint;
            }

        }
        return full;
    }


    public static String toRxGroupName(String str) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch >= 'a' && ch <= 'z') {
                buffer.append(ch);
            } else if (ch >= 'A' && ch <= 'Z') {
                buffer.append(ch);
            } else if (ch >= '0' && ch <= '0') {
                buffer.append(ch);
            } else {
                buffer.append("0");
            }
        }
        return buffer.toString();
    }

    /**
     * converts time (in milliseconds) to human-readable format
     * "<w> days, <x> hours, <y> minutes and (z) seconds"
     */

    public static String millisToLongDHMS(long duration) {
        return millisToLongDHMS(duration, true);
    }

    public static String millisToLongDHMS(long duration, boolean includemillis) {
        if (duration < 0) {
            return "[Clock not in sync]";
        }
        StringBuffer res = new StringBuffer();
        long temp = 0;

        if (duration >= ONE_SECOND) {
            temp = duration / ONE_YEAR;
            if (temp > 0) {
                duration -= temp * ONE_YEAR;
                res.append(temp).append(" year").append(temp > 1 ? "s" : "").append(duration >= ONE_MINUTE ? ", " : "");
            }

            temp = duration / ONE_DAY;
            if (temp > 0) {
                duration -= temp * ONE_DAY;
                res.append(temp).append(" day").append(temp > 1 ? "s" : "").append(duration >= ONE_MINUTE ? ", " : "");
            }

            temp = duration / ONE_HOUR;
            if (temp > 0) {
                duration -= temp * ONE_HOUR;
                res.append(temp).append(" hour").append(temp > 1 ? "s" : "").append(duration >= ONE_MINUTE ? ", " :
                        "");
            }

            temp = duration / ONE_MINUTE;
            if (temp > 0) {
                duration -= temp * ONE_MINUTE;
                res.append(temp).append(" minute").append(temp > 1 ? "s" : "");
            }

            if (!res.toString().equals("") && duration >= ONE_SECOND) {
                if (includemillis) {
                    res.append(", ");
                } else {
                    res.append(" and ");
                }
            }

            temp = duration / ONE_SECOND;
            if (temp > 0) {
                duration -= temp * ONE_SECOND;
                res.append(temp).append(" second").append(temp > 1 ? "s" : "");
            }
            if (includemillis) {
                if (!res.toString().equals("")) {
                    res.append(" and ");
                }

                res.append(duration).append(" ms");

            }
            return res.toString();
        } else {
            return duration + " ms";
        }
    }


//    public static boolean isUyarThinhaipPeyar(TamilWord w) {
//        //E.gகலாம் -உயர்திணை
//        List<IKnownWord> list = PersistenceInterface.findMatchingDerivedWords(w, true, 5, peyar);
//        boolean isUyarthinai = false;
//        for (IKnownWord ipeyar : list) {
//            if (((IPeyarchchol) ipeyar).isUyarThinhai()) {
//                isUyarthinai = true;
//                break;
//            }
//        }
//        return isUyarthinai;
//    }


    public static SimpleMatcher transpose(SimpleMatcher matcher) {
        return new SimpleMatcherTransposed0(matcher);
    }

    private static class SimpleMatcherTransposed0 implements SimpleMatcher {

        SimpleMatcher wrapped = null;
        private int lastMatchPointerStart = 0;
        private int lastMatchPointerEnd = 0;

        private int previousStart = 0;
        private boolean finished = false;

        SimpleMatcherTransposed0(SimpleMatcher wrapped) {
            this.wrapped = wrapped;
        }

        @Override
        public boolean find() {
            while (!finished) {

                boolean yes = wrapped.find();
                lastMatchPointerStart = previousStart;
                if (yes) {

                    previousStart = wrapped.end();
                    if (lastMatchPointerStart == wrapped.start()) {

                        continue;
                    } else {

                        lastMatchPointerEnd = wrapped.start();
                        return true;
                    }
                } else {
                    finished = true;
                    lastMatchPointerEnd = getSourceLength();
                    return lastMatchPointerStart < lastMatchPointerEnd;
                }
            }
            lastMatchPointerStart = lastMatchPointerEnd;
            return false;
        }

        @Override
        public int start() {

            return lastMatchPointerStart;
        }

        @Override
        public int end() {

            return lastMatchPointerEnd;
        }

        @Override
        public String getPattern() {
            return wrapped.getPattern();
        }

        @Override
        public boolean isTransposed() {
            return !wrapped.isTransposed();
        }


        public int getSourceLength() {
            return wrapped.getSourceLength();
        }
    }

}
