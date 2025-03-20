package model;

import java.util.List;

public final class RunePage {
	private final List<Rune> keyStones;
	private final SlotRunes slotRunes;
	private final SecondPath secondPath;

	public RunePage(final RunePath first, final RunePath second) {
		this.keyStones = first.getKeyStones();
		this.slotRunes = new SlotRunes(first.getSlotRunes());
		this.secondPath = new SecondPath(second.getSlotRunes());
	}

	public void selectKeyStone(final int columnIndex) {
		if (columnIndex < 0 || columnIndex >= keyStones.size()) {
			throw new IllegalArgumentException();
		}
		keyStones.get(columnIndex).setSelected(true);
	}

	public void selectSlotRune(final int rowIndex, final int columnIndex) {
		slotRunes.select(rowIndex, columnIndex);
	}

	public void selectSecondPath(final int rowIndex, final int columnIndex) {
		secondPath.select(rowIndex, columnIndex);
	}
}
