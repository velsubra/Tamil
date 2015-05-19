package my.interest.lang.tamil.punar.handler.lagaraveeru;

import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilWord;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.TamilWordSplitResult;
import my.interest.lang.tamil.punar.handler.AbstractPunarchiHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class NannolHandler227 extends AbstractPunarchiHandler {
    @Override
    public String getName() {
        return "நன்னூல்விதி-227(லகர, ளகரவிதி)";
    }

    public static final NannolHandler227 HANDLER = new NannolHandler227();


    public List<TamilWordSplitResult> split(TamilWordPartContainer nilaipart, TamilWordPartContainer varumpart) {
        List<TamilWordSplitResult> list = new ArrayList<TamilWordSplitResult>();
        //முட் + செடி
        if (nilaipart.getWord().endsWith(TamilCompoundCharacter.IDD) && varumpart.isStartingWithOneConsonantsOfKASATHABA() && varumpart.isStartingWithUyirMei()) {
            TamilWord n = nilaipart.getWord().duplicate();
            n.removeLast();
            n.add(TamilCompoundCharacter.ILL);


            list.add(new TamilWordSplitResult(new TamilWordPartContainer(n), varumpart));
            return list;
        }

        if (nilaipart.getWord().endsWith(TamilCompoundCharacter.IRR) && varumpart.isStartingWithOneConsonantsOfKASATHABA() && varumpart.isStartingWithUyirMei()) {
            TamilWord n = nilaipart.getWord().duplicate();
            n.removeLast();
            n.add(TamilCompoundCharacter.IL);


            list.add(new TamilWordSplitResult(new TamilWordPartContainer(n), varumpart));
            return list;
        }


        return list;
    }

    @Override
    public TamilWordPartContainer join(TamilWordPartContainer nilai, TamilWordPartContainer varum) {

        return null;
    }
}