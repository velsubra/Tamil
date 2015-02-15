package my.interest.lang.tamil.impl;

import common.lang.impl.UnknownCharacter;
import my.interest.lang.tamil.impl.number.*;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.join.WordsJoiner;
import tamil.lang.api.number.NumberReader;

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


    private TamilWord readNumberWithFiltering(String number) {
        if (number == null) {
            return new TamilWord();
        }
        number = number.trim();

        StringBuffer base = new StringBuffer();
        StringBuffer fraction = new StringBuffer();

        boolean fractionReached = false;
        boolean noZeroReached = false;
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            if (ch >= '0' && ch <= '9' || ch == '.' || ch == '+'   || ch == '-' ) {
                  // Just fine
            } else {
                ch = '0';
            }

            if (!fractionReached && ch == '0' && !noZeroReached) continue;
            if (ch >= '0' && ch <= '9') {

                if (fractionReached) {
                    fraction.append(ch);
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
        TamilWord full = new TamilWord();
        if (base.length() == 0) {
            base.append('0');
        }

        full.addAll(readCleanNumber(base.toString(), null));
        if (fraction.length() > 0) {
            full.add(new UnknownCharacter(' '));
            full.addAll(readCleanFractionWithoutDot(fraction.toString()));
        }
        return full;


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
        return readCleanNumber(String.valueOf(number), null);
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
            bword = readCleanNumber(base, null);
        }
        if (fraction != null && fraction.length() > 0) {
            fword = readCleanFractionWithoutDot(fraction);
        }

        TamilWord full = new TamilWord();
        if (bword != null) {
            full.addAll(bword);
        }
        if (fword != null) {
            full.add(new UnknownCharacter(' '));
            full.addAll(fword);
        }
        return full;

    }


    public TamilWord readNumber(long number, TamilWord denomi) {
        if (number < 0) {
            number = -number;
        }
        return readCleanNumber(String.valueOf(number), denomi);

    }

    public TamilWord readCleanFractionWithoutDot(String fraction) {
        TamilWord fword = TamilWord.from("புள்ளி");
        TamilWordPartContainer empty = new TamilWordPartContainer(new TamilWord());
        for (int i = 0; i < fraction.length(); i++) {
            fword.add(new UnknownCharacter(' '));
            fword.addAll(new Ones((int) (fraction.charAt(i)) - 48).read(null, null, empty, null).getWord());


        }
        return fword;
    }

    public TamilWord readCleanNumber(String number, TamilWord denomi) {
        int rem = 0;
        if (number.length() > 7) {
            String remString = number.substring(number.length() - 7, number.length());
            rem = Integer.parseInt(remString);
            number = number.substring(0, number.length() - 7);


            TamilWord kodi = null;

            boolean more = true;

            if (rem == 0) {
                if (denomi != null && denomi.size() != 0) {
                    kodi = TamilWord.from("கோடியே");
                    kodi.add(new UnknownCharacter(' '));

                } else {
                    kodi = TamilWord.from("கோடி");
                    more = false;
                }
            } else {
                kodi = TamilWord.from("கோடியே");
                kodi.add(new UnknownCharacter(' '));

            }
            TamilWord basesum = readCleanNumber(number, kodi);
            if (more) {
                WordsJoiner h = TamilFactory.createWordJoiner(basesum);
                h.addVaruMozhi(readCleanNumber(remString, denomi));
                basesum = h.getSum();
            }
            return basesum;

        } else {
            rem = Integer.parseInt(number);
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

            return AbstractPlace.sumUp(list, denomi == null ? null : new TamilWordPartContainer(denomi)).getWord();
        }


    }


}

