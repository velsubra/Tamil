package test.app;

import junit.framework.Assert;
import my.interest.lang.tamil.generated.types.*;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import my.interest.lang.tamil.xml.AppCache;
import org.junit.Test;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class InheritanceTest {

    @Test
    public void testInheritance() {
        TamilRootWords all = new TamilRootWords();
        all.setApps(new Apps());
        all.getApps().setApps(new AppsDescription());
        all.getApps().getApps().setList(new AppsDescriptionList());





        AppDescription one = new AppDescription();
        one.setName("one");
        one.setCache(new AppCache());
        one.setResources(new AppResources());
        AppResource one1 = new AppResource();
        one1.setName("one1");
        one.getResources().getResources().add(one1);
        all.getApps().getApps().getList().getApp().add(one);

        AppResource one2 = new AppResource();
        one2.setName("one2");
        one.getResources().getResources().add(one2);

        AppResource same1 = new AppResource();
        same1.setName("same");
        one.getResources().getResources().add(same1);


        AppDescription two = new AppDescription();
        two.setName("two");
        two.setCache(new AppCache());
        two.setResources(new AppResources());
        AppResource two1 = new AppResource();
        two1.setName("two1");
        two.getResources().getResources().add(two1);
        all.getApps().getApps().getList().getApp().add(two);

        AppResource two2 = new AppResource();
        two2.setName("two2");
        two.getResources().getResources().add(two2);

        AppResource same2 = new AppResource();
        same2.setName("same");
        two.getResources().getResources().add(same2);


        AppDescription three = new AppDescription();
        three.setName("three");
        three.setCache(new AppCache());
        three.setResources(new AppResources());
        AppResource three1 = new AppResource();
        three1.setName("three1");
        three.getResources().getResources().add(three1);
        all.getApps().getApps().getList().getApp().add(three);

        AppResource three2 = new AppResource();
        three2.setName("three2");
        three.getResources().getResources().add(three2);

        AppResource three3 = new AppResource();
        three3.setName("three3");
        three.getResources().getResources().add(three3);


        AppResource r = PersistenceInterface.findAppResource(all, one.getName(), one1.getName(), true);
        Assert.assertEquals(one1, r);
        r = PersistenceInterface.findAppResource(all, one.getName(), one2.getName(), true);
        Assert.assertEquals(one2, r);

        r = PersistenceInterface.findAppResource(all, two.getName(), one2.getName(), true);
        Assert.assertEquals(null, r);

        clearCache(all);


        //Setting two to inherit one

        two.setResourceInheritance(new ResourceInheritance());
        two.getResourceInheritance().getParentApps().add(one.getName());
        two.getResourceInheritance().setInheritanceOrder(ResourceInheritanceOrder.DEPTH_FIRST_PRE_ORDER);  //does not matter for now

        r = PersistenceInterface.findAppResource(all, two.getName(), one2.getName(),true);
        Assert.assertEquals(PersistenceInterface.findAppResource(all, one.getName(), one2.getName(), true), r);

        r = PersistenceInterface.findAppResource(all, two.getName(), three1.getName(),true);
        Assert.assertEquals(null, r);

       // setting one to inherit three
        one.setResourceInheritance(new ResourceInheritance());
        one.getResourceInheritance().getParentApps().add(three.getName());




        clearCache(all);
        r = PersistenceInterface.findAppResource(all, two.getName(), three1.getName(), true);
        Assert.assertEquals(PersistenceInterface.findAppResource(all, three.getName(), three1.getName(), true), r);
        clearCache(all);

        // setting three to inherit one
        three.setResourceInheritance(new ResourceInheritance());
        three.getResourceInheritance().getParentApps().add(three.getName());

        r = PersistenceInterface.findAppResource(all, three.getName(), three1.getName(), true);
        Assert.assertEquals(PersistenceInterface.findAppResource(all, two.getName(), three1.getName(), true), r);



        clearInheritance(all);


        AppDescription four = new AppDescription();
        four.setName("four");
        four.setCache(new AppCache());
        four.setResources(new AppResources());
        AppResource four1 = new AppResource();
        four1.setName("four1");
        four.getResources().getResources().add(four1);
        all.getApps().getApps().getList().getApp().add(four);

        AppResource four2 = new AppResource();
        four2.setName("four2");
        four.getResources().getResources().add(four2);

        r = PersistenceInterface.findAppResource(all, four.getName(), same1.getName(), true);
        Assert.assertEquals(null, r);  // same is not available through 4



        //  4     ie) 4 inherits 3 and 2; 3 inherits 1 ; 1 and 2 have "same" resource
        //  3 , 2
        //  1


        // setting four to inherit three
        four.setResourceInheritance(new ResourceInheritance());
        four.getResourceInheritance().getParentApps().add(three.getName());
        four.getResourceInheritance().setInheritanceOrder(ResourceInheritanceOrder.DEPTH_FIRST_PRE_ORDER);
        r = PersistenceInterface.findAppResource(all, four.getName(), same1.getName(), true);
        Assert.assertEquals(null, r);  // same is Still not available through 4


        // setting four to inherit two as well
        four.getResourceInheritance().getParentApps().add(two.getName());
        r = PersistenceInterface.findAppResource(all, four.getName(), same1.getName(), true);
        Assert.assertEquals(null, r);  // same is Still not available through 4 as the cache is not cleared

        clearCache(all);
        r = PersistenceInterface.findAppResource(all, four.getName(), same1.getName(), true);
        Assert.assertEquals(PersistenceInterface.findAppResource(all, two.getName(), same2.getName(), true), r);

        clearCache(all);

        //setting three to inherit one

        three.setResourceInheritance(new ResourceInheritance());
        three.getResourceInheritance().getParentApps().add(one.getName());

        clearCache(all);
        four.getResourceInheritance().setInheritanceOrder(ResourceInheritanceOrder.BREADTH_FIRST);

        r = PersistenceInterface.findAppResource(all, four.getName(), same1.getName(), true);
        Assert.assertEquals(PersistenceInterface.findAppResource(all, two.getName(), same2.getName(), true), r);


        clearCache(all);
        four.getResourceInheritance().setInheritanceOrder(ResourceInheritanceOrder.DEPTH_FIRST_PRE_ORDER);

        // in case of depth first, it should resolve from app one.
        r = PersistenceInterface.findAppResource(all, four.getName(), same1.getName(), true);
        Assert.assertEquals(PersistenceInterface.findAppResource(all, one.getName(), same2.getName(), true), r);

    }

    private static void clearCache(TamilRootWords all) {
        for (AppDescription a : all.getApps().getApps().getList().getApp()) {
            a.getCache().getInheritanceList().clear();
        }
    }

    private static void clearInheritance(TamilRootWords all) {
        for (AppDescription a : all.getApps().getApps().getList().getApp()) {
            if ( a.getResourceInheritance()!= null) {
                a.getResourceInheritance().getParentApps().clear();
                a.getResourceInheritance().setInheritanceOrder(null);
            }
        }

        clearCache(all);
    }
}
