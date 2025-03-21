package view;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageReading {
	public static Image getImageFromName(final String name, final String imageType) {
		try {
			final String imageFileName = name.replaceAll("[^A-Za-z0-9]", "") + "." + imageType;
			final Image image = ImageIO.read(ImageReading.class.getResourceAsStream("/" + imageFileName));
			if (image == null) {
				throw new IllegalArgumentException();
			}
			return image;
		} catch (final IOException | RuntimeException e) {
			throw new IllegalArgumentException(String.format("Unable to get image for rune '%s'", name));
		}
	}

	public static Image getImageFromName(final String name, final String imageType, final int width, final int height) {
		try {
			final String imageFileName = name.replaceAll("[^A-Za-z0-9]", "") + "." + imageType;
			final Image image = ImageIO.read(ImageReading.class.getResourceAsStream("/" + imageFileName));
			if (image == null) {
				throw new IllegalArgumentException();
			}
			return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		} catch (final IOException | RuntimeException e) {
			throw new IllegalArgumentException(String.format("Unable to get image for rune '%s'", name));
		}
	}
}
