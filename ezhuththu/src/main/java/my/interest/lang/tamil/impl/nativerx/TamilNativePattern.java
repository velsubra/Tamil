package my.interest.lang.tamil.impl.nativerx;

import my.interest.lang.tamil.generated.antlr.letterset.TamilLetterSetParser;
import my.interest.lang.tamil.generated.antlr.rx.TamilRXLexer;
import my.interest.lang.tamil.generated.antlr.rx.TamilRXParser;
import my.interest.lang.tamil.impl.TamilEzhuththuSetExpressionInterpreter;
import my.interest.lang.tamil.impl.antlr4.ErrorListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import tamil.lang.TamilWord;
import tamil.lang.exception.service.ServiceException;

/**
 * Created by velsubra on 1/8/17.
 */
public class TamilNativePattern {


    TamilRXParser parser = null;

    TamilRXParser.ExpressionContext expressionContext = null;

    ErrorListener listener = null;


    private TamilNativePattern(TamilRXParser parser) {
        this.parser = parser;
        listener = new ErrorListener();
        parser.getErrorListeners().clear();
        parser.addErrorListener(listener);


    }

    private void compile() {
        listener.reset();
        expressionContext = (TamilRXParser.ExpressionContext)parser.rx_list();
        if (listener.errorOccurred) {
            throw new ServiceException("Compilation failed:" + listener.errors);
        }

    }


    public TamilNativeMatcher matcher(TamilWord text) {
        return new TamilNativeMatcher(this.parser, expressionContext, text);
    }


    public static TamilNativePattern compile(String nativeExpression) throws ServiceException {

        TamilNativePattern pattern = new TamilNativePattern(createTamilRxParser(nativeExpression));
        pattern.compile();
        return pattern;

    }

    public static TamilRXParser createTamilRxParser(String nativeExpression) {

        TamilRXLexer lexer = new TamilRXLexer(new ANTLRInputStream(nativeExpression));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        TamilRXParser parser = new TamilRXParser(tokens);
        return parser;
    }

}
