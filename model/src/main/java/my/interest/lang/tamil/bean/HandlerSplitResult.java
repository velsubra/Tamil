package my.interest.lang.tamil.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Represents the split results created by a single handler.
 * </p>
 *
 * @author velsubra
 */
public class HandlerSplitResult  {
    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public String getHandlerDescription() {
        return handlerDescription;
    }

    public void setHandlerDescription(String handlerDescription) {
        this.handlerDescription = handlerDescription;
    }

    public List<SimpleSplitResult> getSplitLists() {
        return splitLists;
    }

    public void setSplitLists(List<SimpleSplitResult> splitLists) {
        this.splitLists = splitLists;
    }

    private List<SimpleSplitResult> splitLists = new ArrayList<SimpleSplitResult>();

    private String handlerName;
    private String handlerDescription;

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(handlerName);
        buffer.append("\n" + super.toString() +"\n\n");
        return buffer.toString();
    }

}
