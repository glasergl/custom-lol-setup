package de.glasergl.custom.lol.setup.ui.selection;

import de.glasergl.custom.lol.setup.model.builder.SetupBuilder;
import de.glasergl.custom.lol.setup.model.entity.Spell;
import de.glasergl.custom.lol.setup.ui.EmptyMouseListener;
import lombok.Getter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.List;

public class SpellMaxOrderUi {
    private final @Getter JPanel ui = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

    public SpellMaxOrderUi(final SetupBuilder setupBuilder) {
        ui.setBorder(new TitledBorder("Spell Max Order"));
        updateSpellLabels(setupBuilder);
    }

    private void updateSpellLabels(final SetupBuilder setupBuilder) {
        ui.removeAll();
        for (int i = 0; i < setupBuilder.getSpellMaxOrder().size(); i++) {
            final Spell currentSpell = setupBuilder.getSpellMaxOrder().get(i);
            final JLabel spellLabel = new JLabel(currentSpell.toString());
            spellLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            spellLabel.setFont(spellLabel.getFont().deriveFont(40.0f));
            spellLabel.addMouseListener(new EmptyMouseListener() {
                @Override
                public void mouseClicked(final MouseEvent click) {
                    if (click.getButton() == MouseEvent.BUTTON1) {
                        swapLeft(setupBuilder, currentSpell);
                    } else if (click.getButton() == MouseEvent.BUTTON3) {
                        swapRight(setupBuilder, currentSpell);
                    }
                }
            });
            ui.add(spellLabel);
            if (i < setupBuilder.getSpellMaxOrder().size() - 1) {
                final JLabel lessThanLabel = new JLabel(">");
                lessThanLabel.setFont(spellLabel.getFont().deriveFont(40.0f));
                ui.add(lessThanLabel);
            }
        }
        ui.revalidate();
        ui.repaint();
    }

    private void swapLeft(final SetupBuilder setupBuilder, final Spell spell) {
        final List<Spell> spells = setupBuilder.getSpellMaxOrder();
        final int i = spells.indexOf(spell);
        if (i > 0) {
            Collections.swap(spells, i, i - 1);
            updateSpellLabels(setupBuilder);
        }
    }

    private void swapRight(final SetupBuilder setupBuilder, final Spell spell) {
        final List<Spell> spells = setupBuilder.getSpellMaxOrder();
        final int i = spells.indexOf(spell);
        if (i < spells.size() - 1) {
            Collections.swap(spells, i, i + 1);
            updateSpellLabels(setupBuilder);
        }
    }
}
