package tamil.lang.api.regex;

/**
 * <p>
 * The feature that allows to treat ஃ as குறில் with 1 மாத்திரை .
 *  {@link <a href="https://www.facebook.com/Harisancruth">ஐயா அரியவர்கள்</a>} கூறிய கருத்து
 * <p/>
 * ஃ பெரும்பாலன இடங்களில் அலகு பெறாது; சில இடங்களில் பெறும்.
 * <p/>
 * ஓர்ந்துகண் ணோடாது இறைபுரிந்து யார்மாட்டும்<br/>
 * தேர்ந்துசெய் வஃதே முறை.
 * <p/>
 * வஃதே தேமாவாகக் கொள்ளப்பட்டுள்ளது.
 * <p/>
 * கற்றில னாயினுங் கேட்க அஃதொருவற்<br/>
 * கொற்கத்தின் ஊற்றாந் துணை
 * <p/>
 * கேட்க என்னும் மாச்சீரைத் தொடரும் அஃகொருவற் என்பதில் ஃ அலகுபெற்று, நிரையசையாகிறது.
 * </p>
 *
 * @author velsubra
 */
public final class RXAythamAsKurrilFeature extends RXFeature {

    private RXAythamAsKurrilFeature() {
    }

    public static final RXAythamAsKurrilFeature FEATURE = new RXAythamAsKurrilFeature();
}
