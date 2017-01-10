package my.interest.lang.tamil.impl;

import common.lang.impl.AbstractCharacter;
import my.interest.lang.tamil.EzhuththuUtils;
import my.interest.lang.tamil.generated.antlr.letterset.TamilLetterSetBaseVisitor;
import my.interest.lang.tamil.generated.antlr.letterset.TamilLetterSetLexer;
import my.interest.lang.tamil.generated.antlr.letterset.TamilLetterSetParser;
import my.interest.lang.tamil.generated.antlr.letterset.TamilLetterSetVisitor;
import my.interest.lang.tamil.impl.antlr4.ErrorListener;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.TerminalNode;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.ezhuththu.EzhuththuSetDescription;
import tamil.lang.api.ezhuththu.TamilCharacterSetCalculator;
import tamil.lang.exception.service.ServiceException;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by velsubra on 12/5/16.
 */
public class TamilEzhuththuSetExpressionInterpreter extends TamilLetterSetBaseVisitor<EzhuththuSetDescription>  implements TamilLetterSetVisitor<EzhuththuSetDescription> {
    private static TamilCharacterSetCalculator calculator = TamilFactory.getTamilCharacterSetCalculator();


    TamilLetterSetParser parser = null;




    public static TamilLetterSetParser createTamilLetterSetParser(String infix) {

        TamilLetterSetLexer lexer = new TamilLetterSetLexer(new ANTLRInputStream(infix));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        TamilLetterSetParser parser = new TamilLetterSetParser(tokens);
        return parser;
    }

    public static Set<TamilCharacter> evaluate(String infix) throws ServiceException {

        TamilLetterSetParser parser = createTamilLetterSetParser(infix);
        return new TamilEzhuththuSetExpressionInterpreter(parser).interpret().getCharacterSet();

    }

    ErrorListener listener = null;

    public TamilEzhuththuSetExpressionInterpreter(TamilLetterSetParser parser) {
        this.parser = parser;
        listener = new ErrorListener();
        parser.getErrorListeners().clear();
        parser.addErrorListener(listener);


    }

    /**
     * Interprets the value
     *
     * @return the interpreted result in the form of a set
     */
    public EzhuththuSetDescription interpret() throws ServiceException {
        listener.reset();
        TamilLetterSetParser.ExprContext ctx = parser.expr();
        if (listener.errorOccurred) {
            throw new ServiceException("Compilation failed:" + listener.errors);
        }
        return visit(ctx);
    }

    @Override
    public EzhuththuSetDescription visitConstantSet(TamilLetterSetParser.ConstantSetContext ctx) {
        return visitConstantSet(ctx.CONSTANT_SET());
    }

    public EzhuththuSetDescription visitConstantSet(TerminalNode ctx) {
        String constant = ctx.getText();
        if (constant.startsWith("[") && constant.endsWith("]")) {
            TamilWord word = TamilFactory.getTransliterator(null).transliterate(constant.substring(1, constant.length() - 1));
            Set<TamilCharacter> set = new HashSet<TamilCharacter>();
            for (AbstractCharacter ac : word) {
                if (ac.isPureTamilLetter()) {
                    set.add((TamilCharacter) ac);
                }
            }
            return new EzhuththuSetDescriptionImpl(ctx.getText(), set);
        } else {
            throw new ServiceException("Unrecognized constant:" + constant);
        }
    }

    @Override
    public EzhuththuSetDescription visitNegatedClosedExpression(TamilLetterSetParser.NegatedClosedExpressionContext ctx) {
        EzhuththuSetDescription ret = visitClosed_expr(ctx.closed_expr());
        return new EzhuththuSetDescriptionImpl(ctx.getText(), EzhuththuUtils.filterOut(ret.getCharacterSet()));

    }

    @Override
    public EzhuththuSetDescription visitMultiplication(TamilLetterSetParser.MultiplicationContext ctx) {
        EzhuththuSetDescription left = visit(ctx.expr(0));
        EzhuththuSetDescription right = visit(ctx.expr(1));
        return multiply(ctx.getText(),left,right);
    }

    private EzhuththuSetDescription multiply(String name, EzhuththuSetDescription left, EzhuththuSetDescription right) {
        Set<TamilCharacter> ret = new HashSet<TamilCharacter>();
        for (TamilCharacter l : left.getCharacterSet()) {
            for (TamilCharacter r : right.getCharacterSet()) {
                ret.add(l.multiply(r));
            }
        }
        return new EzhuththuSetDescriptionImpl(name, ret);
    }

    public EzhuththuSetDescription visitNegatedConstantSet(TamilLetterSetParser.NegatedConstantSetContext ctx) {
        EzhuththuSetDescription ret = visitConstantSet(ctx.CONSTANT_SET());
        return new EzhuththuSetDescriptionImpl(ctx.getText(), EzhuththuUtils.filterOut(ret.getCharacterSet()));
    }

    @Override
    public EzhuththuSetDescription visitClosedExpression(TamilLetterSetParser.ClosedExpressionContext ctx) {

        return visit(ctx.closed_expr());
    }


    @Override
    public EzhuththuSetDescription visitStandardOperation(TamilLetterSetParser.StandardOperationContext ctx) {

        String firstSymbolicName = parser.getVocabulary().getSymbolicName(ctx.op.getType());
        if (firstSymbolicName == null) {
            firstSymbolicName = ctx.getText();
        }
        EzhuththuSetDescription left = visit(ctx.expr(0));
        EzhuththuSetDescription right = visit(ctx.expr(1));

        if ("UNION".equals(firstSymbolicName)) {
            Set<TamilCharacter> ret = new HashSet<TamilCharacter>(left.getCharacterSet());
            ret.addAll(right.getCharacterSet());
            return new EzhuththuSetDescriptionImpl(ctx.getText(), ret);
        }
        if ("INTERSECTION".equals(firstSymbolicName)) {
            Set<TamilCharacter> ret = EzhuththuUtils.filterIntersection(left.getCharacterSet(), right.getCharacterSet());
            return new EzhuththuSetDescriptionImpl(ctx.getText(), ret);
        }
        if ("SUBTRACTION".equals(firstSymbolicName)) {
            Set<TamilCharacter> ret = EzhuththuUtils.filterSubtraction(left.getCharacterSet(), right.getCharacterSet());
            return new EzhuththuSetDescriptionImpl(ctx.getText(), ret);
        } else {
            throw new ServiceException("Operation " + ctx.op.getText() + " not handled.");
        }

    }
    @Override
    public EzhuththuSetDescription visitNegatedNamedSet(TamilLetterSetParser.NegatedNamedSetContext ctx) {
        EzhuththuSetDescription ret = visitNamedSet(ctx.NAMED_SET());
        return new EzhuththuSetDescriptionImpl(ctx.getText(), EzhuththuUtils.filterOut(ret.getCharacterSet()));
    }

    @Override
    public EzhuththuSetDescription visitImplicitMultiplication1(TamilLetterSetParser.ImplicitMultiplication1Context ctx) {
        EzhuththuSetDescription left = visit(ctx.expr());
        EzhuththuSetDescription right = visit(ctx.closed_expr());
        return multiply(ctx.getText(),left,right);
    }

    @Override
    public EzhuththuSetDescription visitImplicitMultiplication2(TamilLetterSetParser.ImplicitMultiplication2Context ctx) {
        EzhuththuSetDescription left = visit(ctx.closed_expr());
        EzhuththuSetDescription right = visit(ctx.expr());
        return multiply(ctx.getText(),left,right);
    }

    @Override
    public EzhuththuSetDescription visitNamedSet(TamilLetterSetParser.NamedSetContext ctx) {
            return visitNamedSet(ctx.NAMED_SET());
    }


    public EzhuththuSetDescription visitNamedSet(TerminalNode ctx) {
        return new EzhuththuSetDescriptionImpl(ctx.getText(), calculator.find(ctx.getText()));
    }

    @Override
    public EzhuththuSetDescription visitClosed_expr(TamilLetterSetParser.Closed_exprContext ctx) {
        return visit(ctx.expr());
    }



    static class EzhuththuSetDescriptionImpl implements EzhuththuSetDescription {
        String name = null;
        Set<TamilCharacter> set = null;

        EzhuththuSetDescriptionImpl(String name, Set<TamilCharacter> set) {
            this.name = name;
            this.set = set;
        }

        public Set<TamilCharacter> getCharacterSet() {
            return set;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return "Calculated: " + name;
        }
    }
}
