package my.interest.lang.tamil.impl.rx;

import my.interest.lang.tamil.impl.FeatureSet;
import tamil.lang.TamilCharacter;
import tamil.lang.api.regex.RXIncludeCanonicalEquivalenceFeature;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class RXCalcForLetterSeries {


    StringBuffer buffer = new StringBuffer();
    private Range lastRange = new Range();

    static class Range {
        int start = -1;
        List<Integer> commonGlyphCollection = new ArrayList<Integer>();

        int end = -1;
        int glyph = -1;

        void reset() {
            start = -1;
            end = -1;
            glyph = -1;
            commonGlyphCollection.clear();
        }

        boolean isFresh() {
            return start < 0;
        }

        boolean isGlyphThere() {
            return glyph >= 0;
        }

    }


    public void insertTamilCharacter(TamilCharacter ch, FeatureSet set) {
        boolean includeCanonEq = set.isFeatureEnabled(RXIncludeCanonicalEquivalenceFeature.class);
        if (!ch.isUnicodeSequenceUnique()) {
            appendLastRange();
            appendRx(ch.toUnicodeRegEXRepresentation(includeCanonEq));
            return;
        }
        List<int[]> reps = ch.getCodePoints(includeCanonEq);


        if (reps.size() > 1) {
            appendLastRange();
            appendRx(ch.toUnicodeRegEXRepresentation(includeCanonEq));
            return;
        }
        int[] rep = reps.get(0);
        if (rep.length > 2) {
            appendLastRange();
            appendRx(ch.toUnicodeRegEXRepresentation(includeCanonEq));
            return;
        }

        boolean breakTheChain = true;


        if (!lastRange.isFresh()) {
            if (rep.length == 1) {
                if (!lastRange.isGlyphThere()) {
                    if (rep[0] == lastRange.end + 1) {
                        lastRange.end = rep[0];
                        breakTheChain = false;
                    }

                }
            } else {
                if (lastRange.isGlyphThere()) {
                    if (lastRange.glyph == rep[1]) {
                        if (lastRange.commonGlyphCollection.isEmpty() && rep[0] == lastRange.end + 1) {
                            lastRange.end = rep[0];
                            breakTheChain = false;
                        } else {
                            lastRange.commonGlyphCollection.add(rep[0]);
                            breakTheChain = false;
                        }
                    }
                }
            }
        }

        if (breakTheChain) {
            appendLastRange();
        }

        if (lastRange.isFresh()) {
            lastRange.start = rep[0];
            lastRange.end = lastRange.start;
            if (rep.length == 2) {
                lastRange.glyph = rep[1];
            }

        }

    }

    private String intToHExString(int code) {
        StringBuffer buffer = new StringBuffer(Integer.toHexString(code));
        while (buffer.length() < 4) {
            buffer.insert(0, "0");

        }
        buffer.insert(0, "\\u");
        return buffer.toString();
    }

    private void appendRx(String rx) {
        if (buffer.length() > 0) {
            buffer.append("|");
        }
        buffer.append(rx);
    }

    private void appendLastRange() {
        if (!lastRange.isFresh()) {
            //boolean needsClosing = false;
            if (buffer.length() > 0) {
                buffer.append("|");
            }
//            if (lastRange.start != lastRange.end || lastRange.glyph >=0 ) {
//                buffer.append("(?:");
//                needsClosing = true;
//            }
            if (lastRange.commonGlyphCollection.isEmpty()) {

                if (lastRange.start == lastRange.end) {
                    buffer.append(intToHExString(lastRange.start));
                } else {
                    buffer.append("[");
                    buffer.append(intToHExString(lastRange.start));
                    buffer.append("-");
                    buffer.append(intToHExString(lastRange.end));
                    buffer.append("]");
                }
            } else {
                buffer.append("(?:[");
                buffer.append(intToHExString(lastRange.start));
                for (int x : lastRange.commonGlyphCollection) {

                    buffer.append(intToHExString(x));
                }
                buffer.append("])");
            }


            if (lastRange.glyph >= 0) {
                buffer.append(intToHExString(lastRange.glyph));
            }
//            if (needsClosing) {
//                buffer.append(")");
//            }

        }
        lastRange.reset();
    }

    public String getRX() {
        appendLastRange();
        return "(?:" + buffer.toString() + ")";
    }
}
