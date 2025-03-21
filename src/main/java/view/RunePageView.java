package view;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import model.RunePage;

/**
 * Creates a JPanel of a single rune page which updates itself based user clicks
 * on runes. An instance of this corresponds 1 to 1 to a rune page. After
 * instantiation, the rune paths cannot be changed anymore (but the selections,
 * of course).
 */
public final class RunePageView {
	private final RunePage runePage;
	private final JPanel view = new JPanel();

	public RunePageView(final RunePage runePage) {
		this.runePage = runePage;
		final JPanel mainRunePath = new JPanel();
		mainRunePath.setLayout(new BoxLayout(mainRunePath, BoxLayout.Y_AXIS));
		mainRunePath.add(new SelectableRunesView(runePage.getKeyStones(), 65).getView());
		mainRunePath.add(new SelectableRunesView(runePage.getSlotRunes(), 35).getView());
		view.setLayout(new BoxLayout(view, BoxLayout.X_AXIS));
		view.add(mainRunePath);
		final JPanel secondRunePathAndShards = new JPanel();
		secondRunePathAndShards.setLayout(new BoxLayout(secondRunePathAndShards, BoxLayout.Y_AXIS));
		secondRunePathAndShards.add(new SelectableRunesView(runePage.getSecondPathSlotRunes(), 35).getView());
		secondRunePathAndShards.add(new SelectableRunesView(runePage.getShards(), 25).getView());
		view.add(secondRunePathAndShards);
	}

	public RunePage getRunePage() {
		return runePage;
	}

	public JPanel getView() {
		return view;
	}
}
