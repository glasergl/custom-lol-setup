package de.glasergl.custom.lol.setup.model.builder;

import java.util.Optional;

import de.glasergl.custom.lol.setup.model.entity.Champion;
import de.glasergl.custom.lol.setup.model.entity.Role;
import de.glasergl.custom.lol.setup.model.entity.Setup;
import de.glasergl.custom.lol.setup.model.entity.SummonerSpell;
import lombok.Getter;

public final class SetupBuilder {
    private final Champion me;
    private final Optional<Role> role;
    private final Champion enemy;
    private final @Getter RunePageBuilder runePageBuilder;
    private final @Getter ItemBuildBuilder itemBuildBuilder;

    private Optional<SummonerSpell> firstSummonerSpell = Optional.empty();
    private Optional<SummonerSpell> secondSummonerSpell = Optional.empty();
    private String notes = "";

    public SetupBuilder(final Champion me, final Optional<Role> role, final Champion enemy, final RunePageBuilder runePageBuilder, final ItemBuildBuilder itemBuildBuilder) {
	this.me = me;
	this.role = role;
	this.enemy = enemy;
	this.runePageBuilder = runePageBuilder;
	this.itemBuildBuilder = itemBuildBuilder;
    }

    public Setup build() {
	return new Setup(me, role.orElse(null), enemy, runePageBuilder.build(), firstSummonerSpell.orElse(null), secondSummonerSpell.orElse(null), itemBuildBuilder.build(), notes);
    }
}
