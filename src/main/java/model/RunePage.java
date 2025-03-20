package model;

import java.util.ArrayList;
import java.util.List;

public final class RunePage {
	private final List<SelectableRune> keyStones;
	private final SlotRunes slotRunes;
	private final SecondPath secondPath;

	public RunePage(final RunePath first, final RunePath second) {
		this.keyStones = createSelectableRunes(first.getKeyStones());
		this.slotRunes = new SlotRunes(first.getSlotRunes());
		this.secondPath = new SecondPath(second.getSlotRunes());
	}

	public void selectKeyStone(final int columnIndex) {
		if (columnIndex < 0 || columnIndex >= keyStones.size()) {
			throw new IllegalArgumentException();
		}
		for (final SelectableRune keyStone : keyStones) {
			keyStone.setSelected(false);
		}
		keyStones.get(columnIndex).setSelected(true);
	}

	public void selectSlotRune(final int rowIndex, final int columnIndex) {
		slotRunes.select(rowIndex, columnIndex);
	}

	public void selectSecondPath(final int rowIndex, final int columnIndex) {
		secondPath.select(rowIndex, columnIndex);
	}

	public List<SelectableRune> getKeyStones() {
		return keyStones;
	}

	public SlotRunes getSlotRunes() {
		return slotRunes;
	}

	public SecondPath getSecondPath() {
		return secondPath;
	}

	public static List<SelectableRune> createSelectableRunes(final List<Rune> runes) {
		final List<SelectableRune> selectableRunes = new ArrayList<>(runes.size());
		for (final Rune rune : runes) {
			selectableRunes.add(new SelectableRune(rune, false));
		}
		return selectableRunes;
	}
}
