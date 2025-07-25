package todo.fileIO;

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

import todo.model.Rune;
import todo.model.RunePath;
import todo.model.Shards;
import todo.view.CustomColors;

/**
 * Loads all required images and transforms them to grayscale.
 */
public final class Images {
	private final int mainRunePathIconSize = 55;
	private final int secondRunePathIconSize = 40;
	private final int keyStoneRuneIconSize = 60;
	private final int slotRuneIconSize = 35;
	private final int shardIconSize = 28;

	private final Map<String, Image> mainRunePathImages = fetchRunePathImages(mainRunePathIconSize);
	private final Map<String, Image> secondRunePathImages = fetchRunePathImages(secondRunePathIconSize);
	private final Map<String, Image> keyStoneImages = fetchKeyStoneImages();
	private final Map<String, Image> slotRuneImages = fetchSlotRuneImages();
	private final Map<String, Image> shardImages = fetchShardImages();

	private final Map<String, Image> mainRunePathGrayImages = getGrayImages(mainRunePathImages);
	private final Map<String, Image> secondRunePathGrayImages = getGrayImages(secondRunePathImages);
	private final Map<String, Image> keyStoneGrayImages = getGrayImages(keyStoneImages);
	private final Map<String, Image> slotRuneGrayImages = getGrayImages(slotRuneImages);
	private final Map<String, Image> shardGrayImages = getGrayImages(shardImages);

	private Map<String, Image> fetchRunePathImages(final int iconSize) {
		final Map<String, Image> images = new HashMap<>();
		for (final RunePath runePath : RunePath.ALL) {
			images.put(runePath.getName(), getImageFromName(runePath.getName(), "png", iconSize, iconSize));
		}
		return images;
	}

	private Map<String, Image> fetchKeyStoneImages() {
		final Map<String, Image> images = new HashMap<>();
		for (final RunePath runePath : RunePath.ALL) {
			for (final Rune keyStone : runePath.getKeyStones()) {
				images.put(keyStone.getName(),
						getImageFromName(keyStone.getName(), "png", keyStoneRuneIconSize, keyStoneRuneIconSize));
			}
		}
		return images;
	}

	private Map<String, Image> fetchSlotRuneImages() {
		final Map<String, Image> images = new HashMap<>();
		for (final RunePath runePath : RunePath.ALL) {
			for (final List<Rune> slotRuneRow : runePath.getSlotRunes()) {
				for (final Rune slotRune : slotRuneRow) {
					images.put(slotRune.getName(),
							getImageFromName(slotRune.getName(), "png", slotRuneIconSize, slotRuneIconSize));
				}
			}
		}
		return images;
	}

	private Map<String, Image> fetchShardImages() {
		final Map<String, Image> images = new HashMap<>();
		for (final List<Rune> shardRow : Shards.ALL) {
			for (final Rune shard : shardRow) {
				images.put(shard.getName(), getImageFromName(shard.getName(), "png", shardIconSize, shardIconSize));
			}
		}
		return images;
	}

	private Image getImageFromName(final String name, final String imageType, final int width, final int height) {
		try {
			final String imageFileName = name.replaceAll("[^A-Za-z0-9]", "") + "." + imageType;
			final Image image = ImageIO.read(getClass().getResourceAsStream("/" + imageFileName));
			if (image == null) {
				throw new IllegalArgumentException();
			}
			return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		} catch (final IOException | RuntimeException e) {
			throw new IllegalArgumentException(String.format("Unable to get image for rune '%s'", name));
		}
	}

	private Map<String, Image> getGrayImages(final Map<String, Image> images) {
		final Map<String, Image> grayImages = new HashMap<>();
		for (final String imageKey : images.keySet()) {
			final Image image = images.get(imageKey);
			grayImages.put(imageKey, getGrayScale(image));
		}
		return grayImages;
	}

	private Image getGrayScale(final Image image) {
		final BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		final Graphics2D painter = bufferedImage.createGraphics();
		painter.setColor(CustomColors.BACKGROUND);
		painter.fillRect(0, 0, image.getWidth(null), image.getHeight(null));
		painter.drawImage(image, 0, 0, null);
		painter.dispose();
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp op = new ColorConvertOp(cs, null);
		return op.filter(bufferedImage, null);
	}

	public Map<String, Image> getMainRunePathImages() {
		return mainRunePathImages;
	}

	public Map<String, Image> getSecondRunePathImages() {
		return secondRunePathImages;
	}

	public Map<String, Image> getMainRunePathGrayImages() {
		return mainRunePathGrayImages;
	}

	public Map<String, Image> getSecondRunePathGrayImages() {
		return secondRunePathGrayImages;
	}

	public Map<String, Image> getKeyStoneImages() {
		return keyStoneImages;
	}

	public Map<String, Image> getKeyStoneGrayImages() {
		return keyStoneGrayImages;
	}

	public Map<String, Image> getSlotRuneImages() {
		return slotRuneImages;
	}

	public Map<String, Image> getSlotRuneGrayImages() {
		return slotRuneGrayImages;
	}

	public Map<String, Image> getShardImages() {
		return shardImages;
	}

	public Map<String, Image> getShardGrayImages() {
		return shardGrayImages;
	}
}
