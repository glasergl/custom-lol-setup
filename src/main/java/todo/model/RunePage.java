package todo.model;

import java.util.List;

/**
 * Contains all components of a mutable runepage with methods to select runes.
 * The methods ensure no invalid runepage is reflected.
 */
public final class RunePage {
	private final SelectableRunes selectableShards = new SelectableRunes(Shards.ALL);

	private String title;
	private RunePath mainRunePath;
	private RunePath secondRunePath;
	private SelectableRunes selectableKeyStones;
	private SelectableRunes selectableSlotRunes;
	private SelectableSecondPathRunes selectableSecondPathSlotRunes;

	public RunePage(final String title, final RunePath mainRunePath, final RunePath secondRunePath) {
		if (mainRunePath.equals(secondRunePath)) {
			throw new IllegalArgumentException();
		}
		this.mainRunePath = mainRunePath;
		this.secondRunePath = secondRunePath;
		this.selectableKeyStones = new SelectableRunes(List.of(mainRunePath.getKeyStones()));
		this.selectableSlotRunes = new SelectableRunes(mainRunePath.getSlotRunes());
		this.selectableSecondPathSlotRunes = new SelectableSecondPathRunes(secondRunePath.getSlotRunes());
		setTitle(title);
	}

	public void selectKeyStone(final int columnIndex) {
		selectableKeyStones.select(0, columnIndex);
	}

	public void selectSlotRune(final int rowIndex, final int columnIndex) {
		selectableSlotRunes.select(rowIndex, columnIndex);
	}

	public void selectSecondPath(final int rowIndex, final int columnIndex) {
		selectableSecondPathSlotRunes.select(rowIndex, columnIndex);
	}

	public void selectShard(final int rowIndex, final int columnIndex) {
		selectableShards.select(rowIndex, columnIndex);
	}

	public void selectMainPath(final RunePath nextMainRunePath) {
		if (nextMainRunePath.equals(mainRunePath)) {
			return;
		}
		selectableKeyStones = new SelectableRunes(List.of(nextMainRunePath.getKeyStones()));
		selectableSlotRunes = new SelectableRunes(nextMainRunePath.getSlotRunes());
		mainRunePath = nextMainRunePath;
		if (nextMainRunePath.equals(secondRunePath)) {
			final RunePath alternativeSecondRunePath = getFirstRunePathExcept(nextMainRunePath);
			secondRunePath = alternativeSecondRunePath;
			selectableSecondPathSlotRunes = new SelectableSecondPathRunes(alternativeSecondRunePath.getSlotRunes());
		}
	}

	public void selectSecondPath(final RunePath nextSecondRunePath) {
		if (nextSecondRunePath.equals(secondRunePath) || nextSecondRunePath.equals(mainRunePath)) {
			return;
		}
		secondRunePath = nextSecondRunePath;
		selectableSecondPathSlotRunes = new SelectableSecondPathRunes(nextSecondRunePath.getSlotRunes());
	}

	public void setTitle(final String title) {
		if (title.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.title = title;
	}

	public RunePath getMainRunePath() {
		return mainRunePath;
	}

	public RunePath getSecondRunePath() {
		return secondRunePath;
	}

	public SelectableRunes getKeyStones() {
		return selectableKeyStones;
	}

	public SelectableRunes getSlotRunes() {
		return selectableSlotRunes;
	}

	public SelectableSecondPathRunes getSecondPathSlotRunes() {
		return selectableSecondPathSlotRunes;
	}

	public SelectableRunes getShards() {
		return selectableShards;
	}

	public String getTitle() {
		return title;
	}

	private RunePath getFirstRunePathExcept(final RunePath excludedRunePath) {
		for (final RunePath runePath : RunePath.ALL) {
			if (!runePath.equals(excludedRunePath)) {
				return runePath;
			}
		}
		throw new IllegalStateException("Unable to retrieve other rune path than " + excludedRunePath.getName());
	}
}
