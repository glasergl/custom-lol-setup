package de.glasergl.custom.lol.setup;

import java.awt.Font;
import java.io.IOException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import de.glasergl.custom.lol.setup.file.Images;
import de.glasergl.custom.lol.setup.file.SetupFileIo;
import de.glasergl.custom.lol.setup.ui.CreateFrame;

/**
 * Main class for this application.
 */
public final class Main {
    /**
     * Entry point of the application.
     * 
     * @param commandLineArguments - unused.
     */
    public static void main(final String[] commandLineArguments) throws IOException {
	final Images images = new Images();
	final SetupFileIo fileIo = new SetupFileIo();
	UIManager.put("Label.font", new Font(Font.SANS_SERIF, Font.PLAIN, 15));
	UIManager.put("Button.font", new Font(Font.SANS_SERIF, Font.PLAIN, 20));
	UIManager.put("TextField.font", new Font(Font.SANS_SERIF, Font.PLAIN, 13));
	UIManager.put("TextArea.font", new Font(Font.SANS_SERIF, Font.PLAIN, 13));
	UIManager.put("CheckBox.font", new Font(Font.SANS_SERIF, Font.PLAIN, 12));
	SwingUtilities.invokeLater(() -> new CreateFrame(images, fileIo));
    }
}
