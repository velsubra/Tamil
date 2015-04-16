package my.interest.lang.tamil.internal.api;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import my.interest.lang.tamil.EzhuththuUtils;
import my.interest.lang.tamil.StringUtils;
import my.interest.lang.tamil.TamilUtils;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import my.interest.lang.tamil.generated.types.*;
import my.interest.lang.tamil.punar.PropertyDescriptionContainer;
import my.interest.lang.tamil.punar.handler.VinaiMutruCreationHandler;
import tamil.lang.api.persist.manager.PersistenceManager;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class DefinitionFactory extends EzhuththuUtils {
    static final Logger logger = Logger.getLogger(DefinitionFactory.class.getName());



    static final GroovyShell shell = new GroovyShell();
    static final Map<String, SoftReference<Script>> compiledScripts = new HashMap<String, SoftReference<Script>>();

    private synchronized static Script compile(String script) {
        SoftReference<Script> s = compiledScripts.get(script);
        Script c = null;
        if (s != null) {
            c = s.get();
        }
        if (c == null) {
            c = shell.parse(script);
            compiledScripts.put(script, new SoftReference<Script>(c));
        }
        return c;
    }


    public static GenericTenseTable generateThozhirPeyar(String v, boolean fortransitive) {
        PersistenceManager per = TamilFactory.getPersistenceManager();
        RootVerbDescription verb = per.getRootVerbManager().findRootVerbDescription(v);
        if (verb == null) {
            throw new RuntimeException("No verb:" + v);
        }
        return generateThozhirPeyar(verb,fortransitive);
    }

    public static GenericTenseTable generateThozhirPeyar(RootVerbDescription verb, boolean fortransitive) {
        GenericTenseTable table = new GenericTenseTable();
        table.setRoot(verb.getRoot());

        PersistenceManager per = TamilFactory.getPersistenceManager();
       // RootVerbDescription verb = per.findRootVerbDescription(v);

        PropertyDescriptionContainer container = per.getRootVerbManager().getConsolidatedPropertyContainerFor(verb);
        if (fortransitive && !container.isTransitive()) return table;
        if (!fortransitive && !container.isInTransitive()) return table;
        if (container.isVerbAsNoun(fortransitive)) {
            TableRow row = new TableRow();
            row.setRowname("முதனிலைத்தொழிற்பெயர் ");
            DerivedValues val = new DerivedValues();
            val.getList().add(new DerivedValue());
            val.getList().get(0).setEquation(verb.getRoot());
            val.getList().get(0).setValue(verb.getRoot());
            row.setPresent(val);
            table.getRows().add(row);
        }
        List<String> others = container.getOtherThozhiPeyars(fortransitive);
        if (!others.isEmpty()) {
            TableRow row = new TableRow();
            row.setRowname("மற்றவை  ");
            DerivedValues val = new DerivedValues();
            table.getRows().add(row);
            row.setPresent(val);
            for (String o : others) {
                DerivedValue d = new DerivedValue();
                d.setEquation(o);
                d.setValue(o);
                val.getList().add(d);

            }
        }


        return table;

    }

    public static GenericTenseTable generateKaddalhai(String verb, boolean fortransitive, String defn) {

        DefinedValues defined = new DefinedValues();
        defined.setDefinition(defn);
        defined.setOf(verb);
        defined.setWithContext(new DefinitionContext());
        defined.getWithContext().getAt().add(fortransitive ? "transitive" : "intransitive");
        defined.getWithContext().getAt().add(SimpleTense.PRESENT.name().toLowerCase());

        GenericTenseTable table = new GenericTenseTable();
        table.setRoot(verb);

        TableRow row = new TableRow();
        row.setRowname("கட்டளை");
        DerivedValues present = getFromDefinition(PersistenceInterface.get(), defined);
        if (present == null) return table;
        row.setPresent(present);
        table.getRows().add(row);


        return table;
    }


    public static GenericTenseTable generateVinaiyecham(String verb, boolean fortransitive) {

        DefinedValues defined = new DefinedValues();
        defined.setDefinition("vinaiyechcham");
        defined.setOf(verb);
        defined.setWithContext(new DefinitionContext());
        defined.getWithContext().getAt().add(fortransitive ? "transitive" : "intransitive");
        defined.getWithContext().getAt().add(SimpleTense.PRESENT.name().toLowerCase());

        GenericTenseTable table = new GenericTenseTable();
        table.setRoot(verb);

        TableRow row = new TableRow();
        row.setRowname("வினையெச்சங்கள்");
        DerivedValues present = getFromDefinition(PersistenceInterface.get(), defined);
        if (present == null) return table;
        row.setPresent(present);

        defined.getWithContext().getAt().set(1, SimpleTense.PAST.name().toLowerCase());
        DerivedValues past = getFromDefinition(PersistenceInterface.get(), defined);
        row.setPast(past);
        row.setFuture(new DerivedValues());
        table.getRows().add(row);

        return table;
    }


    public static GenericTenseTable generatePeyarechcham(String verb, boolean fortransitive) {
        DefinedValues defined = new DefinedValues();
        defined.setDefinition("peyarechcham");
        defined.setOf(verb);
        defined.setWithContext(new DefinitionContext());
        defined.getWithContext().getAt().add(fortransitive ? "transitive" : "intransitive");
        defined.getWithContext().getAt().add(SimpleTense.PRESENT.name().toLowerCase());

        GenericTenseTable table = new GenericTenseTable();
        table.setRoot(verb);

        TableRow row = new TableRow();
        row.setRowname("பெயரெச்சங்கள்");
        DerivedValues present = getFromDefinition(PersistenceInterface.get(), defined);
        if (present == null) return table;
        row.setPresent(present);
        defined.getWithContext().getAt().set(1, SimpleTense.PAST.name().toLowerCase());
        DerivedValues past = getFromDefinition(PersistenceInterface.get(), defined);
        row.setPast(past);

        defined.setDefinition("vinaimuttu");
        defined.getWithContext().getAt().set(1, SimpleTense.FUTURE.name().toLowerCase());
        defined.getWithContext().getAt().add(PaalViguthi.THU.name().toLowerCase());
        DerivedValues future = getFromDefinition(PersistenceInterface.get(), defined);
        row.setFuture(future);
        table.getRows().add(row);

        return table;
    }

    public static GenericTenseTable generateVinaimuttu(String definition, String verb, boolean fortransitive) {
        return generateVinaimuttu(definition, verb, fortransitive, null, false);
    }


    public static GenericTenseTable generateVinaimuttu(String definition, String verb, boolean fortransitive, String rowName, boolean notense) {
        GenericTenseTable table = new GenericTenseTable();
        table.setRoot(verb);
        DefinedValues defined = new DefinedValues();
        defined.setDefinition(definition);
        defined.setOf(verb);
        defined.setWithContext(new DefinitionContext());
        defined.getWithContext().getAt().add(fortransitive ? "transitive" : "intransitive");
        defined.getWithContext().getAt().add("");
        defined.getWithContext().getAt().add("");

        for (PaalViguthi p : PaalViguthi.values()) {
            defined.getWithContext().getAt().set(2, p.name().toLowerCase());
            // TamilWord viguthi = EnglishToTamilCharacterLookUpContext.getBestMatch(p.name());

            TableRow row = new TableRow();
            // row.setRowname(viguthi.toString());
            row.setRowname(rowName == null ? p.name().toLowerCase() : rowName);
            defined.getWithContext().getAt().set(1, SimpleTense.PRESENT.name().toLowerCase());
            DerivedValues present = getFromDefinition(PersistenceInterface.get(), defined);
            row.setPresent(present);
            if (present == null) return table;
            if (!notense) {
                defined.getWithContext().getAt().set(1, SimpleTense.PAST.name().toLowerCase());
                DerivedValues past = getFromDefinition(PersistenceInterface.get(), defined);
                row.setPast(past);
                defined.getWithContext().getAt().set(1, SimpleTense.FUTURE.name().toLowerCase());
                DerivedValues future = getFromDefinition(PersistenceInterface.get(), defined);
                row.setFuture(future);
            }
            table.getRows().add(row);

            if (rowName != null) {
                break;
            }
        }
        return table;


    }


    public static GenericTenseTable generateVinaiyaalanaiyumPeyar(String verb, boolean fortransitive, boolean negative) {
        GenericTenseTable table = new GenericTenseTable();
        table.setRoot(verb);
        DefinedValues defined = new DefinedValues();
        defined.setDefinition(negative ? "ethirmarrai-vinaiyaalanaiyum-peyar" : "vinaiyaalanaiyum-peyar");
        defined.setOf(verb);
        defined.setWithContext(new DefinitionContext());
        defined.getWithContext().getAt().add(fortransitive ? "transitive" : "intransitive");
        defined.getWithContext().getAt().add("");
        defined.getWithContext().getAt().add("");

        for (VinaiyaalanaiyumViguthi p : VinaiyaalanaiyumViguthi.values()) {
            defined.getWithContext().getAt().set(2, p.name().toLowerCase());
          //  TamilWord viguthi = EnglishToTamilCharacterLookUpContext.getBestMatch(p.name());
            TableRow row = new TableRow();
            // row.setRowname(viguthi.toString());
            row.setRowname(p.name().toLowerCase());
            defined.getWithContext().getAt().set(1, SimpleTense.PRESENT.name().toLowerCase());
            DerivedValues present = getFromDefinition(PersistenceInterface.get(), defined);
            row.setPresent(present);
            if (present == null) return table;

            if (!negative) {
                defined.getWithContext().getAt().set(1, SimpleTense.PAST.name().toLowerCase());
                DerivedValues past = getFromDefinition(PersistenceInterface.get(), defined);
                row.setPast(past);
                defined.getWithContext().getAt().set(1, SimpleTense.FUTURE.name().toLowerCase());
                DerivedValues future = getFromDefinition(PersistenceInterface.get(), defined);
                row.setFuture(future);
            }
            table.getRows().add(row);

        }
        return table;
    }


    public static DerivedValues getFromDefinition(PersistenceInterface persistenceInterface, DefinedValues defined) {// PropertyDescriptionContainer container, Definition definition) {

        DerivedValues values = new DerivedValues();

        Definition definition = persistenceInterface.findDefinition(defined.getDefinition());
        RootVerbDescription verb = persistenceInterface.findRootVerbDescription(defined.getOf());
        if (definition != null & verb != null) {
            PropertyDescriptionContainer container = persistenceInterface.getConsolidatedPropertyContainerFor(verb);

            if (definition.getContext() != null && defined.getWithContext() != null) {
                for (int i = 0; i < definition.getContext().getAt().size(); i++) {
                    String atname = definition.getContext().getAt().get(i);
                    if (i < defined.getWithContext().getAt().size()) {
                        String atvalue = defined.getWithContext().getAt().get(i);
                        container.getMap().put(atname, atvalue);

                        if ("verbtype".equals(atname)) {
                            if ("transitive".equals(atvalue)) {
                                if (!container.isTransitive()) {
                                    return null;
                                }
                            } else {
                                if (!container.isInTransitive()) {
                                    return null;
                                }
                            }
                        }
                    } else {
                        break;
                    }
                }

                for (SummedItems sum : definition.getSumOf()) {
                    List<VinaiMutruCreationHandler> handlers = new ArrayList<VinaiMutruCreationHandler>();
                    VinaiMutruCreationHandler vmh = new VinaiMutruCreationHandler();
                    handlers.add(vmh);
                    for (String special : definition.getSpecialHandler()) {
                        vmh.addInstanceHandler(special);
                    }

                    boolean firstitem = true;
                    for (SummedItem item : sum.getParam()) {
                        List<String> commavalueslist = null;
                        if (item.getPropertyValues() != null) {
                            String commavalues = null;
                            String possibles = item.getPropertyValues().trim();
                            for (String possible : TamilUtils.parseString(possibles, "|")) {
                                String propName = StringUtils.replaceFor$(possible, container, false);
                                commavalues = container.findProperty(propName);
                                //  System.out.println(propName + ":" + commavalues);
                                if (commavalues != null) {
                                    break;
                                }
                            }
                            if (commavalues == null) {

                                commavalues = " ";
                            }
                            commavalueslist = TamilUtils.parseString(commavalues, ",", false,true);

                        } else if (item.getDerivedValues() != null) {
                            DefinedValues sub = new DefinedValues();
                            sub.setDefinition(item.getDerivedValues().getDefinition());
                            sub.setOf(StringUtils.replaceFor$(item.getDerivedValues().getOf(), container, false));
                            if (item.getDerivedValues().getWithContext() != null) {
                                sub.setWithContext(new DefinitionContext());
                                for (String at : item.getDerivedValues().getWithContext().getAt()) {
                                    sub.getWithContext().getAt().add(StringUtils.replaceFor$(at, container, false));
                                }
                            }

                            DerivedValues subvalues = getFromDefinition(persistenceInterface, sub);
                            commavalueslist = new ArrayList<String>();
                            if (subvalues != null) {
                                for (DerivedValue dv : subvalues.getList()) {
                                    String derived = dv.getValue();
                                    if (item.getDerivedValues().getFilterScript() != null) {
                                        Map<String, Object> map = new HashMap<String, Object>();
                                        map.put("container", container);
                                        map.putAll(container.getMap());
                                        map.put(item.getDerivedValues().getToScriptVar(), derived);
                                        String script = StringUtils.replaceForT(StringUtils.replaceFor$(item.getDerivedValues().getFilterScript(), container, false));
                                        // System.out.println("Script:" + script);
                                        Script compiled = compile(script);

                                        synchronized (compiled) {
                                            compiled.setBinding(new Binding(map));
                                            Object val = compiled.run();
                                            if (val != null) {
                                                derived = val.toString();
                                            } else {
                                                derived = null;
                                            }
                                        }

                                    }
                                    if (derived != null) {
                                        if (!commavalueslist.contains(derived)) {
                                            commavalueslist.add(derived);
                                        }
                                    }
                                }
                            }
                        } else if (item.getLiteralValues() != null) {
                            commavalueslist = new ArrayList<String>();
                            commavalueslist.add(StringUtils.replaceForT(StringUtils.replaceFor$(item.getLiteralValues().trim(), container, false)));
                        } else if (item.getScriptValues() != null) {

                            //Just populate  commavalueslist at the end.
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("container", container);
                            map.putAll(container.getMap());
                            String script = StringUtils.replaceForT(StringUtils.replaceFor$(item.getScriptValues(), container, false));
                            //System.out.println("Script:" + script);
                            Object val = null;
                            Script compiled = compile(script);
                            synchronized (compiled) {
                                compiled.setBinding(new Binding(map));
                                val = compiled.run();
                            }

                            if (val != null) {
                                commavalueslist = TamilUtils.parseString(val.toString(), ",", false, true);
                            }

                        } else {

                            continue;
                        }


                        if (commavalueslist == null || commavalueslist.isEmpty() || commavalueslist.get(0).trim().equals("")) {
                            if (firstitem) {
                                break;
                            } else {
                                continue;
                            }
                        }

                        firstitem = false;

                        HashMap<String, List<VinaiMutruCreationHandler>> allHandlers = new HashMap<String, List<VinaiMutruCreationHandler>>();
                        allHandlers.put(commavalueslist.get(0), handlers);

                        //Multiply handlers
                        for (int i = 1; i < commavalueslist.size(); i++) {
                            allHandlers.put(commavalueslist.get(i), duplicate(handlers));
                        }

                        for (Map.Entry<String, List<VinaiMutruCreationHandler>> pair : allHandlers.entrySet()) {
                            for (VinaiMutruCreationHandler h : pair.getValue()) {
                                h.add(TamilWord.from(pair.getKey()));
                            }
                        }
                        //Copy them back
                        for (int i = 1; i < commavalueslist.size(); i++) {
                            handlers.addAll(allHandlers.get(commavalueslist.get(i)));
                        }
                    }

                    for (VinaiMutruCreationHandler h : handlers) {
                        DerivedValue val = new DerivedValue();
                        if (h.getVinaiMutru().size() > 0) {
                            val.setValue(h.getVinaiMutru().toString());
                            val.setEquation(h.getEquation());
                            values.getList().add(val);
                        }
                    }
                }


            }
        }
        return values;


    }

    private static List<VinaiMutruCreationHandler> duplicate(List<VinaiMutruCreationHandler> handlers) {
        List<VinaiMutruCreationHandler> duplicates = new ArrayList<VinaiMutruCreationHandler>();
        for (VinaiMutruCreationHandler hand : handlers) {
            duplicates.add(hand.duplicate());
        }
        return duplicates;
    }

}
