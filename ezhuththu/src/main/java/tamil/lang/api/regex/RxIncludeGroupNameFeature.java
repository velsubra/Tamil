package tamil.lang.api.regex;

/**
 * Created by velsubra on 6/27/16.
 *
 */

/**
 * Causes the regular expression engine to automatically include the group name as it is expanded.
 *
 */
public class RxIncludeGroupNameFeature extends RXFeature {
    private RxIncludeGroupNameFeature(){}

    public static final RxIncludeGroupNameFeature FEATURE  = new RxIncludeGroupNameFeature();
}
