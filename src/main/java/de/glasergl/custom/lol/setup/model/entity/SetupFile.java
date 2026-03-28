package de.glasergl.custom.lol.setup.model.entity;

import java.util.Set;

public record SetupFile(Set<Setup> setups, Set<Champion> favoriteChampions) {
}
