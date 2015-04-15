package test.applet;

import junit.framework.Assert;
import my.interest.lang.tamil.EzhuththuUtils;
import my.interest.lang.tamil.generated.types.PeyarchchchorrkalhDescription;
import org.json.JSONObject;
import org.junit.Test;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class AppletTest {

    @Test
    public void testDeserialize() throws Exception {
        String json = "\n" +
                "{\"name\":\"{http://my.interest.lang.tamil}nouns\",\"declaredType\":\"my.interest.lang.tamil.generated.types.PeyarchchchorrkalhDescription\",\"scope\":\"javax.xml.bind.JAXBElement$GlobalScope\",\"value\":{\"list\":{\"word\":[{\"root\":\"கலாம்\",\"description\":{\"property\":[{\"name\":\"definition.type\",\"value\":\"p\"},{\"name\":\"i.definition.type.p.uyarthinhai\",\"value\":\"true\"},{\"name\":\"i.definition.type.p.it\",\"value\":\"ot\"}],\"name\":null}},{\"root\":\"தாங்கள்\",\"description\":{\"property\":[{\"name\":\"definition.type\",\"value\":\"p\"},{\"name\":\"i.definition.type.p.uyarthinhai\",\"value\":\"true\"},{\"name\":\"i.definition.type.p.it\",\"value\":\"pn\"},{\"name\":\"i.i.definition.type.p.it.pn.v\",\"value\":\"தங்கள்\"},{\"name\":\"meaning.english.words\",\"value\":\"you\"}],\"name\":null}},{\"root\":\"தாம்\",\"description\":{\"property\":[{\"name\":\"definition.type\",\"value\":\"p\"},{\"name\":\"i.definition.type.p.uyarthinhai\",\"value\":\"true\"},{\"name\":\"i.definition.type.p.it\",\"value\":\"pn\"},{\"name\":\"i.i.definition.type.p.it.pn.v\",\"value\":\"தம்\"},{\"name\":\"meaning.english.words\",\"value\":\"you\"}],\"name\":null}},{\"root\":\"நாங்கள்\",\"description\":{\"property\":[{\"name\":\"definition.type\",\"value\":\"p\"},{\"name\":\"i.definition.type.p.uyarthinhai\",\"value\":\"true\"},{\"name\":\"i.definition.type.p.it\",\"value\":\"pn\"},{\"name\":\"i.i.definition.type.p.it.pn.v\",\"value\":\"எங்கள்\"}],\"name\":null}},{\"root\":\"நான்\",\"description\":{\"property\":[{\"name\":\"definition.type\",\"value\":\"p\"},{\"name\":\"i.definition.type.p.it\",\"value\":\"pn\"},{\"name\":\"i.i.definition.type.p.it.pn.v\",\"value\":\"என்\"},{\"name\":\"meaning.english.words\",\"value\":\"i\"},{\"name\":\"i.definition.type.p.uyarthinhai\",\"value\":\"true\"}],\"name\":null}},{\"root\":\"நாம்\",\"description\":{\"property\":[{\"name\":\"definition.type\",\"value\":\"p\"},{\"name\":\"i.definition.type.p.it\",\"value\":\"pn\"},{\"name\":\"i.i.definition.type.p.it.pn.v\",\"value\":\"நம்\"},{\"name\":\"meaning.english.words\",\"value\":\"we\"}],\"name\":null}},{\"root\":\"நீ\",\"description\":{\"property\":[{\"name\":\"definition.type\",\"value\":\"p\"},{\"name\":\"i.definition.type.p.it\",\"value\":\"pn\"},{\"name\":\"i.i.definition.type.p.it.pn.v\",\"value\":\"உன்\"}],\"name\":null}},{\"root\":\"நீங்கள்\",\"description\":{\"property\":[{\"name\":\"definition.type\",\"value\":\"p\"},{\"name\":\"i.definition.type.p.uyarthinhai\",\"value\":\"true\"},{\"name\":\"i.definition.type.p.it\",\"value\":\"pn\"},{\"name\":\"i.i.definition.type.p.it.pn.v\",\"value\":\"உங்கள்\"},{\"name\":\"meaning.english.words\",\"value\":\"you\"}],\"name\":null}},{\"root\":\"நீர்ஃ\",\"description\":{\"property\":[{\"name\":\"definition.type\",\"value\":\"p\"},{\"name\":\"i.definition.type.p.it\",\"value\":\"pn\"},{\"name\":\"i.definition.type.p.uyarthinhai\",\"value\":\"true\"},{\"name\":\"i.i.definition.type.p.it.pn.v\",\"value\":\"நிம்\"}],\"name\":null}},{\"root\":\"யான்\",\"description\":{\"property\":[{\"name\":\"definition.type\",\"value\":\"p\"},{\"name\":\"i.definition.type.p.uyarthinhai\",\"value\":\"true\"},{\"name\":\"i.definition.type.p.it\",\"value\":\"pn\"},{\"name\":\"i.i.definition.type.p.it.pn.v\",\"value\":\"என்\"},{\"name\":\"meaning.english.words\",\"value\":\"i\"}],\"name\":null}},{\"root\":\"யாம்\",\"description\":{\"property\":[{\"name\":\"definition.type\",\"value\":\"p\"},{\"name\":\"i.definition.type.p.uyarthinhai\",\"value\":\"true\"},{\"name\":\"i.definition.type.p.it\",\"value\":\"pn\"},{\"name\":\"i.i.definition.type.p.it.pn.v\",\"value\":\"எம்\"},{\"name\":\"meaning.english.words\",\"value\":\"we\"}],\"name\":null}}]}},\"nil\":false,\"globalScope\":true,\"typeSubstituted\":false}";
        JSONObject obj = new JSONObject(json).getJSONObject("value");
        PeyarchchchorrkalhDescription p = EzhuththuUtils.getPeyarchchchorrkalhDescription(obj);
        System.out.println("AppletTest.testDeserialize:" + p.getList().getWord().size());
        Assert.assertEquals(11, p.getList().getWord().size());

    }
}
