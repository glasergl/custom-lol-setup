package de.glasergl.custom.lol.setup.io;

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

import de.glasergl.custom.lol.setup.Main;
import de.glasergl.custom.lol.setup.model.entity.Champion;
import de.glasergl.custom.lol.setup.model.entity.Role;
import de.glasergl.custom.lol.setup.model.entity.Rune;
import de.glasergl.custom.lol.setup.model.entity.RunePath;
import lombok.Getter;

/**
 * Loads all required images and transforms them to grayscale.
 */
public final class Images {
    private final int mainRunePathIconSize = 55;
    private final int secondRunePathIconSize = 40;
    private final int keyStoneRuneIconSize = 60;
    private final int slotRuneIconSize = 35;
    private final int shardIconSize = 28;

    private final @Getter Map<RunePath, Image> mainRunePathImages = fetchRunePathImages(mainRunePathIconSize);
    private final @Getter Map<RunePath, Image> secondRunePathImages = fetchRunePathImages(secondRunePathIconSize);
    private final @Getter Map<Rune, Image> keyStoneImages = fetchKeyStoneImages();
    private final @Getter Map<Rune, Image> slotRuneImages = fetchSlotRuneImages();
    private final @Getter Map<Rune, Image> shardImages = fetchShardImages();

    private final @Getter Map<RunePath, Image> mainRunePathGrayImages = getGrayImages(mainRunePathImages);
    private final @Getter Map<RunePath, Image> secondRunePathGrayImages = getGrayImages(secondRunePathImages);
    private final @Getter Map<Rune, Image> keyStoneGrayImages = getGrayImages(keyStoneImages);
    private final @Getter Map<Rune, Image> slotRuneGrayImages = getGrayImages(slotRuneImages);
    private final @Getter Map<Rune, Image> shardGrayImages = getGrayImages(shardImages);

    private final @Getter Map<Champion, Image> championImages = fetchChampionImages();

    private final @Getter Image frameIcon = fetchFrameIcon();

    private final @Getter Map<Role, Image> roleImages = fetchRoleIcons();

    private Map<RunePath, Image> fetchRunePathImages(final int iconSize) {
	final Map<RunePath, Image> images = new HashMap<>();
	for (final RunePath runePath : RunePath.ALL) {
	    images.put(runePath, getImageFromName("runes/" + runePath.name(), "png", iconSize, iconSize));
	}
	return images;
    }

    private Map<Role, Image> fetchRoleIcons() {
	final Map<Role, Image> images = new HashMap<>();
	for (final Role role : Role.values()) {
	    images.put(role, getImageFromName("roles/" + role.toString(), "png", 40, 40));
	}
	return images;
    }

    private Image fetchFrameIcon() {
	try {
	    return ImageIO.read(Main.class.getResource("/runes/GATHERING_STORM.png")).getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	} catch (final IOException e) {
	    throw new IllegalStateException(e);
	}
    }

    public Image getRoleImage(final Role role) {
	return roleImages.get(role);
    }

    private Map<Rune, Image> fetchKeyStoneImages() {
	final Map<Rune, Image> images = new HashMap<>();
	for (final RunePath runePath : RunePath.ALL) {
	    for (final Rune keyStone : runePath.keyStones()) {
		images.put(keyStone, getImageFromName("runes/" + keyStone.name(), "png", keyStoneRuneIconSize, keyStoneRuneIconSize));
	    }
	}
	return images;
    }

    private Map<Rune, Image> fetchSlotRuneImages() {
	final Map<Rune, Image> images = new HashMap<>();
	for (final RunePath runePath : RunePath.ALL) {
	    for (final List<Rune> slotRuneRow : runePath.slotRuneRows()) {
		for (final Rune slotRune : slotRuneRow) {
		    images.put(slotRune, getImageFromName("runes/" + slotRune.name(), "png", slotRuneIconSize, slotRuneIconSize));
		}
	    }
	}
	return images;
    }

    private Map<Rune, Image> fetchShardImages() {
	final Map<Rune, Image> images = new HashMap<>();
	for (final List<Rune> shardRow : RunePath.SHARDS) {
	    for (final Rune shard : shardRow) {
		images.put(shard, getImageFromName("runes/" + shard.name(), "png", shardIconSize, shardIconSize));
	    }
	}
	return images;
    }

    private Map<Champion, Image> fetchChampionImages() {
	final Map<Champion, Image> images = new HashMap<>();
	for (final Champion champion : Champion.values()) {
	    images.put(champion, getImageFromName("champions/" + champion.toString(), "png", 40, 40));
	}
	return images;
    }

    private Image getImageFromName(final String name, final String imageType, final int width, final int height) {
	try {
	    final String imageFileName = name + "." + imageType;
	    final Image image = ImageIO.read(getClass().getResourceAsStream("/" + imageFileName));
	    if (image == null) {
		throw new IllegalArgumentException();
	    }
	    return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	} catch (final IOException | RuntimeException e) {
	    throw new IllegalArgumentException(String.format("Unable to get image for rune '%s'", name), e);
	}
    }

    private <T> Map<T, Image> getGrayImages(final Map<T, Image> images) {
	final Map<T, Image> grayImages = new HashMap<>();
	for (final T imageKey : images.keySet()) {
	    final Image image = images.get(imageKey);
	    grayImages.put(imageKey, getGrayScale(image));
	}
	return grayImages;
    }

    private Image getGrayScale(final Image image) {
	final BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	final Graphics2D painter = bufferedImage.createGraphics();
	painter.fillRect(0, 0, image.getWidth(null), image.getHeight(null));
	painter.drawImage(image, 0, 0, null);
	painter.dispose();
	ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
	ColorConvertOp op = new ColorConvertOp(cs, null);
	return op.filter(bufferedImage, null);
    }

    public Image getImage(final Champion champion) {
	return championImages.get(champion);
    }
}
