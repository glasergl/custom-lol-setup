package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public final class SecondPath extends SlotRunes {
	private final Queue<Rune> selectionHistory = new LinkedList<>();

	public SecondPath(final List<List<Rune>> runes) {
		super(runes);
	}

	@Override
	public void select(final int rowIndex, final int columnIndex) {
		super.select(rowIndex, columnIndex);
		final Rune selectedRune = getRune(rowIndex, columnIndex);
		selectionHistory.add(selectedRune);
		if (selectionHistory.size() == 3) {
			final Rune firstSelectedRune = selectionHistory.poll();
			firstSelectedRune.setSelected(false);
		}
	}

	protected Rune getRune(final int rowIndex, final int columnIndex) {
		if (runes.size() >= rowIndex || rowIndex < 0 || columnIndex < 0) {
			throw new IllegalArgumentException();
		}
		final List<Rune> row = runes.get(rowIndex);
		if (row.size() >= columnIndex) {
			throw new IllegalArgumentException();
		}
		return row.get(columnIndex);
	}
}
