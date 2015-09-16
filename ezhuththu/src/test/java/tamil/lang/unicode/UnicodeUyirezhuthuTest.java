package tamil.lang.unicode;

import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class UnicodeUyirezhuthuTest {

    @Test
    public void shouldCheckOliAlavuPropertyOfUyirezhuthu() {
        List<UnicodeUyirezhuthu> kurilEzhuthukkal = asList(UnicodeUyirezhuthu.a, UnicodeUyirezhuthu.E, UnicodeUyirezhuthu.U, UnicodeUyirezhuthu.A, UnicodeUyirezhuthu.O);
        for (UnicodeUyirezhuthu e : kurilEzhuthukkal) {
            assertThat(e.isKuril(), is(true));
            assertThat(e.isNedil(), is(false));
        }

        List<UnicodeUyirezhuthu> nedilEzhuthukkal = asList(UnicodeUyirezhuthu.aa, UnicodeUyirezhuthu.EE, UnicodeUyirezhuthu.UU, UnicodeUyirezhuthu.AA, UnicodeUyirezhuthu.I, UnicodeUyirezhuthu.OO, UnicodeUyirezhuthu.OU);
        for (UnicodeUyirezhuthu e : nedilEzhuthukkal) {
            assertThat(e.isKuril(), is(false));
            assertThat(e.isNedil(), is(true));
        }
    }
}