package my.interest.lang.tamil.gui;

import javax.swing.*;
import java.awt.*;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class EditorPanel extends JPanel {

    private JTextArea editor = new JTextArea();

    public  EditorPanel() {
        super();
        java.awt.Font font = new java.awt.Font("Arial Unicode MS", Font.TYPE1_FONT, 18);
        editor.setFont(font);
        setLayout(new BorderLayout());
        add(editor, BorderLayout.CENTER);


    }
}
