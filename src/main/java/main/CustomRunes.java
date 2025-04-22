package main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import fileIO.Images;
import fileIO.RunePageImportExport;
import model.RunePage;
import model.RunePageSuite;
import view.RunePageSuiteView;

public final class CustomRunes {
	public static void main(String[] args) {
		Images.loadImages();
		Locale.setDefault(Locale.ENGLISH);
		final Map<String, List<RunePage>> runePagesByGroup;
		try {
			runePagesByGroup = RunePageImportExport.getRunePages();
		} catch (final IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Unable to read rune pages json file", "File Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		createMainFrame(runePagesByGroup);
	}

	private static void createMainFrame(final Map<String, List<RunePage>> runePagesByGroup) {
		final RunePageSuite runePageSuite = new RunePageSuite(runePagesByGroup);
		SwingUtilities.invokeLater(() -> {
			final JFrame frame = new JFrame("Custom Runes");
			try {
				frame.setIconImage(ImageIO.read(CustomRunes.class.getResource("/GatheringStorm.png"))
						.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
			} catch (final IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Unable to read image from jar", "Jar Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			frame.addWindowListener(new WindowListener() {
				@Override
				public void windowOpened(WindowEvent e) {
				}

				@Override
				public void windowClosing(WindowEvent e) {
					final int response = JOptionPane.showConfirmDialog(frame, "Save before closing? Any non-saved rune pages might be lost.", "Save?",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
					if (response == JOptionPane.YES_OPTION) {
						saveRunePages(runePageSuite, frame);
						frame.dispose();
					} else if (response == JOptionPane.NO_OPTION) {
						frame.dispose();
					}
				}

				@Override
				public void windowClosed(WindowEvent e) {
				}

				@Override
				public void windowIconified(WindowEvent e) {
				}

				@Override
				public void windowDeiconified(WindowEvent e) {
				}

				@Override
				public void windowActivated(WindowEvent e) {
				}

				@Override
				public void windowDeactivated(WindowEvent e) {
				}
			});

			final RunePageSuiteView runePageSuiteView = new RunePageSuiteView(runePageSuite);

			final JButton saveButton = new JButton("Save");
			saveButton.addActionListener(click -> {
				saveRunePages(runePageSuite, frame);
				runePageSuiteView.update();
			});

			final Container frameContentPane = frame.getContentPane();
			frameContentPane.setLayout(new BorderLayout());
			frameContentPane.add(runePageSuiteView.getView(), BorderLayout.CENTER);
			final JPanel saveButtonWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			saveButtonWrapper.add(saveButton);
			frameContentPane.add(saveButtonWrapper, BorderLayout.NORTH);
			frame.setSize(1200, 700);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}

	private static void saveRunePages(final RunePageSuite runePageSuite, final JFrame frame) {
		try {
			RunePageImportExport.storeRunePages(runePageSuite.getRunePagesByGroupName());
		} catch (final IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame,
					String.format("Unable to save rune pages as a file, because '%s'", e.getMessage()), "File Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
