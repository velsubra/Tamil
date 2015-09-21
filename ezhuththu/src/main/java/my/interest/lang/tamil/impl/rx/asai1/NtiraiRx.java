package my.interest.lang.tamil.impl.rx.asai1;

import my.interest.lang.tamil.impl.yaappu.AsaiRx;
import my.interest.lang.tamil.internal.api.PatternGenerator;

/**
 * <p>
 *     நிரையசைக்கான செங்கோவை
 * </p>
 *
 * @author velsubra
 */
public final class NtiraiRx  extends AsaiRx {

    public NtiraiRx() {
        super("நிரை");
    }

    public String generate() {
      //  return  "((${kuttukkurril}${ntedil}${mey}*)|(${kuttukkurril}${kurril}${mey}*)|(${kurril}${kuttuntedil}${mey}*)|(${kuttukkurril}${kuttukkurril}${mey}*)|(${kurril}${ntedil}${mey}*)|(${kurril}${kurril}${mey}*))";
        return   "((${குற்றுக்குறில்}${நெடில்}${மெய்}*)|(${குற்றுக்குறில்}${குறில்}${மெய்}*)|(${குறில்}${குற்றுநெடில்}${மெய்}*)|(${குற்றுக்குறில்}${குற்றுக்குறில்}${மெய்}*)|(${குறில்}${நெடில்}${மெய்}*)|(${குறில்}${குறில்}${மெய்}*))";
    }
}