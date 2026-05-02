package de.glasergl.custom.lol.setup.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

import de.glasergl.custom.lol.setup.file.Images;
import de.glasergl.custom.lol.setup.model.entity.Item;
import de.glasergl.custom.lol.setup.model.entity.Setup;
import de.glasergl.custom.lol.setup.model.entity.Setups;

public class SetupPopOutUi {
    public SetupPopOutUi(final JFrame referenceFrame, final Images images, final Setup setupToVisualize, final Setups allSetups) {
	final JLabel startSpellLabel = new JLabel("Start Spell: %s".formatted(setupToVisualize.startSpell() != null ? setupToVisualize.startSpell().toString() : ""));
	startSpellLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

	final JTextArea matchupNotesTextArea = new JTextArea(setupToVisualize.notes());
	matchupNotesTextArea.setLineWrap(true);
	matchupNotesTextArea.setEditable(false);
	matchupNotesTextArea.setWrapStyleWord(true);
	matchupNotesTextArea.setOpaque(false);
	matchupNotesTextArea.setFocusable(false);
	matchupNotesTextArea.setBorder(new TitledBorder("Match-Up Notes"));

	final JTextArea championNotesTextArea = new JTextArea(allSetups.championSpecificNotes().get(setupToVisualize.me()));
	championNotesTextArea.setLineWrap(true);
	championNotesTextArea.setEditable(false);
	championNotesTextArea.setWrapStyleWord(true);
	championNotesTextArea.setOpaque(false);
	championNotesTextArea.setFocusable(false);
	championNotesTextArea.setBorder(new TitledBorder("Champion Notes"));

	final JPanel itemBuildPanel = new JPanel();
	itemBuildPanel.setBorder(new TitledBorder("Item Build"));
	itemBuildPanel.setLayout(new BoxLayout(itemBuildPanel, BoxLayout.Y_AXIS));
	for (int i = 0; i < setupToVisualize.build().notes().size(); i++) {
	    final JLabel note = new JLabel(setupToVisualize.build().notes().get(i));
	    note.setAlignmentX(Component.LEFT_ALIGNMENT);
	    final JPanel itemsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    for (final Item item : setupToVisualize.build().items().get(i)) {
		itemsPanel.add(new JLabel(new ImageIcon(images.get(item))));
	    }
	    final JPanel noteWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    noteWrapper.add(note);
	    itemBuildPanel.add(noteWrapper);
	    itemBuildPanel.add(itemsPanel);
	}

	final JPanel ui = new JPanel();
	ui.setLayout(new BoxLayout(ui, BoxLayout.Y_AXIS));
	ui.setBorder(new EmptyBorder(3, 3, 3, 3));
	final JPanel startSpellWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
	startSpellWrapper.add(startSpellLabel);
	ui.add(startSpellWrapper);
	ui.add(championNotesTextArea);
	ui.add(matchupNotesTextArea);
	ui.add(itemBuildPanel);

	final JDialog dialog = new JDialog(referenceFrame, "gl hf", true);
	dialog.setLayout(new BorderLayout());
	dialog.add(ui, BorderLayout.CENTER);
	dialog.pack();
	dialog.setLocationRelativeTo(null);
	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	dialog.setVisible(true);
    }
}
