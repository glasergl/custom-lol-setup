package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class RunePageSuite {
	private final Map<String, List<RunePage>> runePagesByGroupName;
	private Optional<RunePage> current = Optional.empty();

	public RunePageSuite(final Map<String, List<RunePage>> runePagesByGroupName) {
		this.runePagesByGroupName = runePagesByGroupName;
	}

	public void addEmptyRunePage(final String groupName) {
		final RunePage emptyRunePage = new RunePage("New Rune Page", RunePath.PRECISION, RunePath.SORCERY,
				RunePath.ALL);
		addRunePage(groupName, emptyRunePage);
	}

	public void addRunePage(final String groupName, final RunePage runePage) {
		if (!runePagesByGroupName.containsKey(groupName)) {
			runePagesByGroupName.put(groupName, new ArrayList<>());
		}
		final List<RunePage> runePagesOfGroup = runePagesByGroupName.get(groupName);
		runePagesOfGroup.add(runePage);
		current = Optional.of(runePage);
	}

	public RunePage getCurrentRunePage() {
		if (current.isEmpty()) {
			addEmptyRunePage("");
		}
		return current.get();
	}

	public void addGroup(final String groupName) {
		if (!runePagesByGroupName.containsKey(groupName)) {
			runePagesByGroupName.put(groupName, new ArrayList<>());
		}
	}

	public void selectRunePage(final String groupName, final int i) {
		final List<RunePage> runePagesOfGroup = runePagesByGroupName.get(groupName);
		current = Optional.of(runePagesOfGroup.get(i));
	}

	public void deleteRunePage(final String groupName, final int i) {
		final List<RunePage> runePagesOfGroup = runePagesByGroupName.get(groupName);
		runePagesOfGroup.remove(i);
	}

	public Map<String, List<RunePage>> getRunePagesByGroupName() {
		return runePagesByGroupName;
	}

	public Set<String> getGroupNames() {
		return runePagesByGroupName.keySet();
	}
}
