package my.interest.lang.tamil;

import my.interest.lang.tamil.generated.types.Properties;
import my.interest.lang.tamil.generated.types.Property;
import org.json.JSONException;
import org.json.JSONObject;
import tamil.lang.exception.TamilPlatformException;

import javax.xml.stream.events.Characters;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;


/**
 * Created by velsubra on 6/10/16.
 */
public final class PropertiesContainer {

    public Properties getInputProperties() {
        return inputProperties == null ? new Properties() : inputProperties;
    }

    private Properties inputProperties = null;
    /**
     * Creates a new Property Container
     *
     * @param inputProperties, it could be null

     */
    public PropertiesContainer(Properties inputProperties) {
        this.inputProperties = inputProperties;


    }

    public int size() {
        if (this.inputProperties == null) {
            return 0;
        } else {
            return this.inputProperties.getProperty().size();
        }
    }




    public String getPropertyValue(String name) {
        Property pair = findProperty(name);
        if (pair == null) {
            return null;
        } else {
            return pair.getValue();
        }
    }

    public void addProperty(String name, String val) {

        Property existing = findProperty(name);
        if (existing != null) {
           existing.setValue(val);

        } else {
            if (inputProperties == null) {
                inputProperties = new Properties();
            }

            Property pair = new Property();
            pair.setName(name);
            pair.setValue(val);

            inputProperties.getProperty().add(pair);
        }
    }

    public void add(Property val) {
        Property existing = findProperty(val.getName());
        if (existing != null) {
            existing.setValue(val.getValue());
        } else {
            if (inputProperties == null) {
                inputProperties = new Properties();
            }
            inputProperties.getProperty().add(val);
        }
    }


    public Property findProperty(String name) {
        if (inputProperties == null) {
            return null;
        }
        if (name == null) {
            return null;
        }
        for (Property pair : inputProperties.getProperty()) {
            if (name.equals(pair.getName())) {
                return pair;
            }
        }
//        todo: revisit
// if (argsDefinition != null) {
//            for (ConfigArgDefinition arg : argsDefinition.getArg()) {
//                    if (name.equals(arg.getName()))  {
//                        if (arg.getDefaultValue() != null) {
//
//                        }
//                    }
//            }
//        }
        return null;
    }


    public void addAllProperties(Properties Properties) {
        if (Properties == null) return;
        for (Property pair : Properties.getProperty()) {
            add(pair);
        }

    }


    public List<String> getProperties() {
        List<String> list = new ArrayList<String>();
        if (inputProperties == null) {
            return list;
        }
        for (Property pair : inputProperties.getProperty()) {
            list.add(pair.getName());
        }
        return list;

    }

    public <T> T getPropertyValue(String name, Class<T> type) throws TamilPlatformException {
        String value = getPropertyValue(name);
        if (value == null) {
            return null;
        } else {
            if (type == null) {
                type = (Class<T>) String.class;
            }
            return toSupportedValue(value, type);
        }
    }

   

    private static boolean isPrimitiveOrWrapper(Class type) {
        Set<Class> wrapper_types = new HashSet(Arrays.asList(
                Boolean.class, Character.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Void.class));
        if(type.isPrimitive() || wrapper_types.contains(type)){
            return true;
        }
        return false;
    }

    public static <T> T toSupportedValue(String value, Class<T> type) throws TamilPlatformException {

        if (value == null) {
            return null;
        } else {
            if (type == null) {
                type = (Class<T>) String.class;
            }
            if (Object.class == type) {
                return (T) value;
            }
            if (String.class == type) {
                return (T) value;
            } else if (byte[].class == type) {
                try {
                    return (T) EncodingUtil.decodetoByteArray(value);
                } catch (IOException io) {
                    throw new TamilPlatformException(io.getMessage());
                }
            } else if (JSONObject.class == type) {
                try {
                    return (T) new JSONObject(value);
                } catch (JSONException je) {
                    throw new TamilPlatformException(je.getMessage());
                }
            } else if (TamilUtils.isValidJAXBClass(type)) {
                try {
                    return (T) TamilUtils.deSerializeNonRootElement(new ByteArrayInputStream(value.toString().getBytes(TamilUtils.ENCODING)), type);
                } catch (Exception e) {

                    throw new TamilPlatformException(e.getMessage());
                }
            } else {
                if (int.class == type || Integer.class == type) {
                    return (T) new Integer(value);
                } else if (char.class == type || Characters.class == type) {
                    if (value.length() < 1) {
                        throw new TamilPlatformException("String does not have any character.");
                    }
                    return (T) new Character(value.charAt(0));
                } else if (float.class == type || Float.class == type) {
                    return (T) new Float(value);
                } else if (double.class == type || Double.class == type) {
                    return (T) new Double(value);
                } else if (boolean.class == type || Boolean.class == type) {
                    return (T) new Boolean(value);
                } else if (byte.class == type || Byte.class == type) {
                    return (T) new Float(value);
                } else if (type.isArray()) {
                    if (byte.class == type.getComponentType()) {
                        try {
                            return (T) value.getBytes(TamilUtils.ENCODING);
                        } catch (Exception e) {

                            throw new TamilPlatformException(e.getMessage());
                        }
                    } else {
                        throw new TamilPlatformException("Array of value  type:" + type.getComponentType() + " is not yet supported");
                    }

                } else {
                    throw new TamilPlatformException("Value  type:" + type + " is not yet supported");
                }
            }
        }
    }

    public Map<String, String> toStringMap() {
        Map<String, String> map = new HashMap<String, String>();
        List<String> keys = getProperties();
        if (keys != null) {
            for (String key : keys) {
                String value = getPropertyValue(key);
                if (value == null) {
                    continue;
                }
                map.put(key, value.trim());
            }
        }
        return map;
    }

    public static PropertiesContainer fromStringMap(Map<String, String> map) {
        PropertiesContainer container = new PropertiesContainer( null);
        if (map != null) {
            for (String key : map.keySet()) {
                String val = map.get(key);
                if (val == null) {
                    continue;
                }
                Property pair = new Property();
                pair.setName(key);
                pair.setValue(val);

                container.add(pair);
            }
        }
        return container;
    }
}
