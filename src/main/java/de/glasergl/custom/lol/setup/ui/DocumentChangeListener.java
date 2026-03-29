package de.glasergl.custom.lol.setup.ui;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public interface DocumentChangeListener extends DocumentListener {
    void onChange(DocumentEvent e);

    @Override
    public default void insertUpdate(DocumentEvent e) {
	onChange(e);
    }

    @Override
    public default void removeUpdate(DocumentEvent e) {
	onChange(e);
    }

    @Override
    public default void changedUpdate(DocumentEvent e) {
	onChange(e);
    }
}
