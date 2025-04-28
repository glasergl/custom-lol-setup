package todo.main;

import java.awt.Image;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import todo.fileIO.Images;
import todo.fileIO.RunePageImportExport;
import todo.model.RunePage;
import todo.model.RunePageSuite;
import todo.view.MainFrameCreation;

/**
 * Main class for this application.
 */
public final class CustomRunes {
	/**
	 * Entry point of the application.
	 * 
	 * @param commandLineArguments - unused.
	 */
	public static void main(final String[] commandLineArguments) {
		Locale.setDefault(Locale.ENGLISH);
		final Images images = new Images();

		final Image frameIcon;
		try {
			frameIcon = ImageIO.read(CustomRunes.class.getResource("/GatheringStorm.png"));
		} catch (final IOException e) {
			throw new IllegalStateException(e);
		}

		final Map<String, List<RunePage>> runePagesByGroup;
		try {
			runePagesByGroup = RunePageImportExport.getRunePages();
		} catch (final IOException e) {
			JOptionPane.showMessageDialog(null, String.format("Unable to read runes from disk: %s", e.getMessage()),
					e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
			throw new IllegalStateException(e);
		}
		final RunePageSuite runePageSuite = new RunePageSuite(runePagesByGroup);

		SwingUtilities.invokeLater(() -> {
			new MainFrameCreation(frameIcon, runePageSuite, images);
		});
	}
}
