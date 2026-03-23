package de.glasergl.custom.lol.setup.io.ui;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.swing.JPanel;

import de.glasergl.custom.lol.setup.io.Images;
import de.glasergl.custom.lol.setup.model.entity.Champion;
import de.glasergl.custom.lol.setup.model.entity.Role;
import de.glasergl.custom.lol.setup.model.entity.Setup;
import lombok.Getter;

public final class SetupSelectionHandler {
    private final Set<Setup> setups;
    private final Images images;
    private final @Getter JPanel ui = new JPanel(new BorderLayout());

    private Optional<Champion> meSelection = Optional.empty();
    private Optional<Champion> enemySelection = Optional.empty();
    private Optional<Role> roleSelection = Optional.empty();

    public SetupSelectionHandler(final Set<Setup> initialSetups, final Images images) {
	this.setups = initialSetups;
	this.images = images;
	ui.setPreferredSize(new SetupUi(images).getUi().getPreferredSize());
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
	    final Optional<Setup> knownSetup = getSetup();
	    final SetupUi setupUi;
	    if (knownSetup.isPresent()) {
		setupUi = new SetupUi(knownSetup.get(), images);
	    } else {
		setupUi = new SetupUi(images);
	    }
	    ui.removeAll();
	    ui.add(setupUi.getUi(), BorderLayout.SOUTH);
	    ui.revalidate();
	    ui.repaint();
	}
    }

    private Optional<Setup> getSetup() {
	assert meSelection.isPresent() && enemySelection.isPresent();
	final List<Setup> knownSetups;
	if (roleSelection.isPresent()) {
	    knownSetups = setups.stream().filter(setup -> {
		return setup.me().equals(meSelection.get()) && setup.enemy().equals(enemySelection.get()) && setup.role().equals(roleSelection.get());
	    }).toList();
	} else {
	    knownSetups = setups.stream().filter(setup -> {
		return setup.me().equals(meSelection.get()) && setup.enemy().equals(enemySelection.get());
	    }).toList();
	}
	return knownSetups.isEmpty() ? Optional.empty() : Optional.of(knownSetups.get(0));
    }
}
