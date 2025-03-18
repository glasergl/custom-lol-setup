package model;

import java.util.List;

import static model.Rune.getListOfRunes;

public final class Shards {
	private final List<List<Rune>> slotRunes;

	private Shards(final List<List<Rune>> slotRunes) {
		this.slotRunes = slotRunes;
	}

	public List<List<Rune>> getSlotRunes() {
		return slotRunes;
	}

	public static final Shards SHARDS = new Shards(
			List.of(getListOfRunes("Adaptive Force", "Attack Speed", "Ability Haste"),
					getListOfRunes("Adaptive Force", "Movement Speed", "Scaling Bonus Health"),
					getListOfRunes("Bonus Health", "Tenacity and Slow Resist", "Scaling Bonus Health")));
}
