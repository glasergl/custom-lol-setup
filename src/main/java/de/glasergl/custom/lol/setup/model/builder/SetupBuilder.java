package de.glasergl.custom.lol.setup.model.builder;

import java.util.Optional;

import de.glasergl.custom.lol.setup.model.entity.Champion;
import de.glasergl.custom.lol.setup.model.entity.Role;
import de.glasergl.custom.lol.setup.model.entity.RunePath;
import de.glasergl.custom.lol.setup.model.entity.Setup;
import de.glasergl.custom.lol.setup.model.entity.SummonerSpell;

public final class SetupBuilder {
    private final Champion me;
    private final Optional<Role> role;
    private final Champion enemy;
    private final RunePageBuilder runePageBuilder = new RunePageBuilder(RunePath.PRECISION, RunePath.RESOLVE);
    private final ItemBuildBuilder itemBuildBuilder = new ItemBuildBuilder();

    private Optional<SummonerSpell> firstSummonerSpell;
    private Optional<SummonerSpell> secondSummonerSpell;
    private Optional<String> notes;

    public SetupBuilder(final Champion me, final Optional<Role> role, final Champion enemy) {
	this.me = me;
	this.role = role;
	this.enemy = enemy;
    }

    public Setup build() {
	return new Setup(me, role.orElse(null), enemy, runePageBuilder.build(), firstSummonerSpell.orElse(null), secondSummonerSpell.orElse(null), itemBuildBuilder.build(), notes.orElse(null));
    }
}
