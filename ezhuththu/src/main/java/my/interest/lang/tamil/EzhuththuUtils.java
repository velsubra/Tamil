package my.interest.lang.tamil;

import common.lang.impl.AbstractCharacter;
import my.interest.lang.tamil.generated.types.*;
import my.interest.lang.tamil.generated.types.Properties;
import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.internal.api.TamilCharacterParserListener;
import my.interest.lang.tamil.internal.api.TamilLetterFilter;
import my.interest.lang.tamil.internal.api.TamilSoundParserListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tamil.lang.*;
import tamil.lang.api.regex.RXIncludeCanonicalEquivalenceFeature;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.IPeyarchchol;
import tamil.lang.sound.AtomicSound;
import tamil.lang.sound.TamilSoundLookUpContext;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class EzhuththuUtils {
    public static final String ENCODING = "UTF-8";

    public static final String NS_TAMIL = "http://my.interest.lang.tamil";
    public static final String NS_XML = "http://www.w3.org/2001/XMLSchema";
    public static final String VINAIMUTRU_BASE = "vinaimuttu.transitive";
    public static final String VINAIMUTRU_BASE_INTRANSITIVE = "vinaimuttu.intransitive";


    //This gets the first chance to get cut
    static final String[] wrapCutText = new String[]{" ", "/", "\\", ";", "|", ")", "}", "]", ","};

    //This gets the last chance to break
    static final String[] wrapCutText_finally = new String[]{"_", "-", ".", "=", ":"};


    //Dont cut when the text ends with any of the below for e.g) http://host should not be cut into http:// and  host
    static final String[] wrapCutText_NOT_ENDING = new String[]{"://", ":/"};


    private static List<Class<? extends IKnownWord>> peyar = new ArrayList<Class<? extends IKnownWord>>();

    static {
        peyar.add(IPeyarchchol.class);
    }


    public static PeyarchchchorrkalhDescription getPeyarchchchorrkalhDescription(JSONObject obj) throws JSONException {
        PeyarchchchorrkalhDescription desc = new PeyarchchchorrkalhDescription();
        desc.setGlobalDescription(new Properties());
        desc.setList(new PeyarchchchorrkalhList());
        JSONObject list = obj.getJSONObject("list");
        if (list != null) {
            JSONArray words = list.getJSONArray("word");
            int length = words.length();
            for (int i = 0; i < length; i++) {
                try {
                    desc.getList().getWord().add(gePeyarchcholDescription(words.getJSONObject(i)));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return desc;
    }

    /**
     * {
     * "root": "கலாம்",
     * "description": {
     * "property": [
     * {
     * "name": "definition.type",
     * "value": "p"
     * },
     * {
     * "name": "i.definition.type.p.uyarthinhai",
     * "value": "true"
     * },
     * {
     * "name": "i.definition.type.p.it",
     * "value": "ot"
     * }
     * ],
     * "name": null
     * }
     *
     * @param obj
     * @return
     * @throws JSONException
     */
    public static PeyarchcholDescription gePeyarchcholDescription(JSONObject obj) throws JSONException {
        PeyarchcholDescription peyar = new PeyarchcholDescription();
        peyar.setDescription(new Properties());
        peyar.setRoot(obj.getString("root"));

        JSONObject list = obj.getJSONObject("description");
        if (list != null) {
            JSONArray words = list.getJSONArray("property");
            int length = words.length();

            for (int i = 0; i < length; i++) {
                try {
                    peyar.getDescription().getProperty().add(geProperty(words.getJSONObject(i)));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        return peyar;

    }

    public static Property geProperty(JSONObject obj) throws JSONException {
        Property property = new Property();
        property.setName(obj.getString("name"));
        property.setValue(obj.getString("value"));
        return property;

    }


    public static boolean isUyarThinhai(PaalViguthi viguthi) {
        return (viguthi != PaalViguthi.A) && (viguthi != PaalViguthi.THU);
    }


    public static boolean isPadarkkai(PaalViguthi viguthi) {
        return viguthi == PaalViguthi.A
                || viguthi == PaalViguthi.THU ||
                viguthi == PaalViguthi.AALH ||
                viguthi == PaalViguthi.AAN ||
                viguthi == PaalViguthi.AAR || viguthi ==
                PaalViguthi.AR;
    }

    public static boolean isVinaiMuttuAsNoun(SimpleTense tense, PaalViguthi viguthi) {
        if (!isPadarkkai(viguthi)) return false;
        if (viguthi == PaalViguthi.A) {
            return false;

        }
        if (viguthi == PaalViguthi.THU) {
            if (tense == SimpleTense.FUTURE) {
                return false;
            } else {
                return true;
            }
        }
        if (tense == SimpleTense.PRESENT) return false;
        return true;
    }


    public static byte[] readAllFromFile(String file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return readAllFrom(fis, false);
        } catch (Exception e) {
            throw new RuntimeException("Unable to read file:" + file, e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static <T> T deSerializeNonRootElement(String filePath, Class<T> clazz) throws Exception {
        return  deSerializeNonRootElement(new FileInputStream(filePath), clazz);
    }

    public static <T> T deSerializeNonRootElement(InputStream in, Class<T> clazz) throws Exception {
        JAXBContext context = JAXBContext.newInstance(clazz);
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(clazz.getClassLoader());
            return context.createUnmarshaller().unmarshal(new StreamSource(in), clazz).getValue();
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


    public static Object deSerialize(JAXBContext context, ClassLoader cl, InputStream in) throws Exception {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(cl);
            return context.createUnmarshaller().unmarshal(in);
        } finally {
            Thread.currentThread().setContextClassLoader(loader);
        }
    }


    public static <T> T deserializeJAXB(InputStream in, Class<T> clazz) {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            JAXBElement elm = jaxbContext.createUnmarshaller().unmarshal(new StreamSource(
                    in), clazz);
            return (T) elm.getValue();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
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

    public static String toXMLJAXB(Object object) {
        if (object == null) return null;
        try {
            return new String(toXMLJAXBData(object), EzhuththuUtils.ENCODING);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] fromThrowable(Throwable t) {

        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        try {
            PrintStream pr = new PrintStream(ba, true, ENCODING);
            t.printStackTrace(pr);
            pr.flush();
            ba.flush();
            ba.close();
            pr.close();
        } catch (IOException io) {

        }
        return ba.toByteArray();
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


    public static byte[] readAllFrom(InputStream in, boolean chunked) {

        if (in == null)
            throw new RuntimeException("Stream can't be null");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            byte[] buf = new byte[1024];
            int len;
            len = in.read(buf);
            //System.out.println("READ: " + len + "bytes for the first time");
            while (len >= 0) {

                bos.write(buf, 0, len);
                if (chunked) {
                    byte[] check = new String(bos.toByteArray(), ENCODING).trim().getBytes(ENCODING);

                    if (check.length >= 1) {

                        if (check[check.length - 1] == '0') {
                            if (check.length == 1)
                                break;
                            if ((check[check.length - 2]) == '\n') {
                                break;
                            }

                        }

                    }
                }


                len = in.read(buf);

            }
            bos.flush();
            return bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Unable to read:", e);
        } finally {
            try {
                bos.close();
            } catch (Exception io) {
                io.printStackTrace();
            }

        }


    }


    public static void writeToFile(File file, byte[] content) throws Exception {

        if (content == null)
            return;

        //   System.out.println("-->Writing file at:" + file.getAbsolutePath());
        FileOutputStream out = null;

        if (!file.exists()) {
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                if (!file.getParentFile().mkdirs()) {
                    throw new Exception("Path could not be created.");
                }
            }
            //System.out.println("Creating file at :" + file.getAbsolutePath());
            if (!file.createNewFile()) {
                throw new Exception("File could not be created.");
            }
        }
        try {
            out = new FileOutputStream(file);
            out.write(content);
            out.flush();
        } catch (Exception e) {
            throw e;
        } finally {
            if (out != null) {

                out.close();
            }
        }
    }

    public static void writeToFile(File to, File from) throws Exception {
        if (!to.getCanonicalPath().equalsIgnoreCase(from.getCanonicalPath())) {
            InputStream in = new FileInputStream(from);
            try {
                writeToFile(to, in);
            } finally {
                in.close();
            }
        }
    }

    public static File validateOutputFile(String fileStr, boolean createParent) throws Exception {
        File file = new File(fileStr);
        if (file.exists()) {
            if (!file.isFile())
                throw new Exception("path not a file");
        } else {
            if (createParent && file.getParentFile() != null && !file.getParentFile().exists()) {
                if (!file.getParentFile().mkdirs()) {
                    throw new Exception("Output dir '" + fileStr + "' could not be created.");
                }
            }
        }
        return file;
    }

    private static final List<TamilWord> suggenstionNullifer = new ArrayList<TamilWord>() {
        {
            add(new TamilWord(TamilCompoundCharacter.IL, TamilCompoundCharacter.IL));
            add(new TamilWord(TamilCompoundCharacter.ILL, TamilCompoundCharacter.ILL));
            add(new TamilWord(TamilCompoundCharacter.IK, TamilCompoundCharacter.IK));
            add(new TamilWord(TamilCompoundCharacter.ICH, TamilCompoundCharacter.ICH));
            add(new TamilWord(TamilCompoundCharacter.IDD, TamilCompoundCharacter.IDD));
            add(new TamilWord(TamilCompoundCharacter.ITH, TamilCompoundCharacter.ITH));
            add(new TamilWord(TamilCompoundCharacter.IRR, TamilCompoundCharacter.IRR));
            add(new TamilWord(TamilCompoundCharacter.INJ, TamilCompoundCharacter.INJ));

            add(new TamilWord(TamilSimpleCharacter.U));

        }
    };

    /**
     * 31 is the driving prime number for hash.
     *
     * @param w1 the word for which the hash code is to be found
     * @return the hashcode of the word
     */

    public static int suggestionHashCode(TamilWord w1) {
        TamilWord w = w1.duplicate();
        for (TamilWord sug : suggenstionNullifer) {
            if (sug.size() > 1) {
                w.replaceAll(0, sug, new TamilWord(sug.getLast()), false, true);
            } else {
                w.replaceAll(0, sug, new TamilWord(), false, false);
            }
        }


        int hash = 0;
        for (int i = 0; i < w.size(); i++) {

            AbstractCharacter ch = w.get(i);
            if (ch.isTamilLetter()) {
                TamilCharacter tamil = ch.asTamilCharacter();
                TamilCharacter uyir = null;
                TamilCharacter cons = null;
                if (tamil.isUyirezhuththu()) {
                    uyir = tamil;
                } else if (tamil.isMeyyezhuththu()) {
                    cons = tamil;
                } else if (tamil.isUyirMeyyezhuththu()) {
                    uyir = tamil.getUyirPart();
                    cons = tamil.getMeiPart();
                }


                if (uyir != null) {
                    if (TamilSimpleCharacter.aa.equals(uyir) || TamilSimpleCharacter.I.equals(uyir)) {
                        uyir = TamilSimpleCharacter.a;
                    } else if (TamilSimpleCharacter.EE.equals(uyir)) {
                        uyir = TamilSimpleCharacter.E;
                    } else if (TamilSimpleCharacter.OO.equals(uyir)) {
                        uyir = TamilSimpleCharacter.O;
                    } else if (TamilSimpleCharacter.EE.equals(uyir)) {
                        uyir = TamilSimpleCharacter.E;
                    } else if (TamilSimpleCharacter.UU.equals(uyir)) {
                        uyir = TamilSimpleCharacter.U;
                    } else if (TamilSimpleCharacter.AA.equals(uyir)) {
                        uyir = TamilSimpleCharacter.A;
                    }
                }

                if (cons != null) {
                    if (cons.equals(TamilCompoundCharacter.IDD)) {
                        cons = TamilCompoundCharacter.IR;
                    } else if (cons.equals(TamilCompoundCharacter.ITH)) {
                        cons = TamilCompoundCharacter.IR;
                    } else if (cons.equals(TamilCompoundCharacter.ILL)) {
                        cons = TamilCompoundCharacter.IL;
                    } else if (cons.equals(TamilCompoundCharacter.ILLL)) {
                        cons = TamilCompoundCharacter.IL;
                    } else if (cons.equals(TamilCompoundCharacter.IRR)) {
                        cons = TamilCompoundCharacter.IR;
                    } else if (cons.equals(TamilCompoundCharacter.INNN)) {
                        cons = TamilCompoundCharacter.IN;
                    } else if (cons.equals(TamilCompoundCharacter.INTH)) {
                        cons = TamilCompoundCharacter.IN;
                    }
                }

                if (cons != null) {
                    if (uyir != null) {
                        ch = cons.addUyir((TamilSimpleCharacter) uyir);
                    } else {
                        ch = cons;
                    }
                } else if (uyir != null) {
                    ch = uyir;
                }
            }

            hash = 31 * hash + ch.getNumericStrength();
        }
        return hash;
    }

    public static Set<TamilCharacter> filterTamilCharacters(TamilLetterFilter filter) {

        Set<TamilCharacter> set = new LinkedHashSet<TamilCharacter>();
        Set<TamilCharacter> all = TamilCharacterLookUpContext.getAllTamilCharacters();
        Iterator<TamilCharacter> it = all.iterator();
        while (it.hasNext()) {
            TamilCharacter ch = it.next();
            if (filter.filter(ch)) {
                set.add(ch);
            }
        }
        return set;
    }

    public static Set<TamilCharacter> filterIntersection(Set<TamilCharacter> one, Set<TamilCharacter> two) {
        Set<TamilCharacter> inter = new LinkedHashSet<TamilCharacter>();
        Iterator<TamilCharacter> it = one.iterator();
        while (it.hasNext()) {
            TamilCharacter ch = it.next();
            if (two.contains(ch)) {
                inter.add(ch);
            }
        }
        return inter;

    }

    public static Set<TamilCharacter> filterKuRil() {
        return filterTamilCharacters(new TamilLetterFilter() {
            public boolean filter(TamilCharacter tamil) {
                return tamil.isPureTamilLetter() && tamil.isKurilezhuththu();
            }
        });
    }

    public static Set<TamilCharacter> filterNedil() {
        return filterTamilCharacters(new TamilLetterFilter() {
            public boolean filter(TamilCharacter tamil) {
                return tamil.isPureTamilLetter() && tamil.isNtedilezhuththu();
            }
        });
    }

    public static Set<TamilCharacter> filterUyir() {
        return filterTamilCharacters(new TamilLetterFilter() {
            public boolean filter(TamilCharacter tamil) {
                return tamil.isPureTamilLetter() && tamil.isUyirezhuththu();
            }
        });
    }

    public static Set<TamilCharacter> filterAaytham() {
        return filterTamilCharacters(new TamilLetterFilter() {
            public boolean filter(TamilCharacter tamil) {
                return tamil.isPureTamilLetter() && tamil.isAaythavezhuththu();
            }
        });
    }


    public static Set<TamilCharacter> filterVali() {
        return filterTamilCharacters(new TamilLetterFilter() {
            public boolean filter(TamilCharacter tamil) {
                return tamil.isPureTamilLetter() && tamil.isVallinam();
            }
        });
    }

    public static Set<TamilCharacter> filterVadaMozhi() {
        return filterTamilCharacters(new TamilLetterFilter() {
            public boolean filter(TamilCharacter tamil) {
                return tamil.isVadaMozhiYezhuththu();
            }
        });
    }


    public static Set<TamilCharacter> filterIdai() {
        return filterTamilCharacters(new TamilLetterFilter() {
            public boolean filter(TamilCharacter tamil) {
                return tamil.isPureTamilLetter() && tamil.isIdaiyinam();
            }
        });
    }

    public static Set<TamilCharacter> filterMeli() {
        return filterTamilCharacters(new TamilLetterFilter() {
            public boolean filter(TamilCharacter tamil) {
                return tamil.isPureTamilLetter() && tamil.isMellinam();
            }
        });
    }

    public static Set<TamilCharacter> filterUyirMei() {
        return filterTamilCharacters(new TamilLetterFilter() {
            public boolean filter(TamilCharacter tamil) {
                return tamil.isPureTamilLetter() && tamil.isUyirMeyyezhuththu();
            }
        });
    }

    public static Set<TamilCharacter> filterUyirMeiWithUyir(final TamilCharacter uyir) {
        return filterTamilCharacters(new TamilLetterFilter() {
            public boolean filter(TamilCharacter tamil) {
                return tamil.isPureTamilLetter() && tamil.isUyirMeyyezhuththu() && uyir == tamil.getUyirPart();
            }
        });
    }

    public static Set<TamilCharacter> filterUyirMeiWithMei(final TamilCharacter mei) {
        return filterTamilCharacters(new TamilLetterFilter() {
            public boolean filter(TamilCharacter tamil) {
                return tamil.isPureTamilLetter() && tamil.isUyirMeyyezhuththu() && mei == tamil.getMeiPart();
            }
        });
    }

    public static Set<TamilCharacter> filterMei() {
        return filterTamilCharacters(new TamilLetterFilter() {
            public boolean filter(TamilCharacter tamil) {
                return tamil.isPureTamilLetter() && tamil.isMeyyezhuththu();
            }
        });
    }


    public static Set<TamilCharacter> filterOarezhutthuMozhi() {
        return filterTamilCharacters(new TamilLetterFilter() {
            public boolean filter(TamilCharacter tamil) {
                return tamil.isPureTamilLetter() && tamil.isWordToStartWith() && tamil.isWordToStartWith() && !tamil.isKurilezhuththu();
            }
        });
    }


    public static Set<TamilCharacter> filterWithSingleCodePoint() {

        return filterTamilCharacters(new TamilLetterFilter() {
            public boolean filter(TamilCharacter tamil) {
                return tamil.isPureTamilLetter() && tamil.isTamilSimpleCharacter();
            }
        });

    }

    public static Set<TamilCharacter> filterWithCodePointSeries(final int x) {
        return filterTamilCharacters(new TamilLetterFilter() {
            public boolean filter(TamilCharacter tamil) {
                if (!tamil.isPureTamilLetter()) {
                    return false;
                }

                for (int[] codepoints : tamil.getCodePoints(new FeatureSet(RXIncludeCanonicalEquivalenceFeature.FEATURE))) {
                    if (codepoints.length == x) {
                        return true;
                    }
                }

                return false;
            }
        });
    }

    public static Set<TamilCharacter> filterWithLipsSpread() {
        return filterTamilCharacters(new TamilLetterFilter() {
            public boolean filter(TamilCharacter tamil) {
                if (!tamil.isPureTamilLetter()) {
                    return false;
                }

                TamilSimpleCharacter uyir = null;
                if (tamil.isUyirezhuththu()) {
                    uyir = (TamilSimpleCharacter) tamil;
                } else if (tamil.isUyirMeyyezhuththu()) {
                    uyir = tamil.getUyirPart();
                }

                if (uyir == null) {
                    return false;
                }

                return uyir == TamilSimpleCharacter.U || uyir == TamilSimpleCharacter.UU ||
                        uyir == TamilSimpleCharacter.O || uyir == TamilSimpleCharacter.OO ||
                        uyir == TamilSimpleCharacter.OU;
            }
        });
    }

    public static Set<TamilCharacter> filterWithIndependentCodePoints() {
        return filterTamilCharacters(new TamilLetterFilter() {
            public boolean filter(TamilCharacter tamil) {
                return tamil.isPureTamilLetter() && tamil.isUnicodeSequenceUnique();
            }
        });
    }


    public static Set<TamilCharacter> filterWithLipsClosed() {
        return filterTamilCharacters(new TamilLetterFilter() {
            public boolean filter(TamilCharacter tamil) {
                if (!tamil.isPureTamilLetter()) {
                    return false;
                }

                TamilCompoundCharacter mei = null;
                if (tamil.isMeyyezhuththu()) {
                    mei = (TamilCompoundCharacter) tamil;
                } else if (tamil.isUyirMeyyezhuththu()) {
                    mei = tamil.getMeiPart();
                }

                if (mei == null) {
                    return false;
                }

                return mei == TamilCompoundCharacter.IP || mei == TamilCompoundCharacter.IM;

            }
        });
    }

    public static Set<TamilCharacter> filterMozhiMuthal() {
        return filterTamilCharacters(new TamilLetterFilter() {
            public boolean filter(TamilCharacter tamil) {
                return tamil.isPureTamilLetter() && tamil.isWordToStartWith();
            }
        });
    }

    public static Set<TamilCharacter> filterMozhiLast() {
        return filterTamilCharacters(new TamilLetterFilter() {
            public boolean filter(TamilCharacter tamil) {
                return tamil.isPureTamilLetter() && tamil.isWordToEndWith();
            }
        });
    }


    public static Set<TamilCharacter> filterMozhiYidai() {
        return filterTamilCharacters(new TamilLetterFilter() {
            public boolean filter(TamilCharacter tamil) {
                return tamil.isPureTamilLetter() && tamil.isWordToContain();
            }
        });
    }

    public static Set<TamilCharacter> filterOut(final Set<TamilCharacter> set) {
        return filterTamilCharacters(new TamilLetterFilter() {
            public boolean filter(TamilCharacter tamil) {
                return tamil.isPureTamilLetter() && !set.contains(tamil);
            }
        });
    }

    public static Set<TamilCharacter> filterKuttiyalugaram() {
        return filterTamilCharacters(new TamilLetterFilter() {
            public boolean filter(TamilCharacter tamil) {
                return tamil.isPureTamilLetter() && tamil.isUyirMeyyezhuththu() && tamil.isVallinam() && tamil.getUyirPart() == TamilSimpleCharacter.U;
            }
        });
    }

    public static void writeToFile(File file, InputStream in) throws Exception {
        FileOutputStream out = new FileOutputStream(validateOutputFile(file.getAbsolutePath(), true));

        try {
            pipe(out, in);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    public static void pipe(OutputStream out, InputStream in) throws IOException {
        byte[] buffer = new byte[102400];
        int read = in.read(buffer);


        try {
            while (read >= 0) {
                out.write(buffer, 0, read);
                out.flush();
                read = in.read(buffer);
            }
        } finally {
            //            if (out != null) {
            //                out.flush();
            //                out.close();
            //            }
        }


    }

    public static String getFileExtension(String path) {
        if (path == null) {
            throw new RuntimeException("path can not be null");
        }
        int last_index = path.lastIndexOf(".");
        if (last_index < 0) {
            return null;
        } else {
            String ext = path.substring(last_index + 1, path.length());
            if (ext.contains(" ") || ext.contains("/") || ext.contains("\\")) {
                return null;
            }
            return ext;
        }
    }

    public static String getPackageName(String clazz) {
        if (clazz == null) {
            throw new RuntimeException("path can not be null");
        }
        int last_index = clazz.lastIndexOf(".");
        if (last_index < 0) {
            return null;
        } else {
            return clazz.substring(0, last_index);
        }
    }


    public static Map<String, String> getEnumValuesAsList(Class cls) {
        return getEnumValuesAsList(cls, null);
    }


    public static Map<String, String> getEnumValuesAsList(Class cls, String toBeRemoved) {
        Enum[] en = (Enum[]) cls.getEnumConstants();
        return getEnumValuesAsList(en, toBeRemoved);
    }

    public static Map<String, String> getEnumValuesAsList(Enum[] en) {
        return getEnumValuesAsList(en, null);
    }

    public static TamilWord trimFinalAKOrReturn(TamilWord w) {
        if (w.endsWith(TamilSimpleCharacter.AKTHU)) {
            TamilWord ret = w.duplicate();
            ret.removeLast();
            return trimFinalAKOrReturn(ret);
        } else {
            return w;
        }

    }


    public static Map<String, String> getEnumValuesAsList(Enum[] en, String toBeRemoved) {
        //Lower case is must.
        boolean lowercase = true;
        Map<String, String> values = new HashMap<String, String>();
        for (int i = 0; i < en.length; i++) {
            if (toBeRemoved != null && toBeRemoved.equals(en[i].name())) {
                continue;
            }
            if (lowercase) {
                values.put(en[i].name().toLowerCase(), en[i].toString());
            } else {
                values.put(en[i].name(), en[i].toString());
            }
        }
        return values;
    }

    public static <T extends List> T readUTF8(InputStream inputStream, TamilCharacterParserListener<T> listener) throws Exception {
        Reader reader = new InputStreamReader(inputStream, "UTF-8");

        TamilCharacterLookUpContext context = null;

        while (true) {
            int read = reader.read();
            if (context != null) {
                TamilCharacterLookUpContext followContext = context.next(read);
                if (followContext == null) {

                    if (context.currentChar == null) {
                        ///TODO: Need to handle incomplete characters later
                    } else {
                        if (listener.tamilCharacter(context.currentChar)) {
                            return listener.get();
                        }
                        context = null;
                    }
                } else {
                    context = followContext;
                }
            }
            if (context == null) {
                context = TamilCharacterLookUpContext.lookup(read);
            }


            if (read == -1) {
                break;
            }
            if (context == null) {
                if (listener.nonTamilCharacter(read)) {
                    return listener.get();
                }
            }
        }
        return listener.get();
    }

    private static AtomicSound findRelevantSound(TamilWord word, TamilSoundLookUpContext context, boolean previousConsonant, boolean previousVowel) {
        if (previousVowel && context.nextToUyirSound != null) {
            return context.nextToUyirSound;
        }

        if (previousConsonant && context.nextToConsonantSound != null) {
            return context.nextToConsonantSound;
        }

        if (context.directSound != null) {
            return context.directSound;
        } else {
            return new AtomicSound(word);
        }

    }

    public static List<Field> getPublicStaticFinalFieldsOfType(Class from, Class type) {
        Field[] fields = from.getFields();
        List<Field> list = new ArrayList<Field>();
        for (Field f : fields) {
            if (Modifier.isStatic(f.getModifiers())) {
                if (Modifier.isFinal(f.getModifiers())) {
                    if (Modifier.isPublic(f.getModifiers())) {
                        if (type == null || type.isAssignableFrom(f.getType())) {
                            list.add(f);
                        }
                    }
                }
            }
        }
        return list;
    }


    public static void readSound(TamilWord word, TamilSoundParserListener listener) throws Exception {
        if (word == null || word.isEmpty()) return;

        TamilSoundLookUpContext context = null;
        AbstractCharacter read = null;
        AbstractCharacter lastconsumed = null;
        AbstractCharacter lastLooked = null;
        boolean previousConsonant = false;
        boolean previousVowel = false;
        TamilWord readPointer = new TamilWord();
        for (int i = 0; i < word.size(); i++) {
            previousConsonant = false;
            previousVowel = false;
            if (lastconsumed != null) {
                if (lastconsumed.isTamilLetter()) {
                    TamilCharacter last = (TamilCharacter) lastconsumed;
                    previousConsonant = last.isMeyyezhuththu();
                    previousVowel = last.isUyirezhuththu() || last.isUyirMeyyezhuththu();
                }
            }

            read = word.get(i);
            readPointer.add(read);
            if (context != null) {
                TamilSoundLookUpContext followContext = context.next(read);
                if (followContext == null) {
                    AtomicSound current = findRelevantSound(readPointer, context, previousConsonant, previousVowel);

                    if (current == null) {
                        //TODO: Need to handle incomplete characters later
                    } else {
                        lastconsumed = lastLooked;
                        if (listener.tamilSound(current)) {

                            return;
                        }
                        context = null;
                        readPointer = new TamilWord();
                    }
                } else {
                    context = followContext;
                    lastLooked = read;
                }
            }
            if (context == null) {
                context = TamilSoundLookUpContext.lookup(read);
                lastLooked = read;
            }


            if (context == null) {

                if (listener.nonTamilSound(read)) {
                    return;
                }
            }
        }


        //Handle last character.

        if (context != null) {
            if (lastconsumed != null) {
                if (lastconsumed.isTamilLetter()) {
                    TamilCharacter last = (TamilCharacter) lastconsumed;
                    previousConsonant = last.isMeyyezhuththu();
                    previousVowel = last.isUyirezhuththu() || last.isUyirMeyyezhuththu();
                }
            }
            AtomicSound current = findRelevantSound(readPointer, context, previousConsonant, previousVowel);
            if (current != null) {
                listener.tamilSound(current);
                //  consumed = read;
            }

        }

    }


    public static String getCommaSeparatedListOfString(String[] list) {
        if (list == null)
            return null;
        return getCommaSeparatedListOfString(Arrays.asList(list));
    }


    public static String parseAndRemoveDuplicatesClassPath(String s) {
        return parseAndRemoveDuplicates(s, File.pathSeparator);
    }

    public static Set<String> parseAndRemoveDuplicatesAsSet(String s, String del) {
        if (s == null)
            return Collections.emptySet();
        List<String> list = parseString(s, del);
        Set<String> set = new HashSet<String>();
        for (String item : list) {
            set.add(item);
        }
        return set;
    }

    public static String parseAndRemoveDuplicates(String s, String del) {
        if (s == null)
            return s;

        Set<String> set = parseAndRemoveDuplicatesAsSet(s, del);

        return getSeparatedListOfString(set, "", "", false, del);
    }

    public static List<String> parseString(String s) {
        return parseString(s, ",");
    }

    public static String[] parseString2StringArray(String s) {
        List<String> list = parseString(s);
        if (list == null)
            return null;
        return list.toArray(new String[0]);
    }

    public static List<String> parseString(String s, String del) {
        return parseString(s, del, false);
    }

    public static List<String> parseString(String s, String del, boolean ignoreNull) {
        return parseString(s, del, ignoreNull, false);
    }

    public static List<String> parseString(String s, String del, boolean ignoreNull, boolean ignoreDup) {
        if (s == null)
            return null;
        List<String> ret = new ArrayList<String>();
        if (del == null) {
            ret.add(s);
            return ret;
        }
        StringTokenizer token = new StringTokenizer(s, del);
        while (token.hasMoreTokens()) {
            String tokenSingle = token.nextToken();
            boolean toAdd = true;
            if (ignoreNull) {
                tokenSingle = tokenSingle.trim();
                if ("".equals(tokenSingle)) {
                    toAdd = false;
                }
            }
            if (toAdd) {
                if (ignoreDup) {
                    toAdd = !ret.contains(tokenSingle);
                }
                if (toAdd) {
                    ret.add(tokenSingle);
                }
            }
        }
        return ret;
    }

    public static String getCommaSeparatedListOfString(Collection<String> list) {
        return getCommaSeparatedListOfString(list, "(", ")", true);
    }

    public static String getSimpleCommaSeparatedListOfString(String[] list) {
        if (list == null)
            return null;
        return getCommaSeparatedListOfString(list, "", "", false);
    }


    public static String getCommaSeparatedListOfString(String[] list, String open, String close,
                                                       boolean singleQuoteElement) {
        List<String> listStr = null;
        if (list != null) {
            listStr = Arrays.asList(list);
        }
        return getCommaSeparatedListOfString(listStr, open, close, singleQuoteElement);

    }

    public static String getCommaSeparatedListOfString(Collection<String> list, String open, String close,
                                                       boolean singleQuoteElement) {
        return getSeparatedListOfString(list, open, close, singleQuoteElement, ",");
    }

    public static String getSeparatedListOfString(Collection<String> list, String open, String close,
                                                  boolean singleQuoteElement, String separator) {
        if (list == null)
            return null;
        StringBuffer buffer = new StringBuffer(open);
        for (Iterator<String> it = list.iterator(); it.hasNext(); ) {
            String item = it.next();
            if (singleQuoteElement) {
                buffer.append("'" + item + "'");
            } else {
                buffer.append(item);
            }
            if (it.hasNext()) {
                buffer.append(separator);
            }
        }
        buffer.append(close);
        return buffer.toString();
    }

    public static AppDescription findApp(String name, TamilRootWords file, boolean context) {
        if (name == null || name.trim().equals("") || file == null) return null;

        if (file.getApps() == null) {
            file.setApps(new my.interest.lang.tamil.generated.types.Apps());
        }
        if (file.getApps().getApps() == null) {
            file.getApps().setApps(new AppsDescription());
        }
        if (file.getApps().getApps().getList() == null) {
            file.getApps().getApps().setList(new AppsDescriptionList());
        }
        for (AppDescription a : file.getApps().getApps().getList().getApp()) {
            if (context) {
                if (name.equals(a.getRoot())) {
                    return a;
                }
            } else {
                if (name.equals(a.getName())) {
                    return a;
                }
            }
        }
        return null;
    }

    public static String padChar(int minLength, char what, String where, int maxLength, boolean addToRight) {
        if (where == null)
            return null;
        for (int i = where.length(); i < minLength && where.length() < maxLength; i++) {
            if (addToRight)
                where = where + what;
            else
                where = what + where;
        }
        if (maxLength > 0 && where.length() > maxLength) {
            where = where.substring(0, maxLength);
        }
        return where;
    }

    public static String convertToHelpText(String source, int eachLineMaxLength, int nextLineOffset) {

        //  try {
        if (eachLineMaxLength <= 0)
            eachLineMaxLength = 80;
        if (source == null)
            return null;
        source = source.replaceAll("\t", "   ");

        StringBuffer ret = new StringBuffer("");
        boolean alreadyCut = false;

        while (source.length() > eachLineMaxLength || source.contains("\n")) {
            boolean newLine = false;
            boolean cutAtMax = false;
            int lastIndexAtSpace =
                    (source.length() > eachLineMaxLength ? source.substring(0, eachLineMaxLength + 1) :
                            source).indexOf("\n");
            if (lastIndexAtSpace < 0) {
                for (int i = 0; i < wrapCutText.length; i++) {
                    int temp = 0;
                    // if (source.length() > eachLineMaxLength) {

                    String cut = source.substring(0, eachLineMaxLength);
                    temp = cut.lastIndexOf(wrapCutText[i]);
                    if (temp > 0) {
                        boolean endSwith = false;
                        String end = cut.substring(0, temp);
                        for (String e : wrapCutText_NOT_ENDING) {
                            if (end.endsWith(e)) {
                                endSwith = true;
                                break;
                            }
                        }
                        if (endSwith) {
                            temp = -1;
                        }
                    }


                    //                        } else {
                    //                            temp = source.lastIndexOf(wrapCutText[i]);
                    //                        }

                    if (temp > lastIndexAtSpace) {
                        lastIndexAtSpace = temp;
                    }

                }
                if (lastIndexAtSpace < 0) {
                    for (int i = 0; i < wrapCutText_finally.length; i++) {
                        int temp = 0;


                        String cut = source.substring(0, eachLineMaxLength);
                        temp = cut.lastIndexOf(wrapCutText_finally[i]);
                        if (temp > 0) {
                            boolean endSwith = false;
                            String end = cut.substring(0, temp);
                            for (String e : wrapCutText_NOT_ENDING) {
                                if (end.endsWith(e)) {
                                    endSwith = true;
                                    break;
                                }
                            }
                            if (endSwith) {
                                temp = -1;
                            }
                        }


                        if (temp > lastIndexAtSpace) {
                            lastIndexAtSpace = temp;
                        }
                    }


                }

            } else {
                newLine = true;
            }


            if (lastIndexAtSpace < 0) {
                lastIndexAtSpace = eachLineMaxLength;
                cutAtMax = true;
            }
            String line =
                    source.substring(0, newLine ? lastIndexAtSpace : (cutAtMax ? lastIndexAtSpace : lastIndexAtSpace +
                            1));

            ret.append(line);
            ret.append("\n");
            source =
                    source.substring(newLine ? (lastIndexAtSpace + 1) : (cutAtMax ? lastIndexAtSpace : lastIndexAtSpace +
                            1), source.length());
            //System.out.println(source);

            for (int i = 0; i < nextLineOffset; i++) {
                ret.append(" ");
            }

            if (eachLineMaxLength > nextLineOffset && !alreadyCut) {
                eachLineMaxLength = eachLineMaxLength - nextLineOffset;
                alreadyCut = true;
            }
            // System.out.println(ret.toString());
            //System.out.println(source);
        }
        ret.append(source);
        return ret.toString();
//        } catch (Exception e){
//            //Logger.getDEFAULT().printlnError(e.getMessage());
//            //System.out.println( source);
//            return source;
//        }
    }


    public static int getKeyMaxLength(Map map) {
        if (map == null)
            return 0;
        int maxLength = 0;
        for (Iterator it = map.keySet().iterator(); it.hasNext(); ) {
            Object obj = it.next();
            if (obj != null && obj.toString().length() > maxLength)
                maxLength = obj.toString().length();
        }
        return maxLength;
    }

    public static String[] getAllInnerErrorMessages(Throwable th) {
        List<String> all = new ArrayList<String>();
        boolean msgFound = false;
        while (th != null) {
            if (th.getMessage() != null && !th.getMessage().trim().equals("")) {
                if (!msgFound && all.size() > 0) {
                    all.add(0, th.getMessage());
                }
                all.add(th.getMessage());
                msgFound = true;
            } else {
                all.add(th.getClass().getName());
            }
            th = th.getCause();
        }
        return all.toArray(new String[0]);
    }

    public static String getString(String sp, int count) {
        StringBuffer ret = new StringBuffer();
        for (int i = 0; i < count; i++) {
            ret.append(sp);
        }
        return ret.toString();
    }

    public static long getLastModifiedDate(File dir) {
        if (!dir.exists())
            return 0;
        if (dir.isFile()) {
            return dir.lastModified();
        } else {
            String[] files = findDirectoriesWithPatternAt(dir, null);
            long ret = dir.lastModified();
            for (int i = 0; i < files.length; i++) {
                long sub = getLastModifiedDate(new File(dir, files[i]));
                if (ret < sub) {
                    ret = sub;
                }
            }
            files = findFilesWithPatternAt(dir, null);

            for (int i = 0; i < files.length; i++) {
                long sub = getLastModifiedDate(new File(dir, files[i]));
                if (ret < sub) {
                    ret = sub;
                }
            }
            return ret;
        }
    }

    public static String[] findDirectoriesWithPatternAt(File dir, final String pattern) {
        if (dir == null)
            return null;
        if (!dir.exists() || !dir.isDirectory())
            return null;
        return dir.list(new FilenameFilter() {
            public boolean accept(File dir, String name) {

                if (!new File(dir, name).isDirectory())
                    return false;
                if (pattern == null)
                    return true;
                Pattern pat = Pattern.compile(pattern);
                Matcher matcher = pat.matcher(name);
                return matcher.matches();

            }

        });
    }

    public static List<String> recurseFindFilesWithPatternAt(File dir, final String pattern) {
        List<String> list = new ArrayList<String>();
        recurseFindAndAddFilesWithPatternAt(dir, "", pattern, list);
        return list;

    }


    public static void recurseFindAndAddFilesWithPatternAt(File dir, String offset, final String pattern,
                                                           List<String> list) {

        File effectiveDir = new File(dir, offset);
        String files[] = findFilesWithPatternAt(effectiveDir, pattern);
        if (files != null) {
            for (String file : files) {
                list.add(offset + file);
            }
        }
        String dirs[] = findDirectoriesWithPatternAt(effectiveDir, null);
        if (dirs != null) {
            for (String sub : dirs) {
                recurseFindAndAddFilesWithPatternAt(dir, offset + sub + File.separator, pattern, list);
            }
        }

    }


    public static String[] findFilesWithPatternAt(File dir, final String pattern) {
        if (dir == null)
            return null;
        if (!dir.exists() || !dir.isDirectory())
            return null;
        return dir.list(new FilenameFilter() {
            public boolean accept(File dir, String name) {

                if (!new File(dir, name).isFile())
                    return false;
                if (pattern == null)
                    return true;
                Pattern pat = Pattern.compile(pattern);
                Matcher matcher = pat.matcher(name);

                return matcher.matches();

            }

        });
    }


    public static String getFileBaseName(String fileName) {
        if (fileName == null) {
            throw new RuntimeException("fileName can not be null");
        }
        int index = fileName.indexOf(".");
        if (index < 0) {
            return fileName;
        } else {
            return fileName.substring(0, index);
        }
    }

}
