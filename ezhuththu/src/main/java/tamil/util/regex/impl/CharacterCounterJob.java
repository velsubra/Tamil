package tamil.util.regex.impl;

import common.lang.impl.AbstractCharacter;
import org.json.JSONArray;
import org.json.JSONObject;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.ezhuththu.TamilCharacterSetCalculator;
import tamil.lang.api.job.JobContext;
import tamil.lang.api.job.JobRunnable;
import tamil.lang.exception.TamilPlatformException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *     Job to count the number of characters  that belong to a given list of character sets  at the given input source.
 *     E.g
 *     <pre>
 *         source :aஇருள்சேர் இருவினையும் சேரா இறைவன்
 *
 *         The job produces the following JSON . This contains only a single work unit that gets updated.
 *         [{"labels":["எழுத்து","வலியுகரவரிசை","அகரவரிசை"],"counts":[17,0,1]]}]
 *
 *         where,
 *         labels - Array, the given list of character set names   . See {@link #PROP_LABELS_ARRAY}
 *         counts - Array, the list counts of characters  from the respective character sets, in the same order as the labels    See {@link #PROP_COUNTS_ARRAY}
 *
 *         "எழுத்து","வலியுகரவரிசை","அகரவரிசை" are the given list of character set names.
 *
 *     </pre>
 *
 * </p>
 *
 * Created by vjhp on 10/25/2015.
 */
public class CharacterCounterJob implements JobRunnable<JSONObject> {

    public static final String PROP_LABELS_ARRAY = "labels";
    public static final String PROP_COUNTS_ARRAY = "counts";

    TamilWord source = null;
    List<String> characterSetNames = null;

    public CharacterCounterJob(TamilWord source, List<String> characterSetNames) {
        this.source = source;
        this.characterSetNames = characterSetNames;
    }

    public void run(JobContext<JSONObject> context) {
        try {
            config(context);
            TamilCharacterSetCalculator calc = TamilFactory.getTamilCharacterSetCalculator();
            List<Set<TamilCharacter>> sets = new ArrayList<Set<TamilCharacter>>(characterSetNames.size());

            context.setStatusMessage("Calculating letter sets ...");
            JSONObject json = new JSONObject();
            JSONArray labels = new JSONArray();
            JSONArray counts = new JSONArray();
            json.put("labels", labels);
            json.put("counts", counts);
            for (String name : characterSetNames) {
                sets.add(calc.find(name));
                counts.put(0);
                labels.put(name);
            }
            context.updateLastResult(json);
            context.setStatusMessage("Counting letters ...");
            int characterCount = 0;
            for (AbstractCharacter c : source) {
                characterCount++;
                if (!c.isTamilLetter()) continue;
                for (int i = 0; i < characterSetNames.size(); i++) {
                    Set<TamilCharacter> set = sets.get(i);
                    if (set.contains(c)) {
                        int count = counts.getInt(i);
                        count++;
                        counts.put(i, count);
                    }

                }
                continuousUpdate(context, characterCount, json);

            }
            context.updateLastResult(json);
        } catch (Exception e) {
            e.printStackTrace();
            throw new TamilPlatformException(e.getMessage());
        }
    }

    protected void continuousUpdate(JobContext<JSONObject> context, int characterCount, JSONObject json) {
        boolean toUpdate = true;
        if (source.size() > 100) {
            toUpdate = characterCount % (source.size() / 100) == 0;
        }
        if (toUpdate) {
            context.setPercentOfCompletion((int) (1.0* characterCount / source.size() * 100.0));
            context.updateLastResult(json);
        }
    }

    protected void config(JobContext<JSONObject> context) {
        context.setAutoFlush(true);
        context.setTitleMessage("Counting Tamil letters ...");
    }
}
