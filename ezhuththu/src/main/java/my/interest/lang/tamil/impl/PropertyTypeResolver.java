package my.interest.lang.tamil.impl;

import my.interest.lang.tamil.StringUtils;
import tamil.util.IPropertyFinder;

import java.util.Properties;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class PropertyTypeResolver  implements IPropertyFinder {

    Properties currentlyResolved = null;
    protected String optionalPrefix = null;

    public String resolveAs(String var, String asProp) {
        if (var == null) return "${" + asProp + "}";
        String ret = replace(var, this, false);
        if (ret == null) {
            ret = "";
        }
        if (asProp != null) {
            currentlyResolved.put(asProp, ret);
        }
        return ret;
    }

    public PropertyTypeResolver(Properties known, String optionalPrefix) {
        if (known == null) {
            known = new Properties();
        }
        this.optionalPrefix = optionalPrefix;
        this.currentlyResolved = known;
    }

    public void addProperties(Properties props) {
        for (Object key :  props.keySet()) {
            currentlyResolved.setProperty(key.toString(), props.getProperty(key.toString()));
        }
    }

    @Override
    public String findProperty(String name) {
        String ret = (String) currentlyResolved.get(name);
        if (ret == null ) {
            if (optionalPrefix != null) {
                if (name.startsWith(optionalPrefix))  {
                    ret = findProperty(name.substring(optionalPrefix.length(), name.length()));
                }
            }
        }
        return ret;
    }

    public static String replace(String origString, IPropertyFinder keys, boolean exceptionWhenNotAllResolved) {
        return StringUtils.replace("${", "}", origString, keys, exceptionWhenNotAllResolved,false,true);

    }
}