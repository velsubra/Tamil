package tamil.lang.unicode;

import tamil.lang.OliAlavu;
import tamil.lang.UyirEzhuthu;

import static tamil.lang.OliAlavu.*;

public class UnicodeUyirezhuthu implements UyirEzhuthu {
    public static UnicodeUyirezhuthu a = new UnicodeUyirezhuthu('\u0B85', KURIL);
    public static UnicodeUyirezhuthu aa = new UnicodeUyirezhuthu('\u0B86', NEDIL);
    public static UnicodeUyirezhuthu E = new UnicodeUyirezhuthu('\u0B87', KURIL);
    public static UnicodeUyirezhuthu EE = new UnicodeUyirezhuthu('\u0B88', NEDIL);
    public static UnicodeUyirezhuthu U = new UnicodeUyirezhuthu('\u0B89', KURIL);
    public static UnicodeUyirezhuthu UU = new UnicodeUyirezhuthu('\u0B8A', NEDIL);
    public static UnicodeUyirezhuthu A = new UnicodeUyirezhuthu('\u0B8E', KURIL);
    public static UnicodeUyirezhuthu AA = new UnicodeUyirezhuthu('\u0B8F', NEDIL);
    public static UnicodeUyirezhuthu I = new UnicodeUyirezhuthu('\u0B90', NEDIL);
    public static UnicodeUyirezhuthu O = new UnicodeUyirezhuthu('\u0B92', KURIL);
    public static UnicodeUyirezhuthu OO = new UnicodeUyirezhuthu('\u0B93', NEDIL);
    public static UnicodeUyirezhuthu OU = new UnicodeUyirezhuthu('\u0B94', NEDIL);
    private int value;
    private OliAlavu oliAlavu;

    public UnicodeUyirezhuthu(int value, OliAlavu oliAlavu) {
        this.value = value;
        this.oliAlavu = oliAlavu;
    }

    public boolean isKuril() {
        return KURIL.equals(oliAlavu);
    }

    public boolean isNedil() {
        return NEDIL.equals(oliAlavu);
    }
}
