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
			final List<List<SelectableRune>> previoslySelectedRunes = runePage.getSecondPathSlotRunes().get();
			runePage = new RunePage(nextMainRunePath, oldSecondRunePath);
			copyPreviousSecondPathSelectionsToSecondPath(previoslySelectedRunes);
		} else {
			final RunePath alternativeSecondRunePath = getFirstRunePathExcept(oldSecondRunePath);
			runePage = new RunePage(nextMainRunePath, alternativeSecondRunePath);
		}
	}

	public void changeSecondPath(final RunePath nextSecondRunePath) {
		final RunePath oldMainRunePath = runePage.getMainRunePath();
		if (nextSecondRunePath.equals(runePage.getSecondRunePath()) || nextSecondRunePath.equals(oldMainRunePath)) {
			return;
		}
		final List<List<SelectableRune>> previouslySelectedSlotRunesOfMainPath = runePage.getSlotRunes().get();
		final List<SelectableRune> previouslySelectedKeyStones = runePage.getKeyStones().get().get(0);
		runePage = new RunePage(oldMainRunePath, nextSecondRunePath);
		copyPreviousMainPathSelectionsToMainPath(previouslySelectedKeyStones, previouslySelectedSlotRunesOfMainPath);
	}

	private void copyPreviousSecondPathSelectionsToSecondPath(final List<List<SelectableRune>> previoslySelectedRunes) {
		for (int rowIndex = 0; rowIndex < previoslySelectedRunes.size(); rowIndex++) {
			final List<SelectableRune> row = previoslySelectedRunes.get(rowIndex);
			for (int columnIndex = 0; columnIndex < row.size(); columnIndex++) {
				final SelectableRune rune = row.get(columnIndex);
				if (rune.isSelected()) {
					runePage.selectSecondPath(rowIndex, columnIndex);
				}
			}
		}
	}

	private void copyPreviousMainPathSelectionsToMainPath(final List<SelectableRune> previouslySelectedKeyStones,
			final List<List<SelectableRune>> previouslySelectedSlotRunes) {
		for (int rowIndex = 0; rowIndex < previouslySelectedSlotRunes.size(); rowIndex++) {
			final List<SelectableRune> row = previouslySelectedSlotRunes.get(rowIndex);
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

	private RunePath getFirstRunePathExcept(final RunePath excludedRunePath) {
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
