package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import model.RunePageBuilder;
import model.RunePath;

/**
 * Manages the paths of rune pages, i.e., creates new rune pages and
 * corresponding rune page views for changing rune paths.
 */
public final class RunePageBuilderView {
	private final RunePageBuilder runePageBuilder;
	private final JPanel view = new JPanel();
	private final List<GraySelectionElement> runePathSelectionElements = new ArrayList<>();
	private RunePageView runePageView;

	public RunePageBuilderView(final RunePageBuilder runePageBuilder) {
		this.runePageBuilder = runePageBuilder;
		this.runePageView = new RunePageView(runePageBuilder.getRunePage());

		final JPanel runePathSelectors = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		runePathSelectors.add(createMainRunePathSelection(runePath -> {
			runePageBuilder.changeMainPath(runePath);
		}, (runePath) -> {
			return runePageBuilder.getRunePage().getMainRunePath().equals(runePath);
		}, 50));
		runePathSelectors.add(createMainRunePathSelection(runePath -> {
			runePageBuilder.changeSecondPath(runePath);
		}, (runePath) -> {
			return runePageBuilder.getRunePage().getSecondRunePath().equals(runePath);
		}, 30));
		view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));
		view.add(runePathSelectors);
		view.add(runePageView.getView());
	}

	private JPanel createMainRunePathSelection(final Consumer<RunePath> executePathChange,
			final Function<RunePath, Boolean> shouldBeSelectedCheck, final int iconSize) {
		final JPanel mainRunePathSelection = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		for (final RunePath runePath : runePageBuilder.getRunePaths()) {
			final Image image = ImageReading.getImageFromName(runePath.getName(), "png", iconSize, iconSize);
			final GraySelectionElement runePathSelection = new GraySelectionElement(image, () -> {
				return shouldBeSelectedCheck.apply(runePath);
			}, () -> {
				final RunePath currentMainPath = runePageBuilder.getRunePage().getMainRunePath();
				if (!currentMainPath.equals(runePath)) {
					executePathChange.accept(runePath);
					updateRunePathSelectionState();
				}
			});
			runePathSelectionElements.add(runePathSelection);
			mainRunePathSelection.add(runePathSelection.getView());
		}
		updateRunePathSelectionState();
		return mainRunePathSelection;
	}

	private void updateRunePathSelectionState() {
		for (final GraySelectionElement runePathSelectionElement : runePathSelectionElements) {
			runePathSelectionElement.updateSelectionState();
		}
		view.remove(runePageView.getView());
		runePageView = new RunePageView(runePageBuilder.getRunePage());
		view.add(runePageView.getView(), BorderLayout.CENTER);
		view.revalidate();
		view.repaint();
	}

	public JPanel getView() {
		return view;
	}
}
