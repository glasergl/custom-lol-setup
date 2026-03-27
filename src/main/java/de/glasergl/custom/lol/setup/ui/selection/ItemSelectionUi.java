package de.glasergl.custom.lol.setup.ui.selection;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.glasergl.custom.lol.setup.file.Images;
import de.glasergl.custom.lol.setup.model.entity.Item;
import de.glasergl.custom.lol.setup.model.entity.ItemProperty;
import de.glasergl.custom.lol.setup.ui.misc.EmptyMouseListener;
import lombok.Getter;

public final class ItemSelectionUi {
    private final @Getter JPanel ui = new JPanel(new BorderLayout());
    private final Consumer<Item> selectionHandler;
    private final Images images;
    private final int margin = 8;
    private final int numberOfItemsPerRow = 5;
    private final JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, margin, margin));

    public ItemSelectionUi(final Images images, final Consumer<Item> selectionHandler) {
	this.images = images;
	this.selectionHandler = selectionHandler;
	renderItems(Collections.emptySet());
	final JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
	wrapper.add(itemPanel);
	final JScrollPane itemSelection = new JScrollPane(wrapper, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	itemSelection.setPreferredSize(new Dimension(325, 300));
	final ItemPropertySelection itemPropertySelection = new ItemPropertySelection(selectedProperties -> renderItems(selectedProperties));
	ui.add(itemSelection, BorderLayout.CENTER);
	ui.add(itemPropertySelection.getUi(), BorderLayout.WEST);
    }

    private void renderItems(final Set<ItemProperty> propertiesItemsHaveToMatch) {
	final List<Item> itemsWithProperties = new ArrayList<>(Arrays.stream(Item.values()).filter(item -> item.getProperties().containsAll(propertiesItemsHaveToMatch)).toList());
	itemsWithProperties.sort((i1, i2) -> Integer.compare(i1.getCost(), i2.getCost()));
	itemPanel.removeAll();
	for (final Item item : itemsWithProperties) {
	    final JLabel itemLabel = new JLabel(new ImageIcon(images.get(item)));
	    itemLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    itemLabel.setToolTipText(item.toString());
	    itemLabel.addMouseListener(new EmptyMouseListener() {
		@Override
		public void mouseClicked(final MouseEvent click) {
		    selectionHandler.accept(item);
		}
	    });
	    itemPanel.add(itemLabel);
	}
	itemPanel.setPreferredSize(getPreferredSizeOfItemPanel(images, itemsWithProperties.size()));
	itemPanel.revalidate();
	itemPanel.repaint();
    }

    private Dimension getPreferredSizeOfItemPanel(final Images images, final int numberOfItems) {
	final JLabel itemLabel = new JLabel(new ImageIcon(images.get(Item.RABADONS_DEATHCAP)));
	final Dimension preferredSizeOfSingleItemLabel = itemLabel.getPreferredSize();
	return new Dimension(numberOfItemsPerRow * (preferredSizeOfSingleItemLabel.width + margin), preferredSizeOfSingleItemLabel.height * (numberOfItems / numberOfItemsPerRow + margin));
    }
}
