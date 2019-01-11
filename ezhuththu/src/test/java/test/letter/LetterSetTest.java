package test.letter;

import junit.framework.Assert;
import my.interest.lang.tamil.generated.antlr.letterset.TamilLetterSetParser;
import my.interest.lang.tamil.impl.TamilEzhuththuSetExpressionInterpreter;
import org.antlr.v4.gui.TreeTextProvider;
import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.tree.Tree;
import org.junit.Test;
import tamil.lang
        .TamilCharacter;
import tamil.lang.TamilFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by velsubra on 11/30/16.
 */
public class LetterSetTest {

    static {
        TamilFactory.init();
    }

    @Test
    public void test0DirectVariable() {
        //   Set<TamilCharacter> set  = TamilFactory.getTamilCharacterSetCalculator().find("எழுத்து");
        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("எழுத்து");
        System.out.println(set1);
        Assert.assertEquals(247, set1.size());
    }

    @Test
    public void test1SimpleAdd() {
        //   Set<TamilCharacter> set  = TamilFactory.getTamilCharacterSetCalculator().find("எழுத்து");
        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("உயிர்+மெய்");
        System.out.println(set1);
        Assert.assertEquals(30, set1.size());
    }

    @Test
    public void test2SimpleAdd() {
        //   Set<TamilCharacter> set  = TamilFactory.getTamilCharacterSetCalculator().find("எழுத்து");
        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("மெய்+!மெய்");
        System.out.println(set1);
        Assert.assertEquals(247, set1.size());
    }

    @Test
    public void test2SimpleIntersection() {
        //   Set<TamilCharacter> set  = TamilFactory.getTamilCharacterSetCalculator().find("எழுத்து");
        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("உயிர்&மெய்");
        System.out.println(set1);
        Assert.assertEquals(0, set1.size());
    }

    @Test
    public void test3SimpleIntersection() {

        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("(uyir|(mey&uyir))");
        System.out.println(set1);
        Assert.assertEquals(12, set1.size());
    }

    @Test
    public void test4SimpleIntersection() {

        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("(uyir|mey)-uyir");
        System.out.println(set1);
        Assert.assertEquals(18, set1.size());
    }

    @Test
    public void test5SimpleIntersection() {

        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("!(uyir|mey)");
        System.out.println(set1);
        Assert.assertEquals(217, set1.size());
    }

    @Test
    public void test6SimpleIntersection() {

        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("!uyir|mey");
        System.out.println(set1);
        Assert.assertEquals(235, set1.size());
    }

    @Test
    public void test6ConstantNegation() {

        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("![a]");
        System.out.println(set1);
        Assert.assertEquals(246, set1.size());
    }

    @Test
    public void test7Constant() {

        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("[a,mmaa]+mey");
        System.out.println(set1);
        Assert.assertEquals(20, set1.size());
    }


    @Test
    public void test7Constant1() {

        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("[]+mey");
        System.out.println(set1);
        Assert.assertEquals(18, set1.size());
    }


    @Test
    public void test7Simplemulti() {

        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("mey*uyir");
        System.out.println(set1);
        Assert.assertEquals(216, set1.size());
    }

    @Test
    public void test7SimplemultiPresedence() {

        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("uyirmey-mey*uyir");
        System.out.println(set1);
        Assert.assertEquals(0, set1.size());
    }

    @Test
    public void test8SimplemultiPresedence() {

        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("(uyirmey-mey)*uyir");
        System.out.println(set1);
        Assert.assertEquals(1, set1.size());
    }

    @Test
    public void test8SimplemultiPresedenceNoStart() {

        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("(uyirmey-mey)uyir");
        System.out.println(set1);
        Assert.assertEquals(1, set1.size());
    }


    @Test
    public void test8Simplemulti() {

        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("[kd]*uyir");
        System.out.println(set1);
        Assert.assertEquals(24, set1.size());
    }

    @Test
    public void test9Simplemulti() {

        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("[k,d]*[A]");
        System.out.println(set1);
        Assert.assertEquals(2, set1.size());
    }

    @Test
    public void test9allathu() {

        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("உயிர் allathu மெய்");
        System.out.println(set1);
        Assert.assertEquals(30, set1.size());
    }

    @Test
    public void test9allathu1() {

        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("உயிர் அல்லது மெய்");
        System.out.println(set1);
        Assert.assertEquals(30, set1.size());
    }

    // @Test
    public void testWithGUI() throws Exception {
        List<String> list = Arrays.asList(TamilLetterSetParser.ruleNames);

        TamilLetterSetParser parser = TamilEzhuththuSetExpressionInterpreter.createTamilLetterSetParser("uyir+mey-uyirmey allathu mey");//(உயிர்-(மெய்-(உயிர்+உயிர்)))*உயிர்-(மெய்-(உயிர்+உயிர்))-[அவாயீ,ஊஉஈஅ]+உயிர்-(மெய்-(உயிர்+உயிர்))-[அவாயீ,ஊஉஈஅ]");

        TreeViewer viewer = new TreeViewer(list, parser.expr());
        viewer.setTextColor(Color.LIGHT_GRAY);
        viewer.setBoxColor(Color.WHITE);
        JDialog dialog = new JDialog();
        Container contentPane = dialog.getContentPane();
        contentPane.add(viewer);
        contentPane.setBackground(Color.white);
        dialog.pack();
        dialog.setLocationRelativeTo((Component) null);
        dialog.dispose();

        Rectangle rect1 = viewer.getBounds();
        BufferedImage image1 = new BufferedImage(rect1.width, rect1.height, 1);
        Graphics2D g1 = (Graphics2D) image1.getGraphics();
        g1.setColor(Color.WHITE);
        g1.fill(rect1);
        viewer.paint(g1);


//        viewer.setArcSize(0);
//        viewer.setTreeTextProvider(new TreeViewer.DefaultTreeTextProvider(list) {
//            public String getText(Tree tree) {
//                String text = super.getText(tree);
//                if ("expression".equals(text)) {
//                    return text + "-" +TamilFactory.getTransliterator(null).transliterate("koavai").toString();
//                }
//                if ("term".equals(text)) {
//                    return text + "-" + TamilFactory.getTransliterator(null).transliterate("seyali").toString();
//                }
//                return text;
//            }
//        });


        viewer.printAll(g1);
        ImageIO.write(image1, "PNG", new FileOutputStream("/Users/velsubra/Downloads/expression.png"));


    }

}


