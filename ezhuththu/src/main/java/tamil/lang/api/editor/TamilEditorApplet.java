package tamil.lang.api.editor;

import javax.swing.*;
import java.awt.*;


/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TamilEditorApplet extends JApplet {

    private JTextArea editor =  null;

    @Override
    public void init() {
        setLayout(new BorderLayout());
        editor = new JTextArea();
        add(editor, BorderLayout.CENTER );
    }

    @Override
    public void start() {

    }
    @Override
    public void stop() {

    }
    @Override
    public void destroy() {

    }
}
