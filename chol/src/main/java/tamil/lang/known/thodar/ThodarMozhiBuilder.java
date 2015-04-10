package tamil.lang.known.thodar;

import tamil.lang.known.derived.PanhpupPeyarththiribu;
import tamil.util.PathBuilder;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.IPeyarchchol;
import tamil.lang.known.non.derived.Ottu;
import tamil.lang.known.non.derived.Vinaiyadi;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     Builds Thodarmozhi
 * </p>
 *
 * @author velsubra
 */
public final class ThodarMozhiBuilder extends PathBuilder<IKnownWord> {

    //[class tamil.common.lang.lang.known.derived.PanhpupPeyarthThiribu,
    // class tamil.common.lang.lang.known.derived.KurrippupPeyarechcham,
    // class tamil.common.lang.lang.known.non.derived.Peyarchchol,
    // class tamil.common.lang.lang.known.derived.Kaddalhai,
    // class tamil.common.lang.lang.known.derived.VinaiMuttu,
    // class tamil.common.lang.lang.known.derived.Peyarechcham,
    // class tamil.common.lang.lang.known.non.derived.NonStartingIdaichchol,
    // class tamil.common.lang.lang.known.non.derived.AtomicIsolatedIdai,
    // class tamil.common.lang.lang.known.derived.Vinaiyechcham,
    // class tamil.common.lang.lang.known.non.derived.Kalh,
    // class tamil.common.lang.lang.known.non.derived.Ottu,
    // class tamil.common.lang.lang.known.derived.ThozhirrPeyar,
    // class tamil.common.lang.lang.known.derived.EthirmarraipPeyarechcham,
    // class tamil.common.lang.lang.known.non.derived.NonEndingIdaichChol,
    // class tamil.common.lang.lang.known.derived.EthirmarraiVinaiyaalanhaiyumPeyar,
    // class tamil.common.lang.lang.known.non.derived.Vinaiyadi,
    // class tamil.common.lang.lang.known.derived.PeyarchCholThiribu,
    // class tamil.common.lang.lang.known.derived.EthirmarraithozhirPeyar,
    // class tamil.common.lang.lang.known.derived.KurrippupVinaiyarechcham,
    // class tamil.common.lang.lang.known.derived.VinaiyaalanhaiyumPeyar]


    public static List<IThodarMozhi.TYPE_SIG> listTypes(IKnownWord known) {
        List<IThodarMozhi.TYPE_SIG> list = new ArrayList<IThodarMozhi.TYPE_SIG>();
        if (IThodarMozhi.class.isAssignableFrom(known.getClass())) {
            IThodarMozhi thodar = (IThodarMozhi) known;

            return listTypes(thodar.getWords().get(thodar.getWords().size() - 1));
        }
        if (PanhpupPeyarththiribu.class.isAssignableFrom(known.getClass())) {
            list.add(IThodarMozhi.TYPE_SIG.PT);
            return list;
        }

        if (Ottu.class.isAssignableFrom(known.getClass())) {
            list.add(IThodarMozhi.TYPE_SIG.O);
            return list;
        }

        if (IPeyarchchol.class.isAssignableFrom(known.getClass())) {
            list.add(IThodarMozhi.TYPE_SIG.N);
        }

        if (Vinaiyadi.class.isAssignableFrom(known.getClass())) {
            list.add(IThodarMozhi.TYPE_SIG.RV);
        }

        if (Vinaiyadi.class.isAssignableFrom(known.getClass())) {
            list.add(IThodarMozhi.TYPE_SIG.RV);
        }
        if (list.isEmpty()) {
            list.add(IThodarMozhi.TYPE_SIG.UN);
        }
        return list;

    }

    @Override
    public void appendNodesToAllPaths(List<IKnownWord> nodes) {
        super.appendNodesToAllPaths(nodes);
        List<List<IKnownWord>> newpaths = new ArrayList<List<IKnownWord>>();
        List<List<IKnownWord>> outerlist = getPaths();

        for (List<IKnownWord> list : outerlist) {
            boolean ignore = false;
            StringBuffer expression = new StringBuffer("");
            // boolean first = true;
            for (IKnownWord wrap : list) {


                List<IThodarMozhi.TYPE_SIG> types = listTypes(wrap);
                if (!types.isEmpty()) {
                    expression.append("(");
                    boolean innerfirst = true;
                    for (IThodarMozhi.TYPE_SIG sig : types) {
                        expression.append(sig);
                        if (!innerfirst) {
                            expression.append("|");
                        }
                        innerfirst = false;
                    }
                    expression.append(")");
                }


            }
            if (!ignore) {
                newpaths.add(list);
            }
        }
        this.paths = newpaths;

    }
}
