package tamil.util.regex;

import common.lang.impl.AbstractCharacter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;

import java.util.*;

/**
 * <p>
 * Simple matcher interface
 * </p>
 *
 * @author velsubra
 * @see tamil.lang.api.regex.TamilRXCompiler#compileToPatternsList(String, tamil.util.IPropertyFinder, java.util.List, tamil.lang.api.regex.RXFeature...)
 * @see tamil.lang.TamilFactory#transpose(SimpleMatcher)
 */
public interface SimpleMatcher {

    /**
     * Finds the next pattern . If the matcher is transposed (see {@link #isTransposed()}), it will match what the underlying pattern could not match.
     *
     * @return true if the pattern is found
     */
    public boolean find();

    /**
     * Gets the starting index of the pattern
     *
     * @return the index if the matcher is looking at the match else it throws exception.
     */
    public int start();

    /**
     * Returns the offset after the last character matched.
     *
     * @return
     */
    public int end();

    /**
     * The string representing the underlying pattern
     *
     * @return the pattern
     */
    public String getPattern();

    /**
     * Checks if the matcher is transposed such that it matches what it would skip if it were not transposed.
     *
     * @return true if it is transposed, false otherwise
     */
    public boolean isTransposed();

    /**
     * Gets the underlying source string's length
     *
     * @return the length of source string.
     */
    public int getSourceLength();

    /**
     * Gets the group name
     *
     * @return
     */
    public String group();

    public String group(String name);

    public int groupCount();

    public String group(int group);


    /**
     * builds the current model; built based on the grouping.
     *
     * @return the matching model
     * @see tamil.lang.api.feature.FeatureConstants#RX_INCLUDE_GROUP_NAME_VAL_187
     */
    public MatchingModel buildMatchingModel();

    public class MatchingModelPart implements Comparable<MatchingModelPart> {
        public int start;
        public int end;
        public int tamilStart;
        public int tamilEnd;

        public MatchingModelPart(int start, int end, String groupName, int tamilStart, int tamilEnd) {
            this.start = start;
            this.end = end;
            namedGroups.add(groupName);
            this.tamilStart = tamilStart;
            this.tamilEnd = tamilEnd;
        }

        public final List<String> namedGroups = new ArrayList<String>();

        private Set<String> getGroupNames() {
            Set<String> set = new HashSet<String>();
            for (String g : namedGroups) {
                TamilWord w = TamilFactory.getTransliterator(null).transliterate(g);
                set.add(w.toString());
            }
            return  set;
        }

        public void addGroupName(String name) {
            if (!namedGroups.contains(name)) {
                namedGroups.add(name);
            }
        }

        public int compareTo(MatchingModelPart o) {
            int ret = new Integer(start).compareTo(o.start);
            if (ret == 0) {
                ret = new Integer(o.end).compareTo(end);
            }
            return ret;
        }


        public JSONObject asJson() throws JSONException {
            JSONObject json = new JSONObject();


            json.put("start", start);
            json.put("end", end);
            json.put("tamilStart", tamilStart);
            json.put("tamilEnd", tamilEnd);
            Collections.sort(this.namedGroups);
            JSONArray groups = new JSONArray();
            json.put("groups", groups);
            for (String group : this.namedGroups) {
                groups.put(group);
            }
            return json;
        }
    }

    public class MatchingModel {
        public MatchingModel(String matchedText, String pattern) {
            this.matchedText = matchedText;
            tamil = TamilWord.from(matchedText, true);
            tamil.setLocked();
            this.pattern = pattern;
        }

        public String matchedText;
        public TamilWord tamil;
        public String pattern;
        public final List<MatchingModelPart> parts = new ArrayList<MatchingModelPart>();

        public MatchingModelPart findMatchingModelPart(int start, int end) {
            for (MatchingModelPart part : parts) {
                if (part.start == start && end == part.end) {
                    return part;
                }

            }
            return null;
        }

        public void addMatchingModelPart(int start, int end, String groupName) {
            MatchingModelPart part = findMatchingModelPart(start, end);
            if (part == null) {
                part = new MatchingModelPart(start, end, groupName, tamil.getIndexForCodepointIndex(start), end == matchedText.length() ? tamil.size() : tamil.getIndexForCodepointIndex(end));
                parts.add(part);
            } else {
                part.addGroupName(groupName);

            }
        }

        public JSONObject asGoJsJson() throws JSONException {
            Collections.sort(this.parts);
            final  int horizontalspacing = 30;
            final  int offset = 30;
            final  int verticalspacing = 25;
            JSONObject json = new JSONObject();
            JSONArray nodes = new JSONArray();
            json.put("nodes", nodes);
            int count = 0;
            for (AbstractCharacter c : tamil) {
                JSONObject node = new JSONObject();
                int x = count;
                int y = tamil.size()-1;
                node.put("key", "key_" + x +"_"+y);
                node.put("text", c.toString());
                node.put("color", "lightblue");
                node.put("loc", ((x * horizontalspacing) + offset) + " " + (verticalspacing * (y-1) + offset) );
                nodes.put(node);
                count++;
            }

            int lastChar = -1;

            for (MatchingModelPart part : parts) {
                //same character is ignored
                if (part.tamilStart == part.tamilEnd - 1) continue;
                if (lastChar == part.tamilStart) continue;
                if (lastChar < part.tamilStart) {
                    for (int i = part.tamilStart + 1; i< part.tamilEnd ; i++ ) {
                        JSONObject node = new JSONObject();
                        int x = part.tamilStart;
                        int y = (tamil.size()-(i - part.tamilStart - 1) -2);
                        node.put("key", "key_" + x +"_"+y);

                        node.put("text", tamil.get(i).toString());
                        node.put("color", "orange");
                        node.put("loc", ((x * horizontalspacing) + offset) + " " + ((verticalspacing * (y-1)) + offset) );
                        nodes.put(node);
                    }
                    lastChar = part.tamilStart;
                }

            }

            JSONArray links = new JSONArray();
            json.put("links", links);
            for (MatchingModelPart part : parts) {
                //same character is ignored
                if (part.tamilStart == part.tamilEnd - 1) continue;

                int tox = part.tamilEnd - 1;
                int toy = tamil.size()-1;

                int fromx = part.tamilStart;
                int fromy = tamil.size()   -  (part.tamilEnd - part.tamilStart);
                //int fromy = tamil.size()   -  1;
                JSONObject link = new JSONObject();
                link.put("from", "key_" + fromx +"_"+fromy);
                link.put("to", "key_" + tox +"_"+toy);
                link.put("text", part.getGroupNames().toString());
                links.put(link);

            }
            JSONObject meta = new JSONObject();
            meta.put("pattern", this.pattern);
            json.put("meta", meta);
            return json;
        }

        public JSONObject asJson() throws JSONException {
            JSONObject json = new JSONObject();
            json.put("matchedText", matchedText);
            JSONArray tamilChars = new JSONArray();
            json.put("tamilChars", tamilChars);
            for (AbstractCharacter c : tamil) {
                tamilChars.put(c.toString());
            }
            JSONArray parts = new JSONArray();
            json.put("parts", parts);
            Collections.sort(this.parts);
            for (MatchingModelPart part : this.parts) {
                parts.put(part.asJson());
            }
            return json;
        }

    }

}
