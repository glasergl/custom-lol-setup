package de.glasergl.custom.lol.setup.ui.selection;

import de.glasergl.custom.lol.setup.ui.helper.EmptyMouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Supplier;

/**
 * Element which switches between two states, selected and unselected.
 */
public final class GraySelectionElement {
    private final JPanel view = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
    private final JLabel icon = new JLabel();
    private final Supplier<Boolean> shouldBeSelectedCheck;
    private final Runnable onClickAction;
    private final Image selectedImage;
    private final Image unselectedImage;

    public GraySelectionElement(final Image selectedImage, final Image unselectedImage, final Supplier<Boolean> shouldBeSelectedCheck, final Runnable onClickAction) {
        this.shouldBeSelectedCheck = shouldBeSelectedCheck;
        this.onClickAction = onClickAction;
        this.selectedImage = selectedImage;
        this.unselectedImage = unselectedImage;
        icon.addMouseListener(getClickActionMouseListener());
        icon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        icon.setOpaque(true);
        view.add(icon);
    }

    public void updateSelectionState() {
        icon.setIcon(new ImageIcon(shouldBeSelectedCheck.get() ? selectedImage : unselectedImage));
    }

    public JLabel getIconLabel() {
        return icon;
    }

    private MouseListener getClickActionMouseListener() {
        return new EmptyMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onClickAction.run();
            }
        };
    }

    public JPanel getView() {
        return view;
    }
}
