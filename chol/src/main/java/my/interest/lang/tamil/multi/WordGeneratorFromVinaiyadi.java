package my.interest.lang.tamil.multi;

import my.interest.lang.tamil.generated.types.GenericTenseTable;
import my.interest.lang.tamil.generated.types.RootVerbDescription;
import my.interest.lang.tamil.internal.api.DefinitionFactory;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import my.interest.lang.tamil.punar.PropertyDescriptionContainer;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.join.WordsJoiner;
import tamil.lang.known.derived.*;
import tamil.lang.known.non.derived.Vinaiyadi;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class WordGeneratorFromVinaiyadi extends WordsGenerator {

    RootVerbDescription root = null;

    RootVerbDescription idu = null;
    TamilWord iduword = TamilWord.from("இடு");

    public WordGeneratorFromVinaiyadi(RootVerbDescription root) {
        this.root = root;
    }

    @Override
    public void run() {

        try {
            PropertyDescriptionContainer container = new PropertyDescriptionContainer(root);

//            Vinaiyadi vi = new Vinaiyadi(TamilWord.from(root.getRoot()), container, true);
//            List<String> engTrans = container.getEnglishMeaningForVerb(true);
//            PersistenceInterface.addEnglishMappings(engTrans, vi);
//
//            vi = new Vinaiyadi(TamilWord.from(root.getRoot()), container, false);
//            engTrans = container.getEnglishMeaningForVerb(false);
//            PersistenceInterface.addEnglishMappings(engTrans, vi);


            GenericTenseTable table = DefinitionFactory.generateVinaimuttu("vinaimuttu", root.getRoot(), true);
            PersistenceInterface.addDerivativeWithTenseAndPaal(container, true, table, false, false, VinaiMuttu.class);

            table = DefinitionFactory.generateVinaimuttu("vinaimuttu", root.getRoot(), false);
            PersistenceInterface.addDerivativeWithTenseAndPaal(container, false, table, false, false, VinaiMuttu.class);


            //thodar


//            table = DefinitionFactory.generateVinaimuttu("thodar-muttu-vinaimuttu", root.getRoot(), true);
//            PersistenceInterface.addDerivativeWithTenseAndPaal(container, true, table, true, true, VinaiMuttu.class);
//
//            table = DefinitionFactory.generateVinaimuttu("thodar-muttu-vinaimuttu", root.getRoot(), false);
//            PersistenceInterface.addDerivativeWithTenseAndPaal(container, false, table, true, true, VinaiMuttu.class);
//
//            table = DefinitionFactory.generateVinaimuttu("muttu-vinaimuttu", root.getRoot(), true);
//            PersistenceInterface.addDerivativeWithTenseAndPaal(container, true, table, false, true, VinaiMuttu.class);
//
//            table = DefinitionFactory.generateVinaimuttu("muttu-vinaimuttu", root.getRoot(), false);
//            PersistenceInterface.addDerivativeWithTenseAndPaal(container, false, table, false, true, VinaiMuttu.class);
//
//            table = DefinitionFactory.generateVinaimuttu("thodar-vinaimuttu", root.getRoot(), true);
//            PersistenceInterface.addDerivativeWithTenseAndPaal(container, true, table, true, false, VinaiMuttu.class);
//
//            table = DefinitionFactory.generateVinaimuttu("thodar-vinaimuttu", root.getRoot(), false);
//            PersistenceInterface.addDerivativeWithTenseAndPaal(container, false, table, true, false, VinaiMuttu.class);

            //thodar ends


            table = DefinitionFactory.generatePeyarechcham(root.getRoot(), true);
            PersistenceInterface.addDerivativeWithTense(container, true, table, Peyarechcham.class);

            table = DefinitionFactory.generatePeyarechcham(root.getRoot(), false);
            PersistenceInterface.addDerivativeWithTense(container, false, table, Peyarechcham.class);


            table = DefinitionFactory.generateVinaiyecham(root.getRoot(), true);
            PersistenceInterface.addDerivativeWithTense(container, true, table, Vinaiyechcham.class);


            table = DefinitionFactory.generateVinaiyecham(root.getRoot(), false);
            PersistenceInterface.addDerivativeWithTense(container, false, table, Vinaiyechcham.class);


            table = DefinitionFactory.generateKaddalhai(root.getRoot(), true, "kaddalhai_ddum");
            PersistenceInterface.addDerivative(container, true, table, Kaddalhai.class);


            table = DefinitionFactory.generateKaddalhai(root.getRoot(), false, "kaddalhai_ddum");
            PersistenceInterface.addDerivative(container, false, table, Kaddalhai.class);


            table = DefinitionFactory.generateKaddalhai(root.getRoot(), true, "kaddalhai_laam");
            PersistenceInterface.addDerivative(container, true, table, Kaddalhai.class);


            table = DefinitionFactory.generateKaddalhai(root.getRoot(), false, "kaddalhai_laam");
            PersistenceInterface.addDerivative(container, false, table, Kaddalhai.class);


            table = DefinitionFactory.generateKaddalhai(root.getRoot(), true, "kaddalhai_um");
            PersistenceInterface.addDerivative(container, true, table, Kaddalhai.class);


            table = DefinitionFactory.generateKaddalhai(root.getRoot(), false, "kaddalhai_um");
            PersistenceInterface.addDerivative(container, false, table, Kaddalhai.class);


            table = DefinitionFactory.generateKaddalhai(root.getRoot(), true, "kaddalhai_ungalh");
            PersistenceInterface.addDerivative(container, true, table, Kaddalhai.class);


            table = DefinitionFactory.generateKaddalhai(root.getRoot(), false, "kaddalhai_ungalh");
            PersistenceInterface.addDerivative(container, false, table, Kaddalhai.class);


            table = DefinitionFactory.generateVinaiyaalanaiyumPeyar(root.getRoot(), true, false);
            PersistenceInterface.addDerivativeWithTenseAndPaal(container, true, table, false, false, VinaiyaalanhaiyumPeyar.class);


            table = DefinitionFactory.generateVinaiyaalanaiyumPeyar(root.getRoot(), false, false);
            PersistenceInterface.addDerivativeWithTenseAndPaal(container, false, table, false, false, VinaiyaalanhaiyumPeyar.class);


            table = DefinitionFactory.generateVinaiyaalanaiyumPeyar(root.getRoot(), true, true);
            PersistenceInterface.addDerivativeWithPaal(container, true, table, EthirmarraiVinaiyaalanhaiyumPeyar.class);


            table = DefinitionFactory.generateVinaiyaalanaiyumPeyar(root.getRoot(), false, true);
            PersistenceInterface.addDerivativeWithPaal(container, false, table, EthirmarraiVinaiyaalanhaiyumPeyar.class);


            table = DefinitionFactory.generateVinaimuttu("ethirmarrai-peyarechcham", root.getRoot(), true, "எதிர்மரறைப்பெயரெச்சம்", true);
            PersistenceInterface.addDerivative(container, true, table, EthirmarraipPeyarechcham.class);

            table = DefinitionFactory.generateVinaimuttu("ethirmarrai-peyarechcham", root.getRoot(), false, "எதிர்மரறைப்பெயரெச்சம்", true);
            PersistenceInterface.addDerivative(container, false, table, EthirmarraipPeyarechcham.class);


            table = DefinitionFactory.generateThozhirPeyar(root, true);
            PersistenceInterface.addDerivative(container, true, table, ThozhirrPeyar.class);


            table = DefinitionFactory.generateThozhirPeyar(root, false);
            PersistenceInterface.addDerivative(container, false, table, ThozhirrPeyar.class);


            TamilWord currentRoot = TamilWord.from(root.getRoot());
            if (!currentRoot.endsWith(iduword, false) && new TamilWordPartContainer(currentRoot).isUkkurralh()) {
                if (idu == null) {
                    idu = PersistenceInterface.get().findRootVerbDescription(iduword.toString());
                }

                WordsJoiner joiner = TamilFactory.createWordJoiner(currentRoot);
                joiner.addVaruMozhi(iduword);
                currentRoot = joiner.getSum();
                Vinaiyadi vi = Vinaiyadi.get(currentRoot, container, true);
                PersistenceInterface.addOrUpdateKnown(vi);

                // System.out.println("IDU:" + currentRoot);

            }


        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


}
