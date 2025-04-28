package todo.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.function.Function;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import todo.fileIO.Images;
import todo.model.RunePage;
import todo.model.RunePath;

/**
 * Creates view of a single rune page which updates itself based user clicks on
 * runes. An instance of this corresponds to one editable rune page.
 */
public final class RunePageView {
	private final RunePage runePage;
	private final Images images;
	private final JPanel view = new JPanel();

	/**
	 * Creates view of a single rune page which updates itself based user clicks on
	 * runes. An instance of this corresponds to one editable rune page.
	 * 
	 * @param runePage - initial visualization, may be an empty rune page, i.e.,
	 *                 nothing selected
	 */
	public RunePageView(final RunePage runePage, final Images images) {
		this.runePage = runePage;
		this.images = images;
		view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));
		updateView();
	}

	/**
	 * Creates an entire new view by removing all elements and creating new ones
	 * based on values of the referenced rune page instance.
	 */
	private void updateView() {
		view.removeAll();

		view.add(getRunePageTitleTextField());

		final JPanel runePathSelectionViews = new JPanel();
		runePathSelectionViews.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		runePathSelectionViews.add(getMainRunePathSelectionView());
		runePathSelectionViews.add(getSecondPathSelectionView());
		runePathSelectionViews.setBorder(new MatteBorder(0, 0, 2, 0, new Color(84, 84, 84)));
		view.add(runePathSelectionViews);

		final JPanel runesView = new JPanel(new FlowLayout(FlowLayout.CENTER));
		final JPanel mainRunesView = getMainRunePathView();
		final JPanel secondRunePathAndShardsView = getSecondRunePathAndShardsView();
		runesView.add(mainRunesView);
		runesView.add(secondRunePathAndShardsView);
		view.add(runesView);

		view.revalidate();
		view.repaint();
	}

	/**
	 * @return View to select the main rune path.
	 */
	private JPanel getMainRunePathSelectionView() {
		return getSingleRunePathSelectionView(runePathToCreateSelectorFor -> {
			final GraySelectionElement runePathSelection = new GraySelectionElement(
					images.getMainRunePathImages().get(runePathToCreateSelectorFor.getName()),
					images.getMainRunePathGrayImages().get(runePathToCreateSelectorFor.getName()), () -> {
						return runePathToCreateSelectorFor.equals(runePage.getMainRunePath());
					}, () -> {
						runePage.selectMainPath(runePathToCreateSelectorFor);
						updateView();
					});
			runePathSelection.getIconLabel().setToolTipText(runePathToCreateSelectorFor.getName());
			runePathSelection.updateSelectionState();
			return runePathSelection;
		});
	}

	/**
	 * @return View to select the second rune path.
	 */
	private JPanel getSecondPathSelectionView() {
		return getSingleRunePathSelectionView(runePathToCreateSelectorFor -> {
			final GraySelectionElement runePathSelection = new GraySelectionElement(
					images.getSecondRunePathImages().get(runePathToCreateSelectorFor.getName()),
					images.getSecondRunePathGrayImages().get(runePathToCreateSelectorFor.getName()), () -> {
						return runePathToCreateSelectorFor.equals(runePage.getSecondRunePath());
					}, () -> {
						runePage.selectSecondPath(runePathToCreateSelectorFor);
						updateView();
					});
			runePathSelection.getIconLabel().setToolTipText(runePathToCreateSelectorFor.getName());
			runePathSelection.updateSelectionState();
			return runePathSelection;
		});
	}

	/**
	 * @param selectionElementCreator
	 * @return View to select a rune path. The given function will be called for
	 *         each rune path to get the desired selection element.
	 */
	private JPanel getSingleRunePathSelectionView(
			final Function<RunePath, GraySelectionElement> selectionElementCreator) {
		final JPanel mainRunePathSelection = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		for (final RunePath runePath : RunePath.ALL) {
			final GraySelectionElement runePathSelection = selectionElementCreator.apply(runePath);
			mainRunePathSelection.add(runePathSelection.getView());
		}
		return mainRunePathSelection;
	}

	/**
	 * @return View to select runes of the main rune path consisting of key stones
	 *         and slot runes of the main path.
	 */
	private JPanel getMainRunePathView() {
		final JPanel mainRunePath = new JPanel(new BorderLayout(0, 0));
		mainRunePath.add(new SelectableRunesView(runePage.getKeyStones(), images.getKeyStoneImages(),
				images.getKeyStoneGrayImages()).getView(), BorderLayout.NORTH);
		mainRunePath.add(new SelectableRunesView(runePage.getSlotRunes(), images.getSlotRuneImages(),
				images.getSlotRuneGrayImages()).getView(), BorderLayout.CENTER);
		return mainRunePath;
	}

	/**
	 * 
	 * @return View to select runes of the second rune path (consisting of slot
	 *         runes of the second rune path), as well as shards.
	 */
	private JPanel getSecondRunePathAndShardsView() {
		final JPanel secondRunePathAndShards = new JPanel(new BorderLayout(0, 0));
		secondRunePathAndShards.add(new SelectableRunesView(runePage.getSecondPathSlotRunes(),
				images.getSlotRuneImages(), images.getSlotRuneGrayImages()).getView(),
				BorderLayout.NORTH);
		secondRunePathAndShards.add(new SelectableRunesView(runePage.getShards(), images.getShardImages(),
				images.getShardGrayImages()).getView(), BorderLayout.CENTER);
		return secondRunePathAndShards;
	}

	/**
	 * @return Textfield that automatically sets the title of the rune to the
	 *         contained text everytime it is updated.
	 */
	private JTextField getRunePageTitleTextField() {
		final JTextField runePageTitleTextField = new JTextField(runePage.getTitle());
		runePageTitleTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateRunePageTitle();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateRunePageTitle();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateRunePageTitle();
			}

			private void updateRunePageTitle() {
				final String userInputForRunePageTitle = runePageTitleTextField.getText();
				if (!userInputForRunePageTitle.isEmpty()) {
					runePage.setTitle(userInputForRunePageTitle);
				}
			}
		});
		return runePageTitleTextField;
	}

	public RunePage getRunePage() {
		return runePage;
	}

	public JPanel getView() {
		return view;
	}
}
