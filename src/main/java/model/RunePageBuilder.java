package model;

import java.util.List;

/**
 * Introduces the functionality to change rune paths of a rune page while
 * ensuring no invalid combination is created. Changing a rune path requires a
 * new rune page instance.
 */
public final class RunePageBuilder {
	private final List<RunePath> runePaths;
	private RunePage runePage;

	public RunePageBuilder(final List<RunePath> allRunePaths, final RunePath initialMainRunePath,
			final RunePath initialSecondRunePath) {
		if (initialMainRunePath.equals(initialSecondRunePath)) {
			throw new IllegalArgumentException();
		}
		this.runePaths = allRunePaths;
		this.runePage = new RunePage(initialMainRunePath, initialSecondRunePath);
	}

	public RunePageBuilder(final List<RunePath> allRunePaths) {
		this(allRunePaths, allRunePaths.get(0), allRunePaths.get(1));
	}

	public void changeMainPath(final RunePath nextMainRunePath) {
		if (nextMainRunePath.equals(runePage.getMainRunePath())) {
			return;
		}
		final RunePath oldSecondRunePath = runePage.getSecondRunePath();
		if (!nextMainRunePath.equals(oldSecondRunePath)) {
			final List<List<SelectableRune>> previoslySelectedRunes = runePage.getSecondPath().get();
			runePage = new RunePage(nextMainRunePath, oldSecondRunePath);
			for (int rowIndex = 0; rowIndex < previoslySelectedRunes.size(); rowIndex++) {
				final List<SelectableRune> row = previoslySelectedRunes.get(rowIndex);
				for (int columnIndex = 0; columnIndex < row.size(); columnIndex++) {
					final SelectableRune rune = row.get(columnIndex);
					if (rune.isSelected()) {
						runePage.selectSecondPath(rowIndex, columnIndex);
					}
				}
			}
		} else {
			final RunePath alternativeSecondRunePath = getFirstRunePathApartFrom(oldSecondRunePath);
			runePage = new RunePage(nextMainRunePath, alternativeSecondRunePath);
		}
	}

	public void changeSecondPath(final RunePath nextSecondRunePath) {
		final RunePath oldMainRunePath = runePage.getMainRunePath();
		if (nextSecondRunePath.equals(runePage.getSecondRunePath()) || nextSecondRunePath.equals(oldMainRunePath)) {
			return;
		}
		final List<List<SelectableRune>> previouslySelectedSlotRunesOfOldMainPath = runePage.getSlotRunes().get();
		final List<SelectableRune> previouslySelectedKeyStones = runePage.getKeyStones();
		runePage = new RunePage(oldMainRunePath, nextSecondRunePath);
		for (int rowIndex = 0; rowIndex < previouslySelectedSlotRunesOfOldMainPath.size(); rowIndex++) {
			final List<SelectableRune> row = previouslySelectedSlotRunesOfOldMainPath.get(rowIndex);
			for (int columnIndex = 0; columnIndex < row.size(); columnIndex++) {
				final SelectableRune rune = row.get(columnIndex);
				if (rune.isSelected()) {
					runePage.selectSlotRune(rowIndex, columnIndex);
				}
			}
		}
		for (int keyStoneIndex = 0; keyStoneIndex < previouslySelectedKeyStones.size(); keyStoneIndex++) {
			final SelectableRune keyStone = previouslySelectedKeyStones.get(keyStoneIndex);
			if (keyStone.isSelected()) {
				runePage.selectKeyStone(keyStoneIndex);
			}
		}
	}

	private RunePath getFirstRunePathApartFrom(final RunePath excludedRunePath) {
		for (final RunePath runePath : runePaths) {
			if (!runePath.equals(excludedRunePath)) {
				return runePath;
			}
		}
		throw new IllegalStateException("Unable to retrieve other rune path than " + excludedRunePath.getName());
	}

	public List<RunePath> getRunePaths() {
		return runePaths;
	}

	public RunePage getRunePage() {
		return runePage;
	}
}
