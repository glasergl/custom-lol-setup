package de.glasergl.custom.lol.setup.model.builder;

import de.glasergl.custom.lol.setup.model.entity.*;
import de.glasergl.custom.lol.setup.ui.builder.ItemBuildBuilder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

public final class SetupBuilder {
    private final @Getter Champion me;
    private final Optional<Role> role;
    private final @Getter Champion enemy;
    private final @Getter RunePageBuilder runePageBuilder;
    private final @Getter ItemBuildBuilder itemBuildBuilder;
    private final @Getter List<Spell> spellMaxOrder;

    private @Getter
    @Setter Optional<SummonerSpell> firstSummonerSpell;
    private @Getter
    @Setter Optional<SummonerSpell> secondSummonerSpell;
    private @Getter
    @Setter Optional<Spell> startSpell;
    private @Getter
    @Setter String notes;

    public SetupBuilder(final Champion me, final Optional<Role> role, final Champion enemy, final RunePageBuilder runePageBuilder, final ItemBuildBuilder itemBuildBuilder, final Optional<SummonerSpell> firstSummonerSpell, final Optional<SummonerSpell> secondSummonerSpell,
                        final Optional<Spell> startSpell, final List<Spell> spellMaxOrder, final String notes) {
        this.me = me;
        this.role = role;
        this.enemy = enemy;
        this.runePageBuilder = runePageBuilder;
        this.itemBuildBuilder = itemBuildBuilder;
        this.firstSummonerSpell = firstSummonerSpell;
        this.secondSummonerSpell = secondSummonerSpell;
        this.startSpell = startSpell;
        this.spellMaxOrder = spellMaxOrder;
        this.notes = notes;
    }

    public Setup build() {
        return new Setup(me, role.orElse(null), enemy, runePageBuilder.build(), firstSummonerSpell.orElse(null), secondSummonerSpell.orElse(null), startSpell.orElse(null), spellMaxOrder, itemBuildBuilder.build(), notes);
    }
}
