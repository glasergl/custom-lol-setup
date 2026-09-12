package de.glasergl.custom.lol.setup.model.entity;

import java.util.Set;

public record ChampionSetup(Champion me, Role role, String notes, Set<MatchUp> matchUps) {
}
