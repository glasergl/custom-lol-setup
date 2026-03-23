package de.glasergl.custom.lol.setup.model.entity;

public record Setup(Champion me, Role role, Champion enemy, RunePage runePage, SummonerSpell first, SummonerSpell second, ItemBuild build, String notes) {
}
