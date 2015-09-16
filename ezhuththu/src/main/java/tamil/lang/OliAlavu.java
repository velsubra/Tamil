package tamil.lang;

public enum OliAlavu {
    NONE(0.5), KURIL(1.0), NEDIL(2.0);

    private double soundSize;

    OliAlavu(double soundSize) {
        this.soundSize = soundSize;
    }

    @Override
    public String toString() {
        return "_" + soundSize + "_";
    }
}
