package model;

import java.util.List;

public class SlotRunes {
	protected final List<List<Rune>> runes;

	public SlotRunes(final List<List<Rune>> runes) {
		this.runes = runes;
	}

	public void select(final int rowIndex, final int columnIndex) {
		if (runes.size() >= rowIndex || rowIndex < 0 || columnIndex < 0) {
			throw new IllegalArgumentException();
		}
		final List<Rune> row = runes.get(rowIndex);
		if (row.size() >= columnIndex) {
			throw new IllegalArgumentException();
		}
		for (final Rune rune : row) {
			rune.setSelected(false);
		}
		final Rune runeToSelect = row.get(columnIndex);
		runeToSelect.setSelected(true);
	}
}
