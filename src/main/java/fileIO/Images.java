package fileIO;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.UIManager;

import model.Rune;
import model.RunePath;
import model.Shards;

/**
 * Stores all required image in public static variables. Each image is scaled
 * according to he desired size.
 */
public final class Images {
	private static final int MAIN_RUNE_PATH_ICON_SIZE = 55;
	private static final int SECOND_RUNE_PATH_ICON_SIZE = 40;
	private static final int KEY_STONE_ICON_SIZE = 60;
	private static final int SLOT_RUNE_ICON_SIZE = 35;
	private static final int SHARD_ICON_SIZE = 25;

	public static final Map<String, Image> MAIN_RUNE_PATH_IMAGES = getRunePathImages(MAIN_RUNE_PATH_ICON_SIZE);
	public static final Map<String, Image> SECOND_RUNE_PATH_IMAGES = getRunePathImages(SECOND_RUNE_PATH_ICON_SIZE);
	public static final Map<String, Image> KEY_STONE_IMAGES = getKeyStoneImages();
	public static final Map<String, Image> SLOT_RUNE_IMAGES = getSlotRuneImages();
	public static final Map<String, Image> SHARD_IMAGES = getShardImages();

	public static final Map<String, Image> MAIN_RUNE_PATH_GRAY_IMAGES = getGrayImages(MAIN_RUNE_PATH_IMAGES);
	public static final Map<String, Image> SECOND_RUNE_PATH_GRAY_IMAGES = getGrayImages(SECOND_RUNE_PATH_IMAGES);
	public static final Map<String, Image> KEY_STONE_GRAY_IMAGES = getGrayImages(KEY_STONE_IMAGES);
	public static final Map<String, Image> SLOT_RUNE_GRAY_IMAGES = getGrayImages(SLOT_RUNE_IMAGES);
	public static final Map<String, Image> SHARD_GRAY_IMAGES = getGrayImages(SHARD_IMAGES);

	/**
	 * Method to call to load all images. Doesn't need an implementation as all
	 * static variables initialize itself directly after declaration.
	 */
	public static void loadImages() {
	}

	private static Map<String, Image> getRunePathImages(final int iconSize) {
		final Map<String, Image> images = new HashMap<>();
		for (final RunePath runePath : RunePath.ALL) {
			images.put(runePath.getName(), getImageFromName(runePath.getName(), "png", iconSize, iconSize));
		}
		return images;
	}

	private static Map<String, Image> getKeyStoneImages() {
		final Map<String, Image> images = new HashMap<>();
		for (final RunePath runePath : RunePath.ALL) {
			for (final Rune keyStone : runePath.getKeyStones()) {
				images.put(keyStone.getName(),
						getImageFromName(keyStone.getName(), "png", KEY_STONE_ICON_SIZE, KEY_STONE_ICON_SIZE));
			}
		}
		return images;
	}

	private static Map<String, Image> getSlotRuneImages() {
		final Map<String, Image> images = new HashMap<>();
		for (final RunePath runePath : RunePath.ALL) {
			for (final List<Rune> slotRuneRow : runePath.getSlotRunes()) {
				for (final Rune slotRune : slotRuneRow) {
					images.put(slotRune.getName(),
							getImageFromName(slotRune.getName(), "png", SLOT_RUNE_ICON_SIZE, SLOT_RUNE_ICON_SIZE));
				}
			}
		}
		return images;
	}

	private static Map<String, Image> getShardImages() {
		final Map<String, Image> images = new HashMap<>();
		for (final List<Rune> shardRow : Shards.ALL) {
			for (final Rune shard : shardRow) {
				images.put(shard.getName(), getImageFromName(shard.getName(), "png", SHARD_ICON_SIZE, SHARD_ICON_SIZE));
			}
		}
		return images;
	}

	private static Image getImageFromName(final String name, final String imageType, final int width,
			final int height) {
		try {
			final String imageFileName = name.replaceAll("[^A-Za-z0-9]", "") + "." + imageType;
			final Image image = ImageIO.read(Images.class.getResourceAsStream("/" + imageFileName));
			if (image == null) {
				throw new IllegalArgumentException();
			}
			return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		} catch (final IOException | RuntimeException e) {
			throw new IllegalArgumentException(String.format("Unable to get image for rune '%s'", name));
		}
	}

	private static Map<String, Image> getGrayImages(final Map<String, Image> images) {
		final Map<String, Image> grayImages = new HashMap<>();
		for (final String imageKey : images.keySet()) {
			final Image image = images.get(imageKey);
			grayImages.put(imageKey, getGrayScale(image));
		}
		return grayImages;
	}

	private static Image getGrayScale(final Image image) {
		final BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_BYTE_GRAY);
		final Graphics2D painter = bufferedImage.createGraphics();
		painter.drawImage(image, 0, 0, UIManager.getColor("Panel.background"), null);
		painter.dispose();
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp op = new ColorConvertOp(cs, null);
		return op.filter(bufferedImage, null);
	}
}
