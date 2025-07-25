package todo.main;

import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import todo.fileIO.Images;
import todo.fileIO.RunePageImportExport;
import todo.model.RunePage;
import todo.model.RunePageSuite;
import todo.view.CustomColors;
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
		UIManager.put("Button.font", new Font("Sans-Serif", Font.BOLD, 20));
		UIManager.put("Label.font", new Font("Sans-Serif", Font.PLAIN, 18));
		UIManager.put("TextField.font", new Font("Sans-Serif", Font.PLAIN, 18));
		UIManager.put("Label.foreground", CustomColors.TEXT);
		UIManager.put("TextField.border", new EmptyBorder(3, 3, 3, 3));
		UIManager.put("Panel.background", CustomColors.BACKGROUND);
		UIManager.put("OptionPane.messageForeground", CustomColors.TEXT);
		UIManager.put("OptionPane.background", CustomColors.BACKGROUND);
		UIManager.put("ScrollBar.background", CustomColors.BACKGROUND);
		UIManager.put("TextArea.background", CustomColors.BACKGROUND);
		UIManager.put("TextArea.foreground", CustomColors.TEXT);
		
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
