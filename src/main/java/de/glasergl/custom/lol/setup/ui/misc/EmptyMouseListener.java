package de.glasergl.custom.lol.setup.ui.misc;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * MouseListener that provides default empty implementations to not force
 * overwriting all.
 */
public interface EmptyMouseListener extends MouseListener {
    @Override
    default void mouseClicked(final MouseEvent mouseEvent) {
    }

    @Override
    default void mouseEntered(final MouseEvent mouseEvent) {
    }

    @Override
    default void mouseExited(final MouseEvent mouseEvent) {
    }

    @Override
    default void mousePressed(final MouseEvent mouseEvent) {
    }

    @Override
    default void mouseReleased(final MouseEvent mouseEvent) {
    }
}
