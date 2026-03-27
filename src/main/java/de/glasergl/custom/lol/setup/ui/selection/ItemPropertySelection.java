package de.glasergl.custom.lol.setup.ui.selection;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import de.glasergl.custom.lol.setup.model.entity.ItemProperty;
import lombok.Getter;

public final class ItemPropertySelection {
    private final @Getter JPanel ui = new JPanel();
    private final Set<ItemProperty> selectedProperties = new HashSet<>();

    public ItemPropertySelection(final Consumer<Set<ItemProperty>> selectionHandler) {
	ui.setLayout(new BoxLayout(ui, BoxLayout.Y_AXIS));
	for (final ItemProperty itemProperty : ItemProperty.values()) {
	    final JCheckBox checkBox = new JCheckBox(itemProperty.toString());
	    checkBox.addChangeListener(change -> {
		if (checkBox.isSelected()) {
		    selectedProperties.add(itemProperty);
		} else {
		    selectedProperties.remove(itemProperty);
		}
		selectionHandler.accept(selectedProperties);
	    });
	    ui.add(checkBox);
	}
    }
}
