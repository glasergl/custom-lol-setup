package de.glasergl.custom.lol.setup.ui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
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
import de.glasergl.custom.lol.setup.ui.builder.RunePageBuilderView;
import de.glasergl.custom.lol.setup.ui.selection.ItemSelectionUi;
import de.glasergl.custom.lol.setup.ui.selection.SummonerSpellSelection;
import lombok.Getter;

public class SetupUi {
    private final RunePageBuilderView runePageBuilderView;
    private final @Getter JPanel ui = new JPanel(new BorderLayout());
    private final JTextArea notes = new JTextArea(10, 30);
    private final JButton storeButton = new JButton("Store");
    private final SetupBuilder setupBuilder;
    private final SetupFileIo setupFileIo;

    public SetupUi(final Images images, final SetupBuilder setupBuilder, final SetupFileIo setupFileIo) {
	this.setupBuilder = setupBuilder;
	this.setupFileIo = setupFileIo;
	this.runePageBuilderView = new RunePageBuilderView(setupBuilder.getRunePageBuilder(), images);
	notes.setText(setupBuilder.getNotes());
	notes.setBorder(new EmptyBorder(2, 2, 2, 2));
	final JPanel summonerSpellRunePageAndNotesPanel = new JPanel();
	summonerSpellRunePageAndNotesPanel.setLayout(new BoxLayout(summonerSpellRunePageAndNotesPanel, BoxLayout.Y_AXIS));
	final JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
	buttonWrapper.add(storeButton);
	storeButton.setFocusPainted(false);
	storeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
	storeButton.addActionListener(click -> storeCurrentSetupState());
	ui.add(buttonWrapper, BorderLayout.NORTH);
	summonerSpellRunePageAndNotesPanel.add(new SummonerSpellSelection(setupBuilder, images).getUi());
	summonerSpellRunePageAndNotesPanel.add(runePageBuilderView.getView());
	final JScrollPane scrollableNotes = new JScrollPane(notes, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	summonerSpellRunePageAndNotesPanel.add(scrollableNotes);
	final JPanel itemUi = new JPanel(new BorderLayout());
	itemUi.add(new ItemSelectionUi(images, s -> {
	}).getUi(), BorderLayout.EAST);
	itemUi.setBorder(new TitledBorder("Item Set"));
	ui.add(itemUi, BorderLayout.EAST);
	ui.add(summonerSpellRunePageAndNotesPanel, BorderLayout.CENTER);
    }

    private void storeCurrentSetupState() {
	setupBuilder.setNotes(notes.getText());
	try {
	    setupFileIo.store(setupBuilder.build());
	} catch (final RuntimeException | IOException e) {
	    e.printStackTrace();
	    JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(ui), String.format("%s: %s", e.getClass().getSimpleName(), e.getMessage()), "Store Failed with Exception", JOptionPane.ERROR_MESSAGE);
	}
    }
}
