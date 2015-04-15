package tamil.lang.api.persist.matcher;

import my.interest.lang.tamil.generated.types.PeyarchcholDescription;
import my.interest.lang.tamil.punar.PropertyDescriptionContainer;
import tamil.lang.api.persist.manager.PersistenceManager;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public interface PeyarchcholDescriptionMatcher extends DescriptionMatcher<PeyarchcholDescription> {

    public static final PeyarchcholDescriptionMatcher FOR_APPLET_MATCHER = new PeyarchcholDescriptionMatcher() {

        @Override
        public boolean matches(PeyarchcholDescription pattern, PeyarchcholDescription root, PersistenceManager per) {
            PropertyDescriptionContainer container = per.getNounManager().getConsolidatedPropertyContainerFor(root);
            return  container.isUyarthinhaipPeyar() || container.getProNounMaruvi() != null;
        }
    };


    public static final PeyarchcholDescriptionMatcher STARTS_MATCHER = new PeyarchcholDescriptionMatcher() {

        @Override
        public boolean matches(PeyarchcholDescription pattern, PeyarchcholDescription root, PersistenceManager per) {
            return root.getRoot().startsWith(pattern.getRoot());
        }
    };

    public static final PeyarchcholDescriptionMatcher TRAVERSE = new PeyarchcholDescriptionMatcher() {

        @Override
        public boolean matches(PeyarchcholDescription pattern, PeyarchcholDescription root, PersistenceManager per) {
            return true;
        }
    };


    public static final PeyarchcholDescriptionMatcher EXACT_MATCHER = new PeyarchcholDescriptionMatcher() {

        @Override
        public boolean matches(PeyarchcholDescription pattern, PeyarchcholDescription root, PersistenceManager per) {
            return root.getRoot().equals(pattern.getRoot());
        }
    };

    public static final PeyarchcholDescriptionMatcher ISSUE = new PeyarchcholDescriptionMatcher() {

        @Override
        public boolean matches(PeyarchcholDescription pattern, PeyarchcholDescription root, PersistenceManager per) {
            return per.getNounManager().getConsolidatedPropertyContainerFor(root).isMarkedWithIssue();
        }
    };
    public static final PeyarchcholDescriptionMatcher DEFN_UNLOCKED = new PeyarchcholDescriptionMatcher() {

        @Override
        public boolean matches(PeyarchcholDescription pattern, PeyarchcholDescription root, PersistenceManager per) {
            return !per.getNounManager().getConsolidatedPropertyContainerFor(root).isDefintionLocked();
        }
    };

    public static final PeyarchcholDescriptionMatcher NEW = new PeyarchcholDescriptionMatcher() {

        @Override
        public boolean matches(PeyarchcholDescription pattern, PeyarchcholDescription root, PersistenceManager per) {
            return per.getNounManager().getConsolidatedPropertyContainerFor(root).isDeletable();
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
        public boolean matches(PeyarchcholDescription pattern, PeyarchcholDescription root, PersistenceManager per) {
            PropertyDescriptionContainer props = per.getNounManager().getConsolidatedPropertyContainerFor(root);
            String val = props.findProperty(this.propName);
            if (val == null) {
                return this.propValue == null;
            } else {
                return val.equals(this.propValue);
            }
        }
    }

}
