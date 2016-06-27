package test.simple;

import org.junit.Test;
import tamil.lang.TamilFactory;
import tamil.lang.api.number.NumberReader;

/**
 * Created by velsubra on 6/25/16.
 */
public class NumberCustomTest {

    static {
        TamilFactory.init();
    }

    @Test
    public void testReadSimple() {
        NumberReader reader = TamilFactory.getNumberReader();
       String val =  reader.readAsNumber("தொட்டகம்");
        System.out.print(val);
    }

}
