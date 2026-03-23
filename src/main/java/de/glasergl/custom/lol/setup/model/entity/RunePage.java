package de.glasergl.custom.lol.setup.model.entity;

import java.util.Set;

public record RunePage(Set<Rune> mainPathRuneSelections, Set<Rune> secondPathRuneSelections, Set<Rune> selectedShards) {
}
