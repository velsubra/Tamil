package tamil.lang.sound;

import common.lang.impl.UnknownCharacter;
import my.interest.lang.tamil.TamilUtils;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilFactory;
import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilWord;

import java.io.InputStream;

/**
 * <p>
 * Represents a atomic unit of sound for a TamilWord that can contain one or more characters.
 * </p>
 *
 * @author velsubra
 */
public final class AtomicSound implements Comparable<AtomicSound> {
    private TamilWord word = null;

    public AtomicSound(TamilWord word) {
        this.word = word;


    }

    public TamilWord getWord() {
        return word;
    }


    public InputStream getDataInputStream() {
        return TamilFactory.class.getResourceAsStream("/sound/download/" + this.word.translitToEnglish() + ".wav");
    }


    public static final AtomicSound SPACE = new AtomicSound(new TamilWord(UnknownCharacter.SPACE));

    public static final AtomicSound AKTHU = new AtomicSound(new TamilWord(TamilSimpleCharacter.AKTHU));


    public static final AtomicSound a = new AtomicSound(new TamilWord(TamilSimpleCharacter.a));
    public static final AtomicSound aa = new AtomicSound(new TamilWord(TamilSimpleCharacter.aa));
    public static final AtomicSound E = new AtomicSound(new TamilWord(TamilSimpleCharacter.E));
    public static final AtomicSound EE = new AtomicSound(new TamilWord(TamilSimpleCharacter.EE));
    public static final AtomicSound U = new AtomicSound(new TamilWord(TamilSimpleCharacter.U));
    public static final AtomicSound UU = new AtomicSound(new TamilWord(TamilSimpleCharacter.UU));
    public static final AtomicSound A = new AtomicSound(new TamilWord(TamilSimpleCharacter.A));
    public static final AtomicSound AA = new AtomicSound(new TamilWord(TamilSimpleCharacter.AA));
    public static final AtomicSound I = new AtomicSound(new TamilWord(TamilSimpleCharacter.I));
    public static final AtomicSound O = new AtomicSound(new TamilWord(TamilSimpleCharacter.O));
    public static final AtomicSound OO = new AtomicSound(new TamilWord(TamilSimpleCharacter.OO));
    public static final AtomicSound OU = new AtomicSound(new TamilWord(TamilSimpleCharacter.OU));


    public static final AtomicSound IK = new AtomicSound(new TamilWord(TamilCompoundCharacter.IK));
    public static final AtomicSound ING = new AtomicSound(new TamilWord(TamilCompoundCharacter.ING));
    public static final AtomicSound ICH = new AtomicSound(new TamilWord(TamilCompoundCharacter.ICH));
    public static final AtomicSound INJ = new AtomicSound(new TamilWord(TamilCompoundCharacter.INJ));
    public static final AtomicSound IDD = new AtomicSound(new TamilWord(TamilCompoundCharacter.IDD));
    public static final AtomicSound INNN = new AtomicSound(new TamilWord(TamilCompoundCharacter.INNN));
    public static final AtomicSound ITH = new AtomicSound(new TamilWord(TamilCompoundCharacter.ITH));
    public static final AtomicSound INTH = new AtomicSound(new TamilWord(TamilCompoundCharacter.INTH));
    public static final AtomicSound IP = new AtomicSound(new TamilWord(TamilCompoundCharacter.IP));
    public static final AtomicSound IM = new AtomicSound(new TamilWord(TamilCompoundCharacter.IM));
    public static final AtomicSound IY = new AtomicSound(new TamilWord(TamilCompoundCharacter.IY));
    public static final AtomicSound IR = new AtomicSound(new TamilWord(TamilCompoundCharacter.IR));
    public static final AtomicSound IL = new AtomicSound(new TamilWord(TamilCompoundCharacter.IL));
    public static final AtomicSound IV = new AtomicSound(new TamilWord(TamilCompoundCharacter.IV));
    public static final AtomicSound ILLL = new AtomicSound(new TamilWord(TamilCompoundCharacter.ILLL));
    public static final AtomicSound ILL = new AtomicSound(new TamilWord(TamilCompoundCharacter.ILL));
    public static final AtomicSound IRR = new AtomicSound(new TamilWord(TamilCompoundCharacter.IRR));
    public static final AtomicSound IN = new AtomicSound(new TamilWord(TamilCompoundCharacter.IN));

    public static final AtomicSound IJ = new AtomicSound(new TamilWord(TamilCompoundCharacter.IJ_));
    public static final AtomicSound ISS = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISS_));
    public static final AtomicSound ISSS = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISSS_));
    public static final AtomicSound ISH = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISH_));
    public static final AtomicSound IH = new AtomicSound(new TamilWord(TamilCompoundCharacter.IH_));


    public static final AtomicSound KA = new AtomicSound(new TamilWord(TamilSimpleCharacter.KA));
    public static final AtomicSound NGA = new AtomicSound(new TamilWord(TamilSimpleCharacter.NGA));
    public static final AtomicSound SA = new AtomicSound(new TamilWord(TamilSimpleCharacter.SA));
    public static final AtomicSound NYA = new AtomicSound(new TamilWord(TamilSimpleCharacter.NYA));
    public static final AtomicSound DA = new AtomicSound(new TamilWord(TamilSimpleCharacter.DA));
    public static final AtomicSound NNA = new AtomicSound(new TamilWord(TamilSimpleCharacter.NNNA));
    public static final AtomicSound THA = new AtomicSound(new TamilWord(TamilSimpleCharacter.THA));
    public static final AtomicSound NTA = new AtomicSound(new TamilWord(TamilSimpleCharacter.NTHA));
    public static final AtomicSound PA = new AtomicSound(new TamilWord(TamilSimpleCharacter.PA));
    public static final AtomicSound MA = new AtomicSound(new TamilWord(TamilSimpleCharacter.MA));
    public static final AtomicSound YA = new AtomicSound(new TamilWord(TamilSimpleCharacter.YA));
    public static final AtomicSound RA = new AtomicSound(new TamilWord(TamilSimpleCharacter.RA));
    public static final AtomicSound LA = new AtomicSound(new TamilWord(TamilSimpleCharacter.LA));
    public static final AtomicSound VA = new AtomicSound(new TamilWord(TamilSimpleCharacter.VA));
    public static final AtomicSound LLLA = new AtomicSound(new TamilWord(TamilSimpleCharacter.LLLA));
    public static final AtomicSound LLA = new AtomicSound(new TamilWord(TamilSimpleCharacter.LLA));
    public static final AtomicSound RRA = new AtomicSound(new TamilWord(TamilSimpleCharacter.RRA));
    public static final AtomicSound NA = new AtomicSound(new TamilWord(TamilSimpleCharacter.NA));

    public static final AtomicSound JA_ = new AtomicSound(new TamilWord(TamilSimpleCharacter.JA_));
    public static final AtomicSound SSA_ = new AtomicSound(new TamilWord(TamilSimpleCharacter.SSA_));
    public static final AtomicSound SSSA_ = new AtomicSound(new TamilWord(TamilSimpleCharacter.SSSA_));
    public static final AtomicSound SHA_ = new AtomicSound(new TamilWord(TamilSimpleCharacter.SHA_));
    public static final AtomicSound HA_ = new AtomicSound(new TamilWord(TamilSimpleCharacter.HA_));


    public static final AtomicSound IK_aa = new AtomicSound(new TamilWord(TamilCompoundCharacter.IK_aa));
    public static final AtomicSound ING_aa = new AtomicSound(new TamilWord(TamilCompoundCharacter.ING_aa));
    public static final AtomicSound ICH_aa = new AtomicSound(new TamilWord(TamilCompoundCharacter.ICH_aa));
    public static final AtomicSound INJ_aa = new AtomicSound(new TamilWord(TamilCompoundCharacter.INJ_aa));
    public static final AtomicSound IDD_aa = new AtomicSound(new TamilWord(TamilCompoundCharacter.IDD_aa));
    public static final AtomicSound INNN_aa = new AtomicSound(new TamilWord(TamilCompoundCharacter.INNN_aa));
    public static final AtomicSound ITH_aa = new AtomicSound(new TamilWord(TamilCompoundCharacter.ITH_aa));
    public static final AtomicSound INTH_aa = new AtomicSound(new TamilWord(TamilCompoundCharacter.INTH_aa));
    public static final AtomicSound IP_aa = new AtomicSound(new TamilWord(TamilCompoundCharacter.IP_aa));
    public static final AtomicSound IM_aa = new AtomicSound(new TamilWord(TamilCompoundCharacter.IM_aa));
    public static final AtomicSound IY_aa = new AtomicSound(new TamilWord(TamilCompoundCharacter.IY_aa));
    public static final AtomicSound IR_aa = new AtomicSound(new TamilWord(TamilCompoundCharacter.IR_aa));
    public static final AtomicSound IL_aa = new AtomicSound(new TamilWord(TamilCompoundCharacter.IL_aa));
    public static final AtomicSound IV_aa = new AtomicSound(new TamilWord(TamilCompoundCharacter.IV_aa));
    public static final AtomicSound ILLL_aa = new AtomicSound(new TamilWord(TamilCompoundCharacter.ILLL_aa));
    public static final AtomicSound ILL_aa = new AtomicSound(new TamilWord(TamilCompoundCharacter.ILL_aa));
    public static final AtomicSound IRR_aa = new AtomicSound(new TamilWord(TamilCompoundCharacter.IRR_aa));
    public static final AtomicSound IN_aa = new AtomicSound(new TamilWord(TamilCompoundCharacter.IN_aa));

    public static final AtomicSound IJ_aa = new AtomicSound(new TamilWord(TamilCompoundCharacter.IJ_aa));
    public static final AtomicSound ISS_aa = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISS_aa));
    public static final AtomicSound ISSS_aa = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISSS_aa));
    public static final AtomicSound ISH_aa = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISH_aa));
    public static final AtomicSound IH_aa = new AtomicSound(new TamilWord(TamilCompoundCharacter.IH_aa));


    public static final AtomicSound IK_E = new AtomicSound(new TamilWord(TamilCompoundCharacter.IK_E));
    public static final AtomicSound ING_E = new AtomicSound(new TamilWord(TamilCompoundCharacter.ING_E));
    public static final AtomicSound ICH_E = new AtomicSound(new TamilWord(TamilCompoundCharacter.ICH_E));
    public static final AtomicSound INJ_E = new AtomicSound(new TamilWord(TamilCompoundCharacter.INJ_E));
    public static final AtomicSound IDD_E = new AtomicSound(new TamilWord(TamilCompoundCharacter.IDD_E));
    public static final AtomicSound INNN_E = new AtomicSound(new TamilWord(TamilCompoundCharacter.INNN_E));
    public static final AtomicSound ITH_E = new AtomicSound(new TamilWord(TamilCompoundCharacter.ITH_E));
    public static final AtomicSound INTH_E = new AtomicSound(new TamilWord(TamilCompoundCharacter.INTH_E));
    public static final AtomicSound IP_E = new AtomicSound(new TamilWord(TamilCompoundCharacter.IP_E));
    public static final AtomicSound IM_E = new AtomicSound(new TamilWord(TamilCompoundCharacter.IM_E));
    public static final AtomicSound IY_E = new AtomicSound(new TamilWord(TamilCompoundCharacter.IY_E));
    public static final AtomicSound IR_E = new AtomicSound(new TamilWord(TamilCompoundCharacter.IR_E));
    public static final AtomicSound IL_E = new AtomicSound(new TamilWord(TamilCompoundCharacter.IL_E));
    public static final AtomicSound IV_E = new AtomicSound(new TamilWord(TamilCompoundCharacter.IV_E));
    public static final AtomicSound ILLL_E = new AtomicSound(new TamilWord(TamilCompoundCharacter.ILLL_E));
    public static final AtomicSound ILL_E = new AtomicSound(new TamilWord(TamilCompoundCharacter.ILL_E));
    public static final AtomicSound IRR_E = new AtomicSound(new TamilWord(TamilCompoundCharacter.IRR_E));
    public static final AtomicSound IN_E = new AtomicSound(new TamilWord(TamilCompoundCharacter.IN_E));

    public static final AtomicSound IJ_E = new AtomicSound(new TamilWord(TamilCompoundCharacter.IJ_E));
    public static final AtomicSound ISS_E = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISS_E));
    public static final AtomicSound ISSS_E = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISSS_E));
    public static final AtomicSound ISH_E = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISH_E));
    public static final AtomicSound IH_E = new AtomicSound(new TamilWord(TamilCompoundCharacter.IH_E));


    public static final AtomicSound IK_EE = new AtomicSound(new TamilWord(TamilCompoundCharacter.IK_EE));
    public static final AtomicSound ING_EE = new AtomicSound(new TamilWord(TamilCompoundCharacter.ING_EE));
    public static final AtomicSound ICH_EE = new AtomicSound(new TamilWord(TamilCompoundCharacter.ICH_EE));
    public static final AtomicSound INJ_EE = new AtomicSound(new TamilWord(TamilCompoundCharacter.INJ_EE));
    public static final AtomicSound IDD_EE = new AtomicSound(new TamilWord(TamilCompoundCharacter.IDD_EE));
    public static final AtomicSound INNN_EE = new AtomicSound(new TamilWord(TamilCompoundCharacter.INNN_EE));
    public static final AtomicSound ITH_EE = new AtomicSound(new TamilWord(TamilCompoundCharacter.ITH_EE));
    public static final AtomicSound INTH_EE = new AtomicSound(new TamilWord(TamilCompoundCharacter.INTH_EE));
    public static final AtomicSound IP_EE = new AtomicSound(new TamilWord(TamilCompoundCharacter.IP_EE));
    public static final AtomicSound IM_EE = new AtomicSound(new TamilWord(TamilCompoundCharacter.IM_EE));
    public static final AtomicSound IY_EE = new AtomicSound(new TamilWord(TamilCompoundCharacter.IY_EE));
    public static final AtomicSound IR_EE = new AtomicSound(new TamilWord(TamilCompoundCharacter.IR_EE));
    public static final AtomicSound IL_EE = new AtomicSound(new TamilWord(TamilCompoundCharacter.IL_EE));
    public static final AtomicSound IV_EE = new AtomicSound(new TamilWord(TamilCompoundCharacter.IV_EE));
    public static final AtomicSound ILLL_EE = new AtomicSound(new TamilWord(TamilCompoundCharacter.ILLL_EE));
    public static final AtomicSound ILL_EE = new AtomicSound(new TamilWord(TamilCompoundCharacter.ILL_EE));
    public static final AtomicSound IRR_EE = new AtomicSound(new TamilWord(TamilCompoundCharacter.IRR_EE));
    public static final AtomicSound IN_EE = new AtomicSound(new TamilWord(TamilCompoundCharacter.IN_EE));

    public static final AtomicSound IJ_EE = new AtomicSound(new TamilWord(TamilCompoundCharacter.IJ_EE));
    public static final AtomicSound ISS_EE = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISS_EE));
    public static final AtomicSound ISSS_EE = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISSS_EE));
    public static final AtomicSound ISH_EE = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISH_EE));
    public static final AtomicSound IH_EE = new AtomicSound(new TamilWord(TamilCompoundCharacter.IH_EE));


    public static final AtomicSound IK_U = new AtomicSound(new TamilWord(TamilCompoundCharacter.IK_U));
    public static final AtomicSound ING_U = new AtomicSound(new TamilWord(TamilCompoundCharacter.ING_U));
    public static final AtomicSound ICH_U = new AtomicSound(new TamilWord(TamilCompoundCharacter.ICH_U));
    public static final AtomicSound INJ_U = new AtomicSound(new TamilWord(TamilCompoundCharacter.INJ_U));
    public static final AtomicSound IDD_U = new AtomicSound(new TamilWord(TamilCompoundCharacter.IDD_U));
    public static final AtomicSound INNN_U = new AtomicSound(new TamilWord(TamilCompoundCharacter.INNN_U));
    public static final AtomicSound ITH_U = new AtomicSound(new TamilWord(TamilCompoundCharacter.ITH_U));
    public static final AtomicSound INTH_U = new AtomicSound(new TamilWord(TamilCompoundCharacter.INTH_U));
    public static final AtomicSound IP_U = new AtomicSound(new TamilWord(TamilCompoundCharacter.IP_U));
    public static final AtomicSound IM_U = new AtomicSound(new TamilWord(TamilCompoundCharacter.IM_U));
    public static final AtomicSound IY_U = new AtomicSound(new TamilWord(TamilCompoundCharacter.IY_U));
    public static final AtomicSound IR_U = new AtomicSound(new TamilWord(TamilCompoundCharacter.IR_U));
    public static final AtomicSound IL_U = new AtomicSound(new TamilWord(TamilCompoundCharacter.IL_U));
    public static final AtomicSound IV_U = new AtomicSound(new TamilWord(TamilCompoundCharacter.IV_U));
    public static final AtomicSound ILLL_U = new AtomicSound(new TamilWord(TamilCompoundCharacter.ILLL_U));
    public static final AtomicSound ILL_U = new AtomicSound(new TamilWord(TamilCompoundCharacter.ILL_U));
    public static final AtomicSound IRR_U = new AtomicSound(new TamilWord(TamilCompoundCharacter.IRR_U));
    public static final AtomicSound IN_U = new AtomicSound(new TamilWord(TamilCompoundCharacter.IN_U));

    public static final AtomicSound IJ_U = new AtomicSound(new TamilWord(TamilCompoundCharacter.IJ_U));
    public static final AtomicSound ISS_U = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISS_U));
    public static final AtomicSound ISSS_U = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISSS_U));
    public static final AtomicSound ISH_U = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISH_U));
    public static final AtomicSound IH_U = new AtomicSound(new TamilWord(TamilCompoundCharacter.IH_U));


    public static final AtomicSound IK_UU = new AtomicSound(new TamilWord(TamilCompoundCharacter.IK_UU));
    public static final AtomicSound ING_UU = new AtomicSound(new TamilWord(TamilCompoundCharacter.ING_UU));
    public static final AtomicSound ICH_UU = new AtomicSound(new TamilWord(TamilCompoundCharacter.ICH_UU));
    public static final AtomicSound INJ_UU = new AtomicSound(new TamilWord(TamilCompoundCharacter.INJ_UU));
    public static final AtomicSound IDD_UU = new AtomicSound(new TamilWord(TamilCompoundCharacter.IDD_UU));
    public static final AtomicSound INNN_UU = new AtomicSound(new TamilWord(TamilCompoundCharacter.INNN_UU));
    public static final AtomicSound ITH_UU = new AtomicSound(new TamilWord(TamilCompoundCharacter.ITH_UU));
    public static final AtomicSound INTH_UU = new AtomicSound(new TamilWord(TamilCompoundCharacter.INTH_UU));
    public static final AtomicSound IP_UU = new AtomicSound(new TamilWord(TamilCompoundCharacter.IP_UU));
    public static final AtomicSound IM_UU = new AtomicSound(new TamilWord(TamilCompoundCharacter.IM_UU));
    public static final AtomicSound IY_UU = new AtomicSound(new TamilWord(TamilCompoundCharacter.IY_UU));
    public static final AtomicSound IR_UU = new AtomicSound(new TamilWord(TamilCompoundCharacter.IR_UU));
    public static final AtomicSound IL_UU = new AtomicSound(new TamilWord(TamilCompoundCharacter.IL_UU));
    public static final AtomicSound IV_UU = new AtomicSound(new TamilWord(TamilCompoundCharacter.IV_UU));
    public static final AtomicSound ILLL_UU = new AtomicSound(new TamilWord(TamilCompoundCharacter.ILLL_UU));
    public static final AtomicSound ILL_UU = new AtomicSound(new TamilWord(TamilCompoundCharacter.ILL_UU));
    public static final AtomicSound IRR_UU = new AtomicSound(new TamilWord(TamilCompoundCharacter.IRR_UU));
    public static final AtomicSound IN_UU = new AtomicSound(new TamilWord(TamilCompoundCharacter.IN_UU));

    public static final AtomicSound IJ_UU = new AtomicSound(new TamilWord(TamilCompoundCharacter.IJ_UU));
    public static final AtomicSound ISS_UU = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISS_UU));
    public static final AtomicSound ISSS_UU = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISSS_UU));
    public static final AtomicSound ISH_UU = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISH_UU));
    public static final AtomicSound IH_UU = new AtomicSound(new TamilWord(TamilCompoundCharacter.IH_UU));


    public static final AtomicSound IK_A = new AtomicSound(new TamilWord(TamilCompoundCharacter.IK_A));
    public static final AtomicSound ING_A = new AtomicSound(new TamilWord(TamilCompoundCharacter.ING_A));
    public static final AtomicSound ICH_A = new AtomicSound(new TamilWord(TamilCompoundCharacter.ICH_A));
    public static final AtomicSound INJ_A = new AtomicSound(new TamilWord(TamilCompoundCharacter.INJ_A));
    public static final AtomicSound IDD_A = new AtomicSound(new TamilWord(TamilCompoundCharacter.IDD_A));
    public static final AtomicSound INNN_A = new AtomicSound(new TamilWord(TamilCompoundCharacter.INNN_A));
    public static final AtomicSound ITH_A = new AtomicSound(new TamilWord(TamilCompoundCharacter.ITH_A));
    public static final AtomicSound INTH_A = new AtomicSound(new TamilWord(TamilCompoundCharacter.INTH_A));
    public static final AtomicSound IP_A = new AtomicSound(new TamilWord(TamilCompoundCharacter.IP_A));
    public static final AtomicSound IM_A = new AtomicSound(new TamilWord(TamilCompoundCharacter.IM_A));
    public static final AtomicSound IY_A = new AtomicSound(new TamilWord(TamilCompoundCharacter.IY_A));
    public static final AtomicSound IR_A = new AtomicSound(new TamilWord(TamilCompoundCharacter.IR_A));
    public static final AtomicSound IL_A = new AtomicSound(new TamilWord(TamilCompoundCharacter.IL_A));
    public static final AtomicSound IV_A = new AtomicSound(new TamilWord(TamilCompoundCharacter.IV_A));
    public static final AtomicSound ILLL_A = new AtomicSound(new TamilWord(TamilCompoundCharacter.ILLL_A));
    public static final AtomicSound ILL_A = new AtomicSound(new TamilWord(TamilCompoundCharacter.ILL_A));
    public static final AtomicSound IRR_A = new AtomicSound(new TamilWord(TamilCompoundCharacter.IRR_A));
    public static final AtomicSound IN_A = new AtomicSound(new TamilWord(TamilCompoundCharacter.IN_A));

    public static final AtomicSound IJ_A = new AtomicSound(new TamilWord(TamilCompoundCharacter.IJ_A));
    public static final AtomicSound ISS_A = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISS_A));
    public static final AtomicSound ISSS_A = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISSS_A));
    public static final AtomicSound ISH_A = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISH_A));
    public static final AtomicSound IH_A = new AtomicSound(new TamilWord(TamilCompoundCharacter.IH_A));


    public static final AtomicSound IK_AA = new AtomicSound(new TamilWord(TamilCompoundCharacter.IK_AA));
    public static final AtomicSound ING_AA = new AtomicSound(new TamilWord(TamilCompoundCharacter.ING_AA));
    public static final AtomicSound ICH_AA = new AtomicSound(new TamilWord(TamilCompoundCharacter.ICH_AA));
    public static final AtomicSound INJ_AA = new AtomicSound(new TamilWord(TamilCompoundCharacter.INJ_AA));
    public static final AtomicSound IDD_AA = new AtomicSound(new TamilWord(TamilCompoundCharacter.IDD_AA));
    public static final AtomicSound INNN_AA = new AtomicSound(new TamilWord(TamilCompoundCharacter.INNN_AA));
    public static final AtomicSound ITH_AA = new AtomicSound(new TamilWord(TamilCompoundCharacter.ITH_AA));
    public static final AtomicSound INTH_AA = new AtomicSound(new TamilWord(TamilCompoundCharacter.INTH_AA));
    public static final AtomicSound IP_AA = new AtomicSound(new TamilWord(TamilCompoundCharacter.IP_AA));
    public static final AtomicSound IM_AA = new AtomicSound(new TamilWord(TamilCompoundCharacter.IM_AA));
    public static final AtomicSound IY_AA = new AtomicSound(new TamilWord(TamilCompoundCharacter.IY_AA));
    public static final AtomicSound IR_AA = new AtomicSound(new TamilWord(TamilCompoundCharacter.IR_AA));
    public static final AtomicSound IL_AA = new AtomicSound(new TamilWord(TamilCompoundCharacter.IL_AA));
    public static final AtomicSound IV_AA = new AtomicSound(new TamilWord(TamilCompoundCharacter.IV_AA));
    public static final AtomicSound ILLL_AA = new AtomicSound(new TamilWord(TamilCompoundCharacter.ILLL_AA));
    public static final AtomicSound ILL_AA = new AtomicSound(new TamilWord(TamilCompoundCharacter.ILL_AA));
    public static final AtomicSound IRR_AA = new AtomicSound(new TamilWord(TamilCompoundCharacter.IRR_AA));
    public static final AtomicSound IN_AA = new AtomicSound(new TamilWord(TamilCompoundCharacter.IN_AA));

    public static final AtomicSound IJ_AA = new AtomicSound(new TamilWord(TamilCompoundCharacter.IJ_AA));
    public static final AtomicSound ISS_AA = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISS_AA));
    public static final AtomicSound ISSS_AA = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISSS_AA));
    public static final AtomicSound ISH_AA = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISH_AA));
    public static final AtomicSound IH_AA = new AtomicSound(new TamilWord(TamilCompoundCharacter.IH_AA));


    public static final AtomicSound IK_I = new AtomicSound(new TamilWord(TamilCompoundCharacter.IK_I));
    public static final AtomicSound ING_I = new AtomicSound(new TamilWord(TamilCompoundCharacter.ING_I));
    public static final AtomicSound ICH_I = new AtomicSound(new TamilWord(TamilCompoundCharacter.ICH_I));
    public static final AtomicSound INJ_I = new AtomicSound(new TamilWord(TamilCompoundCharacter.INJ_I));
    public static final AtomicSound IDD_I = new AtomicSound(new TamilWord(TamilCompoundCharacter.IDD_I));
    public static final AtomicSound INNN_I = new AtomicSound(new TamilWord(TamilCompoundCharacter.INNN_I));
    public static final AtomicSound ITH_I = new AtomicSound(new TamilWord(TamilCompoundCharacter.ITH_I));
    public static final AtomicSound INTH_I = new AtomicSound(new TamilWord(TamilCompoundCharacter.INTH_I));
    public static final AtomicSound IP_I = new AtomicSound(new TamilWord(TamilCompoundCharacter.IP_I));
    public static final AtomicSound IM_I = new AtomicSound(new TamilWord(TamilCompoundCharacter.IM_I));
    public static final AtomicSound IY_I = new AtomicSound(new TamilWord(TamilCompoundCharacter.IY_I));
    public static final AtomicSound IR_I = new AtomicSound(new TamilWord(TamilCompoundCharacter.IR_I));
    public static final AtomicSound IL_I = new AtomicSound(new TamilWord(TamilCompoundCharacter.IL_I));
    public static final AtomicSound IV_I = new AtomicSound(new TamilWord(TamilCompoundCharacter.IV_I));
    public static final AtomicSound ILLL_I = new AtomicSound(new TamilWord(TamilCompoundCharacter.ILLL_I));
    public static final AtomicSound ILL_I = new AtomicSound(new TamilWord(TamilCompoundCharacter.ILL_I));
    public static final AtomicSound IRR_I = new AtomicSound(new TamilWord(TamilCompoundCharacter.IRR_I));
    public static final AtomicSound IN_I = new AtomicSound(new TamilWord(TamilCompoundCharacter.IN_I));

    public static final AtomicSound IJ_I = new AtomicSound(new TamilWord(TamilCompoundCharacter.IJ_I));
    public static final AtomicSound ISS_I = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISS_I));
    public static final AtomicSound ISSS_I = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISSS_I));
    public static final AtomicSound ISH_I = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISH_I));
    public static final AtomicSound IH_I = new AtomicSound(new TamilWord(TamilCompoundCharacter.IH_I));


    public static final AtomicSound IK_O = new AtomicSound(new TamilWord(TamilCompoundCharacter.IK_O));
    public static final AtomicSound ING_O = new AtomicSound(new TamilWord(TamilCompoundCharacter.ING_O));
    public static final AtomicSound ICH_O = new AtomicSound(new TamilWord(TamilCompoundCharacter.ICH_O));
    public static final AtomicSound INJ_O = new AtomicSound(new TamilWord(TamilCompoundCharacter.INJ_O));
    public static final AtomicSound IDD_O = new AtomicSound(new TamilWord(TamilCompoundCharacter.IDD_O));
    public static final AtomicSound INNN_O = new AtomicSound(new TamilWord(TamilCompoundCharacter.INNN_O));
    public static final AtomicSound ITH_O = new AtomicSound(new TamilWord(TamilCompoundCharacter.ITH_O));
    public static final AtomicSound INTH_O = new AtomicSound(new TamilWord(TamilCompoundCharacter.INTH_O));
    public static final AtomicSound IP_O = new AtomicSound(new TamilWord(TamilCompoundCharacter.IP_O));
    public static final AtomicSound IM_O = new AtomicSound(new TamilWord(TamilCompoundCharacter.IM_O));
    public static final AtomicSound IY_O = new AtomicSound(new TamilWord(TamilCompoundCharacter.IY_O));
    public static final AtomicSound IR_O = new AtomicSound(new TamilWord(TamilCompoundCharacter.IR_O));
    public static final AtomicSound IL_O = new AtomicSound(new TamilWord(TamilCompoundCharacter.IL_O));
    public static final AtomicSound IV_O = new AtomicSound(new TamilWord(TamilCompoundCharacter.IV_O));
    public static final AtomicSound ILLL_O = new AtomicSound(new TamilWord(TamilCompoundCharacter.ILLL_O));
    public static final AtomicSound ILL_O = new AtomicSound(new TamilWord(TamilCompoundCharacter.ILL_O));
    public static final AtomicSound IRR_O = new AtomicSound(new TamilWord(TamilCompoundCharacter.IRR_O));
    public static final AtomicSound IN_O = new AtomicSound(new TamilWord(TamilCompoundCharacter.IN_O));

    public static final AtomicSound IJ_O = new AtomicSound(new TamilWord(TamilCompoundCharacter.IJ_O));
    public static final AtomicSound ISS_O = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISS_O));
    public static final AtomicSound ISSS_O = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISSS_O));
    public static final AtomicSound ISH_O = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISH_O));
    public static final AtomicSound IH_O = new AtomicSound(new TamilWord(TamilCompoundCharacter.IH_O));


    public static final AtomicSound IK_OO = new AtomicSound(new TamilWord(TamilCompoundCharacter.IK_OO));
    public static final AtomicSound ING_OO = new AtomicSound(new TamilWord(TamilCompoundCharacter.ING_OO));
    public static final AtomicSound ICH_OO = new AtomicSound(new TamilWord(TamilCompoundCharacter.ICH_OO));
    public static final AtomicSound INJ_OO = new AtomicSound(new TamilWord(TamilCompoundCharacter.INJ_OO));
    public static final AtomicSound IDD_OO = new AtomicSound(new TamilWord(TamilCompoundCharacter.IDD_OO));
    public static final AtomicSound INNN_OO = new AtomicSound(new TamilWord(TamilCompoundCharacter.INNN_OO));
    public static final AtomicSound ITH_OO = new AtomicSound(new TamilWord(TamilCompoundCharacter.ITH_OO));
    public static final AtomicSound INTH_OO = new AtomicSound(new TamilWord(TamilCompoundCharacter.INTH_OO));
    public static final AtomicSound IP_OO = new AtomicSound(new TamilWord(TamilCompoundCharacter.IP_OO));
    public static final AtomicSound IM_OO = new AtomicSound(new TamilWord(TamilCompoundCharacter.IM_OO));
    public static final AtomicSound IY_OO = new AtomicSound(new TamilWord(TamilCompoundCharacter.IY_OO));
    public static final AtomicSound IR_OO = new AtomicSound(new TamilWord(TamilCompoundCharacter.IR_OO));
    public static final AtomicSound IL_OO = new AtomicSound(new TamilWord(TamilCompoundCharacter.IL_OO));
    public static final AtomicSound IV_OO = new AtomicSound(new TamilWord(TamilCompoundCharacter.IV_OO));
    public static final AtomicSound ILLL_OO = new AtomicSound(new TamilWord(TamilCompoundCharacter.ILLL_OO));
    public static final AtomicSound ILL_OO = new AtomicSound(new TamilWord(TamilCompoundCharacter.ILL_OO));
    public static final AtomicSound IRR_OO = new AtomicSound(new TamilWord(TamilCompoundCharacter.IRR_OO));
    public static final AtomicSound IN_OO = new AtomicSound(new TamilWord(TamilCompoundCharacter.IN_OO));

    public static final AtomicSound IJ_OO = new AtomicSound(new TamilWord(TamilCompoundCharacter.IJ_OO));
    public static final AtomicSound ISS_OO = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISS_OO));
    public static final AtomicSound ISSS_OO = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISSS_OO));
    public static final AtomicSound ISH_OO = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISH_OO));
    public static final AtomicSound IH_OO = new AtomicSound(new TamilWord(TamilCompoundCharacter.IH_OO));


    public static final AtomicSound IK_OU = new AtomicSound(new TamilWord(TamilCompoundCharacter.IK_OU));
    public static final AtomicSound ING_OU = new AtomicSound(new TamilWord(TamilCompoundCharacter.ING_OU));
    public static final AtomicSound ICH_OU = new AtomicSound(new TamilWord(TamilCompoundCharacter.ICH_OU));
    public static final AtomicSound INJ_OU = new AtomicSound(new TamilWord(TamilCompoundCharacter.INJ_OU));
    public static final AtomicSound IDD_OU = new AtomicSound(new TamilWord(TamilCompoundCharacter.IDD_OU));
    public static final AtomicSound INNN_OU = new AtomicSound(new TamilWord(TamilCompoundCharacter.INNN_OU));
    public static final AtomicSound ITH_OU = new AtomicSound(new TamilWord(TamilCompoundCharacter.ITH_OU));
    public static final AtomicSound INTH_OU = new AtomicSound(new TamilWord(TamilCompoundCharacter.INTH_OU));
    public static final AtomicSound IP_OU = new AtomicSound(new TamilWord(TamilCompoundCharacter.IP_OU));
    public static final AtomicSound IM_OU = new AtomicSound(new TamilWord(TamilCompoundCharacter.IM_OU));
    public static final AtomicSound IY_OU = new AtomicSound(new TamilWord(TamilCompoundCharacter.IY_OU));
    public static final AtomicSound IR_OU = new AtomicSound(new TamilWord(TamilCompoundCharacter.IR_OU));
    public static final AtomicSound IL_OU = new AtomicSound(new TamilWord(TamilCompoundCharacter.IL_OU));
    public static final AtomicSound IV_OU = new AtomicSound(new TamilWord(TamilCompoundCharacter.IV_OU));
    public static final AtomicSound ILLL_OU = new AtomicSound(new TamilWord(TamilCompoundCharacter.ILLL_OU));
    public static final AtomicSound ILL_OU = new AtomicSound(new TamilWord(TamilCompoundCharacter.ILL_OU));
    public static final AtomicSound IRR_OU = new AtomicSound(new TamilWord(TamilCompoundCharacter.IRR_OU));
    public static final AtomicSound IN_OU = new AtomicSound(new TamilWord(TamilCompoundCharacter.IN_OU));

    public static final AtomicSound IJ_OU = new AtomicSound(new TamilWord(TamilCompoundCharacter.IJ_OU));
    public static final AtomicSound ISS_OU = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISS_OU));
    public static final AtomicSound ISSS_OU = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISSS_OU));
    public static final AtomicSound ISH_OU = new AtomicSound(new TamilWord(TamilCompoundCharacter.ISH_OU));
    public static final AtomicSound IH_OU = new AtomicSound(new TamilWord(TamilCompoundCharacter.IH_OU));


    public int compareTo(AtomicSound o) {
        return this.word.compareTo(o.word);
    }
}
