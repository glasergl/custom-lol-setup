package view;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageReading {
	public static Image getImageFromName(final String name, final String imageType) {
		try {
			final String imageFileName = name.replaceAll("[^A-Za-z0-9]", "") + "." + imageType;
			return ImageIO.read(ImageReading.class.getResourceAsStream("/" + imageFileName));
		} catch (final IOException e) {
			throw new IllegalArgumentException(String.format("Unable to get image for %s", name));
		}
	}
}
