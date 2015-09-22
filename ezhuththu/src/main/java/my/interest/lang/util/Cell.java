package my.interest.lang.util;

import my.interest.lang.tamil.EzhuththuUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class Cell {
    public Cell() {
        super();
    }
    /**
     * A cell can contain three types of contents
     * One one of them can be set.
     */
    private String content; // simple text can contain multiple \n
    private List<String> contents = null; // List of Gtrings
    private Grid innderGrid; // Grids


    boolean innerGridSerialized = false;
    private List<String> lines = null;
    private boolean centerAlign = false;
    private int verticalAlignExtraLineAdded = 0;



    public void verticalAlign(int maxlines) {
        if (lines != null && !lines.isEmpty() && verticalAlignExtraLineAdded == 0) {
            lines = new ArrayList<String>(lines);
            int width = lines.get(0).length();
            verticalAlignExtraLineAdded = maxlines - lines.size();
            int top = (maxlines - lines.size()) / 2;
            for (int i = 0 ;i < top ;i ++) {
                lines.add(0, EzhuththuUtils.padChar(width, ' ', "", width, true));
            }
            while (lines.size() < maxlines) {
                lines.add( EzhuththuUtils.padChar(width, ' ', "", width, true));
            }
        }
    }

    public int getExtraWidth(int colWidth) {
        if (colWidth == 1)
            return 0;
        if (lines == null) {
            return colWidth - 1;
        } else {
            int extra = colWidth - 1;
            for (String l : lines) {
                int e = colWidth - l.length();
                if (e == 0)
                    return 0;
                if (e < extra) {
                    extra = e;
                }
            }
            return extra;
        }
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void cutWithWidth(int w, boolean verticalAlign) {
        lines = null;
        verticalAlignExtraLineAdded = 0;
        if (innderGrid != null) {
            setCenterAlign(true);
            if (!innderGrid.pack(w + 2, verticalAlign)) {
                innerGridSerialized = false;
                content =
                        innderGrid.getRows().size() + " item(s). Grid not serialized inline. See below for details.";


            } else {
                int formedwidth = innderGrid.calculateTableWidth();
                int diff = w + 2 - formedwidth;
                if (diff > 0) {
                    HeaderCell hc =  innderGrid.getColumns().getList().get(innderGrid.getColumns().getList().size() - 1).getValue();
                    hc.setWidth(hc.getWidth() + diff);
                    innderGrid.resetFormatting(verticalAlign);
                }
                lines = innderGrid.getAsListOfString(false);
                innerGridSerialized = true;

            }
        } else if (contents != null && !contents.isEmpty()) {
            lines = new ArrayList<String>();
            for (int i = 0; i < contents.size(); i++) {
                lines.addAll( Arrays.asList(EzhuththuUtils.convertToHelpText(contents.get(i), w, 0).split("\\n")));
                if (i != contents.size() - 1) {
                    lines.add(EzhuththuUtils.getString("-", w ));
                }
            }
        }
        if (content != null && lines == null) {
            String l = EzhuththuUtils.convertToHelpText(content, w, 0);
            lines = Arrays.asList(l.split("\\n"));


        }
    }
    /**
     * Lines count after formatting.
     * @return
     */
    public int getLinesCount() {
        if (lines == null || lines.isEmpty()) {
            return 1;
        } else {
            return lines.size();
        }
    }


    /**
     * It has to return based on the original type (String, List or Grid) of the content that is set.
     *
     * @return number of new line characters.
     */
    public int getOriginalLinesCount() {
        if (innderGrid != null && innerGridSerialized) {
            return getLinesCount();
        }

        /**
         * if size is 1; it should return 0 (assuming no \n in the content)
         * if size is two; it should return 2;
         *
         */
        if (contents != null && !contents.isEmpty()) {
            int count = contents.size() - 1; // --- lines count; Every line is separated with an additonal line

            for (String s : contents) {
                count +=Arrays.asList(s.split("\\n")).size();
            }
            return count - 1;
        }

        if (content != null) {
            return Arrays.asList(content.split("\\n")).size()  - 1;
            //return CloudUtil.countChars(content, '\n');
        }
        return 0;
    }


    protected String getJustLine(int l, int w) {
        String ret = "";
        if (lines == null || l >= lines.size()) {

        } else {
            ret = lines.get(l);
        }
        if (centerAlign) {
            if (ret.length() < w - 1) {
                int withleftSpace = ret.length() + (w - ret.length()) / 2;
                ret = EzhuththuUtils.padChar(withleftSpace, ' ', ret, withleftSpace, false);
            }
        }
        return ret;
    }

    public String getLine(int l, int w) {
        String ret = getJustLine(l, w);
        return EzhuththuUtils.padChar(w, ' ', ret, w, true);
    }

    public boolean hasLine(int l) {
        if (lines == null || l >= lines.size()) {
            return false;
        } else {
            return true;
        }
    }

    void setInnderGrid(Grid innderGrid) {
        this.innderGrid = innderGrid;
    }

    public Grid getInnderGrid() {
        return innderGrid;
    }

    public boolean isInnerGridSerialized() {
        return innerGridSerialized;
    }

    public void setCenterAlign(boolean centerAlign) {
        this.centerAlign = centerAlign;
    }

    public boolean isCenterAlign() {
        return centerAlign;
    }

    public int getVerticalAlignExtraLineAdded() {
        return verticalAlignExtraLineAdded;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    public List<String> getContents() {
        return contents;
    }

    public String toHtml() {
        StringBuffer buffer = new StringBuffer("<td>");
        if (innderGrid != null) {
            buffer.append(innderGrid.toHtml());
        } else {
            buffer.append(this.getContent());
        }
        buffer.append("</td>");
        return buffer.toString();
    }
}

