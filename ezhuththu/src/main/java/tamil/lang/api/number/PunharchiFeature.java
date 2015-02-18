package tamil.lang.api.number;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class PunharchiFeature implements ReaderFeature {

    private PunharchiFeature(boolean full) {
        this.full = full;
    }

    public boolean isFull() {
        return full;
    }

    private boolean full = false;

    public static final PunharchiFeature INSTANCE_KEEP_ONLY_POSITION = new PunharchiFeature(false);
    public static final PunharchiFeature INSTANCE_FULL = new PunharchiFeature(true);
}
