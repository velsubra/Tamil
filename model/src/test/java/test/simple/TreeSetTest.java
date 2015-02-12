package test.simple;

import tamil.lang.TamilWord;
import my.interest.lang.tamil.internal.api.DefinitionFactory;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import my.interest.lang.tamil.generated.types.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TreeSetTest {


    static SortedSet<String> set = Collections.synchronizedSortedSet(new TreeSet<String>());

    static void add(GenericTenseTable table) {
        if (table == null) return;
        for (TableRow row : table.getRows()) {
            add(row.getPresent());
            add(row.getPast());
            add(row.getFuture());
        }
    }

    static void add(DerivedValues dvalues) {
        if (dvalues == null) return;
        for (DerivedValue vale : dvalues.getList()) {
            set.add(vale.getValue());
        }
    }

    @Test
    public void testStarts() throws Exception {
        TamilWord start = TamilWord.from("கூடன்க்ள்ம்");
        TamilWord actual = TamilWord.from("கூடன்குளம்");
      Assert.assertTrue(actual.startsWith(start, false));

    }


    @Test
    public void test1() throws Exception {
       if (true) return;
        Date start = new Date();
        int count = 0;
        for (RootVerbDescription root : PersistenceInterface.get().getAllRootWords().getVinai().getVerbs().getList().getVerb()) {
           if (count == 10) break;
            set.add(root.getRoot());
            System.out.print(count++ +":" + root.getRoot()+":");
            GenericTenseTable table = DefinitionFactory.generateVinaimuttu("vinaimuttu", root.getRoot(), true);
            add(table);

            table = DefinitionFactory.generateVinaimuttu("vinaimuttu", root.getRoot(), false);
            add(table);

            table = DefinitionFactory.generateVinaimuttu("thodar-muttu-vinaimuttu", root.getRoot(), true);
            add(table);

            table = DefinitionFactory.generateVinaimuttu("thodar-muttu-vinaimuttu", root.getRoot(), false);
            add(table);

            table = DefinitionFactory.generateVinaimuttu("muttu-vinaimuttu", root.getRoot(), true);
            add(table);

            table = DefinitionFactory.generateVinaimuttu("muttu-vinaimuttu", root.getRoot(), false);
            add(table);



            table = DefinitionFactory.generateVinaiyecham(root.getRoot(), true);
            add(table);

            table = DefinitionFactory.generateVinaiyecham(root.getRoot(), false);
            add(table);


            table = DefinitionFactory.generatePeyarechcham(root.getRoot(), true);
            add(table);

            table = DefinitionFactory.generatePeyarechcham(root.getRoot(), false);
            add(table);



            table = DefinitionFactory.generateVinaiyaalanaiyumPeyar(root.getRoot(), true, false);
            add(table);

            table = DefinitionFactory.generateVinaiyaalanaiyumPeyar(root.getRoot(), false,false);
            add(table);


            System.out.println(set.size());

        }

        //System.out.println(set);
        System.out.println(set.size());



    }
}
