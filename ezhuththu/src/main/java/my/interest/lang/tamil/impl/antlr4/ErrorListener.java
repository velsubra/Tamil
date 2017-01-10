package my.interest.lang.tamil.impl.antlr4;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 * Created by velsubra on 1/8/17.
 */
public class ErrorListener extends BaseErrorListener {

    public StringBuffer errors = new StringBuffer();
    public boolean errorOccurred = false;

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol,
                            int line,
                            int charPositionInLine,
                            String msg,
                            RecognitionException e) {
        if (errorOccurred) {
            errors.append("\n");
        }
        errors.append("line " + line + ":" + charPositionInLine + " " + msg);
        errorOccurred = true;
    }

    public void reset() {
        errorOccurred = false;
        errors = new StringBuffer();
    }
}