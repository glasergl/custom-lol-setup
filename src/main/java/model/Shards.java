package model;

import static model.Rune.getListOfRunes;

import java.util.List;

public class Shards {
	public static final List<List<Rune>> ALL = List.of(
			getListOfRunes("Adaptive Force", "Attack Speed", "Ability Haste"),
			getListOfRunes("Adaptive Force", "Movement Speed", "Scaling Bonus Health"),
			getListOfRunes("Bonus Health", "Tenacity and Slow Resist", "Scaling Bonus Health"));
}
