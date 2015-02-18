package test.simple;

import junit.framework.Assert;
import my.interest.lang.tamil.TamilUtils;
import tamil.util.PathBuilder;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class PathBuilderTest {



    @Test
    public void test00Simple() throws Exception {
        PathBuilder<String> builder = new PathBuilder<String>();
        builder.includeNewPathFromRoot(Arrays.asList(new String[]{"a", "b",}));
        builder.includeNewPathFromRoot(Arrays.asList(new String[]{"c","d"}));
        builder.appendNodesToAllPaths(Arrays.asList(new String[]{"e","f", "g"}));
        List<List<String>>  paths = builder.getPaths();
        System.out.println(paths);
        Assert.assertEquals("[[a, b, e], [c, d, e], [a, b, f], [c, d, f], [a, b, g], [c, d, g]]", paths.toString());
    }

    @Test
    public void test0Simple() throws Exception {
        PathBuilder<String> builder = new PathBuilder<String>();
        builder.appendNodesToAllPaths(Arrays.asList(new String[]{"நிரை", "மலர்", "கலாம்", "அடி"}));
        builder.appendNodesToAllPaths(Arrays.asList(new String[]{"நேர்", "மா", "அ", "மண்"}));
        List<List<String>>  paths = builder.getPaths();
        System.out.println(paths);
        Assert.assertEquals("[[நிரை, நேர்], [மலர், நேர்], [கலாம், நேர்], [அடி, நேர்], [நிரை, மா], [மலர், மா], [கலாம், மா], [அடி, மா], [நிரை, அ], [மலர், அ], [கலாம், அ], [அடி, அ], [நிரை, மண்], [மலர், மண்], [கலாம், மண்], [அடி, மண்]]", paths.toString());
    }

    @Test
    public void test1Simple() throws Exception {
        PathBuilder<String> builder = new PathBuilder<String>();
        builder.appendNodeToAllPaths("a");
         List<List<String>>  paths = builder.getPaths();
        System.out.println(paths);
        Assert.assertEquals("[[a]]", paths.toString());
    }

    @Test
    public void test2Simple() throws Exception {
        PathBuilder<String> builder = new PathBuilder<String>();
        builder.appendNodeToAllPaths("a");
        builder.appendNodeToAllPaths("b");
        List<List<String>>  paths = builder.getPaths();
        System.out.println(paths);
        Assert.assertEquals("[[a, b]]", paths.toString());
    }

    @Test
    public void test3Simple() throws Exception {
        PathBuilder<String> builder = new PathBuilder<String>();
        builder.appendNodeToAllPaths("a");
        builder.appendNodesToAllPaths(TamilUtils.parseString("b,c,d"));
        List<List<String>>  paths = builder.getPaths();
        System.out.println(paths);
        Assert.assertEquals("[[a, b], [a, c], [a, d]]", paths.toString());
    }

    @Test
    public void test4Simple() throws Exception {
        PathBuilder<String> builder = new PathBuilder<String>();

        builder.appendNodesToAllPaths(TamilUtils.parseString("b,c,d"));
        builder.appendNodeToAllPaths("a");
        List<List<String>>  paths = builder.getPaths();
        System.out.println(paths);
        Assert.assertEquals("[[b, a], [c, a], [d, a]]", paths.toString());
    }

    @Test
    public void test5Simple() throws Exception {
        PathBuilder<String> builder = new PathBuilder<String>();

        builder.appendNodesToAllPaths(TamilUtils.parseString("b,c,d"));
        builder.appendNodesToAllPaths(TamilUtils.parseString("d,e"));
        List<List<String>>  paths = builder.getPaths();
        System.out.println(paths);
        Assert.assertEquals("[[b, d], [c, d], [d, d], [b, e], [c, e], [d, e]]", paths.toString());
    }

    @Test
    public void test6Simple() throws Exception {
        PathBuilder<String> builder = new PathBuilder<String>();

        builder.appendNodesToAllPaths(TamilUtils.parseString("b,c,d"));
        builder.appendNodesToAllPaths(TamilUtils.parseString("d,e"));
        builder.appendNodesToAllPaths(TamilUtils.parseString("f"));
        List<List<String>>  paths = builder.getPaths();
        System.out.println(paths);
        Assert.assertEquals("[[b, d, f], [c, d, f], [d, d, f], [b, e, f], [c, e, f], [d, e, f]]", paths.toString());
    }

    @Test
    public void test7Simple() throws Exception {
        PathBuilder<String> builder = new PathBuilder<String>();

        builder.appendNodesToAllPaths(TamilUtils.parseString("b,c,d"));
        builder.appendNodesToAllPaths(TamilUtils.parseString("d,e"));
        builder.appendNodesToAllPaths(TamilUtils.parseString("f,g"));
        builder.appendNodesToAllPaths(TamilUtils.parseString("h"));
        builder.appendNodesToAllPaths(TamilUtils.parseString("b,a"));
        List<List<String>>  paths = builder.getPaths();
        System.out.println(paths);
        Assert.assertEquals("[[b, d, f, h, b], [c, d, f, h, b], [d, d, f, h, b], [b, e, f, h, b], [c, e, f, h, b], [d, e, f, h, b], [b, d, g, h, b], [c, d, g, h, b], [d, d, g, h, b], [b, e, g, h, b], [c, e, g, h, b], [d, e, g, h, b], [b, d, f, h, a], [c, d, f, h, a], [d, d, f, h, a], [b, e, f, h, a], [c, e, f, h, a], [d, e, f, h, a], [b, d, g, h, a], [c, d, g, h, a], [d, d, g, h, a], [b, e, g, h, a], [c, e, g, h, a], [d, e, g, h, a]]", paths.toString());
    }
}
