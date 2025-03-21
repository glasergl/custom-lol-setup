package model;

import java.util.List;

public final class RunePageBuilder {
	private final List<RunePath> runePaths;
	private RunePage runePage;

	public RunePageBuilder(final List<RunePath> runePaths, final RunePath initialMainRunePath,
			final RunePath initialSecondRunePath) {
		if (initialMainRunePath.equals(initialSecondRunePath)) {
			throw new IllegalArgumentException();
		}
		this.runePaths = runePaths;
		this.runePage = new RunePage(initialMainRunePath, initialSecondRunePath);
	}

	public RunePageBuilder(final List<RunePath> runePaths) {
		this(runePaths, runePaths.get(0), runePaths.get(1));
	}

	public void changeMainPath(final RunePath nextMainRunePath) {
		if (nextMainRunePath.equals(runePage.getMainRunePath())) {
			return;
		}
		final RunePath oldSecondRunePath = runePage.getSecondRunePath();
		if (!nextMainRunePath.equals(oldSecondRunePath)) {
			final SelectableSecondPathRunes slotRunesOfOldSecondPath = runePage.getSecondPath();
			runePage = new RunePage(nextMainRunePath, oldSecondRunePath);
			final List<List<SelectableRune>> previoslySelectedRunes = slotRunesOfOldSecondPath.get();
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
		if (nextSecondRunePath.equals(oldMainRunePath)) {
			throw new IllegalArgumentException();
		}
		runePage = new RunePage(oldMainRunePath, nextSecondRunePath);
	}

	public List<RunePath> getRunePaths() {
		return runePaths;
	}

	public RunePage getRunePage() {
		return runePage;
	}

	private RunePath getFirstRunePathApartFrom(final RunePath excludedRunePath) {
		for (final RunePath runePath : runePaths) {
			if (!runePath.equals(excludedRunePath)) {
				return runePath;
			}
		}
		throw new IllegalStateException("Unable to retrieve other rune path than " + excludedRunePath.getName());
	}
}
