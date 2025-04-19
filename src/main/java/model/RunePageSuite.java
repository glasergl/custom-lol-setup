package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class RunePageSuite {
	public static final String DEFAULT_RUNE_GROUP_NAME = "default";
	private static final String DEFAULT_EMPTY_RUNE_PAGE_TITLE = "New Rune Page";

	private final Map<String, List<RunePage>> runePagesByGroupName;
	private Optional<String> selectedGroupName = Optional.empty();
	private Optional<Integer> selectedRunePageIndex = Optional.empty();

	public RunePageSuite(final Map<String, List<RunePage>> runePagesByGroupName) {
		for (final String groupName : runePagesByGroupName.keySet()) {
			if (groupName.isEmpty()) {
				throw new IllegalArgumentException();
			}
		}
		this.runePagesByGroupName = runePagesByGroupName;
	}

	public boolean containsGroup(final String groupName) {
		return runePagesByGroupName.keySet().contains(groupName);
	}

	public void addGroupIfNotExistsAndSelect(final String groupName) {
		if (groupName.isEmpty()) {
			throw new IllegalArgumentException();
		}
		if (!runePagesByGroupName.containsKey(groupName)) {
			runePagesByGroupName.put(groupName, new ArrayList<>());
			selectedRunePageIndex = Optional.empty();
		}
		selectGroup(groupName);
	}

	public void selectGroup(final String groupName) {
		if (!containsGroup(groupName)) {
			throw new IllegalArgumentException();
		}
		selectedGroupName = Optional.of(groupName);
		selectedRunePageIndex = Optional.empty();
	}

	public void deleteGroup(final String groupName) {
		if (!containsGroup(groupName)) {
			throw new IllegalArgumentException();
		}
		runePagesByGroupName.remove(groupName);
		if (selectedGroupName.isPresent() && selectedGroupName.get().equals(groupName)) {
			selectedGroupName = Optional.empty();
			selectedRunePageIndex = Optional.empty();
		}
	}

	public void addEmptyRunePage() {
		if (!selectedGroupName.isPresent()) {
			addGroupIfNotExistsAndSelect(DEFAULT_RUNE_GROUP_NAME);
			addEmptyRunePage();
		} else {
			final RunePage emptyRunePage = new RunePage(DEFAULT_EMPTY_RUNE_PAGE_TITLE, RunePath.PRECISION,
					RunePath.SORCERY);
			final List<RunePage> runePagesOfGroup = runePagesByGroupName.get(selectedGroupName.get());
			runePagesOfGroup.add(emptyRunePage);
			selectedRunePageIndex = Optional.of(runePagesOfGroup.size() - 1);
		}
	}

	public void selectRunePage(final String groupName, final int i) {
		if (!containsGroup(groupName)) {
			throw new IllegalArgumentException();
		}
		final List<RunePage> runePagesOfGroup = runePagesByGroupName.get(groupName);
		if (i < 0 || i >= runePagesOfGroup.size()) {
			throw new IllegalArgumentException();
		}
		selectedGroupName = Optional.of(groupName);
		selectedRunePageIndex = Optional.of(i);
	}

	public void deleteRunePage(final String groupName, final int i) {
		if (!containsGroup(groupName)) {
			throw new IllegalArgumentException();
		}
		final List<RunePage> runePagesOfGroup = runePagesByGroupName.get(groupName);
		if (i < 0 || i >= runePagesOfGroup.size()) {
			throw new IllegalArgumentException();
		}
		runePagesOfGroup.remove(i);
		if (selectedRunePageIndex.isPresent() && selectedRunePageIndex.get().equals(i)) {
			selectedRunePageIndex = Optional.empty();
		}
	}

	public Optional<String> getSelectedGroupName() {
		return selectedGroupName;
	}

	public Optional<Integer> getSelectedRunePageIndex() {
		return selectedRunePageIndex;
	}

	public Optional<RunePage> getSelectedRunePage() {
		if (selectedGroupName.isEmpty() || selectedRunePageIndex.isEmpty()) {
			return Optional.empty();
		} else {
			final String groupName = selectedGroupName.get();
			final int index = selectedRunePageIndex.get();
			return Optional.of(runePagesByGroupName.get(groupName).get(index));
		}
	}

	public Map<String, List<RunePage>> getRunePagesByGroupName() {
		return runePagesByGroupName;
	}
}
