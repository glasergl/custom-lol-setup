package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Immutable class that represents a rune by name.
 */
public final class Rune {
	private final String name;

	public Rune(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
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

	@Override
	public String toString() {
		return name;
	}

	public static List<Rune> getListOfRunes(final String... runeNames) {
		final List<Rune> runes = new ArrayList<>(runeNames.length);
		for (final String runeName : runeNames) {
			runes.add(new Rune(runeName));
		}
		return runes;
	}
}
