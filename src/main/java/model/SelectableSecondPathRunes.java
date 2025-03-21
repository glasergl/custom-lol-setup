package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Special kind of selectable slot runes where only 2 runes in total can be
 * selected.
 */
public final class SelectableSecondPathRunes extends SelectableRunes {
	private final Queue<Integer> rowSelectionHistory = new LinkedList<>();

	public SelectableSecondPathRunes(final List<List<Rune>> runes) {
		super(runes);
	}

	@Override
	public void select(final int rowIndex, final int columnIndex) {
		super.select(rowIndex, columnIndex);
		if (rowSelectionHistory.contains(rowIndex)) {
			rowSelectionHistory.remove(rowIndex);
		}
		rowSelectionHistory.add(rowIndex);
		if (rowSelectionHistory.size() == 3) {
			final int firstSelectedRow = rowSelectionHistory.poll();
			for (final SelectableRune runeToUnselect : runes.get(firstSelectedRow)) {
				runeToUnselect.setSelected(false);
			}
		}
	}
}
