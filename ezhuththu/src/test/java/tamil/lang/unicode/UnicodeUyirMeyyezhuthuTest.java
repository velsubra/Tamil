package tamil.lang.unicode;

import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static tamil.lang.unicode.UnicodeUyirMeyyezhuthu.*;

public class UnicodeUyirMeyyezhuthuTest {

    @Test
    public void shouldVerifyConsonantDigest() {
        List<UnicodeUyirMeyyezhuthu> kakaraVarisai = asList(KA, IK_aa, IK_E, IK_EE, IK_U, IK_UU, IK_A, IK_AA, IK_I, IK_O, IK_OO, IK_OU);
        verifyConsonantDigest(kakaraVarisai, "_01_");

        List<UnicodeUyirMeyyezhuthu> ngakaraVarisai = asList(NGA, ING_aa, ING_E, ING_EE, ING_U, ING_UU, ING_A, ING_AA, ING_I, ING_O, ING_OO, ING_OU);
        verifyConsonantDigest(ngakaraVarisai, "_02_");

        List<UnicodeUyirMeyyezhuthu> chakaraVarisai = asList(SA, ICH_aa, ICH_E, ICH_EE, ICH_U, ICH_UU, ICH_A, ICH_AA, ICH_I, ICH_O, ICH_OO, ICH_OU);
        verifyConsonantDigest(chakaraVarisai, "_03_");

        List<UnicodeUyirMeyyezhuthu> njakaraVarisai = asList(NYA, INJ_aa, INJ_E, INJ_EE, INJ_U, INJ_UU, INJ_A, INJ_AA, INJ_I, INJ_O, INJ_OO, INJ_OU);
        verifyConsonantDigest(njakaraVarisai, "_04_");

        List<UnicodeUyirMeyyezhuthu> dakaraVarisai = asList(DA, IDD_aa, IDD_E, IDD_EE, IDD_U, IDD_UU, IDD_A, IDD_AA, IDD_I, IDD_O, IDD_OO, IDD_OU);
        verifyConsonantDigest(dakaraVarisai, "_05_");

        List<UnicodeUyirMeyyezhuthu> nnnakaraVarisai = asList(NNNA, INNN_aa, INNN_E, INNN_EE, INNN_U, INNN_UU, INNN_A, INNN_AA, INNN_I, INNN_O, INNN_OO, INNN_OU);
        verifyConsonantDigest(nnnakaraVarisai, "_06_");

        List<UnicodeUyirMeyyezhuthu> thakaraVarisai = asList(THA, ITH_aa, ITH_E, ITH_EE, ITH_U, ITH_UU, ITH_A, ITH_AA, ITH_I, ITH_O, ITH_OO, ITH_OU);
        verifyConsonantDigest(thakaraVarisai, "_07_");

        List<UnicodeUyirMeyyezhuthu> nthakaraVarisai = asList(NTHA, INTH_aa, INTH_E, INTH_EE, INTH_U, INTH_UU, INTH_A, INTH_AA, INTH_I, INTH_O, INTH_OO, INTH_OU);
        verifyConsonantDigest(nthakaraVarisai, "_08_");

        List<UnicodeUyirMeyyezhuthu> pakaraVarisai = asList(PA, IP_aa, IP_E, IP_EE, IP_U, IP_UU, IP_A, IP_AA, IP_I, IP_O, IP_OO, IP_OU);
        verifyConsonantDigest(pakaraVarisai, "_09_");

        List<UnicodeUyirMeyyezhuthu> makaraVarisai = asList(MA, IM_aa, IM_E, IM_EE, IM_U, IM_UU, IM_A, IM_AA, IM_I, IM_O, IM_OO, IM_OU);
        verifyConsonantDigest(makaraVarisai, "_10_");

        List<UnicodeUyirMeyyezhuthu> yakaraVarisai = asList(YA, IY_aa, IY_E, IY_EE, IY_U, IY_UU, IY_A, IY_AA, IY_I, IY_O, IY_OO, IY_OU);
        verifyConsonantDigest(yakaraVarisai, "_11_");

        List<UnicodeUyirMeyyezhuthu> rakaraVarisai = asList(RA, IR_aa, IR_E, IR_EE, IR_U, IR_UU, IR_A, IR_AA, IR_I, IR_O, IR_OO, IR_OU);
        verifyConsonantDigest(rakaraVarisai, "_12_");

        List<UnicodeUyirMeyyezhuthu> lakaraVarisai = asList(LA, IL_aa, IL_E, IL_EE, IL_U, IL_UU, IL_A, IL_AA, IL_I, IL_O, IL_OO, IL_OU);
        verifyConsonantDigest(lakaraVarisai, "_13_");

        List<UnicodeUyirMeyyezhuthu> vakaraVarisai = asList(VA, IV_aa, IV_E, IV_EE, IV_U, IV_UU, IV_A, IV_AA, IV_I, IV_O, IV_OO, IV_OU);
        verifyConsonantDigest(vakaraVarisai, "_14_");

        List<UnicodeUyirMeyyezhuthu> lllakaraVarisai = asList(LLLA, ILLL_aa, ILLL_E, ILLL_EE, ILLL_U, ILLL_UU, ILLL_A, ILLL_AA, ILLL_I, ILLL_O, ILLL_OO, ILLL_OU);
        verifyConsonantDigest(lllakaraVarisai, "_15_");

        List<UnicodeUyirMeyyezhuthu> llakaraVarisai = asList(LLA, ILL_aa, ILL_E, ILL_EE, ILL_U, ILL_UU, ILL_A, ILL_AA, ILL_I, ILL_O, ILL_OO, ILL_OU);
        verifyConsonantDigest(llakaraVarisai, "_16_");

        List<UnicodeUyirMeyyezhuthu> rrakaraVarisai = asList(RRA, IRR_aa, IRR_E, IRR_EE, IRR_U, IRR_UU, IRR_A, IRR_AA, IRR_I, IRR_O, IRR_OO, IRR_OU);
        verifyConsonantDigest(rrakaraVarisai, "_17_");

        List<UnicodeUyirMeyyezhuthu> nakaraVarisai = asList(NA, IN_aa, IN_E, IN_EE, IN_U, IN_UU, IN_A, IN_AA, IN_I, IN_O, IN_OO, IN_OU);
        verifyConsonantDigest(nakaraVarisai, "_18_");

    }

    private void verifyConsonantDigest(List<UnicodeUyirMeyyezhuthu> kakaraVarisai, String expectedConsonantDigest) {
        for (UnicodeUyirMeyyezhuthu e: kakaraVarisai){
            assertThat(e.getConsonantDigest(), is(expectedConsonantDigest));
        }
    }

    @Test
    public void shouldverifyPropertiesOfUyirMeyyezhuthu() {
        List<UnicodeUyirMeyyezhuthu> agaraVarisai = asList(KA, NGA, SA, NYA, DA, NNNA, THA, NTHA, PA, MA, YA, RA, LA, VA, LLLA, LLA, RRA, NA);
        List<UnicodeUyirMeyyezhuthu> vallinam = asList(KA, SA, DA, THA, PA, RRA);
        List<UnicodeUyirMeyyezhuthu> mellinam = asList(NGA, NYA, NNNA, NTHA, MA, NA);
        List<UnicodeUyirMeyyezhuthu> idaiyinam = asList(YA, RA, LA, VA, LLLA, LLA);

        verifyPropertiesOf(agaraVarisai, vallinam, mellinam, idaiyinam, 1, true, "_01_");

        List<UnicodeUyirMeyyezhuthu> aagaaraVarisai = asList(IK_aa, ING_aa, ICH_aa, INJ_aa, IDD_aa, INNN_aa, ITH_aa, INTH_aa, IP_aa, IM_aa, IY_aa, IR_aa, IL_aa, IV_aa, ILLL_aa, ILL_aa, IRR_aa, IN_aa);
        vallinam = asList(IK_aa, ICH_aa, IDD_aa, ITH_aa, IP_aa, IRR_aa);
        mellinam = asList(ING_aa, INJ_aa, INNN_aa, INTH_aa, IM_aa, IN_aa);
        idaiyinam = asList(IY_aa, IR_aa, IL_aa, IV_aa, ILLL_aa, ILL_aa);

        verifyPropertiesOf(aagaaraVarisai, vallinam, mellinam, idaiyinam, 2, false, "_02_");

        List<UnicodeUyirMeyyezhuthu> egaraVarisai = asList(IK_E, ING_E, ICH_E, INJ_E, IDD_E, INNN_E, ITH_E, INTH_E, IP_E, IM_E, IY_E, IR_E, IL_E, IV_E, ILLL_E, ILL_E, IRR_E, IN_E);
        vallinam = asList(IK_E, ICH_E, IDD_E, ITH_E, IP_E, IRR_E);
        mellinam = asList(ING_E, INJ_E, INNN_E, INTH_E, IM_E, IN_E);
        idaiyinam = asList(IY_E, IR_E, IL_E, IV_E, ILLL_E, ILL_E);

        verifyPropertiesOf(egaraVarisai, vallinam, mellinam, idaiyinam, 3, true, "_03_");

        List<UnicodeUyirMeyyezhuthu> eegaaraVarisai = asList(IK_EE, ING_EE, ICH_EE, INJ_EE, IDD_EE, INNN_EE, ITH_EE, INTH_EE, IP_EE, IM_EE, IY_EE, IR_EE, IL_EE, IV_EE, ILLL_EE, ILL_EE, IRR_EE, IN_EE);
        vallinam = asList(IK_EE, ICH_EE, IDD_EE, ITH_EE, IP_EE, IRR_EE);
        mellinam = asList(ING_EE, INJ_EE, INNN_EE, INTH_EE, IM_EE, IN_EE);
        idaiyinam = asList(IY_EE, IR_EE, IL_EE, IV_EE, ILLL_EE, ILL_EE);

        verifyPropertiesOf(eegaaraVarisai, vallinam, mellinam, idaiyinam, 4, false, "_04_");

        List<UnicodeUyirMeyyezhuthu> ugaraVarisai = asList(IK_U, ING_U, ICH_U, INJ_U, IDD_U, INNN_U, ITH_U, INTH_U, IP_U, IM_U, IY_U, IR_U, IL_U, IV_U, ILLL_U, ILL_U, IRR_U, IN_U);
        vallinam = asList(IK_U, ICH_U, IDD_U, ITH_U, IP_U, IRR_U);
        mellinam = asList(ING_U, INJ_U, INNN_U, INTH_U, IM_U, IN_U);
        idaiyinam = asList(IY_U, IR_U, IL_U, IV_U, ILLL_U, ILL_U);

        verifyPropertiesOf(ugaraVarisai, vallinam, mellinam, idaiyinam, 5, true, "_05_");

        List<UnicodeUyirMeyyezhuthu> uugaaraVarisai = asList(IK_UU, ING_UU, ICH_UU, INJ_UU, IDD_UU, INNN_UU, ITH_UU, INTH_UU, IP_UU, IM_UU, IY_UU, IR_UU, IL_UU, IV_UU, ILLL_UU, ILL_UU, IRR_UU, IN_UU);
        vallinam = asList(IK_UU, ICH_UU, IDD_UU, ITH_UU, IP_UU, IRR_UU);
        mellinam = asList(ING_UU, INJ_UU, INNN_UU, INTH_UU, IM_UU, IN_UU);
        idaiyinam = asList(IY_UU, IR_UU, IL_UU, IV_UU, ILLL_UU, ILL_UU);

        verifyPropertiesOf(uugaaraVarisai, vallinam, mellinam, idaiyinam, 6, false, "_06_");

        List<UnicodeUyirMeyyezhuthu> AgaraVarisai = asList(IK_A, ING_A, ICH_A, INJ_A, IDD_A, INNN_A, ITH_A, INTH_A, IP_A, IM_A, IY_A, IR_A, IL_A, IV_A, ILLL_A, ILL_A, IRR_A, IN_A);
        vallinam = asList(IK_A, ICH_A, IDD_A, ITH_A, IP_A, IRR_A);
        mellinam = asList(ING_A, INJ_A, INNN_A, INTH_A, IM_A, IN_A);
        idaiyinam = asList(IY_A, IR_A, IL_A, IV_A, ILLL_A, ILL_A);

        verifyPropertiesOf(AgaraVarisai, vallinam, mellinam, idaiyinam, 7, true, "_07_");

        List<UnicodeUyirMeyyezhuthu> AAgaaraVarisai = asList(IK_AA, ING_AA, ICH_AA, INJ_AA, IDD_AA, INNN_AA, ITH_AA, INTH_AA, IP_AA, IM_AA, IY_AA, IR_AA, IL_AA, IV_AA, ILLL_AA, ILL_AA, IRR_AA, IN_AA);
        vallinam = asList(IK_AA, ICH_AA, IDD_AA, ITH_AA, IP_AA, IRR_AA);
        mellinam = asList(ING_AA, INJ_AA, INNN_AA, INTH_AA, IM_AA, IN_AA);
        idaiyinam = asList(IY_AA, IR_AA, IL_AA, IV_AA, ILLL_AA, ILL_AA);

        verifyPropertiesOf(AAgaaraVarisai, vallinam, mellinam, idaiyinam, 8, false, "_08_");

        List<UnicodeUyirMeyyezhuthu> igaaraVarisai = asList(IK_I, ING_I, ICH_I, INJ_I, IDD_I, INNN_I, ITH_I, INTH_I, IP_I, IM_I, IY_I, IR_I, IL_I, IV_I, ILLL_I, ILL_I, IRR_I, IN_I);
        vallinam = asList(IK_I, ICH_I, IDD_I, ITH_I, IP_I, IRR_I);
        mellinam = asList(ING_I, INJ_I, INNN_I, INTH_I, IM_I, IN_I);
        idaiyinam = asList(IY_I, IR_I, IL_I, IV_I, ILLL_I, ILL_I);

        verifyPropertiesOf(igaaraVarisai, vallinam, mellinam, idaiyinam, 9, false, "_09_");

        List<UnicodeUyirMeyyezhuthu> ogaraVarisai = asList(IK_O, ING_O, ICH_O, INJ_O, IDD_O, INNN_O, ITH_O, INTH_O, IP_O, IM_O, IY_O, IR_O, IL_O, IV_O, ILLL_O, ILL_O, IRR_O, IN_O);
        vallinam = asList(IK_O, ICH_O, IDD_O, ITH_O, IP_O, IRR_O);
        mellinam = asList(ING_O, INJ_O, INNN_O, INTH_O, IM_O, IN_O);
        idaiyinam = asList(IY_O, IR_O, IL_O, IV_O, ILLL_O, ILL_O);

        verifyPropertiesOf(ogaraVarisai, vallinam, mellinam, idaiyinam, 10, true, "_10_");

        List<UnicodeUyirMeyyezhuthu> oogaaraVarisai = asList(IK_OO, ING_OO, ICH_OO, INJ_OO, IDD_OO, INNN_OO, ITH_OO, INTH_OO, IP_OO, IM_OO, IY_OO, IR_OO, IL_OO, IV_OO, ILLL_OO, ILL_OO, IRR_OO, IN_OO);
        vallinam = asList(IK_OO, ICH_OO, IDD_OO, ITH_OO, IP_OO, IRR_OO);
        mellinam = asList(ING_OO, INJ_OO, INNN_OO, INTH_OO, IM_OO, IN_OO);
        idaiyinam = asList(IY_OO, IR_OO, IL_OO, IV_OO, ILLL_OO, ILL_OO);

        verifyPropertiesOf(oogaaraVarisai, vallinam, mellinam, idaiyinam, 11, false, "_11_");

        List<UnicodeUyirMeyyezhuthu> ougaaraVarisai = asList(IK_OU, ING_OU, ICH_OU, INJ_OU, IDD_OU, INNN_OU, ITH_OU, INTH_OU, IP_OU, IM_OU, IY_OU, IR_OU, IL_OU, IV_OU, ILLL_OU, ILL_OU, IRR_OU, IN_OU);
        vallinam = asList(IK_OU, ICH_OU, IDD_OU, ITH_OU, IP_OU, IRR_OU);
        mellinam = asList(ING_OU, INJ_OU, INNN_OU, INTH_OU, IM_OU, IN_OU);
        idaiyinam = asList(IY_OU, IR_OU, IL_OU, IV_OU, ILLL_OU, ILL_OU);

        verifyPropertiesOf(ougaaraVarisai, vallinam, mellinam, idaiyinam, 12, false, "_12_");
    }

    private void verifyPropertiesOf(List<UnicodeUyirMeyyezhuthu> uyirMeyyezhuthukkal, List<UnicodeUyirMeyyezhuthu> vallinam, List<UnicodeUyirMeyyezhuthu> mellinam, List<UnicodeUyirMeyyezhuthu> idaiyinam, int vowelOffset, boolean isKuril, String vowelDigest) {
        String soundSizeDigest = isKuril ? "_1.0_" : "_2.0_";
        boolean isNedil = !isKuril;
        for (UnicodeUyirMeyyezhuthu e : uyirMeyyezhuthukkal){
            assertThat(e.isKuril(), is(isKuril));
            assertThat(e.isNedil(), is(isNedil));
            assertThat(e.getSoundSizeDigest(), is(soundSizeDigest));
            assertThat(e.getCharacterTypeDigest(), is("_4_"));
            assertThat(e.getVowelDigest(), is(vowelDigest));
            assertThat(e.getCodePointsCount(), is(2));
        }

        for (UnicodeUyirMeyyezhuthu e : vallinam){
            assertThat(e.isVallinam(), is(true));
            assertThat(e.isMellinam(), is(false));
            assertThat(e.isIdaiyinam(), is(false));
            assertThat(e.getSoundStrengthDigest(), is("_3_"));
        }

        for (UnicodeUyirMeyyezhuthu e : mellinam){
            assertThat(e.isVallinam(), is(false));
            assertThat(e.isMellinam(), is(true));
            assertThat(e.isIdaiyinam(), is(false));
            assertThat(e.getSoundStrengthDigest(), is("_1_"));
        }

        for (UnicodeUyirMeyyezhuthu e : idaiyinam){
            assertThat(e.isVallinam(), is(false));
            assertThat(e.isMellinam(), is(false));
            assertThat(e.isIdaiyinam(), is(true));
            assertThat(e.getSoundStrengthDigest(), is("_2_"));
        }

        assertThat(uyirMeyyezhuthukkal.get(0).getNumericStrength(), is(1800 + vowelOffset));
        assertThat(uyirMeyyezhuthukkal.get(1).getNumericStrength(), is(2200 + vowelOffset));
        assertThat(uyirMeyyezhuthukkal.get(2).getNumericStrength(), is(2300 + vowelOffset));
        assertThat(uyirMeyyezhuthukkal.get(3).getNumericStrength(), is(2700 + vowelOffset));
        assertThat(uyirMeyyezhuthukkal.get(4).getNumericStrength(), is(2800 + vowelOffset));
        assertThat(uyirMeyyezhuthukkal.get(5).getNumericStrength(), is(3200 + vowelOffset));
        assertThat(uyirMeyyezhuthukkal.get(6).getNumericStrength(), is(3300 + vowelOffset));
        assertThat(uyirMeyyezhuthukkal.get(7).getNumericStrength(), is(3700 + vowelOffset));
        assertThat(uyirMeyyezhuthukkal.get(8).getNumericStrength(), is(3900 + vowelOffset));
        assertThat(uyirMeyyezhuthukkal.get(9).getNumericStrength(), is(4300 + vowelOffset));
        assertThat(uyirMeyyezhuthukkal.get(10).getNumericStrength(), is(4400 + vowelOffset));
        assertThat(uyirMeyyezhuthukkal.get(11).getNumericStrength(), is(4500 + vowelOffset));
        assertThat(uyirMeyyezhuthukkal.get(12).getNumericStrength(), is(4700 + vowelOffset));
        assertThat(uyirMeyyezhuthukkal.get(13).getNumericStrength(), is(5000 + vowelOffset));
        assertThat(uyirMeyyezhuthukkal.get(14).getNumericStrength(), is(4900 + vowelOffset));
        assertThat(uyirMeyyezhuthukkal.get(15).getNumericStrength(), is(4800 + vowelOffset));
        assertThat(uyirMeyyezhuthukkal.get(16).getNumericStrength(), is(4600 + vowelOffset));
        assertThat(uyirMeyyezhuthukkal.get(17).getNumericStrength(), is(3800 + vowelOffset));
    }
}