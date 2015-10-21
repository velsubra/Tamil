package my.interest.lang.tamil.multi;

import my.interest.lang.tamil.TamilUtils;
import my.interest.lang.tamil.generated.types.PeyarchcholDescription;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import my.interest.lang.tamil.punar.PropertyDescriptionContainer;
import my.interest.lang.tamil.punar.handler.verrrrumai.AbstractVearrrrumaiHandler;
import my.interest.lang.tamil.punar.handler.verrrrumai.VAllHandler;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.join.KnownWordsJoiner;
import tamil.lang.known.derived.KurrippuVinaiyechcham;
import tamil.lang.known.derived.KurrippupPeyarechcham;
import tamil.lang.known.derived.PanhpupPeyarththiribu;
import tamil.lang.known.derived.PeyarchCholThiribu;
import tamil.lang.known.non.derived.idai.Kalh;
import tamil.lang.known.non.derived.Peyarchchol;
import my.interest.lang.tamil.impl.job.ExecuteManager;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class WordGeneratorFromPeyar extends WordsGenerator {

    PeyarchcholDescription peyar = null;
    PersistenceInterface per = null;
    boolean derived = false;

    public WordGeneratorFromPeyar(PeyarchcholDescription peyar, PersistenceInterface per) {
        this.peyar = peyar;
        this.per = per;
    }

    public WordGeneratorFromPeyar(PeyarchcholDescription peyar, PersistenceInterface per, boolean derived) {
        this.peyar = peyar;
        this.per = per;
        this.derived = true;
    }

    @Override
    public void run() {

        try {

            PropertyDescriptionContainer container = per.getConsolidatedPropertyContainerFor(peyar);
            String pronoun = container.getProNounMaruvi();
            List<String> pnounlist = null;
            if (pronoun != null) {
                pnounlist = TamilUtils.parseString(pronoun, ",", true);
                if (pnounlist != null && pnounlist.isEmpty()) {
                    pnounlist = null;
                }
            }

            TamilWord n = TamilWord.from(peyar.getRoot());
            TamilWord n_rm_ak = TamilUtils.trimFinalAKOrReturn(n);
            Peyarchchol p = new Peyarchchol(n_rm_ak, n.size() - n_rm_ak.size(), container.isUyarthinhaipPeyar(), pnounlist != null);
            PersistenceInterface.addOrUpdateKnown(p);
            if (p.isUyarThinhai()) {
                AbstractVearrrrumaiHandler.addUyarThinai(p);
            }

            String english = container.getEnglishForNoun();
            PersistenceInterface.addEnglishMappings(english, p);
            if (container.isPanhpupPeyar()) {
                if (n_rm_ak.endsWith(TamilCompoundCharacter.IM_I)) {
                    String trans = container.getPanhputhThiribu();
                    if (trans != null) {
                        List<String> list = TamilUtils.parseString(trans, ",", true);
                        for (String m : list) {
                            PersistenceInterface.addOrUpdateKnown(new PanhpupPeyarththiribu(TamilWord.from(m), p));
                        }
                    }
                }


                String pechcham = container.getKurrippupPeyarechcham();
                if (pechcham != null) {
                    List<String> list = TamilUtils.parseString(pechcham, ",", true);
                    for (String m : list) {
                        KurrippupPeyarechcham kp = new KurrippupPeyarechcham(TamilWord.from(m), p);
                        PersistenceInterface.addOrUpdateKnown(kp);
                        PersistenceInterface.addEnglishMappings(english, kp);
                    }
                }

                String echcham = container.getKurrippupVinaiechcham();
                if (echcham != null) {
                    List<String> list = TamilUtils.parseString(echcham, ",", true);
                    for (String m : list) {
                        KurrippuVinaiyechcham kv = new KurrippuVinaiyechcham(TamilWord.from(m), p);
                        PersistenceInterface.addOrUpdateKnown(kv);
                        PersistenceInterface.addEnglishMappings(english, kv);

                    }
                }


            }


            if (pnounlist != null) {

                for (String m : pnounlist) {
                    PeyarchCholThiribu pro = new PeyarchCholThiribu(TamilWord.from(m), p);
                    AbstractVearrrrumaiHandler.addPeyarchCholThiribu(pro);
                    PersistenceInterface.addOrUpdateKnown(pro);
                }
            } else {
                if (!p.isUyarThinhai()) {
                    if (!this.derived) {
                        KnownWordsJoiner joiner = TamilFactory.createKnownWordJoiner(p);
                        joiner.addVaruMozhi(Kalh.KALH, KnownWordsJoiner.TYPE.ALVAZHI);
                        PeyarchcholDescription kalh = new PeyarchcholDescription();
                        kalh.setRoot(joiner.getSum().toString());
                        kalh.setDescription(peyar.getDescription());
                        ExecuteManager.fire(new WordGeneratorFromPeyar(kalh, per, true));
                    }

                }
            }


            VAllHandler.HANDLER.generateAndAdd(p);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

