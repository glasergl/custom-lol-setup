package view;

import java.awt.FlowLayout;
import java.awt.Image;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Rune;
import model.RunePath;

public final class RunePathView {
	private final RunePath runePath;
	private final JPanel view = new JPanel();

	public RunePathView(final RunePath runePath) {
		this.runePath = runePath;
		view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));
		view.add(createRuneRow(runePath.getKeyStones(), 50));
		for (final List<Rune> slotRunes : runePath.getSlotRunes()) {
			view.add(createRuneRow(slotRunes, 30));
		}
	}

	private JPanel createRuneRow(final List<Rune> runes, final int runeIconSize) {
		final JPanel runeRowView = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		for (final Rune rune : runes) {
			final JPanel runeView = new JPanel();
			runeView.setLayout(new BoxLayout(runeView, BoxLayout.Y_AXIS));
			Image image = ImageReading.getImageFromName(rune.getName(), "png");
			image = image.getScaledInstance(runeIconSize, runeIconSize, Image.SCALE_SMOOTH);
			final JLabel runeIcon = new JLabel(new ImageIcon(image));
			runeIcon.setVerticalAlignment(SwingConstants.CENTER);
			runeView.add(runeIcon);
			runeRowView.add(runeView);
		}
		return runeRowView;
	}

	public JPanel getView() {
		return view;
	}
}
