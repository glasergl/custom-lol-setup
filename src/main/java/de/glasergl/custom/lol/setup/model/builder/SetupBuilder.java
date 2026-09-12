package de.glasergl.custom.lol.setup.model.builder;

import de.glasergl.custom.lol.setup.model.entity.*;
import de.glasergl.custom.lol.setup.ui.builder.ItemBuildBuilder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

public final class SetupBuilder {
    private final @Getter Champion me;
    private final @Getter Role role;
    private final @Getter Champion enemy;
    private final @Getter RunePageBuilder runePageBuilder;
    private final @Getter ItemBuildBuilder itemBuildBuilder;
    private final @Getter List<Spell> spellMaxOrder;

    private @Getter
    @Setter String championNotes;
    private @Getter
    @Setter Optional<SummonerSpell> firstSummonerSpell;
    private @Getter
    @Setter Optional<SummonerSpell> secondSummonerSpell;
    private @Getter
    @Setter Optional<Spell> startSpell;
    private @Getter
    @Setter String matchUpNotes;

    public SetupBuilder(final Champion me, final Role role, final String championNotes, final Champion enemy, final RunePageBuilder runePageBuilder, final ItemBuildBuilder itemBuildBuilder, final Optional<SummonerSpell> firstSummonerSpell, final Optional<SummonerSpell> secondSummonerSpell,
                        final Optional<Spell> startSpell, final List<Spell> spellMaxOrder, final String matchUpNotes) {
        this.me = me;
        this.role = role;
        this.championNotes = championNotes;
        this.enemy = enemy;
        this.runePageBuilder = runePageBuilder;
        this.itemBuildBuilder = itemBuildBuilder;
        this.firstSummonerSpell = firstSummonerSpell;
        this.secondSummonerSpell = secondSummonerSpell;
        this.startSpell = startSpell;
        this.spellMaxOrder = spellMaxOrder;
        this.matchUpNotes = matchUpNotes;
    }

    public MatchUp build() {
        final Build build = new Build(runePageBuilder.build(), firstSummonerSpell.orElse(null), secondSummonerSpell.orElse(null), startSpell.orElse(null), spellMaxOrder, itemBuildBuilder.build());
        return new MatchUp(enemy, matchUpNotes, build);
    }
}
