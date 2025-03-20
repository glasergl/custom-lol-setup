package view;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import model.RuneRow;

public class SlotRunesView {
	private final JPanel view = new JPanel();

	public SlotRunesView(final List<RuneRow> slotRuneRows, final boolean isSecondPath) {
		view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));
		for(final RuneRow slotRuneRow : slotRuneRow) {
			
		}
	}
}
