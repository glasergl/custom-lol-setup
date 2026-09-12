package de.glasergl.custom.lol.setup.ui;

import de.glasergl.custom.lol.setup.file.Images;
import de.glasergl.custom.lol.setup.model.entity.Build;
import de.glasergl.custom.lol.setup.model.entity.Item;
import de.glasergl.custom.lol.setup.model.entity.MatchUp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ChampionSetupPopOutUi {
    public ChampionSetupPopOutUi(final JFrame referenceFrame, final Images images, final MatchUp matchUpToVisualize, final String championNotes) {
        final Build build = matchUpToVisualize.build();
        final JLabel startSpellLabel = new JLabel("Start Spell: %s".formatted(build.startSpell() != null ? build.startSpell().toString() : ""));
        startSpellLabel.setFont(startSpellLabel.getFont().deriveFont(20.0f));
        final JLabel maxSpellOrderLabel = new JLabel(
                "Spell Max Order: %s > %s > %s > %s".formatted(build.spellMaxOrder().get(0).toString(), build.spellMaxOrder().get(1).toString(), build.spellMaxOrder().get(2).toString(), build.spellMaxOrder().get(3).toString()));
        maxSpellOrderLabel.setFont(maxSpellOrderLabel.getFont().deriveFont(20.0f));

        final JTextArea matchupNotesTextArea = new JTextArea(matchUpToVisualize.notes());
        matchupNotesTextArea.setLineWrap(true);
        matchupNotesTextArea.setEditable(false);
        matchupNotesTextArea.setWrapStyleWord(true);
        matchupNotesTextArea.setOpaque(false);
        matchupNotesTextArea.setFocusable(false);
        matchupNotesTextArea.setBorder(new TitledBorder("Match-Up Notes"));

        final JTextArea championNotesTextArea = new JTextArea(championNotes);
        championNotesTextArea.setLineWrap(true);
        championNotesTextArea.setEditable(false);
        championNotesTextArea.setWrapStyleWord(true);
        championNotesTextArea.setOpaque(false);
        championNotesTextArea.setFocusable(false);
        championNotesTextArea.setBorder(new TitledBorder("Champion Notes"));

        final JPanel itemBuildPanel = new JPanel();
        itemBuildPanel.setBorder(new TitledBorder("Item Build"));
        itemBuildPanel.setLayout(new BoxLayout(itemBuildPanel, BoxLayout.Y_AXIS));
        for (int i = 0; i < build.items().notes().size(); i++) {
            final JLabel note = new JLabel(build.items().notes().get(i));
            note.setAlignmentX(Component.LEFT_ALIGNMENT);
            final JPanel itemsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            for (final Item item : build.items().items().get(i)) {
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

        final JPanel maxSpellOrderWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        maxSpellOrderWrapper.add(maxSpellOrderLabel);
        ui.add(championNotesTextArea);
        ui.add(startSpellWrapper);
        ui.add(maxSpellOrderWrapper);
        ui.add(matchupNotesTextArea);

        final JDialog dialog = new JDialog(referenceFrame, "gl hf", true);
        dialog.setLayout(new BorderLayout());
        dialog.add(ui, BorderLayout.CENTER);
        dialog.add(itemBuildPanel, BorderLayout.EAST);
        dialog.pack();
        dialog.setLocationRelativeTo(referenceFrame);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}
