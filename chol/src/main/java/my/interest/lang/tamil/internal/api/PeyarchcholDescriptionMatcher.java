package my.interest.lang.tamil.internal.api;

import my.interest.lang.tamil.generated.types.PeyarchcholDescription;
import my.interest.lang.tamil.punar.PropertyDescriptionContainer;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public interface PeyarchcholDescriptionMatcher extends DescriptionMatcher<PeyarchcholDescription> {

    public static final PeyarchcholDescriptionMatcher STARTS_MATCHER = new PeyarchcholDescriptionMatcher() {

        @Override
        public boolean matches(PeyarchcholDescription pattern, PeyarchcholDescription root, PersistenceInterface per) {
            return root.getRoot().startsWith(pattern.getRoot());
        }
    };

    public static final PeyarchcholDescriptionMatcher TRAVERSE = new PeyarchcholDescriptionMatcher() {

        @Override
        public boolean matches(PeyarchcholDescription pattern, PeyarchcholDescription root, PersistenceInterface per) {
            return true;
        }
    };


    public static final PeyarchcholDescriptionMatcher EXACT_MATCHER = new PeyarchcholDescriptionMatcher() {

        @Override
        public boolean matches(PeyarchcholDescription pattern, PeyarchcholDescription root, PersistenceInterface per) {
            return root.getRoot().equals(pattern.getRoot());
        }
    };

    public static final PeyarchcholDescriptionMatcher ISSUE = new PeyarchcholDescriptionMatcher() {

        @Override
        public boolean matches(PeyarchcholDescription pattern, PeyarchcholDescription root, PersistenceInterface per) {
            return per.getConsolidatedPropertyContainerFor(root).isMarkedWithIssue();
        }
    };
    public static final PeyarchcholDescriptionMatcher DEFN_UNLOCKED = new PeyarchcholDescriptionMatcher() {

        @Override
        public boolean matches(PeyarchcholDescription pattern, PeyarchcholDescription root, PersistenceInterface per) {
            return !per.getConsolidatedPropertyContainerFor(root).isDefintionLocked();
        }
    };

    public static final PeyarchcholDescriptionMatcher NEW = new PeyarchcholDescriptionMatcher() {

        @Override
        public boolean matches(PeyarchcholDescription pattern, PeyarchcholDescription root, PersistenceInterface per) {
            return per.getConsolidatedPropertyContainerFor(root).isDeletable();
        }
    };

    public static final class ValueBasedMatcher implements PeyarchcholDescriptionMatcher {
        private String propName = null;
        private String propValue = null;

        public ValueBasedMatcher(String prop, String val) {
            this.propName = prop;
            this.propValue = val;
        }

        @Override
        public boolean matches(PeyarchcholDescription pattern, PeyarchcholDescription root, PersistenceInterface per) {
            PropertyDescriptionContainer props = per.getConsolidatedPropertyContainerFor(root);
            String val = props.findProperty(this.propName);
            if (val == null) {
                return this.propValue == null;
            } else {
                return val.equals(this.propValue);
            }
        }
    }

}
