package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class RunePageGroup {
	private static final Set<String> KNOWN_RUNE_PAGE_GROUP_NAMES = new HashSet<>();

	private final String name;
	private final List<RunePage> runePages = new ArrayList<>();

	public RunePageGroup(final String name) {
		if (name.isEmpty() || KNOWN_RUNE_PAGE_GROUP_NAMES.contains(name)) {
			throw new IllegalArgumentException();
		}
		this.name = name;
		KNOWN_RUNE_PAGE_GROUP_NAMES.add(name);
	}

	public RunePageGroup() {
		this("New Rune Page Group");
	}

	public void add(final RunePage runePage) {
		runePages.add(runePage);
	}

	public void swap(final RunePage runePage1, final RunePage runePage2) {
		Collections.swap(runePages, runePages.indexOf(runePage1), runePages.indexOf(runePage2));
	}

	public void delete(final RunePage runePage) {
		runePages.remove(runePage);
	}

	public String getName() {
		return name;
	}
}
