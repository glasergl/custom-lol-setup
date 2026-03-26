package de.glasergl.custom.lol.setup.io.ui.selection;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import de.glasergl.custom.lol.setup.io.Images;
import de.glasergl.custom.lol.setup.io.ui.misc.EmptyMouseListener;
import de.glasergl.custom.lol.setup.model.builder.SetupBuilder;
import de.glasergl.custom.lol.setup.model.entity.SummonerSpell;
import lombok.Getter;

public final class SummonerSpellSelection {
    private final @Getter JPanel ui = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private final JLabel firstSummonerSpellIcon = new JLabel();
    private final JLabel secondSummonerSpellIcon = new JLabel();

    public SummonerSpellSelection(final SetupBuilder setupBuilder, final Images images) {
	for (final JLabel label : List.of(firstSummonerSpellIcon, secondSummonerSpellIcon)) {
	    label.setOpaque(true);
	    label.setBackground(Color.CYAN);
	    label.setPreferredSize(new Dimension(images.getSummonerSpellIconSize(), images.getSummonerSpellIconSize()));
	    label.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    ui.add(label);
	}

	firstSummonerSpellIcon.addMouseListener(new EmptyMouseListener() {
	    @Override
	    public void mouseClicked(final MouseEvent click) {
		final SummonerSpellSelectionDialog summonerSpellSelectionDialog = new SummonerSpellSelectionDialog(images, (JFrame) SwingUtilities.getWindowAncestor(ui), firstSummonerSpellIcon);
		final Optional<SummonerSpell> selectedSummonerSpell = summonerSpellSelectionDialog.getSelectedSummonerSpell();
		selectedSummonerSpell.ifPresent(summonerSpell -> {
		    final Optional<SummonerSpell> currentFirstSummonerSpell = setupBuilder.getFirstSummonerSpell();
		    final Optional<SummonerSpell> currentSecondSummonerSpell = setupBuilder.getSecondSummonerSpell();
		    if (currentSecondSummonerSpell.isPresent() && currentSecondSummonerSpell.get().equals(selectedSummonerSpell.get())) {
			setupBuilder.setSecondSummonerSpell(currentFirstSummonerSpell);
			if (currentFirstSummonerSpell.isPresent()) {
			    secondSummonerSpellIcon.setIcon(new ImageIcon(images.getSummonerSpellImage(currentFirstSummonerSpell.get())));
			} else {
			    secondSummonerSpellIcon.setIcon(null);
			}
		    }
		    firstSummonerSpellIcon.setIcon(new ImageIcon(images.getSummonerSpellImage(summonerSpell)));
		    setupBuilder.setFirstSummonerSpell(Optional.of(summonerSpell));
		});
	    }
	});

	secondSummonerSpellIcon.addMouseListener(new EmptyMouseListener() {
	    @Override
	    public void mouseClicked(final MouseEvent click) {
		final SummonerSpellSelectionDialog summonerSpellSelectionDialog = new SummonerSpellSelectionDialog(images, (JFrame) SwingUtilities.getWindowAncestor(ui), secondSummonerSpellIcon);
		final Optional<SummonerSpell> selectedSummonerSpell = summonerSpellSelectionDialog.getSelectedSummonerSpell();
		selectedSummonerSpell.ifPresent(summonerSpell -> {
		    final Optional<SummonerSpell> currentFirstSummonerSpell = setupBuilder.getFirstSummonerSpell();
		    final Optional<SummonerSpell> currentSecondSummonerSpell = setupBuilder.getSecondSummonerSpell();
		    if (currentFirstSummonerSpell.isPresent() && currentFirstSummonerSpell.get().equals(selectedSummonerSpell.get())) {
			setupBuilder.setFirstSummonerSpell(currentSecondSummonerSpell);
			if (currentSecondSummonerSpell.isPresent()) {
			    firstSummonerSpellIcon.setIcon(new ImageIcon(images.getSummonerSpellImage(currentSecondSummonerSpell.get())));
			} else {
			    firstSummonerSpellIcon.setIcon(null);
			}
		    }
		    secondSummonerSpellIcon.setIcon(new ImageIcon(images.getSummonerSpellImage(summonerSpell)));
		    setupBuilder.setSecondSummonerSpell(Optional.of(summonerSpell));
		});
	    }
	});

	setupBuilder.getFirstSummonerSpell().ifPresent(summonerSpell -> {
	    firstSummonerSpellIcon.setIcon(new ImageIcon(images.getSummonerSpellImage(summonerSpell)));
	    setupBuilder.setFirstSummonerSpell(Optional.of(summonerSpell));
	});

	setupBuilder.getSecondSummonerSpell().ifPresent(summonerSpell -> {
	    secondSummonerSpellIcon.setIcon(new ImageIcon(images.getSummonerSpellImage(summonerSpell)));
	    setupBuilder.setSecondSummonerSpell(Optional.of(summonerSpell));
	});
    }
}
