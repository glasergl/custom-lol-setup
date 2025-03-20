package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(final Object otherObject) {
		if (!(otherObject instanceof Rune)) {
			return false;
		}
		final Rune otherRune = (Rune) otherObject;
		return name.equals(otherRune.name);
	}

	public static List<Rune> getListOfRunes(final String... runeNames) {
		final List<Rune> runes = new ArrayList<>(runeNames.length);
		for (final String runeName : runeNames) {
			runes.add(new Rune(runeName));
		}
		return runes;
	}
}
