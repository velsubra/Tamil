package my.interest.lang.tamil.punar;

import my.interest.lang.tamil.internal.api.HandlerFactory;
import my.interest.lang.tamil.punar.thodar.DirectWordBuilder;
import my.interest.lang.tamil.punar.thodar.NounVearrrrumaiththogaiBuilder;
import my.interest.lang.tamil.punar.thodar.VinaiththogaiBuilder;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.known.IKnownWord;

import java.util.*;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class ThodarMozhiBuilders {
    public List<ThodarMozhiBuilder> getList() {
        return list;
    }

    public static ThodarMozhiBuilders createNewBuilder() {
        ThodarMozhiBuilders builders = new ThodarMozhiBuilders();
        builders.rootContext = true;
        builders.list.add(new NounVearrrrumaiththogaiBuilder());
        builders.list.add(new VinaiththogaiBuilder());
        builders.list.add(new DirectWordBuilder());
        return builders;
    }

    private ThodarMozhiBuilders() {
    }

    List<ThodarMozhiBuilder> list = new LinkedList<ThodarMozhiBuilder>();
    private boolean rootContext = false;

    public boolean isEmpty() {
        return list.isEmpty();

    }

    public ThodarMozhiBuilders build(TamilWordPartContainer container) {
        Map<String, List<IKnownWord>> cache = new HashMap<String, List<IKnownWord>>();
        Set<String> dontparse = new HashSet<String>();
        Set<String> unknowns = new HashSet<String>();
        //Map<String, ThodarMozhiBuilders> cachedContext = new HashMap<String, ThodarMozhiBuilders>();
        ThodarMozhiBuilders done = add(container, cache, unknowns, dontparse);

//        System.out.println("Dont parse:" + dontparse);
//        System.out.println("Cache:" + cache.keySet());
//        System.out.println("Unknowns:" + unknowns);
        ThodarMozhiBuilders ret = new ThodarMozhiBuilders();
        for (ThodarMozhiBuilder b : done.getList()) {
            if (b.isAtLogicalCompletion()) {
                ret.list.add(b);
            }
        }

        return ret;


    }

    private ThodarMozhiBuilders add(TamilWordPartContainer container, Map<String, List<IKnownWord>> cache, Set<String> unknowns, Set<String> dontparse) {

        String word = container.getWord().toString();
        ThodarMozhiBuilders ret = new ThodarMozhiBuilders();


        if (dontparse.contains(word)) return ret;

        List<IKnownWord> knowns = cache.get(word);
        if (knowns == null) {
            knowns = unknowns.contains(word) ? null : TamilFactory.getSystemDictionary().search(TamilWord.from(word), true, 1, null);
        }

        if (knowns == null || knowns.isEmpty()) {
            unknowns.add(word);
            Set<TamilWordSplitResult> fulllist = new HashSet<TamilWordSplitResult>(HandlerFactory.split(container));
            int count = 0;
            for (TamilWordSplitResult result : fulllist) {
                ThodarMozhiBuilders sibling = (count == result.size() - 1) ? this : cloneContext();
                count++;
                for (TamilWordPartContainer next : result) {
                    sibling = sibling.add(next, cache, unknowns, dontparse);
                    if (sibling.isEmpty()) {

                        break;
                    }
                }
                if (!sibling.isEmpty()) {
                    ret.add(sibling);
//                   if (this.rootContext) {
//                       System.out.println(" Ok..");
//                   }
                    //    break;

                }
            }

            if (ret.isEmpty()) {
                dontparse.add(word);
            }


        } else {
            cache.put(word, knowns);
            for (IKnownWord k : knowns) {
                for (ThodarMozhiBuilder builder : list) {
                    ThodarMozhiBuilder accepted = builder.accept(k, knowns.size() > 1);
                    if (accepted != null) {

                        ret.list.add(accepted);

                    }
                }

            }
        }
        //  cachedContext.put(word,ret.cloneContext());

        return ret;

    }

    private ThodarMozhiBuilders cloneContext() {
        //System.out.println("Cloning size:"+ list.size());
        ThodarMozhiBuilders builders = new ThodarMozhiBuilders();
        for (ThodarMozhiBuilder b : list) {
            builders.list.add(b.cloneContext());
        }
        return builders;
    }

    private void add(ThodarMozhiBuilders sibling) {
        list.addAll(sibling.list);
    }

}
