package model;

import java.util.ArrayList;
import java.util.List;

public final class Rune {
	private final String name;
	private boolean isSelected = false;

	public Rune(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(final boolean shouldBeSelected) {
		this.isSelected = shouldBeSelected;
	}

	public static List<Rune> getListOfRunes(final String... runeNames) {
		final List<Rune> runes = new ArrayList<>(runeNames.length);
		for (final String runeName : runeNames) {
			runes.add(new Rune(runeName));
		}
		return runes;
	}
}
