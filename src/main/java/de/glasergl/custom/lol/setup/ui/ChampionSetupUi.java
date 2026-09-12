package de.glasergl.custom.lol.setup.ui;

import de.glasergl.custom.lol.setup.file.Images;
import de.glasergl.custom.lol.setup.file.SetupFileIo;
import de.glasergl.custom.lol.setup.model.builder.SetupBuilder;
import de.glasergl.custom.lol.setup.model.entity.MatchUp;
import de.glasergl.custom.lol.setup.ui.builder.RunePageBuilderUi;
import de.glasergl.custom.lol.setup.ui.selection.ItemSelectionUi;
import de.glasergl.custom.lol.setup.ui.selection.SpellMaxOrderUi;
import de.glasergl.custom.lol.setup.ui.selection.StartSpellSelectionUi;
import de.glasergl.custom.lol.setup.ui.selection.SummonerSpellSelection;
import lombok.Getter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;

public final class ChampionSetupUi {
    private final RunePageBuilderUi runePageBuilderUi;
    private final @Getter JPanel ui = new JPanel(new BorderLayout());
    private final JTextArea championNotesTextArea = new JTextArea(3, 25);
    private final JButton storeButton = CustomSwingComponents.createButton("Store");
    private final SetupBuilder setupBuilder;
    private final SetupFileIo setupFileIo;
    private final JTextArea matchUpNotesTextArea = new JTextArea(4, 35);

    public ChampionSetupUi(final Images images, final SetupBuilder setupBuilder, final SetupFileIo setupFileIo) {
        this.setupBuilder = setupBuilder;
        this.setupFileIo = setupFileIo;
        this.runePageBuilderUi = new RunePageBuilderUi(setupBuilder.getRunePageBuilder(), images);

        matchUpNotesTextArea.setText(setupBuilder.getChampionNotes());

        championNotesTextArea.setText(setupBuilder.getMatchUpNotes());
        championNotesTextArea.setBorder(new EmptyBorder(2, 2, 2, 2));
        final JScrollPane scrollableNotes = CustomSwingComponents.createScrollPane(championNotesTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableNotes.setBorder(new TitledBorder("Match-Up Notes"));

        storeButton.setFocusPainted(false);
        storeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        storeButton.addActionListener(click -> storeCurrentSetupState());
        final JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonWrapper.add(storeButton);
        final JButton popOutButton = CustomSwingComponents.createButton("Pop Out");
        popOutButton.addActionListener(click -> {
            final MatchUp currentMatchUp = setupBuilder.build();
            new ChampionSetupPopOutUi((JFrame) SwingUtilities.windowForComponent(ui), images, currentMatchUp, setupBuilder.getChampionNotes());
        });
        buttonWrapper.add(popOutButton);
        ui.add(buttonWrapper, BorderLayout.NORTH);

        final JPanel vsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        vsPanel.add(new JLabel(new ImageIcon(images.getBigChampionImages().get(setupBuilder.getMe()))));
        vsPanel.add(new JLabel(new ImageIcon(images.getVsIcon())));
        vsPanel.add(new JLabel(new ImageIcon(images.getBigChampionImages().get(setupBuilder.getEnemy()))));

        final JPanel summonerSpellRunePageStartSpellSpellMaxOrderAndNotesPanel = new JPanel();
        summonerSpellRunePageStartSpellSpellMaxOrderAndNotesPanel.setLayout(new BoxLayout(summonerSpellRunePageStartSpellSpellMaxOrderAndNotesPanel, BoxLayout.Y_AXIS));
        summonerSpellRunePageStartSpellSpellMaxOrderAndNotesPanel.add(vsPanel);
        final JScrollPane scrollableChampionSpecificNotesTextArea = CustomSwingComponents.createScrollPane(matchUpNotesTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableChampionSpecificNotesTextArea.setBorder(new TitledBorder("Champion Notes"));
        summonerSpellRunePageStartSpellSpellMaxOrderAndNotesPanel.add(scrollableChampionSpecificNotesTextArea);
        summonerSpellRunePageStartSpellSpellMaxOrderAndNotesPanel.add(new SummonerSpellSelection(setupBuilder, images).getUi());
        summonerSpellRunePageStartSpellSpellMaxOrderAndNotesPanel.add(runePageBuilderUi.getUi());
        summonerSpellRunePageStartSpellSpellMaxOrderAndNotesPanel.add(new StartSpellSelectionUi(setupBuilder).getUi());
        summonerSpellRunePageStartSpellSpellMaxOrderAndNotesPanel.add(new SpellMaxOrderUi(setupBuilder).getUi());
        summonerSpellRunePageStartSpellSpellMaxOrderAndNotesPanel.add(scrollableNotes);
        ui.add(summonerSpellRunePageStartSpellSpellMaxOrderAndNotesPanel, BorderLayout.CENTER);

        final JPanel itemUi = new JPanel(new BorderLayout());
        itemUi.add(new ItemSelectionUi(images, item -> {
            setupBuilder.getItemBuildBuilder().addItem(item);
        }).getUi(), BorderLayout.CENTER);
        itemUi.add(setupBuilder.getItemBuildBuilder().getUi(), BorderLayout.SOUTH);
        itemUi.setBorder(new TitledBorder("Item Set"));
        ui.add(itemUi, BorderLayout.EAST);
    }

    private void storeCurrentSetupState() {
        setupBuilder.setChampionNotes(championNotesTextArea.getText());
        setupBuilder.setMatchUpNotes(matchUpNotesTextArea.getText());
        try {
            final MatchUp matchUp = setupBuilder.build();
            setupFileIo.store(setupBuilder.getMe(), setupBuilder.getRole(), setupBuilder.getChampionNotes(), matchUp);
        } catch (final RuntimeException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(ui), String.format("%s: %s", e.getClass().getSimpleName(), e.getMessage()), "Store Failed with Exception", JOptionPane.ERROR_MESSAGE);
        }
    }
}
