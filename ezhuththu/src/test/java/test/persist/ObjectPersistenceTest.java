package test.persist;

import junit.framework.Assert;
import my.interest.lang.tamil.TamilUtils;
import my.interest.lang.tamil.impl.persist.FileBasedPersistenceImpl;
import org.junit.Test;
import tamil.lang.TamilFactory;
import tamil.lang.api.persist.object.ObjectPersistenceInterface;
import tamil.lang.exception.service.ServiceException;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class ObjectPersistenceTest {

    static {
        TamilFactory.init();
    }


    @Test
    public void testList() throws Exception {
        ObjectPersistenceInterface inter = new FileBasedPersistenceImpl("dat");
        List<Long> list = inter.list("data");
        System.out.println(list);
        for (long id : list) {
            inter.delete(id, "data");
        }
        long id = inter.create( "data", "This is sample data".getBytes(TamilUtils.ENCODING));
        id = inter.create( "data", "This is sample data".getBytes(TamilUtils.ENCODING));
        System.out.println("id:" + id);
        String s = new String(inter.get(2, "data"), TamilUtils.ENCODING);
        Assert.assertEquals(s, "This is sample data");
        inter.update(2, "data", "This is sample data1".getBytes(TamilUtils.ENCODING));
         s = new String(inter.get(2, "data"), TamilUtils.ENCODING);
        Assert.assertEquals(s, "This is sample data1");
        try {
            inter.update(3, "data", "This is sample data1".getBytes(TamilUtils.ENCODING));
        } catch (ServiceException se) {

        }

        try {
            inter.get(3, "data");
        } catch (ServiceException se) {

        }

        try {
            inter.delete(3, "data");
        } catch (ServiceException se) {

        }


    }

    @Test
    public void testList1() throws Exception {
        ObjectPersistenceInterface inter = new FileBasedPersistenceImpl("dat");
        List<Long> list = inter.list("a/data/b");
        System.out.println(list);
        for (long id : list) {
            inter.delete(id, "a/data/b");
        }
        long id = inter.create( "a/data/b", "This is sample data".getBytes(TamilUtils.ENCODING));
        List<Long> ret = inter.list("a/data/b");
        Assert.assertEquals("["+id+"]", ret.toString());


    }


}
