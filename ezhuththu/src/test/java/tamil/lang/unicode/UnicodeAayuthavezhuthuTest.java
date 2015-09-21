package tamil.lang.unicode;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UnicodeAayuthavezhuthuTest {

    @Test
    public void shouldCheckPropertiesOfAayuthavezhuthu() {
        assertThat(UnicodeAayuthavezhuthu.AKTHU.getSoundSizeDigest(), is("_0.5_"));
        assertThat(UnicodeAayuthavezhuthu.AKTHU.getSoundStrengthDigest(), is("_0_"));
        assertThat(UnicodeAayuthavezhuthu.AKTHU.getCharacterTypeDigest(), is("_1_"));
        assertThat(UnicodeAayuthavezhuthu.AKTHU.getConsonantDigest(), is("_00_"));
        assertThat(UnicodeAayuthavezhuthu.AKTHU.getCodePointsCount(), is(1));
        assertThat(UnicodeAayuthavezhuthu.AKTHU.getNumericStrength(), is(1));
    }
}