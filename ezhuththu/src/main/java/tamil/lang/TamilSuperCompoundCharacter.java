package tamil.lang;

import common.lang.CharacterSequenceCharacter;
import tamil.lang.exception.NoMeiPartException;
import tamil.lang.exception.NoUyirPartException;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TamilSuperCompoundCharacter extends TamilCharacter implements CharacterSequenceCharacter {
    TamilCharacter[] sequence = null;

    static List<TamilSuperCompoundCharacter> supercompound = new ArrayList<TamilSuperCompoundCharacter>();

    private TamilSuperCompoundCharacter(TamilCharacter... sequence) {
        this.sequence = sequence;
        supercompound.add(this);
    }


    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (TamilCharacter s : sequence) {
            buffer.append(s.toString());
        }
        return buffer.toString();
    }


    void init() {
        //Order is important
        this.typeSpecification |= 0;
        this.typeSpecification |= _isMeyyezhuththu() ? MEI : 0;
        this.typeSpecification |= _isUyirMeyyezhuththu() ? UYIR_MEI : 0;
        this.typeSpecification |= 0;
        this.typeSpecification |= VADA_MOZHI;
        this.typeSpecification |= _isKurilezhuththu() ? KURIL : 0;
        this.typeSpecification |= !_isKurilezhuththu() && !isMeyyezhuththu() ? NEDIL : 0;
        this.typeSpecification |= 0;
        this.typeSpecification |= 0;
        this.typeSpecification |= 0;

    }

    private boolean _isMeyyezhuththu() {
        return sequence[sequence.length - 1].isMeyyezhuththu();
    }


    private boolean _isUyirMeyyezhuththu() {
        return sequence[sequence.length - 1].isUyirMeyyezhuththu();
    }

    private boolean _isKurilezhuththu() {
        return sequence[sequence.length - 1].isKurilezhuththu();
    }


    @Override
    public TamilCompoundCharacter getMeiPart() throws NoMeiPartException {
        return sequence[sequence.length-1].getMeiPart();
       // throw new NoMeiPartException("Invalid call. There is no Mei part in this letter:" + this.toString());
    }


    @Override
    public TamilSimpleCharacter getUyirPart() throws NoUyirPartException {
        return sequence[sequence.length-1].getUyirPart();
       // throw new NoUyirPartException("Invalid call. There is no Uyir part in this letter:" + this.toString());
    }


    @Override
    public TamilCharacter addUyir(TamilSimpleCharacter s) {
        throw new RuntimeException(this + " is not UyiMei");
    }

    @Override
    public String translitToEnglish() {
        StringBuffer buffer = new StringBuffer();
        for (TamilCharacter t : sequence) {
             buffer.append(t.translitToEnglish());
        }
        return  buffer.toString();
    }

    @Override
    public int getCodePointsCount() {
        int count = 0;
        for (TamilCharacter t : sequence) {
            count += t.getCodePointsCount();
        }
        return count;
    }

    @Override
    public int[] getCodePoints() {
        int[] ret = new int[getCodePointsCount()];
        int i = 0;
        for (TamilCharacter t : sequence) {
            for (int c : t.getCodePoints()) {
                ret[i++] = c;
            }
        }
        return ret;
    }

    public TamilCharacter[] getSequence() {
        return sequence;
    }

    public static final TamilSuperCompoundCharacter SHREE_ = new TamilSuperCompoundCharacter(TamilCompoundCharacter.ISS_, TamilCompoundCharacter.IR_EE);

    public static final TamilSuperCompoundCharacter IKSH = new TamilSuperCompoundCharacter(TamilCompoundCharacter.IK, TamilCompoundCharacter.ISH_);
    public static final TamilSuperCompoundCharacter IKSHA = new TamilSuperCompoundCharacter(TamilCompoundCharacter.IK, TamilSimpleCharacter.SHA_);
    public static final TamilSuperCompoundCharacter IKSH_aa = new TamilSuperCompoundCharacter(TamilCompoundCharacter.IK, TamilCompoundCharacter.ISH_aa);
    public static final TamilSuperCompoundCharacter IKSH_E = new TamilSuperCompoundCharacter(TamilCompoundCharacter.IK, TamilCompoundCharacter.ISH_E);
    public static final TamilSuperCompoundCharacter IKSH_EE = new TamilSuperCompoundCharacter(TamilCompoundCharacter.IK, TamilCompoundCharacter.ISH_EE);
    public static final TamilSuperCompoundCharacter IKSH_U = new TamilSuperCompoundCharacter(TamilCompoundCharacter.IK, TamilCompoundCharacter.ISH_U);
    public static final TamilSuperCompoundCharacter IKSH_UU = new TamilSuperCompoundCharacter(TamilCompoundCharacter.IK, TamilCompoundCharacter.ISH_UU);
    public static final TamilSuperCompoundCharacter IKSH_A = new TamilSuperCompoundCharacter(TamilCompoundCharacter.IK, TamilCompoundCharacter.ISH_A);
    public static final TamilSuperCompoundCharacter IKSH_AA = new TamilSuperCompoundCharacter(TamilCompoundCharacter.IK, TamilCompoundCharacter.ISH_AA);
    public static final TamilSuperCompoundCharacter IKSH_I = new TamilSuperCompoundCharacter(TamilCompoundCharacter.IK, TamilCompoundCharacter.ISH_I);
    public static final TamilSuperCompoundCharacter IKSH_O = new TamilSuperCompoundCharacter(TamilCompoundCharacter.IK, TamilCompoundCharacter.ISH_O);
    public static final TamilSuperCompoundCharacter IKSH_OO = new TamilSuperCompoundCharacter(TamilCompoundCharacter.IK, TamilCompoundCharacter.ISH_OO);
    public static final TamilSuperCompoundCharacter IKSH_OU = new TamilSuperCompoundCharacter(TamilCompoundCharacter.IK, TamilCompoundCharacter.ISH_OU);

}
