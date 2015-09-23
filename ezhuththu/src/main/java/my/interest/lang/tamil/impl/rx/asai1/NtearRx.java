package my.interest.lang.tamil.impl.rx.asai1;

import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public  class NtearRx extends YaappuBaseRx {

    protected NtearRx(String name) {
        super(name);
    }

    public NtearRx() {
        super("நேர்");
    }

    public String generate() {
        return "(?!(${ntirai}))((${kuttukkurril}${mey}*)(${kuttuntedil}${mey}*)|(${ntedil}${mey}*)|(${kurril}${mey}*))";
    }
}