package de.glasergl.custom.lol.setup.model.entity;

import java.util.List;

public record RunePath(String name, List<Rune> keyStones, List<List<Rune>> slotRuneRows) {
    public static final RunePath PRECISION = new RunePath("PRECISION", List.of(Rune.PRESS_THE_ATTACK, Rune.LETHAL_TEMPO, Rune.FLEET_FOOTWORK, Rune.CONQUEROR),
            List.of(List.of(Rune.ABSORB_LIFE, Rune.TRIUMPH, Rune.PRESENCE_OF_MIND), List.of(Rune.LEGEND_ALACRITY, Rune.LEGEND_HASTE, Rune.LEGEND_BLOODLINE), List.of(Rune.COUP_DE_GRACE, Rune.CUT_DOWN, Rune.LAST_STAND)));

    public static final RunePath DOMINATION = new RunePath("DOMINATION", List.of(Rune.ELECTROCUTE, Rune.DARK_HARVEST, Rune.HAIL_OF_BLADES),
            List.of(List.of(Rune.CHEAP_SHOT, Rune.TASTE_OF_BLOOD, Rune.SUDDEN_IMPACT), List.of(Rune.SIXTH_SENSE, Rune.GRISLY_MEMENTOS, Rune.DEEP_WARD), List.of(Rune.TREASURE_HUNTER, Rune.RELENTLESS_HUNTER, Rune.ULTIMATE_HUNTER)));

    public static final RunePath SORCERY = new RunePath("SORCERY", List.of(Rune.SUMMON_AERY, Rune.ARCANE_COMET, Rune.STORMRAIDERS_SURGE, Rune.DEATHFIRE_TOUCH),
            List.of(List.of(Rune.AXIOM_ARCANIST, Rune.MANAFLOW_BAND, Rune.NIMBUS_CLOAK), List.of(Rune.TRANSCENDENCE, Rune.CELERITY, Rune.ABSOLUTE_FOCUS), List.of(Rune.SCORCH, Rune.WATERWALKING, Rune.GATHERING_STORM)));

    public static final RunePath RESOLVE = new RunePath("RESOLVE", List.of(Rune.GRASP_OF_THE_UNDYING, Rune.AFTERSHOCK, Rune.GUARDIAN),
            List.of(List.of(Rune.DEMOLISH, Rune.FONT_OF_LIFE, Rune.SHIELD_BASH), List.of(Rune.CONDITIONING, Rune.SECOND_WIND, Rune.BONE_PLATING), List.of(Rune.OVERGROWTH, Rune.REVITALIZE, Rune.UNFLINCHING)));

    public static final RunePath INSPIRATION = new RunePath("INSPIRATION", List.of(Rune.GLACIAL_AUGMENT, Rune.UNSEALED_SPELLBOOK, Rune.FIRST_STRIKE),
            List.of(List.of(Rune.HEXTECH_FLASHTRAPTION, Rune.MAGICAL_FOOTWEAR, Rune.CASH_BACK), List.of(Rune.TRIPLE_TONIC, Rune.TIME_WARP_TONIC, Rune.BISCUIT_DELIVERY), List.of(Rune.COSMIC_INSIGHT, Rune.APPROACH_VELOCITY, Rune.JACK_OF_ALL_TRADES)));

    public static final List<List<Rune>> SHARDS = List.of(List.of(Rune.OFFENSIVE_ADAPTIVE_FORCE, Rune.OFFENSIVE_ATTACK_SPEED, Rune.OFFENSIVE_ABILITY_HASTE), List.of(Rune.FLEX_ADAPTIVE_FORCE, Rune.FLEX_MOVEMENT_SPEED, Rune.FLEX_SCALING_HEALTH),
            List.of(Rune.DEFENSE_DIRECT_HEALTH, Rune.DEFENSE_TENACITY_SLOW_RESIST, Rune.DEFENSE_SCALING_HEALTH));

    public static final List<RunePath> ALL = List.of(PRECISION, DOMINATION, SORCERY, RESOLVE, INSPIRATION);
}
