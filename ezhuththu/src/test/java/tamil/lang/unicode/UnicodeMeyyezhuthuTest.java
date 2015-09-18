package tamil.lang.unicode;

import org.junit.Test;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilCompoundCharacter;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class UnicodeMeyyezhuthuTest {

    @Test
    public void shouldVerifyPropertiesOfMeyyezhuthu() {
        List<UnicodeMeyyezhuthu> vallinaEzhuthukkal = asList(UnicodeMeyyezhuthu.IK, UnicodeMeyyezhuthu.ICH, UnicodeMeyyezhuthu.IDD, UnicodeMeyyezhuthu.ITH, UnicodeMeyyezhuthu.IP, UnicodeMeyyezhuthu.IRR);
        for (UnicodeMeyyezhuthu e : vallinaEzhuthukkal) {
            assertThat(e.isVallinam(), is(true));
            assertThat(e.isMellinam(), is(false));
            assertThat(e.isIdaiyinam(), is(false));
            assertThat(e.getSoundSizeDigest(), is("_0.5_"));
            assertThat(e.getSoundStrengthDigest(), is(TamilCharacter.DIGEST_SOUND_STRENGTH._V_.toString()));
            assertThat(e.getVowelDigest(), is("_00_"));
            assertThat(e.getCharacterTypeDigest(), is("_3_"));
            assertThat(e.getCodePointsCount(), is(2));
        }

        List<UnicodeMeyyezhuthu> mellinaEzhuthukkal = asList(UnicodeMeyyezhuthu.ING, UnicodeMeyyezhuthu.INJ, UnicodeMeyyezhuthu.INNN, UnicodeMeyyezhuthu.INTH, UnicodeMeyyezhuthu.IM, UnicodeMeyyezhuthu.IN);
        for (UnicodeMeyyezhuthu e : mellinaEzhuthukkal) {
            assertThat(e.isVallinam(), is(false));
            assertThat(e.isMellinam(), is(true));
            assertThat(e.isIdaiyinam(), is(false));
            assertThat(e.getSoundSizeDigest(), is("_0.5_"));
            assertThat(e.getSoundStrengthDigest(), is(TamilCharacter.DIGEST_SOUND_STRENGTH._M_.toString()));
            assertThat(e.getVowelDigest(), is("_00_"));
            assertThat(e.getCharacterTypeDigest(), is("_3_"));
            assertThat(e.getCodePointsCount(), is(2));
        }

        List<UnicodeMeyyezhuthu> idaiyinaEzhuthukkal = asList(UnicodeMeyyezhuthu.IY, UnicodeMeyyezhuthu.IR, UnicodeMeyyezhuthu.IL, UnicodeMeyyezhuthu.IV, UnicodeMeyyezhuthu.ILLL, UnicodeMeyyezhuthu.ILL);
        for (UnicodeMeyyezhuthu e : idaiyinaEzhuthukkal) {
            assertThat(e.isVallinam(), is(false));
            assertThat(e.isMellinam(), is(false));
            assertThat(e.isIdaiyinam(), is(true));
            assertThat(e.getSoundSizeDigest(), is("_0.5_"));
            assertThat(e.getSoundStrengthDigest(), is(TamilCharacter.DIGEST_SOUND_STRENGTH._I_.toString()));
            assertThat(e.getVowelDigest(), is("_00_"));
            assertThat(e.getCharacterTypeDigest(), is("_3_"));
            assertThat(e.getCodePointsCount(), is(2));
        }

        assertThat(UnicodeMeyyezhuthu.IK.getConsonantDigest(), is("_01_"));
        assertThat(UnicodeMeyyezhuthu.ING.getConsonantDigest(), is("_02_"));
        assertThat(UnicodeMeyyezhuthu.ICH.getConsonantDigest(), is("_03_"));
        assertThat(UnicodeMeyyezhuthu.INJ.getConsonantDigest(), is("_04_"));
        assertThat(UnicodeMeyyezhuthu.IDD.getConsonantDigest(), is("_05_"));
        assertThat(UnicodeMeyyezhuthu.INNN.getConsonantDigest(), is("_06_"));
        assertThat(UnicodeMeyyezhuthu.ITH.getConsonantDigest(), is("_07_"));
        assertThat(UnicodeMeyyezhuthu.INTH.getConsonantDigest(), is("_08_"));
        assertThat(UnicodeMeyyezhuthu.IP.getConsonantDigest(), is("_09_"));
        assertThat(UnicodeMeyyezhuthu.IM.getConsonantDigest(), is("_10_"));
        assertThat(UnicodeMeyyezhuthu.IY.getConsonantDigest(), is("_11_"));
        assertThat(UnicodeMeyyezhuthu.IR.getConsonantDigest(), is("_12_"));
        assertThat(UnicodeMeyyezhuthu.IL.getConsonantDigest(), is("_13_"));
        assertThat(UnicodeMeyyezhuthu.IV.getConsonantDigest(), is("_14_"));
        assertThat(UnicodeMeyyezhuthu.ILLL.getConsonantDigest(), is("_15_"));
        assertThat(UnicodeMeyyezhuthu.ILL.getConsonantDigest(), is("_16_"));
        assertThat(UnicodeMeyyezhuthu.IRR.getConsonantDigest(), is("_17_"));
        assertThat(UnicodeMeyyezhuthu.IN.getConsonantDigest(), is("_18_"));

        assertThat(UnicodeMeyyezhuthu.IK.getNumericStrength(), is(1800));
        assertThat(UnicodeMeyyezhuthu.ING.getNumericStrength(), is(2200));
        assertThat(UnicodeMeyyezhuthu.ICH.getNumericStrength(), is(2300));
        assertThat(UnicodeMeyyezhuthu.INJ.getNumericStrength(), is(2700));
        assertThat(UnicodeMeyyezhuthu.IDD.getNumericStrength(), is(2800));
        assertThat(UnicodeMeyyezhuthu.INNN.getNumericStrength(), is(3200));
        assertThat(UnicodeMeyyezhuthu.ITH.getNumericStrength(), is(3300));
        assertThat(UnicodeMeyyezhuthu.INTH.getNumericStrength(), is(3700));
        assertThat(UnicodeMeyyezhuthu.IP.getNumericStrength(), is(3900));
        assertThat(UnicodeMeyyezhuthu.IM.getNumericStrength(), is(4300));
        assertThat(UnicodeMeyyezhuthu.IY.getNumericStrength(), is(4400));
        assertThat(UnicodeMeyyezhuthu.IR.getNumericStrength(), is(4500));
        assertThat(UnicodeMeyyezhuthu.IL.getNumericStrength(), is(4700));
        assertThat(UnicodeMeyyezhuthu.IV.getNumericStrength(), is(5000));
        assertThat(UnicodeMeyyezhuthu.ILLL.getNumericStrength(), is(4900));
        assertThat(UnicodeMeyyezhuthu.ILL.getNumericStrength(), is(4800));
        assertThat(UnicodeMeyyezhuthu.IRR.getNumericStrength(), is(4600));
        assertThat(UnicodeMeyyezhuthu.IN.getNumericStrength(), is(3800));
    }
}