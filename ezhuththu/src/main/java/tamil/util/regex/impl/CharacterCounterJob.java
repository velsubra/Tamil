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
 * A background character counter.
 * Created by vjhp on 10/25/2015.
 */
public class CharacterCounterJob  implements JobRunnable<JSONObject> {
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
        if (source.size() > 100) {
            boolean toUpdate = characterCount % (source.size()/100)  == 0;
            if (toUpdate) {
                context.setPercentOfCompletion((int)(characterCount/source.size() * 100.0));
                context.updateLastResult(json);
            }
        }
    }

    protected void config(JobContext<JSONObject> context) {
        context.setAutoFlush(true);
        context.setTitleMessage("Counting Tamil letters ...");
    }
}
