package todo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent runes of a rune path where only a single rune per row can
 * be selected concurrently.
 */
public class SelectableRunes {
	protected final List<List<SelectableRune>> runes;

	public SelectableRunes(final List<List<Rune>> runes) {
		this.runes = new ArrayList<>(runes.size());
		for (final List<Rune> row : runes) {
			this.runes.add(createSelectableRunes(row));
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

	private List<SelectableRune> createSelectableRunes(final List<Rune> runes) {
		final List<SelectableRune> selectableRunes = new ArrayList<>(runes.size());
		for (final Rune rune : runes) {
			selectableRunes.add(new SelectableRune(rune, false));
		}
		return selectableRunes;
	}

	public final List<List<SelectableRune>> get() {
		return runes;
	}
}
