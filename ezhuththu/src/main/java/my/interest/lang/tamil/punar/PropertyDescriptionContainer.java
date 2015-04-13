package my.interest.lang.tamil.punar;

import my.interest.lang.tamil.EzhuththuUtils;
import my.interest.lang.tamil.StringUtils;
import my.interest.lang.tamil.generated.types.IdaichcholDescription;
import my.interest.lang.tamil.generated.types.PeyarchcholDescription;
import my.interest.lang.tamil.generated.types.Property;
import my.interest.lang.tamil.generated.types.RootVerbDescription;
import tamil.lang.TamilCharacter;
import my.interest.lang.tamil.TamilUtils;

import my.interest.lang.tamil.internal.api.IPropertyFinder;
import my.interest.lang.tamil.generated.types.*;
import my.interest.lang.tamil.generated.types.Properties;

import java.util.*;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class PropertyDescriptionContainer implements IPropertyFinder {

    public Map<String, String> getMap() {
        return map;
    }

    //Properties props = null;
    Map<String, String> map = null;
    PropertyDescriptionContainer[] parents = null;

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public static final String PROP_TRANS = "property.transitive";
    public static final String PROP_INTRANS = "property.intransitive";


    private String setName;


    public boolean isPanhpupPeyar() {
        return "pa".equals(findProperty("definition.type"));
    }

    public String getPanhputhThiribu() {
        return findProperty("i.i.definition.type.pa.mai.true.pp");
    }

    public String getKurrippupPeyarechcham() {
        return findProperty("i.definition.type.pa.pe");
    }

    public String getKurrippupVinaiechcham() {
        return findProperty("i.definition.type.pa.ve");
    }

    public String getProNounMaruvi() {
        return findProperty("i.i.definition.type.p.it.pn.v");
    }


    public String getEnglishForNoun() {
        return findProperty("meaning.english.words");
    }


    public boolean isPorudPeyar() {
        return "p".equals(findProperty("definition.type"));
    }

    public boolean isUyarthinhaipPeyar() {
        return isPorudPeyar() &&   Boolean.valueOf(findProperty("i.definition.type.p.uyarthinhai"));
    }


    public boolean isTransitive() {
        return Boolean.valueOf(findProperty(PROP_TRANS)).booleanValue();
    }

    public boolean isMarkedWithIssue() {
        List<String> props = getPropertiesStartingWith("issue");
        for (String prop : props) {
            if (Boolean.valueOf(findProperty(prop)).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    public boolean isVerbAsNoun(boolean transitive) {
        if (transitive) {
            return Boolean.valueOf(findProperty("thozhirrpeyar.transitive.verbasnoun")).booleanValue();
        } else {
            return Boolean.valueOf(findProperty("thozhirrpeyar.intransitive.verbasnoun")).booleanValue();
        }
    }

    public boolean isAtomicIdaichchol() {
        return !Boolean.valueOf(findProperty("definition.compound")).booleanValue();
    }

    public boolean isChuttuIdaichchol() {
        return "s".equals(findProperty("definition.type")) || Boolean.valueOf(findProperty("i.definition.type.q.s")).booleanValue();

    }


    public List<String> getEnglishMeaningForVerb(boolean transitive) {
        String val = null;
        if (transitive) {
            val = findProperty("meaning.english.transitive.words");
        } else {
            val = findProperty("meaning.english.intransitive.words");
        }
        if (val == null) {
            return Collections.emptyList();
        } else {
            return TamilUtils.parseString(val);
        }
    }

    public List<String> getOtherThozhiPeyars(boolean transitive) {
        String val = null;
        if (transitive) {
            val = findProperty("thozhirrpeyar.transitive.nouns");
        } else {
            val = findProperty("thozhirrpeyar.intransitive.nouns");
        }
        if (val == null) {
            return Collections.emptyList();
        } else {
            return TamilUtils.parseString(val);
        }
    }

    public boolean canTheHolderBeDeleted() {
        return Boolean.valueOf(findProperty(".deletable")).booleanValue();
    }

    public boolean isVinaiMuttuLocked() {
        return Boolean.valueOf(findProperty("vinaimuttu.locked")).booleanValue();
    }

    public boolean isDefintionLocked() {
        return Boolean.valueOf(findProperty("definition.locked")).booleanValue();
    }

    public boolean isvinaiyechchamLocked() {
        return Boolean.valueOf(findProperty("vinaiyechcham.locked")).booleanValue();
    }

    public boolean isDeletable() {
        return Boolean.valueOf(findProperty(".deletable")).booleanValue();
    }


    public void setDeletable() {
        map.put(".deletable", "true");
    }

    public boolean canPropertyBeUpdated(String prop) {

        if (prop.endsWith(".locked")) return true;
        String temp = prop;
        while (true) {
            String val = findPropertyRecursive(temp + ".locked", this, false);
            if (val != null) {
                return !Boolean.valueOf(val).booleanValue();
            }


            int lastIndex = prop.lastIndexOf(".");
            if (lastIndex < 0) {
                if (prop.trim().equals("")) {
                    return true;
                } else {
                    prop = "";
                }

            } else {
                prop = prop.substring(0, lastIndex);
            }
            return canPropertyBeUpdated(prop);
        }
    }

    public boolean isInTransitive() {
        return Boolean.valueOf(findProperty(PROP_INTRANS)).booleanValue();
    }

    public PropertyDescriptionContainer(PeyarchcholDescription root, PropertyDescriptionContainer... parent) {
        this(root.getDescription(), parent);
        map.put("root", root.getRoot());
    }

    public PropertyDescriptionContainer(IdaichcholDescription root, PropertyDescriptionContainer... parent) {
        this(root.getDescription(), parent);
        map.put("root", root.getRoot());
    }

    public PropertyDescriptionContainer(RootVerbDescription root, PropertyDescriptionContainer... parent) {
        this(root.getDescription(), parent);
        map.put("root", root.getRoot());
//        if (map.get(DefinitionFactory.VINAIMUTRU_BASE + ".vigaaram") == null) {
//            map.put(DefinitionFactory.VINAIMUTRU_BASE + ".vigaaram", root.getRoot());
//        }
//
//        if (map.get(DefinitionFactory.VINAIMUTRU_BASE_INTRANSITIVE + ".vigaaram") == null) {
//            map.put(DefinitionFactory.VINAIMUTRU_BASE_INTRANSITIVE + ".vigaaram", root.getRoot());
//        }

    }

    public String getVigaaram(boolean  transitive, String tense, String paal) {
        String name = null;
        if (transitive) {
            name = EzhuththuUtils.VINAIMUTRU_BASE ;

        } else {
            name = EzhuththuUtils.VINAIMUTRU_BASE_INTRANSITIVE;
        }
        name += ".vigaaram";
        if (tense != null) {
            name += "."+ tense.toLowerCase();
        }
        if (paal != null) {
            name += "."+ paal.toLowerCase();
        }


         return  findProperty(name);
    }

    public void setIdaiNilai(String ch, String tense, boolean transitive) {
        if (transitive) {
            map.put(EzhuththuUtils.VINAIMUTRU_BASE + ".idainilai." + tense, ch == null ? "" : ch.toString());
        } else {
            map.put(EzhuththuUtils.VINAIMUTRU_BASE_INTRANSITIVE + ".idainilai." + tense, ch == null ? "" : ch.toString());
        }
    }

    public void setSanthi(TamilCharacter ch, String tense, boolean transitive) {
        if (transitive) {
            map.put(EzhuththuUtils.VINAIMUTRU_BASE + ".santhi." + tense, ch == null ? "" : ch.toString());
        } else {
            map.put(EzhuththuUtils.VINAIMUTRU_BASE_INTRANSITIVE + ".santhi." + tense, ch == null ? "" : ch.toString());
        }
    }

    public void setSaariyai(TamilCharacter ch, String tense, boolean transitive) {
        if (transitive) {
            map.put(EzhuththuUtils.VINAIMUTRU_BASE + ".saariyai." + tense, ch == null ? "" : ch.toString());
        } else {
            map.put(EzhuththuUtils.VINAIMUTRU_BASE_INTRANSITIVE + ".saariyai." + tense, ch == null ? "" : ch.toString());
        }
    }


    public void setSanthiOntran(TamilCharacter ch, String tense, boolean transitive) {
        if (transitive) {
            map.put(EzhuththuUtils.VINAIMUTRU_BASE + ".santhi." + tense + ".thu", ch == null ? "" : ch.toString());
        } else {
            map.put(EzhuththuUtils.VINAIMUTRU_BASE_INTRANSITIVE + ".santhi." + tense + ".thu", ch == null ? "" : ch.toString());
        }
    }

    public void setSaariyaiOntran(TamilCharacter ch, String tense, boolean transitive) {
        if (transitive) {
            map.put(EzhuththuUtils.VINAIMUTRU_BASE + ".saariyai." + tense + ".thu", ch == null ? "" : ch.toString());
        } else {
            map.put(EzhuththuUtils.VINAIMUTRU_BASE_INTRANSITIVE + ".saariyai." + tense + ".thu", ch == null ? "" : ch.toString());
        }
    }

    public void setSanthiPalavin(TamilCharacter ch, String tense, boolean transitive) {
        if (transitive) {
            map.put(EzhuththuUtils.VINAIMUTRU_BASE + ".santhi." + tense + ".a", ch == null ? "" : ch.toString());
        } else {
            map.put(EzhuththuUtils.VINAIMUTRU_BASE_INTRANSITIVE + ".santhi." + tense + ".a", ch == null ? "" : ch.toString());
        }
    }

    public void setSaariyaiPalavin(TamilCharacter ch, String tense, boolean transitive) {
        if (transitive) {
            map.put(EzhuththuUtils.VINAIMUTRU_BASE + ".saariyai." + tense + ".a", ch == null ? "" : ch.toString());
        } else {
            map.put(EzhuththuUtils.VINAIMUTRU_BASE_INTRANSITIVE + ".saariyai." + tense + ".a", ch == null ? "" : ch.toString());
        }
    }


    public PropertyDescriptionContainer(Properties props) {
        this(props, (PropertyDescriptionContainer[])null);
    }

    public PropertyDescriptionContainer(Properties props, PropertyDescriptionContainer... parent) {
        //  this.props = props;
        this.parents = parent;

        map = new HashMap<String, String>();
        if (props != null) {
            if (props != null && props.getProperty() != null) {
                for (Property p : props.getProperty()) {
                    map.put(p.getName(), p.getValue());
                }
            }
        }
    }

    public Properties getProperties() {

        Properties prop = new Properties();
        prop.setName(setName);
        for (Map.Entry<String, String> e : this.map.entrySet()) {
            Property p = new Property();
            p.setValue(e.getValue());
            p.setName(e.getKey());
            prop.getProperty().add(p);
        }
        return prop;
    }

    public String findProperty(String base, String prop) {
        String val = findPropertyRecursive(base + "." + prop, this);
        return val;
    }

    @Override
    public String findProperty(String prop) {
        String val = findPropertyRecursive(prop, this);
        return val;

    }

    public String findPropertyWithResolver(String prop, IPropertyFinder resolver, boolean deep) {
        String val = findPropertyRecursive(prop, resolver, deep);
        return val;

    }

    private String findPropertyRecursive(String prop, IPropertyFinder resolver) {
        return findPropertyRecursive(prop, resolver, true);
    }


    private List<String> getPropertiesStartingWith(String prop) {
        List<String> props = new ArrayList<String>();
        if (map != null) {
            for (String key : map.keySet()) {
                if (key.startsWith(prop)) {
                    props.add(key);
                }
            }
        }
        return props;

    }

    private String findPropertyRecursive(String prop, IPropertyFinder resolver, boolean deep) {
        String value = map.get(prop);
        if (value != null) {
            return StringUtils.replaceForT(StringUtils.replaceFor$(value, resolver, false));
        } else {
            if (parents != null) {
                String ret = null;
                for (PropertyDescriptionContainer parent : parents) {
                    if (parent == null || parent == this) continue;
                    ret = parent.findPropertyWithResolver(prop, resolver, deep);
                    if (ret != null) {
                        break;
                    }
                }
                if (ret != null) {
                    return StringUtils.replaceForT(StringUtils.replaceFor$(ret, resolver, false));
                } else {
                    if (deep) {
                        int lastIndex = prop.lastIndexOf(".");
                        if (lastIndex < 0) {

                            return null;
                        } else {
                            return findPropertyRecursive(prop.substring(0, lastIndex), resolver, deep);
                        }
                    } else {
                        return null;
                    }
                }
            } else {
                return null;
            }

        }
    }


}
