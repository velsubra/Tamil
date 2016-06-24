package my.interest.lang.tamil.impl.rx.block;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.rx.ORPatternGenerator;
import tamil.lang.TamilCharacter;
import tamil.lang.exception.TamilPlatformException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by velsubra on 6/21/16.
 */
public class UnicodeBlockOrGenerator extends ORPatternGenerator {

    private boolean exclude = false;
    List<String> blockNames = null;

    public UnicodeBlockOrGenerator(List<String> blockNames, boolean exclude) {
        this.blockNames = blockNames;
        this.exclude = exclude;
    }

    @Override
    public List<String> getList(FeatureSet set) {
        List<UnicodeBlockDescription> blocks = new ArrayList<UnicodeBlockDescription>();
        for (String block : blockNames) {
            UnicodeBlockDescription description = UnicodeBlockDescription.find(block.trim());
            if (description == null) {
                throw new TamilPlatformException("Unknown unicode BMP block name:" + block);
            }
            blocks.add(description);
        }
        Collections.sort(blocks);

        List<String> patterns = new ArrayList<String>();
        UnicodeBlockDescription last = null; //center
        UnicodeBlockDescription lastlast = null;
        for (UnicodeBlockDescription description : blocks) {
            if (exclude) {
                if (last != null) {
                    patterns.add(new UnicodeBlockRelativeExcluder(last, lastlast == null? -1 : lastlast.getEndingCodePoint(), description.getStartingCodePoint()).generate(set));
                }
                lastlast = last;
                last = description;
            } else {
                patterns.add(description.generate(set));
            }


        }
        if (exclude && last != null) {
            patterns.add(new UnicodeBlockRelativeExcluder(last, lastlast == null? -1 : lastlast.getEndingCodePoint(), LAST_UNICODE_CODEPOINT + 1).generate(set));
        }
        return patterns;
    }



    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }

    public String getName() {
        return null;
    }

    public String getDescription() {
        return null;
    }
}
