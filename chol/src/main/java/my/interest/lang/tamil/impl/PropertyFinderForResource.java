package my.interest.lang.tamil.impl;

import my.interest.lang.tamil.generated.types.AppDescription;
import my.interest.lang.tamil.generated.types.AppResource;
import tamil.util.IPropertyFinder;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import tamil.lang.api.applet.AppletTamilFactory;

import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class PropertyFinderForResource implements IPropertyFinder {
    private AppDescription app;
    private AppResource resource;
    private Map<String, Object> map;
    private String relativeParent = null;

    static String local = PersistenceInterface.isOnCloud() ? "../" :"";

    /**
     * This is now hard coded here assuming what goes on web.xml. But this can be discovered as well.
     */
    static String REST_CONTEXT = PersistenceInterface.isOnCloud() ? "rest/" :"";


    public PropertyFinderForResource(AppDescription app, AppResource resource, Map<String, Object> map) {
        this.app = app;
        this.resource = resource;
        this.map = map;
        this.relativeParent = getRelativeParent();

    }

    @Override
    public String findProperty(String p1) {
//        if (p1.startsWith("skip:")) {
//            return "${" + p1.substring(5) +"}";
//        }

        if ("R_PLATFORM_CONTEXT".equals(p1)) {
            if (local.equals("")) {
                return "/xyz";
            } else {
                return "/tamil";
            }

        }

        if (app.getPlatform() == null) {
            app.setPlatform("1.1");
        }
        if ("R_PATH".equals(p1)) {
            return resource.getName();
        }
        if ("R_APP_NAME".equals(p1)) {
            return app.getName();
        }
        if ("R_PLATFORM_JS_PATH".equals(p1)) {
            //rest/apps/resources/<app_name> -- needs four level parent.
            return relativeParent + local + "../../../js/api/tamil-platform-api-" + app.getPlatform() + ".js";


        }
        if ("R_PLATFORM_CSS_PATH".equals(p1)) {
            //rest/apps/resources/<app_name> -- needs four level parent.
            return relativeParent + local + "../../../css/tamil-platform-" + app.getPlatform() + ".css";


        }

        if ("R_JQUERY_JS_PATH".equals(p1)) {
            //rest/apps/resources/<app_name> -- needs four level parent.
            return relativeParent + local + "../../../js/jquery/jquery-1.9.1.js";
        }

        if ("R_ANGULAR_JS_PATH".equals(p1)) {
            //rest/apps/resources/<app_name> -- needs four level parent.
            return relativeParent + local + "../../../js/angularjs/angular.min-1.4.8.js";
        }

        if ("R_INJECT_PLATFORM_APPLET".equals(p1)) {
            return "\n" +
                    "<object type=\"application/x-java-applet\"\n" +
                    "        id=\"TAMIL_APPLET_INJECTED\" name=\"TamilPlatform Applet\"\n" +
                    "        archive=\"" + relativeParent + local  +"../../../"+ REST_CONTEXT + "api/browse/tamil-letter-" +  app.getPlatform() +".jar\"\n" +
                    "        width=\"0\" height=\"0\">\n" +
                   "    <param name=\"code\"      value=\""+ AppletTamilFactory.class.getName() + "\" />\n" +
                    "   <param name=\"java_arguments\"      value=\"-Dfile.encoding=UTF-8\" />\n" +
                    "\n" +
                    "</object>";
        }


        if (map == null) {
            return null;
        } else {
            Object obj = map.get(p1);
            if (obj == null) {
                return null;
            } else {
                return obj.toString();
            }
        }
    }

    private String getRelativeParent() {
        String path = "";
        for (int i = 0; i < resource.getName().length(); i++) {
            if (resource.getName().charAt(i) == '/') {
                path = "../" + path;
            }
        }
        return path;
    }
}

