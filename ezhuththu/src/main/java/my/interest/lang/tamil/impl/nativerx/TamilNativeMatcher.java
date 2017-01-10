package my.interest.lang.tamil.impl.nativerx;

import common.lang.Character;
import common.lang.impl.AbstractCharacter;
import my.interest.lang.tamil.generated.antlr.rx.TamilRXBaseVisitor;
import my.interest.lang.tamil.generated.antlr.rx.TamilRXParser;
import my.interest.lang.tamil.generated.antlr.rx.TamilRXVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.RuleNode;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.exception.service.ServiceException;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by velsubra on 1/8/17.
 */
public class TamilNativeMatcher extends TamilRXBaseVisitor<Boolean> implements TamilRXVisitor<Boolean> {


    private TamilWord text;
    private Map<String,TamilWord> capturedGroup = new HashMap<String, TamilWord>();

    private int start = 0;
    private int end = -1;

    public int start() {
        return start;
    }

    public int end() {
        return end;
    }

    private int currentPointer = 0;


    private TamilRXParser parser = null;

    TamilRXParser.ExpressionContext expressionContext = null;

    public TamilWord group(String name) {
        return  capturedGroup.get(name);
    }


    TamilNativeMatcher(TamilRXParser parser, TamilRXParser.ExpressionContext expressionContext, TamilWord text) {
        this.parser = parser;
        this.text = text;
        this.expressionContext = expressionContext;
    }

    private void reset() {
        this.currentPointer = start;
    }

    private boolean hasMore() {
        return currentPointer < text.size();
    }


    public boolean find() {
        capturedGroup.clear();
        start = currentPointer;

        boolean found = false;
        do {
            if (!hasMore()) break;
            found = visitExpression(expressionContext);
            if (found) {
                end = currentPointer;
                break;

            } else {
                start++;
                currentPointer = start;
            }
        } while (!found);

        return found;
    }


    @Override
    public Boolean visitExpression(TamilRXParser.ExpressionContext ctx) {
        return super.visitExpression(ctx);

    }

    @Override
    public Boolean visitOneOrMore(TamilRXParser.OneOrMoreContext ctx) {
        TamilRXParser.Simple_rxContext context = ctx.simple_rx();
        Boolean ret = context.accept(this);

        if (!ret) return false;
        consumeAsmuchAsPossible(context);
        return true;

    }

    private void consumeAsmuchAsPossible(ParserRuleContext context) {

        int cp = this.currentPointer;
        while (true) {
            if (context.accept(this)) {
                cp = currentPointer;
                continue;
            } else {
                this.currentPointer = cp;
                break;
            }
        }
    }

    @Override
    public Boolean visitZeroOrMore(TamilRXParser.ZeroOrMoreContext ctx) {

        TamilRXParser.Simple_rxContext context = ctx.simple_rx();
        consumeAsmuchAsPossible(context);
        return true;
    }

    @Override
    public Boolean visitFixedCount(TamilRXParser.FixedCountContext ctx) {
        TamilRXParser.Simple_rxContext ex = ctx.simple_rx();
        int count = Integer.parseInt(ctx.POSITIVE_INTEGER().getText());
        for (int i = 0; i < count; i++) {
            if (!ex.accept(this)) return false;
        }
        return true;
    }

    @Override
    public Boolean visitORExpression(TamilRXParser.ORExpressionContext ctx) {
        TamilRXParser.Or_rxContext orRxContext = ctx.or_rx();
        return visitOr_rx(orRxContext);

    }

    @Override
    public Boolean visitANDExpression(TamilRXParser.ANDExpressionContext ctx) {
        TamilRXParser.And_rxContext andRxContext = ctx.and_rx();
        return visitAnd_rx(andRxContext);

    }

    @Override
    public Boolean visitSimpleExpression(TamilRXParser.SimpleExpressionContext ctx) {
        return ctx.simple_rx().accept(this);
    }

    @Override
    public Boolean visitPositiveLookAhead(TamilRXParser.PositiveLookAheadContext ctx) {

        TamilRXParser.Rx_listContext la = ctx.lahead;

        int cp = this.currentPointer;
        if (la.accept(this)) {
            this.currentPointer = cp;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean visitNegativeLookAhead(TamilRXParser.NegativeLookAheadContext ctx) {
        TamilRXParser.Rx_listContext la = ctx.lahead;
        int cp = this.currentPointer;
        if (!la.accept(this)) {
            this.currentPointer = cp;
            return true;
        } else {
            return false;
        }
    }

    private boolean matchWhiteSpace() {

        if (hasMore()) {
            return text.get(currentPointer++).isWhiteSpace();

        } else {
            return false;
        }

    }

    private boolean matchWordBoundary() {
        AbstractCharacter last = null;
        AbstractCharacter cur = null;
        // AbstractCharacter next = null;
        if (currentPointer < text.length()) {
            cur = text.get(currentPointer);
//            if (currentPointer + 1 < text.length()) {
//                next = text.get(currentPointer + 1);
//            }
        }
        if (currentPointer > 0) {
            last = text.get(currentPointer - 1);
        }


        if (cur == null || cur.isWhiteSpace()) {
            if (last == null) {

                return false;
            } else {
                return !last.isWhiteSpace();
            }

        } else {

            if (last == null) {
                //Starting!
                return true;
            } else {
                return last.isWhiteSpace();
            }


        }


    }

    private boolean matchNonWordBoundary() {
        return !matchWordBoundary();
    }

    private boolean matchAny() {
        if (hasMore()) {
            currentPointer++;
            return true;
        } else {
            return false;
        }
    }

    private boolean matchCodePoint(char codepoint) {
        if (hasMore()) {
            AbstractCharacter character = text.get(currentPointer++);
            return character.toString().equals(String.valueOf(codepoint));

        } else {
            return false;
        }
    }

    @Override
    public Boolean visitSingleExpression(TamilRXParser.SingleExpressionContext ctx) {

        String token = ctx.TAMIL_RX().getText();
        String setName = token.substring(2, token.length() - 1);
        boolean escaping = false;
        if (setName.startsWith("[[[") && setName.endsWith("]]]")) {
            String control = setName.substring(3, setName.length() - 3);
            for (int i = 0; i < control.length(); i++) {

                char c = control.charAt(i);
                if (escaping) {
                    if (c == '\\') {
                        if (!matchCodePoint('\\')) {
                            return false;
                        }
                    } else if (c == 'b') {
                        if (!matchWordBoundary()) {
                            return false;
                        }
                    } else if (c == 'B') {
                        if (!matchNonWordBoundary()) {
                            return false;
                        }
                    } else if (c == 's') {
                        if (!matchWhiteSpace()) {
                            return false;
                        }
                    } else if (c == '.') {
                        if (!matchCodePoint('.')) {
                            return false;
                        }
                    } else {
                        throw new ServiceException("Invalid escape char:" + c);
                    }
                    escaping = false;
                } else {
                    if (c == '\\') {
                        escaping = true;
                    } else {
                        if (c == '.') {
                            if (!matchAny()) {
                                return false;
                            }
                        } else {
                            if (!matchCodePoint(c)) {
                                return false;
                            }
                        }
                    }
                }
            }
            if (escaping) {
                throw new ServiceException("Dangling escape sequence at the end");
            }
            return true;

        } else if (setName.startsWith("[[") && setName.endsWith("]]")) {
            TamilWord literal = TamilWord.from(setName.substring(2, setName.length() - 2), true);
            if (currentPointer + literal.length() > text.length()) {
                return false;
            }
            for (int i = 0; i < literal.length(); i++) {

                AbstractCharacter t = text.get(this.currentPointer++);
                AbstractCharacter lit = literal.get(i);
                if (t != lit) {
                    if (!t.equals(lit)) {
                        return false;
                    }
                }

            }
            return true;
        } else {
            Set<TamilCharacter> set = TamilFactory.getTamilCharacterSetCalculator().evaluate(setName);
            if (hasMore()) {
                AbstractCharacter t = text.get(this.currentPointer++);
                return set.contains(t);
            } else {
                return set.isEmpty();
            }
        }


    }

    @Override
    public Boolean visitGroupedExpression(TamilRXParser.GroupedExpressionContext ctx) {
        return visitGrouped_rx(ctx.grouped_rx());
    }

    @Override
    public Boolean visitGrouped_rx(TamilRXParser.Grouped_rxContext ctx) {
        String groupName = ctx.groupname.getText();
        TamilRXParser.Simple_rxContext rx = ctx.ex;
        int cp = currentPointer;
        boolean matched =  rx.accept(this);
        if (matched) {
            capturedGroup.put(groupName, this.text.subWord(cp, currentPointer));
        }
        return matched;
    }

    @Override
    public Boolean visitClosedExpression(TamilRXParser.ClosedExpressionContext ctx) {
        TamilRXParser.Rx_listContext context = ctx.closed_rx().ex;
        return context.accept(this);
    }

    @Override
    public Boolean visitOr_rx(TamilRXParser.Or_rxContext ctx) {
        int cp = this.currentPointer;
        for (TamilRXParser.Simple_rxContext c : ctx.simple_rx()) {
            if (c.accept(this)) {
                return true;

            } else {
                this.currentPointer = cp;
            }
        }

        return false;
    }

    @Override
    public Boolean visitAnd_rx(TamilRXParser.And_rxContext ctx) {
        List<TamilRXParser.Simple_rxContext> list = ctx.simple_rx();
        TamilRXParser.Simple_rxContext first = list.get(0);
        int boundary = -1;
        int cp = this.currentPointer;
        if (first.accept(this)) {
            boundary = currentPointer;
        } else {
            return false;

        }
        for (int i = 1; i < list.size(); i++) {
            currentPointer = cp;
            if (list.get(i).accept(this)) {
                if (boundary != currentPointer) {
                    this.currentPointer = cp;
                    return false;
                }
            } else {
                this.currentPointer = cp;
                return false;
            }
        }
        return true;

    }

    private boolean isAtStart() {
        return currentPointer == 0;
    }

    private boolean isFinished() {
        return currentPointer == text.length();
    }


    @Override
    public Boolean visitClosed_rx(TamilRXParser.Closed_rxContext ctx) {
        return ctx.ex.accept(this);
    }


    protected boolean shouldVisitNextChild(RuleNode node, Boolean currentResult) {

        return currentResult;
    }

    protected Boolean defaultResult() {
        return true;
    }


}
