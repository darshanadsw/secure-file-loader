package org.secure.apps.ui;

import javax.swing.*;
import java.awt.*;

import static org.secure.apps.utils.PanelServiceUtils.getByName;

public class MainFramePanel extends JPanel {
    public MainFramePanel() {
        setLayout(new BorderLayout());
        add(getByName("mainPagePanel"), BorderLayout.CENTER);
    }
}
