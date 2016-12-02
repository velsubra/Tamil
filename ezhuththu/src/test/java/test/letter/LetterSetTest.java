package test.letter;

import junit.framework.Assert;
import org.junit.Test;
import tamil.lang
        .TamilCharacter;
import tamil.lang.TamilFactory;

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
        Assert.assertEquals(247,set1.size());
    }

    @Test
    public void test1SimpleAdd() {
        //   Set<TamilCharacter> set  = TamilFactory.getTamilCharacterSetCalculator().find("எழுத்து");
        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("உயிர்+மெய்");
        System.out.println(set1);
        Assert.assertEquals(30,set1.size());
    }

    @Test
    public void test2SimpleAdd() {
        //   Set<TamilCharacter> set  = TamilFactory.getTamilCharacterSetCalculator().find("எழுத்து");
        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("மெய்+!மெய்");
        System.out.println(set1);
        Assert.assertEquals(247,set1.size());
    }

    @Test
    public void test2SimpleIntersection() {
        //   Set<TamilCharacter> set  = TamilFactory.getTamilCharacterSetCalculator().find("எழுத்து");
        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("உயிர்&மெய்");
        System.out.println(set1);
        Assert.assertEquals(0,set1.size());
    }

    @Test
    public void test3SimpleIntersection() {

        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("(uyir|(mey&uyir))");
        System.out.println(set1);
        Assert.assertEquals(12,set1.size());
    }

    @Test
    public void test4SimpleIntersection() {

        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("(uyir|mey)-uyir");
        System.out.println(set1);
        Assert.assertEquals(18,set1.size());
    }

    @Test
    public void test5SimpleIntersection() {

        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("!(uyir|mey)");
        System.out.println(set1);
        Assert.assertEquals(217,set1.size());
    }

    @Test
    public void test6SimpleIntersection() {

        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("!uyir|mey");
        System.out.println(set1);
        Assert.assertEquals(235,set1.size());
    }

    @Test
    public void test6ConstantNegation() {

        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("![a]");
        System.out.println(set1);
        Assert.assertEquals(246,set1.size());
    }

    @Test
    public void test7Constant() {

        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("[a,mmaa]+mey");
        System.out.println(set1);
        Assert.assertEquals(20,set1.size());
    }


    @Test
    public void test7Constant1() {

        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("[]+mey");
        System.out.println(set1);
        Assert.assertEquals(18,set1.size());
    }


    @Test
    public void test7Simplemulti() {

        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("mey*uyir");
        System.out.println(set1);
        Assert.assertEquals(216,set1.size());
    }

    @Test
    public void test8Simplemulti() {

        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("[kd]*uyir");
        System.out.println(set1);
        Assert.assertEquals(24,set1.size());
    }

    @Test
    public void test9Simplemulti() {

        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("[k,d]*[A]");
        System.out.println(set1);
        Assert.assertEquals(2,set1.size());
    }

    @Test
    public void test9allathu() {

        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("உயிர் allathu மெய்");
        System.out.println(set1);
        Assert.assertEquals(30,set1.size());
    }

    @Test
    public void test9allathu1() {

        Set<TamilCharacter> set1 = TamilFactory.getTamilCharacterSetCalculator().evaluate("உயிர் அல்லது மெய்");
        System.out.println(set1);
        Assert.assertEquals(30,set1.size());
    }

}


