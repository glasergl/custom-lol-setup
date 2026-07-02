package de.glasergl.custom.lol.setup.ui;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public interface EmptyFocusListener extends FocusListener {
    @Override
    default void focusGained(final FocusEvent focusGain) {
    }

    @Override
    default void focusLost(final FocusEvent focusGain) {
    }
}
