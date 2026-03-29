package de.glasergl.custom.lol.setup.ui.builder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import de.glasergl.custom.lol.setup.file.Images;
import de.glasergl.custom.lol.setup.model.entity.Item;
import de.glasergl.custom.lol.setup.model.entity.ItemBuild;
import de.glasergl.custom.lol.setup.ui.EmptyFocusListener;
import de.glasergl.custom.lol.setup.ui.EmptyMouseListener;
import lombok.Getter;

public final class ItemBuildBuilder {
    private static final String DEFAULT_ROW_TEXT = "New row";

    private final @Getter JPanel ui = new JPanel(new BorderLayout());
    private final JPanel rowsUi = new JPanel();
    private final List<ItemRow> itemRows = new ArrayList<>();
    private final Images images;

    public ItemBuildBuilder(final Images images, final ItemBuild itemBuild) {
	if (itemBuild.notes().size() != itemBuild.items().size()) {
	    throw new IllegalArgumentException();
	}
	this.images = images;

	this.rowsUi.setLayout(new BoxLayout(rowsUi, BoxLayout.Y_AXIS));
	initializeRowsWithExistingItemBuild(itemBuild);
    }

    private void initializeRowsWithExistingItemBuild(final ItemBuild itemBuild) {
	if (itemBuild.notes().isEmpty()) {
	    itemRows.add(new ItemRow(DEFAULT_ROW_TEXT, List.of()));
	} else {
	    for (int i = 0; i < itemBuild.notes().size(); i++) {
		itemRows.add(new ItemRow(itemBuild.notes().get(i), itemBuild.items().get(i)));
	    }
	}
	final JPanel rowWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
	rowWrapper.add(rowsUi);
	final JScrollPane rowsScrollPane = new JScrollPane(rowWrapper, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	rowsScrollPane.setPreferredSize(new Dimension(550, 300));
	ui.add(rowsScrollPane, BorderLayout.CENTER);
	createFooter(rowsScrollPane);
    }

    private void createFooter(final JScrollPane rowsScrollPaneToScrollDownWhenNewRowIsAdded) {
	final JButton addRowButton = new JButton("Add Row");
	addRowButton.addActionListener(click -> {
	    itemRows.add(new ItemRow(DEFAULT_ROW_TEXT, List.of()));
	    SwingUtilities.invokeLater(() -> {
		final JScrollBar vertical = rowsScrollPaneToScrollDownWhenNewRowIsAdded.getVerticalScrollBar();
		vertical.setValue(vertical.getMaximum());
	    });
	});
	final JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
	buttonWrapper.add(addRowButton);
	buttonWrapper.add(new JButton("Pop Out"));
	ui.add(buttonWrapper, BorderLayout.SOUTH);
    }

    public ItemBuildBuilder(final Images images) {
	this(images, new ItemBuild(new ArrayList<>(List.of(DEFAULT_ROW_TEXT)), new ArrayList<>(List.of(new ArrayList<>()))));
    }

    public void addItem(final Item item) {
	assert !itemRows.isEmpty();
	if (!anyRowSelected()) {
	    itemRows.get(0).select();
	}
	for (final ItemRow row : itemRows) {
	    if (row.isSelected) {
		row.addItem(item);
	    }
	}
    }

    private boolean anyRowSelected() {
	for (final ItemRow row : itemRows) {
	    if (row.isSelected) {
		return true;
	    }
	}
	return false;
    }

    public void updateItemRows() {
	rowsUi.removeAll();
	for (final ItemRow itemRow : itemRows) {
	    rowsUi.add(itemRow.ui);
	}
	rowsUi.revalidate();
	rowsUi.repaint();
    }

    public ItemBuild build() {
	return new ItemBuild(itemRows.stream().map(ItemRow::getNoteTextField).map(JTextField::getText).toList(), itemRows.stream().map(ItemRow::getItems).toList());
    }

    private final class ItemRow {
	private final JPanel ui = new JPanel(new BorderLayout());;
	private final @Getter JTextField noteTextField = new JTextField(30);
	private final @Getter List<Item> items;
	private final JPanel itemsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

	private boolean isSelected = false;

	private ItemRow(final String note, final List<Item> items) {
	    this.items = new ArrayList<>(items);

	    itemsPanel.setPreferredSize(new Dimension(300, 80));
	    itemsPanel.addMouseListener(new EmptyMouseListener() {
		@Override
		public void mouseClicked(final MouseEvent click) {
		    select();
		}
	    });
	    itemsPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

	    final JPanel rowHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    noteTextField.setText(note);
	    noteTextField.addFocusListener(getFocusListenerThatSelectsOnFocus());
	    rowHeader.add(noteTextField);
	    final JButton upButton = createUpButton();
	    final JButton downButton = createDownButton();
	    final JButton deleteButton = createDeleteButton();
	    List.of(upButton, downButton).stream().forEach(button -> button.addFocusListener(getFocusListenerThatSelectsOnFocus()));
	    rowHeader.add(upButton);
	    rowHeader.add(downButton);
	    rowHeader.add(deleteButton);

	    ui.add(itemsPanel, BorderLayout.CENTER);
	    ui.add(rowHeader, BorderLayout.NORTH);
	    ui.setBorder(new EmptyBorder(2, 2, 2, 2));

	    for (final Item item : items) {
		addItem(item);
	    }
	    ItemBuildBuilder.this.rowsUi.add(ui);
	    select();
	    ItemBuildBuilder.this.rowsUi.revalidate();
	    ItemBuildBuilder.this.rowsUi.repaint();
	}

	private JButton createUpButton() {
	    final JButton upButton = new JButton("▲");
	    upButton.addActionListener(click -> {
		final int i = itemRows.indexOf(this);
		if (i > 0) {
		    Collections.swap(itemRows, i, i - 1);
		    ItemBuildBuilder.this.updateItemRows();
		}
	    });
	    return upButton;
	}

	private JButton createDownButton() {
	    final JButton downButton = new JButton("▼");
	    downButton.addActionListener(click -> {
		final int i = itemRows.indexOf(this);
		if (i < itemRows.size() - 1) {
		    Collections.swap(itemRows, i, i + 1);
		    ItemBuildBuilder.this.updateItemRows();
		}
	    });
	    return downButton;
	}

	private JButton createDeleteButton() {
	    final JButton deleteButton = new JButton("-");
	    deleteButton.addActionListener(click -> {
		itemRows.remove(itemRows.indexOf(this));
		ItemBuildBuilder.this.updateItemRows();
	    });
	    return deleteButton;
	}

	private void updateItemPanel() {
	    itemsPanel.removeAll();
	    for (int i = 0; i < items.size(); i++) {
		addItem(items.get(i), i, false);
	    }
	    itemsPanel.revalidate();
	    itemsPanel.repaint();
	}

	private void addItem(final Item item, final int i, final boolean repaint) {
	    final JLabel itemIcon = new JLabel(new ImageIcon(images.get(item)));
	    itemIcon.setToolTipText(item.toString());
	    itemIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    itemIcon.addMouseListener(new EmptyMouseListener() {
		@Override
		public void mouseClicked(final MouseEvent click) {
		    if (click.getButton() == MouseEvent.BUTTON3) {
			items.remove(i);
			updateItemPanel();
		    } else if (click.getButton() == MouseEvent.BUTTON1 && click.isControlDown() && i > 0) {
			Collections.swap(items, i, i - 1);
			updateItemPanel();
		    } else if (click.getButton() == MouseEvent.BUTTON1 && !click.isControlDown() && i < items.size() - 1) {
			Collections.swap(items, i, i + 1);
			updateItemPanel();
		    }
		}
	    });
	    itemsPanel.add(itemIcon);
	    if (repaint) {
		itemsPanel.revalidate();
		itemsPanel.repaint();
	    }
	}

	private FocusListener getFocusListenerThatSelectsOnFocus() {
	    return new EmptyFocusListener() {
		@Override
		public void focusGained(final FocusEvent focusGain) {
		    select();
		}
	    };
	}

	private void addItem(final Item item) {
	    items.add(item);
	    addItem(item, items.size() - 1, true);
	}

	private void unselect() {
	    isSelected = false;
	    ui.setBorder(new EmptyBorder(2, 2, 2, 2));
	}

	private void select() {
	    for (final ItemRow otherItemRow : itemRows) {
		otherItemRow.unselect();
	    }
	    isSelected = true;
	    ui.setBorder(new LineBorder(Color.CYAN, 2));
	}
    }
}
