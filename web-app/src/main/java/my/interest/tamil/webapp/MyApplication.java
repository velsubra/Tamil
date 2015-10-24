package my.interest.tamil.webapp;

import my.interest.tamil.rest.resources.*;
import my.interest.tamil.rest.resources.api.*;
import my.interest.tamil.rest.resources.api.defn.NounQuestionTreeResource;
import my.interest.tamil.rest.resources.apps.AppManagementResource;
import my.interest.tamil.rest.resources.apps.AppsAccessResource;
import my.interest.tamil.rest.resources.filters.TamilContainerFilter;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
@ApplicationPath("root")
public class MyApplication extends Application {


    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> set = new HashSet<Class<?>>();
        set.add(PunarchiResource.class);
        set.add(HandlerResource.class);
        set.add(RootVerbResource.class);
        set.add(ResourceExceptionMapper.class);
        set.add(VerbCoumpoundWordResource.class);
        set.add(NoCacheFilter.class);
        set.add(WordLookupResource.class);
        set.add(PeyarchcholResource.class);
        set.add(IdaichcholResource.class);
        set.add(TamilContainerFilter.class);
        set.add(DigestResource.class);

        set.add(AppsAccessResource.class);
        set.add(AppManagementResource.class);
        set.add(DictionaryLocatorResource.class);
        set.add(ParserResource.class);
        set.add(TranslitResource.class);
        set.add(NumberReaderResource.class);
        set.add(FileAccessResource.class);
        set.add(PersistenceResource.class);
        set.add(EzhuththuResource.class);
        set.add(NounQuestionTreeResource.class);
        set.add(JobResource.class);




        return set;
    }


}
