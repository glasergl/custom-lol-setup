package de.glasergl.custom.lol.setup.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public final class CustomSwingComponents {
    public static JButton createButton(final String buttonText) {
        final JButton button = new JButton(buttonText);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public static JScrollPane createScrollPane(final JComponent componentToScroll, final int verticalScrollingPolicy, final int horizontalScrollingPolicy) {
        final JScrollPane scrollPane = new JScrollPane(componentToScroll, verticalScrollingPolicy, horizontalScrollingPolicy);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(25);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        return scrollPane;
    }
}
