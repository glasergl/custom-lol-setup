package de.glasergl.custom.lol.setup.model.entity;

import java.util.List;

public record MatchUp(Champion me, Role role, Champion enemy, RunePage runePage, SummonerSpell first,
                      SummonerSpell second, Spell startSpell, List<Spell> spellMaxOrder, ItemBuild build, String notes) {
}
