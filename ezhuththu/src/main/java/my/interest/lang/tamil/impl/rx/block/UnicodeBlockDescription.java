package my.interest.lang.tamil.impl.rx.block;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;
import tamil.lang.api.regex.RxDescription;

import java.util.*;

/**
 * Created by velsubra on 6/20/16.
 */
public class UnicodeBlockDescription implements PatternGenerator, Comparable<UnicodeBlockDescription> {
    private Character.UnicodeBlock unicodeBlock;

    public int getStartingCodePoint() {
        return startingCodePoint;
    }

    public int getEndingCodePoint() {
        return endingCodePoint;
    }

    public Character.UnicodeBlock getUnicodeBlock() {
        return unicodeBlock;
    }

    public static   UnicodeBlockDescription find(String blockName) {
        return  blocks.get(blockName);
    }

    private int startingCodePoint;
    private int endingCodePoint;

    /**
     * returns the starting codepoint represented in a string of the form \uaa80
     *
     * @return the starting unicode codepoint for this block
     */
    public String getStartingUnicodeString() {
        return getUniCodeString(startingCodePoint);
    }

    public String getEndingUnicodeString() {
        return getUniCodeString(endingCodePoint);
    }


    public static String getUniCodeString(int code) {
        StringBuffer buffer = new StringBuffer();
        String val = Integer.toHexString(code);
        while (val.length() < 4) {
            val = "0" + val;
        }
        buffer.append("\\u" + val);
        return buffer.toString();
    }

    private UnicodeBlockDescription() {

    }

    public static Map<String, UnicodeBlockDescription> blocks = new HashMap<String, UnicodeBlockDescription>();

    static {
        int codepoint = 0;
        Character.UnicodeBlock block = Character.UnicodeBlock.of(codepoint);
        UnicodeBlockDescription last = new UnicodeBlockDescription();
        last.startingCodePoint = codepoint;
        last.unicodeBlock = block;
        try {
            while (true) {
                codepoint++;
                if (codepoint > LAST_UNICODE_CODEPOINT) {
                    throw  new IllegalArgumentException("End BMP");
                }
                block = Character.UnicodeBlock.of(codepoint);
                if (block == null) {
                    if (last == null) {
                        continue;
                    }
                    last.endingCodePoint = codepoint - 1;
                    blocks.put(last.unicodeBlock.toString().toUpperCase(), last);
                    last = null;
                } else if (last != null && block == last.unicodeBlock) {
                    continue;
                } else {
                    if (last != null) {
                        last.endingCodePoint = codepoint - 1;
                        blocks.put(last.unicodeBlock.toString().toUpperCase(), last);
                    }
                    last = new UnicodeBlockDescription();
                    last.startingCodePoint = codepoint;
                    last.unicodeBlock = block;

                }


            }
        } catch (IllegalArgumentException e) {
            last.endingCodePoint = codepoint - 1;
            blocks.put(last.unicodeBlock.toString().toUpperCase(), last);
        }
    }

//    public static void main(String[] s) throws Exception {
//
//        for (UnicodeBlockDescription desc : blocks.values()) {
//            System.out.println(desc.unicodeBlock.toString() + ":" + Integer.toHexString(desc.startingCodePoint) + ":size " + (desc.endingCodePoint - desc.startingCodePoint + 1));
//        }
//    }

    public String getName() {
        return unicodeBlock.toString();
    }

    public String getDescription() {
        return "Represents any character from the unicode block " + unicodeBlock.toString() +"  that starts with " + getStartingUnicodeString() +". Hex Range:" + Integer.toHexString(getStartingCodePoint()) + " - " +Integer.toHexString(getEndingCodePoint());
    }

    public String generate(FeatureSet set) {
        return "["+ getStartingUnicodeString() + "-" + getEndingUnicodeString() + "]";
    }

    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }

    public int compareTo(UnicodeBlockDescription o) {
        return new Integer(getStartingCodePoint()).compareTo(o.getStartingCodePoint());
    }
}
