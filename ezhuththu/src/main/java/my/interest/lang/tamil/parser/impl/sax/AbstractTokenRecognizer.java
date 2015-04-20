package my.interest.lang.tamil.parser.impl.sax;

import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.known.IKnownWord;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public abstract class AbstractTokenRecognizer extends  TokenRecognizer {

    protected AbstractTokenRecognizer(TamilWord token)     {
        this.token = token;
        this.list = TamilFactory.getSystemDictionary().lookup(token);
        this.tokenContainer = new TamilWordPartContainer(token);
    }


    protected TamilWord token;
    protected TamilWordPartContainer tokenContainer;


    List<IKnownWord> list = null;

    protected List<IKnownWord> getKnowns() {
       return list;
    }


}
