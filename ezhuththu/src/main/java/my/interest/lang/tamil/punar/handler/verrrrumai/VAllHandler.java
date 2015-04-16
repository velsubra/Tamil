package my.interest.lang.tamil.punar.handler.verrrrumai;


import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.handler.AbstractPunarchiHandler;
import tamil.lang.TamilFactory;
import tamil.lang.known.non.derived.IPeyarchchol;
import tamil.lang.known.non.derived.VUrubu;
import tamil.lang.known.thodar.Veattumaiththodar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class VAllHandler extends AbstractPunarchiHandler {
    public static final Map<VUrubu,AbstractVearrrrumaiHandler> all = new HashMap<VUrubu, AbstractVearrrrumaiHandler>();

    public static final VAllHandler HANDLER = new VAllHandler();

    static {



        AbstractVearrrrumaiHandler handler = new V2Handler();
        VUrubu urubu = new VUrubu(handler.getUrubu(), true, handler.getNumber());

        all.put(urubu, handler);

        handler = new V3Handler();
        urubu = new VUrubu(handler.getUrubu(), true, handler.getNumber());

        all.put(urubu, handler);

        handler = new V31Handler();
        urubu = new VUrubu(handler.getUrubu(), true, handler.getNumber());

        all.put(urubu, handler);

        handler = new V32Handler();
        urubu = new VUrubu(handler.getUrubu(), true, handler.getNumber());

        all.put(urubu, handler);

        handler = new V33Handler();
        urubu = new VUrubu(handler.getUrubu(), true, handler.getNumber());
        all.put(urubu, handler);


        handler = new V4Handler();
        urubu = new VUrubu(handler.getUrubu(), true, handler.getNumber());

        all.put(urubu, handler);


        handler = new V5Handler();
        urubu = new VUrubu(handler.getUrubu(), true, handler.getNumber());

        all.put(urubu, handler);

        handler = new V6Handler();
        urubu = new VUrubu(handler.getUrubu(), true, handler.getNumber());

        all.put(urubu, handler);


        handler = new V61Handler();
        urubu = new VUrubu(handler.getUrubu(), true, handler.getNumber());

        all.put(urubu, handler);

        handler = new V7Handler();
        urubu = new VUrubu(handler.getUrubu(), true, handler.getNumber());

        all.put(urubu, handler);


    }

    @Override
    public String getName() {
        return "வேற்றுமை";
    }

    @Override
    public TamilWordPartContainer join(TamilWordPartContainer nilai, TamilWordPartContainer varum) {
        for (AbstractVearrrrumaiHandler v : all.values()) {
            TamilWordPartContainer r = v.join(nilai, varum);
            if (r != null) {
                return r;
            }
        }
        return null;
    }

    public List<Veattumaiththodar> generateAndAdd(IPeyarchchol p) {


        List<Veattumaiththodar> list = new ArrayList<Veattumaiththodar>();

        for (VUrubu u : all.keySet()) {
            AbstractVearrrrumaiHandler v = all.get(u);
            TamilWordPartContainer r = v.join(new TamilWordPartContainer(p.getWord()), new TamilWordPartContainer(u.getWord()));
            if (r != null) {
                Veattumaiththodar thodar = new Veattumaiththodar(r.getWord(),p, u);
                list.add(thodar);

                TamilFactory.getSystemDictionary().add(thodar);
            }
        }
        return list;
    }


}
