package de.glasergl.custom.lol.setup;

import java.io.IOException;
import java.util.Locale;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import de.glasergl.custom.lol.setup.io.Images;
import de.glasergl.custom.lol.setup.io.SetupFileIo;
import de.glasergl.custom.lol.setup.io.ui.CreateFrame;
import de.glasergl.custom.lol.setup.model.entity.Setup;

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
	Locale.setDefault(Locale.ENGLISH);
	final Images images = new Images();
	final SetupFileIo fileIo = new SetupFileIo();
	final Set<Setup> initialSetups = fileIo.getSetups();
	
	UIManager.put("ScrollBar.unitIncrement", 50);
	SwingUtilities.invokeLater(() -> {
	    new CreateFrame(images, initialSetups, fileIo);
	});
    }
}
