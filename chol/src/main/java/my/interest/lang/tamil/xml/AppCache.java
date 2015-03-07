package my.interest.lang.tamil.xml;

import my.interest.lang.tamil.generated.types.AppDescription;
import my.interest.lang.tamil.generated.types.ResourceInheritanceOrder;
import my.interest.lang.tamil.generated.types.TamilRootWords;
import my.interest.lang.tamil.internal.api.PersistenceInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
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

    public static String deserialize(AppCache s) {
        return "";
    }

    public void buildInheritanceOrder(TamilRootWords all, AppDescription current) {
        inheritanceList.clear();
        inheritanceList.add(current);
        if (current.getResourceInheritance() == null || current.getResourceInheritance().getParentApps() == null) {
            return;
        }
        if (current.getResourceInheritance().getInheritanceOrder() == ResourceInheritanceOrder.BREADTH_FIRST) {
            Queue<AppDescription> q = new LinkedBlockingQueue<AppDescription>();
            for (String parent : current.getResourceInheritance().getParentApps()) {
                AppDescription parentapp = PersistenceInterface.findApp(parent, all, false);
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
                AppDescription parentapp = PersistenceInterface.findApp(parent, all, false);
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
