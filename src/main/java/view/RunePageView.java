package view;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

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
	private final List<RunePath> allRunePaths;
	private final JPanel view = new JPanel();
	private final List<GraySelectionElement> runePathSelectors = new ArrayList<>();

	public RunePageView(final RunePage runePage) {
		this.runePage = runePage;
		this.allRunePaths = runePage.getAllRunePaths();
		view.setLayout(new BoxLayout(view, BoxLayout.X_AXIS));
		view.add(getMainRunePathView());
		view.add(getSecondRunePathAndShardsView());
		updateRunePathSelectors();
	}

	private JPanel getMainRunePathView() {
		final JPanel mainRunePath = new JPanel();
		mainRunePath.setLayout(new BoxLayout(mainRunePath, BoxLayout.Y_AXIS));
		final JPanel mainRunePathSelection = getRunePathSelectionView(runePathToCreateSelectorFor -> {
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
		mainRunePath.add(mainRunePathSelection);
		mainRunePath.add(
				new SelectableRunesView(runePage.getKeyStones(), Images.KEY_STONE_IMAGES, Images.KEY_STONE_GRAY_IMAGES)
						.getView());
		mainRunePath.add(
				new SelectableRunesView(runePage.getSlotRunes(), Images.SLOT_RUNE_IMAGES, Images.SLOT_RUNE_GRAY_IMAGES)
						.getView());
		return mainRunePath;
	}

	private JPanel getSecondRunePathAndShardsView() {
		final JPanel secondRunePathAndShards = new JPanel();
		secondRunePathAndShards.setLayout(new BoxLayout(secondRunePathAndShards, BoxLayout.Y_AXIS));
		final JPanel secondRunePathSelection = getRunePathSelectionView(runePathToCreateSelectorFor -> {
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
		secondRunePathAndShards.add(secondRunePathSelection);
		secondRunePathAndShards.add(new SelectableRunesView(runePage.getSecondPathSlotRunes(), Images.SLOT_RUNE_IMAGES,
				Images.SLOT_RUNE_GRAY_IMAGES).getView());
		secondRunePathAndShards.add(
				new SelectableRunesView(runePage.getShards(), Images.SHARD_IMAGES, Images.SHARD_GRAY_IMAGES).getView());
		return secondRunePathAndShards;
	}

	private JPanel getRunePathSelectionView(final Function<RunePath, GraySelectionElement> selectionElementCreator) {
		final JPanel mainRunePathSelection = new JPanel();
		mainRunePathSelection.setLayout(new BoxLayout(mainRunePathSelection, BoxLayout.X_AXIS));
		for (final RunePath runePath : allRunePaths) {
			final GraySelectionElement runePathSelection = selectionElementCreator.apply(runePath);
			mainRunePathSelection.add(runePathSelection.getView());
		}
		return mainRunePathSelection;
	}

	private void updateRunePathSelectors() {
		runePathSelectors.clear();
		view.removeAll();
		view.add(getMainRunePathView());
		view.add(getSecondRunePathAndShardsView());
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
