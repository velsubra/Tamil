package my.interest.lang.tamil.xml;

import my.interest.lang.tamil.EzhuththuUtils;
import my.interest.lang.tamil.generated.types.AppDescription;
import my.interest.lang.tamil.generated.types.AppResource;
import my.interest.lang.tamil.generated.types.ResourceInheritanceOrder;
import my.interest.lang.tamil.generated.types.TamilRootWords;

import java.lang.ref.SoftReference;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class AppCache {
    public List<AppDescription> getInheritanceList() {
        return inheritanceList;
    }

    List<AppDescription> inheritanceList = new ArrayList<AppDescription>();

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
