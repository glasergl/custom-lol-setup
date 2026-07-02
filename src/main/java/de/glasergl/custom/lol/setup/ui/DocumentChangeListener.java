package de.glasergl.custom.lol.setup.ui;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public interface DocumentChangeListener extends DocumentListener {
    void onChange(DocumentEvent e);

    @Override
    default void insertUpdate(DocumentEvent e) {
        onChange(e);
    }

    @Override
    default void removeUpdate(DocumentEvent e) {
        onChange(e);
    }

    @Override
    default void changedUpdate(DocumentEvent e) {
        onChange(e);
    }
}
