package my.interest.lang.tamil.internal.api;

import my.interest.lang.tamil.generated.types.IdaichcholDescription;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public interface IdaichcholDescriptionMatcher extends DescriptionMatcher<IdaichcholDescription>  {

    public static final IdaichcholDescriptionMatcher STARTS_MATCHER = new IdaichcholDescriptionMatcher() {

        @Override
        public boolean matches(IdaichcholDescription pattern, IdaichcholDescription root, PersistenceInterface per) {
            return root.getRoot().startsWith(pattern.getRoot());
        }
    };

    public static final IdaichcholDescriptionMatcher TRAVERSE = new IdaichcholDescriptionMatcher() {

        @Override
        public boolean matches(IdaichcholDescription pattern, IdaichcholDescription root, PersistenceInterface per) {
            return true;
        }
    };




    public static final IdaichcholDescriptionMatcher EXACT_MATCHER = new IdaichcholDescriptionMatcher() {

        @Override
        public boolean matches(IdaichcholDescription pattern, IdaichcholDescription root, PersistenceInterface per) {
            return root.getRoot().equals(pattern.getRoot());
        }
    };

    public static final IdaichcholDescriptionMatcher ISSUE = new IdaichcholDescriptionMatcher() {

        @Override
        public boolean matches(IdaichcholDescription pattern, IdaichcholDescription root, PersistenceInterface per) {
            return per.getConsolidatedPropertyContainerFor(root).isMarkedWithIssue();
        }
    };

    public static final IdaichcholDescriptionMatcher DEFN_UNLOCKED = new IdaichcholDescriptionMatcher() {

        @Override
        public boolean matches(IdaichcholDescription pattern, IdaichcholDescription root, PersistenceInterface per) {
            return !per.getConsolidatedPropertyContainerFor(root).isDefintionLocked();
        }
    };

    public static final IdaichcholDescriptionMatcher NEW = new IdaichcholDescriptionMatcher() {

        @Override
        public boolean matches(IdaichcholDescription pattern, IdaichcholDescription root, PersistenceInterface per) {
            return per.getConsolidatedPropertyContainerFor(root).isDeletable();
        }
    };

}
