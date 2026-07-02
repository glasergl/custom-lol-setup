package de.glasergl.custom.lol.setup.ui.selection;

import de.glasergl.custom.lol.setup.model.entity.ItemProperty;
import lombok.Getter;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public final class ItemPropertySelection {
    private final @Getter JPanel ui = new JPanel();
    private final @Getter Set<ItemProperty> selectedProperties = new HashSet<>();

    public ItemPropertySelection(final Consumer<Set<ItemProperty>> selectionHandler) {
        ui.setLayout(new BoxLayout(ui, BoxLayout.Y_AXIS));
        for (final ItemProperty itemProperty : ItemProperty.values()) {
            final JCheckBox checkBox = new JCheckBox(itemProperty.toString());
            checkBox.setFocusPainted(false);
            checkBox.addItemListener(change -> {
                if (Set.of(ItemEvent.SELECTED, ItemEvent.DESELECTED).contains(change.getStateChange())) {
                    if (checkBox.isSelected()) {
                        selectedProperties.add(itemProperty);
                    } else {
                        selectedProperties.remove(itemProperty);
                    }
                    selectionHandler.accept(selectedProperties);
                }
            });
            ui.add(checkBox);
        }
    }
}
