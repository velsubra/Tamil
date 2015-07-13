package tamil.lang;

import tamil.lang.exception.TamilPlatformException;

import java.util.*;

/**
 * <p>
 * The class that registers all the tamil characters as defined by the Unicode consortium.  This allows reading Tamil text in the normalized form.
 * <p/>
 * <p/>
 * End users typically would not need to use this class. If you need to read a tamil word from a  String, please use
 * {@link TamilWord#from(String)}.  Glyphs that cannot be legally paired with adjacent consonants are mapped with  real vowel during normalization.
 * <p/>
 * <p/>
 * </p>
 *
 * @author velsubra
 */
public final class TamilCharacterLookUpContext {

    /**
     * Returns the current character in the context.
     */
    public TamilCharacter currentChar;

    // TamilCharacterLookUpContext parent = null;

    private Map<Integer, TamilCharacterLookUpContext> continuations = null;

    private static final Map<Integer, TamilCharacterLookUpContext> map = new HashMap<Integer, TamilCharacterLookUpContext>();

    private TamilCharacterLookUpContext(TamilCharacter simpleCharacter) {
        this.currentChar = simpleCharacter;
    }

    /**
     * The continuous registered sequence.
     *
     * @see <a href="ftp://ftp.unicode.org/Public/UNIDATA/UnicodeData.txt>UnicodeData.txt</a>
     */
    public Map<Integer, TamilCharacterLookUpContext> getContinuations() {
        if (continuations == null) {
            return null;
        }
        return Collections.unmodifiableMap(continuations);
    }

    public static Map<Integer, TamilCharacterLookUpContext> getMap() {
        return Collections.unmodifiableMap(map);
    }


    /**
     * Lists all tamil characters registered.
     *
     * @return the set of all known tamil characters
     */

    public Set<TamilCharacter> getAllTamilCharacters() {
        Set<TamilCharacter> list = new HashSet<TamilCharacter>();
        if (this.currentChar != null) {
            list.add(this.currentChar);
        }
        if (continuations != null) {
            for (TamilCharacterLookUpContext context : this.continuations.values()) {
                list.addAll(context.getAllTamilCharacters());
            }
        }
        return list;
    }


    private static TamilCharacterLookUpContext registerSimple(TamilSimpleCharacter simpleCharacter) {
        TamilCharacterLookUpContext context = map.get(simpleCharacter.getValue());

        if (context == null) {
            context = new TamilCharacterLookUpContext(simpleCharacter);
            map.put(simpleCharacter.getValue(), context);
        }
        return context;
    }

    private static TamilCharacterLookUpContext registerSimple(int value, TamilSimpleCharacter simpleCharacter) {
        TamilCharacterLookUpContext context = map.get(value);

        if (context == null) {
            context = new TamilCharacterLookUpContext(simpleCharacter);
            map.put(value, context);
        }
        return context;
    }

    /**
     * Returns the new Context for the next character in the path to be registered.
     *
     * @param continuation
     * @param tamilCharacter
     * @return the  TamilCharacterLookUpContext
     */
    private TamilCharacterLookUpContext registerNext(int continuation, TamilCharacter tamilCharacter) {
        if (continuations == null) {
            continuations = new HashMap<Integer, TamilCharacterLookUpContext>();
        }
        TamilCharacterLookUpContext context = new TamilCharacterLookUpContext(tamilCharacter);
        // context.parent = this;
        continuations.put(continuation, context);
        return context;

    }

    private List<TamilCharacterLookUpContext> registerCompoundAt(TamilCompoundCharacter tamilCharacter) {
        return  registerCompoundAt(tamilCharacter, tamilCharacter);
    }

    private List<TamilCharacterLookUpContext> registerCompoundAt(TamilCompoundCharacter tamilCompoundCharacter, TamilCharacter toRegister) {
        List<TamilCharacterLookUpContext> ret = new ArrayList<TamilCharacterLookUpContext>();
        ret.add(registerNext(tamilCompoundCharacter.getVowel(), toRegister));
        TamilCharacterLookUpContext context = this;
        if (tamilCompoundCharacter.vowelDecomposed != null && tamilCompoundCharacter.vowelDecomposed.length > 0) {
            for (int i = 0; i < tamilCompoundCharacter.vowelDecomposed.length; i++) {
                TamilCharacterLookUpContext newContext = context.next(tamilCompoundCharacter.vowelDecomposed[i]);
                if (newContext == null) {
                    if (i != tamilCompoundCharacter.vowelDecomposed.length - 1) {
                        throw new TamilPlatformException("Invalid registration at index: " + i + " of total length " + tamilCompoundCharacter.vowelDecomposed.length);
                    } else {
                        break;
                    }
                } else {
                    if (i == tamilCompoundCharacter.vowelDecomposed.length - 1) {
                        throw new TamilPlatformException("Invalid registration at last index: Character exists:" + newContext.currentChar);
                    }
                    context = newContext;

                }

            }
            ret.add(context.registerNext(tamilCompoundCharacter.vowelDecomposed[tamilCompoundCharacter.vowelDecomposed.length - 1], toRegister));

        }
        return ret;
    }

//    private List<TamilCharacterLookUpContext> registerSuperCompoundAt(TamilSuperCompoundCharacter tamilCharacter) {
//        if (tamilCharacter.getSequence().length <= 1) {
//            throw new TamilPlatformException("Invalid registration for TamilSuperCompoundCharacter. " + tamilCharacter + ". Minimum two characters are required");
//        }
//        List<TamilCharacterLookUpContext> ret = new ArrayList<TamilCharacterLookUpContext>();
//        ret.add(this);
//
//
//        for (int i = 1; i < tamilCharacter.getSequence().length; i++) {
//            List<TamilCharacterLookUpContext> newret = new ArrayList<TamilCharacterLookUpContext>();
//            for (TamilCharacterLookUpContext context : ret) {
//                TamilCharacter next = tamilCharacter.getSequence()[i];
//                if (TamilSimpleCharacter.class.isAssignableFrom(next.getClass())) {
//                    newret.add(context.registerNext(((TamilSimpleCharacter) next).getValue(), tamilCharacter));
//                }  else {
//                    throw new TamilPlatformException("Invalid character:" + next);
//                }
//            }
//            ret = newret;
//        }
//        return ret;
//    }


    static {
        registerSimple(TamilSimpleCharacter.OM);
        registerSimple(TamilSimpleCharacter.ZERO);
        registerSimple(TamilSimpleCharacter.ONE);
        registerSimple(TamilSimpleCharacter.TWO);
        registerSimple(TamilSimpleCharacter.THREE);
        registerSimple(TamilSimpleCharacter.FOUR);
        registerSimple(TamilSimpleCharacter.FIVE);
        registerSimple(TamilSimpleCharacter.SIX);
        registerSimple(TamilSimpleCharacter.SEVEN);
        registerSimple(TamilSimpleCharacter.EIGHT);
        registerSimple(TamilSimpleCharacter.NINE);
        registerSimple(TamilSimpleCharacter.TEN);
        registerSimple(TamilSimpleCharacter.HUNDRED);
        registerSimple(TamilSimpleCharacter.THOUSAND);
        registerSimple(TamilSimpleCharacter.RS);


        registerSimple(TamilSimpleCharacter.AKTHU);
        registerSimple(TamilSimpleCharacter.a);
        registerSimple(TamilSimpleCharacter.aa);
        registerSimple(TamilSimpleCharacter.E);
        registerSimple(TamilSimpleCharacter.EE);
        registerSimple(TamilSimpleCharacter.U);
        registerSimple(TamilSimpleCharacter.UU);
        registerSimple(TamilSimpleCharacter.A);
        registerSimple(TamilSimpleCharacter.AA);
        registerSimple(TamilSimpleCharacter.I);
        registerSimple(TamilSimpleCharacter.O);

        //ftp://ftp.unicode.org/Public/UNIDATA/UnicodeData.txt
        //0B94;TAMIL LETTER AU;Lo;0;L;0B92 0BD7;;;;N;;;;;
        registerSimple(TamilSimpleCharacter.O).registerNext(TamilCompoundCharacter.OU_, TamilSimpleCharacter.OU);

        registerSimple(TamilSimpleCharacter.OO);
        registerSimple(TamilSimpleCharacter.OU);


        //Register extension vowels as simple real vowels. Useful when the consonant was missed.
        registerSimple(TamilCompoundCharacter.aa, TamilSimpleCharacter.aa);
        registerSimple(TamilCompoundCharacter.E, TamilSimpleCharacter.E);
        registerSimple(TamilCompoundCharacter.EE, TamilSimpleCharacter.EE);
        registerSimple(TamilCompoundCharacter.U, TamilSimpleCharacter.U);
        registerSimple(TamilCompoundCharacter.UU, TamilSimpleCharacter.UU);
        registerSimple(TamilCompoundCharacter.A, TamilSimpleCharacter.A);
        registerSimple(TamilCompoundCharacter.AA, TamilSimpleCharacter.AA);
        registerSimple(TamilCompoundCharacter.I, TamilSimpleCharacter.I);
        registerSimple(TamilCompoundCharacter.O, TamilSimpleCharacter.O);
        registerSimple(TamilCompoundCharacter.OO, TamilSimpleCharacter.OO);
        registerSimple(TamilCompoundCharacter.OU, TamilSimpleCharacter.OU);
        registerSimple(TamilCompoundCharacter.OU_, TamilSimpleCharacter.OU);


        List<TamilCharacterLookUpContext> iks = registerSimple(TamilSimpleCharacter.KA).registerCompoundAt(TamilCompoundCharacter.IK);
        registerSimple(TamilSimpleCharacter.NGA).registerCompoundAt(TamilCompoundCharacter.ING);
        registerSimple(TamilSimpleCharacter.SA).registerCompoundAt(TamilCompoundCharacter.ICH);
        registerSimple(TamilSimpleCharacter.NYA).registerCompoundAt(TamilCompoundCharacter.INJ);
        registerSimple(TamilSimpleCharacter.DA).registerCompoundAt(TamilCompoundCharacter.IDD);
        registerSimple(TamilSimpleCharacter.NNNA).registerCompoundAt(TamilCompoundCharacter.INNN);
        registerSimple(TamilSimpleCharacter.THA).registerCompoundAt(TamilCompoundCharacter.ITH);
        registerSimple(TamilSimpleCharacter.NTHA).registerCompoundAt(TamilCompoundCharacter.INTH);
        registerSimple(TamilSimpleCharacter.PA).registerCompoundAt(TamilCompoundCharacter.IP);
        registerSimple(TamilSimpleCharacter.MA).registerCompoundAt(TamilCompoundCharacter.IM);
        registerSimple(TamilSimpleCharacter.YA).registerCompoundAt(TamilCompoundCharacter.IY);
        registerSimple(TamilSimpleCharacter.RA).registerCompoundAt(TamilCompoundCharacter.IR);
        registerSimple(TamilSimpleCharacter.LA).registerCompoundAt(TamilCompoundCharacter.IL);
        registerSimple(TamilSimpleCharacter.VA).registerCompoundAt(TamilCompoundCharacter.IV);
        registerSimple(TamilSimpleCharacter.LLLA).registerCompoundAt(TamilCompoundCharacter.ILLL);
        registerSimple(TamilSimpleCharacter.LLA).registerCompoundAt(TamilCompoundCharacter.ILL);
        registerSimple(TamilSimpleCharacter.RRA).registerCompoundAt(TamilCompoundCharacter.IRR);
        registerSimple(TamilSimpleCharacter.NA).registerCompoundAt(TamilCompoundCharacter.IN);

        registerSimple(TamilSimpleCharacter.SHA_).registerCompoundAt(TamilCompoundCharacter.ISH_);
        List<TamilCharacterLookUpContext> isss = registerSimple(TamilSimpleCharacter.SSA_).registerCompoundAt(TamilCompoundCharacter.ISS_);
        registerSimple(TamilSimpleCharacter.SSSA_).registerCompoundAt(TamilCompoundCharacter.ISSS_);
        registerSimple(TamilSimpleCharacter.HA_).registerCompoundAt(TamilCompoundCharacter.IH_);
        registerSimple(TamilSimpleCharacter.JA_).registerCompoundAt(TamilCompoundCharacter.IJ_);


        registerSimple(TamilSimpleCharacter.KA).registerCompoundAt(TamilCompoundCharacter.IK_I);
        registerSimple(TamilSimpleCharacter.NGA).registerCompoundAt(TamilCompoundCharacter.ING_I);
        registerSimple(TamilSimpleCharacter.SA).registerCompoundAt(TamilCompoundCharacter.ICH_I);
        registerSimple(TamilSimpleCharacter.NYA).registerCompoundAt(TamilCompoundCharacter.INJ_I);
        registerSimple(TamilSimpleCharacter.DA).registerCompoundAt(TamilCompoundCharacter.IDD_I);
        registerSimple(TamilSimpleCharacter.NNNA).registerCompoundAt(TamilCompoundCharacter.INNN_I);
        registerSimple(TamilSimpleCharacter.THA).registerCompoundAt(TamilCompoundCharacter.ITH_I);
        registerSimple(TamilSimpleCharacter.NTHA).registerCompoundAt(TamilCompoundCharacter.INTH_I);
        registerSimple(TamilSimpleCharacter.PA).registerCompoundAt(TamilCompoundCharacter.IP_I);
        registerSimple(TamilSimpleCharacter.MA).registerCompoundAt(TamilCompoundCharacter.IM_I);
        registerSimple(TamilSimpleCharacter.YA).registerCompoundAt(TamilCompoundCharacter.IY_I);
        registerSimple(TamilSimpleCharacter.RA).registerCompoundAt(TamilCompoundCharacter.IR_I);
        registerSimple(TamilSimpleCharacter.LA).registerCompoundAt(TamilCompoundCharacter.IL_I);
        registerSimple(TamilSimpleCharacter.VA).registerCompoundAt(TamilCompoundCharacter.IV_I);
        registerSimple(TamilSimpleCharacter.LLLA).registerCompoundAt(TamilCompoundCharacter.ILLL_I);
        registerSimple(TamilSimpleCharacter.LLA).registerCompoundAt(TamilCompoundCharacter.ILL_I);
        registerSimple(TamilSimpleCharacter.RRA).registerCompoundAt(TamilCompoundCharacter.IRR_I);
        registerSimple(TamilSimpleCharacter.NA).registerCompoundAt(TamilCompoundCharacter.IN_I);

        registerSimple(TamilSimpleCharacter.SHA_).registerCompoundAt(TamilCompoundCharacter.ISH_I);
        registerSimple(TamilSimpleCharacter.SSA_).registerCompoundAt(TamilCompoundCharacter.ISS_I);
        registerSimple(TamilSimpleCharacter.SSSA_).registerCompoundAt(TamilCompoundCharacter.ISSS_I);
        registerSimple(TamilSimpleCharacter.HA_).registerCompoundAt(TamilCompoundCharacter.IH_I);
        registerSimple(TamilSimpleCharacter.JA_).registerCompoundAt(TamilCompoundCharacter.IJ_I);


        registerSimple(TamilSimpleCharacter.KA).registerCompoundAt(TamilCompoundCharacter.IK_AA);
        registerSimple(TamilSimpleCharacter.NGA).registerCompoundAt(TamilCompoundCharacter.ING_AA);
        registerSimple(TamilSimpleCharacter.SA).registerCompoundAt(TamilCompoundCharacter.ICH_AA);
        registerSimple(TamilSimpleCharacter.NYA).registerCompoundAt(TamilCompoundCharacter.INJ_AA);
        registerSimple(TamilSimpleCharacter.DA).registerCompoundAt(TamilCompoundCharacter.IDD_AA);
        registerSimple(TamilSimpleCharacter.NNNA).registerCompoundAt(TamilCompoundCharacter.INNN_AA);
        registerSimple(TamilSimpleCharacter.THA).registerCompoundAt(TamilCompoundCharacter.ITH_AA);
        registerSimple(TamilSimpleCharacter.NTHA).registerCompoundAt(TamilCompoundCharacter.INTH_AA);
        registerSimple(TamilSimpleCharacter.PA).registerCompoundAt(TamilCompoundCharacter.IP_AA);
        registerSimple(TamilSimpleCharacter.MA).registerCompoundAt(TamilCompoundCharacter.IM_AA);
        registerSimple(TamilSimpleCharacter.YA).registerCompoundAt(TamilCompoundCharacter.IY_AA);
        registerSimple(TamilSimpleCharacter.RA).registerCompoundAt(TamilCompoundCharacter.IR_AA);
        registerSimple(TamilSimpleCharacter.LA).registerCompoundAt(TamilCompoundCharacter.IL_AA);
        registerSimple(TamilSimpleCharacter.VA).registerCompoundAt(TamilCompoundCharacter.IV_AA);
        registerSimple(TamilSimpleCharacter.LLLA).registerCompoundAt(TamilCompoundCharacter.ILLL_AA);
        registerSimple(TamilSimpleCharacter.LLA).registerCompoundAt(TamilCompoundCharacter.ILL_AA);
        registerSimple(TamilSimpleCharacter.RRA).registerCompoundAt(TamilCompoundCharacter.IRR_AA);
        registerSimple(TamilSimpleCharacter.NA).registerCompoundAt(TamilCompoundCharacter.IN_AA);

        registerSimple(TamilSimpleCharacter.SHA_).registerCompoundAt(TamilCompoundCharacter.ISH_AA);
        registerSimple(TamilSimpleCharacter.SSA_).registerCompoundAt(TamilCompoundCharacter.ISS_AA);
        registerSimple(TamilSimpleCharacter.SSSA_).registerCompoundAt(TamilCompoundCharacter.ISSS_AA);
        registerSimple(TamilSimpleCharacter.HA_).registerCompoundAt(TamilCompoundCharacter.IH_AA);
        registerSimple(TamilSimpleCharacter.JA_).registerCompoundAt(TamilCompoundCharacter.IJ_AA);


        registerSimple(TamilSimpleCharacter.KA).registerCompoundAt(TamilCompoundCharacter.IK_A);
        registerSimple(TamilSimpleCharacter.NGA).registerCompoundAt(TamilCompoundCharacter.ING_A);
        registerSimple(TamilSimpleCharacter.SA).registerCompoundAt(TamilCompoundCharacter.ICH_A);
        registerSimple(TamilSimpleCharacter.NYA).registerCompoundAt(TamilCompoundCharacter.INJ_A);
        registerSimple(TamilSimpleCharacter.DA).registerCompoundAt(TamilCompoundCharacter.IDD_A);
        registerSimple(TamilSimpleCharacter.NNNA).registerCompoundAt(TamilCompoundCharacter.INNN_A);
        registerSimple(TamilSimpleCharacter.THA).registerCompoundAt(TamilCompoundCharacter.ITH_A);
        registerSimple(TamilSimpleCharacter.NTHA).registerCompoundAt(TamilCompoundCharacter.INTH_A);
        registerSimple(TamilSimpleCharacter.PA).registerCompoundAt(TamilCompoundCharacter.IP_A);
        registerSimple(TamilSimpleCharacter.MA).registerCompoundAt(TamilCompoundCharacter.IM_A);
        registerSimple(TamilSimpleCharacter.YA).registerCompoundAt(TamilCompoundCharacter.IY_A);
        registerSimple(TamilSimpleCharacter.RA).registerCompoundAt(TamilCompoundCharacter.IR_A);
        registerSimple(TamilSimpleCharacter.LA).registerCompoundAt(TamilCompoundCharacter.IL_A);
        registerSimple(TamilSimpleCharacter.VA).registerCompoundAt(TamilCompoundCharacter.IV_A);
        registerSimple(TamilSimpleCharacter.LLLA).registerCompoundAt(TamilCompoundCharacter.ILLL_A);
        registerSimple(TamilSimpleCharacter.LLA).registerCompoundAt(TamilCompoundCharacter.ILL_A);
        registerSimple(TamilSimpleCharacter.RRA).registerCompoundAt(TamilCompoundCharacter.IRR_A);
        registerSimple(TamilSimpleCharacter.NA).registerCompoundAt(TamilCompoundCharacter.IN_A);

        registerSimple(TamilSimpleCharacter.SHA_).registerCompoundAt(TamilCompoundCharacter.ISH_A);
        registerSimple(TamilSimpleCharacter.SSA_).registerCompoundAt(TamilCompoundCharacter.ISS_A);
        registerSimple(TamilSimpleCharacter.SSSA_).registerCompoundAt(TamilCompoundCharacter.ISSS_A);
        registerSimple(TamilSimpleCharacter.HA_).registerCompoundAt(TamilCompoundCharacter.IH_A);
        registerSimple(TamilSimpleCharacter.JA_).registerCompoundAt(TamilCompoundCharacter.IJ_A);


        registerSimple(TamilSimpleCharacter.KA).registerCompoundAt(TamilCompoundCharacter.IK_UU);
        registerSimple(TamilSimpleCharacter.NGA).registerCompoundAt(TamilCompoundCharacter.ING_UU);
        registerSimple(TamilSimpleCharacter.SA).registerCompoundAt(TamilCompoundCharacter.ICH_UU);
        registerSimple(TamilSimpleCharacter.NYA).registerCompoundAt(TamilCompoundCharacter.INJ_UU);
        registerSimple(TamilSimpleCharacter.DA).registerCompoundAt(TamilCompoundCharacter.IDD_UU);
        registerSimple(TamilSimpleCharacter.NNNA).registerCompoundAt(TamilCompoundCharacter.INNN_UU);
        registerSimple(TamilSimpleCharacter.THA).registerCompoundAt(TamilCompoundCharacter.ITH_UU);
        registerSimple(TamilSimpleCharacter.NTHA).registerCompoundAt(TamilCompoundCharacter.INTH_UU);
        registerSimple(TamilSimpleCharacter.PA).registerCompoundAt(TamilCompoundCharacter.IP_UU);
        registerSimple(TamilSimpleCharacter.MA).registerCompoundAt(TamilCompoundCharacter.IM_UU);
        registerSimple(TamilSimpleCharacter.YA).registerCompoundAt(TamilCompoundCharacter.IY_UU);
        registerSimple(TamilSimpleCharacter.RA).registerCompoundAt(TamilCompoundCharacter.IR_UU);
        registerSimple(TamilSimpleCharacter.LA).registerCompoundAt(TamilCompoundCharacter.IL_UU);
        registerSimple(TamilSimpleCharacter.VA).registerCompoundAt(TamilCompoundCharacter.IV_UU);
        registerSimple(TamilSimpleCharacter.LLLA).registerCompoundAt(TamilCompoundCharacter.ILLL_UU);
        registerSimple(TamilSimpleCharacter.LLA).registerCompoundAt(TamilCompoundCharacter.ILL_UU);
        registerSimple(TamilSimpleCharacter.RRA).registerCompoundAt(TamilCompoundCharacter.IRR_UU);
        registerSimple(TamilSimpleCharacter.NA).registerCompoundAt(TamilCompoundCharacter.IN_UU);

        registerSimple(TamilSimpleCharacter.SHA_).registerCompoundAt(TamilCompoundCharacter.ISH_UU);
        registerSimple(TamilSimpleCharacter.SSA_).registerCompoundAt(TamilCompoundCharacter.ISS_UU);
        registerSimple(TamilSimpleCharacter.SSSA_).registerCompoundAt(TamilCompoundCharacter.ISSS_UU);
        registerSimple(TamilSimpleCharacter.HA_).registerCompoundAt(TamilCompoundCharacter.IH_UU);
        registerSimple(TamilSimpleCharacter.JA_).registerCompoundAt(TamilCompoundCharacter.IJ_UU);


        registerSimple(TamilSimpleCharacter.KA).registerCompoundAt(TamilCompoundCharacter.IK_U);
        registerSimple(TamilSimpleCharacter.NGA).registerCompoundAt(TamilCompoundCharacter.ING_U);
        registerSimple(TamilSimpleCharacter.SA).registerCompoundAt(TamilCompoundCharacter.ICH_U);
        registerSimple(TamilSimpleCharacter.NYA).registerCompoundAt(TamilCompoundCharacter.INJ_U);
        registerSimple(TamilSimpleCharacter.DA).registerCompoundAt(TamilCompoundCharacter.IDD_U);
        registerSimple(TamilSimpleCharacter.NNNA).registerCompoundAt(TamilCompoundCharacter.INNN_U);
        registerSimple(TamilSimpleCharacter.THA).registerCompoundAt(TamilCompoundCharacter.ITH_U);
        registerSimple(TamilSimpleCharacter.NTHA).registerCompoundAt(TamilCompoundCharacter.INTH_U);
        registerSimple(TamilSimpleCharacter.PA).registerCompoundAt(TamilCompoundCharacter.IP_U);
        registerSimple(TamilSimpleCharacter.MA).registerCompoundAt(TamilCompoundCharacter.IM_U);
        registerSimple(TamilSimpleCharacter.YA).registerCompoundAt(TamilCompoundCharacter.IY_U);
        registerSimple(TamilSimpleCharacter.RA).registerCompoundAt(TamilCompoundCharacter.IR_U);
        registerSimple(TamilSimpleCharacter.LA).registerCompoundAt(TamilCompoundCharacter.IL_U);
        registerSimple(TamilSimpleCharacter.VA).registerCompoundAt(TamilCompoundCharacter.IV_U);
        registerSimple(TamilSimpleCharacter.LLLA).registerCompoundAt(TamilCompoundCharacter.ILLL_U);
        registerSimple(TamilSimpleCharacter.LLA).registerCompoundAt(TamilCompoundCharacter.ILL_U);
        registerSimple(TamilSimpleCharacter.RRA).registerCompoundAt(TamilCompoundCharacter.IRR_U);
        registerSimple(TamilSimpleCharacter.NA).registerCompoundAt(TamilCompoundCharacter.IN_U);

        registerSimple(TamilSimpleCharacter.SHA_).registerCompoundAt(TamilCompoundCharacter.ISH_U);
        registerSimple(TamilSimpleCharacter.SSA_).registerCompoundAt(TamilCompoundCharacter.ISS_U);
        registerSimple(TamilSimpleCharacter.SSSA_).registerCompoundAt(TamilCompoundCharacter.ISSS_U);
        registerSimple(TamilSimpleCharacter.HA_).registerCompoundAt(TamilCompoundCharacter.IH_U);
        registerSimple(TamilSimpleCharacter.JA_).registerCompoundAt(TamilCompoundCharacter.IJ_U);


        registerSimple(TamilSimpleCharacter.KA).registerCompoundAt(TamilCompoundCharacter.IK_EE);
        registerSimple(TamilSimpleCharacter.NGA).registerCompoundAt(TamilCompoundCharacter.ING_EE);
        registerSimple(TamilSimpleCharacter.SA).registerCompoundAt(TamilCompoundCharacter.ICH_EE);
        registerSimple(TamilSimpleCharacter.NYA).registerCompoundAt(TamilCompoundCharacter.INJ_EE);
        registerSimple(TamilSimpleCharacter.DA).registerCompoundAt(TamilCompoundCharacter.IDD_EE);
        registerSimple(TamilSimpleCharacter.NNNA).registerCompoundAt(TamilCompoundCharacter.INNN_EE);
        registerSimple(TamilSimpleCharacter.THA).registerCompoundAt(TamilCompoundCharacter.ITH_EE);
        registerSimple(TamilSimpleCharacter.NTHA).registerCompoundAt(TamilCompoundCharacter.INTH_EE);
        registerSimple(TamilSimpleCharacter.PA).registerCompoundAt(TamilCompoundCharacter.IP_EE);
        registerSimple(TamilSimpleCharacter.MA).registerCompoundAt(TamilCompoundCharacter.IM_EE);
        registerSimple(TamilSimpleCharacter.YA).registerCompoundAt(TamilCompoundCharacter.IY_EE);
        registerSimple(TamilSimpleCharacter.RA).registerCompoundAt(TamilCompoundCharacter.IR_EE);
        registerSimple(TamilSimpleCharacter.LA).registerCompoundAt(TamilCompoundCharacter.IL_EE);
        registerSimple(TamilSimpleCharacter.VA).registerCompoundAt(TamilCompoundCharacter.IV_EE);
        registerSimple(TamilSimpleCharacter.LLLA).registerCompoundAt(TamilCompoundCharacter.ILLL_EE);
        registerSimple(TamilSimpleCharacter.LLA).registerCompoundAt(TamilCompoundCharacter.ILL_EE);
        registerSimple(TamilSimpleCharacter.RRA).registerCompoundAt(TamilCompoundCharacter.IRR_EE);
        registerSimple(TamilSimpleCharacter.NA).registerCompoundAt(TamilCompoundCharacter.IN_EE);

        registerSimple(TamilSimpleCharacter.SHA_).registerCompoundAt(TamilCompoundCharacter.ISH_EE);
        registerSimple(TamilSimpleCharacter.SSA_).registerCompoundAt(TamilCompoundCharacter.ISS_EE);
        registerSimple(TamilSimpleCharacter.SSSA_).registerCompoundAt(TamilCompoundCharacter.ISSS_EE);
        registerSimple(TamilSimpleCharacter.HA_).registerCompoundAt(TamilCompoundCharacter.IH_EE);
        registerSimple(TamilSimpleCharacter.JA_).registerCompoundAt(TamilCompoundCharacter.IJ_EE);


        registerSimple(TamilSimpleCharacter.KA).registerCompoundAt(TamilCompoundCharacter.IK_E);
        registerSimple(TamilSimpleCharacter.NGA).registerCompoundAt(TamilCompoundCharacter.ING_E);
        registerSimple(TamilSimpleCharacter.SA).registerCompoundAt(TamilCompoundCharacter.ICH_E);
        registerSimple(TamilSimpleCharacter.NYA).registerCompoundAt(TamilCompoundCharacter.INJ_E);
        registerSimple(TamilSimpleCharacter.DA).registerCompoundAt(TamilCompoundCharacter.IDD_E);
        registerSimple(TamilSimpleCharacter.NNNA).registerCompoundAt(TamilCompoundCharacter.INNN_E);
        registerSimple(TamilSimpleCharacter.THA).registerCompoundAt(TamilCompoundCharacter.ITH_E);
        registerSimple(TamilSimpleCharacter.NTHA).registerCompoundAt(TamilCompoundCharacter.INTH_E);
        registerSimple(TamilSimpleCharacter.PA).registerCompoundAt(TamilCompoundCharacter.IP_E);
        registerSimple(TamilSimpleCharacter.MA).registerCompoundAt(TamilCompoundCharacter.IM_E);
        registerSimple(TamilSimpleCharacter.YA).registerCompoundAt(TamilCompoundCharacter.IY_E);
        registerSimple(TamilSimpleCharacter.RA).registerCompoundAt(TamilCompoundCharacter.IR_E);
        registerSimple(TamilSimpleCharacter.LA).registerCompoundAt(TamilCompoundCharacter.IL_E);
        registerSimple(TamilSimpleCharacter.VA).registerCompoundAt(TamilCompoundCharacter.IV_E);
        registerSimple(TamilSimpleCharacter.LLLA).registerCompoundAt(TamilCompoundCharacter.ILLL_E);
        registerSimple(TamilSimpleCharacter.LLA).registerCompoundAt(TamilCompoundCharacter.ILL_E);
        registerSimple(TamilSimpleCharacter.RRA).registerCompoundAt(TamilCompoundCharacter.IRR_E);
        registerSimple(TamilSimpleCharacter.NA).registerCompoundAt(TamilCompoundCharacter.IN_E);

        registerSimple(TamilSimpleCharacter.SHA_).registerCompoundAt(TamilCompoundCharacter.ISH_E);
        registerSimple(TamilSimpleCharacter.SSA_).registerCompoundAt(TamilCompoundCharacter.ISS_E);
        registerSimple(TamilSimpleCharacter.SSSA_).registerCompoundAt(TamilCompoundCharacter.ISSS_E);
        registerSimple(TamilSimpleCharacter.HA_).registerCompoundAt(TamilCompoundCharacter.IH_E);
        registerSimple(TamilSimpleCharacter.JA_).registerCompoundAt(TamilCompoundCharacter.IJ_E);


        registerSimple(TamilSimpleCharacter.KA).registerCompoundAt(TamilCompoundCharacter.IK_aa);
        registerSimple(TamilSimpleCharacter.NGA).registerCompoundAt(TamilCompoundCharacter.ING_aa);
        registerSimple(TamilSimpleCharacter.SA).registerCompoundAt(TamilCompoundCharacter.ICH_aa);
        registerSimple(TamilSimpleCharacter.NYA).registerCompoundAt(TamilCompoundCharacter.INJ_aa);
        registerSimple(TamilSimpleCharacter.DA).registerCompoundAt(TamilCompoundCharacter.IDD_aa);
        registerSimple(TamilSimpleCharacter.NNNA).registerCompoundAt(TamilCompoundCharacter.INNN_aa);
        registerSimple(TamilSimpleCharacter.THA).registerCompoundAt(TamilCompoundCharacter.ITH_aa);
        registerSimple(TamilSimpleCharacter.NTHA).registerCompoundAt(TamilCompoundCharacter.INTH_aa);
        registerSimple(TamilSimpleCharacter.PA).registerCompoundAt(TamilCompoundCharacter.IP_aa);
        registerSimple(TamilSimpleCharacter.MA).registerCompoundAt(TamilCompoundCharacter.IM_aa);
        registerSimple(TamilSimpleCharacter.YA).registerCompoundAt(TamilCompoundCharacter.IY_aa);
        registerSimple(TamilSimpleCharacter.RA).registerCompoundAt(TamilCompoundCharacter.IR_aa);
        registerSimple(TamilSimpleCharacter.LA).registerCompoundAt(TamilCompoundCharacter.IL_aa);
        registerSimple(TamilSimpleCharacter.VA).registerCompoundAt(TamilCompoundCharacter.IV_aa);
        registerSimple(TamilSimpleCharacter.LLLA).registerCompoundAt(TamilCompoundCharacter.ILLL_aa);
        registerSimple(TamilSimpleCharacter.LLA).registerCompoundAt(TamilCompoundCharacter.ILL_aa);
        registerSimple(TamilSimpleCharacter.RRA).registerCompoundAt(TamilCompoundCharacter.IRR_aa);
        registerSimple(TamilSimpleCharacter.NA).registerCompoundAt(TamilCompoundCharacter.IN_aa);

        registerSimple(TamilSimpleCharacter.SHA_).registerCompoundAt(TamilCompoundCharacter.ISH_aa);
        registerSimple(TamilSimpleCharacter.SSA_).registerCompoundAt(TamilCompoundCharacter.ISS_aa);
        registerSimple(TamilSimpleCharacter.SSSA_).registerCompoundAt(TamilCompoundCharacter.ISSS_aa);
        registerSimple(TamilSimpleCharacter.HA_).registerCompoundAt(TamilCompoundCharacter.IH_aa);
        registerSimple(TamilSimpleCharacter.JA_).registerCompoundAt(TamilCompoundCharacter.IJ_aa);


        // These have to registered later to help the normalization.

        registerSimple(TamilSimpleCharacter.KA).registerCompoundAt(TamilCompoundCharacter.IK_OU);
        registerSimple(TamilSimpleCharacter.NGA).registerCompoundAt(TamilCompoundCharacter.ING_OU);
        registerSimple(TamilSimpleCharacter.SA).registerCompoundAt(TamilCompoundCharacter.ICH_OU);
        registerSimple(TamilSimpleCharacter.NYA).registerCompoundAt(TamilCompoundCharacter.INJ_OU);
        registerSimple(TamilSimpleCharacter.DA).registerCompoundAt(TamilCompoundCharacter.IDD_OU);
        registerSimple(TamilSimpleCharacter.NNNA).registerCompoundAt(TamilCompoundCharacter.INNN_OU);
        registerSimple(TamilSimpleCharacter.THA).registerCompoundAt(TamilCompoundCharacter.ITH_OU);
        registerSimple(TamilSimpleCharacter.NTHA).registerCompoundAt(TamilCompoundCharacter.INTH_OU);
        registerSimple(TamilSimpleCharacter.PA).registerCompoundAt(TamilCompoundCharacter.IP_OU);
        registerSimple(TamilSimpleCharacter.MA).registerCompoundAt(TamilCompoundCharacter.IM_OU);
        registerSimple(TamilSimpleCharacter.YA).registerCompoundAt(TamilCompoundCharacter.IY_OU);
        registerSimple(TamilSimpleCharacter.RA).registerCompoundAt(TamilCompoundCharacter.IR_OU);
        registerSimple(TamilSimpleCharacter.LA).registerCompoundAt(TamilCompoundCharacter.IL_OU);
        registerSimple(TamilSimpleCharacter.VA).registerCompoundAt(TamilCompoundCharacter.IV_OU);
        registerSimple(TamilSimpleCharacter.LLLA).registerCompoundAt(TamilCompoundCharacter.ILLL_OU);
        registerSimple(TamilSimpleCharacter.LLA).registerCompoundAt(TamilCompoundCharacter.ILL_OU);
        registerSimple(TamilSimpleCharacter.RRA).registerCompoundAt(TamilCompoundCharacter.IRR_OU);
        registerSimple(TamilSimpleCharacter.NA).registerCompoundAt(TamilCompoundCharacter.IN_OU);

        registerSimple(TamilSimpleCharacter.SHA_).registerCompoundAt(TamilCompoundCharacter.ISH_OU);
        registerSimple(TamilSimpleCharacter.SSA_).registerCompoundAt(TamilCompoundCharacter.ISS_OU);
        registerSimple(TamilSimpleCharacter.SSSA_).registerCompoundAt(TamilCompoundCharacter.ISSS_OU);
        registerSimple(TamilSimpleCharacter.HA_).registerCompoundAt(TamilCompoundCharacter.IH_OU);
        registerSimple(TamilSimpleCharacter.JA_).registerCompoundAt(TamilCompoundCharacter.IJ_OU);


        registerSimple(TamilSimpleCharacter.KA).registerCompoundAt(TamilCompoundCharacter.IK_OO);
        registerSimple(TamilSimpleCharacter.NGA).registerCompoundAt(TamilCompoundCharacter.ING_OO);
        registerSimple(TamilSimpleCharacter.SA).registerCompoundAt(TamilCompoundCharacter.ICH_OO);
        registerSimple(TamilSimpleCharacter.NYA).registerCompoundAt(TamilCompoundCharacter.INJ_OO);
        registerSimple(TamilSimpleCharacter.DA).registerCompoundAt(TamilCompoundCharacter.IDD_OO);
        registerSimple(TamilSimpleCharacter.NNNA).registerCompoundAt(TamilCompoundCharacter.INNN_OO);
        registerSimple(TamilSimpleCharacter.THA).registerCompoundAt(TamilCompoundCharacter.ITH_OO);
        registerSimple(TamilSimpleCharacter.NTHA).registerCompoundAt(TamilCompoundCharacter.INTH_OO);
        registerSimple(TamilSimpleCharacter.PA).registerCompoundAt(TamilCompoundCharacter.IP_OO);
        registerSimple(TamilSimpleCharacter.MA).registerCompoundAt(TamilCompoundCharacter.IM_OO);
        registerSimple(TamilSimpleCharacter.YA).registerCompoundAt(TamilCompoundCharacter.IY_OO);
        registerSimple(TamilSimpleCharacter.RA).registerCompoundAt(TamilCompoundCharacter.IR_OO);
        registerSimple(TamilSimpleCharacter.LA).registerCompoundAt(TamilCompoundCharacter.IL_OO);
        registerSimple(TamilSimpleCharacter.VA).registerCompoundAt(TamilCompoundCharacter.IV_OO);
        registerSimple(TamilSimpleCharacter.LLLA).registerCompoundAt(TamilCompoundCharacter.ILLL_OO);
        registerSimple(TamilSimpleCharacter.LLA).registerCompoundAt(TamilCompoundCharacter.ILL_OO);
        registerSimple(TamilSimpleCharacter.RRA).registerCompoundAt(TamilCompoundCharacter.IRR_OO);
        registerSimple(TamilSimpleCharacter.NA).registerCompoundAt(TamilCompoundCharacter.IN_OO);

        registerSimple(TamilSimpleCharacter.SHA_).registerCompoundAt(TamilCompoundCharacter.ISH_OO);
        registerSimple(TamilSimpleCharacter.SSA_).registerCompoundAt(TamilCompoundCharacter.ISS_OO);
        registerSimple(TamilSimpleCharacter.SSSA_).registerCompoundAt(TamilCompoundCharacter.ISSS_OO);
        registerSimple(TamilSimpleCharacter.HA_).registerCompoundAt(TamilCompoundCharacter.IH_OO);
        registerSimple(TamilSimpleCharacter.JA_).registerCompoundAt(TamilCompoundCharacter.IJ_OO);


        registerSimple(TamilSimpleCharacter.KA).registerCompoundAt(TamilCompoundCharacter.IK_O);
        registerSimple(TamilSimpleCharacter.NGA).registerCompoundAt(TamilCompoundCharacter.ING_O);
        registerSimple(TamilSimpleCharacter.SA).registerCompoundAt(TamilCompoundCharacter.ICH_O);
        registerSimple(TamilSimpleCharacter.NYA).registerCompoundAt(TamilCompoundCharacter.INJ_O);
        registerSimple(TamilSimpleCharacter.DA).registerCompoundAt(TamilCompoundCharacter.IDD_O);
        registerSimple(TamilSimpleCharacter.NNNA).registerCompoundAt(TamilCompoundCharacter.INNN_O);
        registerSimple(TamilSimpleCharacter.THA).registerCompoundAt(TamilCompoundCharacter.ITH_O);
        registerSimple(TamilSimpleCharacter.NTHA).registerCompoundAt(TamilCompoundCharacter.INTH_O);
        registerSimple(TamilSimpleCharacter.PA).registerCompoundAt(TamilCompoundCharacter.IP_O);
        registerSimple(TamilSimpleCharacter.MA).registerCompoundAt(TamilCompoundCharacter.IM_O);
        registerSimple(TamilSimpleCharacter.YA).registerCompoundAt(TamilCompoundCharacter.IY_O);
        registerSimple(TamilSimpleCharacter.RA).registerCompoundAt(TamilCompoundCharacter.IR_O);
        registerSimple(TamilSimpleCharacter.LA).registerCompoundAt(TamilCompoundCharacter.IL_O);
        registerSimple(TamilSimpleCharacter.VA).registerCompoundAt(TamilCompoundCharacter.IV_O);
        registerSimple(TamilSimpleCharacter.LLLA).registerCompoundAt(TamilCompoundCharacter.ILLL_O);
        registerSimple(TamilSimpleCharacter.LLA).registerCompoundAt(TamilCompoundCharacter.ILL_O);
        registerSimple(TamilSimpleCharacter.RRA).registerCompoundAt(TamilCompoundCharacter.IRR_O);
        registerSimple(TamilSimpleCharacter.NA).registerCompoundAt(TamilCompoundCharacter.IN_O);

        registerSimple(TamilSimpleCharacter.SHA_).registerCompoundAt(TamilCompoundCharacter.ISH_O);
        registerSimple(TamilSimpleCharacter.SSA_).registerCompoundAt(TamilCompoundCharacter.ISS_O);
        registerSimple(TamilSimpleCharacter.SSSA_).registerCompoundAt(TamilCompoundCharacter.ISSS_O);
        registerSimple(TamilSimpleCharacter.HA_).registerCompoundAt(TamilCompoundCharacter.IH_O);
        registerSimple(TamilSimpleCharacter.JA_).registerCompoundAt(TamilCompoundCharacter.IJ_O);


        for (TamilSimpleCharacter t : TamilSimpleCharacter.simplecharacters) {
            t.init();
        }
        TamilSimpleCharacter.simplecharacters.clear();
        TamilSimpleCharacter.simplecharacters = null;

        for (TamilCompoundCharacter t : TamilCompoundCharacter.characters) {
            t.init();
        }
        TamilCompoundCharacter.characters.clear();
        TamilCompoundCharacter.characters = null;




        for (TamilCharacterLookUpContext ik : iks) {
            TamilCharacterLookUpContext ksha = ik.registerNext(TamilSimpleCharacter.SHA_.getValue(), TamilSuperCompoundCharacter.IKSHA);
            ksha.registerCompoundAt(TamilCompoundCharacter.ISH_, TamilSuperCompoundCharacter.IKSH);



            ksha.registerCompoundAt(TamilCompoundCharacter.ISH_aa, TamilSuperCompoundCharacter.IKSH_aa);
             ksha.registerCompoundAt(TamilCompoundCharacter.ISH_E,TamilSuperCompoundCharacter.IKSH_E);
             ksha.registerCompoundAt(TamilCompoundCharacter.ISS_EE,TamilSuperCompoundCharacter.IKSH_EE);
             ksha.registerCompoundAt(TamilCompoundCharacter.ISH_U,TamilSuperCompoundCharacter.IKSH_U);
             ksha.registerCompoundAt(TamilCompoundCharacter.ISH_UU,TamilSuperCompoundCharacter.IKSH_UU);
             ksha.registerCompoundAt(TamilCompoundCharacter.ISH_A,TamilSuperCompoundCharacter.IKSH_A);
             ksha.registerCompoundAt(TamilCompoundCharacter.ISH_AA,TamilSuperCompoundCharacter.IKSH_AA);
             ksha.registerCompoundAt(TamilCompoundCharacter.ISH_I,TamilSuperCompoundCharacter.IKSH_I);

             ksha.registerCompoundAt(TamilCompoundCharacter.ISH_O,TamilSuperCompoundCharacter.IKSH_O);
             ksha.registerCompoundAt(TamilCompoundCharacter.ISH_OU,TamilSuperCompoundCharacter.IKSH_OU);
             ksha.registerCompoundAt(TamilCompoundCharacter.ISH_OO,TamilSuperCompoundCharacter.IKSH_OO);



        }
//
//        for (TamilCharacterLookUpContext iss : isss) {
//            iss.registerSuperCompoundAt(TamilSuperCompoundCharacter.SHREE_);
//        }

        for (TamilSuperCompoundCharacter t : TamilSuperCompoundCharacter.supercompound) {
            t.init();
        }
        TamilSuperCompoundCharacter.supercompound.clear();
        TamilSuperCompoundCharacter.supercompound = null;


    }


    /**
     * Looks up a tamil character based on the given code point
     * See {@link #currentChar} to know the character interpreted.
     *
     * @param value the code point value
     * @return null if it is not start of a tamil character
     */
    public static TamilCharacterLookUpContext lookup(int value) {
        return map.get(value);
    }


    /**
     * Concatenates the next code point with the current context.
     * <p/>
     * See {@link #currentChar} to know the character interpreted.
     *
     * @param value the next code point
     * @return null, when the next code point cannot be concatenated with the current context,
     */
    public TamilCharacterLookUpContext next(int value) {
        if (continuations == null) {
            return null;
        }
        return continuations.get(value);
    }


}
