package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import model.RunePage;
import model.SelectableRune;

public final class RunePageView {
	private final RunePage runePage;
	private final JPanel view;
	private List<RuneView> runeViews;

	public RunePageView(final RunePage runePage) {
		this.runePage = runePage;
		this.view = new JPanel(new BorderLayout(0, 0));
		this.runeViews = new ArrayList<>();
		final JPanel mainRunePath = new JPanel(new BorderLayout(0, 0));
		mainRunePath.add(createKeyStoneView(), BorderLayout.NORTH);
		mainRunePath.add(createSlotRuneView(true), BorderLayout.CENTER);
		view.add(wrapInEmptyJPanel(mainRunePath), BorderLayout.CENTER);
		view.add(wrapInEmptyJPanel(createSlotRuneView(false)), BorderLayout.EAST);
		updateRuneViews();
	}

	private JPanel createKeyStoneView() {
		final JPanel keyStonesView = new JPanel();
		int keyStoneIndex = 0;
		for (final SelectableRune keyStone : runePage.getKeyStones()) {
			final RuneView runeView = new RuneView(keyStone, 60, new SelectKeyStone(keyStoneIndex));
			keyStonesView.add(runeView.getView());
			keyStoneIndex++;
			runeViews.add(runeView);
		}
		return keyStonesView;
	}

	private JPanel createSlotRuneView(final boolean firstPath) {
		final JPanel slotRuneView = new JPanel();
		slotRuneView.setLayout(new BoxLayout(slotRuneView, BoxLayout.Y_AXIS));
		final List<List<SelectableRune>> slotRunes = (firstPath ? runePage.getSlotRunes() : runePage.getSecondPath())
				.get();
		for (int rowIndex = 0; rowIndex < slotRunes.size(); rowIndex++) {
			final List<SelectableRune> row = slotRunes.get(rowIndex);
			final JPanel rowView = new JPanel();
			for (int columnIndex = 0; columnIndex < row.size(); columnIndex++) {
				final RuneView runeView = new RuneView(row.get(columnIndex), 35,
						new SelectSlotRune(rowIndex, columnIndex, firstPath));
				rowView.add(runeView.getView());
				runeViews.add(runeView);
			}
			slotRuneView.add(rowView);
		}
		return slotRuneView;
	}

	public JPanel getView() {
		return view;
	}

	private void updateRuneViews() {
		for (final RuneView runeView : runeViews) {
			runeView.updateSelectionState();
		}
	}

	private JPanel wrapInEmptyJPanel(final JComponent jComponent) {
		final JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		jPanel.add(jComponent);
		return jPanel;
	}

	private final class SelectKeyStone implements Runnable {
		private final int keyStoneIndex;

		public SelectKeyStone(final int keyStoneIndex) {
			this.keyStoneIndex = keyStoneIndex;
		}

		@Override
		public void run() {
			runePage.selectKeyStone(keyStoneIndex);
			updateRuneViews();
		}
	}

	private final class SelectSlotRune implements Runnable {
		private final int rowIndex;
		private final int columnIndex;
		private final boolean firstPath;

		public SelectSlotRune(final int rowIndex, final int columnIndex, final boolean firstPath) {
			this.rowIndex = rowIndex;
			this.columnIndex = columnIndex;
			this.firstPath = firstPath;
		}

		@Override
		public void run() {
			if (firstPath) {
				runePage.selectSlotRune(rowIndex, columnIndex);
			} else {
				runePage.selectSecondPath(rowIndex, columnIndex);
			}
			updateRuneViews();
		}
	}

}
