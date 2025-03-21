package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent the non-keystone runes of a rune path where only a single
 * rune per row can be selected concurrently.
 */
public class SelectableSlotRunes {
	protected final List<List<SelectableRune>> runes;

	public SelectableSlotRunes(final List<List<Rune>> runes) {
		this.runes = new ArrayList<>(runes.size());
		for (final List<Rune> row : runes) {
			this.runes.add(SelectableRune.createSelectableRunes(row));
		}
	}

	public void select(final int rowIndex, final int columnIndex) {
		if (rowIndex >= runes.size() || rowIndex < 0 || columnIndex < 0) {
			throw new IllegalArgumentException();
		}
		final List<SelectableRune> row = runes.get(rowIndex);
		if (columnIndex >= row.size()) {
			throw new IllegalArgumentException();
		}
		for (final SelectableRune rune : row) {
			rune.setSelected(false);
		}
		final SelectableRune runeToSelect = row.get(columnIndex);
		runeToSelect.setSelected(true);
	}

	public final List<List<SelectableRune>> get() {
		return runes;
	}
}
