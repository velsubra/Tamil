package my.interest.lang.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class Row {
    public Row(int colCount) {
        super();
        cells = new ArrayList<Cell>(Arrays.asList(new Cell[colCount]));
        for (int i = 0; i < cells.size(); i++) {
            cells.set(i, new Cell());
        }
    }

    private List<Cell> cells = null;


    public void setGridAt(Grid grid, int col) {
        Cell c = cells.get(col);
        if (c == null) {
            c = new Cell();
            cells.set(col, c);
        }
        c.setInnderGrid(grid);
        c.setCenterAlign(true);
    }

    public void setContents(List<String> contents, int col) {
        Cell c = cells.get(col);
        if (c == null) {
            c = new Cell();
            cells.set(col, c);
        }
        c.setContents(contents);

    }


    public void setValueAt(String val, int col) {
        setValueAt(val, col, false);
    }

    public void setValueAt(String val, int col, boolean center) {
        Cell c = cells.get(col);
        if (c == null) {
            c = new Cell();
            cells.set(col, c);
        }
        c.setContent(val);
        c.setCenterAlign(center);
    }

    public void setObjectAt(Object val, int col) {
        setObjectAt(val, col, false);

    }

    public void setObjectAt(Object val, int col, boolean center) {
        if (val == null) {
            setValueAt(null, col);
        } else {
            if (Grid.class.isAssignableFrom(val.getClass())) {
                setGridAt((Grid) val, col);
            } else if (List.class.isAssignableFrom(val.getClass())) {
                setContents((List) val, col);
            } else {
                setValueAt(val.toString(), col, center);
            }
        }
    }

    public List<Cell> getCells() {
        return cells;
    }

    public int getMaxLines() {
        int lines = 0;
        for (Cell c : cells) {
            if (c.getLinesCount() > lines) {
                lines = c.getLinesCount();
            }
        }
        return lines;
    }

    public void verticalAlign() {
        int maxlines = getMaxLines();
        for (Cell c : cells) {
            c.verticalAlign(maxlines);

        }
    }

    public String toHtml() {
        StringBuffer buffer = new StringBuffer("<tr>");
        for (Cell c : cells) {
            buffer.append(c.toHtml());
        }
        buffer.append("</tr>");
        return buffer.toString();
    }
}
