package de.glasergl.custom.lol.setup.ui.selection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.List;
import java.util.Locale;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;

import de.glasergl.custom.lol.setup.file.Images;
import de.glasergl.custom.lol.setup.model.entity.Champion;
import de.glasergl.custom.lol.setup.ui.DocumentChangeListener;
import de.glasergl.custom.lol.setup.ui.EmptyMouseListener;
import lombok.Getter;

public final class ChampionSelectionUi {
    private final @Getter JPanel ui = new JPanel(new BorderLayout());
    private final Consumer<Champion> selectionHandler;
    private final Images images;
    private final JPanel championPanelsUi = new JPanel();
    private final List<JPanel> championPanels = new ArrayList<>();

    public ChampionSelectionUi(final Consumer<Champion> selectionHandler, final Images images) {
	this.selectionHandler = selectionHandler;
	this.images = images;
	championPanelsUi.setLayout(new BoxLayout(championPanelsUi, BoxLayout.Y_AXIS));
	visualizeChampions(new ArrayList<>(List.of(Champion.values())));

	final JPanel championPanelsWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
	championPanelsWrapper.add(championPanelsUi);
	final JScrollPane scrollableChampionPane = new JScrollPane(championPanelsWrapper, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	scrollableChampionPane.setPreferredSize(new Dimension(170, 400));
	this.ui.add(scrollableChampionPane, BorderLayout.CENTER);

	final JTextField searchTextField = new JTextField();
	searchTextField.getDocument().addDocumentListener(new DocumentChangeListener() {
	    @Override
	    public void onChange(final DocumentEvent textChange) {
		final String championSearchQueryText = searchTextField.getText();
		final List<Champion> championsWithMatchingName = new ArrayList<>(Arrays.stream(Champion.values()).filter(getChampionNameFilter(championSearchQueryText)).toList());
		visualizeChampions(championsWithMatchingName);
	    }
	});
	this.ui.add(searchTextField, BorderLayout.NORTH);
    }

    private void visualizeChampions(final List<Champion> champions) {
	championPanels.clear();
	championPanelsUi.removeAll();
	champions.sort((c1, c2) -> {
	    return c1.toString().compareTo(c2.toString());
	});
	for (final Champion champion : champions) {
	    final JLabel championIcon = new JLabel(new ImageIcon(images.getImage(champion)));
	    final JPanel championPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    championPanel.setPreferredSize(new Dimension(170, 50));
	    championPanel.add(championIcon);
	    championPanel.add(new JLabel(champion.toString()));
	    championPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
	    championPanelsUi.add(championPanel);
	}
	championPanelsUi.revalidate();
	championPanelsUi.repaint();
    }

    private Predicate<Champion> getChampionNameFilter(final String championSearchQueryText) {
	return champion -> {
	    if (championSearchQueryText.isBlank()) {
		return true;
	    }
	    final String championName = champion.toString();
	    return championName.matches(buildChampionSearchQueryStringTextRegEx(championSearchQueryText));
	};
    }

    private String buildChampionSearchQueryStringTextRegEx(final String championSearchQueryText) {
	final StringBuilder regEx = new StringBuilder();
	for (char character : championSearchQueryText.toCharArray()) {
	    regEx.append(character);
	    regEx.append(".*");
	}
	return regEx.toString().toUpperCase(Locale.ROOT);
    }
}
