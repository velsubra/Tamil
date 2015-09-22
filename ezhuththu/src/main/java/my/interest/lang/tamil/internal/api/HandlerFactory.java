package my.interest.lang.tamil.internal.api;

import my.interest.lang.tamil.punar.handler.verrrrumai.VAllHandler;
import tamil.lang.TamilWord;
import my.interest.lang.tamil.bean.*;
import my.interest.lang.tamil.punar.AllTamilWordSplitResult;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.TamilWordSplitResult;
import my.interest.lang.tamil.punar.handler.AbstractPunarchiHandler;
import my.interest.lang.tamil.punar.handler.idai.KalhViguthiHandler;
import my.interest.lang.tamil.punar.handler.idai.KurilNedilHandler;
import my.interest.lang.tamil.punar.handler.iyalbu.IyalbuPunarchiHandler;
import my.interest.lang.tamil.punar.handler.iyalbu.JustAddHandler;
import my.interest.lang.tamil.punar.handler.magaraveeru.NannolHandler219;
import my.interest.lang.tamil.punar.handler.lagaraveeru.NannolHandler227;
import my.interest.lang.tamil.punar.handler.nannool.*;
import my.interest.lang.tamil.punar.handler.palapala.NannoolHandler170_Pala;
import my.interest.lang.tamil.punar.handler.thannotru.ThannotruIrattiththalHandler;
import my.interest.lang.tamil.punar.handler.udambadu.UadambaduMeiHandler;
import my.interest.lang.tamil.punar.handler.uyirvarin.UyirvarinUkkuralMeiVittodumHandler;


import java.util.*;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class HandlerFactory {

    public static AbstractPunarchiHandler findHandlerByName(String name) {
        for (AbstractPunarchiHandler h : getAllHandlers()) {
            if (name.equals(h.getName())) {
                return h;
            }
        }
        return null;
    }

    public static List<? extends AbstractPunarchiHandler> getAllHandlers() {
        List<AbstractPunarchiHandler> list = new ArrayList<AbstractPunarchiHandler>();
        list.add(new IyalbuPunarchiHandler());

        list.add(new NannoolHandler164_2());
        list.add(new NannoolHandler158_1());
        list.add(new NannoolHandler158_2());
        list.add(new NannoolHandler158_3_1());
        list.add(new NannoolHandler158_3_2());
        list.add(new NannoolHandler163_1());
        list.add(new NannoolHandler163_2());
        list.add(ThannotruIrattiththalHandler.HANDLER);
        list.add(UadambaduMeiHandler.HANDLER);
        list.add(UyirvarinUkkuralMeiVittodumHandler.HANDLER);
        list.add(NannoolHandler165.HANDLER);
        list.add(new NannoolHandler170_Pala());
        list.add(NannolHandler219.HANDLER);
        list.add(NannoolHandler237.HANDLER);
        list.add(JustAddHandler.HANDLER);
        list.add(KurilNedilHandler.HANDLER);
        list.add(KalhViguthiHandler.HANDLER);
        list.add(NannolHandler227.HANDLER);
        list.add(NannoolHandler183.HANDLER);
        list.add(VAllHandler.HANDLER);


        return list;
    }

    public static HandlerJoinResult join(String nilai, String varum, AbstractPunarchiHandler handler) {
        TamilWordPartContainer container = handler.join(new TamilWordPartContainer(TamilWord.from(nilai)), new TamilWordPartContainer(TamilWord.from(varum)));
        if (container == null) return null;
        HandlerJoinResult result = new HandlerJoinResult();

        result.setHandlerDescription(handler.getDescription());
        result.setHandlerName(handler.getName());
        result.setResult(container.getWord().toString());
        return result;
    }

    public static FullJoinResult join(String nilai, String varum) {
        FullJoinResult result = new FullJoinResult();
        for (AbstractPunarchiHandler handler : getAllHandlers()) {
            HandlerJoinResult ret = join(nilai, varum, handler);
            if (ret != null) {
                result.add(ret);
            }
        }
        // System.out.print(result);
        return result;

    }

    public static HandlerSplitResult split(String joined, AbstractPunarchiHandler handler) {
        List<TamilWordSplitResult> list = handler.splitAll(new TamilWordPartContainer(TamilWord.from(joined)));
        if (list == null || list.isEmpty()) {
            return null;
        }
        HandlerSplitResult result = new HandlerSplitResult();
        for (TamilWordSplitResult r : list) {
            SimpleSplitResult sr = new SimpleSplitResult();

            for (TamilWordPartContainer con : r) {
                sr.getSplitList().add(con.toString());
            }
            result.getSplitLists().add(sr);
        }

        result.setHandlerDescription(handler.getDescription());
        result.setHandlerName(handler.getName());

        return result;
    }

    public static FullSplitResult split(String joined) {
        FullSplitResult result = new FullSplitResult();
        for (AbstractPunarchiHandler handler : getAllHandlers()) {
            HandlerSplitResult sp = split(joined, handler);
            if (sp != null) {
                result.add(sp);
            }
        }
        // System.out.print(result);
        return result;

    }

    /**
     * Applies all the handlers and returns list of  all possible split results
     * @param container
     * @return
     */
    public static AllTamilWordSplitResult split(TamilWordPartContainer container) {

        AllTamilWordSplitResult result = new AllTamilWordSplitResult();
        splitInto(container, result);
        return result;
    }

    public static void splitInto(TamilWordPartContainer container, AllTamilWordSplitResult list) {

        for (AbstractPunarchiHandler handler : getAllHandlers()) {
            handler.splitAllInto(container, list);
        }

    }


    public static MultipleWordSplitResult parseForChance(String joined, boolean returnonFirstMatch, int max) {
        return parse(joined, returnonFirstMatch, true, max);
    }

    public static MultipleWordSplitResult parse(String joined, int max) {
        return parse(joined, false, false, max);
    }


    public static MultipleWordSplitResult parse(String joined, boolean returnonFirstMatch, final boolean firstOk, int max) {


        SimpleSplitResult result = new SimpleSplitResult();
        result.getSplitList().add(joined);

        List<SimpleSplitResult> ret = result.checkAndsplitToKnown(firstOk, returnonFirstMatch);

        if (ret != null && !returnonFirstMatch) {
//            System.out.println("Returned#" + ret.size());
//            for (int i = 0 ;i < ret.size(); i ++) {
//                System.out.println(ret.get(i));
//            }

            TreeSet<SimpleSplitResult> set = new TreeSet<SimpleSplitResult>(new Comparator<SimpleSplitResult>() {

                @Override
                public int compare(SimpleSplitResult o1, SimpleSplitResult o2) {
                    if (firstOk) {
                        return o1.compareTo(o2);
                    } else {
                        int ret = o2.getSplitList().size() - o1.getSplitList().size();
                        if (ret == 0) {
                            ret = o1.toString().length() - o2.toString().length();
                            if (ret == 0) {
                                return o1.compareTo(o2);
                            }
                        }
                        return ret;
                    }
                }
            });
            set.addAll(ret);

            ret = new ArrayList<SimpleSplitResult>();
            Iterator<SimpleSplitResult> it = set.descendingIterator();
            for (int i = 0; i < max; i++) {
                if (it.hasNext()) {
                    ret.add(0, it.next());
                } else {
                    break;
                }
            }
        }
        MultipleWordSplitResult multi = new MultipleWordSplitResult(result);
        multi.setSplit(ret);
        return multi;

    }


}
