package my.interest.lang.tamil.punar.handler;

import tamil.lang.TamilWord;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import my.interest.lang.tamil.punar.handler.iyalbu.IyalbuPunarchiHandler;
import my.interest.lang.tamil.punar.handler.nannool.IdaiNilaiHandler;
import my.interest.lang.tamil.punar.handler.nannool.YechchaSpecialHandler;
import my.interest.lang.tamil.punar.handler.thannotru.ThannotruIrattiththalHandler;
import my.interest.lang.tamil.punar.handler.udambadu.UadambaduMeiHandler;
import my.interest.lang.tamil.punar.handler.uyirvarin.UyirvarinUkkuralMeiVittodumHandler;
import my.interest.lang.tamil.translit.EnglishToTamilCharacterLookUpContext;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class VinaiMutruCreationHandler extends AbstractPunarchiHandler  {
    @Override
    public String getName() {
        return "வினைமுற்றிற்கான விதி";  //To change body of implemented methods use File | Settings | File Templates.
    }

    public VinaiMutruCreationHandler() {
        instancehandlers.addAll(syshandlers);
    }


    protected TamilWord vinaiMutru = new TamilWord();
    private String equation = "";

    public VinaiMutruCreationHandler duplicate() {
        VinaiMutruCreationHandler ret = new VinaiMutruCreationHandler();
        ret.vinaiMutru = vinaiMutru.duplicate();
        ret.equation = equation;
        ret.instancehandlers = instancehandlers;
        return ret;

    }

    public void addInstanceHandler(String cls) {
        try {
            instancehandlers.add((AbstractPunarchiHandler) Class.forName(cls).newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addInstanceHandler(AbstractPunarchiHandler inst) {
        instancehandlers.add(inst);

    }

    public void prependInstanceHandler(AbstractPunarchiHandler inst) {
        instancehandlers.add(0,inst);

    }



    static final List<AbstractPunarchiHandler> syshandlers = new ArrayList<AbstractPunarchiHandler>();

    private List<AbstractPunarchiHandler> instancehandlers = new ArrayList<AbstractPunarchiHandler>();

    static {


        syshandlers.add(YechchaSpecialHandler.HANDLER);
        syshandlers.add(UyirvarinUkkuralMeiVittodumHandler.HANDLER);
        syshandlers.add(ThannotruIrattiththalHandler.HANDLER);
        syshandlers.add(IyalbuPunarchiHandler.HANDLER);
        syshandlers.add(UadambaduMeiHandler.HANDLER);
        syshandlers.add(IdaiNilaiHandler.HANDLER);


    }


    public void add(TamilWord word) {
        add(new TamilWordPartContainer(EnglishToTamilCharacterLookUpContext.getBestMatch(word.toString())));
    }


    public String getEquation() {
        return equation;
    }

    public void insertFirst(TamilWordPartContainer part) {
        if (part.size() == 0) return;
        TamilWordPartContainer reverse = new TamilWordPartContainer(vinaiMutru);
        vinaiMutru = part.getWord();
        add(reverse, true);
    }
    public void add(TamilWordPartContainer part) {
        add(part, false);
    }

    private void add(TamilWordPartContainer part, boolean reversed) {
        if (part.getWord().isEmpty()) {
            return;
        }
        if (vinaiMutru.isEmpty()) {
            vinaiMutru.addAll(part.getWord().duplicate());
            equation = part.toString();
        } else {
            if (reversed) {
                equation =  part.toString() + "+" + equation;
            } else {
                equation += "+" + part.toString();
            }
            boolean applied = false;
            for (AbstractPunarchiHandler handler : instancehandlers) {
                TamilWordPartContainer result = handler.join(new TamilWordPartContainer(vinaiMutru), part);
                if (result != null) {
                    vinaiMutru = result.getWord();
                    applied = true;
                    break;
                }
            }
            if (!applied) {
                vinaiMutru.addAll(part.getWord().duplicate());
            }
        }


    }

    public TamilWord getVinaiMutru() {
        return vinaiMutru;
    }


    @Override
    public TamilWordPartContainer join(TamilWordPartContainer nilai, TamilWordPartContainer varum) {
        add(nilai);
        add(varum);
        return new TamilWordPartContainer(vinaiMutru);

    }
}
