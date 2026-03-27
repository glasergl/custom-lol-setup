package de.glasergl.custom.lol.setup.ui;

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
    private final @Getter JPanel ui = new JPanel();
    private final JTextArea notes = new JTextArea(10, 30);
    private final JButton storeButton = new JButton("Store");

    public SetupUi(final Images images, final SetupBuilder setupBuilder, final SetupFileIo setupFileIo) {
	this.runePageBuilderView = new RunePageBuilderView(setupBuilder.getRunePageBuilder(), images);
	ui.setLayout(new BoxLayout(ui, BoxLayout.Y_AXIS));
	final JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
	buttonWrapper.add(storeButton);
	storeButton.setFocusPainted(false);
	storeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
	storeButton.addActionListener(click -> {
	    try {
		setupFileIo.store(setupBuilder.build());
	    } catch (final RuntimeException | IOException e) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(ui), String.format("%s: %s", e.getClass().getSimpleName(), e.getMessage()), "Store Failed with Exception", JOptionPane.ERROR_MESSAGE);
	    }
	});
	ui.add(buttonWrapper);
	ui.add(new SummonerSpellSelection(setupBuilder, images).getUi());
	ui.add(runePageBuilderView.getView());
	final JScrollPane scrollableNotes = new JScrollPane(notes, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	scrollableNotes.setBorder(new TitledBorder("Notes"));
	ui.add(scrollableNotes);
	ui.add(new ItemSelectionUi(images, s -> {
	}).getUi());
    }
}
