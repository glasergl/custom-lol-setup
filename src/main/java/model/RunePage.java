package model;

import java.util.List;

/**
 * Contains all components of a mutable runepage with methods to select runes.
 * The methods ensure no invalid runepage is reflected. After instantiation, the
 * rune paths cannot be changed anymore (selections can be changed, of course).
 */
public final class RunePage {
	private final RunePath mainRunePath;
	private final RunePath secondRunePath;
	private final List<SelectableRune> keyStones;
	private final SelectableSlotRunes slotRunes;
	private final SelectableSecondPathRunes secondPath;

	public RunePage(final RunePath mainRunePath, final RunePath secondRunePath) {
		if (mainRunePath.equals(secondRunePath)) {
			throw new IllegalArgumentException();
		}
		this.mainRunePath = mainRunePath;
		this.secondRunePath = secondRunePath;
		this.keyStones = SelectableRune.createSelectableRunes(mainRunePath.getKeyStones());
		this.slotRunes = new SelectableSlotRunes(mainRunePath.getSlotRunes());
		this.secondPath = new SelectableSecondPathRunes(secondRunePath.getSlotRunes());
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

	public RunePath getMainRunePath() {
		return mainRunePath;
	}

	public RunePath getSecondRunePath() {
		return secondRunePath;
	}

	public List<SelectableRune> getKeyStones() {
		return keyStones;
	}

	public SelectableSlotRunes getSlotRunes() {
		return slotRunes;
	}

	public SelectableSecondPathRunes getSecondPath() {
		return secondPath;
	}
}
