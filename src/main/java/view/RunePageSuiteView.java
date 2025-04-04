package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.RunePage;
import model.RunePageSuite;

public class RunePageSuiteView {
	private final RunePageSuite runePageSuite;
	private final JPanel view = new JPanel();

	private String currentGroupName = "";

	public RunePageSuiteView(final RunePageSuite runePageSuite) {
		this.runePageSuite = runePageSuite;
		view.setLayout(new BorderLayout());
		update();
	}

	private void update() {
		final RunePage currentRunePage = runePageSuite.getCurrentRunePage();
		final RunePageView runePageView = new RunePageView(currentRunePage);
		view.removeAll();
		view.add(runePageView.getView(), BorderLayout.CENTER);
		view.add(getRunePageGroupController(), BorderLayout.WEST);
		view.add(getRunePageAdderController(), BorderLayout.SOUTH);
		view.revalidate();
		view.repaint();
	}

	private JPanel getRunePageGroupController() {
		final Map<String, List<RunePage>> runePagesByGroupName = runePageSuite.getRunePagesByGroupName();
		final Set<String> groupNames = runePagesByGroupName.keySet();
		final List<String> sortedGroupNames = new ArrayList<>(groupNames);
		Collections.sort(sortedGroupNames);

		final JPanel controllerView = new JPanel();
		controllerView.setLayout(new BoxLayout(controllerView, BoxLayout.Y_AXIS));
		for (final String groupName : sortedGroupNames) {
			controllerView.add(new JLabel(groupName));
			final List<RunePage> runePages = runePagesByGroupName.get(groupName);
			for (int i = 0; i < runePages.size(); i++) {
				final RunePage runePage = runePages.get(i);
				final JLabel runePageTitle = new JLabel(runePage.getTitle());
				final int runePageIndex = i;
				runePageTitle.addMouseListener(new MouseListener() {
					@Override
					public void mouseClicked(MouseEvent e) {
						runePageSuite.selectRunePage(groupName, runePageIndex);
						currentGroupName = groupName;
						update();
					}

					@Override
					public void mousePressed(MouseEvent e) {
					}

					@Override
					public void mouseReleased(MouseEvent e) {
					}

					@Override
					public void mouseEntered(MouseEvent e) {
					}

					@Override
					public void mouseExited(MouseEvent e) {
					}
				});
				if (!groupName.isEmpty()) {
					runePageTitle.setBorder(new EmptyBorder(0, 20, 0, 0));
				}
				controllerView.add(runePageTitle);
			}
		}
		return controllerView;
	}

	private JPanel getRunePageAdderController() {
		final JLabel groupNameLabel = new JLabel("Group Name:");
		final JTextField groupNameTextField = new JTextField(20);
		final JButton addGroupNameButton = new JButton("Add Group");
		addGroupNameButton.addActionListener(click -> {
			final String groupNameEnteredByUser = groupNameTextField.getText();
			runePageSuite.addGroup(groupNameEnteredByUser);
			currentGroupName = groupNameEnteredByUser;
			update();
		});
		final JPanel groupNamePanel = new JPanel(new BorderLayout());
		groupNamePanel.add(groupNameLabel, BorderLayout.WEST);
		groupNamePanel.add(groupNameTextField, BorderLayout.CENTER);
		groupNamePanel.add(addGroupNameButton, BorderLayout.SOUTH);

		final JButton addEmptyRunePageButton = new JButton("Add Empty Rune Page");
		addEmptyRunePageButton.addActionListener(click -> {
			runePageSuite.addEmptyRunePage(currentGroupName);
			update();
		});

		final JPanel adderController = new JPanel(new FlowLayout(FlowLayout.CENTER));
		adderController.add(groupNamePanel);
		adderController.add(addEmptyRunePageButton);
		return adderController;
	}

	public RunePageSuite getRunePageSuite() {
		return runePageSuite;
	}

	public JPanel getView() {
		return view;
	}
}
