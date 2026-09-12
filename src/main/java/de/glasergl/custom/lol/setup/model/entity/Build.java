package de.glasergl.custom.lol.setup.model.entity;

import java.util.List;

public record Build(RunePage runePage, SummonerSpell firstSpell, SummonerSpell secondSpell, Spell startSpell,
                    List<Spell> spellMaxOrder, ItemBuild items) {
}
