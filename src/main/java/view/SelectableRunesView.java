package view;

import java.awt.FlowLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import model.SelectableRune;
import model.SelectableRunes;

public class SelectableRunesView {
	private static final Map<String, Image> IMAGE_STORAGE = new HashMap<>();

	private final JPanel view = new JPanel();
	private final List<GraySelectionElement> runeViews = new ArrayList<>();

	public SelectableRunesView(final SelectableRunes selectableRunes, final int iconSize) {
		view.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		view.add(createRuneIcons(selectableRunes, iconSize));
		updateSelectionStates();
	}

	private JPanel createRuneIcons(final SelectableRunes selectableRunes, final int iconSize) {
		final JPanel runeIcons = new JPanel();
		runeIcons.setLayout(new BoxLayout(runeIcons, BoxLayout.Y_AXIS));
		final List<List<SelectableRune>> rows = selectableRunes.get();
		for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
			final List<SelectableRune> row = rows.get(rowIndex);
			final JPanel rowView = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
			for (int columnIndex = 0; columnIndex < row.size(); columnIndex++) {
				final SelectableRune rune = row.get(columnIndex);
				final Image runeIcon = IMAGE_STORAGE.containsKey(rune.getName()) ? IMAGE_STORAGE.get(rune.getName())
						: ImageReading.getImageFromName(rune.getName(), "png", iconSize, iconSize);
				IMAGE_STORAGE.put(rune.getName(), runeIcon);
				final GraySelectionElement runeView = new GraySelectionElement(runeIcon, () -> {
					return rune.isSelected();
				}, new SelectRune(rowIndex, columnIndex, selectableRunes));
				runeViews.add(runeView);
				rowView.add(runeView.getView());
			}
			runeIcons.add(rowView);
		}
		return runeIcons;
	}

	private void updateSelectionStates() {
		for (final GraySelectionElement runeView : runeViews) {
			runeView.updateSelectionState();
		}
	}

	public JPanel getView() {
		return view;
	}

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
			updateSelectionStates();
		}
	}
}
