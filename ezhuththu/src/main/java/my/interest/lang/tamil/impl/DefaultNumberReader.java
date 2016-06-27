package my.interest.lang.tamil.impl;

import common.lang.impl.UnknownCharacter;
import my.interest.lang.tamil.impl.number.*;
import my.interest.lang.tamil.impl.number.known.KnownNumberComponent;
import my.interest.lang.tamil.parser.impl.sax.SaxParser;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.feature.FeatureConstants;
import tamil.lang.api.join.WordsJoiner;
import tamil.lang.api.number.NotANumberException;
import tamil.lang.api.number.NumberReader;
import tamil.lang.api.number.ReaderFeature;
import tamil.lang.api.parser.CompoundWordParser;
import tamil.lang.api.parser.ParserResult;
import tamil.lang.api.parser.ParserResultCollection;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.Theriyaachchol;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class DefaultNumberReader implements NumberReader {

    private DefaultNumberReader() {

    }

    public static final DefaultNumberReader reader = new DefaultNumberReader();

    private static class FloatingPoint {
        private String base;
        private String fraction;

        public String getBase() {
            return base;
        }

        public void setBase(String name) {
            this.base = name;
        }

        public String getFraction() {
            return fraction;
        }

        public void setFraction(String value) {
            this.fraction = value;
        }
    }

    private static FloatingPoint filter(String number, FeatureSet set) {
        if (number == null) {
            return null;
        }
        if (set.isToIgnoreNonDigit() && !set.isToTreatNonDigitAsNumber()) {
            number = number.trim();
        }

        StringBuffer base = new StringBuffer();
        StringBuffer fraction = new StringBuffer();

        boolean fractionReached = false;
        boolean noZeroReached = false;
        int fractionLength = 0;
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            if (ch >= '0' && ch <= '9' || ch == '.' || ch == '+' || ch == '-') {
                // Just fine
            } else {
                if (set.isToTreatNonDigitAsNumber()) {
                    ch = '0';
                } else {
                    if (set.isToIgnoreNonDigit()) {
                        continue;
                    }
                    throw new NotANumberException("Index:" + i + " Invalid character:" + ch);
                }
            }

            if (!fractionReached && ch == '0' && !noZeroReached) continue;
            if (ch >= '0' && ch <= '9') {

                if (fractionReached) {
                    fraction.append(ch);
                    if (ch != '0') {
                        fractionLength = fraction.length();
                    }
                } else {
                    if (ch == '0') {
                        if (!noZeroReached) {
                            continue;
                        } else {
                            base.append(ch);
                        }
                    } else {
                        noZeroReached = true;
                        base.append(ch);
                    }
                }
            } else if (ch == '.') {
                if (fractionReached) {
                    break;

                } else {
                    fractionReached = true;
                }
            } else {
                continue;
            }
        }


        if (base.length() == 0) {
            base.append('0');
        }

        FloatingPoint ret = new FloatingPoint();
        ret.setFraction(fraction.toString().substring(0, fractionLength));
        ret.setBase(base.toString());
        return ret;


    }


    public TamilWord readNumberWithFiltering(String number, ReaderFeature... features) {
        if (number == null) {
            return new TamilWord();
        }
        FeatureSet set = features == null ? FeatureSet.EMPTY : new FeatureSet(features);
        FloatingPoint val = filter(number, set);


        TamilWord full = new TamilWord();
        if (val.getBase().length() == 0) {
            val.setBase("0");
        }

        full.addAll(readCleanNumber(val.getBase(), null, set));
        if (val.getFraction().length() > 0) {
            if (full.size() > 0) {
                full.addSpace();
            }
            full.addAll(readCleanFractionWithoutDot(val.getFraction(), set));
        }
        return full;


    }


    @Override
    public TamilWord readNumber(String number, ReaderFeature... features) throws NotANumberException {
        return readNumberWithFiltering(number, features);
    }

    /**
     * Reads a number in string form.
     *
     * @param number number that can contain digits and a dot. All other characters will be ignored.
     * @return the Tamil text form of that number.
     */
    @Override
    public TamilWord readNumber(String number) {
        return readNumberWithFiltering(number);
    }

    /**
     * Reads a number in a string form
     *
     * @param number the number to be read
     * @return the string form of the number
     */
    @Override
    public TamilWord readNumber(long number) {
        return readCleanNumber(String.valueOf(number), null, FeatureSet.EMPTY);
    }

    /**
     * Reads a number in a string form
     *
     * @param number the number to be read
     * @return the string form of the number
     */
    @Override
    public TamilWord readNumber(double number) {
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(8);

        String str = df.format(number);
        int index = str.indexOf(".");
        String base = null;
        String fraction = null;
        if (index >= 0) {
            if (index > 0) {
                base = str.substring(0, index);
            }

            if (index < str.length()) {
                fraction = str.substring(index + 1, str.length());
            }
        } else {
            base = str;
        }
        TamilWord bword = null;
        TamilWord fword = null;
        if (base != null) {
            bword = readCleanNumber(base, null, FeatureSet.EMPTY);
        }
        if (fraction != null && fraction.length() > 0) {
            fword = readCleanFractionWithoutDot(fraction, FeatureSet.EMPTY);
        }

        TamilWord full = new TamilWord();
        if (bword != null) {
            full.addAll(bword);
        }
        if (fword != null) {
            if (full.size() > 0) {
                full.add(UnknownCharacter.SPACE);
            }
            full.addAll(fword);
        }
        return full;

    }

    public BigInteger readAsNumberInternal(List<KnownNumberComponent> undercrore, int fromIndex) throws NotANumberException {
        BigInteger ret = new BigInteger("0");
        KnownNumberComponent last = null;
        for (int i = fromIndex; i < undercrore.size(); i++) {
            KnownNumberComponent know = undercrore.get(i);
            if (last == null) {
                ret = ret.add( know.getPosition().multiply( new BigInteger(String.valueOf( know.getUnit()))));
            } else {
                if (last.getPower() ==  know.getPower()){
                    throw new NotANumberException(last.getWord() + " and " + know.getWord() + " can not be together");
                }
                if (last.getPower()< know.getPower()) {

                    ret = ret.multiply(new BigInteger(String.valueOf( know.getPosition())));
                    BigInteger folded = readAsNumberInternal(undercrore, i + 1);
                    if (ret.compareTo(folded) < 0) {
                        //ஐயாயிரம்    இலட்சத்தொன்று    - 105001

                        throw new NotANumberException("Not a right combination " + ret.toString() + " and " + folded.toString() );
                    } else {
                        ret = ret.add(folded);
                    }
                    break;
                } else {
                    if (know.getUnit() == 0) {
                        ret = new BigInteger ("0");
                    } else {
                        ret = ret.add( know.getPosition().multiply(new BigInteger(String.valueOf( know.getUnit()))));
                    }
                }
            }
            last = know;
        }
        return ret;
    }

    @Override
    public String readAsNumber(String numbertext, ReaderFeature... features) throws NotANumberException {
        if (numbertext == null) {
            return "0";
        } else {
            numbertext = numbertext.replaceAll("\n", " ");
            int pulli = numbertext.indexOf("புள்ளி");
            if (pulli < 0) {
                return readAsNumberWholePart(numbertext, features);
            } else {
                String whole = numbertext.substring(0, pulli);
                String fractional = numbertext.substring(pulli + 6, numbertext.length());
                String ret = readAsNumberWholePart(whole, features) + ".";
                ret += readAsNumberFractionalPart(fractional, features);
                return ret;

            }
        }
    }

    private String readAsNumberFractionalPart(String numbertext, ReaderFeature... features) throws NotANumberException {
        if (numbertext == null || numbertext.trim().equals("")) {
            return "0";
        } else {
            numbertext = numbertext.replaceAll("\n", " ");
            String[] all = numbertext.trim().split(" ");
            if (all.length == 0) {
                return "0";
            } else {
                List<KnownNumberComponent> series = new ArrayList<KnownNumberComponent>();
                CompoundWordParser parser = new SaxParser();// TamilFactory.getCompoundWordParser();
                for (String s : all) {
                    if (s.trim().equals("")) continue;
                    TamilWord tamilWord = TamilWord.from(s, true);
                    ParserResultCollection singleresult = parser.parse(tamilWord, 1, FeatureConstants.PARSE_FOR_NUMBER_VAL_172);
                    if (singleresult.isEmpty()) {
                        singleresult = parser.parse(tamilWord, 2, FeatureConstants.PARSE_FOR_NUMBER_VAL_172, FeatureConstants.PARSE_WITH_UNKNOWN_VAL_170);
                        List<IKnownWord> suggestion =   FeatureConstants.PARSE_FOR_NUMBER_VAL_172.getDictionary().search(tamilWord,2,null,FeatureConstants.DICTIONARY_AUTO_SUGGEST_VAL_165);
                        if (singleresult.isEmpty()) {

                            throw new NotANumberException("Could not parse part:" + s + ". Suggestion:"+ suggestion);
                        } else {
                            ParserResult result = singleresult.getList().get(0);
                            if (result.isParsed()) {
                                Theriyaachchol unknown = result.findUnknownPart();
                                if (unknown != null) {
                                    throw new NotANumberException("Could not parse unknown part:" + unknown.getWord().toString()+ ". Suggestion:"+ suggestion);
                                } else {
                                    throw new NotANumberException("Could not parse part:" + s+ ". Suggestion:"+ suggestion);
                                }
                            } else {
                                throw new NotANumberException("Could not parse part:" + s+ ". Suggestion:"+ suggestion);
                            }
                        }

                    }
                    for (IKnownWord d : singleresult.getList().get(0).getSplitWords()) {
                        if (!KnownNumberComponent.class.isAssignableFrom(d.getClass())) {
                            throw new NotANumberException("Could not recognize part:" + s);
                        }
                        series.add((KnownNumberComponent) d);
                    }
                }
                // Got a full series.
                StringBuffer buffer = new StringBuffer();
                for (KnownNumberComponent d : series) {
                    buffer.append(d.getUnit());
                }
                if (buffer.length() == 0) {
                    buffer.append("0");
                }
                return buffer.toString();

            }
        }

    }


    private String readAsNumberWholePart(String numbertext, ReaderFeature... features) throws NotANumberException {
        if (numbertext == null || numbertext.trim().equals("")) {
            return "0";
        } else {
            numbertext = numbertext.replaceAll("\n", " ");
            String[] all = numbertext.trim().split(" ");
            if (all.length == 0) {
                return "0";
            } else {
                KnownNumberComponent maxCompoent = null;
                List<KnownNumberComponent> series = new ArrayList<KnownNumberComponent>();
                CompoundWordParser parser = new SaxParser();// TamilFactory.getCompoundWordParser();
                for (String s : all) {
                    if (s.trim().equals("")) continue;
                    TamilWord tamilWord = TamilWord.from(s, true);
                    ParserResultCollection singleresult = parser.parse(tamilWord, 1, FeatureConstants.PARSE_FOR_NUMBER_VAL_172);
                    if (singleresult.isEmpty()) {
                        singleresult = parser.parse(tamilWord, 1, FeatureConstants.PARSE_FOR_NUMBER_VAL_172, FeatureConstants.PARSE_WITH_UNKNOWN_VAL_170);
                        List<IKnownWord> suggestion =    FeatureConstants.PARSE_FOR_NUMBER_VAL_172.getDictionary().search(tamilWord,2,null,FeatureConstants.DICTIONARY_AUTO_SUGGEST_VAL_165);
                        if (singleresult.isEmpty()) {
                            throw new NotANumberException("Could not parse part:" + s);
                        } else {
                            ParserResult result = singleresult.getList().get(0);
                            if (result.isParsed()) {
                                Theriyaachchol unknown = result.findUnknownPart();
                                if (unknown != null) {
                                    throw new NotANumberException("Could not parse unknown part:" + unknown.getWord().toString() +". Suggestions:" + suggestion);
                                } else {
                                    throw new NotANumberException("Could not parse part:" + s+". Suggestions:" + suggestion);
                                }
                            } else {
                                throw new NotANumberException("Could not parse part:" + s+". Suggestions:" + suggestion);
                            }
                        }
                    }
                    for (IKnownWord d : singleresult.getList().get(0).getSplitWords()) {
                        if (!KnownNumberComponent.class.isAssignableFrom(d.getClass())) {
                           continue;
                           // throw new NotANumberException("Could not recognize part:" + s + ". Resolved to a wrong type:" + d.getClass().getName() + ":" + d.getWord());
                        }
                        KnownNumberComponent known = (KnownNumberComponent) d;
                        if (maxCompoent == null){
                            maxCompoent = known;
                        } else {
                            if (known.getPosition().compareTo(maxCompoent.getPosition()) > 0) {
                                maxCompoent = known;
                            }
                        }
                        series.add(known);
                    }

                }
                // Got a full series.
                List<List<KnownNumberComponent>> coresseries = new ArrayList<List<KnownNumberComponent>>();

                List<KnownNumberComponent> undercrore = new ArrayList<KnownNumberComponent>();
                boolean firstcrore = true;
                for (KnownNumberComponent d : series) {
                    if (d.getPower() == maxCompoent.getPower() && d.getUnit() == 1) {
                        if (undercrore.isEmpty() && firstcrore) {
                            undercrore.add(NumberDictionary.ORU);

                        }
                        coresseries.add(undercrore);
                        undercrore = new ArrayList<KnownNumberComponent>();
                    } else {
                        undercrore.add(d);
                    }
                    firstcrore = false;
                }
                coresseries.add(undercrore);

                // Folded under biggest value
                boolean first = true;
                StringBuffer buffer = new StringBuffer();
                for (List<KnownNumberComponent> lessthancore : coresseries) {
                    if (lessthancore.isEmpty() && maxCompoent.getPower() == 0) continue;
                    BigInteger val = readAsNumberInternal(lessthancore, 0);
                    if (first) {
                        buffer.append(String.valueOf(val));
                    } else {
                        String _7digit = String.valueOf(val);
                        if (_7digit.length() > maxCompoent.getPower()) {
                            throw new NotANumberException("Encountering over-flow!:" + _7digit + ". Please make sure you break at hundreds, thousands, lakhs and crores. The usage such as நூறாயிரம் should be avoided. Use இலட்சம்  instead. Given:" + numbertext);
                        }
                        while (_7digit.length() < maxCompoent.getPower()) {
                            _7digit = "0" + _7digit;
                        }
                        buffer.append(_7digit);
                    }
                    first = false;
                }
                if (buffer.length() == 0) {
                    buffer.append("0");
                }
                return buffer.toString();
            }
        }

    }


    public TamilWord readNumber(long number, TamilWord denomi, FeatureSet set) {
        if (number < 0) {
            number = -number;
        }
        return readCleanNumber(String.valueOf(number), denomi, set);

    }

    public TamilWord readCleanFractionWithoutDot(String fraction, FeatureSet set) {
        TamilWord fword = TamilWord.from("புள்ளி");
        TamilWordPartContainer empty = new TamilWordPartContainer(new TamilWord());
        for (int i = 0; i < fraction.length(); i++) {
            fword.add(UnknownCharacter.SPACE);
            fword.addAll(new Ones((int) (fraction.charAt(i)) - 48).read(null, null, empty, null, set).getWord());


        }
        return fword;
    }

    private TamilWord readCleanNumber(String number, TamilWord denomi, FeatureSet set) {
        int rem = 0;
        if (number.length() > 7) {
            String remString = number.substring(number.length() - 7, number.length());
            try {
                rem = Integer.parseInt(remString);
            } catch (NumberFormatException ne) {
                throw new NotANumberException(ne.getMessage());
            }
            number = number.substring(0, number.length() - 7);


            TamilWord kodi = null;

            boolean more = true;

            if (rem == 0) {
                if (denomi != null && denomi.size() != 0) {
                    kodi = TamilWord.from("கோடியே");
                    if (!set.isNumberPurchchiFeatureFull()) {
                        kodi.add(UnknownCharacter.SPACE);
                    }

                } else {
                    kodi = TamilWord.from("கோடி");
                    more = false;
                }
            } else {
                kodi = TamilWord.from("கோடியே");
                if (!set.isNumberPurchchiFeatureFull()) {
                    kodi.add(UnknownCharacter.SPACE);
                }

            }
            TamilWord basesum = readCleanNumber(number, kodi, set);
            if (more) {
                WordsJoiner h = TamilFactory.createWordJoiner(basesum);
                h.addVaruMozhi(readCleanNumber(remString, denomi, set));
                basesum = h.getSum();
            }
            return basesum;

        } else {
            try {
                rem = Integer.parseInt(number);
            } catch (NumberFormatException ne) {
                throw new NotANumberException(ne.getMessage());
            }
            List<AbstractPlace> list = new ArrayList<AbstractPlace>();
            list.add(new Lakhs(rem / 100000));
            rem = rem % 100000;

            list.add(new Thousands(rem / 1000));
            rem = rem % 1000;

            list.add(new Hundreds(rem / 100));
            rem = rem % 100;


            list.add(new Tens(rem / 10));
            rem = rem % 10;
            list.add(new Ones(rem));

            return AbstractPlace.sumUp(list, denomi == null ? null : new TamilWordPartContainer(denomi), set).getWord();
        }


    }


}

