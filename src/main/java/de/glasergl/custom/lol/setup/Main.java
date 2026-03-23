package de.glasergl.custom.lol.setup;

import java.util.Locale;

import javax.swing.SwingUtilities;

import de.glasergl.custom.lol.setup.io.Images;
import de.glasergl.custom.lol.setup.io.ui.CreateFrame;

/**
 * Main class for this application.
 */
public final class Main {
    /**
     * Entry point of the application.
     * 
     * @param commandLineArguments - unused.
     */
    public static void main(final String[] commandLineArguments) {
	Locale.setDefault(Locale.ENGLISH);
	final Images images = new Images();
	SwingUtilities.invokeLater(() -> {
	    new CreateFrame(images);
	});
    }
}
