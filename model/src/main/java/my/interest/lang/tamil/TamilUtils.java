package my.interest.lang.tamil;

import common.lang.impl.AbstractCharacter;
import my.interest.lang.tamil.generated.types.PaalViguthi;
import my.interest.lang.tamil.generated.types.SimpleTense;
import my.interest.lang.tamil.internal.api.TamilCharacterParserListener;
import tamil.lang.*;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.IPeyarchchol;

import java.io.*;
import java.util.*;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TamilUtils {

    public static final String ENCODING = "UTF-8";


    private static List<Class<? extends IKnownWord>> peyar = new ArrayList<Class<? extends IKnownWord>>();

    static {
        peyar.add(IPeyarchchol.class);
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

    public static byte[] readAllFrom(InputStream in, boolean chunked) {
        if (in == null)
            throw new RuntimeException("Stream can't be null");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            byte[] buf = new byte[1024];
            int len;
            len = in.read(buf);
            //System.out.println("READ: " + len + "bytes for the first time");
            while (len > 0) {

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
        } catch (Exception e) {
            throw new RuntimeException("Unable to read:", e);
        } finally {
            try {
                bos.close();
            } catch (Exception io) {
                io.printStackTrace();
            }

        }
        return bos.toByteArray();

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

    /**
     * 31 is the driving prime number for hash.
     *
     * @param w
     * @return
     */

    public static int suggestionHashCode(TamilWord w) {
        TamilCharacter lastcons = null;
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
                    if (cons.equals(TamilCompoundCharacter.ILL)) {
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

                    if (cons.equals(lastcons)) {
                        cons = null;


                    }


                    lastcons = cons;
                } else {
                    lastcons = null;
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
            return TamilUtils.trimFinalAKOrReturn(ret);
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
                ret.add(tokenSingle);
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
}
