package de.glasergl.custom.lol.setup.io.ui;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Optional;

import javax.swing.JPanel;

import de.glasergl.custom.lol.setup.io.Images;
import de.glasergl.custom.lol.setup.io.SetupFileIo;
import de.glasergl.custom.lol.setup.model.builder.ItemBuildBuilder;
import de.glasergl.custom.lol.setup.model.builder.RunePageBuilder;
import de.glasergl.custom.lol.setup.model.builder.SetupBuilder;
import de.glasergl.custom.lol.setup.model.entity.Champion;
import de.glasergl.custom.lol.setup.model.entity.Role;
import de.glasergl.custom.lol.setup.model.entity.RunePath;
import de.glasergl.custom.lol.setup.model.entity.Setup;
import lombok.Getter;

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
	ui.setPreferredSize(new SetupUi(images, createEmptySetupBuilderToGetUiSize(), setupFileIo).getUi().getPreferredSize());
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
	    final Optional<Setup> knownSetup = getSetupFromKnownSetups();
	    final SetupUi setupUi;
	    if (knownSetup.isPresent()) {
		setupUi = new SetupUi(images, createSetupBuilderFromKnownSetup(knownSetup.get()), setupFileIo);
	    } else {
		setupUi = new SetupUi(images, createEmptySetupBuilder(), setupFileIo);
	    }
	    ui.removeAll();
	    ui.add(setupUi.getUi(), BorderLayout.SOUTH);
	    ui.revalidate();
	    ui.repaint();
	}
    }

    private Optional<Setup> getSetupFromKnownSetups() {
	assert meSelection.isPresent() && enemySelection.isPresent();
	final List<Setup> knownSetups;
	if (roleSelection.isPresent()) {
	    knownSetups = setupFileIo.getSetups().stream().filter(setup -> {
		return setup.me().equals(meSelection.get()) && setup.enemy().equals(enemySelection.get()) && setup.role().equals(roleSelection.get());
	    }).toList();
	} else {
	    knownSetups = setupFileIo.getSetups().stream().filter(setup -> {
		return setup.me().equals(meSelection.get()) && setup.enemy().equals(enemySelection.get());
	    }).toList();
	}
	return knownSetups.isEmpty() ? Optional.empty() : Optional.of(knownSetups.get(0));
    }

    private SetupBuilder createSetupBuilderFromKnownSetup(final Setup knownSetup) {
	assert meSelection.isPresent() && enemySelection.isPresent();

	final RunePageBuilder runePageBuilder = new RunePageBuilder(knownSetup.runePage());
	final ItemBuildBuilder itemBuildBuilder = new ItemBuildBuilder();
	return new SetupBuilder(meSelection.get(), roleSelection, enemySelection.get(), runePageBuilder, itemBuildBuilder);
    }

    private SetupBuilder createEmptySetupBuilder() {
	assert meSelection.isPresent() && enemySelection.isPresent();

	final RunePageBuilder runePageBuilder = new RunePageBuilder(RunePath.PRECISION, RunePath.RESOLVE);
	final ItemBuildBuilder itemBuildBuilder = new ItemBuildBuilder();
	return new SetupBuilder(meSelection.get(), roleSelection, enemySelection.get(), runePageBuilder, itemBuildBuilder);
    }

    private SetupBuilder createEmptySetupBuilderToGetUiSize() {
	final RunePageBuilder runePageBuilder = new RunePageBuilder(RunePath.PRECISION, RunePath.RESOLVE);
	final ItemBuildBuilder itemBuildBuilder = new ItemBuildBuilder();
	return new SetupBuilder(Champion.GWEN, Optional.empty(), Champion.AATROX, runePageBuilder, itemBuildBuilder);
    }
}
