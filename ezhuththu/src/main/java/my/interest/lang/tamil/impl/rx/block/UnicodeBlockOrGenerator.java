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
            if(!blocks.contains(description)) {
                blocks.add(description);
            }
        }
        Collections.sort(blocks);

        List<String> patterns = new ArrayList<String>();

        int lastIndex = -1;
        int count = 0;
        for (UnicodeBlockDescription description : blocks) {
            count ++;
            if (exclude) {
               if (lastIndex + 1== description.getStartingCodePoint()) {
                   lastIndex = description.getEndingCodePoint() ;
                   continue;
               } else {
                   patterns.add("["+ UnicodeBlockDescription.getUniCodeString (lastIndex+1) + "-" +  UnicodeBlockDescription.getUniCodeString (description.getStartingCodePoint()-1) + "]");
                   lastIndex = description.getEndingCodePoint() ;
                   //last!
                   if ( count  == blocks.size() && lastIndex < LAST_UNICODE_CODEPOINT ) {
                       patterns.add("["+ UnicodeBlockDescription.getUniCodeString (lastIndex+1) + "-" +  UnicodeBlockDescription.getUniCodeString (LAST_UNICODE_CODEPOINT) + "]");
                   }
               }
            } else {
                patterns.add(description.generate(set));
            }



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
