package tamil.lang.api.persist.matcher;

import my.interest.lang.tamil.generated.types.RootVerbDescription;
import my.interest.lang.tamil.punar.PropertyDescriptionContainer;
import tamil.lang.api.persist.manager.PersistenceManager;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public interface RootVerbDescriptionMatcher extends DescriptionMatcher<RootVerbDescription> {


    public static final RootVerbDescriptionMatcher STARTS_MATCHER = new RootVerbDescriptionMatcher() {

        @Override
        public boolean matches(RootVerbDescription pattern, RootVerbDescription root, PersistenceManager per) {
            return root.getRoot().startsWith(pattern.getRoot());
        }
    };


    public static final RootVerbDescriptionMatcher TRAVERSE = new RootVerbDescriptionMatcher() {

        @Override
        public boolean matches(RootVerbDescription pattern, RootVerbDescription root, PersistenceManager per) {
            return true;
        }
    };


    public static final RootVerbDescriptionMatcher EXACT_MATCHER = new RootVerbDescriptionMatcher() {

        @Override
        public boolean matches(RootVerbDescription pattern, RootVerbDescription root, PersistenceManager per) {
            return root.getRoot().equals(pattern.getRoot());
        }
    };

    public static final RootVerbDescriptionMatcher ISSUE = new RootVerbDescriptionMatcher() {

        @Override
        public boolean matches(RootVerbDescription pattern, RootVerbDescription root, PersistenceManager per) {
            return per.getRootVerbManager().getConsolidatedPropertyContainerFor(root).isMarkedWithIssue();
        }
    };

    public static final RootVerbDescriptionMatcher VINAIMUTTU_UNLOCKED = new RootVerbDescriptionMatcher() {

        @Override
        public boolean matches(RootVerbDescription pattern, RootVerbDescription root, PersistenceManager per) {
            return !per.getRootVerbManager().getConsolidatedPropertyContainerFor(root).isVinaiMuttuLocked();
        }
    };

    public static final RootVerbDescriptionMatcher NEW = new RootVerbDescriptionMatcher() {

        @Override
        public boolean matches(RootVerbDescription pattern, RootVerbDescription root, PersistenceManager per) {
            return per.getRootVerbManager().getConsolidatedPropertyContainerFor(root).isDeletable();
        }
    };


    public static final RootVerbDescriptionMatcher NO_CHANGE_ON_THOZHIRPEYAR = new RootVerbDescriptionMatcher() {

        @Override
        public boolean matches(RootVerbDescription pattern, RootVerbDescription root, PersistenceManager per) {
            PropertyDescriptionContainer container = per.getRootVerbManager().getConsolidatedPropertyContainerFor(root);
            return container.getOtherThozhiPeyars(true).isEmpty() && container.getOtherThozhiPeyars(false).isEmpty();
        }
    };

    public static final RootVerbDescriptionMatcher NO_CHANGE_ON_ENGLISH = new RootVerbDescriptionMatcher() {

        @Override
        public boolean matches(RootVerbDescription pattern, RootVerbDescription root, PersistenceManager per) {
            PropertyDescriptionContainer container = per.getRootVerbManager().getConsolidatedPropertyContainerFor(root);
            return container.getEnglishMeaningForVerb(true).isEmpty() && container.getEnglishMeaningForVerb(false).isEmpty();
        }
    };



}
