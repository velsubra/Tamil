package my.interest.lang.tamil.punar.handler.verrrrumai;

import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.handler.AbstractPunarchiHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class AllVHandler extends AbstractPunarchiHandler {
    static final List<AbstractVearrrrumaiHandler> all = new ArrayList<AbstractVearrrrumaiHandler>();

    public  static  final AllVHandler HANDLER = new AllVHandler();

    static {
        all.add(new V2Handler());
        all.add(new V3Handler());
        all.add(new V31Handler());
        all.add(new V32Handler());
        all.add(new V33Handler());
        all.add(new V4Handler());
        all.add(new V5Handler());
        all.add(new V6Handler());
        all.add(new V61Handler());
        all.add(new V7Handler());

    }

    @Override
    public String getName() {
        return "வேற்றுமை";
    }

    @Override
    public TamilWordPartContainer join(TamilWordPartContainer nilai, TamilWordPartContainer varum) {
            for (AbstractVearrrrumaiHandler v : all) {
            TamilWordPartContainer r = v.join(nilai, varum);
            if (r != null) {
                return r;
            }
        }
        return null;
    }
}
