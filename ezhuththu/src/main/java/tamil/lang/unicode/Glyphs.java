package tamil.lang.unicode;

public enum Glyphs {
    PULLI('\u0BCD'),
    NONE,
    aa('\u0BBE'),
    E('\u0BBF'),
    EE('\u0BC0'),
    U('\u0BC1'),
    UU('\u0BC2'),
    A('\u0BC6'),
    AA('\u0BC7'),
    I('\u0BC8'),
    O('\u0BCA'),
    OO('\u0BCB'),
    OU('\u0BCC');

    private int value;

    Glyphs(int value) {
        this.value = value;
    }

    Glyphs() {
    }
}
