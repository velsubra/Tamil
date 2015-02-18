package common.lang.impl;

import common.lang.Character;
import common.lang.Sentence;
import common.lang.Word;

import java.util.LinkedList;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public abstract class AbstractWord<T extends Character> extends LinkedList<T> implements Word<T> {

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for ( Character c : this.toArray(new common.lang.Character[0])) {
              buffer.append(c.toString());
        }
        return buffer.toString();
    }

    public void setContainingSentence(Sentence containingSentence) {
        this.containingSentence = containingSentence;
    }


    private Sentence containingSentence;



    @Override
    public int getNextSpacesCount() {
        return nextSpacesCount;
    }

    public void setNextSpacesCount(int nextSpacesCount) {
        this.nextSpacesCount = nextSpacesCount;
    }

    private int nextSpacesCount;

    @Override
    public Sentence getContainingSentence() {
        return containingSentence;
    }

    public abstract AbstractWord<T>  duplicate();
}
