package my.interest.lang.util;

import my.interest.lang.tamil.EzhuththuUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class HeaderCell extends Cell {
    public HeaderCell() {
        super();
        setCenterAlign(true);
    }

    private int width;

    public void setWidth(int width) {
        this.width = width;
    }

    public void incrementWidth() {
        width++;
    }

    public void decrementWidth() {
        width--;
    }


    @Override
    public void setContent(String content) {
        super.setContent(content);
        this.width = content.length();
    }

    public int getWidth() {
        return width;
    }

    public List<String> getAsTitleString(int width, boolean verticalAlign) {
        setWidth(width);
        cutWithWidth(width, verticalAlign);

        List<String> list = new ArrayList<String>();
        StringBuffer buf = new StringBuffer();
        buf.append(Grid.TAB_LEFT_TOP);
        buf.append(EzhuththuUtils.padChar(width, Grid.TITLE_HORIZONTAL, "", width, true));
        buf.append(Grid.TAB_RIGHT_TOP);
        list.add(buf.toString());
        for (int i = 0; i < getLinesCount(); i++) {
            list.add(Grid.TITLE_VERTICAL + getLine(i, width) + Grid.TITLE_VERTICAL);
        }
        return list;
    }


    public String toHtml() {
        StringBuffer buffer = new StringBuffer("<th align='center'>");
        buffer.append(this.getContent());
        buffer.append("</th>");
        return buffer.toString();
    }
}

