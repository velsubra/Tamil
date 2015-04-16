package my.interest.lang.tamil.parser.impl;

import my.interest.lang.tamil.EzhuththuUtils;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilPage;
import tamil.lang.TamilPara;

import my.interest.lang.tamil.internal.api.TamilCharacterParserListener;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TamilPageListener implements TamilCharacterParserListener<TamilPage> {

    TamilParaListener currentPara = new TamilParaListener();
    TamilPage page = new TamilPage();

    /**
     * Called when a tamil character is encountered
     *
     * @param tamil
     * @return true when parsing is to be finished, false otherwise
     */
    @Override
    public boolean tamilCharacter(TamilCharacter tamil) {
        if (currentPara.tamilCharacter(tamil)) {
            page.add(currentPara.get());
            currentPara = new TamilParaListener();

        }
        return false;
    }

    @Override
    public boolean nonTamilCharacter(int nonTamil) {


        if (currentPara.nonTamilCharacter(nonTamil)) {
            page.add(currentPara.get());
            currentPara = new TamilParaListener();

        }

        return false;
    }

    @Override
    public TamilPage get() {
        TamilPara last = currentPara.get();
        if (!last.isEmpty()) {
            page.add(last);
            currentPara = new TamilParaListener();
        }
        return page;
    }

    public static TamilPage readUTF8(String page) {
        try {
            return readUTF8(new ByteArrayInputStream(page.getBytes(EzhuththuUtils.ENCODING)));
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static TamilPage readUTF8(InputStream inputStream) {
        try {
            return EzhuththuUtils.readUTF8(inputStream, new TamilPageListener());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
