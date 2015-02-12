package my.interest.lang.tamil.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class DigestBeans {
    public List<DigestBean> getData() {
        return data;
    }

    public void setData(List<DigestBean> data) {
        this.data = data;
    }

    List<DigestBean> data = null;

    public static DigestBeans getDigestBean(String liststr) {
        DigestBeans beans = new DigestBeans();
        beans.data  = DigestBean.getDigestBean(liststr);
        return beans;
    }
}
