package my.interest.lang.tamil.bean;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class HandlerJoinResult {
    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getHandlerDescription() {
        return handlerDescription;
    }

    public void setHandlerDescription(String handlerDescription) {
        this.handlerDescription = handlerDescription;
    }

    private String handlerName;
    private String handlerDescription;
    private String result;

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(handlerName +":");
        buffer.append("\n" + result + "\n\n");
        return buffer.toString();
    }
}
