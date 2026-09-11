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

public final class SetupSelectionHandler {
    private final SetupFileIo setupFileIo;
    private final Images images;
    private final @Getter JPanel ui = new JPanel(new BorderLayout());

    private Optional<Champion> meSelection = Optional.empty();
    private Optional<Champion> enemySelection = Optional.empty();
    private Optional<Role> roleSelection = Optional.empty();

    public SetupSelectionHandler(final SetupFileIo setupFileIo, final Images images) {
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

    public void unsetRole() {
        roleSelection = Optional.empty();
        updateView();
    }

    private void updateView() {
        if (meSelection.isPresent() && enemySelection.isPresent()) {
            final Optional<MatchUp> knownSetup = getSetupFromKnownSetups();
            final SetupUi setupUi;
            if (knownSetup.isPresent()) {
                setupUi = new SetupUi(images, createSetupBuilderFromKnownSetup(knownSetup.get()), setupFileIo);
            } else {
                setupUi = new SetupUi(images, createEmptySetupBuilder(), setupFileIo);
            }
            ui.removeAll();
            ui.add(setupUi.getUi(), BorderLayout.CENTER);
            SwingUtilities.windowForComponent(ui).pack();
            SwingUtilities.windowForComponent(ui).pack(); // need to call twice for proper layout
            SwingUtilities.windowForComponent(ui).setLocationRelativeTo(null);
        }
    }

    private Optional<MatchUp> getSetupFromKnownSetups() {
        assert meSelection.isPresent() && enemySelection.isPresent();
        final List<MatchUp> knownMatchUps;
        if (roleSelection.isPresent()) {
            knownMatchUps = setupFileIo.getSetups().matchUps().stream().filter(setup -> {
                return setup.me().equals(meSelection.get()) && setup.enemy().equals(enemySelection.get()) && setup.role() != null && setup.role().equals(roleSelection.get());
            }).toList();
        } else {
            knownMatchUps = setupFileIo.getSetups().matchUps().stream().filter(setup -> {
                return setup.me().equals(meSelection.get()) && setup.enemy().equals(enemySelection.get());
            }).toList();
        }
        return knownMatchUps.isEmpty() ? Optional.empty() : Optional.of(knownMatchUps.get(0));
    }

    private SetupBuilder createSetupBuilderFromKnownSetup(final MatchUp knownMatchUp) {
        assert meSelection.isPresent() && enemySelection.isPresent();

        final RunePageBuilder runePageBuilder = new RunePageBuilder(knownMatchUp.runePage());
        final ItemBuildBuilder itemBuildBuilder = new ItemBuildBuilder(images, knownMatchUp.build());
        final Optional<SummonerSpell> firstSummonerSpell = knownMatchUp.first() != null ? Optional.of(knownMatchUp.first()) : Optional.empty();
        final Optional<SummonerSpell> secondSummonerSpell = knownMatchUp.second() != null ? Optional.of(knownMatchUp.second()) : Optional.empty();
        final Optional<Spell> startSpell = knownMatchUp.startSpell() != null ? Optional.of(knownMatchUp.startSpell()) : Optional.empty();
        return new SetupBuilder(meSelection.get(), roleSelection, enemySelection.get(), runePageBuilder, itemBuildBuilder, firstSummonerSpell, secondSummonerSpell, startSpell,
                knownMatchUp.spellMaxOrder() != null ? knownMatchUp.spellMaxOrder() : new ArrayList<>(List.of(Spell.R, Spell.Q, Spell.E, Spell.W)), knownMatchUp.notes() != null ? knownMatchUp.notes() : "");
    }

    private SetupBuilder createEmptySetupBuilder() {
        assert meSelection.isPresent() && enemySelection.isPresent();

        final RunePageBuilder runePageBuilder = new RunePageBuilder(RunePath.PRECISION, RunePath.SORCERY);
        final ItemBuildBuilder itemBuildBuilder = new ItemBuildBuilder(images);
        return new SetupBuilder(meSelection.get(), roleSelection, enemySelection.get(), runePageBuilder, itemBuildBuilder, Optional.empty(), Optional.empty(), Optional.empty(), new ArrayList<>(List.of(Spell.R, Spell.Q, Spell.E, Spell.W)), "");
    }
}
