package de.glasergl.custom.lol.setup.io.ui;

import java.awt.FlowLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import de.glasergl.custom.lol.setup.model.SelectableRune;
import de.glasergl.custom.lol.setup.model.SelectableRunes;
import de.glasergl.custom.lol.setup.model.entity.Rune;

/**
 * Creates a view for selectable runes, i.e., a list of lists of selectable
 * runes.
 */
public final class SelectableRunesView {
    private final JPanel view = new JPanel();
    private final List<GraySelectionElement> runeViews = new ArrayList<>();

    /**
     * Creates a view for selectable runes, i.e., a list of lists of selectable
     * runes (wrapped in a singel SelectableRunes object).
     * 
     * @param selectableRunes - to create a view for
     * @param images          - mapping from rune name to colored image of the rune
     * @param grayImages      - mapping from rune name to gray image of the rune
     */
    public SelectableRunesView(final SelectableRunes selectableRunes, final Map<Rune, Image> images, final Map<Rune, Image> grayImages) {
	view.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
	view.add(createRuneIcons(selectableRunes, images, grayImages));
	updateView();
    }

    private JPanel createRuneIcons(final SelectableRunes selectableRunes, final Map<Rune, Image> images, final Map<Rune, Image> grayImages) {
	final JPanel runeIcons = new JPanel();
	runeIcons.setLayout(new BoxLayout(runeIcons, BoxLayout.Y_AXIS));
	final List<List<SelectableRune>> rows = selectableRunes.get();
	for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
	    final List<SelectableRune> row = rows.get(rowIndex);
	    final JPanel rowView = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
	    for (int columnIndex = 0; columnIndex < row.size(); columnIndex++) {
		final SelectableRune selectableRune = row.get(columnIndex);
		final GraySelectionElement runeView = new GraySelectionElement(images.get(selectableRune.getRune()), grayImages.get(selectableRune.getRune()), () -> {
		    return selectableRune.isSelected();
		}, new SelectRune(rowIndex, columnIndex, selectableRunes));
		runeView.getIconLabel().setToolTipText(selectableRune.toString());
		runeViews.add(runeView);
		rowView.add(runeView.getView());
	    }
	    runeIcons.add(rowView);
	}
	return runeIcons;
    }

    private void updateView() {
	for (final GraySelectionElement runeView : runeViews) {
	    runeView.updateSelectionState();
	}
    }

    public JPanel getView() {
	return view;
    }

    /**
     * Runnable that selects a rune at some row and column and updates the view
     * afterwards.
     */
    private final class SelectRune implements Runnable {
	private final int rowIndex;
	private final int columnIndex;
	private final SelectableRunes runes;

	private SelectRune(final int rowIndex, final int columnIndex, final SelectableRunes runes) {
	    this.rowIndex = rowIndex;
	    this.columnIndex = columnIndex;
	    this.runes = runes;
	}

	@Override
	public void run() {
	    runes.select(rowIndex, columnIndex);
	    updateView();
	}
    }
}
