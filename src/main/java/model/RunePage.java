package model;

import static model.Rune.getListOfRunes;

import java.util.List;

/**
 * Contains all components of a mutable runepage with methods to select runes.
 * The methods ensure no invalid runepage is reflected. After instantiation, the
 * rune paths cannot be changed anymore (selections can be changed, of course).
 */
public final class RunePage {
	private final RunePath mainRunePath;
	private final RunePath secondRunePath;
	private final SelectableRunes keyStones;
	private final SelectableRunes slotRunes;
	private final SelectableSecondPathRunes secondPathSlotRunes;
	private final SelectableRunes shards = new SelectableRunes(
			List.of(getListOfRunes("Adaptive Force", "Attack Speed", "Ability Haste"),
					getListOfRunes("Adaptive Force", "Movement Speed", "Scaling Bonus Health"),
					getListOfRunes("Bonus Health", "Tenacity and Slow Resist", "Scaling Bonus Health")));

	public RunePage(final RunePath mainRunePath, final RunePath secondRunePath) {
		if (mainRunePath.equals(secondRunePath)) {
			throw new IllegalArgumentException();
		}
		this.mainRunePath = mainRunePath;
		this.secondRunePath = secondRunePath;
		this.keyStones = new SelectableRunes(List.of(mainRunePath.getKeyStones()));
		this.slotRunes = new SelectableRunes(mainRunePath.getSlotRunes());
		this.secondPathSlotRunes = new SelectableSecondPathRunes(secondRunePath.getSlotRunes());
	}

	public void selectKeyStone(final int columnIndex) {
		keyStones.select(0, columnIndex);
	}

	public void selectSlotRune(final int rowIndex, final int columnIndex) {
		slotRunes.select(rowIndex, columnIndex);
	}

	public void selectSecondPath(final int rowIndex, final int columnIndex) {
		secondPathSlotRunes.select(rowIndex, columnIndex);
	}

	public void selectShard(final int rowIndex, final int columnIndex) {
		shards.select(rowIndex, columnIndex);
	}

	public RunePath getMainRunePath() {
		return mainRunePath;
	}

	public RunePath getSecondRunePath() {
		return secondRunePath;
	}

	public SelectableRunes getKeyStones() {
		return keyStones;
	}

	public SelectableRunes getSlotRunes() {
		return slotRunes;
	}

	public SelectableSecondPathRunes getSecondPathSlotRunes() {
		return secondPathSlotRunes;
	}

	public SelectableRunes getShards() {
		return shards;
	}
}
