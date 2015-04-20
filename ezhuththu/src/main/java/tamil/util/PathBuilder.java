package tamil.util;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Simple Path builder interface that can be used to build paths. A path is just a List of nodes of type T.
 * A path builder can have a list of paths already. The list will be empty initially.
 * Now, one can add list of new possible nodes that the existing paths can transition to. Please see {@link #appendNodesToAllPaths(java.util.List)}.
 * <p/>
 * <pre>
 *     Assume [a,b] and [c,d]  are two existing paths. Also assume [e,f,g]  is a set of nodes each of which is individually reachable from the previous paths.
 *     Now adding  [e,f,g] into the path builder will end up having 6 paths.
 *
 *    [a, b, e], [c, d, e], [a, b, f], [c, d, f], [a, b, g], [c, d, g]
 * </pre>
 * Please note that the number of nodes in each path may not be same as the method {@link #includeNewPathFromRoot(java.util.List)}  can add a new path with different size.
 * <p/>
 * <p/>
 * </p>
 *
 * @param <T> the node type
 * @author velsubra
 */
public class PathBuilder<T> {


    protected List<List<T>> paths = new ArrayList<List<T>>();

    private List<List<T>> dupMe() {
        List<List<T>> dups = new ArrayList<List<T>>();
        for (List<T> sub : paths) {
            List<T> subd = new ArrayList<T>();
            dups.add(subd);
            for (T n : sub) {
                subd.add(n);
            }
        }
        return dups;
    }

    /**
     * Appends a new node.  Number of paths wont be changed as one node is added.
     *
     * @param t the node
     */
    public void appendNodeToAllPaths(T t) {
        List<T> list = new ArrayList<T>();
        list.add(t);
        multiplyPathsWithNodes(list);
    }

    /**
     * Inserts a new Path at the root. Any more appending using {@link #appendNodesToAllPaths(java.util.List)} will add nodes into this path as well.
     *
     * @param aPath the new path at the root.
     */
    public void includeNewPathFromRoot(List<T> aPath) {
        if (aPath.size() == 0) return;
        paths.add(new ArrayList<T>(aPath));
    }


    /**
     * This will just lengthen each path with nodes. Number of paths wont changes
     * @param nodes  the series of nodes that will go onto the existing paths.
     */
    public void addIntoExistingPaths(final List<T> nodes) {
        for (T t : nodes) {
            appendNodeToAllPaths(t);
        }
    }


    /**
     * Appends each node from nodes to all the existing paths.
     * The number of paths in the path builder following this method call will be previous number of paths * size of nodes.
     *
     * @param nodes the set of nodes each of which has to be added to the existing path.  The list should typically contain   unique number of nodes.
     */
    public void multiplyPathsWithNodes(final List<T> nodes) {
        if (nodes.size() == 0) return;
        if (paths.isEmpty()) {
            for (T node : nodes) {
                List<T> nodeList = new ArrayList<T>();
                paths.add(nodeList);
                nodeList.add(node);

            }
            return;
        }
        int pathSize = paths.size();
        //Duplicate list entries
        if (nodes.size() > 1) {

            List<List<T>> dups = new ArrayList<List<T>>();
            for (int i = 0; i < nodes.size() - 1; i++) {
                dups.addAll(dupMe());
            }
            paths.addAll(dups);
        }


        for (int i = 0; i < paths.size(); i++) {
            int chunk = i / pathSize;
            T atChunk = nodes.get(chunk);
            paths.get(i).add(atChunk);
        }
    }

    @Deprecated
    /**
     * This name of the method confuses.!
     * Please use {@link #multiplyPathsWithNodes}
     */
    public void appendNodesToAllPaths(final List<T> nodes) {

         multiplyPathsWithNodes(nodes);
    }


    /**
     * Returns the list of paths.
     *
     * @return the list
     */
    public List<List<T>> getPaths() {
        return paths;
    }


}
