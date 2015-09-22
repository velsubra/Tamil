package my.interest.lang.tamil.xml;

import my.interest.lang.tamil.EzhuththuUtils;
import my.interest.lang.tamil.generated.types.AppDescription;
import my.interest.lang.tamil.generated.types.AppResource;
import my.interest.lang.tamil.generated.types.ResourceInheritanceOrder;
import my.interest.lang.tamil.generated.types.TamilRootWords;
import my.interest.lang.tamil.impl.ApplicationClassLoader;

import java.lang.ref.SoftReference;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * <p>
 * This is a transient cache object used per {@link my.interest.lang.tamil.generated.types.AppDescription}
 * </p>
 *
 * @author velsubra
 */
public class AppCache {


    public List<String> buildClassloader(AppDescription current) {
        String paths = current.getLibraryDependencies();
        this.groovyScriptEngine = null;
        this.appClassLoader = new ApplicationClassLoader(AppResource.class.getClassLoader());
        if (paths == null || paths.trim().equals("")) {

            return Collections.emptyList();
        }
        try {
            String[] urls = paths.split("\n");


            for (String u : urls) {
                u = u.trim();
                if (u.startsWith("#")) {
                    continue;
                }
                if (u.toLowerCase().endsWith(".pom")) {
                    if (u.contains(",")) {
                        int com = u.indexOf(",");
                        this.appClassLoader.addPom(u.substring(0, com), u.substring(com + 1, u.length()));

                    } else {
                        this.appClassLoader.addPom(u);
                    }
                } else if (u.toLowerCase().endsWith(".jar")) {
                    this.appClassLoader.addUrl(u);
                } else {
                    // classLoader.addUrl("Unsupported://" + u);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this.appClassLoader.getAllUrls();
    }

    public List<AppDescription> getInheritanceList() {
        return inheritanceList;
    }

    List<AppDescription> inheritanceList = new ArrayList<AppDescription>();

    public ApplicationClassLoader getAppClassLoader() {
        return appClassLoader;
    }

    ApplicationClassLoader appClassLoader = null;

    public Object getGroovyScriptEngine() {
        return groovyScriptEngine;
    }

    public void setGroovyScriptEngine(Object groovyScriptEngine) {
        this.groovyScriptEngine = groovyScriptEngine;
    }

    private Object groovyScriptEngine = null;

    public static AppCache serialize(String s) {
        return new AppCache();
    }

    private final Map<String, SoftReference<AppResource>> externalResource = new HashMap<String, SoftReference<AppResource>>();

    public static String deserialize(AppCache s) {
        return "";
    }


    public AppResource getExternalResource(String name) {
        SoftReference<AppResource> ref = externalResource.get(name);
        if (ref == null) {
            return null;
        } else {
            return ref.get();
        }
    }

    public void putExternalResource(AppResource resource) {
        externalResource.put(resource.getName(), new SoftReference<AppResource>(resource));
    }

    public void buildInheritanceOrder(TamilRootWords all, AppDescription current) {
        // ApplicationManager applicationManager =  TamilFactory.getPersistenceManager().getApplicationManager();
        inheritanceList.clear();
        inheritanceList.add(current);
        if (current.getResourceInheritance() == null || current.getResourceInheritance().getParentApps() == null) {
            return;
        }
        if (current.getResourceInheritance().getInheritanceOrder() == ResourceInheritanceOrder.BREADTH_FIRST) {
            Queue<AppDescription> q = new LinkedBlockingQueue<AppDescription>();
            for (String parent : current.getResourceInheritance().getParentApps()) {
                AppDescription parentapp = EzhuththuUtils.findApp(parent, all, false);
                if (parentapp == null) continue;
                if (inheritanceList.contains(parentapp)) continue;

                q.add(parentapp);

            }
            while (!q.isEmpty()) {
                AppDescription dequed = q.remove();
                if (!inheritanceList.contains(dequed)) {
                    inheritanceList.add(dequed);
                    if (dequed.getCache() == null) {
                        dequed.setCache(new AppCache());
                    }
                    dequed.getCache().buildInheritanceOrder(all, dequed);
                    q.addAll(dequed.getCache().inheritanceList);
                }

            }

        } else {
            for (String parent : current.getResourceInheritance().getParentApps()) {
                AppDescription parentapp = EzhuththuUtils.findApp(parent, all, false);
                if (parentapp == null) continue;
                if (inheritanceList.contains(parentapp)) continue;
                if (parentapp.getCache() == null) {
                    parentapp.setCache(new AppCache());
                }
                parentapp.getCache().buildInheritanceOrder(all, parentapp);
                for (AppDescription depth : parentapp.getCache().inheritanceList) {
                    if (!inheritanceList.contains(depth)) {
                        inheritanceList.add(depth);
                    }
                }
            }
        }


    }

}
