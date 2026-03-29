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
import java.util.Locale;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;

import de.glasergl.custom.lol.setup.file.Images;
import de.glasergl.custom.lol.setup.model.entity.Item;
import de.glasergl.custom.lol.setup.model.entity.ItemProperty;
import de.glasergl.custom.lol.setup.ui.CustomSwingComponents;
import de.glasergl.custom.lol.setup.ui.DocumentChangeListener;
import de.glasergl.custom.lol.setup.ui.EmptyMouseListener;
import lombok.Getter;

public final class ItemSelectionUi {
    private final @Getter JPanel ui = new JPanel(new BorderLayout());
    private final Consumer<Item> selectionHandler;
    private final Images images;
    private final int margin = 8;
    private final int numberOfItemsPerRow = 5;
    private final JPanel itemsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, margin, margin));
    private final JTextField itemSearchTextField = new JTextField();
    private final ItemPropertySelection itemPropertySelection = new ItemPropertySelection(selectedProperties -> renderItems(selectedProperties));

    public ItemSelectionUi(final Images images, final Consumer<Item> selectionHandler) {
	this.images = images;
	this.selectionHandler = selectionHandler;
	renderItems(Collections.emptySet());
	final JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
	wrapper.add(itemsPanel);
	final JScrollPane itemSelection = CustomSwingComponents.createScrollPane(wrapper, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	itemSelection.setPreferredSize(new Dimension(325, 300));
	itemSearchTextField.getDocument().addDocumentListener(new DocumentChangeListener() {
	    @Override
	    public void onChange(DocumentEvent e) {
		renderItems(itemPropertySelection.getSelectedProperties());
	    }
	});
	ui.add(itemSearchTextField, BorderLayout.NORTH);
	ui.add(itemSelection, BorderLayout.CENTER);
	ui.add(itemPropertySelection.getUi(), BorderLayout.WEST);
    }

    private void renderItems(final Set<ItemProperty> propertiesItemsHaveToMatch) {
	final List<Item> itemsWithProperties = new ArrayList<>(Arrays.stream(Item.values()).filter(item -> item.getProperties().containsAll(propertiesItemsHaveToMatch)).filter(getItemNameFilter(itemSearchTextField.getText())).toList());
	itemsWithProperties.sort((i1, i2) -> Integer.compare(i1.getCost(), i2.getCost()));
	itemsPanel.removeAll();
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
	    final JLabel itemCostLabel = new JLabel(String.valueOf(item.getCost()));
	    itemCostLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    final JPanel itemWrapper = new JPanel(new BorderLayout());
	    itemWrapper.add(itemLabel, BorderLayout.CENTER);
	    itemWrapper.add(itemCostLabel, BorderLayout.SOUTH);
	    itemsPanel.add(itemWrapper);
	}
	itemsPanel.setPreferredSize(getPreferredSizeOfItemPanel(images, itemsWithProperties.size()));
	itemsPanel.revalidate();
	itemsPanel.repaint();
    }

    private Dimension getPreferredSizeOfItemPanel(final Images images, final int numberOfItems) {
	final JLabel itemLabel = new JLabel(new ImageIcon(images.get(Item.RABADONS_DEATHCAP)));
	final Dimension preferredSizeOfSingleItemLabel = itemLabel.getPreferredSize();
	final int costLabelHeight = 15;
	return new Dimension(numberOfItemsPerRow * (preferredSizeOfSingleItemLabel.width + margin), (preferredSizeOfSingleItemLabel.height + costLabelHeight) * (numberOfItems / numberOfItemsPerRow + margin));
    }

    private Predicate<Item> getItemNameFilter(final String itemSearchQueryText) {
	return item -> {
	    if (itemSearchQueryText.isBlank()) {
		return true;
	    }
	    final String itemName = item.toString();
	    return itemName.matches(buildItemSearchQueryStringTextRegEx(itemSearchQueryText));
	};
    }

    private String buildItemSearchQueryStringTextRegEx(final String itemSearchQueryText) {
	final StringBuilder regEx = new StringBuilder();
	regEx.append(".*");
	for (char character : itemSearchQueryText.toCharArray()) {
	    regEx.append(character);
	    regEx.append(".*");
	}
	return regEx.toString().toUpperCase(Locale.ROOT);
    }
}
