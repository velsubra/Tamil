package my.interest.lang.util;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class Grid {
    public Grid() {
        super();

    }

    public static final String GRID_USE_GRAPHICS_CHARS = "GRID_USE_GRAPHICS_CHARS";
    private final OrderedMap<String, HeaderCell> columns = new OrderedMap<String, HeaderCell>();
    public static final boolean useGrphics = ProcessUtil.isOS_WINDOWS() && Boolean.getBoolean(GRID_USE_GRAPHICS_CHARS);

    static final char TAB_LEFT_TOP = useGrphics ? 201 : '#';

    static final char TAB_RIGHT_TOP = useGrphics ? 187 : '#';


    static final char TITLE_VERTICAL = useGrphics ? 186 : '|';

    static final char TABLE_BOTTOM_LEFT = useGrphics ? 192 : '+';
    static final char TABLE_BOTTOM_RIGHT = useGrphics ? 217 : '+';
    static final char TABLE_BOTTOM_SPLIT = useGrphics ? 193 : '+';

    static final char TITLE_HORIZONTAL = useGrphics ? 205 : '=';

    static final char TITLE_SPLIT_DOWN = useGrphics ? 203 : '#';


    static final char VERTICAL = useGrphics ? 179 : '|';

    private static final char VERTICAL_WITH_RIGHT = useGrphics ? 195 : '|';
    private static final char TITLE_VERTICAL_WITH_RIGHT = useGrphics ? 185 : '|';
    private static final char TITLE_VERTICAL_WITH_LEFT = useGrphics ? 204 : '|';
    private static final char VERTICAL_WITH_LEFT = useGrphics ? 180 : '|';
    public static final char HORIZONTAL = useGrphics ? 196 : '-';

    private static final char PLUS = useGrphics ? 197 : '+';
    private static final char TITLE_PLUS = useGrphics ? 206 : '|';

    private final List<Row> rows = new ArrayList<Row>();


    public int getMaxLinesInHeaders() {
        int lineCount = 0;
        for (int i = 0; i < columns.getList().size(); i++) {
            if (lineCount < columns.getList().get(i).getValue().getLinesCount()) {
                lineCount = columns.getList().get(i).getValue().getLinesCount();
            }
        }
        return lineCount;
    }

    public HeaderCell addColumn(String name) {
        HeaderCell c = new HeaderCell();
        c.setContent(name);
        c.setWidth(name.length());
        columns.put(name, c);
        return c;
    }

    public void removeColumn(int i) {
        columns.getList().remove(i);
        for (Row row : rows) {
            row.getCells().remove(i);
        }
    }

    public void removeEmptyColumns() {
        for (Row row : rows) {
            for (Cell c : row.getCells()) {
                if (c.getInnderGrid() != null) {
                    c.getInnderGrid().removeEmptyColumns();
                }
            }
        }
        for (int i = 0; i < columns.size(); i++) {
            if (!hasAnyDataAtColumn(i)) {
                removeColumn(i);
                i--;
            }
        }
    }

    public boolean hasAnyDataAtColumn(int i) {
        for (Row row : rows) {
            if (row.getCells().get(i).getInnderGrid() != null) {
                for (int j = 0; j < row.getCells().get(i).getInnderGrid().columns.size(); j++) {
                    if (row.getCells().get(i).getInnderGrid().hasAnyDataAtColumn(j)) {
                        return true;
                    }
                }
            } else {
                if (row.getCells().get(i).getContent() != null)
                    return true;
            }

        }
        return false;
    }

    public int getExtraWidthAtColumn(int i) {
        HeaderCell c = columns.getList().get(i).getValue();
        if (c.getWidth() == 1)
            return 0;
        int extraWidth = c.getWidth() - 1;
        for (Row r : rows) {
            int e = r.getCells().get(i).getExtraWidth(c.getWidth());
            if (e < extraWidth) {
                extraWidth = e;
            }
            if (extraWidth == 0)
                return 0;
        }
        return extraWidth;
    }

    public HeaderCell getColumnWithMoreExtraWidth() {
        int index = 0;
        int extra = getExtraWidthAtColumn(0);
        for (int i = 1; i < columns.getList().size(); i++) {
            int e = getExtraWidthAtColumn(i);
            if (e > extra) {
                index = i;
                extra = e;

            }
        }
        if (getExtraWidthAtColumn(index) > 0) {
            return columns.getList().get(index).getValue();
        } else {
            return null;
        }

    }

    public void checkAndAddColumn(String name) {
        int index = getColumnIndex(name);
        if (index < 0) {
            addColumn(name);
        }

    }

    public Row createNewRow() {
        Row r = new Row(columns.size());

        rows.add(r);
        return r;
    }

    public int getColumnIndex(String name) {
        for (int i = 0; i < columns.getList().size(); i++) {
            if (name.equals(columns.getList().get(i).getValue().getContent())) {
                return i;
            }
        }
        return -1;
    }

    public void resetFormatting(boolean verticalAlign) {
        int c = 0;
        for (NameValuePair<String, HeaderCell> pair : columns.getList()) {
            pair.getValue().cutWithWidth(pair.getValue().getWidth(), verticalAlign);
            for (Row r : rows) {
                Cell cell = r.getCells().get(c);
                cell.cutWithWidth(pair.getValue().getWidth(), verticalAlign);

            }


            c++;
        }
        if (verticalAlign) {
            for (Row r : rows) {
                r.verticalAlign();
            }
        }

    }
    //
    //    public boolean canDataExpand() {
    //        int index = findCellWithMoreline(false);
    //        int maxLines = getMaxLinesAtCell(index, false);
    //        return maxLines != 1;
    //    }

    public int getMaxLinesAtCell(int c, boolean excludeTitle) {
        int max =
                excludeTitle ? 0 : columns.getList().get(c).getValue().getLinesCount() - columns.getList().get(c).getValue().getOriginalLinesCount() -
                        columns.getList().get(c).getValue().getVerticalAlignExtraLineAdded();
        for (Row r : rows) {
            boolean swap = false;
            int maxLines = max;
            if (r.getCells().get(c).isInnerGridSerialized()) {
                //  && !r.getCells().get(c).getInnderGrid().canDataExpand()) {
                int index = r.getCells().get(c).getInnderGrid().findCellWithMoreline(false);
                maxLines = r.getCells().get(c).getInnderGrid().getMaxLinesAtCell(index, false);
                if (maxLines > max) {
                    swap = true;
                }

            } else {
                maxLines =
                        r.getCells().get(c).getLinesCount() - r.getCells().get(c).getOriginalLinesCount() - r.getCells().get(c).getVerticalAlignExtraLineAdded();
                if (maxLines > max) {

                    swap = true;
                }

            }
            if (swap) {
                max = maxLines;
            }

        }
        return max;
    }

    public int findCellWithMoreline(boolean excludeTitle) {

        int c = 0;
        int max = getMaxLinesAtCell(0, excludeTitle);
        for (int i = 1; i < columns.getList().size(); i++) {
            int newMax = getMaxLinesAtCell(i, excludeTitle);
            boolean swap = false;
            if (max == newMax) {
                swap = getExtraWidthAtColumn(c) > getExtraWidthAtColumn(i);
            } else if (max < newMax) {
                swap = true;
            }
            if (swap) {
                c = i;
                max = newMax;
            }
        }
        return c;

    }

    //    public int getLessLinesAtCell(int c) {
    //        int less = columns.getList().get(c).getValue().getLinesCount();
    //
    //        for (Row r : rows) {
    //            if (r.getCells().get(c).getLinesCount() < less) {
    //                less = r.getCells().get(c).getLinesCount();
    //            }
    //
    //        }
    //        return less;
    //    }

    public HeaderCell findLargerWidthCell() {
        HeaderCell cell = columns.getList().get(0).getValue();
        int width = cell.getWidth();

        for (int i = 1; i < columns.getList().size(); i++) {
            HeaderCell c = columns.getList().get(i).getValue();
            if (c.getWidth() > width) {
                width = c.getWidth();
                cell = c;
            }
        }
        return cell;
    }

    //    public int findCellWithLessline() {
    //
    //        int c = 0;
    //        int less = getLessLinesAtCell(0);
    //        for (int i = 1; i < columns.getList().size(); i++) {
    //            if (columns.getList().get(i).getValue().getWidth() == 1) {
    //                continue;
    //            }
    //            int newLess = getLessLinesAtCell(i);
    //            if (less > newLess) {
    //                c = i;
    //                less = newLess;
    //            }
    //        }
    //        return c;
    //
    //    }

    public boolean pack(int max) {

        return pack(max, false);
    }

    public boolean pack(int max, boolean verticalAlign) {
        return pack(max, verticalAlign, false);
    }

    public boolean pack(int max, boolean verticalAlign, boolean removeEmpty) {
        if (removeEmpty) {
            removeEmptyColumns();
        }

        if (columns.isEmpty())
            return true;

        boolean ret = pack(max, new ArrayList<HeaderCell>(), verticalAlign);
        if (ret && verticalAlign) {
            int maxLines = getMaxLinesInHeaders();
            for (int i = 0; i < columns.getList().size(); i++) {
                columns.getList().get(i).getValue().verticalAlign(maxLines);
            }

            for (Row r : rows) {
                r.verticalAlign();
            }
        }

        return ret;
    }

    private boolean pack(int max, List<HeaderCell> alreadyAdjusted, boolean verticalAlign) {
        boolean done = packRecursive(max, verticalAlign);
        if (done) {
            HeaderCell c = getColumnWithMoreExtraWidth();
            if (c != null && c.getWidth() > 1) {
                //  Logger.getDEFAULT().printlnInfo("cell :" + c.getContent() + " has more extra width");
                int index = findCellWithMoreline(false);

                //  Logger.getDEFAULT().printlnInfo("Index:" + index + " has more lines");
                if (c == columns.getList().get(index).getValue()) {
                    return true;
                }
                int maxLines = getMaxLinesAtCell(index, false);
                if (maxLines <= 1) {
                    return true;
                } else {
                    if (alreadyAdjusted.contains(c)) {
                        return true;
                    }
                    c.decrementWidth();
                    columns.getList().get(index).getValue().incrementWidth();
                    alreadyAdjusted.add(columns.getList().get(index).getValue());
                    pack(max, alreadyAdjusted, verticalAlign);
                }
            }
            return true;


        }
        return false;
    }

    private boolean packRecursive(int max, boolean verticalAlign) {
        resetFormatting(verticalAlign);
        int currentWidth = calculateTableWidth();
        if (max == currentWidth)
            return true;
        boolean toexpand = currentWidth < max;
        if (toexpand) {
            int index = findCellWithMoreline(false);
            int maxLines = getMaxLinesAtCell(index, false);
            if (maxLines == 1)
                return true;
            HeaderCell c = columns.getList().get(index).getValue();
            c.incrementWidth();
            return packRecursive(max, verticalAlign);


        } else {

            HeaderCell c = getColumnWithMoreExtraWidth();
            if (c != null) {
                c.decrementWidth();
                return packRecursive(max, verticalAlign);
            } else {
                c = findLargerWidthCell();
                if (c.getWidth() == 1) {
                    return false;
                }
                //            int index = findCellWithLessline();
                //
                //
                //            HeaderCell c = columns.getList().get(index).getValue();
                //            if (c.getWidth() == 1) {
                //               c = findLargerWidthCell();
                //               //Logger.getDEFAULT().printlnInfo(c.getContent() +":" + c.getWidth() );
                //               if (c.getWidth() == 1) {
                //                 return false;
                //               }
                //            }
                c.decrementWidth();
                return packRecursive(max, verticalAlign);
            }
        }

    }

    public int calculateTableWidth() {
        int w = 0;
        for (NameValuePair<String, HeaderCell> pair : columns.getList()) {
            w += pair.getValue().getWidth();
        }
        w += columns.size() + 1;
        return w;
    }

    public List<String> getAsListOfString(boolean includeBorder) {
        List<String> list = new ArrayList<String>();
        if (includeBorder) {
            list.add(getTableTitleTop(includeBorder));
        }
        list.addAll(getAllColumnHeadersAsListOfStrings(includeBorder));

        list.add(getTableTitleBottom(includeBorder));
        for (int i = 0; i < rows.size(); i++) {
            list.addAll(getRowAsListOfString(rows.get(i), includeBorder));

            if (i < rows.size() - 1) {
                list.add(getRowSpliter(includeBorder));
            }
        }
        if (includeBorder) {
            list.add(getTablBottom(includeBorder));
        }
        return list;
    }

    public void printTo(TextFileWriter writer) {
        printTo(writer, null);
    }

    public void printTo(TextFileWriter writer, String title) {
        if (columns.isEmpty())
            return;
        synchronized (writer) {
            writer.println();

            List<String> list = getAsListOfString(true);
            if (title != null) {
                HeaderCell tit = new HeaderCell();
                tit.setContent(title);
                List<String> titles = tit.getAsTitleString(this.calculateTableWidth() - 2, false);
                titles.addAll(list);
                list = titles;
            }
            for (String l : list) {
                writer.println(l);
            }
            writer.println();
        }
    }

    public List<String> getAllColumnHeadersAsListOfStrings(boolean includeBorder) {
        boolean moreTextOnCol = true;
        List<String> list = new ArrayList<String>();

        int l = 0;
        while (moreTextOnCol) {
            StringBuffer buf = new StringBuffer();
            if (includeBorder) {
                buf.append(TITLE_VERTICAL);
            }
            moreTextOnCol = false;

            for (int c = 0; c < columns.getList().size(); c++) {
                NameValuePair<String, HeaderCell> pair = columns.getList().get(c);
                buf.append(pair.getValue().getLine(l, pair.getValue().getWidth()));
                if (includeBorder || c < columns.size() - 1) {
                    buf.append(TITLE_VERTICAL);
                }
                if (!moreTextOnCol) {
                    moreTextOnCol = pair.getValue().hasLine(l + 1);
                }
            }
            l++;


            list.add(buf.toString());

        }
        return list;

    }

    public void printAllColumnHeaders(TextFileWriter writer) {
        boolean moreTextOnCol = true;

        int l = 0;
        while (moreTextOnCol) {
            StringBuffer buf = new StringBuffer();
            buf.append(TITLE_VERTICAL);
            moreTextOnCol = false;

            for (int c = 0; c < columns.getList().size(); c++) {
                NameValuePair<String, HeaderCell> pair = columns.getList().get(c);
                buf.append(pair.getValue().getLine(l, pair.getValue().getWidth()));
                buf.append(TITLE_VERTICAL);
                if (!moreTextOnCol) {
                    moreTextOnCol = pair.getValue().hasLine(l + 1);
                }
            }
            l++;


            writer.println(buf.toString());

        }

    }


    public void printRow(TextFileWriter writer, Row r, boolean includeBorder) {
        List<String> list = getRowAsListOfString(r, includeBorder);
        for (String l : list) {
            writer.println(l);
        }

    }


    public List<String> getRowAsListOfString(Row r, boolean includeBorder) {
        List<String> list = new ArrayList<String>();
        boolean moreTextOnCol = true;

        int l = 0;
        while (moreTextOnCol) {
            StringBuffer buf = new StringBuffer();
            if (includeBorder) {
                buf.append(VERTICAL);
            }
            moreTextOnCol = false;

            for (int c = 0; c < columns.size(); c++) {
                buf.append(r.getCells().get(c).getLine(l, columns.getList().get(c).getValue().getWidth()));
                if (includeBorder || c < columns.size() - 1) {
                    buf.append(VERTICAL);
                }
                if (!moreTextOnCol) {
                    moreTextOnCol = r.getCells().get(c).hasLine(l + 1);
                }
            }
            l++;

            list.add(buf.toString());

        }
        return list;

    }


    public String getTableTitleTop(boolean includeBorder) {
        return getHorizontalLine(TAB_LEFT_TOP, TITLE_HORIZONTAL, TITLE_SPLIT_DOWN, TAB_RIGHT_TOP, includeBorder);

    }

    public String getRowSpliter(boolean includeBorder) {

        return getHorizontalLine(VERTICAL_WITH_RIGHT, HORIZONTAL, PLUS, VERTICAL_WITH_LEFT, includeBorder);

    }

    public String getTableTitleBottom(boolean includeBorder) {
        return getHorizontalLine(TITLE_VERTICAL_WITH_LEFT, TITLE_HORIZONTAL, TITLE_PLUS, TITLE_VERTICAL_WITH_RIGHT,
                includeBorder);

    }

    public String getTablBottom(boolean includeBorder) {
        return getHorizontalLine(TABLE_BOTTOM_LEFT, HORIZONTAL, TABLE_BOTTOM_SPLIT, TABLE_BOTTOM_RIGHT, includeBorder);

    }

    public String getHorizontalLine(char left, char horizontal, char split, char right, boolean includeBorder) {
        StringBuffer buf = new StringBuffer();
        if (includeBorder) {
            buf.append(left);
        }
        int c = 0;
        for (NameValuePair<String, HeaderCell> pair : columns.getList()) {
            for (int i = 0; i < pair.getValue().getWidth(); i++) {
                buf.append(horizontal);
            }
            c++;
            if (c < columns.size()) {
                buf.append(split);
            }
        }
        if (includeBorder) {
            buf.append(right);
        }
        return buf.toString();
    }

    public List<Row> getRows() {
        return rows;
    }

    public OrderedMap<String, HeaderCell> getColumns() {
        return columns;
    }

    public Grid asTree(String groupcolumnname, int lastcolumns_to_retain) {
        Grid table = this;
        if (lastcolumns_to_retain == 0)
            return table;
        if (table.getColumns().size() <= 2) {
            return table;
        }
        if (table.getColumns().size() <= lastcolumns_to_retain + 1) {
            return table;
        }

        Grid g = new Grid();
        g.addColumn(table.getColumns().getList().get(0).getValue().getContent());
        g.addColumn(groupcolumnname);

        for (Row r : table.getRows()) {
            Grid temp = g;
            Row temprow = null;
            for (int i = 0; i < r.getCells().size(); i++) {
                Cell c = r.getCells().get(i);
                if (i >= r.getCells().size() - lastcolumns_to_retain - 1) {
                    if (temprow == null) {
                        int col = i;
                        while (col < table.getColumns().getList().size()) {

                            temp.addColumn(table.getColumns().getList().get(col).getName());
                            col++;
                        }
                        temprow = temp.createNewRow();
                    }

                    temprow.setValueAt(c.getContent() == null ? "" : c.getContent(),
                            i - r.getCells().size() + lastcolumns_to_retain + 1);
                } else {
                    Grid current = findGrid(temp, c);
                    if (current == null) {


                        Row row = temp.createNewRow();
                        row.setValueAt(c.getContent() == null ? "" : c.getContent(), 0);
                        Grid inner = new Grid();
                        inner.addColumn(table.getColumns().getList().get(i + 1).getName());
                        if (i + 1 < r.getCells().size() - lastcolumns_to_retain - 1) {
                            inner.addColumn(groupcolumnname);
                        }
                        row.setGridAt(inner, 1);
                        temp = inner;
                    } else {
                        temp = current;

                    }
                }
            }
        }
        return g;
    }

    private
    static Grid findGrid(Grid g, Cell c) {
        //for (Row r : g.getRows()) {
        if (g.getRows().size() > 0) {
            Row r = g.getRows().get(g.getRows().size() - 1);
            if (r.getCells().get(0).getContent().equals(c.getContent())) {
                return r.getCells().get(1).getInnderGrid();
            }
        }
        //}
        return null;

    }

    public String toHtml() {
        StringBuffer buffer = new StringBuffer("<table border='1'>");
        buffer.append("</tr>");
        for (NameValuePair<String, HeaderCell> header : columns.getList()) {
            buffer.append(header.getValue().toHtml());
        }
        buffer.append("</tr>");

        for (Row r : rows) {
            buffer.append(r.toHtml());
        }

        buffer.append("</table>");
        return buffer.toString();
    }
}
