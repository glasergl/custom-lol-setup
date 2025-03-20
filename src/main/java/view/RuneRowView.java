package view;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import model.Rune;

public final class RuneRowView {
	private final JPanel runeRowView = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
	private final List<Rune> runes;
	private final List<RuneView> runeViews;

	public RuneRowView(final List<Rune> runes, final int runeIconSize) {
		this.runes = runes;
		this.runeViews = new ArrayList<>(runes.size());
		for (final Rune rune : runes) {
			final RuneView runeView = new RuneView(rune, runeIconSize);
			runeViews.add(runeView);
			runeRowView.add(runeView.getView());
		}
		addExcludingRuneViews();
	}

	private void addExcludingRuneViews() {
		for (int currentRuneIndex = 0; currentRuneIndex < runes.size(); currentRuneIndex++) {
			final RuneView currentRuneView = runeViews.get(currentRuneIndex);
			for (int indexOfRuneToExclude = 0; indexOfRuneToExclude < runes.size(); indexOfRuneToExclude++) {
				if (currentRuneIndex != indexOfRuneToExclude) {
					final RuneView excludingRuneView = runeViews.get(indexOfRuneToExclude);
					currentRuneView.addExcludingRuneView(excludingRuneView);
				}

			}
		}
	}

	public JPanel getView() {
		return runeRowView;
	}
}
