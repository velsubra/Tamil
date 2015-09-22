package tamil.lang.api.parser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class ParserResultCollection {

    public List<ParserResult> getList() {
        return list;
    }

    private final LinkedList<ParserResult> list = new LinkedList<ParserResult>();

    public void add(ParserResult result) {
        int before = list.size();

        for (int i =0; i <  list.size(); i++) {
             if (result.isParsed()) {
                 if (result.getSplitWords().size() < list.get(i).getSplitWords().size()) {
                     before = i;
                     break;
                 }
             }
        }
        list.add(before,result);
    }

    public boolean isEmpty() {
        return  list.isEmpty();
    }

    public int size() {
        return  list.size();
    }

    public boolean contains(ParserResult result) {
        return list.contains(result);
    }
}
