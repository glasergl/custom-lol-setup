package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import fileIO.Images;
import model.RunePage;
import model.RunePath;

/**
 * Creates a JPanel of a single rune page which updates itself based user clicks
 * on runes. An instance of this corresponds 1 to 1 to a rune page. After
 * instantiation, the rune paths cannot be changed anymore (but the selections,
 * of course).
 */
public final class RunePageView {
	private final RunePage runePage;
	private final JPanel view = new JPanel();
	private final List<GraySelectionElement> runePathSelectors = new ArrayList<>();

	public RunePageView(final RunePage runePage) {
		this.runePage = runePage;
		view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));
		updateRunePathSelectors();
	}

	private JPanel getBothRunePathSelectionViews() {
		final JPanel mainRunePathSelection = getSingleRunePathSelectionView(runePathToCreateSelectorFor -> {
			final GraySelectionElement runePathSelection = new GraySelectionElement(
					Images.MAIN_RUNE_PATH_IMAGES.get(runePathToCreateSelectorFor.getName()),
					Images.MAIN_RUNE_PATH_GRAY_IMAGES.get(runePathToCreateSelectorFor.getName()), () -> {
						return runePathToCreateSelectorFor.equals(runePage.getMainRunePath());
					}, () -> {
						runePage.selectMainPath(runePathToCreateSelectorFor);
						updateRunePathSelectors();
					});
			runePathSelectors.add(runePathSelection);
			return runePathSelection;
		});

		final JPanel secondRunePathSelection = getSingleRunePathSelectionView(runePathToCreateSelectorFor -> {
			final GraySelectionElement runePathSelection = new GraySelectionElement(
					Images.SECOND_RUNE_PATH_IMAGES.get(runePathToCreateSelectorFor.getName()),
					Images.SECOND_RUNE_PATH_GRAY_IMAGES.get(runePathToCreateSelectorFor.getName()), () -> {
						return runePathToCreateSelectorFor.equals(runePage.getSecondRunePath());
					}, () -> {
						runePage.selectSecondPath(runePathToCreateSelectorFor);
						updateRunePathSelectors();
					});
			runePathSelectors.add(runePathSelection);
			return runePathSelection;
		});

		final JPanel runePathSelectionView = new JPanel();
		runePathSelectionView.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		runePathSelectionView.add(mainRunePathSelection);
		runePathSelectionView.add(secondRunePathSelection);
		return runePathSelectionView;
	}

	private JPanel getMainRunePathView() {
		final JPanel mainRunePath = new JPanel(new BorderLayout(0, 0));
		mainRunePath.add(
				new SelectableRunesView(runePage.getKeyStones(), Images.KEY_STONE_IMAGES, Images.KEY_STONE_GRAY_IMAGES)
						.getView(),
				BorderLayout.NORTH);
		mainRunePath.add(
				new SelectableRunesView(runePage.getSlotRunes(), Images.SLOT_RUNE_IMAGES, Images.SLOT_RUNE_GRAY_IMAGES)
						.getView(),
				BorderLayout.CENTER);
		return mainRunePath;
	}

	private JPanel getSecondRunePathAndShardsView() {
		final JPanel secondRunePathAndShards = new JPanel(new BorderLayout(0, 0));
		secondRunePathAndShards.add(new SelectableRunesView(runePage.getSecondPathSlotRunes(), Images.SLOT_RUNE_IMAGES,
				Images.SLOT_RUNE_GRAY_IMAGES).getView(), BorderLayout.NORTH);
		secondRunePathAndShards.add(
				new SelectableRunesView(runePage.getShards(), Images.SHARD_IMAGES, Images.SHARD_GRAY_IMAGES).getView(),
				BorderLayout.CENTER);
		return secondRunePathAndShards;
	}

	private JPanel getSingleRunePathSelectionView(
			final Function<RunePath, GraySelectionElement> selectionElementCreator) {
		final JPanel mainRunePathSelection = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		for (final RunePath runePath : RunePath.ALL) {
			final GraySelectionElement runePathSelection = selectionElementCreator.apply(runePath);
			mainRunePathSelection.add(runePathSelection.getView());
		}
		return mainRunePathSelection;
	}

	private void updateRunePathSelectors() {
		runePathSelectors.clear();
		view.removeAll();
		final JTextField runePageTitleTextField = new JTextField(runePage.getTitle());
		view.add(runePageTitleTextField);
		final JPanel runePathSelectionViews = getBothRunePathSelectionViews();
		runePathSelectionViews.setBorder(new MatteBorder(0, 0, 2, 0, new Color(84, 84, 84)));
		view.add(runePathSelectionViews);
		final JPanel runesView = new JPanel(new FlowLayout(FlowLayout.CENTER));
		final JPanel mainRunesView = getMainRunePathView();
		final JPanel secondRunePathAndShardsView = getSecondRunePathAndShardsView();
		mainRunesView.setAlignmentY(JComponent.TOP_ALIGNMENT);
		secondRunePathAndShardsView.setAlignmentY(JComponent.TOP_ALIGNMENT);
		runesView.add(mainRunesView);
		runesView.add(secondRunePathAndShardsView);
		view.add(runesView);
		for (final GraySelectionElement runePath : runePathSelectors) {
			runePath.updateSelectionState();
		}
		view.revalidate();
		view.repaint();
	}

	public RunePage getRunePage() {
		return runePage;
	}

	public JPanel getView() {
		return view;
	}
}
