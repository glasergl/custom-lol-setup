package de.glasergl.custom.lol.setup.io.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;
import java.util.List;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.glasergl.custom.lol.setup.io.Images;
import de.glasergl.custom.lol.setup.model.entity.Champion;

public final class ChampionSelectionUi {
    private final JScrollPane ui;
    private final List<JPanel> championPanels = new ArrayList<>();

    public ChampionSelectionUi(final Consumer<Champion> selectionHandler, final Images images) {
	final JPanel ui = new JPanel();
	ui.setLayout(new BoxLayout(ui, BoxLayout.Y_AXIS));
	for (final Champion champion : Champion.values()) {
	    final JLabel championIcon = new JLabel(new ImageIcon(images.getImage(champion)));
	    final JPanel championPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    championPanel.add(championIcon);
	    championPanel.add(new JLabel(champion.toString()));
	    championPanel.addMouseListener(new EmptyMouseListener() {
		@Override
		public void mouseClicked(final MouseEvent click) {
		    for (final JPanel otherChampionPanel : championPanels) {
			otherChampionPanel.setBackground(null);
		    }
		    selectionHandler.accept(champion);
		    championPanel.setBackground(Color.CYAN);
		}
	    });
	    championPanels.add(championPanel);
	    ui.add(championPanel);
	}
	this.ui = new JScrollPane(ui, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	this.ui.setPreferredSize(new Dimension(180, 700));
    }

    public JScrollPane getUi() {
	return ui;
    }
}
