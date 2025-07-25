package todo.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import todo.fileIO.Images;
import todo.model.RunePage;
import todo.model.RunePageSuite;

/**
 * Creates view for a rune page suite, i.e., lists of rune pages organized by
 * group names.
 */
public final class RunePageSuiteView {
	private final RunePageSuite runePageSuite;
	private final Images images;
	private final JPanel view = new JPanel();

	/**
	 * Creates view for a rune page suite, i.e., lists of rune pages organized by
	 * group names. The view of this instance is able to create, remove and organize
	 * rune pages, as well as modifying rune pages (using rune page views).
	 * 
	 * @param runePageSuite
	 */
	public RunePageSuiteView(final RunePageSuite runePageSuite, final Images images) {
		this.runePageSuite = runePageSuite;
		this.images = images;
		view.setLayout(new BorderLayout());
		updateView();
	}

	/**
	 * Creates an entire new view by removing all elements and creating new ones
	 * based on values of the referenced rune page suite instance.
	 */
	public void updateView() {
		view.removeAll();

		final Optional<RunePage> currentRunePage = runePageSuite.getSelectedRunePage();
		if (currentRunePage.isPresent()) {
			final RunePageView runePageView = new RunePageView(currentRunePage.get(), images);
			final JPanel runePageViewWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
			runePageViewWrapper.add(runePageView.getView());
			view.add(runePageViewWrapper, BorderLayout.CENTER);
		}
		view.add(new RuneGroupsView(runePageSuite, this).getView(), BorderLayout.WEST);
		view.add(getRunePageAdderController(), BorderLayout.SOUTH);
		
		view.revalidate();
		view.repaint();
	}

	/**
	 * @return Panel that contains fields to add groups, delete groups, add empty
	 *         rune pages an delete rune pages.
	 */
	private JPanel getRunePageAdderController() {
		final JPanel groupNamePanel = getGroupNameAdderPanel();

		final JButton addEmptyRunePageButton = new JButton("Add Empty Rune Page");
		addEmptyRunePageButton.addActionListener(click -> {
			runePageSuite.addEmptyRunePage();
			updateView();
		});

		final JButton deleteCurrentGroupButton = new JButton("Delete Current Group");
		deleteCurrentGroupButton.addActionListener(click -> {
			final Optional<String> currentGroupName = runePageSuite.getSelectedGroupName();
			if (currentGroupName.isPresent()) {
				final int response = JOptionPane.showConfirmDialog(view,
						String.format("Are you sure you want to delete the group '%s'?", currentGroupName.get()),
						"Deleting Group", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {
					runePageSuite.deleteGroup(currentGroupName.get());
				}
			}
			updateView();
		});

		final JButton deleteCurrentRunePageButton = new JButton("Delete Current Rune Page");
		deleteCurrentRunePageButton.addActionListener(click -> {
			final Optional<String> currenGroupName = runePageSuite.getSelectedGroupName();
			final Optional<RunePage> currentRunePage = runePageSuite.getSelectedRunePage();
			if (currentRunePage.isPresent()) {
				final RunePage runePageToDelete = currentRunePage.get();
				final int response = JOptionPane.showConfirmDialog(view,
						String.format("Are you sure you want to delete rune page '%s' of group '%s'?",
								runePageToDelete.getTitle(), currenGroupName.get()),
						"Deleting Rune Page", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {
					runePageSuite.deleteSelectedRunePage();
				}
			}
			updateView();
		});

		final JPanel adderController = new JPanel(new FlowLayout(FlowLayout.CENTER));
		adderController.add(groupNamePanel);
		adderController.add(addEmptyRunePageButton);
		final JPanel deleteButtonsPanel = new JPanel();
		deleteButtonsPanel.setLayout(new BorderLayout(5, 5));
		deleteButtonsPanel.add(deleteCurrentRunePageButton, BorderLayout.NORTH);
		deleteButtonsPanel.add(deleteCurrentGroupButton, BorderLayout.SOUTH);
		adderController.add(deleteButtonsPanel);
		return adderController;
	}

	/**
	 * @return Generates the UI that allows adding groups.
	 */
	private JPanel getGroupNameAdderPanel() {
		final JLabel groupNameLabel = new JLabel("Group Name:");
		final JTextField groupNameTextField = new JTextField(20);
		final JButton addGroupNameButton = new JButton("Add Group");
		addGroupNameButton.setEnabled(false);
		addGroupNameButton.addActionListener(click -> {
			final String groupNameEnteredByUser = groupNameTextField.getText();
			runePageSuite.addGroupIfNotExistsAndSelect(groupNameEnteredByUser);
			updateView();
		});
		groupNameTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				deactivateButtonIfTextIsEmpty();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				deactivateButtonIfTextIsEmpty();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				deactivateButtonIfTextIsEmpty();
			}

			private void deactivateButtonIfTextIsEmpty() {
				addGroupNameButton.setEnabled(!groupNameTextField.getText().isEmpty());
			}
		});
		final JPanel groupNamePanel = new JPanel(new BorderLayout(5, 5));
		groupNamePanel.add(groupNameLabel, BorderLayout.WEST);
		groupNamePanel.add(groupNameTextField, BorderLayout.CENTER);
		groupNamePanel.add(addGroupNameButton, BorderLayout.SOUTH);
		return groupNamePanel;
	}

	public JPanel getView() {
		return view;
	}
}
