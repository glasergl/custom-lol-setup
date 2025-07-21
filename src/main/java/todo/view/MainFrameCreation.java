package todo.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import todo.fileIO.Images;
import todo.fileIO.RunePageImportExport;
import todo.model.RunePageSuite;

/**
 * Generates the main frame for this application.
 */
public final class MainFrameCreation {
	private final JFrame jFrame;
	private final String title = "Custom Runes";
	private final RunePageSuite runePageSuite;
	private final Images images;

	public MainFrameCreation(final Image icon, final RunePageSuite runePageSuite, final Images images) {
		this.jFrame = new JFrame(title);
		this.images = images;
		this.runePageSuite = runePageSuite;
		final Image scaledIcon = icon.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		jFrame.setIconImage(scaledIcon);
		jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // custom closing behavior with window listener
		setupClosingBehaviour();

		createAndAddComponents();
		jFrame.setSize(1100, 600);
		jFrame.setLocationRelativeTo(null);
		jFrame.setVisible(true);
	}

	private void createAndAddComponents() {
		final RunePageSuiteView runePageSuiteView = new RunePageSuiteView(runePageSuite, images);
		final JButton saveButton = new JButton("Save");
		saveButton.addActionListener(click -> {
			new SaveRunePages();
			runePageSuiteView.updateView();
		});

		final Container frameContentPane = jFrame.getContentPane();
		frameContentPane.setLayout(new BorderLayout());
		frameContentPane.add(runePageSuiteView.getView(), BorderLayout.CENTER);
		final JPanel saveButtonWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		saveButtonWrapper.add(saveButton);
		frameContentPane.add(saveButtonWrapper, BorderLayout.NORTH);
	}

	private void setupClosingBehaviour() {
		jFrame.addWindowListener(new EmptyWindowListener() {
			@Override
			public void windowClosing(final WindowEvent windowEvent) {
				final int response = JOptionPane.showConfirmDialog(jFrame,
						"Save before closing? Any non-saved rune pages might be lost.", "Save?",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {
					new SaveRunePages();
					jFrame.dispose();
				} else if (response == JOptionPane.NO_OPTION) {
					jFrame.dispose();
				}
			}
		});
	}

	private final class SaveRunePages extends SwingWorker<Optional<Exception>, Void> {
		private SaveRunePages() {
			execute();
		}

		@Override
		public Optional<Exception> doInBackground() throws Exception {
			try {
				RunePageImportExport.storeRunePages(runePageSuite.getRunePagesByGroupName());
				return Optional.empty();
			} catch (final IOException e) {
				return Optional.of(e);
			}
		}

		@Override
		public void done() {
			try {
				final Optional<Exception> errorDuringSave = get();
				if (errorDuringSave.isPresent()) {
					final Exception error = errorDuringSave.get();
					JOptionPane.showMessageDialog(jFrame, error.getMessage(), error.getClass().getName(),
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (final InterruptedException | ExecutionException e) {
				/*
				 * not reachable, because done() is only called after doInBackground is
				 * finished, i.e., get() of SwingWorker doesn't block
				 */
			}
		}
	}

	public JFrame getFrame() {
		return jFrame;
	}
}
