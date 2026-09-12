package de.glasergl.custom.lol.setup.ui;

import de.glasergl.custom.lol.setup.file.Images;
import de.glasergl.custom.lol.setup.file.SetupFileIo;
import de.glasergl.custom.lol.setup.model.builder.RunePageBuilder;
import de.glasergl.custom.lol.setup.model.builder.SetupBuilder;
import de.glasergl.custom.lol.setup.model.entity.*;
import de.glasergl.custom.lol.setup.ui.builder.ItemBuildBuilder;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class ChampionSetupSelectionHandler {
    private final SetupFileIo setupFileIo;
    private final Images images;
    private final @Getter JPanel ui = new JPanel(new BorderLayout());

    private Optional<Champion> meSelection = Optional.empty();
    private Optional<Champion> enemySelection = Optional.empty();
    private Optional<Role> roleSelection = Optional.empty();

    public ChampionSetupSelectionHandler(final SetupFileIo setupFileIo, final Images images) {
        this.setupFileIo = setupFileIo;
        this.images = images;
    }

    public void setMe(final Champion me) {
        meSelection = Optional.of(me);
        updateView();
    }

    public void setEnemy(final Champion enemy) {
        enemySelection = Optional.of(enemy);
        updateView();
    }

    public void setRole(final Role role) {
        roleSelection = Optional.of(role);
        updateView();
    }

    private void updateView() {
        if (meSelection.isPresent() && enemySelection.isPresent() && roleSelection.isPresent()) {
            final Optional<ChampionSetup> knownSetup = setupFileIo.getSetup(meSelection.get(), roleSelection.get());
            final Optional<MatchUp> knownMatchUp = setupFileIo.getMatchUp(knownSetup, enemySelection.get());
            final ChampionSetupUi championSetupUi;
            if (knownSetup.isPresent() && knownMatchUp.isPresent()) {
                championSetupUi = new ChampionSetupUi(images, createSetupBuilderFromKnownSetup(knownSetup.get(), knownMatchUp.get()), setupFileIo);
            } else {
                championSetupUi = new ChampionSetupUi(images, createEmptySetupBuilder(knownSetup), setupFileIo);
            }
            ui.removeAll();
            ui.add(championSetupUi.getUi(), BorderLayout.CENTER);
            SwingUtilities.windowForComponent(ui).pack();
            SwingUtilities.windowForComponent(ui).pack(); // need to call twice for proper layout
            SwingUtilities.windowForComponent(ui).setLocationRelativeTo(null);
        }
    }

    private SetupBuilder createSetupBuilderFromKnownSetup(final ChampionSetup setup, final MatchUp matchUp) {
        assert meSelection.isPresent() && roleSelection.isPresent() && enemySelection.isPresent();

        final Build build = matchUp.build();
        final RunePageBuilder runePageBuilder = new RunePageBuilder(build.runePage());
        final ItemBuildBuilder itemBuildBuilder = new ItemBuildBuilder(images, build.items());
        final Optional<SummonerSpell> firstSummonerSpell = build.firstSpell() != null ? Optional.of(build.firstSpell()) : Optional.empty();
        final Optional<SummonerSpell> secondSummonerSpell = build.secondSpell() != null ? Optional.of(build.secondSpell()) : Optional.empty();
        final Optional<Spell> startSpell = build.startSpell() != null ? Optional.of(build.startSpell()) : Optional.empty();
        return new SetupBuilder(meSelection.get(), roleSelection.get(), setup.notes(), enemySelection.get(), runePageBuilder, itemBuildBuilder, firstSummonerSpell, secondSummonerSpell, startSpell,
                build.spellMaxOrder() != null ? build.spellMaxOrder() : new ArrayList<>(List.of(Spell.R, Spell.Q, Spell.E, Spell.W)), matchUp.notes() != null ? matchUp.notes() : "");
    }

    private SetupBuilder createEmptySetupBuilder(final Optional<ChampionSetup> knownSetup) {
        assert meSelection.isPresent() && roleSelection.isPresent() && enemySelection.isPresent();

        final RunePageBuilder runePageBuilder = new RunePageBuilder(RunePath.PRECISION, RunePath.SORCERY);
        final ItemBuildBuilder itemBuildBuilder = new ItemBuildBuilder(images);
        return new SetupBuilder(meSelection.get(), roleSelection.get(), knownSetup.isPresent() ? knownSetup.get().notes() : "", enemySelection.get(), runePageBuilder, itemBuildBuilder, Optional.empty(), Optional.empty(), Optional.empty(), new ArrayList<>(List.of(Spell.R, Spell.Q, Spell.E, Spell.W)), "");
    }
}
