package de.glasergl.custom.lol.setup.io.ui.misc;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Interface with empty methods from window listener to not force overwriting
 * all methods.
 */
public interface EmptyWindowListener extends WindowListener {
    @Override
    default void windowClosing(final WindowEvent windowEvent) {
    }

    @Override
    default void windowOpened(final WindowEvent windowEvent) {
    }

    @Override
    default void windowClosed(final WindowEvent windowEvent) {
    }

    @Override
    default void windowIconified(final WindowEvent windowEvent) {
    }

    @Override
    default void windowDeiconified(final WindowEvent windowEvent) {
    }

    @Override
    default void windowActivated(final WindowEvent windowEvent) {
    }

    @Override
    default void windowDeactivated(final WindowEvent windowEvent) {
    }
}
