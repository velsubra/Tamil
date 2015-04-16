package test.gui;

import my.interest.lang.tamil.gui.EditorPanel;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class GUITest {

    @Test
    public void testGUI() throws Exception{

        if (true) return;

        Object fontDefinition = new UIDefaults.ProxyLazyValue("javax.swing.plaf.FontUIResource", null, new Object[] { "dialog", new Integer(Font.PLAIN), new Integer(12) });

        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, fontDefinition);
            }
        }

        JFrame window = new JFrame("Tamil editor");
        window.setLayout(new BorderLayout());
        window.add(new EditorPanel(), BorderLayout.CENTER);
        window.setSize(new Dimension(500, 400));
        window.setVisible(true);
        window.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
        System.in.read();
    }
}
