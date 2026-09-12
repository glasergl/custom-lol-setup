package de.glasergl.custom.lol.setup.ui.selection;

import de.glasergl.custom.lol.setup.file.Images;
import de.glasergl.custom.lol.setup.model.entity.SummonerSpell;
import de.glasergl.custom.lol.setup.ui.helper.EmptyMouseListener;
import lombok.Getter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Optional;

public final class SummonerSpellSelectionDialog extends JDialog {
    private @Getter Optional<SummonerSpell> selectedSummonerSpell = Optional.empty();

    public SummonerSpellSelectionDialog(final Images images, final Frame parent, final JComponent relative) {
        super(parent, "Summoner Spell", true);
        setLayout(new BorderLayout());
        final JPanel content = new JPanel(new GridLayout(3, 3, 8, 8));
        for (final SummonerSpell summonerSpell : SummonerSpell.values()) {
            final JLabel summonerSpellIcon = new JLabel(new ImageIcon(images.getSummonerSpellImage(summonerSpell)));
            summonerSpellIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
            content.add(summonerSpellIcon);
            summonerSpellIcon.addMouseListener(new EmptyMouseListener() {
                @Override
                public void mouseClicked(final MouseEvent click) {
                    selectedSummonerSpell = Optional.of(summonerSpell);
                    dispose();
                }
            });
        }
        content.setBorder(new EmptyBorder(5, 5, 5, 5));
        add(content, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(relative);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
