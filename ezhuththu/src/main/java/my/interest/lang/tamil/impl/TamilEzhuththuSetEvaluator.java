package my.interest.lang.tamil.impl;

import common.lang.impl.AbstractCharacter;
import my.interest.lang.tamil.EzhuththuUtils;
import my.interest.lang.tamil.generated.antlr.letterset.TamilLetterSetLexer;
import my.interest.lang.tamil.generated.antlr.letterset.TamilLetterSetParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.expression.*;
import tamil.lang.api.expression.model.*;
import tamil.lang.api.ezhuththu.EzhuththuSetDescription;
import tamil.lang.exception.service.ServiceException;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by velsubra on 11/30/16.
 */
public class TamilEzhuththuSetEvaluator extends Evaluator<EzhuththuSetDescription> {

    private TamilEzhuththuSetEvaluator() {

    }

    public static TamilEzhuththuSetEvaluator DEFAULT = null;

    static {
        DEFAULT = new TamilEzhuththuSetEvaluator();
        Set<EzhuththuSetDescription> set = TamilFactory.getTamilCharacterSetCalculator().getEzhuththuSetDescriptions();
        for (final EzhuththuSetDescription item : set) {
            DEFAULT.registerKnownOperand(item.getName(), new OperandImpl(item));
            //System.out.println("adding " + item.getName());
        }

        DEFAULT.registerOperator(new NegationOperatorImpl());
        DEFAULT.registerOperator(new UnionOperatorImpl());
        DEFAULT.registerOperator(new IntersectionOperatorImpl());
        DEFAULT.registerOperator(new SubtractionOperatorImpl());
        DEFAULT.registerOperator(new MultiplicationOperatorImpl());

        DEFAULT.setVariableResolver(new VariableResolverImpl());
    }

    static class VariableResolverImpl implements VariableResolver<EzhuththuSetDescription> {

        public Operand<EzhuththuSetDescription> resolve(String variable) throws ServiceException {

            Set<TamilCharacter> set = null;
            if (variable.startsWith("[") && variable.endsWith("]")) {
                TamilWord word = TamilFactory.getTransliterator(null).transliterate(variable.substring(1, variable.length() -1));
                set = new HashSet<TamilCharacter>();
                 for( AbstractCharacter ac : word) {
                     if (ac.isPureTamilLetter()) {
                         set.add((TamilCharacter)ac);
                     }
                 }
            } else {
                set = TamilFactory.getTamilCharacterSetCalculator().find(variable);
            }
            return new OperandImpl(new EzhuththuSetDescriptionImpl(variable, set));
        }
    }

    static class OperandImpl implements Operand<EzhuththuSetDescription> {
        EzhuththuSetDescription setDescription = null;

        OperandImpl(EzhuththuSetDescription setDescription) {
            this.setDescription = setDescription;
        }

        public EzhuththuSetDescription getValue() {
            return setDescription;
        }
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


    static class MultiplicationOperatorImpl implements BinaryOperator<EzhuththuSetDescription> {

        public OperatorDefinition getOperator() {
            OperatorDefinition definition = new OperatorDefinition();
            definition.setName("MULTIPLICATION");
            return definition;
        }

        public Operand<EzhuththuSetDescription> perform(Operand<EzhuththuSetDescription> left, Operand<EzhuththuSetDescription> right) throws ServiceException {

            Set<TamilCharacter> ret = new HashSet<TamilCharacter>();
            for (TamilCharacter l : left.getValue().getCharacterSet()) {
                for (TamilCharacter r : right.getValue().getCharacterSet()) {
                    ret.add(l.multiply(r));
                }
            }
            return new OperandImpl(new EzhuththuSetDescriptionImpl(left.getValue().getName() + "*" + right.getValue().getName(), ret));
        }
    }

    static class SubtractionOperatorImpl implements BinaryOperator<EzhuththuSetDescription> {

        public OperatorDefinition getOperator() {
            OperatorDefinition definition = new OperatorDefinition();
            definition.setName("SUBTRACTION");
            return definition;
        }

        public Operand<EzhuththuSetDescription> perform(Operand<EzhuththuSetDescription> left, Operand<EzhuththuSetDescription> right) throws ServiceException {

            Set<TamilCharacter> ret = EzhuththuUtils.filterSubtraction(left.getValue().getCharacterSet(), right.getValue().getCharacterSet());
            return new OperandImpl(new EzhuththuSetDescriptionImpl(left.getValue().getName() + "|" + right.getValue().getName(), ret));
        }
    }


    static class IntersectionOperatorImpl implements BinaryOperator<EzhuththuSetDescription> {

        public OperatorDefinition getOperator() {
            OperatorDefinition definition = new OperatorDefinition();
            definition.setName("INTERSECTION");
            return definition;
        }

        public Operand<EzhuththuSetDescription> perform(Operand<EzhuththuSetDescription> left, Operand<EzhuththuSetDescription> right) throws ServiceException {

            Set<TamilCharacter> ret = EzhuththuUtils.filterIntersection(left.getValue().getCharacterSet(), right.getValue().getCharacterSet());
            return new OperandImpl(new EzhuththuSetDescriptionImpl(left.getValue().getName() + "|" + right.getValue().getName(), ret));
        }
    }


    static class UnionOperatorImpl implements BinaryOperator<EzhuththuSetDescription> {

        public OperatorDefinition getOperator() {
            OperatorDefinition definition = new OperatorDefinition();
            definition.setName("UNION");
            return definition;
        }

        public Operand<EzhuththuSetDescription> perform(Operand<EzhuththuSetDescription> left, Operand<EzhuththuSetDescription> right) throws ServiceException {

            Set<TamilCharacter> ret = new HashSet<TamilCharacter>(left.getValue().getCharacterSet());
            ret.addAll(right.getValue().getCharacterSet());
            return new OperandImpl(new EzhuththuSetDescriptionImpl(left.getValue().getName() + "|" + right.getValue().getName(), ret));
        }
    }


    static class NegationOperatorImpl implements UnaryOperator<EzhuththuSetDescription> {

        public OperatorDefinition getOperator() {
            OperatorDefinition definition = new OperatorDefinition();
            definition.setName("NEGATION");
            return definition;
        }

        public Operand<EzhuththuSetDescription> perform(Operand<EzhuththuSetDescription> operand) throws ServiceException {
            Set<TamilCharacter> set = operand.getValue().getCharacterSet();
            Set<TamilCharacter> ret = EzhuththuUtils.filterOut(set);
            return new OperandImpl(new EzhuththuSetDescriptionImpl("!" + operand.getValue().getName(), ret));
        }
    }

    public List<? extends PostFixExpressionItem> generatePostFix(String infix) throws ServiceException {
        TamilLetterSetLexer lexer = new TamilLetterSetLexer(new ANTLRInputStream(infix));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        TamilLetterSetParser parser = new TamilLetterSetParser(tokens);
        TamilLetterSetParser.ExpressionContext ex = parser.expression();

        return toPostFix(parser, ex);

    }


    private List<PostFixExpressionItem> toPostFixTerm(TamilLetterSetParser parser, TamilLetterSetParser.TermContext term) throws ServiceException {
        for (ParseTree node : term.children) {
            checkErrorNode(node);
        }

        List<PostFixExpressionItem> ret = new LinkedList<PostFixExpressionItem>();

        if (term.direct_variable != null) {
            TerminalNode terminalNode = (TerminalNode) term.children.get(0);

            String firstSymbolicName = parser.getVocabulary().getSymbolicName(terminalNode.getSymbol().getType());
            if (firstSymbolicName == null) {
                firstSymbolicName = terminalNode.getText();
            }
            ret.add(new VariableItem(firstSymbolicName, terminalNode.getText(), terminalNode.getSymbol().getTokenIndex()));
        } else if (term.simple_expression != null) {
            ret.addAll(toPostFix(parser, term.simple_expression.expression()));
        } else if (term.negated_expression != null) {
            TerminalNode terminalNode = (TerminalNode) term.children.get(0);

            ret.addAll(toPostFix(parser, ((TamilLetterSetParser.Closed_expressionContext) term.children.get(1)).expression()));
            ret.add(toUnaryOperatorItem(parser, terminalNode));
        } else if (term.negated_variable != null) {
            TerminalNode terminalNode = (TerminalNode) term.children.get(0);

            TerminalNode secondNode = (TerminalNode) term.children.get(1);
            String secondSymbolicName = parser.getVocabulary().getSymbolicName(secondNode.getSymbol().getType());
            if (secondSymbolicName == null) {
                secondSymbolicName = secondNode.getText();
            }
            ret.add(new VariableItem(secondSymbolicName, secondNode.getText(), secondNode.getSymbol().getTokenIndex()));
            ret.add(toUnaryOperatorItem(parser, terminalNode));
        } else {
            throw new ServiceException("Unexpected term type!");
        }
        return ret;
    }


    private UnaryOperatorItem toUnaryOperatorItem(TamilLetterSetParser parser, ParseTree node) throws ServiceException {
        if (!TerminalNode.class.isAssignableFrom(node.getClass())) {
            throw new ServiceException("Expected operator instead of " + node.getText());
        }
        TerminalNode terminalNode = (TerminalNode) node;
        String symbolicName = parser.getVocabulary().getSymbolicName(terminalNode.getSymbol().getType());
        boolean operation = false;
        if (symbolicName == null) {
            symbolicName = node.getText();
        } else {
            operation = operatorDefinitionMap.containsKey(symbolicName);
        }
        //  String literalName = parser.getVocabulary().getLiteralName(terminalNode.getSymbol().getType());


        if (!operation) {
            operation = operatorDefinitionMap.containsKey(node.getText());
        }

        if (operation) {
            return new UnaryOperatorItem(symbolicName, node.getText(), terminalNode.getSymbol().getTokenIndex());
        } else {
            throw new ServiceException(node.getText() + " is not registered to be binary operator.");
        }
    }

    private BinaryOperatorItem toBinaryOperatorItem(TamilLetterSetParser parser, ParseTree node) throws ServiceException {
        if (!TerminalNode.class.isAssignableFrom(node.getClass())) {
            throw new ServiceException("Expected operator instead of " + node.getText());
        }
        TerminalNode terminalNode = (TerminalNode) node;
        String symbolicName = parser.getVocabulary().getSymbolicName(terminalNode.getSymbol().getType());
        boolean operation = false;
        if (symbolicName == null) {
            symbolicName = node.getText();
        } else {
            operation = operatorDefinitionMap.containsKey(symbolicName);
        }
        //  String literalName = parser.getVocabulary().getLiteralName(terminalNode.getSymbol().getType());


        if (!operation) {
            operation = operatorDefinitionMap.containsKey(node.getText());
        }

        if (operation) {
            return new BinaryOperatorItem(symbolicName, node.getText(), terminalNode.getSymbol().getTokenIndex());
        } else {
            throw new ServiceException(node.getText() + " is not registered to be a binary operator.");
        }
    }

    private void checkErrorNode(ParseTree node) throws ServiceException {
        if (ErrorNode.class.isAssignableFrom(node.getClass())) {
            ErrorNode errorNode = (ErrorNode) node;
            throw new ServiceException("Node " + errorNode.toString() + " is parsed! but after an error.");
        }
    }

    private List<PostFixExpressionItem> toPostFix(TamilLetterSetParser parser, TamilLetterSetParser.ExpressionContext expressionContext) throws ServiceException {
        List<PostFixExpressionItem> ret = new LinkedList<PostFixExpressionItem>();

        for (int i = 0; i < expressionContext.children.size(); i++) {
            ParseTree node = expressionContext.children.get(i);
            checkErrorNode(node);
            if (TamilLetterSetParser.TermContext.class.isAssignableFrom(node.getClass())) {
                TamilLetterSetParser.TermContext lerftTerm = (TamilLetterSetParser.TermContext) node;
                ret.addAll(toPostFixTerm(parser, lerftTerm));
                if (i < expressionContext.children.size() - 1) {
                    i++;
                    ParseTree operation = expressionContext.children.get(i);
                    checkErrorNode(operation);
                    BinaryOperatorItem operationItem = toBinaryOperatorItem(parser, operation);
                    if (i < expressionContext.children.size() - 1) {
                        i++;
                        ParseTree rightNode = expressionContext.children.get(i);
                        checkErrorNode(rightNode);
                        if (!TamilLetterSetParser.TermContext.class.isAssignableFrom(rightNode.getClass())) {
                            throw new ServiceException("Operation " + operation.getText() + " is not followed by a valid term. The symbol is of type:" + rightNode.getClass().getName());
                        }

                        TamilLetterSetParser.TermContext rightTerm = (TamilLetterSetParser.TermContext) expressionContext.children.get(i);
                        ret.addAll(toPostFixTerm(parser, rightTerm));
                    } else {
                        throw new ServiceException("Missing right operand after " + operation.getText());
                    }


                    ret.add(operationItem);


                }

            } else {
                throw new ServiceException("Unexpected symbol " + node.getText() + " of type " + node.getClass().getName());
            }


        }
        return ret;
    }


}
