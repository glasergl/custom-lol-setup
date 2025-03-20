package view;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import model.Rune;
import model.RunePath;

public final class SecondRunePathView {
	private final RunePath runePath;
	private final JPanel view = new JPanel();

	public SecondRunePathView(final RunePath runePath) {
		this.runePath = runePath;
		view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));
		for (final List<Rune> slotRunes : runePath.getSlotRunes()) {
			view.add(new RuneRowView(slotRunes, 30).getView());
		}
	}

	public JPanel getView() {
		return view;
	}
}
