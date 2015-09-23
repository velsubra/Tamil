package my.interest.lang.tamil.impl.rx.asai1;

import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public  class NtearbuRx  extends YaappuBaseRx {
    protected NtearbuRx(String name) {
        super(name);
    }
    public NtearbuRx() {
        super("நேர்பு");
    }

    public String generate() {
        return  "((${ntear})(${valiyugaravarisai}))";
    }
}