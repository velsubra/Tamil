package my.interest.lang.util;

import my.interest.lang.tamil.EzhuththuUtils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TextFileWriter  {

    protected int spaces = 0;
    protected static  final int TAB_SIZE = 2;
    protected boolean lastPrint = false;
    protected PrintWriter pr = null, actual = null;
    // alternative output for the printWriter, use it if is is set to not null
    protected PrintStream pStream = null;
    private OrderedMap<String, String> map = new OrderedMap<String, String>();
    protected ByteArrayOutputStream br;
    protected int cursorPosition = 0;
    protected int currentLineNonSpaceCharOffset = 0;
    protected boolean autoCommit = false;
    protected String caption = null;


    public TextFileWriter(PrintWriter pr) throws java.io.UnsupportedEncodingException {
        this(pr, false);
    }

    public TextFileWriter(PrintWriter pr, boolean autoCommit) throws java.io.UnsupportedEncodingException {
        this.autoCommit = autoCommit;
        this.actual = pr;
        if (!autoCommit) {
            this.br = new ByteArrayOutputStream();
            this.pr = new PrintWriter(new OutputStreamWriter( br, EzhuththuUtils.ENCODING), true);
        } else {
            this.pr = pr;
        }
    }

    public TextFileWriter(PrintStream stream) {
        this.pStream = stream;
        this.pr = null;
    }

    public void close() {
        if (actual != null) {

            actual.close();
        }
    }



    public void flush() {
        if (actual != null) {
            actual.flush();
        }
    }


    public void printContextSpace() {
        printSpace(spaces * TAB_SIZE);
    }

    public String getSpace(int length) {
        return EzhuththuUtils.padChar(length, ' ', "", length, true);
    }


    public String getContextSpace() {
        return getSpace(spaces * TAB_SIZE);
    }

    public void printSpace(int length) {
        boolean count = cursorPosition == 0;

        for (int i = 0; i < length; i++) {
            if (pStream != null) {
                pStream.print(" ");
            } else {
                pr.print(" ");
            }
            if (count) {
                currentLineNonSpaceCharOffset++;
            }
            cursorPosition++;
        }
        lastPrint = true;
    }

    public void tab() {
        spaces++;
    }

    public void shiftTab() {
        spaces--;

    }

    public void println() {
        println(false, false);
    }

    public void println(boolean align, boolean lastCursor) {
        //        if (align)
        //            System.out.println("Aligning based on cursor:" + lastCursor);
        int space = lastCursor ? cursorPosition : currentLineNonSpaceCharOffset;
        println("");
        if (align) {
            printSpace(space);
        }
    }

    public void println(String s) {
        println(s, true);
    }

    public void println(String s, boolean alignSpace) {
        println(s, alignSpace, true);
    }

    public void println(String s, boolean alignSpace, boolean printcaption) {
        if (printcaption) {

            print(getCaptionToPrint());

        }
        if (alignSpace)
            printContextSpace();
        if (pStream != null) {
            pStream.print(s + "\r\n");
            pStream.flush();
        } else {
            pr.println(s);
            pr.flush();
        }

        lastPrint = false;
        cursorPosition = 0;
        currentLineNonSpaceCharOffset = 0;

    }

    public void print(String s) {
        if (!lastPrint)
            printContextSpace();
        if (pStream != null) {
            pStream.print(s);
        } else {
            pr.print(s);
        }
        cursorPosition += s.length();
        lastPrint = true;


    }

    public void printBinaryAsString(byte[] data) throws java.io.UnsupportedEncodingException  {
        if (data != null) {
            if (pStream != null) {
                pStream.print(new String(data, EzhuththuUtils.ENCODING));
            } else {
                pr.print(new String(data, EzhuththuUtils.ENCODING));
            }
            // System.out.println(new String(data));
        }
    }

    public void commit() {
        try {
            if (!autoCommit) {
                StringBuffer buf = new StringBuffer(br.toString(EzhuththuUtils.ENCODING));
                actual.print(buf);
            }
            actual.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void printCommentStart() {
        print("# ");

    }

    public void printlnComment(String comment) {
        List<String> lines = EzhuththuUtils.parseString(comment, "\n");
        for (String line : lines) {
            printCommentStart();
            println(line);
        }
        println();

    }

    public void printUnderLined(String text) {
        printUnderLined(text, '-');
    }

    public void printUnderLined(String text, char with) {
        println(text);
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < text.length(); i++) {
            buf.append(with);
        }
        println(buf.toString());
    }

    public OrderedMap<String, String> initMapText() {


        return map;

    }

    public String getCaptionToPrint() {
        if (caption != null && !caption.equals("")) {
            return ("[" + caption + "] ");
        } else {
            return "";
        }

    }

    public int getCaptionLength() {
        return getCaptionToPrint().length();
    }

    public void addMapText(String name, Object value) {

        map.put(name, value == null ? "" : value.toString(),
                true); //.replaceAll("\\\n", " ").replaceAll("\\\r",   ""));
    }

    public int endMapText() {
        int x = endMapText(100);
        flush();
        return x;
    }

    public int endMapText(int maxLineLength) {

        int allignLength = EzhuththuUtils.getKeyMaxLength(map);
        String spaces = getContextSpace();

        for (Iterator<NameValuePair<String, String>> it = map.getList().iterator(); it.hasNext(); ) {
            NameValuePair<String, String> pair = it.next();
            String key = pair.getName();

            if (key == null) {
                println();
            } else {
                String value = pair.getValue();
                if (value == null)
                    value = "";
                println(EzhuththuUtils.convertToHelpText(spaces + getCaptionToPrint() + EzhuththuUtils.padChar(allignLength, ' ', key.trim(), allignLength,
                                true) + " - " + value, maxLineLength,
                        allignLength + 03 + getCaptionLength() + spaces.length()), false, false);
            }
        }
        map.clear();
        return allignLength;
    }

    public void printComment(String t) {
        printlnComment(t);
    }


    public void printComment(Throwable t) {
        String[] messages = EzhuththuUtils.getAllInnerErrorMessages(t);
        printlnComment("Exception has occurred.");

        for (int i = 0; i < messages.length; i++) {

            if (i != 0) {
                printlnComment("\tCaused By ->" + messages[i]);
            } else {
                printlnComment(messages[i]);
            }
        }


    }


    public PrintWriter getActual() {
        return actual;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void reSetCaption() {
        this.caption = null;
    }

    public String getCaption() {
        return caption;
    }
}
