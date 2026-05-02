package de.glasergl.custom.lol.setup;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

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
	Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
	    final StringWriter stringWriter = new StringWriter();
	    final PrintWriter printWriter = new PrintWriter(stringWriter);
	    throwable.printStackTrace(printWriter);

	    JOptionPane.showMessageDialog(null, stringWriter.toString(), "Uncaught Exception: Program will Terminate", JOptionPane.ERROR_MESSAGE);
	    System.exit(1);
	});
	final Images images = new Images();
	final SetupFileIo fileIo = new SetupFileIo();
	SwingUtilities.invokeLater(() -> new CreateFrame(images, fileIo));
    }
}
