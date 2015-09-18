package tamil.lang;

public enum Inam {
    NONE(0), VALLINAM(3), MELLINAM(1), IDAIYINAM(2);

    private int digest;

    Inam(int digest) {
        this.digest = digest;
    }

    @Override
    public String toString() {
        return "_" + digest + "_";
    }

}
