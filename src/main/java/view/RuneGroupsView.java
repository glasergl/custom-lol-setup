package view;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import model.RunePage;
import model.RunePageSuite;

/**
 * Creates a view to select groups and rune pages by title.
 */
public final class RuneGroupsView {
	private final RunePageSuite runePageSuite;
	private final RunePageSuiteView runePageSuiteView;
	private final JScrollPane view;
	private final int runePageLabelLeftMargin = 25;

	/**
	 * Creates a view to select groups and rune pages by title.
	 * 
	 * @param runePageSuite     - where the selection of groups and rune pages
	 *                          should be done
	 * @param runePageSuiteView - which need to be updated after the rune page suite
	 *                          changed.
	 */
	public RuneGroupsView(final RunePageSuite runePageSuite, final RunePageSuiteView runePageSuiteView) {
		this.runePageSuite = runePageSuite;
		this.runePageSuiteView = runePageSuiteView;

		final Map<String, List<RunePage>> runePagesByGroupName = runePageSuite.getRunePagesByGroupName();
		final Set<String> groupNames = runePagesByGroupName.keySet();
		final List<String> sortedGroupNames = new ArrayList<>(groupNames);
		Collections.sort(sortedGroupNames);

		final JPanel groupsView = new JPanel();
		groupsView.setLayout(new BoxLayout(groupsView, BoxLayout.Y_AXIS));
		for (final String groupName : sortedGroupNames) {
			groupsView.add(getGroupSelectionLabel(groupName));
			final List<RunePage> runePages = runePagesByGroupName.get(groupName);
			for (int runePageIndex = 0; runePageIndex < runePages.size(); runePageIndex++) {
				final RunePage runePage = runePages.get(runePageIndex);
				groupsView.add(getRunePageSelectionLabel(groupName, runePageIndex, runePage));
			}
		}

		this.view = new JScrollPane(groupsView, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		groupsView.setBorder(new EmptyBorder(5, 5, 5, 5));
		view.setBorder(new EmptyBorder(0, 0, 0, 0));
	}

	/**
	 * @param groupNameToSelect
	 * @return Label which when clicked selects the given group name
	 */
	private JLabel getGroupSelectionLabel(final String groupNameToSelect) {
		final JLabel groupNameLabel = new JLabel(groupNameToSelect);
		groupNameLabel.addMouseListener(new EmptyMouseListener() {
			@Override
			public void mouseClicked(final MouseEvent mouseEvent) {
				runePageSuite.selectGroup(groupNameToSelect);
				runePageSuiteView.updateView();
			}
		});
		return groupNameLabel;
	}

	/**
	 * 
	 * @param groupName
	 * @param runePageIndexToSelect
	 * @param runePage              - to fetch the title from and check whether the
	 *                              label should display as selected
	 * @return Label which when clicked selects the rune page index within the given
	 *         group.
	 */
	private JLabel getRunePageSelectionLabel(final String groupName, final int runePageIndexToSelect,
			final RunePage runePage) {
		final JLabel runePageTitle = new JLabel(runePage.getTitle());
		final Optional<RunePage> selectedRunePage = runePageSuite.getSelectedRunePage();
		if (selectedRunePage.isPresent() && runePage.equals(selectedRunePage.get())) {
			runePageTitle.setOpaque(true);
			runePageTitle.setBackground(Color.YELLOW);
		}
		runePageTitle.addMouseListener(new EmptyMouseListener() {
			@Override
			public void mouseClicked(final MouseEvent mouseEvent) {
				runePageSuite.selectRunePage(groupName, runePageIndexToSelect);
				runePageSuiteView.updateView();
			}
		});
		runePageTitle.setBorder(new EmptyBorder(0, runePageLabelLeftMargin, 0, 0));
		return runePageTitle;
	}

	public JScrollPane getView() {
		return view;
	}
}
