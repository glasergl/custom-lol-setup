package todo.model;

import static todo.model.Rune.getListOfRunes;

import java.util.List;

/**
 * Immutable representation of a rune path with its keystones and slot runes.
 */
public final class RunePath {
	private final String name;
	private final List<Rune> keyStones;
	private final List<List<Rune>> slotRuneRows;

	private RunePath(final String name, final List<Rune> keyStones, final List<List<Rune>> slotRunes) {
		this.name = name;
		this.keyStones = keyStones;
		this.slotRuneRows = slotRunes;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof RunePath)) {
			return false;
		}
		final RunePath otherRunePath = (RunePath) other;
		return name.equals(otherRunePath.name);
	}

	public String getName() {
		return name;
	}

	public List<Rune> getKeyStones() {
		return keyStones;
	}

	public List<List<Rune>> getSlotRunes() {
		return slotRuneRows;
	}

	public static final RunePath PRECISION = new RunePath("Precision",
			getListOfRunes("Press the Attack", "Lethal Tempo", "Fleet Footwork", "Conqueror"),
			List.of(getListOfRunes("Absorb Life", "Triumph", "Presence of Mind"),
					getListOfRunes("Legend: Alacrity", "Legend: Haste", "Legend: Bloodline"),
					getListOfRunes("Coup de Grace", "Cut Down", "Last Stand")));

	public static final RunePath DOMINATION = new RunePath("Domination",
			getListOfRunes("Electrocute", "Dark Harvest", "Hail of Blades"),
			List.of(getListOfRunes("Cheap Shot", "Taste of Blood", "Sudden Impact"),
					getListOfRunes("Sixth Sense", "Grisly Mementos", "Deep Ward"),
					getListOfRunes("Treasure Hunter", "Relentless Hunter", "Ultimate Hunter")));

	public static final RunePath SORCERY = new RunePath("Sorcery",
			getListOfRunes("Summon Aery", "Arcane Comet", "Phase Rush"),
			List.of(getListOfRunes("Axiom Arcanist", "Manaflow Band", "Nimbus Cloak"),
					getListOfRunes("Transcendence", "Celerity", "Absolute Focus"),
					getListOfRunes("Scorch", "Waterwalking", "Gathering Storm")));

	public static final RunePath RESOLVE = new RunePath("Resolve",
			getListOfRunes("Grasp of the Undying", "Aftershock", "Guardian"),
			List.of(getListOfRunes("Demolish", "Font of Life", "Shield Bash"),
					getListOfRunes("Conditioning", "Second Wind", "Bone Plating"),
					getListOfRunes("Overgrowth", "Revitalize", "Unflinching")));

	public static final RunePath INSPIRATION = new RunePath("Inspiration",
			getListOfRunes("Glacial Augment", "Unsealed Spellbook", "First Strike"),
			List.of(getListOfRunes("Hextech Flashtraption", "Magical Footwear", "Cash Back"),
					getListOfRunes("Triple Tonic", "Time Warp Tonic", "Biscuit Delivery"),
					getListOfRunes("Cosmic Insight", "Approach Velocity", "Jack of All Trades")));

	public static final List<RunePath> ALL = List.of(PRECISION, DOMINATION, SORCERY, RESOLVE, INSPIRATION);
}
