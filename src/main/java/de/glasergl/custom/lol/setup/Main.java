package de.glasergl.custom.lol.setup;

import java.io.IOException;

import javax.swing.SwingUtilities;

import de.glasergl.custom.lol.setup.io.Images;
import de.glasergl.custom.lol.setup.io.SetupFileIo;
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
    public static void main(final String[] commandLineArguments) throws IOException {
	final Images images = new Images();
	final SetupFileIo fileIo = new SetupFileIo();
	SwingUtilities.invokeLater(() -> {
	    new CreateFrame(images, fileIo);
	});
    }
}
