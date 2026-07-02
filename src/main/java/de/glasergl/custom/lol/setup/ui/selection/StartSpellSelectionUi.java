package de.glasergl.custom.lol.setup.ui.selection;

import de.glasergl.custom.lol.setup.model.builder.SetupBuilder;
import de.glasergl.custom.lol.setup.model.entity.Spell;
import de.glasergl.custom.lol.setup.ui.EmptyMouseListener;
import lombok.Getter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Optional;

public final class StartSpellSelectionUi {
    private final @Getter JPanel ui = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 5));

    public StartSpellSelectionUi(final SetupBuilder setupBuilder) {
        ui.setBorder(new TitledBorder("Start Spell"));
        for (final Spell startSpell : List.of(Spell.Q, Spell.W, Spell.E, Spell.R)) {
            final JLabel spellLabel = new JLabel(startSpell.toString());
            spellLabel.setBorder(new EmptyBorder(0, 5, 0, 5));
            spellLabel.setFont(spellLabel.getFont().deriveFont(40.0f));
            spellLabel.setOpaque(true);
            spellLabel.addMouseListener(new EmptyMouseListener() {
                @Override
                public void mouseClicked(final MouseEvent click) {
                    if (spellLabel.getBackground() != Color.CYAN) {
                        for (final Component component : ui.getComponents()) {
                            component.setBackground(null);
                        }
                        spellLabel.setBackground(Color.CYAN);
                        setupBuilder.setStartSpell(Optional.of(startSpell));
                    } else {
                        spellLabel.setBackground(null);
                        setupBuilder.setStartSpell(Optional.empty());
                    }
                }
            });
            spellLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            ui.add(spellLabel);
            if (setupBuilder.getStartSpell().isPresent() && setupBuilder.getStartSpell().get().equals(startSpell)) {
                spellLabel.setBackground(Color.CYAN);
            }
        }
    }
}
