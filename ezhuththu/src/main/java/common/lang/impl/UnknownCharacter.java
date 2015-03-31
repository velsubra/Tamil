package common.lang.impl;

import common.lang.SimpleCharacter;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class UnknownCharacter extends AbstractCharacter implements SimpleCharacter{

    public static final UnknownCharacter   SPACE = new UnknownCharacter(' ');
    private UnknownCharacter(int value) {
        this.value = value;
    }


    public static UnknownCharacter getFor(int ch) {
        if (ch == ' ') {
            return SPACE;
        } else {
            return new UnknownCharacter(ch);
        }
    }
    public int getValue() {
        return value;
    }

//    public void setValue(int value) {
//        this.value = value;
//    }

    int value;

    @Override
    public String toString() {
        return Character.toString((char)getValue());
    }

    @Override
    public int compareTo(Object o) {
        if (o == null) return  1;
        if (!UnknownCharacter.class.isAssignableFrom(o.getClass())) {
            return -1;
        }
        return Integer.valueOf(getValue()).compareTo(((UnknownCharacter) o).getValue());
    }


    @Override
    public int getCodePointsCount() {
        return 1;
    }
}
