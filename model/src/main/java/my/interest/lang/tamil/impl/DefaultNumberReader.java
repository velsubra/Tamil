package my.interest.lang.tamil.impl;

import common.lang.impl.UnknownCharacter;
import my.interest.lang.tamil.generated.types.NameValue;
import my.interest.lang.tamil.impl.number.*;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.join.WordsJoiner;
import tamil.lang.api.number.NotANumberException;
import tamil.lang.api.number.NumberReader;
import tamil.lang.api.number.ReaderFeature;

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

    private static NameValue filter(String number, FeatureSet set) {
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

        NameValue ret = new NameValue();
        ret.setValue(fraction.toString().substring(0, fractionLength));
        ret.setName(base.toString());
        return ret;


    }


    public TamilWord readNumberWithFiltering(String number, ReaderFeature... features) {
        if (number == null) {
            return new TamilWord();
        }
        FeatureSet set = features == null ? FeatureSet.EMPTY : new FeatureSet(features);
        NameValue val = filter(number, set);


        TamilWord full = new TamilWord();
        if (val.getName().length() == 0) {
            val.setName("0");
        }

        full.addAll(readCleanNumber(val.getName(), null, set));
        if (val.getValue().length() > 0) {
            if (full.size() > 0) {
                full.addSpace();
            }
            full.addAll(readCleanFractionWithoutDot(val.getValue(), set));
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

