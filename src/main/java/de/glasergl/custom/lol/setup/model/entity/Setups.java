package de.glasergl.custom.lol.setup.model.entity;

import java.util.Map;
import java.util.Set;

public record Setups(Map<Champion, String> championSpecificNotes, Set<Setup> setups) {
}
