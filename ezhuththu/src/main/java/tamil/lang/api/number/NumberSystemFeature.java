package tamil.lang.api.number;

/**
 * Created by velsubra on 6/28/16.
 */
public class NumberSystemFeature implements NumberReaderFeature {

    public  enum NumberSystem {DEFAULT, ILANGO_PICHCHANDI, JEYAPANDIAN_K, WIKI}


    private NumberSystem system;


    private NumberSystemFeature(NumberSystem system) {
        this.system = system;
    }

    public static final NumberSystemFeature DEFAULT = new NumberSystemFeature(NumberSystem.DEFAULT);
    public static final NumberSystemFeature ILANGO_PICHCHANDI = new NumberSystemFeature(NumberSystem.ILANGO_PICHCHANDI);
    public static final NumberSystemFeature JEYAPANDIAN_K = new NumberSystemFeature(NumberSystem.JEYAPANDIAN_K);
    public static final NumberSystemFeature WIKI = new NumberSystemFeature(NumberSystem.WIKI);

}
