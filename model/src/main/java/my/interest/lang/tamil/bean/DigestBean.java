package my.interest.lang.tamil.bean;

import common.lang.impl.UnknownCharacter;
import my.interest.lang.tamil.TamilUtils;
import tamil.lang.TamilWord;
import tamil.lang.CharacterDigest;
import my.interest.lang.tamil.punar.TamilWordPartContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class DigestBean {

    public List<String> getLabels() {
        return labels;
    }

    public List<Dataset> getDatasets() {
        return datasets;
    }

    List<String> labels = new ArrayList<String>();
    List<Dataset> datasets = new ArrayList<Dataset>();

    public static List<DigestBean> getDigestBean(String liststr) {
        List<String> list = new ArrayList<String>(TamilUtils.parseAndRemoveDuplicatesAsSet(liststr, ","));
        List<TamilWordPartContainer> temp = new ArrayList<TamilWordPartContainer>();
        int max = 0;
        for (String s : list) {
            s = s.trim();
            TamilWordPartContainer c = new TamilWordPartContainer(TamilWord.from(s));
            if (max < c.getWord().size()) {
                max = c.getWord().size();
            }
            temp.add(c);
        }


        List<DigestBean> beans = new ArrayList<DigestBean>();
        List<TamilWordPartContainer> containers = new ArrayList<TamilWordPartContainer>();
        for (TamilWordPartContainer c : temp) {
            TamilWord w = c.getWord();
            while (w.size() < max) {
                w.add(new UnknownCharacter('X'));
            }
            TamilWordPartContainer cont = new TamilWordPartContainer(w);
            containers.add(cont);


        }

        List<String> commonlabels = new ArrayList<String>();
        for (int i = 0; i < max; i++) {
            TamilWord label = new TamilWord();
            for (TamilWordPartContainer c : temp) {
                label.add(c.getWord().get(i));
                label.add(new UnknownCharacter(','));
            }
            commonlabels.add(label.toString());
        }
        beans.add(createFor(commonlabels, containers, 0));
        beans.add(createFor(commonlabels, containers, 1));
        beans.add(createFor(commonlabels, containers, 2));
        beans.add(createFor(commonlabels, containers, 3));
        beans.add(createFor(commonlabels, containers, 4));

        return beans;
    }

    private static DigestBean createFor(List<String> commonlabels, List<TamilWordPartContainer> temp, int digestType) {
        DigestBean bean = new DigestBean();
        bean.labels = commonlabels;
        CharacterDigest digest = null;
        for (TamilWordPartContainer c : temp) {
            switch (digestType) {
                case 4:
                    digest = c.getDIGEST_CHARACTER_TYPE();
                    break;
                case 1:
                    digest = c.getDIGEST_CONSONANT();
                    break;
                case 3:
                    digest = c.getDIGEST_SOUND_STRENGTH();
                    break;
                case 0:
                    digest = c.getDIGEST_VOWEL();
                    break;
                case 2:
                    digest = c.getDIGEST_SOUND_SIZE();
                    break;
            }
            bean.datasets.add(Dataset.forName(c, digest));
        }
        return bean;
    }


    public static class Dataset {
        public String getLabel() {
            return label;
        }

        public String getFillColor() {
            return fillColor;
        }

        public String getStrokeColor() {
            return strokeColor;
        }

        public String getPointColor() {
            return pointColor;
        }

        public String getPointStrokeColor() {
            return pointStrokeColor;
        }

        public String getPointHighlightFill() {
            return pointHighlightFill;
        }

        public List<Double> getData() {
            return data;
        }

        public String getPointHighlightStroke() {
            return pointHighlightStroke;
        }

        private String label = null;
        private String fillColor = null;
        private String strokeColor = null;
        private String pointColor = null;
        private String pointStrokeColor = null;
        private String pointHighlightFill = null;
        private String pointHighlightStroke = null;
        List<Double> data = null;

        public static Dataset forName(TamilWordPartContainer container, CharacterDigest digest) {
            String name = container.getWord().toString();
            Dataset data = new Dataset();
            data.label = name;
            int code = name.hashCode() % 256;
            if (code < 0) {
                code = -code;
            }
            if (code == 0) {
                code = 100;
            }
            data.fillColor = "rgba(" + code + "," + (code + 20) % 256 + "," + (code + 10) % 256 + ",0.2)";
            data.strokeColor = "rgba(" + code + "," + (code + 20) % 256 + "," + (code + 10) % 256 + ",1.0)";
            data.pointColor = "rgba(" + code + "," + code + "," + code + ",1.0)";
            data.pointStrokeColor = "#fff";
            data.pointHighlightFill = "#fff";
            data.pointHighlightStroke = "rgba(" + code + "," + code + "," + code + ",1.0)";
            data.data = digest.getNumberArray();
            return data;
        }

    }
}
