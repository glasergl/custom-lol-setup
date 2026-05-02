package de.glasergl.custom.lol.setup.ui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import de.glasergl.custom.lol.setup.file.Images;
import de.glasergl.custom.lol.setup.file.SetupFileIo;
import de.glasergl.custom.lol.setup.model.builder.SetupBuilder;
import de.glasergl.custom.lol.setup.model.entity.Setup;
import de.glasergl.custom.lol.setup.ui.builder.RunePageBuilderUi;
import de.glasergl.custom.lol.setup.ui.selection.ItemSelectionUi;
import de.glasergl.custom.lol.setup.ui.selection.StartSpellSelectionUi;
import de.glasergl.custom.lol.setup.ui.selection.SummonerSpellSelection;
import lombok.Getter;

public final class SetupUi {
    private final RunePageBuilderUi runePageBuilderUi;
    private final @Getter JPanel ui = new JPanel(new BorderLayout());
    private final JTextArea notes = new JTextArea(3, 25);
    private final JButton storeButton = CustomSwingComponents.createButton("Store");
    private final SetupBuilder setupBuilder;
    private final SetupFileIo setupFileIo;
    private final JTextArea championSpecificNotesTextArea = new JTextArea(4, 35);

    public SetupUi(final Images images, final SetupBuilder setupBuilder, final SetupFileIo setupFileIo) {
	this.setupBuilder = setupBuilder;
	this.setupFileIo = setupFileIo;
	this.runePageBuilderUi = new RunePageBuilderUi(setupBuilder.getRunePageBuilder(), images);

	championSpecificNotesTextArea.setText(setupFileIo.getSetups().championSpecificNotes().get(setupBuilder.getMe()));

	notes.setText(setupBuilder.getNotes());
	notes.setBorder(new EmptyBorder(2, 2, 2, 2));
	final JScrollPane scrollableNotes = CustomSwingComponents.createScrollPane(notes, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	scrollableNotes.setBorder(new TitledBorder("Match-Up Notes"));

	storeButton.setFocusPainted(false);
	storeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
	storeButton.addActionListener(click -> storeCurrentSetupState());
	final JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
	buttonWrapper.add(storeButton);
	final JButton popOutButton = CustomSwingComponents.createButton("Pop Out");
	popOutButton.addActionListener(click -> {
	    final Setup currentSetup = setupBuilder.build();
	    new SetupPopOutUi((JFrame) SwingUtilities.windowForComponent(ui), images, currentSetup, setupFileIo.getSetups());
	});
	buttonWrapper.add(popOutButton);
	ui.add(buttonWrapper, BorderLayout.NORTH);

	final JPanel vsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	vsPanel.add(new JLabel(new ImageIcon(images.getBigChampionImages().get(setupBuilder.getMe()))));
	vsPanel.add(new JLabel(new ImageIcon(images.getVsIcon())));
	vsPanel.add(new JLabel(new ImageIcon(images.getBigChampionImages().get(setupBuilder.getEnemy()))));

	final JPanel summonerSpellRunePageStartSpellAndNotesPanel = new JPanel();
	summonerSpellRunePageStartSpellAndNotesPanel.setLayout(new BoxLayout(summonerSpellRunePageStartSpellAndNotesPanel, BoxLayout.Y_AXIS));
	summonerSpellRunePageStartSpellAndNotesPanel.add(vsPanel);
	final JScrollPane scrollableChampionSpecificNotesTextArea = CustomSwingComponents.createScrollPane(championSpecificNotesTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	scrollableChampionSpecificNotesTextArea.setBorder(new TitledBorder("Champion Notes"));
	summonerSpellRunePageStartSpellAndNotesPanel.add(scrollableChampionSpecificNotesTextArea);
	summonerSpellRunePageStartSpellAndNotesPanel.add(new SummonerSpellSelection(setupBuilder, images).getUi());
	summonerSpellRunePageStartSpellAndNotesPanel.add(runePageBuilderUi.getUi());
	summonerSpellRunePageStartSpellAndNotesPanel.add(new StartSpellSelectionUi(setupBuilder).getUi());
	summonerSpellRunePageStartSpellAndNotesPanel.add(scrollableNotes);
	ui.add(summonerSpellRunePageStartSpellAndNotesPanel, BorderLayout.CENTER);

	final JPanel itemUi = new JPanel(new BorderLayout());
	itemUi.add(new ItemSelectionUi(images, item -> {
	    setupBuilder.getItemBuildBuilder().addItem(item);
	}).getUi(), BorderLayout.CENTER);
	itemUi.add(setupBuilder.getItemBuildBuilder().getUi(), BorderLayout.SOUTH);
	itemUi.setBorder(new TitledBorder("Item Set"));
	ui.add(itemUi, BorderLayout.EAST);
    }

    private void storeCurrentSetupState() {
	setupBuilder.setNotes(notes.getText());
	try {
	    setupFileIo.getSetups().championSpecificNotes().put(setupBuilder.getMe(), championSpecificNotesTextArea.getText());
	    setupFileIo.store(setupBuilder.build());
	} catch (final RuntimeException | IOException e) {
	    e.printStackTrace();
	    JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(ui), String.format("%s: %s", e.getClass().getSimpleName(), e.getMessage()), "Store Failed with Exception", JOptionPane.ERROR_MESSAGE);
	}
    }
}
