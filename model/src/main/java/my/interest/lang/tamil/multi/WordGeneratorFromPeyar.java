package my.interest.lang.tamil.multi;

import my.interest.lang.tamil.punar.handler.verrrrumai.AbstractVearrrrumaiHandler;
import tamil.lang.TamilWord;
import tamil.lang.TamilCompoundCharacter;
import my.interest.lang.tamil.TamilUtils;
import tamil.lang.known.derived.KurrippuVinaiyechcham;
import tamil.lang.known.derived.PanhpupPeyarththiribu;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import my.interest.lang.tamil.generated.types.PeyarchcholDescription;
import tamil.lang.known.derived.KurrippupPeyarechcham;
import tamil.lang.known.derived.PeyarchCholThiribu;
import tamil.lang.known.non.derived.Peyarchchol;
import my.interest.lang.tamil.punar.PropertyDescriptionContainer;

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

    public WordGeneratorFromPeyar(PeyarchcholDescription peyar, PersistenceInterface per) {
        this.peyar = peyar;
        this.per = per;
    }

    @Override
    public void run() {

        try {

            PropertyDescriptionContainer container = per.getConsolidatedPropertyContainerFor(peyar);
            TamilWord n = TamilWord.from(peyar.getRoot());
            TamilWord n_rm_ak = TamilUtils.trimFinalAKOrReturn(n);
            Peyarchchol p = new Peyarchchol(n_rm_ak, n.size() - n_rm_ak.size(), container.isUyarthinhaipPeyar());
            if (p.isUyarThinhai()) {
                AbstractVearrrrumaiHandler.addUyarThinai(p);
            }
            PersistenceInterface.addOrUpdateKnown(p);
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
                        KurrippupPeyarechcham kp  = new KurrippupPeyarechcham(TamilWord.from(m), p);
                        PersistenceInterface.addOrUpdateKnown(kp);
                        PersistenceInterface.addEnglishMappings(english,kp);
                    }
                }

                String echcham = container.getKurrippupVinaiechcham();
                if (echcham != null) {
                    List<String> list = TamilUtils.parseString(echcham, ",", true);
                    for (String m : list) {
                        KurrippuVinaiyechcham kv =  new KurrippuVinaiyechcham(TamilWord.from(m), p);
                        PersistenceInterface.addOrUpdateKnown(kv);
                        PersistenceInterface.addEnglishMappings(english,kv);

                    }
                }


            }

            String pronoun = container.getProNounMaruvi();
            if (pronoun != null) {
                List<String> list = TamilUtils.parseString(pronoun, ",", true);
                for (String m : list) {
                    PeyarchCholThiribu pro = new PeyarchCholThiribu(TamilWord.from(m), p);
                    AbstractVearrrrumaiHandler.addPeyarchCholThiribu(pro);
                    PersistenceInterface.addOrUpdateKnown(pro);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

