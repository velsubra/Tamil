package tamil.lang;

public enum Inam {
    NONE, MELLINAM, IDAIYINAM, VALLINAM;

    @Override
    public String toString() {
        return "_" + this.ordinal() + "_";
    }

}
