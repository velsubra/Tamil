package tamil.lang.unicode;

import org.junit.Test;
import tamil.lang.TamilCharacter;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UnicodeUyirezhuthuTest {

    @Test
    public void shouldCheckOliAlavuPropertyOfUyirezhuthu() {
        List<UnicodeUyirezhuthu> kurilEzhuthukkal = asList(UnicodeUyirezhuthu.a, UnicodeUyirezhuthu.E, UnicodeUyirezhuthu.U, UnicodeUyirezhuthu.A, UnicodeUyirezhuthu.O);
        for (UnicodeUyirezhuthu e : kurilEzhuthukkal) {
            assertThat(e.isKuril(), is(true));
            assertThat(e.isNedil(), is(false));
            assertThat(e.getSoundSizeDigest(), is("_1.0_"));
            assertThat(e.getSoundStrengthDigest(), is("_0_"));
            assertThat(e.getConsonantDigest(), is("_00_"));
            assertThat(e.getCharacterTypeDigest(), is(TamilCharacter.DIGEST_CHAR_TYPE._U_.toString()));
            assertThat(e.getCodePointsCount(), is(1));
        }

        List<UnicodeUyirezhuthu> nedilEzhuthukkal = asList(UnicodeUyirezhuthu.aa, UnicodeUyirezhuthu.EE, UnicodeUyirezhuthu.UU, UnicodeUyirezhuthu.AA, UnicodeUyirezhuthu.I, UnicodeUyirezhuthu.OO, UnicodeUyirezhuthu.OU);
        for (UnicodeUyirezhuthu e : nedilEzhuthukkal) {
            assertThat(e.isKuril(), is(false));
            assertThat(e.isNedil(), is(true));
            assertThat(e.getSoundSizeDigest(), is("_2.0_"));
            assertThat(e.getSoundStrengthDigest(), is("_0_"));
            assertThat(e.getConsonantDigest(), is("_00_"));
            assertThat(e.getCharacterTypeDigest(), is(TamilCharacter.DIGEST_CHAR_TYPE._U_.toString()));
            assertThat(e.getCodePointsCount(), is(1));
        }

        assertThat(UnicodeUyirezhuthu.a.getVowelDigest(), is("_01_"));
        assertThat(UnicodeUyirezhuthu.aa.getVowelDigest(), is("_02_"));
        assertThat(UnicodeUyirezhuthu.E.getVowelDigest(), is("_03_"));
        assertThat(UnicodeUyirezhuthu.EE.getVowelDigest(), is("_04_"));
        assertThat(UnicodeUyirezhuthu.U.getVowelDigest(), is("_05_"));
        assertThat(UnicodeUyirezhuthu.UU.getVowelDigest(), is("_06_"));
        assertThat(UnicodeUyirezhuthu.A.getVowelDigest(), is("_07_"));
        assertThat(UnicodeUyirezhuthu.AA.getVowelDigest(), is("_08_"));
        assertThat(UnicodeUyirezhuthu.I.getVowelDigest(), is("_09_"));
        assertThat(UnicodeUyirezhuthu.O.getVowelDigest(), is("_10_"));
        assertThat(UnicodeUyirezhuthu.OO.getVowelDigest(), is("_11_"));
        assertThat(UnicodeUyirezhuthu.OU.getVowelDigest(), is("_12_"));

        assertThat(UnicodeUyirezhuthu.a.getNumericStrength(), is(201));
        assertThat(UnicodeUyirezhuthu.aa.getNumericStrength(), is(301));
        assertThat(UnicodeUyirezhuthu.E.getNumericStrength(), is(401));
        assertThat(UnicodeUyirezhuthu.EE.getNumericStrength(), is(501));
        assertThat(UnicodeUyirezhuthu.U.getNumericStrength(), is(601));
        assertThat(UnicodeUyirezhuthu.UU.getNumericStrength(), is(701));
        assertThat(UnicodeUyirezhuthu.A.getNumericStrength(), is(1101));
        assertThat(UnicodeUyirezhuthu.AA.getNumericStrength(), is(1201));
        assertThat(UnicodeUyirezhuthu.I.getNumericStrength(), is(1301));
        assertThat(UnicodeUyirezhuthu.O.getNumericStrength(), is(1501));
        assertThat(UnicodeUyirezhuthu.OO.getNumericStrength(), is(1601));
        assertThat(UnicodeUyirezhuthu.OU.getNumericStrength(), is(1701));
    }
}